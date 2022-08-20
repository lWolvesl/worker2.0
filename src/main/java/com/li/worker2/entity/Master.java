package com.li.worker2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class Master implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("mailHost")
    private String mailHost;

    @TableId(value = "mailServer",type = IdType.AUTO)
    private String mailServer;

    @TableField("mailAuth")
    private String mailAuth;

    @TableField("mailPassword")
    private String mailPassword;

    @TableField("mailRemind")
    private String mailRemind;

    @TableField("startTime")
    private int start;

    @TableField("endTime")
    private int end;

    @Override
    public String toString() {
        return "mailHost='" + mailHost + '\'' +
                ", mailServer='" + mailServer + '\'' +
                ", mailAuth='" + mailAuth + '\'' +
                ", mailPassword='" + mailPassword + '\'' +
                ", mailRemind='" + mailRemind;
    }
}
