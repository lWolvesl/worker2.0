package com.li.worker2.service;

import com.li.worker2.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
public interface UserService extends IService<User> {
    List<User> getAll();

    void run();
}
