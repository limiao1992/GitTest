package cn.li.service.impl;

import cn.li.bean.User;
import cn.li.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName:UserServiceImpl
 * Package:cn.li.service.impl
 * Description:
 *
 * @date:2019/7/6 21:30
 * @author:cn.li
 */

public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * Redis String类型测试
     * 通过某个key得到值
     * 如果key在Redis中不存在  到数据库中进行查询
     * @param key
     * @return
     */
    @Override
    public String getString(String key) {
        ValueOperations<String,String> string=redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)){
            //在Redis中取
            System.out.println("在redis中取出并返回");
            return string.get(key);
        }else {
            //查询数据库
            String result = "RedisTemplate模板练习";
            string.set(key, result);
            System.out.println("在MYSQL数据库中取出并返回");
            return result;
        }
    }
}
