package tech.mhuang.core.exception;

import tech.mhuang.core.util.ObjectUtil;

/**
 * 异常工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class ExceptionUtil {

    /**
     * 获得完整消息，包括异常名
     *
     * @param e 异常
     * @return 完整消息
     */
    public static String getMessage(Throwable e) {
        if (ObjectUtil.isEmpty(e)) {
            return null;
        }
        return String.format("%s:%s", e.getClass().getSimpleName(), e.getMessage());
    }
}
