package com.husen.service;

import com.husen.exception.UserHadRegistForbbiden;
import com.husen.model.Boss;
import com.husen.vo.One;
import com.husen.vo.Three;
import com.husen.vo.Two;

/**
 * @author 11785
 */
public interface BossService {
    boolean isRegisted(String mobilePhone);

    boolean regist(Boss boss) throws UserHadRegistForbbiden;

    Boss findByUsername(String username);

    void saveStores(Boss boss, One one, Two two, Three three);
}
