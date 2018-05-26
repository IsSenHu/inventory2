package ecjtu.husen.service;

import ecjtu.husen.model.OperationNote;
import ecjtu.husen.util.Page;

import java.util.List;

/**
 * @author 11785
 */
public interface VerfyNoteService {
    Page<OperationNote> page(Integer currentPage);

    List<OperationNote> afterTenNote();

    Page<OperationNote> page2(Integer currentPage);

    Page<OperationNote> page3(Integer currentPage);
}
