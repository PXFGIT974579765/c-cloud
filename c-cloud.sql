/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : c-cloud

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2020-03-14 23:14:40
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

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
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
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
INSERT INTO `product_info` VALUES ('1', '1', '星际穿越', '100.00', '1000', '2019星际穿越，请购买版权', 'https://img1.doubanio.com/view/photo/m/public/p2208323529.webp', '1001', '2020-03-14 14:35:25', '2020-03-14 14:42:37', '0');
INSERT INTO `product_info` VALUES ('2', '2', '山丘', '100.00', '1000', '李宗盛山丘', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584178265336&di=cf021b5e0d6b5fa8e50a923414f84590&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017eb858327452a8012060c8c6a2ec.jpg%401280w_1l_2o_100sh.jpg', '1003', '2020-03-14 14:35:25', '2020-03-14 14:43:21', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

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
INSERT INTO `user_info` VALUES ('1', '彭雄峰', 'E10ADC3949BA59ABBE56E057F20F883E', '900.00', '18143558715', '2020-03-13 00:44:05', '2020-03-14 21:50:46');
