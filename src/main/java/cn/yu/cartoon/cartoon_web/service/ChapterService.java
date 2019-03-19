package cn.yu.cartoon.cartoon_web.service;

import cn.yu.cartoon.cartoon_web.pojo.dto.Chapter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/17 17:03
 **/
public interface ChapterService {

    /**
     *  判断目录地址的名称是否已经存在
     *
     * @author Yu
     * @date 17:12 2019/2/17
     * @param dirUri 目录地址
     * @return boolean 存在目录地址则返回true，没有返回false
     **/
    boolean uriIsExit(String dirUri);

    /** 
     *  将章节漫画文件夹存到远程服务器中，并将章节数据存到数据库中
     *
     * @author Yu
     * @date 17:28 2019/2/17
     * @param chapter 章节数据信息 除了chapterUri属性都要填入
     * @param chapterFilePath 漫画章节文件夹地址
     * @return Chapter 存入服务器的章节信息
     * @throws IOException 输入输出错误
     **/
    Chapter storageChapter(Chapter chapter, String chapterFilePath) throws IOException;

    /**
     * 将zip压缩的漫画章节解压并上传到ftp图片服务器中
     *
     * @author Yu
     * @date 22:00 2019/2/17
     * @param chapter 章节信息
     * @param zipFilePath 漫画章节的zip文件地址
     * @param decompressDirPath 存放解压文件的临时地址
     * @return Chapter 存入服务器的章节信息
     * @throws IOException IO异常
     **/
    Chapter uploadChapterByZip(Chapter chapter, String zipFilePath, String decompressDirPath) throws IOException;

    /**
     *  将漫画章节数据存入redis中
     *
     * @author Yu
     * @date 15:22 2019/2/19
     * @param chapter 章节数据对象
     **/
    void insertChapterIntoRedis(Chapter chapter);

    /**
     * 根据章节id获得章节数据
     *
     * @author Yu
     * @date 16:48 2019/2/19
     * @param id 章节id
     * @return Chapter 返回的章节数据，如果数据库中不存在则返回null
     * @throws ParseException 时间字符串转换为date时出错
     **/
    Chapter getChapterById(int id) throws ParseException;

    /**
     *  根据漫画ID获得所有章节信息
     *      1.查询redis中是否有该分页数据
     *      2.若没有则去mysql中查找
     *      3.将查到的数据放入redis缓存并当作结果返回
     *
     * @author Yu
     * @date 19:39 2019/3/16
     * @param cartoonId 漫画ID
     * @param page 第几页
     * @param size 分页大小
     * @return 如果没有数据返回null
     **/
    List<Chapter> getChaptersByCartoonIdByPage(Integer cartoonId, Integer page, Integer size);

    /**
     *  根据漫画id查询漫画的章节总数
     *
     * @author Yu
     * @date 21:05 2019/3/16
     * @param cartoonId 漫画id
     **/
    Integer getChapterCountByCartoonId(Integer cartoonId);

    /**
     * 根据漫画ID 查询出最新更新的quantity个数据<br/>
     *      1. 计算出漫画一共会有多少页  count/size + 1<br/>
     *      2. 判断最后一页能否找到quantity个章节 count % size >= quantity<br/>
     *      3. 若是能在最后一页找到quantity个 则去redis中最后一页取<br/>
     *      4.      若redis缓存中没有最后一页的数据，则去数据库中查找到并存入缓存<br/>
     *      5. 若是不能在最后一页找到quantity个 则分别在最后一页和倒数第二页找到总数为quantity个章节<br/>
     *
     * @author Yu
     * @date 20:24 2019/3/19
     * @param quantity 要查询的章节数量
     * @param size 一页的数量
     * @param chapterCount 章节总数
     * @param cartoonId 漫画Id
     * @return List<Chapter>
     **/
    List<Chapter> getNewestChapterByCartoonId(Integer cartoonId, Integer size, Integer chapterCount, Integer quantity );
}
