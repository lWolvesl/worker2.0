package com.li.worker2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Stack;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    @TableField("mailServer")
    private String mailServer;

    @TableField("mailAuth")
    private String mailAuth;

    @TableField("mailPassword")
    private String mailPassword;
}
