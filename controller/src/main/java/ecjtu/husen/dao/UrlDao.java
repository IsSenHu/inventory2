package ecjtu.husen.dao;

import ecjtu.husen.pojo.DAO.Url;

import java.util.List;

/**
 * @author 11785
 */
public interface UrlDao {
    int findTotal();

    List<Url> page(Integer currentPage, int pageSize);

    Url findById(Integer urlId);

    void deletePermissionUrl(Integer urlId);

    void deleteById(Integer urlId);

    void add(Url url);

    void update(Url url);
}
