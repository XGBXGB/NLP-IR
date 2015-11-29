SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema informationretrieval
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `informationretrieval` DEFAULT CHARACTER SET utf8 ;
USE `informationretrieval` ;

-- -----------------------------------------------------
-- Table `informationretrieval`.`files`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `informationretrieval`.`files` ;

CREATE TABLE IF NOT EXISTS `informationretrieval`.`files` (
  `fileId` INT(11) NOT NULL AUTO_INCREMENT,
  `fileName` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`fileId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `informationretrieval`.`words`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `informationretrieval`.`words` ;

CREATE TABLE IF NOT EXISTS `informationretrieval`.`words` (
  `wordId` INT(11) NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(45) NOT NULL,
  `inverseDocFreq` DOUBLE NOT NULL DEFAULT '1',
  PRIMARY KEY (`wordId`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `informationretrieval`.`wordsfiles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `informationretrieval`.`wordsfiles` ;

CREATE TABLE IF NOT EXISTS `informationretrieval`.`wordsfiles` (
  `wordId` INT(11) NOT NULL,
  `fileId` INT(11) NOT NULL,
  `termFreq` DOUBLE NOT NULL DEFAULT '1',
  `score` DOUBLE NOT NULL DEFAULT '1',
  INDEX `wf wordId_idx` (`wordId` ASC),
  INDEX `wf fileId_idx` (`fileId` ASC),
  CONSTRAINT `wf fileId`
    FOREIGN KEY (`fileId`)
    REFERENCES `informationretrieval`.`files` (`fileId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `wf wordId`
    FOREIGN KEY (`wordId`)
    REFERENCES `informationretrieval`.`words` (`wordId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
