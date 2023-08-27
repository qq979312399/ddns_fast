package com.gpingguo.provide;

import com.gpingguo.model.MyConfig;
import com.gpingguo.model.ParsingRecord;
import com.gpingguo.service.ParsingRecordService;

public interface DnsService {

    /**
     * @Author gpingguo
     * dns解析记录更新
     * @Date 2023/8/21
     * @param dto:
	 * @param record:
	 * @param myConfig:
	 * @param parsingRecordService:
     * @return void
     */
    void updateDnsRecord(ParsingRecord dto, ParsingRecord record, MyConfig myConfig, ParsingRecordService parsingRecordService) throws Exception;

}
