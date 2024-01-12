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


-- Table for the activities of teachers (course, seminar, lab)
CREATE TABLE IF NOT EXISTS Activitate (
    idActivitate int UNIQUE AUTO_INCREMENT PRIMARY KEY,
    tip ENUM('curs', 'seminar', 'laborator', 'examen', 'colocviu', 'grup de studiu'),
    nume char(255),
    descriere char(255),
    nrMaximStudenti int,
    dataDesfasurare DATE,
    ziua ENUM('LUNI', 'MARTI', 'MIERCURI', 'JOI', 'VINERI', 'SAMBATA', 'DUMINICA'),
    frecventa ENUM('saptamanal', 'bisaptamanal', 'lunar', 'odata'),
    oraIncepere INT,
    minuteIncepere INT,
    durata INT,
    procentNotaFinala INT
);
-- Relationship table for mapping teachers to activities
CREATE TABLE IF NOT EXISTS ActivitateProfesor (
    idActivitateProfesor int UNIQUE AUTO_INCREMENT PRIMARY KEY,
    idActivitate int,
    idProfesor int,
    FOREIGN KEY (idActivitate) REFERENCES Activitate(idActivitate),
    FOREIGN KEY (idProfesor) REFERENCES Profesor(idProfesor)
);

CREATE TABLE IF NOT EXISTS ActivitateStudent (
    idActivitateStudent int UNIQUE AUTO_INCREMENT PRIMARY KEY,
    idActivitate int,
    idStudent int,
    FOREIGN KEY (idActivitate) REFERENCES Activitate(idActivitate),
    FOREIGN KEY (idStudent) REFERENCES Student(idStudent)
);

# aici tin notele studentilor
CREATE TABLE IF NOT EXISTS NoteStudent(
	idNota int unique auto_increment primary key,
    nota int,
    id_student int,
    idActivitateStudent int,
    foreign key(id_student) references Student(idStudent),
    foreign key(idActivitateStudent) references ActivitateStudent(idActivitateStudent)
);

/*
# aici tin marele grup de whatsapp cu un profesor
CREATE TABLE IF NOT EXISTS GrupStudiu(
	idGrupStudiu int unique auto_increment primary key,
    descriereGrupStudiu char(50),
    id_participant_activitate int,
    foreign key(id_participant_activitate) references ParticipantActivitate(idParticipantActivitate)
);
*/

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

# procedura pentru adaugarea unui student
DELIMITER //
CREATE PROCEDURE AddNoteStudent(
    IN idNota int,
    IN nota int,
    IN id_student int,
    IN id_participant_activitate int
)
BEGIN
    INSERT INTO NoteStudent(idNota, nota, id_student, id_participant_activitate)
    VALUES(idNota, nota, id_student, id_participant_activitate);
END //
DELIMITER ;

-- Insert into Activitate table
DELIMITER //

CREATE PROCEDURE InsertActivitate(
    IN p_tip ENUM('curs', 'seminar', 'laborator', 'examen', 'colocviu', 'grup de studiu'),
    IN p_nume CHAR(255),
    IN p_descriere CHAR(255),
    IN p_nrMaximStudenti INT,
    IN p_dataDesfasurare DATE,
    IN p_ziua ENUM('LUNI', 'MARTI', 'MIERCURI', 'JOI', 'VINERI', 'SAMBATA', 'DUMINICA'),
    IN p_frecventa ENUM('saptamanal', 'bisaptamanal', 'lunar', 'odata'),
    IN p_oraIncepere INT,
    IN p_minuteIncepere INT,
    IN p_durata INT,
    IN p_procentNotaFinala INT
)
BEGIN
    INSERT INTO Activitate (tip, nume, descriere, nrMaximStudenti, dataDesfasurare, ziua, frecventa, oraIncepere, minuteIncepere, durata, procentNotaFinala)
    VALUES (p_tip, p_nume, p_descriere, p_nrMaximStudenti, p_dataDesfasurare, p_ziua, p_frecventa, p_oraIncepere, p_minuteIncepere, p_durata, p_procentNotaFinala);
END //

-- Insert into ActivitateProfesor table
CREATE PROCEDURE InsertActivitateProfesor(
    IN p_idActivitate INT,
    IN p_idProfesor INT
)
BEGIN
    INSERT INTO ActivitateProfesor (idActivitate, idProfesor)
    VALUES (p_idActivitate, p_idProfesor);
END //

-- Insert into ActivitateStudent table
CREATE PROCEDURE InsertActivitateStudent(
    IN p_idActivitate INT,
    IN p_idStudent INT
)
BEGIN
    INSERT INTO ActivitateStudent (idActivitate, idStudent)
    VALUES (p_idActivitate, p_idStudent);
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

-- Update Activitate table
DELIMITER //

CREATE PROCEDURE UpdateActivitate(
    IN p_idActivitate INT,
    IN p_tip ENUM('curs', 'seminar', 'laborator', 'examen', 'colocviu', 'grup de studiu'),
    IN p_nume CHAR(255),
    IN p_descriere CHAR(255),
    IN p_nrMaximStudenti INT,
    IN p_dataDesfasurare DATE,
    IN p_ziua ENUM('LUNI', 'MARTI', 'MIERCURI', 'JOI', 'VINERI', 'SAMBATA', 'DUMINICA'),
    IN p_frecventa ENUM('saptamanal', 'bisaptamanal', 'lunar', 'odata'),
    IN p_oraIncepere INT,
    IN p_minuteIncepere INT,
    IN p_durata INT,
    IN p_procentNotaFinala INT
)
BEGIN
    UPDATE Activitate
    SET 
        tip = p_tip,
        nume = p_nume,
        descriere = p_descriere,
        nrMaximStudenti = p_nrMaximStudenti,
        dataDesfasurare = p_dataDesfasurare,
        ziua = p_ziua,
        frecventa = p_frecventa,
        oraIncepere = p_oraIncepere,
        minuteIncepere = p_minuteIncepere,
        durata = p_durata,
        procentNotaFinala = p_procentNotaFinala
    WHERE idActivitate = p_idActivitate;
END //

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

DELIMITER //
-- Delete from Activitate table
CREATE PROCEDURE DeleteActivitate(
    IN p_idActivitate INT
)
BEGIN
    DELETE FROM Activitate
    WHERE idActivitate = p_idActivitate;
END //
DELIMITER ;

DELIMITER //

-- Delete from ActivitateProfesor table
CREATE PROCEDURE DeleteActivitateProfesor(
    IN p_idActivitateProfesor INT
)
BEGIN
    DELETE FROM ActivitateProfesor
    WHERE idActivitateProfesor = p_idActivitateProfesor;
END //
DELIMITER ;

DELIMITER //

-- Delete from ActivitateStudent table
CREATE PROCEDURE DeleteActivitateStudent(
    IN p_idActivitateStudent INT
)
BEGIN
    DELETE FROM ActivitateStudent
    WHERE idActivitateStudent = p_idActivitateStudent;
END //
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
SELECT * FROM ActivitateProfesor;


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