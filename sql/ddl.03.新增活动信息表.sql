CREATE TABLE `activity` (
  `activity_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `start_time` varchar(20) NOT NULL DEFAULT '' COMMENT '活动开始时间',
  `end_time` varchar(20) NOT NULL DEFAULT '' COMMENT '活动结束时间',
  `topic` varchar(64) NOT NULL DEFAULT '' COMMENT '活动主题',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '活动内容',
  `background_url` varchar(128) NOT NULL DEFAULT '' COMMENT '背景图片',
  `money` double NOT NULL DEFAULT 0 COMMENT '活动费用',
  
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
