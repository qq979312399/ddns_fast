package com.gpingguo.provide.impl;

import com.baidubce.services.dns.DnsClient;
import com.baidubce.services.dns.model.CreateRecordRequest;
import com.baidubce.services.dns.model.ListRecordResponse;
import com.gpingguo.model.MyConfig;
import com.gpingguo.model.ParsingRecord;
import com.gpingguo.model.em.PlatformEnum;
import com.gpingguo.provide.DnsService;
import com.gpingguo.provide.Validator;
import com.gpingguo.service.ParsingRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Validator(authType = PlatformEnum.BAIDU)
public class BaiDuDnsServiceImpl implements DnsService {

    public void updateDnsRecord(ParsingRecord dto, ParsingRecord record, MyConfig myConfig, ParsingRecordService service) throws Exception {
        //百度dns客户端
        DnsClient dnsClient = new DnsClient(myConfig.getAccessKey(), myConfig.getAccessKeySecret());

        //查询解析列表
        ListRecordResponse response = dnsClient.listRecord(dto.getDomain(), dto.getRr(), "", "", null);
        dto.setRecordId("");
        List<ListRecordResponse.Record> records = response.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            for (ListRecordResponse.Record item : records) {
                dto.setRecordId(item.getId());
            }
        }

        //删除解析记录
        if (StringUtils.isNotBlank(dto.getRecordId())) {
            dnsClient.deleteRecord(dto.getDomain(), dto.getRecordId(), null);
        }

        //新增解析记录
        CreateRecordRequest createRecordRequest = new CreateRecordRequest();
        createRecordRequest.setRr(dto.getRr());
        createRecordRequest.setType(dto.getType());
        createRecordRequest.setValue(dto.getValue());
        dnsClient.createRecord(dto.getDomain(), createRecordRequest, "");

        //解析记录入库
        if (record != null) {
            service.removeById(record.getId());
        }
        service.save(dto);
    }

}
