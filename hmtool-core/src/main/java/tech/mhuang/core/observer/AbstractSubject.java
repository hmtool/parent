package tech.mhuang.core.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 抽象主题类
 *
 * @author mhuang
 * @since 1.0.0
 */
public abstract class AbstractSubject<T> implements BaseSubject<T> {

    private List<BaseObServer<T>> obServerList = new CopyOnWriteArrayList();

    public List<BaseObServer<T>> getObServerList() {
        return obServerList;
    }

    @Override
    public void add(BaseObServer<T> obServer) {
        obServerList.add(obServer);
    }

    @Override
    public void delete(BaseObServer<T> obServer) {
        obServerList.remove(obServer);
    }

    @Override
    public void clear() {
        obServerList.clear();
    }
}
