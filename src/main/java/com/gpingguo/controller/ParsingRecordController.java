package com.gpingguo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gpingguo.model.*;
import com.gpingguo.service.ParsingRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@Slf4j
public class ParsingRecordController {

    @Autowired
    private ParsingRecordService parsingRecordService;

    /**
     * @Author gpingguo
     * 路由至主页面
     * @Date 2023/8/18
     * @return String
     */
    @RequestMapping("/")
    public String indexPage() {
        return "index";
    }

    /**
     * @Author gpingguo
     * 加载数据
     * @Date 2023/8/16
     * @param :
     * @return ResponseResult<?>
     */
    @GetMapping("/getData")
    @ResponseBody
    public ResponseResult<?> getData() {

        JSONObject result = new JSONObject();

        MyConfig config = parsingRecordService.getConfig();
        if (config != null) {
            result.put("platform", config.getPlatform());
            result.put("accessKey", config.getAccessKey());
            result.put("accessKeySecret", config.getAccessKeySecret());
        }
        Scheduled cron = parsingRecordService.getCron();
        if (cron != null) {
            result.put("cron", cron.getCron());
        }
        ParsingRecord parsingRecord = parsingRecordService.getParsingRecord();
        if (parsingRecord != null) {
            result.put("domain", parsingRecord.getDomain());
            result.put("rr", parsingRecord.getRr());
            result.put("type", parsingRecord.getType());
            result.put("value", parsingRecord.getValue());
        }
        return ResponseResult.okData(result);
    }

    /**
     * @Author gpingguo
     * 跳转登录页
     * @Date 2023/8/16
     * @param :
     * @return String
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * @Author gpingguo
     * 登录
     * @Date 2023/8/16
     * @param dto:
	 * @param request:
     * @return ResponseResult<?>
     */
    @PostMapping("/myLogin")
    @ResponseBody
    public ResponseResult<?> login(User dto, HttpServletRequest request) {
        log.info("{},{}",dto.getAccount(),dto.getPassword());
        Boolean pass = parsingRecordService.login(dto.getAccount(), dto.getPassword());
        if (pass !=null && pass) {
            request.getSession().setAttribute("auth", true);
            return ResponseResult.ok("登录成功", 1);
        } else {
            //request.getSession().setAttribute("auth", false);
            log.info("登录失败");
            return ResponseResult.ok("登录失败", 0);
        }
    }

    /**
     * @Author gpingguo
     * 设置密码
     * @Date 2023/8/16
     * @param dto:
     * @return ResponseResult<?>
     */
    @GetMapping("/setPassword")
    @ResponseBody
    public ResponseResult<?> setPassword(User dto) {
        parsingRecordService.setPassword(dto);
        return ResponseResult.ok();
    }

    /**
     * @Author gpingguo
     * 设置认证配置
     * @Date 2023/8/16
     * @param dto:
     * @return ResponseResult<MyConfig>
     */
    @GetMapping("/setConfig")
    @ResponseBody
    public ResponseResult<MyConfig> setConfig(MyConfig dto) {
        MyConfig config = parsingRecordService.setConfig(dto);
        return ResponseResult.okData(config);
    }

    /**
     * @Author gpingguo
     * 设置执行间隔时间
     * @Date 2023/8/16
     * @param dto:
     * @return ResponseResult<Scheduled>
     */
    @GetMapping("/setCron")
    @ResponseBody
    public ResponseResult<Scheduled> setCron(Scheduled dto) {
        Scheduled scheduled = parsingRecordService.setCron(dto);
        return ResponseResult.okData(scheduled);
    }

    /**
     * @Author gpingguo
     * 新增记录
     * @Date 2023/8/16
     * @param dto:
     * @return ResponseResult<ParsingRecord>
     */
    @GetMapping("/add")
    @ResponseBody
    public ResponseResult<ParsingRecord> add(ParsingRecord dto) {
        parsingRecordService.add(dto);
        return ResponseResult.ok();
    }


}
