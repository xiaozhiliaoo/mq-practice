USE dt2;

DROP TABLE IF EXISTS `t_point`;

CREATE TABLE `t_point` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL COMMENT '关联的用户ID',
  `amount` int(11) DEFAULT NULL COMMENT '积分金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;
