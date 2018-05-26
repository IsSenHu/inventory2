package ecjtu.husen.service;

import ecjtu.husen.dao.UserDao;
import ecjtu.husen.exception.UserHadRegistForbbiden;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DTO.Gender;
import ecjtu.husen.pojo.DTO.UserStatu;
import ecjtu.husen.pojo.DTO.UserVO;
import ecjtu.husen.util.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 11785
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public boolean userRegist(UserVO userVO) throws Exception {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO, userPO);
        //先检查手机号是否已被注册
        UserPO userPO1 = userDao.findUserByUsername(userVO.getMobilePhone());
        if(userPO == null){
            throw new UserHadRegistForbbiden("该用户已被注册");
        }
        /*
        * 1.默认禁用，需用admin启用
        * 2.默认手机号为用户名，后面可修改
        * 3.默认性别为男
        * */
        userPO.setUserStatu(UserStatu.disable);
        userPO.setUsername(userVO.getMobilePhone());
        userPO.setGender(Gender.male);
        try {
            userDao.userRegist(userPO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ifUserRegisted(String mobilePhone) throws Exception {
        return userDao.findUserByUsername(mobilePhone) == null ? false : true;
    }

    @Override
    public UserPO findByUsername(String username) throws Exception {
        UserPO userPO = userDao.findUserByUsername(username);
        if(userPO != null){
            return userPO;
        }
        return null;
    }

    @Override
    public Page<UserPO> page(Integer currentPage) {
        Page<UserPO> page = new Page<>();
        //设置当前页
        page.setCurrentPage(currentPage);
        //每页显示10个
        page.setPageSize(10);
        /*
        * 查询出总记录数，并设置
        * */
        int rowsTotal = userDao.findTotal();
        page.setRowsTotal(rowsTotal);
        /*
        * 开始计算总共有多少页并进行设置
        * */
        if(rowsTotal % page.getPageSize() == 0){
            page.setTotalPage(rowsTotal / page.getPageSize());
        }else {
            page.setTotalPage(rowsTotal / page.getPageSize() + 1);
        }
        /*
        * 判断传入的当前页是否合法
        * */
        if(currentPage <= 0){
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }else if (currentPage > page.getTotalPage()){
            currentPage = page.getTotalPage();
            page.setCurrentPage(currentPage);
        }
        List<UserPO> userPOS = userDao.page(currentPage, page.getPageSize());
        page.setContent(userPOS);
        return page;
    }

    @Override
    public UserPO update(UserPO userPO) {
        return userDao.update(userPO);
    }
}
