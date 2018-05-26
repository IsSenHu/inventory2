package ecjtu.husen.reamls;

import ecjtu.husen.dao.UserDao;
import ecjtu.husen.exception.UserHadForbbidenException;
import ecjtu.husen.pojo.DAO.*;
import ecjtu.husen.pojo.DTO.UserStatu;
import ecjtu.husen.pojo.DTO.UserVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 11785
 */
public class ShiroRealm extends AuthorizingRealm {
    private static Logger logger = LogManager.getLogger(ShiroRealm.class);
    @Autowired
    private UserDao userDao;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /**
         * 获得角色和权限
         * */
        logger.info("获取授权信息");
        String username = principalCollection.toString();
        logger.info("获取的用户名是:{}", username);
        Set<String> roles = new HashSet<>(10);
        try {
            List<RolePO> result = userDao.findRoleByUserId(userDao.findUserByUsername(username).getUserId());
            for(RolePO role : result){
                roles.add(role.getRoleName());
            }
            logger.info("该用户拥有的角色:{}", roles);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
            for(RolePO role : result){
                List<PermissionPO> permissions = userDao.findPermissionByRoleId(role.getRoleId());
                for(PermissionPO permission : permissions){
                    info.addStringPermission(permission.getPermissionName());
                }
                logger.info("角色" + role.getRoleName() + "拥有的权限:{}", permissions);
            }
            return info;
        } catch (Exception e) {
            logger.error("获取权限失败:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
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
        UserPO userPO = null;
        try {
            userPO = userDao.findUserByUsername(username);
        } catch (Exception e) {
            throw new AuthenticationException("数据库错误");
        }
        if (userPO == null){
                throw new UnknownAccountException("该用户不存在");
            }
            if (userPO.getUserStatu().equals(UserStatu.disable)){
                throw new UserHadForbbidenException("用户已被禁用，无法登录，请联系管理员");
            }
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userPO, userVO);
            Object principal = userVO.getUsername();
            ByteSource salt = ByteSource.Util.bytes(userVO.getPassword());
            //使用MD5加密1024次
            Object credentials = new SimpleHash("MD5", userVO.getPassword(), salt, 1024);
            String realmName = getName();
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, salt, realmName);
            return info;
    }
}
