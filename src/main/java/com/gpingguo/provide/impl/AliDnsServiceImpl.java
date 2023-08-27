package com.gpingguo.provide.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.alidns20150109.Client;
import com.aliyun.alidns20150109.models.AddDomainRecordRequest;
import com.aliyun.alidns20150109.models.AddDomainRecordResponse;
import com.aliyun.alidns20150109.models.DeleteSubDomainRecordsRequest;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.gpingguo.model.MyConfig;
import com.gpingguo.model.ParsingRecord;
import com.gpingguo.model.em.PlatformEnum;
import com.gpingguo.provide.DnsService;
import com.gpingguo.provide.Validator;
import com.gpingguo.service.ParsingRecordService;
import org.springframework.stereotype.Service;

@Service
@Validator(authType = PlatformEnum.ALI)
public class AliDnsServiceImpl implements DnsService {

    public void updateDnsRecord(ParsingRecord dto, ParsingRecord record, MyConfig myConfig, ParsingRecordService service) throws Exception {
        //key配置
        Config config = new Config();
        config.setAccessKeyId(myConfig.getAccessKey());
        config.setAccessKeySecret(myConfig.getAccessKeySecret());
        //创建客户端
        Client client = new Client(config);

        //先删除解析记录（阿里云不支持单独修改域名记录值）
        DeleteSubDomainRecordsRequest requestDel = new DeleteSubDomainRecordsRequest();
        requestDel.setDomainName(dto.getDomain());
        requestDel.setRR(dto.getRr());

        client.deleteSubDomainRecords(requestDel);

        //新增解析记录
        AddDomainRecordRequest requestAdd = new AddDomainRecordRequest();
        requestAdd.setDomainName(dto.getDomain());
        requestAdd.setRR(dto.getRr());
        requestAdd.setValue(dto.getValue());
        requestAdd.setType(dto.getType());

        AddDomainRecordResponse recordResponseAdd = client.addDomainRecord(requestAdd);
        String jsonStringAdd = Common.toJSONString(TeaModel.buildMap(recordResponseAdd));
        JSONObject jsonObject = JSONObject.parseObject(jsonStringAdd);
        JSONObject body = jsonObject.getJSONObject("body");
        String recordId = body.getString("RecordId");
        dto.setRecordId(recordId);

        //解析记录入库
        if (record != null) {
            service.removeById(record.getId());
        }
        service.save(dto);
    }

}
