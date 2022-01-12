package com.li.worker2.mapper;

import com.li.worker2.entity.Master;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@Repository
@Mapper
public interface MasterMapper extends BaseMapper<Master> {
}
