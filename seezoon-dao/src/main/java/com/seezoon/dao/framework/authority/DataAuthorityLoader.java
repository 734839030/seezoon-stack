package com.seezoon.dao.framework.authority;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DataAuthorityLoader {

    private static final DataAuthority instance = load();

    private DataAuthorityLoader() {}

    private static DataAuthority load() {
        ServiceLoader<DataAuthority> serviceLoader = ServiceLoader.load(DataAuthority.class);
        Iterator<DataAuthority> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public static DataAuthority getInstance() {
        return instance;
    }
}
