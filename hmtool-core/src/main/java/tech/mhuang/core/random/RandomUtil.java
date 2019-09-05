package tech.mhuang.core.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class RandomUtil {

    /**
     * 根据最小最大范围值获取随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 返回随机数
     */
    public static int getInRange(int min, int max) {
        return ThreadLocalRandom.current().ints(min, max).findFirst().getAsInt();
    }

    /**
     * 获取随机值
     *
     * @return 随机数
     */
    public static int nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }
}
