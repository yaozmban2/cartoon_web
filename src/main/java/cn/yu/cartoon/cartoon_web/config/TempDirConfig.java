package cn.yu.cartoon.cartoon_web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 存放一些临时文件路径的类
 *
 * @author Yu
 * @version 1.0
 * @date 2019/2/18 11:49
 **/
public class TempDirConfig {

    private static Logger logger = LoggerFactory.getLogger(TempDirConfig.class);

    private static String tempZipDirPath;

    private static String tempDecompressDirPath;

    static {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        if(!path.exists()){
            path = new File("");
        }
        String relativelyPath = path.getAbsolutePath();
        tempZipDirPath = relativelyPath + File.separator + "temZipDir";
        tempDecompressDirPath = relativelyPath + "temDepressDir";
    }

    public static String getTempZipDirPath() {
        return tempZipDirPath;
    }

    public static String getTempDecompressDirPath() {
        return tempDecompressDirPath;
    }
}
