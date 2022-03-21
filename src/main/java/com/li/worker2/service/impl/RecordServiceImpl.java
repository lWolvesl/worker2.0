package com.li.worker2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.li.worker2.entity.Master;
import com.li.worker2.entity.Record;
import com.li.worker2.entity.User;
import com.li.worker2.mapper.MasterMapper;
import com.li.worker2.mapper.RecordMapper;
import com.li.worker2.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@Service
public class RecordServiceImpl extends ServiceImpl<MasterMapper, Master> implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void save(User user, Integer status, @Nullable String remarker) {
        Record record = new Record();
        record.setTime(new Date());
        record.setStatus(status);
        record.setName(user.getName());
        record.setRemaker(remarker);
        record.setMail(user.getMail());
        recordMapper.insert(record);
        logger.info("记录保存成功");
    }

    @Override
    public String getAllRecord() {
        List<Record> records = recordMapper.selectList(null);
        StringBuilder sb = new StringBuilder();
        for (Record record : records) {
            sb.append(record).append("\n");
        }
        return sb.toString();
    }
}
