package com.gpingguo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpingguo.model.MyConfig;
import com.gpingguo.model.ParsingRecord;
import com.gpingguo.model.Scheduled;
import com.gpingguo.model.User;

public interface ParsingRecordService extends IService<ParsingRecord> {

    /**
     * @Author gpingguo
     * 新增解析记录
     * @Date 2023/8/13
     * @param dto:
     * @return ParsingRecord
     */
    void add(ParsingRecord dto);

    /**
     * @Author gpingguo
     * 获取解析记录
     * @Date 2023/8/16
     * @param :
     * @return ParsingRecord
     */
    ParsingRecord getParsingRecord();

    /**
     * @Author gpingguo
     * 获取配置信息
     * @Date 2023/8/14
     * @return MyConfig
     */
    MyConfig getConfig();

    /**
     * @Author gpingguo
     * 设置配置信息
     * @Date 2023/8/14
     * @param dto:
     * @return MyConfig
     */
    MyConfig setConfig(MyConfig dto);

    /**
     * @Author gpingguo
     * 查询表达式
     * @Date 2023/8/16
     * @param :
     * @return Scheduled
     */
    Scheduled getCron();

    /**
     * @Author gpingguo
     * 设置表达式
     * @Date 2023/8/16
     * @param dto:
     * @return Scheduled
     */
    Scheduled setCron(Scheduled dto);

    /**
     * @param :
     * @param account
     * @param password
     * @return void
     * @Author gpingguo
     * 登录
     * @Date 2023/8/16
     */
    Boolean login(String account, String password);

    /**
     * @Author gpingguo
     * 设置密码
     * @Date 2023/8/16
     * @param dto:
     * @return void
     */
    void setPassword(User dto);

}
