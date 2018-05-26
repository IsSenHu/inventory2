package ecjtu.husen.dao;

import ecjtu.husen.model.OperationNote;

import java.util.List;

/**
 * @author 11785
 */
public interface VerfyNoteDao {
    int findTotal();

    List<OperationNote> page(Integer currentPage, int pageSize);

    List<OperationNote> afterTenNote();

    int findTotal2();

    List<OperationNote> page2(Integer currentPage, int pageSize);

    int findTotal3();

    List<OperationNote> page3(Integer currentPage, int pageSize);

    void save(OperationNote operationNote);
}
