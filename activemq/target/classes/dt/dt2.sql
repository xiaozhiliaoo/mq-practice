USE dt2;

DROP TABLE IF EXISTS `t_point`;

CREATE TABLE `t_point` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL COMMENT '关联的用户ID',
  `amount` int(11) DEFAULT NULL COMMENT '积分金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;


DROP TABLE IF EXISTS `t_event`;

CREATE TABLE `t_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) DEFAULT NULL COMMENT '事件类型',
  `process` varchar(30) DEFAULT NULL COMMENT '表示事件进行到了哪个环节',
  `content` text COMMENT '事件包含的内容',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;