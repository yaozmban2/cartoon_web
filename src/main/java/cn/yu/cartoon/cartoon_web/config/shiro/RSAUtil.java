/*
package cn.yu.cartoon.cartoon_web.config.shiro;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/14 11:29
 **//*


public class RSAUtil {

    //非对称密钥算法
    public static final String KEY_ALGORITHM="RSA";

*/
/**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     * *//*


    private static final int KEY_SIZE=1024;

*/
/**
     * 公钥
     *//*


    public static final String PUBLIC_KEY="RSAPublicKey";

*/
/**
     * 私钥
     *//*


    public static final String PRIVATE_KEY="RSAPrivateKey";

*/
/**
     * 初始化密钥对
     * @return Map 甲方密钥的Map
     * *//*


    public static Map<String,Object> initKey() throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //甲方私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

}
*/
