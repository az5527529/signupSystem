CREATE TABLE `signup_info` (
  `signup_info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名字',
  `telephone` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `id_card` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证',
  `sex` tinyint NOT NULL DEFAULT 0 COMMENT '性别',
  `activity_id` bigint NOT NULL DEFAULT 0 COMMENT '活动id',
  `openid` varchar(64) NOT NULL DEFAULT '' COMMENT '微信id',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `number` varchar(32) NOT NULL DEFAULT '' COMMENT '报名序号',
  `signup_time` varchar(20) NOT NULL DEFAULT '' COMMENT '报名时间',
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
  `is_take_material` tinyint NOT NULL DEFAULT 0 COMMENT '是否已领取物资',
  
  PRIMARY KEY (`signup_info_id`),
  key signup_info_name(`name`),
  key signup_info_telephone(`telephone`),
  key signup_info_activity_id(`activity_id`),
  key signup_info_openid(`openid`),
  key signup_info_number(`number`),
  key signup_info_order_no(`order_no`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
