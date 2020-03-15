/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : c-cloud

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2020-03-16 01:22:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) NOT NULL,
  `phone` varchar(32) NOT NULL COMMENT '买家电话',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态 默认0 新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态 默认0未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `reason` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of order_master
-- ----------------------------

-- ----------------------------
-- Table structure for pay_info
-- ----------------------------
DROP TABLE IF EXISTS `pay_info`;
CREATE TABLE `pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(32) NOT NULL,
  `status` tinyint(3) NOT NULL,
  `amount` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pay_info
-- ----------------------------

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名称',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uqe_category_type` (`category_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='类目表';

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('1', '电影', '1001', '2020-03-14 14:32:37', '2020-03-14 14:32:37');
INSERT INTO `product_category` VALUES ('2', '图片', '1002', '2020-03-14 14:32:51', '2020-03-14 14:32:51');
INSERT INTO `product_category` VALUES ('3', '音乐', '1003', '2020-03-14 14:33:00', '2020-03-14 14:33:00');
INSERT INTO `product_category` VALUES ('4', '小说', '1004', '2020-03-14 14:33:10', '2020-03-14 14:33:10');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `seller_id` varchar(32) DEFAULT NULL COMMENT '卖家id',
  `product_name` varchar(64) NOT NULL,
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(512) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `product_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态 0正常 1下架',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('1', '1', '星际穿越', '100.00', '1000', '近未来的地球黄沙遍野，小麦、秋葵等基础农作物相继因枯萎病灭绝，人类不再像从前那样仰望星空，放纵想象力和灵感的迸发，而是每日在沙尘暴的肆虐下倒数着所剩不多的光景。在家务农的前NASA宇航员库珀（马修·麦康纳 Matthew McConaughey 饰）接连在女儿墨菲（麦肯吉·弗依 Mackenzie Foy 饰）的书房发现奇怪的重力场现象，随即得知在某个未知区域内前NASA成员仍秘密进行一个拯救人类的计划。多年以前土星附近出现神秘虫洞，NASA借机将数名宇航员派遣到遥远的星系寻找适合居住的星球。在布兰德教授（迈克尔·凯恩 Michael Caine 饰）的劝说下，库珀忍痛告别了女儿，和其他三名专家教授女儿艾米莉亚·布兰德（安妮·海瑟薇 Anne Hathaway 饰）、罗米利（大卫·吉雅西 David Gyasi 饰）、多伊尔（韦斯·本特利 Wes B', 'https://img1.doubanio.com/view/photo/m/public/p2208323529.webp', '1001', '2020-03-14 14:35:25', '2020-03-15 21:29:30', '0');
INSERT INTO `product_info` VALUES ('2', '2', '速度与激情8', '100.00', '1000', '多米尼克（范·迪塞尔 Vin Diesel 饰）与莱蒂（米歇尔·罗德里格兹 Michelle Rodriguez 饰）共度蜜月，布莱恩与米娅退出了赛车界，这支曾环游世界的顶级飞车家族队伍的生活正渐趋平淡。然而，一位神秘女子Cipher（查理兹·塞隆 Charlize T heron 饰）的出现，令整个队伍卷入信任与背叛的危机，面临前所未有的考验', 'https://img3.doubanio.com/view/photo/l/public/p2450800270.webp', '1001', '2020-03-14 14:35:25', '2020-03-15 21:29:01', '0');

-- ----------------------------
-- Table structure for product_lock
-- ----------------------------
DROP TABLE IF EXISTS `product_lock`;
CREATE TABLE `product_lock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(32) NOT NULL,
  `phone` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key_product_phone` (`product_id`,`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of product_lock
-- ----------------------------

-- ----------------------------
-- Table structure for seller_info
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info` (
  `seller_id` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`seller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卖家信息表';

-- ----------------------------
-- Records of seller_info
-- ----------------------------
INSERT INTO `seller_info` VALUES ('1', '导演-张三', '2020-03-14 14:39:01', '2020-03-14 14:39:01', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `seller_info` VALUES ('2', '音乐家-李宗盛', '2020-03-14 14:41:09', '2020-03-14 14:41:09', 'E10ADC3949BA59ABBE56E057F20F883E');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(32) NOT NULL,
  `username` varchar(32) DEFAULT '',
  `password` varchar(32) DEFAULT '',
  `deposit` decimal(10,2) NOT NULL DEFAULT '1000.00' COMMENT '用户余额',
  `phone` varchar(32) NOT NULL COMMENT '1 买家 2 卖家',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '彭雄峰', 'E10ADC3949BA59ABBE56E057F20F883E', '1000.00', '18143558715', '2020-03-13 00:44:05', '2020-03-16 01:21:40');
