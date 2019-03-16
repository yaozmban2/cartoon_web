package cn.yu.cartoon.cartoon_web.pojo.vo.cartoonvo;

import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 12:55
 **/
public class CartoonInfoVo extends Cartoon {

    private String countryName;

    private Integer chapterCount;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(Integer chapterCount) {
        this.chapterCount = chapterCount;
    }
}
