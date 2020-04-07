package cn.chay.controller;

import cn.chay.entity.UserInfo;
import cn.chay.service.UserInfoService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Chay
 * @date 2020/4/7 10:56
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("add user")
    @PostMapping
    public String add(@RequestBody UserInfo userInfo) {
        userInfoService.insert(userInfo);
        return JSON.toJSONString(userInfo);
    }

    @ApiOperation("update user")
    @PutMapping
    public String update(@RequestBody UserInfo userInfo) {
        userInfoService.updateById(userInfo);
        return JSON.toJSONString(userInfo);
    }

    @ApiOperation("delete user")
    @DeleteMapping("/{id}")
    public String delete(@ApiParam("user id") @PathVariable() Long id) {
        userInfoService.deleteById(id);
        return "success";
    }

    @ApiOperation("list user")
    @GetMapping
    public List<UserInfo> list() {
        Wrapper<UserInfo> wrapper = new EntityWrapper<>();
        return userInfoService.selectList(wrapper);
    }
}
