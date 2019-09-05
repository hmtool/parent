package tech.mhuang.core.util;

import java.net.URL;

/**
 * Jar路径管理工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class PathUtil {

    private static final String SUFFIX_JAR = ".jar";
    private static final String CLASSES_PASH = "/classes/";

    private PathUtil() {

    }

    /**
     * 根据class获取里面的jar包路径、目前基于存放路径classes
     *
     * @param clazz   class类
     * @param jarName jar包路径
     * @return 返回对应的完整路径
     */
    public static String getJarPash(Class<?> clazz, String jarName) {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = url.getFile().replaceFirst("/", "");
        if (!jarPath.endsWith(SUFFIX_JAR)) {
            jarPath = jarPath.replaceFirst(CLASSES_PASH, "") + "/" + jarName + SUFFIX_JAR;
        }
        return jarPath;
    }
}
