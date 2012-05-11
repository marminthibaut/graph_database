

CREATE TABLE `region` 
(
  `nom_region` varchar(64), 
  `sit_geo` varchar(128)
);

CREATE TABLE `departement` 
(
  `num_dept` varchar(3),
  `nom_dept` varchar(64), 
  `nbr_habs` integer,
  `region` varchar(64)
);

CREATE TABLE `ville` 
(
  `nom_ville` varchar(64), 
  `nbre_habitants` integer,
  `temp_moyenne` integer,
  `dept` varchar(3)
);


ALTER TABLE `region`
  ADD PRIMARY KEY (`nom_region`);
  
ALTER TABLE `departement`
  ADD CONSTRAINT `pk_dept` PRIMARY KEY (`num_dept`),
  ADD CONSTRAINT `fk_dept_reg` FOREIGN KEY (`region`) REFERENCES `region` (`nom_region`),
  ADD CONSTRAINT `ck_dept_nbr_habs` CHECK (`nbr_habitants` >= 0);
  
ALTER TABLE `ville`
  ADD CONSTRAINT `pk_vil` PRIMARY KEY (`nom_ville`),
  ADD CONSTRAINT `fk_vil_reg` FOREIGN KEY (`dept`) REFERENCES `dept` (`num_dept`),
  ADD CONSTRAINT `ck_vil_nbr_habs` CHECK (`nbr_habs` >= 0);


