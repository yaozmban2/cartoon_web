package cn.yu.cartoon.cartoon_web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取cartoonFtp.properties文件中的配置
 *
 * @author Yu
 * @version 1.0
 * @date 2019/2/17 17:37
 **/
public class CartoonFtpConfig {

    private static Logger logger = LoggerFactory.getLogger(CartoonFtpConfig.class);

    private static String host;

    private static Integer port;

    private static String userName;

    private static String password;

    private static String basePath;

    /**
     * 漫画服务器的基础地址
     */
    private static String cartoonUrl;

    private static String cartoonFrontCoverImaName = "frontCover.png";

    static {
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(ResourceUtils.getFile("classpath:application.properties")));
            Properties p = new Properties();
            p.load(inputStream);
            host = p.getProperty("FTP.ADDRESS");
            port = new Integer(p.getProperty("FTP.PORT"));
            userName = p.getProperty("FTP.USERNAME");
            password = p.getProperty("FTP.PASSWORD");
            basePath = p.getProperty("FTP.BASEPATH");
            cartoonUrl = p.getProperty("IMAGE.BASE.URL");
        } catch (IOException e) {
            logger.error("读取配置文件失败", e);
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static String getHost() {
        return host;
    }

    public static Integer getPort() {
        return port;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getBasePath() {
        return basePath;
    }

    public static String getCartoonUrl() {
        return cartoonUrl;
    }

    public static String getCartoonFrontCoverImaName() {
        return cartoonFrontCoverImaName;
    }
}
