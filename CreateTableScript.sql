CREATE TABLE `groupprojectdb`.`location` (
    `locationId` INT NOT NULL,
    `city` VARCHAR(50) NULL,
    `province` VARCHAR(50) NULL,
    `street` VARCHAR(100) NULL,
    `zip` VARCHAR(6) NULL,
    PRIMARY KEY (`locationId`)
);

CREATE TABLE `groupprojectdb`.`bloodbank` (
  `bloodbankId` INT NOT NULL,
  `name` VARCHAR(50) NULL,
  `locationId` INT NOT NULL,
  `capacity` INT NULL,
  PRIMARY KEY (`bloodbankId`));

CREATE TABLE `groupprojectdb`.`bloodtype` (
  `bloodtypeId` INT NOT NULL,
  `type` VARCHAR(3) NULL, 
  `group` VARCHAR(2) NULL,
  PRIMARY KEY (`bloodtypeId`));


CREATE TABLE `groupprojectdb`.`donor` (
  `donorId` INT NOT NULL,
  `firstName` VARCHAR(50) NULL,
  `lastName` VARCHAR(50) NULL,
  `age` INT UNSIGNED NULL,
  `bloodtypeId` INT NOT NULL,
  `bloodType` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`donorId`));

CREATE TABLE `groupprojectdb`.`donation` (
  `donationId` INT NOT NULL,
  `date` DATE NULL,
  `donorId` INT NOT NULL,
  `bloodbankId` INT NOT NULL,
  PRIMARY KEY (`donationId`));
