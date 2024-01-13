ALTER TABLE `groupprojectdb`.`bloodbank` 
ADD INDEX `locationId_idx` (`locationId` ASC) VISIBLE;
;
ALTER TABLE `groupprojectdb`.`bloodbank` 
ADD CONSTRAINT `locationId`
  FOREIGN KEY (`locationId`)
  REFERENCES `groupprojectdb`.`location` (`locationId`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `groupprojectdb`.`donation` 
ADD INDEX `donorId_idx` (`donorId` ASC) VISIBLE,
ADD INDEX `bloodbankId_idx` (`bloodbankId` ASC) VISIBLE;
;
ALTER TABLE `groupprojectdb`.`donation` 
ADD CONSTRAINT `donorId`
  FOREIGN KEY (`donorId`)
  REFERENCES `groupprojectdb`.`donor` (`donorId`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `bloodbankId`
  FOREIGN KEY (`bloodbankId`)
  REFERENCES `groupprojectdb`.`bloodbank` (`bloodbankId`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `groupprojectdb`.`donor` 
ADD INDEX `bloodtypeId_idx` (`bloodtypeId` ASC) VISIBLE;
;
ALTER TABLE `groupprojectdb`.`donor` 
ADD CONSTRAINT `bloodtypeId`
  FOREIGN KEY (`bloodtypeId`)
  REFERENCES `groupprojectdb`.`bloodtype` (`bloodtypeId`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
