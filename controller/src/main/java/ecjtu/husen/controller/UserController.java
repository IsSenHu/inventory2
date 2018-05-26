package ecjtu.husen.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import ecjtu.husen.exception.UserHadForbbidenException;
import ecjtu.husen.exception.UserHadRegistForbbiden;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DTO.UserVO;
import ecjtu.husen.rabbitmq.service.Producer;
import ecjtu.husen.service.UserService;
import ecjtu.husen.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 11785
 * 和用户相关的控制层
 */
@Controller
public class UserController {
    @Autowired
    private Producer producer;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    private static Logger logger = LogManager.getLogger(UserController.class);
    private static final String SMS_CODE = "t_sms_code";
    private String queueId = "send_sms";
    private String key = "registQueueKey";
    private static Map<String, String> OK = new HashMap<>(1);
    private static Map<String, String> FAILE = new HashMap<>(1);
    private static Map<String, String> UNKNOWN = new HashMap<>(1);
    private static Map<String, String> FORBIDDEN = new HashMap<>(1);
    private static Map<String, String> EXISTSED = new HashMap<>(1);
    private static final String YES = "yes";
    private static final String DATABASE_ERROR = "数据库错误";
    static {
        OK.put("result", "ok");
        FAILE.put("result", "faile");
        UNKNOWN.put("result", "unknown");
        FORBIDDEN.put("result", "forbidden");
        EXISTSED.put("result", "existed");
    }
    /*
    * 测试消息队列
    * */
    @RequestMapping("/test.action")
    public void testQueue(){
        try {
            // 注意：第二个属性是 Queue 与 交换机绑定的路由
            producer.sendQueue(queueId + "_exchange", key, "ok");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    * 获取短信验证码
    * */
    @RequestMapping(value = "/sendSms.action", method = RequestMethod.POST)
    public@ResponseBody Map<String, String> sendSms(String mobilePhone) throws Exception {
        //检查手机号是否已被注册
        if(userService.ifUserRegisted(mobilePhone)){
            return EXISTSED;
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        //短信API产品名称（短信产品名固定，无需修改）
        final String product = "Dysmsapi";
        //短信API产品域名（接口地址固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";
        //替换成你的AK
        //你的accessKeyId,参考本文档步骤2
        final String accessKeyId = "LTAI07hchIXDO0ZY";
        //你的accessKeySecret，参考本文档步骤2
        final String accessKeySecret = "d08PwCrtoiQGjroNo8IoxdQ4OhuxXO";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            logger.info(e.getMessage());
            return FAILE;
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(mobilePhone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("爱动网");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_123739127");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //随机生成6位验证码
        int code = 100000 + (int) (Math.random() * 900000);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            logger.info(e.getMessage());
            return FAILE;
        }
        logger.info(sendSmsResponse.getCode());
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            /*
            * 如果发送成功了，就把验证码和手机号存进redis中，并设置超时时间为5分钟
            * */
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            logger.info(code);
            valueOperations.set(mobilePhone, Integer.valueOf(code).toString(), 60 * 5, TimeUnit.SECONDS);
            return OK;
        }
        return FAILE;
    }
    /*
    * 用户注册
    * */
    @RequestMapping("/userRegist.action")
    public @ResponseBody Map<String, String> userRegist(UserVO userVO, String valiCode){
        //先校验短信验证码
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        if(StringUtils.isNotBlank(valiCode) && !StringUtils.equals(valiCode, valueOperations.get(userVO.getMobilePhone()))){
            return FAILE;
        }else {
            //进行注册
            try {
                if(userService.userRegist(userVO)){
                    //注册成功后把验证码删除，防止用户再次注册
                    stringRedisTemplate.delete(userVO.getMobilePhone());
                    return OK;
                }
            } catch (Exception e) {
                if(e instanceof UserHadRegistForbbiden){
                    //用户已被注册
                    logger.info(e.getMessage());
                    return EXISTSED;
                }
            }
            return FAILE;
        }
    }
    @RequestMapping("/userLogin.action")
    public @ResponseBody Map<String, String> userLogin(HttpServletRequest request, String username, String password, String rememberMe){
        Subject currentUser = SecurityUtils.getSubject();
        /*
        * 如果没有被认证，即，没有登录
        * */
        if(!currentUser.isAuthenticated()){
            /*
            * 把用户名和密码封装为UsernamePasswordToken对象
            * */
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            /*
            * rememberMe
            * */
            if(StringUtils.isNotBlank(rememberMe) && StringUtils.equals(rememberMe, YES)){
                token.setRememberMe(true);
            }
            try {
                //执行登录
                currentUser.login(token);
                //捕捉更多的异常......
                //所有认证时异常的父类
            }catch (Exception e){
                if(e instanceof UnknownAccountException){
                    /*
                * 用户名不存在
                * */
                    logger.info(e.getMessage());
                    return UNKNOWN;
                }else if(e instanceof UserHadForbbidenException){
                    //用户被禁用
                    logger.info(e.getMessage());
                    return FORBIDDEN;
                }else if (e instanceof AuthenticationException){
                    //密码错误
                    if(StringUtils.equals(e.getMessage(), DATABASE_ERROR)){
                        logger.info(e.getMessage());
                        return FORBIDDEN;
                    }else {
                        //否则密码错误
                        logger.info(e.getMessage());
                        return FAILE;
                    }
                }
            }
        }
        HttpSession session = request.getSession();
        UserPO userVO = null;
        try {
            userVO = userService.findByUsername(username);
            //登录的用户
            session.setAttribute("user", userVO);
            //登录的时间
            session.setAttribute("logTime", new Date());
            //登录ip
            session.setAttribute("ip", getIpAddress(request));
            return OK;
        } catch (Exception e) {
            e.printStackTrace();
            return FORBIDDEN;
        }
    }

    /**
     * 分页查询出系统的管理员
     * 按权限大小从大到下排列
     * @return
     */
    @RequestMapping("/pageUser.action")
    private ModelAndView pageUser(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<UserPO> page = userService.page(currentPage);
        return new ModelAndView("views/jsp/manager")
                .addObject("page", page);
    }

    /**
     * 跳转到用户信息页面
     * @return
     */
    @RequestMapping("/showUser.action")
    private ModelAndView showUser(){
        return new ModelAndView("views/jsp/updateUser");
    }

    @RequestMapping("/updateUser.action")
    private ModelAndView updateUser(UserPO userPO, HttpServletRequest request){
        if(StringUtils.isBlank(userPO.getUsername())){
            return new ModelAndView("views/jsp/updateUser")
                    .addObject("msg", "用户名不能为空");
        }
        UserPO newUser = userService.update(userPO);
        request.getSession().setAttribute("user", newUser);
        return new ModelAndView("redirect:/showUser.action")
                .addObject("msg", "修改成功");
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
