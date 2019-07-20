package cn.li.maven;

import cn.li.bean.User;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:RedisDemo
 * Package:cn.li.maven
 * Description:
 *
 * @date:2019/7/5 18:52
 * @author:cn.li
 */
public class RedisDemo {
    /**
     * java端通过jedis操作redis服务器
     *
     * @param args
     */
    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);//最大连接数
        poolConfig.setMaxIdle(1);//最大空闲数
        //...
        //2.连接池
        String host = "192.168.117.20";
        int port = 6379;
        JedisPool pool = new JedisPool(poolConfig, host, port);

        Jedis jedis = pool.getResource();
        jedis.auth("lm");
        System.out.println(jedis.ping());


    }

    /*
    测试字符串String
     */
    @Test
    public void t1() {
        //1连接池RedisPOOl 基本配置信息

        Jedis jedis = RedisPoolUtil.getJedis();
//        jedis.set("strName", "字符串的名称");
//        String strName = jedis.get("strName");
//        System.out.println("Redis中的数据 " + strName);
        System.out.println(jedis.get("name"));
        RedisPoolUtil.close(jedis);
    }

    /*
     * Redis作用：为了减轻数据库的访问压力
     * 需求：判断某key是否存在，如果存在就从redis中查询；
     * 如果不存在就查询数据库，且要将查询出的数据存入redis
     *
     * */
    @Test
    public void t2() {
//        Jedis jedis = new Jedis("192.168.117.20", 6379);
//        jedis.auth("lm");
        Jedis jedis = RedisPoolUtil.getJedis();
        String key = "applicationName";
        if (jedis.exists(key)) {
            String result = jedis.get(key);
            System.out.println("redis数据库中查询得到" + result);
        } else {
            //从数据库中查询
            String result = "应用名称";
            jedis.set(key, result);
            System.out.println("mysql数据库中查询得到" + result);
        }
        RedisPoolUtil.close(jedis);
    }

    /**
     * Jedis完成对hash操作
     * 需求：hash存储一个对象
     * 判断redis中是否存在该key 如果存在直接返回对应值
     * 不存在 查询数据库 将查询的结果存入redis
     */
    @Test
    public void t3() {
        Jedis jedis = RedisPoolUtil.getJedis();
        String key = "users";
        if (jedis.exists(key)) {
            Map<String, String> map = jedis.hgetAll(key);
            System.out.println("-Redis中查询的结果是：");
            System.out.println(map.get("id") + "\t" + map.get("name") + "\t" + map.get("age") + "\t" + map.get("remark"));
        } else {
            //查询数据库并返回结果
            String id = "1";
            String name = "张三";
            jedis.hset(key, "id", id);
            jedis.hset(key, "name", name);
            jedis.hset(key, "age", "18");
            jedis.hset(key, "remark", "这是一位男同学");
            System.out.println("数据库中查询结果是：" + id + name);
        }
        RedisPoolUtil.close(jedis);
    }
    /*
    *对上面的方法进行优化
     */
    @Test
    public void t4(){
        Jedis jedis = RedisPoolUtil.getJedis();
        int id=5;
        String key=User.getKey()+id;//user:1
        if(jedis.exists(key)){
            //redis中取出该对象
            Map<String, String> map=jedis.hgetAll(key);
            User user=new User();
            user.setId(Integer.parseInt(map.get("id")));
            user.setName(map.get("name"));
            user.setAge(Integer.parseInt(map.get("age")));
            user.setRemark(map.get("remark"));
            System.out.println("Redis中查询对象："+user);
        }else {
            //mysql数据库查询
            User user=new User();
            user.setId(id);
            user.setName("lisi");
            user.setAge(18);
            user.setRemark("这是一个女生");
            Map<String,String> map=new HashMap<>();
            map.put("id",String.valueOf(user.getId()));
            map.put("name",user.getName());
            map.put("age",user.getAge()+"");
            map.put("remark",user.getRemark());
            jedis.hmset(key,map);
            System.out.println("mysql中查询结果是："+user);
        }

        RedisPoolUtil.close(jedis);
    }
}
