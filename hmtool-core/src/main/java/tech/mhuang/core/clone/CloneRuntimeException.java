package tech.mhuang.core.clone;

import tech.mhuang.core.exception.ExceptionUtil;

/**
 * 克隆异常类
 *
 * @author mhuang
 * @since 1.0.0
 */

public class CloneRuntimeException extends RuntimeException {

    public CloneRuntimeException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }
}
