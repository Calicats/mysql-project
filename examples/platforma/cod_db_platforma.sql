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

-- AICI TIN DENUMRIIILE cursurilor
-- LE FACE ADMINU
CREATE TABLE IF NOT EXISTS Curs (
    idCurs INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    numeCurs CHAR(255),
    descriere char(255),
    nrMaximStudenti INT
);

-- Aici tin toate Activitatile (CURS, seminar, etc)
-- O FACE PROFU
CREATE TABLE IF NOT EXISTS Activitate(
	idActivitate int unique auto_increment primary key,
    tip char(255),
    procentNota int,
    
    idCurs int,
    idProfesor int,
    FOREIGN KEY (idCurs) REFERENCES Curs(idCurs),
    FOREIGN KEY (idProfesor) REFERENCES Profesor(idProfesor)
);

-- Tabel pentru maparea studenților la activități
-- LE FACE STUDENTU
CREATE TABLE IF NOT EXISTS ParticipantActivitate (
    idParticipantActivitate INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    numarParticipanti INT,
    
    idActivitate INT,
    idStudent INT,
    idProfesor INT,
    FOREIGN KEY (idActivitate) REFERENCES Activitate(idActivitate),
    FOREIGN KEY (idStudent) REFERENCES Student(idStudent),
    FOREIGN KEY (idProfesor) REFERENCES Profesor(idProfesor)
);

-- Cu tabela asta programezi activitatea
-- LE FACE PROFESORUL
CREATE TABLE IF NOT EXISTS ProgramareActivitate (
    idProgramareActivitate INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    dataIncepere DATE,
    dataFinalizare DATE,
    frecventa CHAR(255),
    zi char(255),
    ora INT,
    minut int,
    durata int,
    
    idParticipantActivitate INT,
    FOREIGN KEY (idParticipantActivitate) REFERENCES ParticipantActivitate(idParticipantActivitate)
);

-- ADAUGARA NOTA LA STUDENT
-- O FACE PROFU

CREATE TABLE IF NOT EXISTS NoteStudent (
	idNoteStudent INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    nota int,
    
    idStudent int,
    idActivitate int,
    FOREIGN KEY (idStudent) REFERENCES Student(idStudent),
	FOREIGN KEY (idActivitate) REFERENCES Activitate(idActivitate)
);

-- GRUP DE WHATSAPP
-- O FACE PROF/STUDENT

CREATE TABLE IF NOT EXISTS GrupStudiu(
    idGrupStudiu INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    numeGrup char(255) unique not null
);

CREATE TABLE IF NOT EXISTS MembruGrupStudiu(
	idMembruGrupStudiu INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    username char(255),
    
    idGrupStudiu int,
    FOREIGN KEY (idGrupStudiu) REFERENCES GrupStudiu(idGrupStudiu)
);

-- Tabel pentru mesaje legate de studiul în grup
CREATE TABLE IF NOT EXISTS MesajGrupStudiu (
    idMesaj INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    textMesaj CHAR(255),
    numeUtilizator CHAR(255),
    
    idGrupStudiu INT,
    FOREIGN KEY (idGrupStudiu) REFERENCES GrupStudiu(idGrupStudiu)
);

CREATE TABLE IF NOT EXISTS IntalnireGrupStudiu (
	idIntalnireGrupStudiu INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
    dataIntalnire date,
    numarMinParticipanti int,
    ora int,
    minut int,
    numarParticipanti int,
    
    idGrupStudiu INT,
    FOREIGN KEY (idGrupStudiu) REFERENCES GrupStudiu(idGrupStudiu)
);

CREATE TABLE IF NOT EXISTS MembruIntalnireGrupStudiu (
   idMembruIntalnireGrupStudiu INT UNIQUE AUTO_INCREMENT PRIMARY KEY,
   username char(255) unique not null,
   
   idIntalnireGrupStudiu INT,
   idGrupStudiu INT,
   FOREIGN KEY (idIntalnireGrupStudiu) REFERENCES IntalnireGrupStudiu(idIntalnireGrupStudiu),
   FOREIGN KEY (idGrupStudiu) REFERENCES GrupStudiu(idGrupStudiu)
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

DELIMITER //
CREATE TRIGGER beforeInsertParticipantActivitate
BEFORE INSERT ON ParticipantActivitate
FOR EACH ROW
BEGIN
    DECLARE totalParticipants INT;

    SET NEW.numarParticipanti = 1;

    SELECT COUNT(*) INTO totalParticipants
    FROM ParticipantActivitate
    WHERE idActivitate = NEW.idActivitate
    AND idProfesor = NEW.idProfesor
    AND idStudent = NEW.idStudent;

    SET NEW.numarParticipanti = NEW.numarParticipanti + totalParticipants;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER IncrementNumarParticipanti
AFTER INSERT ON MembruIntalnireGrupStudiu
FOR EACH ROW
BEGIN
    UPDATE IntalnireGrupStudiu
    SET numarParticipanti = numarParticipanti + 1
    WHERE idIntalnireGrupStudiu = NEW.idIntalnireGrupStudiu;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DecrementNumarParticipanti
AFTER DELETE ON MembruIntalnireGrupStudiu
FOR EACH ROW
BEGIN
    UPDATE IntalnireGrupStudiu
    SET numarParticipanti = numarParticipanti - 1
    WHERE idIntalnireGrupStudiu = OLD.idIntalnireGrupStudiu;
END;
//
DELIMITER ;

-- triggere lipsa

DELIMITER //
CREATE TRIGGER DeleteParticipantActivitateOnStudentDelete
BEFORE DELETE ON Student
FOR EACH ROW
BEGIN
    DECLARE studentId INT;

    SELECT idStudent INTO studentId
    FROM Student
    WHERE username = OLD.username;

    DELETE FROM ParticipantActivitate WHERE idStudent = studentId;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteParticipantActivitateOnProfesorDelete
BEFORE DELETE ON Profesor
FOR EACH ROW
BEGIN
    DECLARE profesorId INT;

    SELECT idProfesor INTO profesorId
    FROM Profesor
    WHERE username = OLD.username;

    DELETE FROM ParticipantActivitate WHERE idProfesor = profesorId;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteMembruGrupStudiuOnStudentDelete
AFTER DELETE ON Student
FOR EACH ROW
BEGIN
    DELETE FROM MembruGrupStudiu WHERE username = OLD.username;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteMembruGrupStudiuOnProfesorDelete
AFTER DELETE ON Profesor
FOR EACH ROW
BEGIN
    DELETE FROM MembruGrupStudiu WHERE username = OLD.username;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteMembruIntalnireGrupStudiuOnStudentDelete
AFTER DELETE ON Student
FOR EACH ROW
BEGIN
    DELETE FROM MembruIntalnireGrupStudiu WHERE username = OLD.username;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteMembruIntalnireGrupStudiuOnProfesorDelete
AFTER DELETE ON Profesor
FOR EACH ROW
BEGIN
    DELETE FROM MembruIntalnireGrupStudiu WHERE username = OLD.username;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteNoteStudentOnStudentDelete
BEFORE DELETE ON Student
FOR EACH ROW
BEGIN
    DECLARE studentId INT;

    SELECT idStudent INTO studentId
    FROM Student
    WHERE username = OLD.username;

    DELETE FROM NoteStudent WHERE idStudent = studentId;
END;
//
DELIMITER ;


DELIMITER //
CREATE TRIGGER DeleteActivitateOnCursDelete
BEFORE DELETE ON Curs
FOR EACH ROW
BEGIN
    DECLARE cursId INT;

    SET cursId = (SELECT idCurs FROM Curs WHERE numeCurs = OLD.numeCurs);

    DELETE FROM Activitate WHERE idCurs = cursId;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteParticipantActivitateOnCursDelete
BEFORE DELETE ON Curs
FOR EACH ROW
BEGIN
    DECLARE cursId INT;

    SET cursId = (SELECT idCurs FROM Curs WHERE numeCurs = OLD.numeCurs);

    DELETE FROM ParticipantActivitate WHERE idActivitate IN (SELECT idActivitate FROM Activitate WHERE idCurs = cursId);
END;
//
DELIMITER ;

DROP TRIGGER DeleteProgramareActivitateOnCursDelete

DELIMITER //
CREATE TRIGGER DeleteProgramareActivitateOnCursDelete
BEFORE DELETE ON Curs
FOR EACH ROW
BEGIN
    DECLARE cursId INT;

    SET cursId = (SELECT idCurs FROM Curs WHERE numeCurs = OLD.numeCurs);

    DELETE FROM ProgramareActivitate WHERE idParticipantActivitate IN (SELECT idParticipantActivitate FROM ParticipantActivitate WHERE idActivitate IN (SELECT idActivitate FROM Activitate WHERE idCurs = cursId));
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteNoteStudentOnCursDelete
BEFORE DELETE ON Curs
FOR EACH ROW
BEGIN
    DECLARE cursId INT;

    SET cursId = (SELECT idCurs FROM Curs WHERE numeCurs = OLD.numeCurs);

    DELETE FROM NoteStudent WHERE idActivitate IN (SELECT idActivitate FROM Activitate WHERE idCurs = cursId);
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteActivitateOnProfesorDelete
BEFORE DELETE ON Profesor
FOR EACH ROW
BEGIN
    DECLARE professorId INT;

    SELECT idProfesor INTO professorId
    FROM Profesor
    WHERE username = OLD.username;

    DELETE FROM Activitate WHERE idProfesor = professorId;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteMembruGrupStudiuOnGrupStudiuDelete
BEFORE DELETE ON GrupStudiu
FOR EACH ROW
BEGIN
    DELETE FROM MembruGrupStudiu WHERE idGrupStudiu = OLD.idGrupStudiu;

    DELETE FROM MesajGrupStudiu WHERE idGrupStudiu = OLD.idGrupStudiu;

    DELETE FROM IntalnireGrupStudiu WHERE idGrupStudiu = OLD.idGrupStudiu;

    DELETE FROM MembruIntalnireGrupStudiu WHERE idGrupStudiu = OLD.idGrupStudiu;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER DeleteMembruIntalnireGrupStudiuOnIntalnireGrupStudiuDelete
BEFORE DELETE ON IntalnireGrupStudiu
FOR EACH ROW
BEGIN
    DELETE FROM MembruIntalnireGrupStudiu WHERE idIntalnireGrupStudiu = OLD.idIntalnireGrupStudiu;
END;
//
DELIMITER ;

call addNewSuperAdministrator("0000000000000", "Vlad Durdeu", "Str Principala nr 1", "0755333444", "johnsmith0@random.org", "Vlad", "Calicats");
call addNewSuperAdministrator( "1111111111111", "Alex Stancu", "Str Principala nr 2", "0755333445", "johnsmith1@random.org", "Stancu", "Calicats");
call addNewSuperAdministrator("2222222222222", "Lion Moroz", "Str Principala nr 3", "0755333446", "johnsmith2@random.org", "Lion", "Calicats");
-- Test call for adding a new professor
CALL AddNewProfesor("0", "Profesor1 Test", "Informatics", 10, 20, "Main Street 4", "0755333447", "prof@test.com", "prof1_test", "test");
CALL AddNewProfesor("1", "Profesor2 Test", "Informatics", 10, 20, "Main Street 4", "0755333448", "prof@test2.com", "prof2_test", "test");

-- Test call for adding a new student
CALL AddNewStudent("2", "Student1 Test", 2, 15, "Main Street 5", "0755333449", "student@test.com", "student1_test", "test");
CALL AddNewStudent("3", "Student2 Test", 2, 15, "Main Street 5", "0755333450", "student@test2.com", "student2_test", "test");

SELECT * FROM ParticipantActivitate;
SELECT * FROM GrupStudiu;
SELECT * FROM MembruGrupStudiu;
SELECT * FROM IntalnireGrupStudiu;
SELECT * FROM MembruIntalnireGrupStudiu;   

/*
call AddNoteStudent("1", 10, "1", "1");
call AddNoteStudent("2", 10, "1", "1");
call AddNoteStudent("3", 8, "1", "2");


call addNewActivitateProfesor("profesor", "Curs", "Curs 1: Introducere", 10);
call addNewActivitateProfesor("profesor", "Seminar", "Seminar 1: Introducere", 10);

call addNewActivitateProfesor("profesormate", "Curs", "Curs 1: Relatii", 10);
call addNewActivitateProfesor("profesormate", "Seminar", "Seminar 1: Relatii", 10);
*/

/*
SELECT * FROM SuperAdministrator;
SELECT * FROM Utilizator;
SELECT * FROM Profesor;
SELECT * FROM Activitate;
SELECT * FROM ParticipantActivitate;

DELETE FROM ParticipantActivitate;
ALTER TABLE ParticipantActivitate auto_increment = 1;

SELECT
    P.username,
    C.numeCurs,
    A.tip,
    C.descriere,
    C.nrMaximStudenti,
    A.procentNota
FROM
    Curs C
    JOIN Activitate A ON C.idCurs = A.idCurs
    JOIN Profesor P ON A.idProfesor = P.idProfesor;
    
SELECT * FROM Curs;
*/
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