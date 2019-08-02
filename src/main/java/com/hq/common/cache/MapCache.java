package com.hq.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huang on 19/3/2019.
 */
public class MapCache
{
    /**
     * 默认缓存1024个
     */
    private static final int DEFAULT_CACHES = 1024;

    private static final MapCache instance = new MapCache();
    /**
     * 缓存容器
     */
    private Map<String, CacheObject> cachePool;

    public static MapCache single(){
        return instance;
    }

    public MapCache()
    {
       this(DEFAULT_CACHES);
    }
    public MapCache(int count){
        cachePool = new ConcurrentHashMap<>(count);
    }

    /**
     * 读取一个缓存
     */
    public  <T> T get(String key){
        CacheObject cacheObject = cachePool.get(key);
        if (null != cacheObject){
            long cur = System.currentTimeMillis() / 1000;
            if (cacheObject.getExpired() <= 0  || cacheObject.getExpired() > cur){
                //处于有效时间内
                Object result = cacheObject.getValue();
                return (T) result;
            }
        }
        return null;
    }

    /**
     * 读取一个hash类型缓存
     */
    public <T> T hget(String key,String field){
        key = key + ":" + field;
        return this.get(key);
    }

    /**
     * 设置一个缓存
     */
    public void set(String key,Object value){
        //不过期的缓存
        this.set(key,value,-1);
    }

    /**
     * 设置一个hash缓存
     */
    public void hset(String key,String field,Object value){
        this.hset(key,field,value,-1);
    }

    /**
     * 设置一个hash缓存并带过期时间
     */
    public void hset(String key,String field,Object value,long expired){
        key = key + ":" + field;
        expired = expired > 0 ? System.currentTimeMillis()/1000 + expired :expired;
        CacheObject cacheObject = new CacheObject(key, value, expired);
        cachePool.put(key, cacheObject);
    }

    /**
     * 设置一个带过期时间的缓存
     */
    public void set(String key, Object value, long expired){
        expired = expired > 0 ? System.currentTimeMillis() /1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key,value,expired);
        cachePool.put(key, cacheObject);
    }

    /**
     * 根据key删除缓存
     */
    public void del(String key){
        cachePool.remove(key);
    }
    /**
     * 根据key和field删除缓存
     */
    public void del(String key,String field){
        key = key + ":" +field;
        cachePool.remove(key);
    }

    public boolean isEmpty(){
        return cachePool.isEmpty();
    }
    /**
     * 清空缓存
     */
    public void clean(){
        cachePool.clear();
    }

    static class CacheObject{
        private String key;
        private Object value;
        private long expired;

        public CacheObject(String key, Object value, long expired)
        {
            this.key = key;
            this.value = value;
            this.expired = expired;
        }

        public String getKey()
        {
            return key;
        }

        public Object getValue()
        {
            return value;
        }

        public long getExpired()
        {
            return expired;
        }
    }
}
