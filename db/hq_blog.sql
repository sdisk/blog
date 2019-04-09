
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_attach`
-- ----------------------------
DROP TABLE IF EXISTS `t_attach`;
CREATE TABLE `t_attach` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) NOT NULL DEFAULT '',
  `ftype` varchar(50) DEFAULT '',
  `fkey` text NOT NULL,
  `creatorId` int(10) DEFAULT NULL,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



-- ----------------------------
--  Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `coid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned DEFAULT '0',
  `created` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
  `author` varchar(200) DEFAULT NULL,
  `authorId` int(10) unsigned DEFAULT '0',
  `ownerId` int(10) unsigned DEFAULT '0',
  `mail` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `agent` varchar(200) DEFAULT NULL,
  `content` text,
  `type` varchar(16) DEFAULT 'comment',
  `status` varchar(16) DEFAULT 'approved',
  `parentId` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`coid`),
  KEY `cid` (`cid`),
  KEY `created` (`created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_contents`
-- ----------------------------
DROP TABLE IF EXISTS `t_contents`;
CREATE TABLE `t_contents` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `titlePic` varchar(55) DEFAULT NULL,
  `slug` varchar(200) DEFAULT NULL,
  `createTime` Timestamp not null DEFAULT CURRENT_TIMESTAMP,
  `modifyTime` Timestamp DEFAULT  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `content` text COMMENT '内容文字',
  `authorId` int(10) unsigned DEFAULT '0',
  `type` varchar(16) DEFAULT 'post',
  `status` varchar(16) DEFAULT 'publish',
  `tags` varchar(200) DEFAULT NULL,
  `categories` varchar(200) DEFAULT NULL,
  `hits` int(10) unsigned DEFAULT '0',
  `commentsNum` int(10) unsigned DEFAULT '0',
  `allowComment` tinyint(1) DEFAULT '1',
  `allowPing` tinyint(1) DEFAULT '1',
  `allowFeed` tinyint(1) DEFAULT '1',
	`allowShow` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `logName` varchar(100) DEFAULT NULL COMMENT '事件',
  `userId` int(10) NOT NULL COMMENT '登录者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `succeed` varchar(20) DEFAULT NULL COMMENT '是否执行成功',
  `ip` varchar(20) DEFAULT NULL COMMENT '登陆者ip地址',
  `messgae` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `logType` varchar(100) DEFAULT NULL COMMENT '日志类型',
  `logName` varchar(100) DEFAULT NULL COMMENT '日志名称',
  `userId` int(10) NOT NULL COMMENT '登录者id',
   `className` varchar(100) DEFAULT NULL COMMENT '类名称',
   `method` varchar(100) DEFAULT NULL COMMENT '方法名称',
   `data` varchar(500) DEFAULT NULL COMMENT '产生的数据',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `succeed` varchar(20) DEFAULT NULL COMMENT '是否执行成功',
  `ip` varchar(20) DEFAULT NULL COMMENT '登陆者ip地址',
  `messgae` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for `t_meta`
-- ----------------------------
DROP TABLE IF EXISTS `t_meta`;
CREATE TABLE `t_meta` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `slug` varchar(200) DEFAULT NULL,
  `type` varchar(32) NOT NULL DEFAULT '',
  `contentType` varchar(32) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `sort` int(10) unsigned DEFAULT '0',
  `parentId` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`mid`),
  KEY `slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_options`
-- ----------------------------
DROP TABLE IF EXISTS `t_option`;
CREATE TABLE `t_option` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `value` varchar(1000) DEFAULT '',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



-- ----------------------------
--  Table structure for `t_relationship`
-- ----------------------------
DROP TABLE IF EXISTS `t_relationship`;
CREATE TABLE `t_relationship` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL,
  `mid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_users`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `homeUrl` varchar(200) DEFAULT NULL,
  `screenName` varchar(32) DEFAULT NULL,
  `created` Timestamp not null DEFAULT CURRENT_TIMESTAMP,
  `activated` Timestamp DEFAULT CURRENT_TIMESTAMP,
  `logged` Timestamp DEFAULT CURRENT_TIMESTAMP,
  `groupName` varchar(16) DEFAULT 'visitor',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

BEGIN;
INSERT INTO `t_option` VALUES
(1,'baidu_site_verification', null, '百度网站验证码'),
(2,'google_site_verification', null, 'google网站验证码'),
(3,'site_description', null, '网站描述'),
(4,'site_keywords', null, null),
(5,'site_record', null, '备案号'),
(6,'site_title', null, '网站标题'),
 (7,'social_csdn', null, 'csdn'),
(8,'social_github', null, 'github'),
(9,'social_jianshu', null, '简书地址'),
(10,'social_resume', null, '简历地址'),
(11,'social_twitter', null, 'twitter'),
 (12,'social_weibo', null, '微博地址'),
(13,'social_zhihu', null, '知乎地址');
COMMIT;

BEGIN;
INSERT INTO `t_user` VALUES ('1', 'admin', 'a66abb5684c45962d887564f08346e8d', 'huang50179@163.com', null, 'admin', NULL, NULL, NULL, 'visitor');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
