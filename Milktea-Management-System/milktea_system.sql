-- 创建数据库
CREATE DATABASE IF NOT EXISTS milktea_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE milktea_system;

-- 产品分类表
CREATE TABLE IF NOT EXISTS category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL,
    description TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 产品表（奶茶产品）
CREATE TABLE IF NOT EXISTS product (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    image_url VARCHAR(200),
    status TINYINT DEFAULT 1 COMMENT '1: 在售, 0: 下架',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 员工表
CREATE TABLE IF NOT EXISTS employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender TINYINT,
    phone VARCHAR(20),
    email VARCHAR(100),
    position VARCHAR(20) DEFAULT '服务员',
    join_date DATE,
    role TINYINT DEFAULT 0 COMMENT '0: 员工, 1: 管理员',
    status TINYINT DEFAULT 1 COMMENT '1: 在职, 0: 离职',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 客户表
CREATE TABLE IF NOT EXISTS customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender TINYINT,
    phone VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100),
    address VARCHAR(200),
    member_level VARCHAR(20) DEFAULT '普通会员',
    points INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单表
CREATE TABLE IF NOT EXISTS `order` (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    customer_id INT,
    employee_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_method TINYINT NOT NULL COMMENT '1: 现金, 2: 微信, 3: 支付宝',
    order_status TINYINT DEFAULT 1 COMMENT '1: 待制作, 2: 制作中, 3: 已完成, 4: 已取消',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单明细表
CREATE TABLE IF NOT EXISTS order_item (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `order`(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入初始数据
-- 分类数据
INSERT INTO category (category_name, description) VALUES 
('经典奶茶', '传统奶茶系列'),
('果茶系列', '水果茶系列'),
('奶盖系列', '奶盖茶系列'),
('冰沙系列', '冰沙饮品');

-- 产品数据
INSERT INTO product (product_name, category_id, price, description) VALUES 
('珍珠奶茶', 1, 15.00, '经典珍珠奶茶，Q弹珍珠搭配香浓奶茶'),
('波霸奶茶', 1, 16.00, '大颗波霸珍珠，口感更佳'),
('草莓奶盖茶', 3, 18.00, '新鲜草莓与香浓奶盖的完美结合'),
('芒果冰沙', 4, 17.00, '新鲜芒果制作的冰沙，清凉解暑'),
('柠檬绿茶', 2, 14.00, '清新柠檬搭配绿茶，酸甜可口');

-- 员工数据（密码：123456，使用MD5加密）
INSERT INTO employee (username, password, name, gender, phone, email, position, join_date, role, status) VALUES 
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 1, '13800138000', 'admin@example.com', '管理员', '2024-01-01', 1, 1),
('employee1', 'e10adc3949ba59abbe56e057f20f883e', '员工张三', 1, '13800138001', 'zhangsan@example.com', '收银员', '2024-02-15', 0, 1);

-- 客户数据
INSERT INTO customer (name, gender, phone, email, address, member_level, points) VALUES 
('李四', 1, '13900139000', 'lisi@example.com', '北京市朝阳区', '金卡会员', 100),
('王五', 0, '13900139001', 'wangwu@example.com', '上海市浦东新区', '银卡会员', 50);
