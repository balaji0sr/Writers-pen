


CREATE DATABASE WritersPen;

CREATE TABLE `WritersPen`.`Comment` (
  `idcomment` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `storyid` BIGINT(20) NOT NULL,
  `userid` BIGINT(20) NOT NULL,
  `name` VARCHAR(75) NOT NULL,
  `comment` LONGTEXT NULL,
  `updatetime` BIGINT(20) NOT NULL,
  PRIMARY KEY (`idcomment`));

CREATE TABLE `WritersPen`.`Likes` (
  `likeid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `storyid` BIGINT(20) NOT NULL,
  `userid` BIGINT(20) NOT NULL,
  `status` TINYINT(3) NULL,
  PRIMARY KEY (`likeid`));
  
  CREATE TABLE `WritersPen`.`Story` (
  `storyid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `userid` BIGINT(20) NOT NULL,
  `username` VARCHAR(75) NOT NULL,
  `title` TEXT(2000) NULL,
  `content` LONGTEXT NULL,
  `type` TINYINT(4) NOT NULL,
  `parentstoryid` BIGINT(20) NOT NULL,
  `updatetime` BIGINT(20) NOT NULL,
  `modifiedtime` BIGINT(20) NOT NULL,
  `genreid` INT(11) NULL,
  `likecount` BIGINT(20) NOT NULL,
  `viewcount` BIGINT(20) NOT NULL,
  PRIMARY KEY (`storyid`));

  CREATE TABLE `WritersPen`.`users` (
  `userid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(75) NOT NULL,
  `mail` VARCHAR(250) NOT NULL,
  `pas` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`userid`));


