package cn.li.maven;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ClassName:RedisPoolUtil
 * Package:cn.li.maven
 * Description:
 *
 * @date:2019/7/5 21:57
 * @author:cn.li
 */
public class RedisPoolUtil {
    /*
    * Redis工具类
    *
    * */
    private static final JedisPool pool;
    static{
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);//最大连接数
        poolConfig.setMaxIdle(1);//最大空闲数
        //...
        //2.连接池
        String host = "192.168.117.20";
        int port = 6379;
        pool = new JedisPool(poolConfig, host, port);

    }
    public static Jedis getJedis(){
        Jedis jedis=pool.getResource();
        jedis.auth("lm");
        return jedis;
    }
    //关闭连接功能
    public static void  close(Jedis jedis){
        jedis.close();
    }
}
