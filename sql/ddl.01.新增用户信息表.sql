CREATE TABLE `user_info` (
  `user_info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  
  PRIMARY KEY (`user_info_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
