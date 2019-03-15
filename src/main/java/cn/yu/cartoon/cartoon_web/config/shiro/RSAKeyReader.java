package cn.yu.cartoon.cartoon_web.config.shiro;

import cn.yu.cartoon.cartoon_web.config.CartoonSystemConfig;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/14 13:34
 **/

public class RSAKeyReader {

    private static final RSAKeyReader keyReader = new RSAKeyReader();

    private static RSAPublicKey publicKey;
    private static RSAPrivateKey privateKey;

    private RSAKeyReader() {
        try {
            byte[] keyBytes = Files.readAllBytes(ResourceUtils.getFile(CartoonSystemConfig.getCartoonSystemConfig().getPrivateKeyFileName()).toPath());

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = (RSAPrivateKey) kf.generatePrivate(spec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        try {
            byte[] keyBytes = Files.readAllBytes(ResourceUtils.getFile(CartoonSystemConfig.getCartoonSystemConfig().getPublicKeyFileName()).toPath());

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


    }

    public static RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public static RSAPublicKey getPublicKey() {
        return publicKey;
    }

}
