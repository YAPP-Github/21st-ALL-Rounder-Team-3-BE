package yapp.allround3.common.security;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import yapp.allround3.common.exception.CustomException;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

@RequiredArgsConstructor
public class SecurityUtils {
    public static String encodeKey(Long id){

        try {
            byte[] textBytes = id.toString().getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(Secret.secretKey.getBytes());
            SecretKeySpec newKey = new SecretKeySpec(Secret.secretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
                    cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return Base64Utils.encodeToString(cipher.doFinal(textBytes));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Long decodeKey(String encodedKey){
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(Secret.secretKey.getBytes());
            SecretKeySpec newKey = new SecretKeySpec(Secret.secretKey.getBytes("UTF-8"), "AES");
            byte[] textBytes = Base64.decodeBase64(encodedKey);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return Long.valueOf(new String(cipher.doFinal(textBytes), "UTF-8"));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
