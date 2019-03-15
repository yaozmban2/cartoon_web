package cn.yu.cartoon.cartoon_web.pojo.dto;

/**
 * 国家表的映射类
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 15:59
 **/
public class Country {

    private Integer countryId;

    private String countryName;

    private String imgUri;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", imgUri='" + imgUri + '\'' +
                '}';
    }
}
