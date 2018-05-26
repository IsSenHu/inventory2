package com.husen.service;

import com.husen.model.Boss;
import com.husen.util.JsonResult;
import com.husen.util.Page;
import com.husen.vo.RefundVO;

/**
 * @author husen
 */
public interface RefundService {
    Page<RefundVO> refunds(Boss boss, Integer userId, Integer currentPage);

    JsonResult refund(Integer refundId);
}
