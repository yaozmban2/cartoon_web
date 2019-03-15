package cn.yu.cartoon.cartoon_web.pojo.dto;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 20:26
 **/
public class CartoonType {

    private Integer typeId;

    private String typeName;

    private String imgUri;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public String toString() {
        return "CartoonType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", imgUri='" + imgUri + '\'' +
                '}';
    }
}
