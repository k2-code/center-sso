package com.center.sso;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.center.sso.controller.UserController;
import com.center.sso.model.po.SysUser;
import com.center.sso.phili.utils.ResultResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CenterSSOApplication.class)
public class UserTest {

    @Autowired
    private UserController userController;

    @Test
    public void  testAddUser(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("admin2");
        sysUser.setPassword("Igy4i4N9S9zUwCVH2DAHobcr2g+2ZLP+6UWVqpixRAy2Udusfs61uT4qogi2lg3D0dfdg+915EtwF9w0Q0XxYw==");
        sysUser.setEnabled(true);
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(sysUser));

         userController.saveUser(jsonObject);


    }

}
