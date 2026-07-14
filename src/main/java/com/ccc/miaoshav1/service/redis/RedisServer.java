package com.ccc.miaoshav1.service.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;



/**
 * 封装redis基础方法的意义在于——统一管理、调用方不用管redis的连接和关闭（由封装服务实现）
 */
@Service
public class RedisServer {
    @Autowired
    private JedisPool jedisPool;


    // 获取redis连接-因为jedis实现了java.lang.AutoCloseable，所以无需手动关闭连接
    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    // ================= 基础crud方法 =======================================

    // 通过缓存key获取缓存信息
    public String get(String key){
        try(Jedis jedis = getJedis()){
            return jedis.get(key);
        }
    }

    // 设置缓存，不指定过期时间-永久缓存
    public void set(String key,String value){
        try(Jedis jedis = getJedis()){
            jedis.set(key, value);
        }
    }

    // 设置缓存，指定过期时间-定时缓存- 可用于设置首次登录失败记录窗口期（5mins内连续输错5次，账号进行锁定半小时）
    public void setex(String key,String value,int seconds){
        try(Jedis jedis = getJedis()){
            SetParams setParams = SetParams.setParams().ex(seconds);
            jedis.set(key,value,setParams);
        }
    }

    // 通过缓存key删除缓存
    public void delete(String key){
        try(Jedis jedis = getJedis()){
            jedis.del(key);
        }
    }

    // 判断缓存是否存在
    public boolean exists(String key){
        try(Jedis jedis = getJedis()){
            return jedis.exists(key);
        }
    }

    // ================= 登录限量相关方法 =======================================

    // 缓存key的原子值+1，用于统计用户登录失败次数
    public long increment(String key){
        try(Jedis jedis = getJedis()){
            return jedis.incr(key);
        }
    }


    // 查询某个缓存的剩余过期时间-可用于展示登录失败后，账号的锁定剩余时长
    public long ttl(String key){
        try(Jedis jedis = getJedis()){
            // return -2 key不存在、-1 无剩余时间、>0 剩余时长
            return jedis.ttl(key);
        }
    }



}
