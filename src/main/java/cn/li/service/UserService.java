package cn.li.service;

import cn.li.bean.User;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserService
 * Package:cn.li.service
 * Description:
 *
 * @date:2019/7/6 21:28
 * @author:cn.li
 */
@Service
public interface UserService {
    /**
     * string 存和取测试
     * */
   String getString(String key);

}
