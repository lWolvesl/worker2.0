package com.li.worker2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.worker2.entity.Master;
import com.li.worker2.entity.User;
import org.springframework.lang.Nullable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
public interface RecordService extends IService<Master> {
    void save(User user, Integer status, @Nullable String remarker);

    String getAllRecord();
}
