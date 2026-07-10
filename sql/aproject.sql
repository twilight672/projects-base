/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : aproject

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2024-03-11 19:39:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dingdan`
-- ----------------------------
DROP TABLE IF EXISTS `dingdan`;
CREATE TABLE `dingdan` (
  `id` int(11) NOT NULL auto_increment,
  `dingdanriqi` varchar(200) default NULL,
  `dingdanzhuangtai` varchar(200) default NULL,
  `heji` varchar(200) default NULL,
  `zhifuzhuangtai` varchar(200) default NULL,
  `dingdanbianhao` varchar(200) default NULL,
  `maijia` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dingdan
-- ----------------------------
INSERT INTO `dingdan` VALUES ('1', '2024-03-11', '已提交', '总金额:55.0 共2件商品', '已支付', 'c509adf9361a4693b49887354e6d9512', '123');

-- ----------------------------
-- Table structure for `dingdanmingxi`
-- ----------------------------
DROP TABLE IF EXISTS `dingdanmingxi`;
CREATE TABLE `dingdanmingxi` (
  `id` int(11) NOT NULL auto_increment,
  `dingdanbianhao` varchar(200) default NULL,
  `jiage` varchar(200) default NULL,
  `shangpin` varchar(200) default NULL,
  `shuliang` varchar(200) default NULL,
  `zongjia` varchar(200) default NULL,
  `shangpinid` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dingdanmingxi
-- ----------------------------
INSERT INTO `dingdanmingxi` VALUES ('1', 'c509adf9361a4693b49887354e6d9512', '33', 'AAA', '1', '33.0', '1');
INSERT INTO `dingdanmingxi` VALUES ('2', 'c509adf9361a4693b49887354e6d9512', '22', 'CCC', '1', '22.0', '3');

-- ----------------------------
-- Table structure for `kucun`
-- ----------------------------
DROP TABLE IF EXISTS `kucun`;
CREATE TABLE `kucun` (
  `id` int(11) NOT NULL auto_increment,
  `shangpinming` varchar(200) default NULL,
  `kucunliang` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kucun
-- ----------------------------
INSERT INTO `kucun` VALUES ('1', 'AAA', '333');
INSERT INTO `kucun` VALUES ('2', 'CCC', '5454');

-- ----------------------------
-- Table structure for `shangpin`
-- ----------------------------
DROP TABLE IF EXISTS `shangpin`;
CREATE TABLE `shangpin` (
  `id` int(11) NOT NULL auto_increment,
  `shangpinming` varchar(200) default NULL,
  `jiage` varchar(200) default NULL,
  `shangpinmiaoshu` varchar(200) default NULL,
  `shangpindalei` varchar(200) default NULL,
  `tupian` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shangpin
-- ----------------------------
INSERT INTO `shangpin` VALUES ('1', 'AAA', '33', 'ddd', '面包', '2.png');
INSERT INTO `shangpin` VALUES ('2', 'BBBB', '444', 'FASDFA', '日用品', 'cd.png');
INSERT INTO `shangpin` VALUES ('3', 'CCC', '22', 'ASDFAS', '蛋糕', 'cefd7b27-5e2e-4c18-9e06-01cd021d75bc.jpg');

-- ----------------------------
-- Table structure for `shangpindalei`
-- ----------------------------
DROP TABLE IF EXISTS `shangpindalei`;
CREATE TABLE `shangpindalei` (
  `id` int(11) NOT NULL auto_increment,
  `leibieming` varchar(200) default NULL,
  `zhanshitupian` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shangpindalei
-- ----------------------------
INSERT INTO `shangpindalei` VALUES ('10', '蛋糕', '90c3ce34-13d9-489a-b4cd-f915a35cc5b9.png');
INSERT INTO `shangpindalei` VALUES ('11', '面包', 'bb6649bd-565e-494f-b0a0-fcbc931b6b0c.png');
INSERT INTO `shangpindalei` VALUES ('12', '冰激凌', 'a1201511-b8f7-4fd5-9a6a-3c386586e6b3.png');
INSERT INTO `shangpindalei` VALUES ('13', '咖啡下午茶', '308cca38-9154-4724-b4e9-2d40c883e554.png');
INSERT INTO `shangpindalei` VALUES ('14', '日用品', 'cd.png');

-- ----------------------------
-- Table structure for `yonghu`
-- ----------------------------
DROP TABLE IF EXISTS `yonghu`;
CREATE TABLE `yonghu` (
  `id` int(11) NOT NULL auto_increment,
  `yonghuming` varchar(200) default NULL,
  `mima` varchar(200) default NULL,
  `jiaose` varchar(200) default NULL,
  `dianhua` varchar(200) default NULL,
  `dizhi` varchar(200) default NULL,
  `xingming` varchar(200) default NULL,
  `youbian` varchar(200) default NULL,
  `youjian` varchar(200) default NULL,
  `touxiang` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yonghu
-- ----------------------------
INSERT INTO `yonghu` VALUES ('1', 'sa', '123', '管理员', '', '', '', '', '', '');
INSERT INTO `yonghu` VALUES ('2', '123', '123', '普通用户', 'null', 'null', 'null', 'null', '123@126.com', 'null');
