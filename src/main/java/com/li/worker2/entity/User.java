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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "name", type = IdType.ID_WORKER_STR)
    private String name;

    @TableField("cookieName")
    private String cookieName;

    @TableField("cookieValue")
    private String cookieValue;

    @TableField("personalPhone")
    private String personalPhone;

    private String emergency;

    @TableField("emergencyPhone")
    private String emergencyPhone;

    private String location;

    @TableField("isInschool")
    private Boolean isInschool;

    private String mail;

    @TableField("enable")
    private Boolean enable;

    @TableField("host")
    private String host;

    @TableField("sc")
    private Boolean sc;

    @TableField("StudentID")
    private String studentId;

    @TableField("schoolLocation")
    private String schoolLocation;
}
