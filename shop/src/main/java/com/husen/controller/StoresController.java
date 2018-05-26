package com.husen.controller;

import com.aliyun.oss.OSSClient;
import com.husen.model.Boss;
import com.husen.service.BossService;
import com.husen.util.GlobalVar;
import com.husen.util.JsonResult;
import com.husen.vo.One;
import com.husen.vo.Three;
import com.husen.vo.Two;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 11785
 */
@Controller
public class StoresController {
    private static Logger logger = LogManager.getLogger(StoresController.class);
    private static final String BUCKET_NAMW = "oss-husen-test";
    private static final String END_POINT = "oss-cn-shenzhen.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIwfCaedv20UgG";
    private static final String SECRET_ACCESS_KEY = "viZURAskaBPvxeN8BC1jrrohxP8znf";
    @Autowired
    private BossService bossService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    /**
     * 第一步
     * @param request
     * @param one
     * @return
     */
    @RequestMapping("/one.do")
    private @ResponseBody JsonResult<String> one(HttpServletRequest request, One one){
        logger.info("接收到的商铺信息为:{}" + one);
        Boss boss = (Boss) request.getSession().getAttribute("boss");
        if(boss == null){
            logger.info("还没有用户登录:{}" + boss);
            return new JsonResult<>("noLogin", "noLogin");
        }else if(boss.getIsAuthenticate().equals(GlobalVar.AUTHENTICATEING)){
            logger.info("已经在认证中:{}" + boss.getIsAuthenticate());
            return new JsonResult<>("no", "no");
        }else if(boss.getIsAuthenticate().equals(GlobalVar.HAVE_AUTHENTICATE)){
            logger.info("已经认证成功了:{}" + boss.getIsAuthenticate());
            return new JsonResult<>("no", "no");
        }
        String storesName = one.getStoresName();
        String address = one.getAddress();
        String sportStr = one.getSportStr();
        List<String> error = new ArrayList<>();
        if(StringUtils.isBlank(storesName)){
            error.add("noStoresName");
        }else {
            logger.info("接收到的商店名为:{}" + storesName);
        }

        if(StringUtils.isBlank(address)){
            error.add("noAddress");
        }else {
            logger.info("接收到的地址为:{}" + address);
        }
        logger.info("接收到的主营运动为:{}" + sportStr);
        if(error.size() > 0){
            logger.info("输入有错误:{}");
            return new JsonResult<>("haveError", "haveError", error);
        }
        /*
        * 检验完毕将one存进session中
        *
        * */
        HttpSession session = request.getSession();
        session.removeAttribute("one");
        session.setAttribute("one", one);
        return new JsonResult<>("ok", "ok");
    }

    /**
     * 第二步
     * @param registNumber
     * @param pic
     * @param request
     * @return
     */
    @RequestMapping("/two.do")
    private @ResponseBody JsonResult<String> two(String registNumber, MultipartFile pic, HttpServletRequest request){
        HttpSession session = request.getSession();
        logger.info("清空session里面或许有的two{}");
        session.removeAttribute("two");
        Boss boss = (Boss) session.getAttribute("boss");
        if(boss == null){
            logger.info("用户没有登陆:{}" + boss);
            return new JsonResult<>("noLogin", "noLogin");
        }else if(boss.getIsAuthenticate().equals(GlobalVar.AUTHENTICATEING)){
            logger.info("已经在认证中:{}" + boss.getIsAuthenticate());
            return new JsonResult<>("no", "no");
        }else if(boss.getIsAuthenticate().equals(GlobalVar.HAVE_AUTHENTICATE)){
            logger.info("已经认证成功了:{}" + boss.getIsAuthenticate());
            return new JsonResult<>("no", "no");
        }
        List<String> error = new ArrayList<>();
        if(StringUtils.isBlank(registNumber)){
            error.add("noRegistNumber");
        }
        if(error.size() == 0 && pic != null){
            try {
                String picPath = uploadFile(pic);
                if(StringUtils.isNotBlank(picPath)){
                    logger.info("得到的图片路径为:{}" + picPath);
                    Two two = new Two();
                    two.setRegistNumber(registNumber);
                    two.setBlPicPath(picPath);
                    logger.info("将生成的two保存进session:{}" + two);
                    session.setAttribute("two", two);
                    return new JsonResult<>("success", "success");
                }else {
                    logger.info("上传失败:{}" + picPath);
                    return new JsonResult<>("uploadFaile", "uploadFaile");
                }
            } catch (IOException e) {
                logger.error("上传过程中发生异常:{}" + e.getMessage());
                return new JsonResult<>("uploadFaile", "uploadFaile");
            }
        }else if(pic == null) {
            error.add("noUpload");
        }
        logger.info("错误个数:{}" + error.size());
        return new JsonResult<>("haveError", "haveError", error);
    }

    /**
     * 第三步
     * @param request
     * @param three
     * @param cardBefore
     * @param cardAfter
     * @return
     */
    @RequestMapping("/three.do")
    private @ResponseBody JsonResult<String> three(HttpServletRequest request, Three three, MultipartFile cardBefore, MultipartFile cardAfter){
        HttpSession session = request.getSession();
        Boss boss = (Boss) session.getAttribute("boss");
        if(boss == null){
            logger.info("用户没有登陆:{}" + boss);
            return new JsonResult<>("noLogin", "noLogin");
        }else if(boss.getIsAuthenticate().equals(GlobalVar.AUTHENTICATEING)){
            logger.info("已经在认证中:{}" + boss.getIsAuthenticate());
            return new JsonResult<>("no", "no");
        }else if(boss.getIsAuthenticate().equals(GlobalVar.HAVE_AUTHENTICATE)){
            logger.info("已经认证成功了:{}" + boss.getIsAuthenticate());
            return new JsonResult<>("no", "no");
        }
        if(three == null){
            logger.info("没有表单输入:{}" + three);
            return new JsonResult<>("noIn", "noIn");
        }
        String realName = three.getRealName();
        String cardId = three.getCardId();
        List<String> error = new ArrayList<>();
        if(StringUtils.isBlank(realName)){
            error.add("noRealName");
        }
        if(StringUtils.isBlank(cardId)){
            error.add("noCardId");
        }

        if(cardBefore == null || cardAfter == null){
            error.add("noUpload");
        }
        if(error.size() > 0){
            logger.info("输入错误个数:{}" + error.size());
            return new JsonResult<>("haveError", "haveError", error);
        }
        try {
            String beforePicPath = uploadFile(cardBefore);
            logger.info(beforePicPath);
            String afterPicPath = uploadFile(cardAfter);
            logger.info(afterPicPath);
            if(StringUtils.isBlank(beforePicPath) || StringUtils.isBlank(afterPicPath)){
                logger.error("上传失败:{}" + beforePicPath + "@" + afterPicPath);
                return new JsonResult<>("uploadFaile", "uploadFaile");
            }else {
                three.setCardBeforePic(beforePicPath);
                three.setCardAfterPic(afterPicPath);
                One one = (One) session.getAttribute("one");
                Two two = (Two) session.getAttribute("two");
                logger.info("one:{}" + one);
                logger.info("two:{}" + two);
                logger.info("three:{}" + three);
                logger.info("boss:{}" + boss);
                if(Objects.nonNull(one) && Objects.nonNull(two) && Objects.nonNull(three) && Objects.nonNull(boss)){
                    logger.info("新开一个线程去保存商家入驻信息");
                    taskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            bossService.saveStores(boss, one, two, three);
                        }
                    });
                }
                session.setAttribute("boss", boss);
                return new JsonResult<>("success", "success");
            }
        } catch (IOException e) {
            logger.error("上传失败:{}");
            e.printStackTrace();
            return new JsonResult<>("uploadFaile", "uploadFaile");
        }
    }

    /**
     * 封装一个上传文件的方法
     * @param multipartFile
     */
    public static String uploadFile(MultipartFile multipartFile) throws IOException {
        //上传照片
        if(multipartFile != null){
            //原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            //上传图片
            String newFileName = null;
            if(multipartFile != null && StringUtils.isNotBlank(originalFilename)){
                //新的图片名称
                newFileName = UUID.randomUUID() +
                        originalFilename.substring(originalFilename.lastIndexOf("."));
                String filePath = LocalDateTime.now().getMonth().toString() + "/" + newFileName;
                //在OSS上存储图片的地址
                String picPath = "http://" + BUCKET_NAMW + "." + END_POINT + "/" + filePath;
                OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
                ossClient.putObject(BUCKET_NAMW, filePath, multipartFile.getInputStream());
                ossClient.shutdown();
                return picPath;
            }
        }
        return null;
    }
}
