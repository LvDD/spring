package com.lvdong.spring.utils;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * Created by lvdong on 2016/10/8.
 */
@Component
public final class RedisUtil {
    private static final Logger logger = Logger.getLogger(RedisUtil.class);
    @Resource
    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 判读缓存中是否有对应的key
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除对应的value
     *
     * @param key 键
     */
    public void delete(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量删除对应的value
     *
     * @param keys 键
     */
    public void deleteBatch(final Set<String> keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern 匹配表达式 TODO 暂不知keys方法的作用
     */
    public void deletePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean set(final String key, Object value) {
        return set(key, value, null);
    }

    /**
     * 写入缓存
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间（秒）
     * @return 是否成功
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            if (expireTime != null) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
