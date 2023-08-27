<p align="center">
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg"/>
	</a>
    <a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/SpringBoot-2.2.9.RELEASE-blue.svg"/>
	</a>
</p>
基于SpringBoot自动获得你的公网 IPv4 或 IPv6 地址，并解析到对应的域名服务。

- [特性](#特性)
- [系统中使用](#系统中使用)
- [界面](#界面)

## 特性

- 支持Mac、Windows、Linux系统，支持ARM、x86架构
- 支持的域名服务商 `Alidns(阿里云)` `Dnspod(腾讯云)`  `百度云` 其他待支持
- 支持以服务的方式运行
- 默认间隔1分钟同步一次（可配置）
- 采用内嵌式数据库，不需要安装繁琐数据库

## 系统中使用
- 可直接安装jdk8，并配置环境变量，运行本项目打包好的jar包
- docker运行（推荐）

## 界面
- 项目运行成功后访问http：服务器ip:9001,登录界面输入用户名admin，密码123456，首次登录后建议修改密码(忘记密码重启服务)
- 登录成功后，配置好认证key和解析记录即可
