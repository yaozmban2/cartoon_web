package cn.yu.cartoon.cartoon_web.service;

import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 漫画服务层
 *
 * @author Yu
 * @version 1.0
 * @date 2019/2/14 20:55
 **/
public interface CartoonService {

    /**
     *  将漫画信息添加到数据库
     *      1.在图片服务器创建一个存放漫画的文件夹
     *      2.将封面改名并上传
     *      3.设置文件夹uri 更新时间 是否删除等信息 并存入数据库
     *      4.将cartoonId和cartoon_typeId 存入漫画-类型关系库
     *
     * @author Yu
     * @date 21:58 2019/3/15
     * @param cartoon 漫画信息
     * @param types 漫画类型
     * @param multipartFile 封面图片文件
     **/
    void createCartoon(Cartoon cartoon, Integer[] types, MultipartFile multipartFile) throws IOException;

    /**
     *  在图片服务器上创建一个文件夹并上传封面图片
     *
     * @author Yu
     * @date 22:33 2019/3/15
     * @param cartoon 漫画信息
     * @param multipartFile 封面图片文件
     **/
    void uploadFrontCoverToImgServer(Cartoon cartoon, MultipartFile multipartFile) throws IOException;
}
