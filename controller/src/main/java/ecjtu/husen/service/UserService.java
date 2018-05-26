package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DTO.UserVO;
import ecjtu.husen.util.Page;

/**
 * @author 11785
 */
public interface UserService {
    boolean userRegist(UserVO userVO) throws Exception;
    boolean ifUserRegisted(String mobilePhone) throws Exception;
    UserPO findByUsername(String username) throws Exception;
    Page<UserPO> page(Integer currentPage);

    UserPO update(UserPO userPO);
}
