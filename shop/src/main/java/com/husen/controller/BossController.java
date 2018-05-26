package com.husen.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.husen.exception.UserHadForbbidenException;
import com.husen.exception.UserHadRegistForbbiden;
import com.husen.model.Boss;
import com.husen.model.Stores;
import com.husen.service.BossService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author husen
 */
@Controller
public class BossController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BossService bossService;

    private static Logger logger = LogManager.getLogger(BossController.class);
    private static final String SMS_CODE = "t_sms_code";
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
    * 获取短信验证码
    * */
    @RequestMapping(value = "/sendSms.do", method = RequestMethod.POST)
    private @ResponseBody Map<String, String> sendSms(String mobilePhone){
        /*
        * 检查手机号是否被注册
        * */
        if(bossService.isRegisted(mobilePhone)){
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
        request.setTemplateCode("SMS_127990077");
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


    /**
     * 商家注册
     * @param boss
     * @param valiCode
     * @return
     */
    @RequestMapping("/bossRegist.do")
    private @ResponseBody Map<String, String> bossRegist(Boss boss, String valiCode){
        //先校验短信验证码W
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        if(StringUtils.isNotBlank(valiCode) && !StringUtils.equals(valiCode, valueOperations.get(boss.getPhone()))){
            return FAILE;
        }else {
            try{
                if(bossService.regist(boss)){
                    //注册成功把验证码删除
                    stringRedisTemplate.delete(boss.getPhone());
                    return OK;
                }
            }catch (Exception e){
                if(e instanceof UserHadRegistForbbiden){
                    //用户已被注册
                    logger.info(e.getMessage());
                    return EXISTSED;
                }
            }
            return FAILE;
        }
    }

    /**
     * 商家登录
     * @param request
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping("/bossLogin.do")
    private @ResponseBody Map<String, String> login(HttpServletRequest request, String username, String password, String rememberMe){
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
        Boss boss = null;
        try {
            boss = bossService.findByUsername(username);
            session.setAttribute("boss", boss);
            Map<String, String> map = new HashMap<>(1);
            map.put("isAuthenticate", boss.getIsAuthenticate());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return FORBIDDEN;
        }
    }
}
