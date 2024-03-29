package com.haozi.account.controller;

import cn.hutool.core.collection.CollUtil;
import com.haozi.account.dao.po.Resouces;
import com.haozi.account.dao.po.Users;
import com.haozi.account.service.*;
import com.haozi.common.exception.internal.ParamMissingException;
import com.haozi.common.model.ResponseResult;
import com.haozi.common.model.dto.account.AccountInfo;
import com.haozi.common.model.dto.account.EmailRequest;
import com.haozi.common.model.dto.account.PhoneRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zyh
 * @version 1.0
 * @date 2022/4/16 3:06 下午
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {


    @Autowired
    IUsersService usersService;
    @Autowired
    IUserRoleService roleService;
    @Autowired
    IRoleResourcesService roleResourcesService;

    /**
     * 根据邮箱地址获取账户信息
     * @param emailRequest 邮箱信息
     * @return
     */
    @RequestMapping("/email")
    public ResponseResult accountInfoByEmail(@RequestBody EmailRequest emailRequest){

        if(emailRequest == null){
            throw new ParamMissingException("emailRequest","根据邮箱地址获取账户信息");
        }

        Users users = usersService.selectByEmail(emailRequest.getEmail());

        if(users == null){
            return ResponseResult.success(null);
        }

        List<String> resources = getResourcesByUsers(users);

        AccountInfo accountInfo =
                new AccountInfo(users.getUsername(),resources,users.getPwd(),users.getEnabled());

        return ResponseResult.success(accountInfo);
    }

    /**
     * 根据手机获取账户信息
     * @param PhoneRequest 邮箱信息
     * @return
     */
    @RequestMapping("/phone")
    public ResponseResult accountInfoByPhone(@RequestBody PhoneRequest phoneRequest){

        if(phoneRequest == null){
            throw new ParamMissingException("emailRequest","根据邮箱地址获取账户信息");
        }

        Users users = usersService.selectByPhone(phoneRequest.getPhone());

        if(users == null){
            return ResponseResult.success(null);
        }

        List<String> resources = getResourcesByUsers(users);

        AccountInfo accountInfo =
                new AccountInfo(users.getUsername(),resources,users.getPwd(),users.getEnabled());

        return ResponseResult.success(accountInfo);
    }
    /**
     * 根据用户获取资源标识
     * @param users
     * @return
     */
    private List<String> getResourcesByUsers(Users users){
        List<Integer> roleIdByUserName = roleService.findRoleIdByUserName(users.getUsername());

        if(CollUtil.isEmpty(roleIdByUserName)){
            return Arrays.asList();
        }

        List<Resouces> resourcesByRoleId = roleResourcesService.findResourcesByRoleId(roleIdByUserName);

        List<String> resourceFlag = resourcesByRoleId.stream().map(resouces -> resouces.getResourceFlag()).collect(Collectors.toList());
        return resourceFlag;

    }
}
