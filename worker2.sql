SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for master
-- ----------------------------
DROP TABLE IF EXISTS `master`;
CREATE TABLE `master` (
  `mailHost` varchar(255) NOT NULL, -- 邮箱服务器地址 例：smtp.163.com
  `mailServer` varchar(255) NOT NULL, -- 管理员匹配邮箱地址
  `mailAuth` varchar(255) NOT NULL, -- 默认为true
  `mailPassword` varchar(255) NOT NULL, -- 邮箱密码（授权码）
  `mailRemind` varchar(255) DEFAULT NULL, -- 管理员提示邮箱（个人邮箱）
  PRIMARY KEY (`mailServer`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `name` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `status` int(255) NOT NULL,
  `remaker` varchar(255) DEFAULT NULL,
  `recordID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`recordID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8;

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
  `isInschool` int(11) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `enable` int(11) NOT NULL DEFAULT '1', -- 是否启用此用户的自动打卡
  `sc` int(11) NOT NULL DEFAULT '0', -- 是否启用截图功能
  `host` varchar(255) NOT NULL, -- 打卡页面地址
  `StudentID` varchar(255) NOT NULL, -- 学号
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
