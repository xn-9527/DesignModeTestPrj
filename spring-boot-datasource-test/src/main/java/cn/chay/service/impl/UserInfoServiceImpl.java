package cn.chay.service.impl;

import cn.chay.entity.UserInfo;
import cn.chay.mapper.UserInfoMapper;
import cn.chay.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author Chay
 * @date 2020/4/7 11:28
 */
@Service
public class UserInfoServiceImpl extends MyBaseServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
