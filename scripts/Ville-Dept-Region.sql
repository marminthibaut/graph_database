CREATE TABLE Region (
	nom VARCHAR(50) NOT NULL,
	situationGeographique VARCHAR(50),
	PRIMARY KEY (nom)
);

CREATE TABLE Departement (
	num INT NOT NULL,
	nom VARCHAR(50) ,
	nbreHabitants INT,
	region VARCHAR(50),
	PRIMARY KEY (num),
	FOREIGN KEY (region) REFERENCES Region(nom)
);

CREATE TABLE Ville (
	nom VARCHAR(50) NOT NULL,
	nbreHabitants INT,
	tempMoyenne INT,
	departement VARCHAR(50),
	PRIMARY KEY (nom),
	FOREIGN KEY (departement) REFERENCES Departement(num)
);

