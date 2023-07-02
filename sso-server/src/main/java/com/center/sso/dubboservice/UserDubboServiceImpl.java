//package com.center.sso.dubboservice;
//
//import com.alibaba.fastjson.JSONObject;
//import com.center.sso.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//@DubboService(version = "1.0.0", group = "com/center/sso")
//public class UserDubboServiceImpl implements UserDubboService {
//
//    @Autowired
//    UserService userService;
//
//    @Override
//    public JSONObject getUserInfoById(List<Long> userIds) {
//        JSONObject result=new JSONObject();
//        if(null==userIds ||0==userIds.size()){
//            return result;
//        }
//        List<SysUser> usersInBatch = userService.getUsersInBatch(userIds);
//        if(null==usersInBatch || 0==usersInBatch.size()){
//            return result;
//        }
//        for(SysUser sysUser:usersInBatch){
//            JSONObject userJSONObject=new JSONObject();
//            userJSONObject.put("name",sysUser.getName());
//            userJSONObject.put("email",sysUser.getEmail());
//            userJSONObject.put("phone",sysUser.getPhone());
//            result.put(sysUser.getId().toString(),userJSONObject);
//        }
//        return result;
//    }
//}
