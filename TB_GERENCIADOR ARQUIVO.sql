-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_gerenciador
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_gerenciador
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_gerenciador` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db_gerenciador` ;

-- -----------------------------------------------------
-- Table `db_gerenciador`.`tb_amigos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gerenciador`.`tb_amigos` (
  `ID_amigo` INT NOT NULL,
  `nome_amigo` VARCHAR(100) NOT NULL,
  `telefone` VARCHAR(15) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `quantidade_emprestimo` INT NOT NULL,
  PRIMARY KEY (`ID_amigo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gerenciador`.`tb_emprestimos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gerenciador`.`tb_emprestimos` (
  `ID_emprestimo` INT NOT NULL,
  `ID_amigo` INT NOT NULL,
  `ID_ferramenta` INT NOT NULL,
  `data_ocorreu` VARCHAR(10) NOT NULL,
  `data_devolucao` VARCHAR(10) NOT NULL,
  `status_emprestimo` TINYINT NOT NULL,
  PRIMARY KEY (`ID_emprestimo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_gerenciador`.`tb_ferramentas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_gerenciador`.`tb_ferramentas` (
  `ID_ferramenta` INT NOT NULL,
  `nome_ferramenta` VARCHAR(90) NOT NULL,
  `marca` VARCHAR(45) NOT NULL,
  `custo_aquisicao` DOUBLE NOT NULL,
  PRIMARY KEY (`ID_ferramenta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
