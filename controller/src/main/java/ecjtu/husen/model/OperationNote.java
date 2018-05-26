package ecjtu.husen.model;

import ecjtu.husen.pojo.DAO.UserPO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 记录入库单操作的实体
 * @author 11785
 */
@Entity(name = "t_inNote")
public class OperationNote implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inNoteId;

    /**
     * 操作的人
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserPO operationPerson;

    /**
     * 操作的时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date operationTime;

    /**
     * 操作的内容
     */
    @Column
    private String operationContent;

    /**
     * 操作的结果
     */
    @Column
    private String operationResult;

    public Integer getInNoteId() {
        return inNoteId;
    }

    public void setInNoteId(Integer inNoteId) {
        this.inNoteId = inNoteId;
    }

    public UserPO getOperationPerson() {
        return operationPerson;
    }

    public void setOperationPerson(UserPO operationPerson) {
        this.operationPerson = operationPerson;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    @Override
    public String toString() {
        return "OperationNote{" +
                "inNoteId=" + inNoteId +
                ", operationTime=" + operationTime +
                ", operationContent='" + operationContent + '\'' +
                ", operationResult='" + operationResult + '\'' +
                '}';
    }
}
