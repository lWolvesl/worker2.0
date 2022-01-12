SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `master`;
CREATE TABLE `master` (
  `mailHost` varchar(255) NOT NULL,
  `mailServer` varchar(255) NOT NULL,
  `mailAuth` varchar(255) NOT NULL,
  `mailPassword` varchar(255) NOT NULL,
  PRIMARY KEY (`mailServer`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
