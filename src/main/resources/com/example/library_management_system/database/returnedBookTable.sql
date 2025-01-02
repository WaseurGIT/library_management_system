CREATE TABLE returnedBook
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    studentId       INT NOT NULL,
    bookId          INT NOT NULL,
    studentName     VARCHAR(255) NOT NULL,
    bookName        VARCHAR(255) NOT NULL,
    bookImage       VARCHAR(255) NOT NULL,
    returnedDate       DATE NOT NULL,
    authorName      VARCHAR(255) NOT NULL,
    FOREIGN KEY (studentId) REFERENCES studentDetails(id),
    FOREIGN KEY (bookId) REFERENCES bookDetails(id)
);