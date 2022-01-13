package com.li.worker2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "name", type = IdType.ID_WORKER_STR)
    private String name;

    private String mail;

    @TableField("status")
    private Integer status;

    @TableField("time")
    private Date time;

    @TableField("remaker")
    private String remaker;

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", status=" + status +
                ", time=" + time +
                ", remaker='";
    }
}
