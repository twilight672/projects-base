CREATE TABLE students (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(50) NOT NULL,
                          password VARCHAR(50) NOT NULL,
                          enroll_date DATE
);

CREATE TABLE books (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(100) NOT NULL,
                       author VARCHAR(100) NOT NULL,
                       publisher VARCHAR(100),
                       price DECIMAL(10,2),
                       is_borrowed BOOLEAN DEFAULT false,
                       borrower_id INT DEFAULT NULL
);

-- 插入10个示例学生数据
INSERT INTO students (id, name, password, enroll_date) VALUES
                                                           (1, '张三', '123456', '2023-09-01'),
                                                           (2, '李四', '123456', '2023-09-01'),
                                                           (3, '王五', '123456', '2023-09-01'),
                                                           (4, '赵六', '123456', '2023-09-01'),
                                                           (5, '钱七', '123456', '2023-09-01'),
                                                           (6, '孙八', '123456', '2023-09-01'),
                                                           (7, '周九', '123456', '2023-09-01'),
                                                           (8, '吴十', '123456', '2023-09-01'),
                                                           (9, '郑十一', '123456', '2023-09-01'),
                                                           (10, '王十二', '123456', '2023-09-01');

-- 插入10本示例图书数据
INSERT INTO books (id, title, author, publisher, price, is_borrowed, borrower_id) VALUES
                                                                                      (1, 'Java核心技术', 'Cay Horstmann', '机械工业出版社', 129.00, false, NULL),
                                                                                      (2, 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 99.00, false, NULL),
                                                                                      (3, 'Python数据分析', 'Wes McKinney', '机械工业出版社', 119.00, false, NULL),
                                                                                      (4, '计算机程序的构造和解释', 'Harold Abelson', '机械工业出版社', 99.00, false, NULL),
                                                                                      (5, '算法导论', 'Thomas H. Cormen', '机械工业出版社', 168.00, false, NULL),
                                                                                      (6, '深入理解计算机系统', 'Randal E. Bryant', '机械工业出版社', 129.00, false, NULL),
                                                                                      (7, '设计模式', 'Erich Gamma', '机械工业出版社', 79.00, false, NULL),
                                                                                      (8, '重构', 'Martin Fowler', '机械工业出版社', 99.00, false, NULL),
                                                                                      (9, '代码大全', 'Steve McConnell', '电子工业出版社', 128.00, false, NULL),
                                                                                      (10, '操作系统概念', 'Abraham Silberschatz', '高等教育出版社', 109.00, false, NULL);