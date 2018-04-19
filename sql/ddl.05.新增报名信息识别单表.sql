CREATE TABLE `identify_order` (
  `identify_order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activity_id` bigint(20) NOT NULL default 0 COMMENT '活动id',
  `openid` varchar(64) NOT NULL DEFAULT '' COMMENT '微信id',
  `identify_no` varchar(32) NOT NULL DEFAULT '' COMMENT '识别号',
  
  PRIMARY KEY (`identify_order_id`),
  key identify_order_identify_no(`identify_no`),
  key identify_order_activity_id(`activity_id`),
  key identify_order_openid(`openid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
