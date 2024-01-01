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

# aici tin activitatea profesorilor (curs, seminar, lab)
CREATE TABLE IF NOT EXISTS ActivitateProfesor(
	idActivitateProfesor int unique auto_increment primary key,
    tipActivitate char(255),
    descriere char(255),
    nrMaximStudenti int,
    id_profesor int,
    foreign key(id_profesor) references Profesor(idProfesor)
);

# aici tin cine participa la activitate
CREATE TABLE IF NOT EXISTS ParticipantActivitate(
	idParticipantActivitate int unique auto_increment primary key,
    id_activitate_profesor int,
    id_student int,
    foreign key(id_activitate_profesor) references ActivitateProfesor(idActivitateProfesor),
    foreign key(id_student) references Student(idStudent)
);

# aici tin calendarul desfasurarii activitatilor
CREATE TABLE IF NOT EXISTS CalendarActivitate(
	idCalendarActivitate int unique auto_increment primary key,
    dataDesfasurareActivitate date,
    id_participant_activitate int,
    foreign key(id_participant_activitate) references ParticipantActivitate(idParticipantActivitate)
);

# aici tin notele studentilor
CREATE TABLE IF NOT EXISTS NoteStudent(
	idNota int unique auto_increment primary key,
    nota int,
    id_student int,
    id_participant_activitate int,
    foreign key(id_student) references Student(idStudent),
    foreign key(id_participant_activitate) references ParticipantActivitate(idParticipantActivitate)
);

# aici tin marele grup de whatsapp cu un profesor
CREATE TABLE IF NOT EXISTS GrupStudiu(
	idGrupStudiu int unique auto_increment primary key,
    descriereGrupStudiu char(50),
    id_participant_activitate int,
    foreign key(id_participant_activitate) references ParticipantActivitate(idParticipantActivitate)
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

CREATE USER 'Student' IDENTIFIED BY 'PASSWORD';
CREATE USER 'Profesor' IDENTIFIED BY 'PASSWORD';
CREATE USER 'Admin' IDENTIFIED BY 'PASSWORD';
CREATE USER 'SuperAdmin' IDENTIFIED BY 'PASSWORD';

# drepturi student
GRANT SELECT ON db_platforma.Student TO 'Student'@'%';
GRANT SELECT ON db_platforma.Profesor TO 'Student'@'%';
GRANT SELECT ON db_platforma.Administrator TO 'Student'@'%';
GRANT SELECT ON db_platforma.SuperAdministrator TO 'Student'@'%';
GRANT SELECT ON db_platforma.ParticipantActivitate TO 'Student'@'%';
GRANT SELECT ON db_platforma.CalendarActivitate TO 'Student'@'%';
GRANT SELECT ON db_platforma.NoteStudent TO 'Student'@'%';
GRANT ALL PRIVILEGES ON db_platforma.GrupStudiu TO 'Student'@'%';

# drepturi profesor
GRANT SELECT ON db_platforma.Student TO 'Profesor'@'%';
GRANT SELECT ON db_platforma.Profesor TO 'Profesor'@'%';
GRANT SELECT ON db_platforma.Administrator TO 'Profesor'@'%';
GRANT SELECT ON db_platforma.SuperAdministrator TO 'Profesor'@'%';
GRANT ALL PRIVILEGES ON db_platforma.ParticipantActivitate TO 'Profesor'@'%';
GRANT ALL PRIVILEGES ON db_platforma.CalendarActivitate TO 'Profesor'@'%';
GRANT ALL PRIVILEGES ON db_platforma.NoteStudent TO 'Profesor'@'%';
GRANT ALL PRIVILEGES ON db_platforma.GrupStudiu TO 'Profesor'@'%';

# drepturi admin
GRANT ALL PRIVILEGES ON db_platforma.Student TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.Profesor TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.Administrator TO 'Admin'@'%';
GRANT SELECT ON db_platforma.SuperAdministrator TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.ParticipantActivitate TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.CalendarActivitate TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.NoteStudent TO 'Admin'@'%';
GRANT ALL PRIVILEGES ON db_platforma.GrupStudiu TO 'Admin'@'%';

#drepturi superadmin
GRANT ALL PRIVILEGES ON db_platforma.* TO 'SuperAdmin'@'%';

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
CREATE PROCEDURE AddNewActivitateProfesor(
    IN username_param CHAR(255),
    IN tipActivitate_param CHAR(255),
    IN descriere_param CHAR(255),
    IN nrMaximStudenti_param int
)
BEGIN
    DECLARE id_profesor INT;

    SELECT idProfesor INTO id_profesor FROM Profesor WHERE username = username_param;

    INSERT INTO ActivitateProfesor(tipActivitate, descriere, nrMaximStudenti, id_profesor)
    VALUES(tipActivitate_param, descriere_param, nrMaximStudenti, id_profesor);
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

DELIMITER //
CREATE TRIGGER DeleteActivitiesOnProfesor
AFTER DELETE ON Profesor
FOR EACH ROW
BEGIN
    DELETE FROM ActivitateProfesor WHERE id_profesor = OLD.idProfesor;
END;
//
DELIMITER ;

call addNewSuperAdministrator("0000000000000", "Vlad Durdeu", "Str Principala nr 1", "0755333444", "johnsmith0@random.org", "Vlad", "Calicats");
call addNewSuperAdministrator( "1111111111111", "Alex Stancu", "Str Principala nr 2", "0755333445", "johnsmith1@random.org", "Stancu", "Calicats");
call addNewSuperAdministrator("2222222222222", "Lion Moroz", "Str Principala nr 3", "0755333446", "johnsmith2@random.org", "Lion", "Calicats");

call addNewActivitateProfesor("profesor", "Curs", "Curs 1: Introducere");
call addNewActivitateProfesor("profesor", "Seminar", "Seminar 1: Introducere");

call addNewActivitateProfesor("profesormate", "Curs", "Curs 1: Relatii");
call addNewActivitateProfesor("profesormate", "Seminar", "Seminar 1: Relatii");

SELECT * FROM SuperAdministrator;
SELECT * FROM Utilizator;
SELECT * FROM Profesor;
SELECT * FROM ActivitateProfesor;

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
