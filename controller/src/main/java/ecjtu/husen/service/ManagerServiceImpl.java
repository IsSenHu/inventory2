package ecjtu.husen.service;

import ecjtu.husen.dao.ManagerDao;
import ecjtu.husen.pojo.DAO.Applyer;
import ecjtu.husen.pojo.DAO.RolePO;
import ecjtu.husen.pojo.DAO.UserPO;
import ecjtu.husen.pojo.DAO.UserRolePO;
import ecjtu.husen.pojo.DTO.UserStatu;
import ecjtu.husen.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 11785
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;

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
        int rowsTotal = managerDao.findTotal();
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
        List<UserPO> userPOS = managerDao.page(currentPage, page.getPageSize());
        page.setContent(userPOS);
        return page;
    }

    @Override
    public void deleteById(Integer userId) {
        /*
        * 删除一个管理员，并不是真的删除该管理员，而是在逻辑上删除
        * */
        UserPO user = managerDao.findById(userId);
        user.setUserStatu(UserStatu.deleted);
        managerDao.deleteById(user);
    }

    @Override
    public void updateStatu(Integer userId) {
        /*
        * 先查出该用户目前的状态
        *   如果是禁用2则启用
        *   如果是启用1则禁用
        * */
        UserPO user = managerDao.findById(userId);
        if(user.getUserStatu().getValue() == 1){
            user.setUserStatu(UserStatu.disable);
        }else if(user.getUserStatu().getValue() == 2){
            user.setUserStatu(UserStatu.enable);
        }
        managerDao.updateStatu(user);
    }

    @Override
    public List<RolePO> findAllRole() {
        return managerDao.findAllRole();
    }

    @Override
    public List<UserRolePO> findMyRP(Integer userId) {
        return managerDao.findMyUR(userId);
    }

    @Override
    public void updateUserRole(Integer userId, List<RolePO> roles) {
        /*
        * 先删除拥有的用户角色关系
        * 再将roles的角色添加进用户角色关系
        * */
        managerDao.deleteUR(userId);
        for(RolePO role : roles){
            managerDao.addUR(userId, role);
        }
    }
}
