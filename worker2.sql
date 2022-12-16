/*
 Navicat Premium Data Transfer

 Source Server         : tx
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : tx.wolves.top:3306
 Source Schema         : worker2

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 16/12/2022 11:07:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for master
-- ----------------------------
DROP TABLE IF EXISTS `master`;
CREATE TABLE `master` (
  `mailHost` varchar(255) NOT NULL,
  `mailServer` varchar(255) NOT NULL,
  `mailAuth` varchar(255) NOT NULL,
  `mailPassword` varchar(255) NOT NULL,
  `mailRemind` varchar(255) DEFAULT NULL,
  `startTime` int DEFAULT NULL,
  `endTime` int DEFAULT NULL,
  PRIMARY KEY (`mailServer`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `name` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `status` int NOT NULL,
  `remaker` varchar(255) DEFAULT NULL,
  `recordID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`recordID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1349 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(255) NOT NULL,
  `cookieName` varchar(255) NOT NULL DEFAULT 'remember_student_59ba36addc2b2f9401580f014c7f58ea4e30989d',
  `cookieValue` varchar(255) NOT NULL,
  `personalPhone` varchar(255) NOT NULL,
  `emergency` varchar(255) NOT NULL,
  `emergencyPhone` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `isInschool` int NOT NULL,
  `mail` varchar(255) NOT NULL,
  `enable` int NOT NULL DEFAULT '1',
  `sc` int NOT NULL DEFAULT '0',
  `host` varchar(255) NOT NULL,
  `StudentID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `schoolLocation` varchar(255) DEFAULT '河南省,新乡市,牧野区,求知路河南师范大学(东区)|35.32802,113.92183',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

SET FOREIGN_KEY_CHECKS = 1;
