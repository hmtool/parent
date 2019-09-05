package tech.mhuang.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class CryptoUtil {

    private CryptoUtil() {

    }

    /**
     * md5或者sha-1加密.
     *
     * @param inputText     要加密的内容
     * @param algorithmName 加密算法名称：md5或者sha-1以及其他，不区分大小写
     * @return 返回对应的数据
     * @throws UnsupportedEncodingException 无法支持的编码类型
     * @throws NoSuchAlgorithmException     不支持的协议
     * @throws IllegalArgumentException     不支持的参数
     */
    public static String encrypt(String inputText, String algorithmName) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            throw new IllegalArgumentException("请输入加密算法的名称");
        }
        MessageDigest m = MessageDigest.getInstance(algorithmName);
        m.update(inputText.getBytes("UTF8"));
        return hex(m.digest());
    }

    /**
     * 返回十六进制字符串
     *
     * @param arr 需要转16进制的byte数组
     * @return 16进制字符串
     */
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (byte b : arr) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
}
