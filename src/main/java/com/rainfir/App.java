package com.rainfir;

import com.rainfir.dao.UserDOMapper;
import com.rainfir.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.rainfir"})
@MapperScan("com.rainfir.dao")
@RestController
public class App
{
    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(2);
        if (userDO==null){
            return "用户不存在";
        }else{
            return userDO.getName();
        }
        //return "Hello World!!";

    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
