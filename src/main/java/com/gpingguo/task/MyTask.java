package com.gpingguo.task;

import com.aliyun.tea.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gpingguo.dao.ScheduledMapper;
import com.gpingguo.model.ParsingRecord;
import com.gpingguo.model.Scheduled;
import com.gpingguo.service.ParsingRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class MyTask implements SchedulingConfigurer {

    @Autowired
    protected ScheduledMapper scheduledMapper;
    @Autowired
    private ParsingRecordService parsingRecordService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(this::process,
                triggerContext -> {
                    QueryWrapper<Scheduled> qw = new QueryWrapper<>();
                    qw.last("limit 1");
                    Scheduled one = scheduledMapper.selectOne(qw);
                    if (one == null || StringUtils.isEmpty(one.getCron())) {
                        log.info("cron is null");
                        return null;
                    }
                    log.info("间隔时间{}秒", one.getCron());
                    long parsed;
                    try {
                        parsed = Long.parseLong(one.getCron());
                    } catch (Exception e) {
                        parsed = 60;
                        log.info("数据格式异常{}", e.getMessage());
                    }
                    PeriodicTrigger periodicTrigger = new PeriodicTrigger(parsed * 1000);
                    return periodicTrigger.nextExecutionTime(triggerContext);
                    //return new CronTrigger(one.getCron()).nextExecutionTime(triggerContext);
                });
    }

    private void process() {
        QueryWrapper<ParsingRecord> qw = new QueryWrapper<>();
        qw.last("limit 1");
        ParsingRecord one = parsingRecordService.getOne(qw);
        if (one != null) {
            parsingRecordService.add(one);
        }
    }

}
