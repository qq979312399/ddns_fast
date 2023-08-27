-- ----------------------------
-- 创建用户表
-- ----------------------------
-- 定时器
CREATE TABLE IF NOT EXISTS `scheduled` (
                             `id` bigint NOT NULL,
                             `cron` varchar(200)  NOT NULL DEFAULT '',
                             PRIMARY KEY (`id`)
);

--DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
                        `id` bigint NOT NULL DEFAULT '0' ,
                        `account` varchar(200) NOT NULL DEFAULT '' ,
                        `password` varchar(100) NOT NULL DEFAULT '',
                        PRIMARY KEY (`id`)
);

-- 创建配置表
--DROP TABLE IF EXISTS `my_config`;
CREATE TABLE IF NOT EXISTS `my_config` (
                             `id` bigint NOT NULL DEFAULT '0' ,
                             `platform` varchar(100) NOT NULL DEFAULT '',
                             `access_key` varchar(300) NOT NULL DEFAULT '',
                             `access_key_secret` varchar(300) NOT NULL DEFAULT '',
                             PRIMARY KEY (`id`)
);

-- 创建解析记录表
CREATE TABLE IF NOT EXISTS `parsing_record` (
                                  `id` bigint DEFAULT NULL,
                                  `record_id` varchar(200) NOT NULL DEFAULT '',
                                  `domain` varchar(300) NOT NULL DEFAULT '',
                                  `rr` varchar(100) NOT NULL DEFAULT '',
                                  `ip_type` varchar(100) NOT NULL DEFAULT '',
                                  `type` varchar(100) NOT NULL DEFAULT '',
                                  `value` varchar(200) NOT NULL DEFAULT '',
                                  `frequency` int NOT NULL DEFAULT '1',
                                  PRIMARY KEY (`id`)
);
