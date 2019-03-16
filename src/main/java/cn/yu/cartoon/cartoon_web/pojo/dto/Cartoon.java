package cn.yu.cartoon.cartoon_web.pojo.dto;

import java.util.Date;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/17 16:06
 **/
public class Cartoon {

    private Integer cartoonId;

    private String cartoonName;

    private String cartoonAuthor;

    private Byte cartoonCountry;

    private String cartoonDescription;

    private String cartoonUri;

    private Date cartoonUpdateTime;

    private Byte isDeleted;

    private Integer collectCount;

    private Integer readCount;

    public Integer getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(Integer cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String getCartoonName() {
        return cartoonName;
    }

    public void setCartoonName(String cartoonName) {
        this.cartoonName = cartoonName;
    }

    public String getCartoonAuthor() {
        return cartoonAuthor;
    }

    public void setCartoonAuthor(String cartoonAuthor) {
        this.cartoonAuthor = cartoonAuthor;
    }

    public Byte getCartoonCountry() {
        return cartoonCountry;
    }

    public void setCartoonCountry(Byte cartoonCountry) {
        this.cartoonCountry = cartoonCountry;
    }

    public String getCartoonUri() {
        return cartoonUri;
    }

    public void setCartoonUri(String cartoonUri) {
        this.cartoonUri = cartoonUri;
    }

    public Date getCartoonUpdateTime() {
        return cartoonUpdateTime;
    }

    public void setCartoonUpdateTime(Date cartoonUpdateTime) {
        this.cartoonUpdateTime = cartoonUpdateTime;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCartoonDescription() {
        return cartoonDescription;
    }

    public void setCartoonDescription(String cartoonDescription) {
        this.cartoonDescription = cartoonDescription;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    @Override
    public String toString() {
        return "Cartoon{" +
                "cartoonId=" + cartoonId +
                ", cartoonName='" + cartoonName + '\'' +
                ", cartoonAuthor='" + cartoonAuthor + '\'' +
                ", cartoonCountry=" + cartoonCountry +
                ", cartoonDescription='" + cartoonDescription + '\'' +
                ", cartoonUri='" + cartoonUri + '\'' +
                ", cartoonUpdateTime=" + cartoonUpdateTime +
                ", isDeleted=" + isDeleted +
                ", collectCount=" + collectCount +
                ", readCount=" + readCount +
                '}';
    }
}
