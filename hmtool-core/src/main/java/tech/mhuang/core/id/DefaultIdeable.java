package tech.mhuang.core.id;

import java.util.UUID;

/**
 * id生成工具类
 *
 * @author mhuang
 * @since 默认提供UUID
 */
public class DefaultIdeable implements BaseIdeable<String> {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
