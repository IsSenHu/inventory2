package com.husen.service.impl;

import com.husen.controller.BossController;
import com.husen.dao.BossDao;
import com.husen.dao.BusinessLicenseDao;
import com.husen.dao.StoresDao;
import com.husen.exception.UserHadRegistForbbiden;
import com.husen.model.Boss;
import com.husen.model.BusinessLicense;
import com.husen.model.Stores;
import com.husen.service.BossService;
import com.husen.util.GlobalVar;
import com.husen.vo.One;
import com.husen.vo.Three;
import com.husen.vo.Two;
import ecjtu.husen.pojo.DTO.UserStatu;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author 11785
 */
@Service
@Transactional
public class BossServiceImpl implements BossService {
    private static Logger logger = LogManager.getLogger(BossServiceImpl.class);
    @Autowired
    private BossDao bossDao;
    @Autowired
    private StoresDao storesDao;
    @Autowired
    private BusinessLicenseDao businessLicenseDao;

    @Override
    public boolean isRegisted(String mobilePhone) {
        try {
            logger.info("{}开始查找Boss");
            Boss boss = bossDao.findByPhone(mobilePhone);
            logger.info("{}根据用户名查找出的Boss：" + boss);
            return Objects.isNull(boss) ? false : true;
        }catch (Exception e){
            logger.error("{}发生错误");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean regist(Boss boss) throws UserHadRegistForbbiden {
        /*
        * 将用户的手机号作为默认的用户名
        * 新注册的商家是启用的
        * 新注册的商家是未被认证的
        * */
        if(boss == null){
            return false;
        }
        boss.setUsername(boss.getPhone());
        boss.setStatu(UserStatu.enable.getValue().toString());
        boss.setIsAuthenticate(GlobalVar.NO_AUTHENTICATE);
        try {
            Boss boss1 = bossDao.save(boss);
            return boss1 == null ? false : true;
        }catch (Exception e){
            throw new UserHadRegistForbbiden("该手机号：" + boss.getPhone() + "已被注册");
        }
    }

    /**
     * 根据用户名查找boss
     * @param username
     * @return
     */
    @Override
    public Boss findByUsername(String username) {
        if(StringUtils.isNotBlank(username)){
            try {
                return bossDao.findByUsername(username);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public void saveStores(Boss boss, One one, Two two, Three three) {
        try {
            Stores stores = new Stores();
            BusinessLicense businessLicense = new BusinessLicense();
            boss = bossDao.getOne(boss.getBossId());

            businessLicense.setPicPath(two.getBlPicPath());
            businessLicense.setRegistNumber(two.getRegistNumber());

            stores.setAddress(one.getAddress());
            stores.setName(one.getStoresName());
            stores.setSportStr(one.getSportStr());

            boss.setCardId(three.getCardId());
            boss.setName(three.getRealName());
            boss.setCardBefore(three.getCardBeforePic());
            boss.setCardAfter(three.getCardAfterPic());
            boss.setIsAuthenticate(GlobalVar.AUTHENTICATEING);
            bossDao.save(boss);

            stores.setBoss(boss);

            stores = storesDao.save(stores);

            businessLicense.setStores(stores);

            businessLicense = businessLicenseDao.save(businessLicense);

            stores.setBusinessLicense(businessLicense);

            stores = storesDao.save(stores);
            boss.setStores(stores);
            bossDao.save(boss);
            logger.info("保存成功:{}");
        }catch (Exception e){
            logger.error("操作失败:{}" + e.getMessage());
        }
    }
}
