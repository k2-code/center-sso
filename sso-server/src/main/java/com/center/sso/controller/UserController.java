package com.center.sso.controller;

import com.alibaba.fastjson.JSONObject;
import com.center.sso.model.po.SysUser;
import com.center.sso.service.UserService;
import com.philisense.utils.PageParam;
import com.philisense.utils.PageResponse;
import com.philisense.utils.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/v1/user/add")
    public ResultResponse saveUser(@RequestBody JSONObject jsonObject){
        SysUser sysUser = JSONObject.parseObject(jsonObject.toJSONString(), SysUser.class);
        Boolean aBoolean = userService.checkUserName(sysUser.getUsername());
        if (aBoolean){
            return new ResultResponse(-1,"用户名重复");
        }
        userService.save(sysUser);
        return new ResultResponse();
    }

    @PostMapping("/v1/user/list")
    public ResultResponse userPageQuery(@RequestBody JSONObject jsonObject){
        SysUser sysUser = JSONObject.parseObject(jsonObject.toJSONString(), SysUser.class);
        PageParam pageParam = JSONObject.parseObject(jsonObject.toJSONString(), PageParam.class);
        PageResponse<SysUser> sysUserPageResponse = userService.userPageQuery(sysUser, pageParam);
        return new ResultResponse(sysUserPageResponse);
    }

    /**
     * 禁用用户
     * @param jsonObject
     * @return
     */
    @DeleteMapping("/v1/user")
    public ResultResponse disabledUser(@RequestBody JSONObject jsonObject) {
        Object selectedIDs = jsonObject.get("selectedIDs");
        if (null == selectedIDs){
            return new ResultResponse(-1,"请选择要禁用的用户");
        }
        int i = userService.disabledUsers((List<Long>) selectedIDs);
        if (i != 0){
            return new ResultResponse();
        }else {
            return new ResultResponse(-1,"禁用失败");
        }
    }

    @GetMapping("/v1/user/{userId}/detail")
    public ResultResponse getUserDetail(@PathVariable Long userId){
        SysUser userDetail = userService.getUserDetail(userId);
        if (null != userDetail) {
            return new ResultResponse(userDetail);
        }
        return new ResultResponse(-1,"用户信息获取失败");
    }


//    @Autowired
//    private ClientDetailsService clientDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    /**
//     * String clientId, String resourceIds,String scopes, String grantTypes, String authorities,String clientSecret
//     * @return
//     */
//    @PostMapping("/client/add")
//    public ResultResponse setClient(@RequestBody JSONObject jsonObject){
//
//        String clientId = (String) jsonObject.get("clientId");
//        String scopes = (String) jsonObject.get("scopes");
//        String grantTypes = "authorization_code,client_credentials,implicit,refresh_token,password,client_pwd";
//        String clientSecret = (String) jsonObject.get("clientSecret");
//
//        String clientSecretEncode = passwordEncoder.encode(clientSecret);
//
//        JdbcClientDetailsService clientDetailsService = (JdbcClientDetailsService) this.clientDetailsService;
//        BaseClientDetails baseClientDetails = new BaseClientDetails(clientId, null, scopes, grantTypes, null);
//        baseClientDetails.setClientSecret(clientSecretEncode);
//        clientDetailsService.addClientDetails(baseClientDetails);
//        return new ResultResponse();
//    }
}
