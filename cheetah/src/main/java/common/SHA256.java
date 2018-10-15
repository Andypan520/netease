package common;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by pandechuan on 2018/08/08.
 */
public class SHA256 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = URLEncoder.encode("https://examples.javacodegeeks.com/core-java/net/urlencoder/java-net-urlencoder-example/", "UTF-8");
        //System.out.println(encrypt("123","123"));
        System.out.println(s);
    }

    private static String encrypt(String secret, String content) {
        String hash = null;
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            hash = Base64.encodeBase64String(sha256_HMAC.doFinal(content.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * ：Content：123456，Password：12345678；加密后的数据是：接下来的话，
     * 我们可以上在线加密网站看看我们刚才的是否一样？这里我推荐一个加密网站
     * ：http://tool.oschina.net/encrypt?type=2，具体自己上去试试。
     * 刚才加密后的内容也公布一下，看看大家加密后的是不是与我的一样；
     * AB6B3243E426F8352B45B4154711ACA13B7F3DC509E8FE65D6911E94ADCC365D，
     *
     * 3cafe40f92be6ac77d2792b4b267c2da11e3f3087b93bb19c6c5133786984b44
     *
     * ab6b3243e426f8352b45b4154711aca13b7f3dc509e8fe65d6911e94adcc365d
     */


}
