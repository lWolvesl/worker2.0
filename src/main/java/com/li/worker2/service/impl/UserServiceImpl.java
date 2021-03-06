package com.li.worker2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.li.worker2.entity.User;
import com.li.worker2.mapper.UserMapper;
import com.li.worker2.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
