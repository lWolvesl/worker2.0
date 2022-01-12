package com.li.worker2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
    private Integer isInschool;

    private String mail;

}
