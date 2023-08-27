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
 * 解析记录表
 * </p>
 *
 * @author sssd
 * @since 2023-03-19
 */
@TableName("parsing_record")
@Data
public class ParsingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    //平台解析记录ID
    private String recordId;
    //ip类型 ipv4 ipv6
    private String ipType;
    //域名
    private String domain;
    //主机记录
    private String rr;
    //解析记录类型
    private String type;
    //记录值
    private String value;
    //更新频次（分钟）
    private String frequency;

    /*@TableField(exist = false)
    private String serviceProviderName;*/

}
