package com.husen.reamls;

import com.husen.dao.BossDao;
import com.husen.model.Boss;
import com.husen.exception.UserHadForbbidenException;
import ecjtu.husen.pojo.DTO.UserStatu;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 11785
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private BossDao bossDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /*
        * 1. 把AuthenticationToken转化为UsernamePasswordToken
        * 2. 从UsernamePasswordToken中来获取username
        * 3. 调用数据库的方法，从数据库中查询username对应的用户记录
        * 4. 若用户不存在，则可以抛出UnknownAccountException异常
        * 5. 根据用户信息情况，决定是否需要抛出其他的AuthenticationException异常
        * 6. 根据用户情况，构建AuthenticationInfo对象并返回，通常使用的实现类为：SimpleAuthenticationInfo
        * */
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String) token.getPrincipal();
        Boss boss = null;
        try {
            boss = bossDao.findByUsername(username);
        } catch (Exception e) {
            throw new AuthenticationException("数据库错误");
        }
        if (boss == null){
                throw new UnknownAccountException("该用户不存在");
            }
            if (Integer.valueOf(boss.getStatu()).equals(UserStatu.disable.getValue())){
                throw new UserHadForbbidenException("用户已被禁用，无法登录，请联系管理员");
            }
            Object principal = boss.getUsername();
            ByteSource salt = ByteSource.Util.bytes(boss.getPassword());
            //使用MD5加密1024次
            Object credentials = new SimpleHash("MD5", boss.getPassword(), salt, 1024);
            String realmName = getName();
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, salt, realmName);
            return info;
    }
}
