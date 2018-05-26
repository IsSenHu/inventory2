package com.husen.vo;

import java.io.Serializable;

/**
 * @author 11785
 */
public class Three implements Serializable {
    private String realName;
    private String cardId;
    private String cardBeforePic;
    private String cardAfterPic;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardBeforePic() {
        return cardBeforePic;
    }

    public void setCardBeforePic(String cardBeforePic) {
        this.cardBeforePic = cardBeforePic;
    }

    public String getCardAfterPic() {
        return cardAfterPic;
    }

    public void setCardAfterPic(String cardAfterPic) {
        this.cardAfterPic = cardAfterPic;
    }

    @Override
    public String toString() {
        return "Three{" +
                "realName='" + realName + '\'' +
                ", cardId='" + cardId + '\'' +
                ", cardBeforePic='" + cardBeforePic + '\'' +
                ", cardAfterPic='" + cardAfterPic + '\'' +
                '}';
    }
}
