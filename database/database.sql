PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;

-- 1. COURSES TABLE
CREATE TABLE Courses (
    CourseID INTEGER PRIMARY KEY,
    CourseName TEXT UNIQUE
);
INSERT INTO Courses VALUES(1,'Databases');
INSERT INTO Courses VALUES(2,'Computer Networks');
INSERT INTO Courses VALUES(3,'Programming 1');

-- 2. STUDENTS TABLE
CREATE TABLE Students (
    StudentID INTEGER PRIMARY KEY,
    Name TEXT,
    Email TEXT UNIQUE
);
INSERT INTO Students VALUES(1,'Peter Kiss','peter.kiss@example.com');
INSERT INTO Students VALUES(2,'Anna Nagy','anna.nagy@example.com');
INSERT INTO Students VALUES(3,'Gabor Toth','gabor.toth@example.com');

-- 3. EXAMS TABLE
CREATE TABLE Exams (
    ExamID INTEGER PRIMARY KEY,
    Name TEXT,
    CourseID INTEGER,
    Date DATETIME,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID) ON DELETE CASCADE
);
INSERT INTO Exams VALUES(1,'Midterm 1',1,'2025-03-15');
INSERT INTO Exams VALUES(2,'Midterm 2',1,'2025-04-15');
INSERT INTO Exams VALUES(3,'Networks Exam',2,'2025-03-20');
INSERT INTO Exams VALUES(4,'Prog1 Exam',3,'2025-04-04');

-- 4. GRADES TABLE
CREATE TABLE Grades (
    GradeID INTEGER PRIMARY KEY,
    StudentID INTEGER,
    ExamID INTEGER,
    GradeValue INTEGER CHECK (GradeValue >= 1 AND GradeValue <= 5),
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID) ON DELETE CASCADE,
    FOREIGN KEY (ExamID) REFERENCES Exams(ExamID) ON DELETE CASCADE,
    UNIQUE (StudentID, ExamID)
);
INSERT INTO Grades VALUES(1,1,1,3);
INSERT INTO Grades VALUES(2,1,2,3);
INSERT INTO Grades VALUES(3,2,1,5);
INSERT INTO Grades VALUES(4,2,3,4);
INSERT INTO Grades VALUES(5,3,4,2);

COMMIT;