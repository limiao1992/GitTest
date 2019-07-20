package cn.li.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:User
 * Package:cn.li.bean
 * Description:
 *
 * @date:2019/7/5 22:39
 * @author:cn.li
 */
@Data
public class User implements Serializable {
    private int id;
    private String name;
    private int age;
    private String remark;

    public static String getKey(){
        return "user:";
    }
}
