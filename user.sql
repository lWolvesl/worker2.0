SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `enable` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
