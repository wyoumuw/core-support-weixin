package com.youmu.maven.weixin.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public abstract class CollectionUtils {
    public static boolean isEmpty(Collection collection){
        return null==collection||collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return null==map||map.isEmpty();
    }
}
