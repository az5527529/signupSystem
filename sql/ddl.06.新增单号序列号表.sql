CREATE TABLE `no_robot` (
  `no_robot_id` varchar(16) NOT NULL default '' COMMENT '主键',
  `current_val` bigint(20) NOT NULL default 0 COMMENT '当前值',
  `bit` int NOT NULL default 0 COMMENT '位数',
  
  PRIMARY KEY (`no_robot_id`)
 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
