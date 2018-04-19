CREATE TABLE `pay_order` (
  `pay_order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态',
  `created_time` varchar(20) NOT NULL DEFAULT '' COMMENT '生成时间',
  `pay_time` varchar(20) NOT NULL DEFAULT '' COMMENT '付款时间',
  `description` varchar(64) NOT NULL DEFAULT '' COMMENT '订单描述',
  `expire_time` varchar(20) NOT NULL DEFAULT '' COMMENT '订单失效时间',
  `signup_numbers` varchar(32) NOT NULL DEFAULT '' COMMENT '报名序号',
  `money` double NOT NULL DEFAULT 0 COMMENT '订单金额',
  
  PRIMARY KEY (`pay_order_id`),
  key pay_order_order_no(`order_no`),
  key pay_order_created_time(`created_time`),
  key pay_order_pay_time(`pay_time`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
