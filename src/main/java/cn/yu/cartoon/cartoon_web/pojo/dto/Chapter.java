package cn.yu.cartoon.cartoon_web.pojo.dto;

import cn.yu.cartoon.cartoon_web.util.JsonDateFormatFull;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/17 16:48
 **/
public class Chapter {

    private Integer chapterId;

    private Integer sortNum;

    private String chapterName;

    @JsonSerialize(using = JsonDateFormatFull.class)
    private Date chapterUpdateTime;

    private Integer cartoonId;

    private Integer chapterPrice;

    private String chapterUri;

    private Integer chapterPageCount;

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Date getChapterUpdateTime() {
        return chapterUpdateTime;
    }

    public void setChapterUpdateTime(Date chapterUpdateTime) {
        this.chapterUpdateTime = chapterUpdateTime;
    }

    public Integer getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(Integer cartoonId) {
        this.cartoonId = cartoonId;
    }

    public Integer getChapterPrice() {
        return chapterPrice;
    }

    public void setChapterPrice(Integer chapterPrice) {
        this.chapterPrice = chapterPrice;
    }

    public String getChapterUri() {
        return chapterUri;
    }

    public void setChapterUri(String chapterUri) {
        this.chapterUri = chapterUri;
    }

    public Integer getChapterPageCount() {
        return chapterPageCount;
    }

    public void setChapterPageCount(Integer chapterPageCount) {
        this.chapterPageCount = chapterPageCount;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId=" + chapterId +
                ", sortNum=" + sortNum +
                ", chapterName='" + chapterName + '\'' +
                ", chapterUpdateTime=" + chapterUpdateTime +
                ", cartoonId=" + cartoonId +
                ", chapterPrice=" + chapterPrice +
                ", chapterUri='" + chapterUri + '\'' +
                ", chapterPageCount=" + chapterPageCount +
                '}';
    }
}
