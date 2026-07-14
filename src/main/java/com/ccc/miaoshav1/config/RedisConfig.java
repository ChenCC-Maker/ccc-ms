package com.ccc.miaoshav1.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.database:0}")
    private int database;
    @Bean
    public JedisPool jedisPool(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100); // redis最大连接数
        jedisPoolConfig.setMaxIdle(50); // redis最大空闲连接数
        jedisPoolConfig.setMinIdle(10); // redis最小空闲连接数

        return new JedisPool(jedisPoolConfig,host,port,3000,null,database);
    }
}
