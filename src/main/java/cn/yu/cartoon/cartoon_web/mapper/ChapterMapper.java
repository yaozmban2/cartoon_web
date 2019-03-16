package cn.yu.cartoon.cartoon_web.mapper;

import cn.yu.cartoon.cartoon_web.pojo.dto.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/17 16:55
 **/
@Mapper
public interface ChapterMapper {

    /**
     *  根据章节目录地址来查看记录总数
     *
     * @author Yu
     * @date 17:01 2019/2/17
     * @param chapterUri 章节目录地址
     * @return Integer返回记录条数
     **/
    Integer selectCountByUri(String chapterUri);

    /**
     *  将漫画章节信息存入数据库
     *
     * @author Yu
     * @date 19:18 2019/2/17
     * @param chapter 章节对象
     **/
    void insert(Chapter chapter);

    /**
     *  根据章节id查找到章节数据
     *
     * @author Yu
     * @date 11:16 2019/2/19
     * @param chapterId 章节id
     * @return Chapter 章节数据
     **/
    Chapter selectChapterById(Integer chapterId);

    /**
     *  根据uri的值查找到章节数据
     *
     * @author Yu
     * @date 17:02 2019/2/19
     * @param chapterUri 章节地址的文件夹名称
     * @return Chapter
     **/
    Chapter selectChapterByUri(String chapterUri);

    /**
     *  查找出漫画的所有章节信息
     *
     * @author Yu
     * @date 17:39 2019/3/16
     * @param cartoonId 漫画ID
     * @return 章节信息的列表
     **/
    List<Chapter> selectChaptersByCartoonId(Integer cartoonId);

    /**
     *  根据漫画id分组 根据sort_num升序 分页 查询
     *
     * @author Yu
     * @date 20:25 2019/3/16
     * @param data 三个entry: "cartoonId", "index" = (page - 1) * size, "size"  =  每页大小
     * @return
     **/
    List<Chapter> selectChaptersByCartoonIdByPage(Map<String, Object> data);

    /**
     *  根据漫画id查询漫画的章节总数
     *
     * @author Yu
     * @date 21:08 2019/3/16
     * @param cartoonId 漫画Id
     **/
    Integer selectChapterCountByCartoonId(Integer cartoonId);
}
