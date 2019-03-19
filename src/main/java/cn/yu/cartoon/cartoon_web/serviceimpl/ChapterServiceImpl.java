package cn.yu.cartoon.cartoon_web.serviceimpl;

import cn.yu.cartoon.cartoon_web.config.CartoonFtpConfig;
import cn.yu.cartoon.cartoon_web.mapper.ChapterMapper;
import cn.yu.cartoon.cartoon_web.pojo.dto.Cartoon;
import cn.yu.cartoon.cartoon_web.pojo.dto.Chapter;
import cn.yu.cartoon.cartoon_web.redis.ChapterRedisDao;
import cn.yu.cartoon.cartoon_web.service.ChapterService;
import cn.yu.cartoon.cartoon_web.util.FtpUtil;
import cn.yu.cartoon.cartoon_web.util.RandomUtils;
import cn.yu.cartoon.cartoon_web.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/2/17 17:03
 **/
@Service("chapterService")
public class ChapterServiceImpl implements ChapterService {

    private static Logger logger = LoggerFactory.getLogger(ChapterServiceImpl.class);

    private final ChapterMapper chapterMapper;
    private final ChapterRedisDao chapterRedisDao;

    @Autowired
    public ChapterServiceImpl(ChapterMapper chapterMapper, ChapterRedisDao chapterRedisDao) {
        this.chapterMapper = chapterMapper;
        this.chapterRedisDao = chapterRedisDao;
    }

    @Override
    public boolean uriIsExit(String dirUri) {
        return null != chapterMapper.selectCountByUri(dirUri) && 0 != chapterMapper.selectCountByUri(dirUri);
    }

    @Override
    public Chapter storageChapter(Chapter chapter, String chapterFilePath) throws IOException {

        //计算章节页数
        File sourceFile = new File(chapterFilePath);
        chapter.setChapterPageCount(Objects.requireNonNull(sourceFile.listFiles()).length);
        //生成章节目录名
        String dirUri = RandomUtils.randomString(8);
        while (uriIsExit(dirUri)) {
            dirUri = RandomUtils.randomString(8);
        }
        chapter.setChapterUri(dirUri);

        //如果上传成功，则将相关的信息存入数据库
        if (FtpUtil.upload(CartoonFtpConfig.getHost(), CartoonFtpConfig.getPort(), CartoonFtpConfig.getUserName(), CartoonFtpConfig.getPassword(), chapterFilePath, CartoonFtpConfig.getBasePath(), dirUri, true)) {
            //插入mysql数据库
            chapterMapper.insert(chapter);
            //插入到redis数据库中
            chapter = chapterMapper.selectChapterByUri(chapter.getChapterUri());
            insertChapterIntoRedis(chapter);
            //删除临时文件夹
            if(sourceFile.delete()) {
                logger.debug(sourceFile.getName() + " is deleted!");
            }else {
                logger.debug("Delete operation is failed.");
            }
            return chapter;
        } else {
            return null;
        }
    }

    @Override
    public Chapter uploadChapterByZip(Chapter chapter, String zipFilePath, String decompressDirPath) throws IOException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-ddHHmmss HH MM SS");
        String tempDirName = sf.format(new Date());
        //解压zip文件
        if (ZipUtils.decompress(zipFilePath, decompressDirPath + File.separator + tempDirName)) {
            //删除临时的zip文件
            File zipFile = new File(zipFilePath);
            boolean isdelete = zipFile.delete();
            //存储到图片服务器
            return storageChapter(chapter, decompressDirPath + File.separator + tempDirName);
        } else {
            return null;
        }
    }

    @Override
    public void insertChapterIntoRedis(Chapter chapter) {
        chapterRedisDao.setChapterRecord(chapter);
    }

    @Override
    public Chapter getChapterById(int id) throws ParseException {
        Chapter chapter = null;
        //先从redis中取数据
        chapter = chapterRedisDao.getChapterRecordById(id);
        //如果redis中没有数据 则从mysql数据库中去取
        if (null == chapter) {
            chapter = chapterMapper.selectChapterById(id);
            //如果数据库中也没有该数据则往redis中存入值免得缓存穿透
            if (null == chapter) {
                chapter = new Chapter();
                chapter.setChapterId(id);
                chapterRedisDao.setChapterRecord(chapter);
                return null;
            } else {
                //如果数据库中有则将该数据存入redis中免得下次查询还要去数据库
                chapterRedisDao.setChapterRecord(chapter);
            }
        }
        return chapter;
    }

    @Override
    public List<Chapter> getChaptersByCartoonIdByPage(Integer cartoonId, Integer page, Integer size) {

        List<Chapter> chapterList = chapterRedisDao.selectChaptersByCartoonId(cartoonId, page, size);
        if (null != chapterList) {
            return chapterList;
        }

        return selectChaptersByCartoonIdByPageThenInsertIntoRedis(cartoonId, page, size);
    }

    @Override
    public Integer getChapterCountByCartoonId(Integer cartoonId) {
        return chapterMapper.selectChapterCountByCartoonId(cartoonId);
    }

    @Override
    public List<Chapter> getNewestChapterByCartoonId(Integer cartoonId, Integer size, Integer chapterCount, Integer quantity) {

        Integer pageCount = chapterCount / size + 1;
        if (chapterCount % size >= quantity) {
            List<Chapter> newChapterList = chapterRedisDao.selectNewestChapterByCartoonId(cartoonId, pageCount, size, quantity);
            if (null == newChapterList) {
                newChapterList = new ArrayList<>();
                selectNewChaptersWhenNotInRedis(cartoonId, pageCount, size, quantity, newChapterList);
            }
            return newChapterList;
        } else {
            List<Chapter> newChapterList = chapterRedisDao.selectNewestChapterByCartoonId(cartoonId, pageCount, size, chapterCount % size);
            if (null == newChapterList) {
                newChapterList = new ArrayList<>();
                selectNewChaptersWhenNotInRedis(cartoonId, pageCount, size, chapterCount % size, newChapterList);
            }

            List<Chapter> chapterList = chapterRedisDao.selectNewestChapterByCartoonId(cartoonId, pageCount - 1, size, quantity - chapterCount % size);
            if (null == chapterList) {
                selectNewChaptersWhenNotInRedis(cartoonId, pageCount - 1, size, quantity - chapterCount % size, newChapterList);
            } else {
                newChapterList.addAll(chapterList);
            }
            return newChapterList;

        }
    }

    /**
     * 根据漫画id和页数从数据库中找到相应的数据并存入redis缓存中
     *
     * @author Yu
     * @date 21:06 2019/3/19
     * @param cartoonId 漫画id
     * @param page 漫画页数
     * @param size 一页的数量
     * @return List<Chapter> 如果没有找到数据返回Null
     **/
    private List<Chapter> selectChaptersByCartoonIdByPageThenInsertIntoRedis(Integer cartoonId, Integer page, Integer size) {

        Map<String, Object> map = new HashMap<>(3);
        map.put("cartoonId", cartoonId);
        map.put("index", (page - 1) * size);
        map.put("size", size);
        List<Chapter> chapterList =  chapterMapper.selectChaptersByCartoonIdByPage(map);
        if (0 == chapterList.size()) {
            return null;
        }
        chapterRedisDao.insertChaptersByCartoonId(cartoonId, chapterList, page, size);
        return chapterList;
    }

    /**
     *  查询某章节分页最新的quantity个数据，并且这些数据不在redis中的情况
     *
     * @author Yu
     * @date 21:29 2019/3/19
     * @param
     * @return
     **/
    private void selectNewChaptersWhenNotInRedis(Integer cartoonId, Integer page, Integer size, Integer quantity, List<Chapter> newChapterList) {

        List<Chapter> chapterList = selectChaptersByCartoonIdByPageThenInsertIntoRedis(cartoonId, page, size);
        if (null != chapterList) {
            for (int i = 0; i < quantity; i ++ ) {
                newChapterList.add(chapterList.get(chapterList.size() - 1 - i));
            }
        }
    }

}
