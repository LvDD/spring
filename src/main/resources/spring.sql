/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50633
Source Host           : localhost:3306
Source Database       : spring

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2016-09-28 20:19:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `user_email` varchar(35) DEFAULT NULL,
  `user_pwd` varchar(20) DEFAULT NULL,
  `pwd_salt` varchar(10) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `modify_time` datetime NOT NULL,
  `is_delete` smallint(6) NOT NULL DEFAULT '0' COMMENT '0:not del ; 1:del',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'lvdong', 'lvdong@jd.com', '123456', '8578', '2016-09-28 17:59:21', '2016-09-28 17:59:24', '0');
INSERT INTO `t_user` VALUES ('2', 'zhangsan', 'zhangsan@jd.com', '123456', '4272', '2016-09-28 17:59:43', '2016-09-28 17:59:46', '0');
INSERT INTO `t_user` VALUES ('3', 'lisi', 'lisi@jd.com', '123456', '2424', '2016-09-28 18:01:21', '2016-09-28 18:01:23', '0');
INSERT INTO `t_user` VALUES ('4', 'wangwu', 'wangwu@jd.com', '123456', '8745', '2016-09-28 18:01:27', '2016-09-28 18:01:29', '0');
