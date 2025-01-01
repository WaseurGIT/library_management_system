CREATE TABLE bookDetails
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    bookName      VARCHAR(255) NOT NULL,
    bookAuthor    VARCHAR(255) NOT NULL,
    bookImage     VARCHAR(255) NOT NULL,
    bookDept      VARCHAR(255) NOT NULL,
    bookStatus    VARCHAR(50)  DEFAULT 'Available' -- 'Available' or 'Issued'
);
