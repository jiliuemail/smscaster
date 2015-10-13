
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema smsdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema smsdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `smsdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `smsdb` ;

-- -----------------------------------------------------
-- Table `smsdb`.`T_USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smsdb`.`T_USER` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USER_NAME` VARCHAR(50) NOT NULL COMMENT '姓名',
  `NICK_NAME` VARCHAR(50) NULL COMMENT '昵称',
  `NUMBER` VARCHAR(100) NOT NULL COMMENT '电话号码、手机号码',
  `USER_TYPE` VARCHAR(20) NULL COMMENT '用户类型',
  `RECEIVE` INT NOT NULL COMMENT '是否接收短信,0：不接收，1：接收',
  `CREATE_DATE` DATETIME NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smsdb`.`T_GROUP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smsdb`.`T_GROUP` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `GROUP_NAME` VARCHAR(50) NOT NULL COMMENT '群组名称',
  `GROUP_TYPE` VARCHAR(20) NULL COMMENT '群组类型',
  `RECEIVE` INT NOT NULL COMMENT '是否接收短信，0：不接收，1：接收',
  PRIMARY KEY (`ID`)  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smsdb`.`T_USER_GROUP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smsdb`.`T_USER_GROUP` (
  `T_USER_ID` INT NOT NULL ,
  `T_GROUP_ID` INT NOT NULL ,
  PRIMARY KEY (`T_USER_ID`, `T_GROUP_ID`)  ,
  INDEX `fk_T_USER_has_T_GROUP_T_GROUP1_idx` (`T_GROUP_ID` ASC)  ,
  CONSTRAINT `fk_T_USER_has_T_GROUP_T_USER`
    FOREIGN KEY (`T_USER_ID`)
    REFERENCES `smsdb`.`T_USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_USER_has_T_GROUP_T_GROUP1`
    FOREIGN KEY (`T_GROUP_ID`)
    REFERENCES `smsdb`.`T_GROUP` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smsdb`.`T_MESSAGE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smsdb`.`T_MESSAGE` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `CONTACT_ID` INT NULL COMMENT '用户ID或者群组ID',
  `CONTACT_NAME` VARCHAR(50) NULL COMMENT '用户或者群组名称',
  `CONTACT_TYPE` INT NOT NULL COMMENT '单发或者群发,0：单发，1：群发（默认单发）',
  `MESSAGE` VARCHAR(30) NOT NULL COMMENT '短信内容',
  `PORT_NAME` VARCHAR(50) NULL COMMENT '端口名称',
  `PORT_NUMBER` VARCHAR(30) NULL COMMENT '端口号码',
  PRIMARY KEY (`ID`)  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smsdb`.`T_MESSAGE_SENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smsdb`.`T_MESSAGE_SENT` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `CONTACT_ID` INT NOT NULL COMMENT '用户ID或者群组ID',
  `CONTACT_NAME` VARCHAR(50) NOT NULL COMMENT '用户或者群组名称',
  `CONTACT_TYPE` INT NOT NULL COMMENT '单发或者群发，0：单发，1：群发',
  `MESSAGE` VARCHAR(30) NOT NULL COMMENT '短信内容',
  `PORT_NAME` VARCHAR(50) NOT NULL COMMENT '端口名称',
  `PORT_NUMBER` VARCHAR(30) NULL COMMENT '端口号码',
  `SENT_DATE` DATETIME NOT NULL COMMENT '端口号码',
  `STATUS` INT NOT NULL COMMENT '发送状态，0：未发送，1：发送成功，2：发送失败',
  `CREATE_DATE` DATETIME NOT NULL COMMENT '创建时间',
  `UPDATE_DATE` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smsdb`.`T_MESSAGE_RECEIVE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smsdb`.`T_MESSAGE_RECEIVE` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `CONTACT_ID` INT NULL COMMENT '用户ID或者群组ID',
  `CONTACT_NAME` VARCHAR(50) NULL COMMENT '用户或者群组名称',
  `CONTACT_NUMBER` VARCHAR(30) NOT NULL COMMENT '发送者号码',
  `MESSAGE` VARCHAR(30) NOT NULL COMMENT '短信内容',
  `PORT_NAME` VARCHAR(50) NOT NULL COMMENT '端口名称',
  `PORT_NUMBER` VARCHAR(30) NULL COMMENT '端口号码',
  `REPLY_STATUS` INT NOT NULL COMMENT '回复状态，0：未回复，1：已回复，2：不用回复',
  `CREATE_DATE` DATETIME NOT NULL COMMENT '创建时间',
  `UPDATE_DATE` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)  )
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
