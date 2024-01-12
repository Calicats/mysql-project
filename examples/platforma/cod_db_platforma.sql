CREATE DATABASE IF NOT EXISTS db_platforma;
USE db_platforma;

CREATE TABLE IF NOT EXISTS SuperAdministrator(
    idSuperAdmin int unique auto_increment primary key,
	  cnp char(20) unique not null,
    nume char(30),
    adresa char(50),
    nrTelefon char(15),
    email char(40),
    username char(255) unique not null,
    parola char(255) not null
);

CREATE TABLE IF NOT EXISTS Administrator(
    idAdmin int unique auto_increment primary key,
	cnp char(20) unique not null,
    nume char(30),
    adresa char(50),
    nrTelefon char(15),
    email char(40),
    username char(255) unique not null,
    parola char(255) not null
);

CREATE TABLE IF NOT EXISTS Profesor(
    idProfesor int unique auto_increment primary key,
	  cnp char(20) unique not null,
    nume char(30),
    departament char(50),
    nrMinOre int,
    nrMaxOre int,
    adresa char(50),
    nrTelefon char(15),
    email char(40),
    username char(255) unique not null,
    parola char(255) not null
);

CREATE TABLE IF NOT EXISTS Student(
    idStudent int unique auto_increment primary key,
	cnp char(20) unique not null,
    nume char(30),
    anStudiu int,
    numarOre int,
    adresa char(50),
    nrTelefon char(15),
    email char(40),
    username char(255) unique not null,
    parola char(255) not null
);


-- Tabel pentru activități (curs, seminar, laborator)
CREATE TABLE IF NOT EXISTS Activitate (
    idActivitate INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    tip CHAR(255), # ENUM('curs', 'seminar', 'laborator', 'examen', 'colocviu', 'grup de studiu'),
    nume CHAR(255),
    descriere CHAR(255),
    nrMaximStudenti INT,
    dataDesfasurare DATE,
    ziua CHAR(255), # ENUM('LUNI', 'MARTI', 'MIERCURI', 'JOI', 'VINERI', 'SAMBATA', 'DUMINICA'),
    frecventa CHAR(255), # ENUM('saptamanal', 'bisaptamanal', 'lunar', 'odata'),
    oraIncepere INT,
    durata INT,
    procentNotaFinala INT
);

-- Tabel pentru subactivități (legate de activități)
CREATE TABLE IF NOT EXISTS Subactivitate (
    idSubactivitate INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    idActivitate INT,
    profesor CHAR(255),
    tip CHAR(255),
    data DATE,
    ora INT,
    nrMaximStudenti INT,
    procent INT,
    FOREIGN KEY (idActivitate) REFERENCES Activitate(idActivitate)
);

-- Tabel pentru maparea studenților la activități
CREATE TABLE IF NOT EXISTS ParticipantActivitate (
    idParticipantActivitate INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    idActivitate INT,
    idSubactivitate INT,
    idStudent INT,
    nota INT,
    FOREIGN KEY (idActivitate) REFERENCES Activitate(idActivitate),
    FOREIGN KEY (idSubactivitate) REFERENCES Subactivitate(idSubactivitate),
    FOREIGN KEY (idStudent) REFERENCES Student(idStudent)
);

-- Tabel pentru mesaje legate de studiul în grup
CREATE TABLE IF NOT EXISTS MesajGrupStudiu (
    idMesaj INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    idSubactivitate INT,
    textMesaj CHAR(255),
    numeUtilizator CHAR(255),
    FOREIGN KEY (idSubactivitate) REFERENCES Subactivitate(idSubactivitate)
);


CREATE TABLE IF NOT EXISTS Rol(
	idRol int unique auto_increment primary key,
    numeRol char(15)
);

INSERT INTO Rol(numeRol) VALUES
('SuperAdmin'),
('Admin'),
('Profesor'),
('Student');

CREATE TABLE IF NOT EXISTS Utilizator(
    idUtilizator int unique auto_increment primary key,
    username char(255) unique not null,
    cnp char(20) unique not null,
    parola char(255),
    id_rol int,
    
    foreign key(id_rol) references Rol(idRol)
);

ALTER TABLE SuperAdministrator ADD COLUMN idRol int, ADD FOREIGN KEY(idRol) REFERENCES Rol(idRol);
ALTER TABLE Administrator ADD COLUMN idRol int, ADD FOREIGN KEY(idRol) REFERENCES Rol(idRol);
ALTER TABLE Profesor ADD COLUMN idRol int, ADD FOREIGN KEY(idRol) REFERENCES Rol(idRol);
ALTER TABLE Student ADD COLUMN idRol int, ADD FOREIGN KEY(idRol) REFERENCES Rol(idRol);

/*
-- Grand privileges
GRANT SELECT ON db_platforma.Student TO 'Student'@'%';
GRANT SELECT ON db_platforma.Profesor TO 'Student'@'%';
GRANT SELECT ON db_platforma.Administrator TO 'Student'@'%';
GRANT SELECT ON db_platforma.SuperAdministrator TO 'Student'@'%';
GRANT SELECT ON db_platforma.ActivitateStudent TO 'Student'@'%';
GRANT SELECT ON db_platforma.Activitate TO 'Student'@'%';
GRANT SELECT ON db_platforma.NoteStudent TO 'Student'@'%';

GRANT SELECT ON db_platforma.Student TO 'Profesor'@'%';
GRANT SELECT ON db_platforma.Profesor TO 'Profesor'@'%';
GRANT SELECT ON db_platforma.Administrator TO 'Profesor'@'%';
GRANT SELECT ON db_platforma.SuperAdministrator TO 'Profesor'@'%';
GRANT ALL PRIVILEGES ON db_platforma.ActivitateProfesor TO 'Profesor'@'%';
GRANT ALL PRIVILEGES ON db_platforma.ActivitateStudent TO 'Profesor'@'%';
GRANT ALL PRIVILEGES ON db_platforma.NoteStudent TO 'Profesor'@'%';

GRANT ALL PRIVILEGES ON db_platforma.Student TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.Profesor TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.Administrator TO 'Admin'@'%';
GRANT SELECT ON db_platforma.SuperAdministrator TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.ActivitateProfesor TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.ActivitateStudent TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.NoteStudent TO 'Admin'@'%';

GRANT ALL PRIVILEGES ON db_platforma.* TO 'SuperAdmin'@'%';
*/
# procedura pentru adaugarea unui super admin
DELIMITER //
CREATE PROCEDURE AddNewSuperAdministrator(
    IN cnp_param CHAR(20),
    IN nume_param CHAR(30),
    IN adresa_param CHAR(50),
    IN nrTelefon_param CHAR(15),
    IN email_param CHAR(40),
    IN username_param CHAR(255),
    IN parola_param CHAR(255)
)
BEGIN
    INSERT INTO SuperAdministrator(cnp, nume, adresa, nrTelefon, email, username, parola, idRol)
    VALUES(cnp_param, nume_param, adresa_param, nrTelefon_param, email_param, username_param, parola_param, (SELECT idRol FROM Rol WHERE numeRol = 'SuperAdmin'));
END //
DELIMITER ;

# procedura pentru adaugarea unui admin
DELIMITER //
CREATE PROCEDURE AddNewAdministrator(
    IN cnp_param CHAR(20),
    IN nume_param CHAR(30),
    IN adresa_param CHAR(50),
    IN nrTelefon_param CHAR(15),
    IN email_param CHAR(40),
    IN username_param CHAR(255),
    IN parola_param CHAR(255)
)
BEGIN
    INSERT INTO Administrator(cnp, nume, adresa, nrTelefon, email, username, parola, idRol)
    VALUES(cnp_param, nume_param, adresa_param, nrTelefon_param, email_param, username_param, parola_param, (SELECT idRol FROM Rol WHERE numeRol = 'Admin'));
END //
DELIMITER ;

# procedura pentru adaugarea unui profesor
DELIMITER //
CREATE PROCEDURE AddNewProfesor(
    IN cnp_param CHAR(20),
    IN nume_param CHAR(30),
    IN departament_param CHAR(50),
    IN nrMinOre_param INT,
    IN nrMaxOre_param INT,
    IN adresa_param CHAR(50),
    IN nrTelefon_param CHAR(15),
    IN email_param CHAR(40),
    IN username_param CHAR(255),
    IN parola_param CHAR(255)
)
BEGIN
    INSERT INTO Profesor(cnp, nume, departament, nrMinOre, nrMaxOre, adresa, nrTelefon, email, username, parola, idRol)
    VALUES(cnp_param, nume_param, departament_param, nrMinOre_param, nrMaxOre_param, adresa_param, nrTelefon_param, email_param, username_param, parola_param, (SELECT idRol FROM Rol WHERE numeRol = 'Profesor'));
END //
DELIMITER ;

# procedura pentru adaugarea unui student
DELIMITER //
CREATE PROCEDURE AddNewStudent(
    IN cnp_param CHAR(20),
    IN nume_param CHAR(30),
    IN anStudiu_param INT,
    IN numarOre_param INT,
    IN adresa_param CHAR(50),
    IN nrTelefon_param CHAR(15),
    IN email_param CHAR(40),
    IN username_param CHAR(255),
    IN parola_param CHAR(255)
)
BEGIN
    INSERT INTO Student(cnp, nume, anStudiu, numarOre, adresa, nrTelefon, email, username, parola, idRol)
    VALUES(cnp_param, nume_param, anStudiu_param, numarOre_param, adresa_param, nrTelefon_param, email_param, username_param, parola_param, (SELECT idRol FROM Rol WHERE numeRol = 'Student'));
END //
DELIMITER ;



DELIMITER //
CREATE TRIGGER InsertSuperAdmin
AFTER INSERT ON SuperAdministrator
FOR EACH ROW
BEGIN
    INSERT INTO Utilizator(username, cnp, parola, id_rol)
    VALUES (NEW.username, NEW.cnp, NEW.parola, (SELECT idRol FROM Rol WHERE BINARY numeRol = 'SuperAdmin'));
END;
//

CREATE TRIGGER InsertAdmin
AFTER INSERT ON Administrator
FOR EACH ROW
BEGIN
    INSERT INTO Utilizator(username, cnp, parola, id_rol)
    VALUES (NEW.username, NEW.cnp, NEW.parola, (SELECT idRol FROM Rol WHERE BINARY numeRol = 'Admin'));
END;
//

CREATE TRIGGER InsertProfesor
AFTER INSERT ON Profesor
FOR EACH ROW
BEGIN
    INSERT INTO Utilizator(username, cnp, parola, id_rol)
    VALUES (NEW.username, NEW.cnp, NEW.parola, (SELECT idRol FROM Rol WHERE BINARY numeRol = 'Profesor'));
END;
//

CREATE TRIGGER InsertStudent
AFTER INSERT ON Student
FOR EACH ROW
BEGIN
    INSERT INTO Utilizator(username, cnp, parola, id_rol)
    VALUES (NEW.username, NEW.cnp, NEW.parola, (SELECT idRol FROM Rol WHERE BINARY numeRol = 'Student'));
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER UpdateSuperAdmin
AFTER UPDATE ON SuperAdministrator
FOR EACH ROW
BEGIN
    UPDATE Utilizator
    SET cnp = NEW.cnp, username = NEW.username, parola = NEW.parola
    WHERE id_rol = (SELECT idRol FROM Rol WHERE BINARY numeRol = 'SuperAdmin') AND cnp = OLD.cnp;
END;
//

CREATE TRIGGER UpdateAdmin
AFTER UPDATE ON Administrator
FOR EACH ROW
BEGIN
    UPDATE Utilizator
    SET cnp = NEW.cnp, username = NEW.username, parola = NEW.parola
    WHERE id_rol = (SELECT idRol FROM Rol WHERE BINARY numeRol = 'Admin') AND cnp = OLD.cnp;
END;
//

CREATE TRIGGER UpdateProfesor
AFTER UPDATE ON Profesor
FOR EACH ROW
BEGIN
    UPDATE Utilizator
    SET cnp = NEW.cnp, username = NEW.username, parola = NEW.parola
    WHERE id_rol = (SELECT idRol FROM Rol WHERE BINARY numeRol = 'Profesor') AND cnp = OLD.cnp;
END;
//

CREATE TRIGGER UpdateStudent
AFTER UPDATE ON Student
FOR EACH ROW
BEGIN
    UPDATE Utilizator
    SET cnp = NEW.cnp, username = NEW.username, parola = NEW.parola
    WHERE id_rol = (SELECT idRol FROM Rol WHERE BINARY numeRol = 'Student') AND cnp = OLD.cnp;
END;
//
DELIMITER ;




DELIMITER //
CREATE TRIGGER DeleteSuperAdmin
AFTER DELETE ON SuperAdministrator
FOR EACH ROW
BEGIN
    DELETE FROM Utilizator WHERE username = OLD.username;
END;
//

CREATE TRIGGER DeleteAdmin
AFTER DELETE ON Administrator
FOR EACH ROW
BEGIN
    DELETE FROM Utilizator WHERE username = OLD.username;
END;
//

CREATE TRIGGER DeleteProfesor
AFTER DELETE ON Profesor
FOR EACH ROW
BEGIN
    DELETE FROM Utilizator WHERE username = OLD.username;
END;
//

CREATE TRIGGER DeleteStudent
AFTER DELETE ON Student
FOR EACH ROW
BEGIN
    DELETE FROM Utilizator WHERE username = OLD.username;
END;
//
DELIMITER ;





call addNewSuperAdministrator("0000000000000", "Vlad Durdeu", "Str Principala nr 1", "0755333444", "johnsmith0@random.org", "Vlad", "Calicats");
call addNewSuperAdministrator( "1111111111111", "Alex Stancu", "Str Principala nr 2", "0755333445", "johnsmith1@random.org", "Stancu", "Calicats");
call addNewSuperAdministrator("2222222222222", "Lion Moroz", "Str Principala nr 3", "0755333446", "johnsmith2@random.org", "Lion", "Calicats");
-- Test call for adding a new professor
CALL AddNewProfesor("0", "Profesor Test", "Informatics", 10, 20, "Main Street 4", "0755333446", "prof@test.com", "prof_test", "test");
CALL AddNewProfesor("1", "Profesor Test", "Informatics", 10, 20, "Main Street 4", "0755333446", "prof@test2.com", "prof", "test");

-- Test call for adding a new student
CALL AddNewStudent("2", "Student Test", 2, 15, "Main Street 5", "0755333446", "student@test.com", "student_test", "test");
CALL AddNewStudent("3", "Student Test", 2, 15, "Main Street 5", "0755333446", "student@test2.com", "student", "test");

/*
call AddNoteStudent("1", 10, "1", "1");
call AddNoteStudent("2", 10, "1", "1");
call AddNoteStudent("3", 8, "1", "2");


call addNewActivitateProfesor("profesor", "Curs", "Curs 1: Introducere", 10);
call addNewActivitateProfesor("profesor", "Seminar", "Seminar 1: Introducere", 10);

call addNewActivitateProfesor("profesormate", "Curs", "Curs 1: Relatii", 10);
call addNewActivitateProfesor("profesormate", "Seminar", "Seminar 1: Relatii", 10);
*/
SELECT * FROM SuperAdministrator;
SELECT * FROM Utilizator;
SELECT * FROM Profesor;


/*

SELECT 
    P.nume,
    P.username,
    AP.tipActivitate,
    AP.descriere,
    AP.nrMaximStudenti
FROM 
    Profesor P
JOIN 
    ActivitateProfesor AP ON P.idProfesor = AP.id_profesor;
    
SELECT P.nume, P.username, AP.tipActivitate, AP.descriere, AP.nrMaximStudenti
FROM Profesor P JOIN ActivitateProfesor AP ON P.idProfesor = AP.id_profesor WHERE LOCATE("curs", AP.descriere) > 0;

DELETE FROM SuperAdministrator;
DELETE FROM ActivitateProfesor;
ALTER TABLE ActivitateProfesor AUTO_INCREMENT = 1;
ALTER TABLE SuperAdministrator AUTO_INCREMENT = 1;
ALTER TABLE Utilizator AUTO_INCREMENT = 1;
*/