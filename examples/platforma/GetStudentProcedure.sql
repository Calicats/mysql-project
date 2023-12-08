USE db_platforma;

DELIMITER //

CREATE PROCEDURE GetStudentDetails(IN student_id INT)
BEGIN
    SELECT *
    FROM Student
    WHERE idStudent = student_id;
END //

DELIMITER ;