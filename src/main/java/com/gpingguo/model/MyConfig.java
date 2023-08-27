package com.gpingguo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 认证配置表
 * </p>
 *
 * @author sssd
 * @since 2023-03-19
 */
@TableName("my_config")
@Data
public class MyConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    //平台
    private String platform;
    //accessKey
    private String accessKey;
    //accessKeySecret
    private String accessKeySecret;

}
