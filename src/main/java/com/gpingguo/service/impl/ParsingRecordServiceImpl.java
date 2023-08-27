package com.gpingguo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpingguo.dao.MyConfigMapper;
import com.gpingguo.dao.ParsingRecordMapper;
import com.gpingguo.dao.ScheduledMapper;
import com.gpingguo.dao.UserMapper;
import com.gpingguo.model.MyConfig;
import com.gpingguo.model.ParsingRecord;
import com.gpingguo.model.Scheduled;
import com.gpingguo.model.User;
import com.gpingguo.model.em.IpTypeEnum;
import com.gpingguo.provide.DnsContainer;
import com.gpingguo.provide.DnsService;
import com.gpingguo.service.ParsingRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ParsingRecordServiceImpl extends ServiceImpl<ParsingRecordMapper,ParsingRecord> implements ParsingRecordService {

    @Autowired
    private DnsContainer authContainer;
    @Autowired
    private MyConfigMapper myConfigMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ScheduledMapper scheduledMapper;

    @Override
    public void add(ParsingRecord dto) {
        dto.setValue("");
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            //判断ip类型
            String command;
            if (IpTypeEnum.IPV6.getName().equals(dto.getIpType())) {
                command = "curl 6.ipw.cn";
            } else {
                command = "curl 4.ipw.cn";
            }
            //执行命令获取公网ip
            process = runtime.exec(command);
            // 读取DOS命令的返回结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String ip = "";
            while ((ip = reader.readLine()) != null) {
                log.info("响应ip{}", ip);
                dto.setValue(ip);
            }
            reader.close();

            if (StringUtils.isEmpty(dto.getValue())) {
                log.info("未获取ip数据，执行失败");
                return;
            }

            //查询库中已存在的解析记录
            QueryWrapper<ParsingRecord> qw = new QueryWrapper<>();
            qw.eq("domain", dto.getDomain());
            qw.eq("rr", dto.getRr());
            qw.last("limit 1");
            ParsingRecord record = getOne(qw);
            if (record != null) {
                if (record.getValue().equals(dto.getValue())) {
                    log.info("ip未改变，不执行调用");
                    return;
                }
            }

            QueryWrapper<MyConfig> my = new QueryWrapper<>();
            my.last("limit 1");
            MyConfig myConfig = myConfigMapper.selectOne(my);

            if (myConfig == null) {
                log.info("未设置认证配置");
                return;
            }

            //开始调用云sdk
            //设置配置信息
            ConcurrentHashMap<String, DnsService> map = authContainer.getValidatorMap();
            DnsService dnsService = map.get(myConfig.getPlatform());
            if (dnsService != null) {
                dnsService.updateDnsRecord(dto, record, myConfig, this);
            }
            //待热心网友补充其他云sdk

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

    }

    @Override
    public ParsingRecord getParsingRecord() {
        QueryWrapper<ParsingRecord> qw = new QueryWrapper<>();
        qw.last("limit 1 ");
        return getOne(qw);
    }

    @Override
    public MyConfig getConfig() {
        QueryWrapper<MyConfig> qw = new QueryWrapper<>();
        qw.last("limit 1 ");
        return myConfigMapper.selectOne(qw);
    }

    @Override
    public MyConfig setConfig(MyConfig dto) {
        QueryWrapper<MyConfig> qw = new QueryWrapper<>();
        qw.ge("id", 0);
        myConfigMapper.delete(qw);
        myConfigMapper.insert(dto);
        return dto;
    }

    @Override
    public Scheduled getCron() {
        QueryWrapper<Scheduled> qw = new QueryWrapper<>();
        qw.last("limit 1 ");
        return scheduledMapper.selectOne(qw);
    }

    @Override
    public Scheduled setCron(Scheduled dto) {
        QueryWrapper<Scheduled> qw = new QueryWrapper<>();
        qw.ge("id", 0);
        scheduledMapper.delete(qw);
        scheduledMapper.insert(dto);
        return dto;
    }

    @Override
    public Boolean login(String account, String password) {
        String pw= userMapper.selectByAccount(account);
        if (!StringUtils.isEmpty(pw)) {
            String digest = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            if (digest.equals(pw)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void setPassword(User dto) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.ge("id", 0);
        userMapper.delete(qw);
        if (StringUtils.isNotBlank(dto.getPassword())) {
            dto.setPassword(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)));
            userMapper.insert(dto);
        }
    }

}
