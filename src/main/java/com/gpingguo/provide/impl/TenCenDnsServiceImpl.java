package com.gpingguo.provide.impl;

import com.gpingguo.model.MyConfig;
import com.gpingguo.model.ParsingRecord;
import com.gpingguo.model.em.PlatformEnum;
import com.gpingguo.provide.DnsService;
import com.gpingguo.provide.Validator;
import com.gpingguo.service.ParsingRecordService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.dnspod.v20210323.DnspodClient;
import com.tencentcloudapi.dnspod.v20210323.models.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Validator(authType = PlatformEnum.TENCENT)
public class TenCenDnsServiceImpl implements DnsService {

    public void updateDnsRecord(ParsingRecord dto, ParsingRecord record, MyConfig myConfig, ParsingRecordService service) throws Exception {
        //认证配置
        Credential credential = new Credential(myConfig.getAccessKey(), myConfig.getAccessKeySecret());
        //腾讯DNSPod客户端
        DnspodClient client = new DnspodClient(credential, "");

        //先查询域名列表获取对应记录id，方便删除操作
        DescribeRecordListRequest listRequest = new DescribeRecordListRequest();
        listRequest.setDomain(dto.getDomain());
        listRequest.setSubdomain(dto.getRr());

        DescribeRecordListResponse response = client.DescribeRecordList(listRequest);
        RecordListItem[] recordList = response.getRecordList();
        dto.setRecordId("");
        if (!ArrayUtils.isEmpty(recordList)) {
            for (RecordListItem item : recordList) {
                dto.setRecordId(item.getRecordId().toString());
            }
        }

        //删除解析记录
        if (StringUtils.isNotBlank(dto.getRecordId())) {
            DeleteRecordRequest requestDel = new DeleteRecordRequest();
            requestDel.setDomain(dto.getDomain());
            requestDel.setRecordId(Long.valueOf(dto.getRecordId()));

            client.DeleteRecord(requestDel);
        }

        //新增解析记录
        CreateRecordRequest requestAdd = new CreateRecordRequest();
        requestAdd.setDomain(dto.getDomain());
        requestAdd.setRecordType(dto.getType());
        requestAdd.setRecordLine("默认");
        requestAdd.setValue(dto.getValue());

        CreateRecordResponse createRecordResponse = client.CreateRecord(requestAdd);

        if (createRecordResponse != null && createRecordResponse.getRecordId() != null) {
            dto.setRecordId(createRecordResponse.getRecordId().toString());
        }

        //解析记录入库
        if (record != null) {
            service.removeById(record.getId());
        }
        service.save(dto);
    }

}
