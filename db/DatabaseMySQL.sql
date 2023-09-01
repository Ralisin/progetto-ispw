-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema LittleFarmers
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `LittleFarmers` ;

-- -----------------------------------------------------
-- Schema LittleFarmers
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `LittleFarmers` DEFAULT CHARACTER SET utf8 ;
USE `LittleFarmers` ;

-- -----------------------------------------------------
-- Table `LittleFarmers`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LittleFarmers`.`users` ;

CREATE TABLE IF NOT EXISTS `LittleFarmers`.`users` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LittleFarmers`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LittleFarmers`.`company` ;

CREATE TABLE IF NOT EXISTS `LittleFarmers`.`company` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `iban` VARCHAR(34) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `emailFk`
    FOREIGN KEY (`email`)
    REFERENCES `LittleFarmers`.`users` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LittleFarmers`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LittleFarmers`.`products` ;

CREATE TABLE IF NOT EXISTS `LittleFarmers`.`products` (
  `companyEmail` VARCHAR(45) NOT NULL,
  `productId` INT NOT NULL AUTO_INCREMENT,
  `productName` VARCHAR(128) NOT NULL,
  `productDescription` TEXT NULL,
  `price` DECIMAL(5,2) UNSIGNED NOT NULL,
  `region` VARCHAR(45) NOT NULL,
  `category` VARCHAR(45) NULL,
  `imageLink` TEXT NULL,
  PRIMARY KEY (`productId`),
  INDEX `fk_products_company1_idx` (`companyEmail` ASC) VISIBLE,
  CONSTRAINT `fk_products_company1`
    FOREIGN KEY (`companyEmail`)
    REFERENCES `LittleFarmers`.`company` (`email`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LittleFarmers`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LittleFarmers`.`customer` ;

CREATE TABLE IF NOT EXISTS `LittleFarmers`.`customer` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `address` VARCHAR(128) NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `emailCustomer`
    FOREIGN KEY (`email`)
    REFERENCES `LittleFarmers`.`users` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LittleFarmers`.`cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LittleFarmers`.`cart` ;

CREATE TABLE IF NOT EXISTS `LittleFarmers`.`cart` (
  `customerEmail` VARCHAR(45) NOT NULL,
  `productId` INT NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`customerEmail`, `productId`),
  INDEX `fk_cart_products1_idx` (`productId` ASC) VISIBLE,
  INDEX `fk_cart_customer1_idx` (`customerEmail` ASC) VISIBLE,
  CONSTRAINT `fk_cart_products1`
    FOREIGN KEY (`productId`)
    REFERENCES `LittleFarmers`.`products` (`productId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_customer1`
    FOREIGN KEY (`customerEmail`)
    REFERENCES `LittleFarmers`.`customer` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LittleFarmers`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LittleFarmers`.`orders` ;

CREATE TABLE IF NOT EXISTS `LittleFarmers`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `companyEmail` VARCHAR(45) NOT NULL,
  `customerEmail` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `status` ENUM('waiting', 'accepted', 'denied', 'shipped', 'delivered') NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_requests_customer1_idx` (`customerEmail` ASC) VISIBLE,
  INDEX `fk_requests_company1_idx` (`companyEmail` ASC) VISIBLE,
  CONSTRAINT `fk_requests_customer1`
    FOREIGN KEY (`customerEmail`)
    REFERENCES `LittleFarmers`.`customer` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_requests_company1`
    FOREIGN KEY (`companyEmail`)
    REFERENCES `LittleFarmers`.`company` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LittleFarmers`.`orderItems`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LittleFarmers`.`orderItems` ;

CREATE TABLE IF NOT EXISTS `LittleFarmers`.`orderItems` (
  `orderId` INT NOT NULL,
  `productId` INT NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`orderId`, `productId`),
  INDEX `fk_requests_has_products_products1_idx` (`productId` ASC) VISIBLE,
  INDEX `fk_requests_has_products_requests1_idx` (`orderId` ASC) VISIBLE,
  CONSTRAINT `fk_requests_has_products_requests1`
    FOREIGN KEY (`orderId`)
    REFERENCES `LittleFarmers`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_requests_has_products_products1`
    FOREIGN KEY (`productId`)
    REFERENCES `LittleFarmers`.`products` (`productId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS login;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'login' IDENTIFIED BY 'login';

GRANT SELECT, INSERT ON TABLE `LittleFarmers`.`users` TO 'login';
SET SQL_MODE = '';
DROP USER IF EXISTS company;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'company' IDENTIFIED BY 'company';

GRANT SELECT, UPDATE ON TABLE `LittleFarmers`.`company` TO 'company';
GRANT DELETE, SELECT, UPDATE, INSERT ON TABLE `LittleFarmers`.`orders` TO 'company';
GRANT DELETE, INSERT, SELECT, UPDATE ON TABLE `LittleFarmers`.`products` TO 'company';
SET SQL_MODE = '';
DROP USER IF EXISTS customer;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'customer' IDENTIFIED BY 'customer';

GRANT SELECT, UPDATE ON TABLE `LittleFarmers`.`customer` TO 'customer';
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE `LittleFarmers`.`cart` TO 'customer';
GRANT SELECT ON TABLE `LittleFarmers`.`products` TO 'customer';
GRANT SELECT ON TABLE `LittleFarmers`.`company` TO 'customer';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `LittleFarmers`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `LittleFarmers`;
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company1@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company2@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('customer1@gmail.com', '0000', 'customer');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('customer2@gmail.com', '0000', 'customer');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company3@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company4@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company5@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company6@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company7@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company8@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company9@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company10@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company11@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company12@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company13@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company14@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company15@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company16@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company17@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company18@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company19@gmail.com', '0000', 'company');
INSERT INTO `LittleFarmers`.`users` (`email`, `password`, `role`) VALUES ('company20@gmail.com', '0000', 'company');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LittleFarmers`.`company`
-- -----------------------------------------------------
START TRANSACTION;
USE `LittleFarmers`;
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company1@gmail.com', 'company1', 'XX00X0000000000000000000001', 'via company 1');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company2@gmail.com', 'company2', 'XX00X0000000000000000000002', 'via company 2');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company3@gmail.com', 'company3', 'XX00X0000000000000000000003', 'via company 3');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company4@gmail.com', 'company4', 'XX00X0000000000000000000004', 'via company 4');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company5@gmail.com', 'company5', 'XX00X0000000000000000000005', 'via company 5');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company6@gmail.com', 'company6', 'XX00X0000000000000000000006', 'via company 6');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company7@gmail.com', 'company7', 'XX00X0000000000000000000007', 'via company 7');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company8@gmail.com', 'company8', 'XX00X0000000000000000000008', 'via company 8');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company9@gmail.com', 'company9', 'XX00X0000000000000000000009', 'via company 9');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company10@gmail.com', 'company10', 'XX00X0000000000000000000010', 'via company 10');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company11@gmail.com', 'company11', 'XX00X0000000000000000000011', 'via company 11');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company12@gmail.com', 'company12', 'XX00X0000000000000000000012', 'via company 12');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company13@gmail.com', 'company13', 'XX00X0000000000000000000013', 'via company 13');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company14@gmail.com', 'company14', 'XX00X0000000000000000000014', 'via company 14');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company15@gmail.com', 'company15', 'XX00X0000000000000000000015', 'via company 15');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company16@gmail.com', 'company16', 'XX00X0000000000000000000016', 'via company 16');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company17@gmail.com', 'company17', 'XX00X0000000000000000000017', 'via company 17');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company18@gmail.com', 'company18', 'XX00X0000000000000000000018', 'via company 18');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company19@gmail.com', 'company19', 'XX00X0000000000000000000019', 'via company 19');
INSERT INTO `LittleFarmers`.`company` (`email`, `name`, `iban`, `address`) VALUES ('company20@gmail.com', 'company20', 'XX00X0000000000000000000020', 'via company 20');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LittleFarmers`.`products`
-- -----------------------------------------------------
START TRANSACTION;
USE `LittleFarmers`;
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Macinato 500g', 'NULL', 6.90, 'Abruzzo', 'carne', 'https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/07/Carne-macinata.jpeg?fit=853%2C853&ssl=1');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Banane 800g', 'NULL', 3.50, 'Abruzzo', 'frutta', 'https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2023/05/banane-bio-fairtrade-1-e1683809595597.jpg?fit=1235%2C1235&ssl=1');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Arance 1Kg', 'NULL', 3.50, 'Abruzzo', 'frutta', 'https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/05/Arance-Navel-Lane-Late-e1651680132312.png?fit=1920%2C1920&ssl=1');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Fragole 300g', 'NULL', 3.50, 'Abruzzo', 'frutta', 'https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2021/03/Favetta-di-Terracina-IGP-1.jpg?w=425&ssl=1');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Fagiolini Corallo 500g', 'NULL', 3.90, 'Abruzzo', 'verdura', 'https://www.alpassofood.com/wp-content/uploads/2023/05/fagioli-corallo_1575x-e1622028739307-600x600-1.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Pomodoro Ciliegino 700g', 'NULL', 4.90, 'Abruzzo', 'verdura', 'https://www.alpassofood.com/wp-content/uploads/2023/05/Pomodoro-Ciliegino.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Zucchina 800g', 'NULL', 4.50, 'Abruzzo', 'verdura', 'https://www.alpassofood.com/wp-content/uploads/2023/05/zucchinaverde-e1683828512317.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Aglio 300g', 'NULL', 2.90, 'Abruzzo', 'verdura', 'https://www.alpassofood.com/wp-content/uploads/2022/04/Aglio-fresco-.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Cetriolo 600g', 'NULL', 2.50, 'Abruzzo', 'verdura', 'https://www.alpassofood.com/wp-content/uploads/2021/06/Cetrioli-Locali.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Melanzana 800g', 'NULL', 3.50, 'Abruzzo', 'verdura', 'https://www.alpassofood.com/wp-content/uploads/2021/06/MELANZANE-STRIATE.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Asparagi 400g', 'NULL', 5.50, 'Abruzzo', 'verdura', 'https://www.alpassofood.com/wp-content/uploads/2021/03/asperge-verte-bio-en-botte-de-500-g-e1648633379864-600x600.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, '4 Hamburger 550g', 'NULL', 8.50, 'Abruzzo', 'carne', 'https://www.alpassofood.com/wp-content/uploads/2022/07/Hamburger-600x600.jpeg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Straccetti 500g', 'NULL', 11.50, 'Abruzzo', 'carne', 'https://www.alpassofood.com/wp-content/uploads/2022/07/Straccetti.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Bistecca Fiorentina 1Kg', 'NULL', 38.00, 'Abruzzo', 'carne', 'https://www.alpassofood.com/wp-content/uploads/2020/11/bistecca-fiorentina-.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company1@gmail.com', 0, 'Ossobuco 500g', 'NULL', 7.50, 'Abruzzo', 'carne', 'https://www.alpassofood.com/wp-content/uploads/2022/07/ossobuco-600x600.jpeg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Salame Dei Monti Lattari 500g', 'NULL', 10, 'Campania', 'carne', 'https://www.specialitadallacampania.it/wp-content/uploads/2013/07/salame-dei-monti-lattari-san-giovanni-scaled.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Pancetta al pepe, finocchietto e alloro 1Kg', 'NULL', 15, 'Campania', 'carme', 'https://www.specialitadallacampania.it/wp-content/uploads/2013/07/pancetta_arrotolata_g.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Caciocavallo 600g', 'NULL', 11.7, 'Campania', 'formaggio', 'https://www.gustotosto.it/wp-content/uploads/2020/11/Caciocavallo_1800x800-600x600.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Cacioricotta del cilento 300g', 'NULL', 6, 'Campania', 'formaggio', 'https://www.gustotosto.it/wp-content/uploads/2020/11/Cacioricotta-Capra-Cilento-Slow-Food-800x800-1-600x600.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Calamarata 500g', 'NULL', 3.3, 'Campania', 'pasta', 'https://www.gustotosto.it/wp-content/uploads/2020/06/calamarata-2-600x600.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Conchiglione 500g', 'NULL', 3.3, 'Campania', 'pasta', 'https://www.gustotosto.it/wp-content/uploads/2023/02/CONCHIGLIONE-2--600x600.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Ditalini 500g', 'NULL', 3.3, 'Campania', 'pasta', 'https://www.gustotosto.it/wp-content/uploads/2023/02/DITALINI-2--600x600.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Ceci cilentani', 'NULL', 2.6, 'Campania', 'legumi', 'https://www.gustotosto.it/wp-content/uploads/2020/11/Ceci-Cilentani-1-600x600.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Soppressata Cilentana 250g', 'NULL', 7.22, 'Campania', 'carne', 'https://www.saporitipicicampani.it/683-thickbox_default/soppressata-cilentana-stagionata-250g-sottovuoto-stagionatura-45-giorni-salumi-tomeo.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Schiacciata piccante 300g', 'NULL', 7.28, 'Campania', 'carne', 'https://www.saporitipicicampani.it/796-thickbox_default/schiacciata-piccante-300g-sottovuoto-stagionatura-50-giorni-salumi-cembalo.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company4@gmail.com', 0, 'Salame Dolce 400g', 'NULL', 9.01, 'Campania', 'carne', 'https://www.saporitipicicampani.it/816-home_default/salame-dolce-400g-sottovuoto-stagionatura-50-giorni-salumi-cembalo.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company2@gmail.com', 0, 'Cicerchie Tostate 100g', 'NULL', 3.74, 'Basilicata', 'legumi', 'https://a9e3d6w3.rocketcdn.me/wp-content/uploads/2023/06/Cicerchie-tostate-OK-1.png');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company2@gmail.com', 0, 'Orechiette Senatore 500g', 'NULL', 3.85, 'Basilicata', 'pasta', 'https://a9e3d6w3.rocketcdn.me/wp-content/uploads/2023/06/orecchiette-senatore-cappelli-PNG.png');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company2@gmail.com', 0, 'Scamorza Podolica 300g', 'NULL', 8.98, 'Basilicata', 'formaggio', 'https://a9e3d6w3.rocketcdn.me/wp-content/webp-express/webp-images/uploads/2022/02/scamorza-podolica-1-291x291.jpg.webp');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company3@gmail.com', 0, 'Lumaconi 500g', 'NULL', 3.5, 'Calabria', 'pasta', 'https://www.sibarizia.it/wp-content/uploads/2019/05/6B4E9641-5602-4700-9E2A-070ECFA4056C.png');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company3@gmail.com', 0, 'Pennoni 500g', 'NULL', 3.5, 'Calabria', 'pasta', 'https://www.sibarizia.it/wp-content/uploads/2019/05/13EDBA2C-C028-4F80-89FB-D2C7187FC0DE.png');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company3@gmail.com', 0, 'Cipolla di tropea 2Kg', 'NULL', 8.91, 'Calabria', 'verdura', 'https://www.sibarizia.it/wp-content/uploads/2020/05/5631D2C2-00AC-4155-A039-A0FE23309D4C.jpeg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company3@gmail.com', 0, 'Olive Arriganate 500g', 'NULL', 4.5, 'Calabria', 'verdura', 'https://www.sibarizia.it/wp-content/uploads/2020/11/Progetto-senza-titolo-6.png');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company3@gmail.com', 0, 'Nduja in budello 500g', 'NULL', 11.9, 'Calabria', 'carne', 'https://www.sibarizia.it/wp-content/uploads/2019/06/3C4EC7D3-2809-423C-B00D-398E4B042D95.png');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company5@gmail.com', 0, 'Prosciutto di parma 10Kg', 'NULL', 322.61, 'Emilia Romagna', 'carne', 'https://www.saporitipiciemiliani.it/281-home_default/prosciutto-di-parma-dop-con-osso-cantina-piu-di-24-lune-10-kg-stagionato-24-mesi-devodier.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company5@gmail.com', 0, 'Culatta Emilia 4,5Kg', 'NULL', 192.68, 'Emilia Romagna', 'formaggio', 'https://www.saporitipiciemiliani.it/295-home_default/culatta-emilia-45-kg-con-anchetta-stagionato-16-mesi-devodier.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company5@gmail.com', 0, 'Parmigiano Reggiano 1Kg', 'NULL', 46.78, 'Emilia Romagna', 'formaggio', 'https://www.foodexplore.com/media/catalog/product/cache/cfb506310985da6e6b5b75be9be619ee/p/a/parmigiano-reggiano-vacche-rosse-24-mesi_2.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company5@gmail.com', 0, 'Ciliegia di Vignola 1Kg', 'NULL', 8.99, 'Emilia Romagna', 'frutta', 'https://i0.wp.com/www.fruitbookmagazine.it/wp-content/uploads/2022/05/ciliegie-vignola-bauletto.jpg?w=1200&ssl=1');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company5@gmail.com', 0, 'Mandorla piacentina 1Kg', 'NULL', 19.9, 'Emilia Romagna', 'frutta', 'https://cdn-gcpni.nitrocdn.com/nmRwbUmgqvOQBClindfmmDUibaNMeLWx/assets/images/optimized/rev-1875a14/image/cache/catalog/produttori/00082/00082_sof60_20220427213902657863-550x550h.webp');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company6@gmail.com', 0, 'Cotechino 950g', 'NULL', 13.9, 'Friuli Venezia Giulia', 'carne', 'https://www.latuabottegaitaliana.it/wp-content/uploads/2022/03/cotechino.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company7@gmail.com', 0, 'Pecorino romano 3,5Kg', 'NULL', 152.88, 'Lazio', 'formaggio', 'https://www.saporitipicilaziali.it/432-home_default/pecorino-romano-dop-in-ottavi-sv-33-35-kg-caseificio-storico-amatrice.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company8@gmail.com', 0, 'Olive tagiasche in salamoia 180g', 'NULL', 3, 'Liguria', 'verdura', 'https://www.gustotosto.it/wp-content/uploads/2020/07/olive_salamoia.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company9@gmail.com', 0, 'Bresaola di Limousine 1Kg', 'NULL', 49.01, 'Lombardia', 'carne', 'https://www.saporitipicilombardi.it/1410-thickbox_default/bresaola-di-limousine-valchiavenna-artigianale-sottovuoto-trancio-1kg-stagionatura-35gg-brisval-bresaole-carni-pregiate.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company10@gmail.com', 0, 'Salsiccia Notrana 600g', 'NULL', 8, 'Marche', 'carne', 'https://www.marcheintavola.com/953-medium_default/salsicce-stagionate.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company11@gmail.com', 0, 'Ventricina Piccante 600g', 'NULL', 11.9, 'Molise', 'carne', 'https://cantinamolisana.it/wp-content/uploads/2021/04/Ventricina-Piccante.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company12@gmail.com', 0, 'Caciotta caprina 750g', 'NULL', 21.5, 'Piemonte', 'formaggio', 'https://www.foodexplore.com/media/catalog/product/cache/22b265966495d7bb7934212431ff990f/c/a/caciotta-caprina-botalla_2.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company13@gmail.com', 0, 'Taralli 500g', 'NULL', 3.01, 'Puglia', 'pasta', 'https://shop.laterradipuglia.it/2565-large_default/taralli-alle-olive-leccine-e-capperi.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company14@gmail.com', 0, 'Pistoccu 500g', 'NULL', 3.6, 'Sardegna', 'pasta', 'https://cuordisardegna.com/2578-large_default/pistoccu-integrale-di-villagrande-strisaili-demurtas.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company15@gmail.com', 0, 'Primo sale 500g', 'NULL', 6.9, 'Sicilia', 'formaggio', 'https://www.dallasicilia.com/wp-content/uploads/Primo-Sale-Caseificio-Puzzillo-300x300.png');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company16@gmail.com', 0, 'Linguine 250g', 'NULL', 4.12, 'Toscana', 'pasta', 'https://www.saporitipicitoscani.it/93-home_default/zafferano-linguine-lorenzo-il-magnifico-250-gr-pasta-aromatizzata-antico-pastificio-morelli.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company17@gmail.com', 0, 'Prosciutto di cervo 400g', 'NULL', 36.5, 'Trentino Alto Adige', 'carne', 'https://www.foodexplore.com/media/catalog/product/cache/22b265966495d7bb7934212431ff990f/p/r/prosciutto-di-cervo-vontavon.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company18@gmail.com', 0, 'Tartufo estivo 500g', 'NULL', 176, 'Umbria', 'frutta', 'https://www.saporitipiciumbri.it/277-thickbox_default/tartufo-estivo-intero-congelato-500-g-tartufi-alfonso-fortunati.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company19@gmail.com', 0, 'Fontina 200g', 'NULL', 4.57, 'Val d\'Aosta', 'formaggio', 'https://www.saporitipicivaldostani.it/123-thickbox_default/fontina-dop-porzionata-200g-peso-variabile-200-g-caseificio-artigiano-variney-elisei-duclos.jpg');
INSERT INTO `LittleFarmers`.`products` (`companyEmail`, `productId`, `productName`, `productDescription`, `price`, `region`, `category`, `imageLink`) VALUES ('company20@gmail.com', 0, 'Bastardo del grappa 3Kg', 'NULL', 46.8, 'Veneto', 'formaggio', 'https://www.saporitipicivaldostani.it/123-thickbox_default/fontina-dop-porzionata-200g-peso-variabile-200-g-caseificio-artigiano-variney-elisei-duclos.jpg');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LittleFarmers`.`customer`
-- -----------------------------------------------------
START TRANSACTION;
USE `LittleFarmers`;
INSERT INTO `LittleFarmers`.`customer` (`email`, `name`, `surname`, `address`) VALUES ('customer1@gmail.com', NULL, NULL, 'via customer 1');
INSERT INTO `LittleFarmers`.`customer` (`email`, `name`, `surname`, `address`) VALUES ('customer2@gmail.com', NULL, NULL, 'via customer 2');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LittleFarmers`.`orders`
-- -----------------------------------------------------
START TRANSACTION;
USE `LittleFarmers`;
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (33, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-23', 'accepted');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (34, 'company3@gmail.com', 'customer1@gmail.com', '2023-08-23', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (35, 'company4@gmail.com', 'customer1@gmail.com', '2023-08-23', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (36, 'company2@gmail.com', 'customer1@gmail.com', '2023-08-23', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (37, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-23', 'denied');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (38, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-23', 'denied');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (39, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-24', 'accepted');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (40, 'company5@gmail.com', 'customer1@gmail.com', '2023-08-24', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (41, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-24', 'accepted');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (42, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-27', 'accepted');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (43, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-27', 'shipped');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (44, 'company6@gmail.com', 'customer1@gmail.com', '2023-08-27', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (45, 'company20@gmail.com', 'customer1@gmail.com', '2023-08-27', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (46, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-27', 'denied');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (47, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-27', 'accepted');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (48, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-29', 'accepted');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (49, 'company1@gmail.com', 'customer1@gmail.com', '2023-08-31', 'accepted');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (50, 'company5@gmail.com', 'customer1@gmail.com', '2023-08-31', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (51, 'company2@gmail.com', 'customer1@gmail.com', '2023-08-31', 'waiting');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (52, 'company1@gmail.com', 'customer1@gmail.com', '2023-09-01', 'denied');
INSERT INTO `LittleFarmers`.`orders` (`id`, `companyEmail`, `customerEmail`, `date`, `status`) VALUES (53, 'company1@gmail.com', 'customer1@gmail.com', '2023-09-01', 'accepted');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LittleFarmers`.`orderItems`
-- -----------------------------------------------------
START TRANSACTION;
USE `LittleFarmers`;
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (33, 1, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (34, 30, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (34, 31, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (35, 16, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (36, 27, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (37, 1, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (38, 1, 9);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (39, 1, 2);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (40, 35, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (40, 36, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (41, 1, 2);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (41, 2, 2);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (41, 3, 2);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (41, 4, 2);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (42, 11, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (42, 12, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (42, 15, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (43, 1, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (43, 2, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (43, 3, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (44, 40, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (45, 54, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (46, 15, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (47, 1, 7);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (48, 1, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (48, 2, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (48, 3, 5);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (49, 1, 3);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (50, 35, 3);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (51, 27, 2);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (52, 1, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (52, 2, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (52, 3, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (53, 1, 1);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (53, 2, 2);
INSERT INTO `LittleFarmers`.`orderItems` (`orderId`, `productId`, `quantity`) VALUES (53, 3, 1);

COMMIT;

