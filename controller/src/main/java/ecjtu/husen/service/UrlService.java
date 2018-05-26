package ecjtu.husen.service;

import ecjtu.husen.pojo.DAO.Url;
import ecjtu.husen.util.Page;

/**
 * @author 11785
 */
public interface UrlService {
    void addUrl(Url url);

    Page<Url> page(Integer currentPage);

    Url findById(Integer urlId);

    void update(Url url);

    void delete(Integer urlId);
}
