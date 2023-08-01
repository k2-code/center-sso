package com.center.sso.config.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class BaseRedis<T> {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    //private static RedisTemplate<String,Object> redisTemplate = SpringUtil.getBean("redisTemplate",RedisTemplate.class);

    public boolean hasKey(String key){
        if (redisTemplate.hasKey(key)){
            return true;
        }
        return false;
    }

    /**
     * 存入普通对象并设置缓存时间
     * @param key
     * @param t
     * @param time
     */
    public void add(String key,T t,Long time){
        addInRedis(key,t,time);
    }

    /**
     * 存入普通对象
     * @param key
     * @param t
     */
    public void add(String key,T t){
        addInRedis(key,t);
    }

    /**
     * 获取普通对象
     * @param key
     * @return
     */
    public T get(String key){
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            return null;
        }
        T t = (T) redisTemplate.opsForValue().get(key);
        return t;
    }

    public List<T> multiGet(Collection<String> key){
        List<T> objects = (List<T>) redisTemplate.opsForValue().multiGet(key);
        return objects;
    }


    private void addInRedis(String key,Object object,Long time){
        redisTemplate.opsForValue().set(key, object, time, TimeUnit.MINUTES);
    }

    private void addInRedis(String key,Object object){
        redisTemplate.opsForValue().set(key,object);
    }

    /**
     * 往set中存入数据
     * @param key
     * @param values
     * @return
     */
    public long sSet(String key,T... values){
        return addSetInRedis(key, (Object) values);
    }

    private long addSetInRedis(String key,Object... values){
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 :count;
    }

    /**
     * 删除set中的数据
     * @param key
     * @param values
     * @return
     */
    public long sDel(String key,T... values){
        return delSet(key, (Object) values);
    }

    private long delSet(String key,Object... values){
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往list中存入数据
     * @param key
     * @param value
     * @return
     */
    public long listPush(String key ,T value){
       return lPush(key,value);
    }

    private long lPush(String key,Object value){
        Long count = redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 往list中存入多个数据
     * @param key
     * @param values
     * @return
     */
    public long listPushAll(String key, Collection<T> values){
        T[] objects = (T[]) values.toArray();
        Long count = redisTemplate.opsForList().rightPushAll(key, objects);
        return count == null ? 0 : count;
    }

    public long listPushAll(String key, Collection<T> values,Long expireTime){
        T[] objects = (T[]) values.toArray();
        Long count = redisTemplate.opsForList().rightPushAll(key, objects);
        redisTemplate.expire(key,expireTime,TimeUnit.SECONDS);
        return count == null ? 0 : count;
    }

    public long listAddAll(String key,Collection<T> values){
        long count = 0;
        for (T t: values) {
            Long aLong = redisTemplate.opsForList().rightPush(key, t);
            count += aLong;
        }
       // Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count;
    }

    /**
     * 获取list中的所有数据
     * @param key
     * @return
     */
    public List<T> getListAll(String key){
        return  (List<T>) redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 获取list的大小
     * @param key
     * @return
     */
    public Long getListSize(String key){
        Long size = redisTemplate.opsForList().size(key);
        return size == null ? 0 : size;
    }

    public void delList(String key){
        redisTemplate.delete(key);
    }


    public List<T> getListRange(String key,int pageSize,int pageIndex){
        Long listSize = getListSize(key);
        if (0 == pageIndex && 0 == pageIndex){
            return getListAll(key);
        }
        if (listSize >= pageSize * pageIndex) {
            return  (List<T>) redisTemplate.opsForList().range(key, pageSize * (pageIndex - 1), pageSize * pageIndex-1);
        }else {
            return (List<T>) redisTemplate.opsForList().range(key, pageSize * (pageIndex - 1), -1);
        }
    }

    public List<String> getKeyInRedis(String key){
        Set<String> keys = redisTemplate.keys(key);
        if (CollectionUtils.isEmpty(keys)){
            return null;
        }
        return new ArrayList<>(keys);
    }

    public void delByKey(List<String> keys){
        if (CollectionUtils.isEmpty(keys)){
            return;
        }
        redisTemplate.delete(keys);
    }

    public void delByKey(String key){
        if (StringUtils.isBlank(key)){
            return;
        }
        redisTemplate.delete(key);
    }

}
