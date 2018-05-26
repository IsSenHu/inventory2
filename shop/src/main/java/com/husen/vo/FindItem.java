package com.husen.vo;

import java.io.Serializable;

/**
 * 接收物品条件的vo
 * @author 11785
 */
public class FindItem implements Serializable {
    private String applyerId;
    private String brandId;
    private String materialId;
    private String specificationId;
    private String sportItemId;

    public String getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(String applyerId) {
        this.applyerId = applyerId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

    public String getSportItemId() {
        return sportItemId;
    }

    public void setSportItemId(String sportItemId) {
        this.sportItemId = sportItemId;
    }

    @Override
    public String toString() {
        return "FindItem{" +
                "applyerId='" + applyerId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", materialId='" + materialId + '\'' +
                ", specificationId='" + specificationId + '\'' +
                ", sportItemId='" + sportItemId + '\'' +
                '}';
    }
}
