package com.li.worker2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.worker2.entity.Master;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
public interface MasterService extends IService<Master> {
    String iniMail();

    String sendMail(String email, String subject, String emailMsg);

    String getAllUser();
}
