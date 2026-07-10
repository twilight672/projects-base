# 学生图书管理系统

一个基于 Java + MySQL 的控制台版图书管理系统，面向学生用户提供登录、浏览图书、借阅与归还功能。

## 项目结构

```
library-management-system/
├── lib/
│   └── mysql-connector-j-9.3.0.jar   # MySQL JDBC 驱动
├── sql/
│   └── db.sql                          # 数据库建表及示例数据脚本
├── src/
│   └── com/caijing/test/
│       ├── Main.java                   # 程序入口，菜单与业务流程
│       ├── Book.java                   # 图书实体类
│       ├── Student.java                # 学生实体类
│       ├── BookDAO.java                # 图书数据访问层
│       ├── StudentDAO.java             # 学生数据访问层
│       └── DBUtil.java                 # 数据库连接工具类
└── 《学生图书管理系统设计与实现》.docx
```

## 功能说明

系统采用控制台交互方式，分为两级菜单：

**主菜单（无需登录）**
- 学生登录：输入学号与密码进行身份验证
- 查看图书列表：浏览馆藏所有图书及借阅状态
- 退出系统

**学生菜单（登录后）**
- 查看我的借阅情况：列出当前学生已借阅的图书
- 借阅图书：选择图书后将其标记为已借出
- 归还图书：选择已借阅图书后将其归还
- 退出登录

## 技术栈

- **语言**：Java
- **数据库**：MySQL
- **驱动**：mysql-connector-j 9.3.0
- **架构模式**：DAO（Data Access Object）分层设计

## 环境准备

1. 安装 JDK 8 或以上版本
2. 安装 MySQL 数据库
3. 执行 [sql/db.sql](sql/db.sql) 脚本创建数据表并导入示例数据

```sql
SOURCE sql/db.sql;
```

## 配置说明

数据库连接信息位于 [src/com/caijing/test/DBUtil.java](src/com/caijing/test/DBUtil.java)，若使用本地或其他数据库，请修改以下常量：

```java
private static final String DB_URL = "jdbc:mysql://<主机地址>:<端口>/<库名>";
private static final String DB_USER = "<用户名>";
private static final String DB_PASSWORD = "<密码>";
```

## 运行方式

由于项目未使用构建工具，需手动将 MySQL 驱动加入类路径：

```bash
# 编译
javac -encoding UTF-8 -cp "lib/mysql-connector-j-9.3.0.jar" -d out src/com/caijing/test/*.java

# 运行
java -cp "out;lib/mysql-connector-j-9.3.0.jar" com.caijing.test.Main
```

> Linux/macOS 系统下请将路径分隔符 `;` 替换为 `:`。

也可直接使用 IntelliJ IDEA 打开本项目，将 `lib` 目录加入模块依赖后运行 [Main.java](src/com/caijing/test/Main.java)。

## 数据库表结构

**students 学生表**

| 字段         | 类型         | 说明       |
| ------------ | ------------ | ---------- |
| id           | INT          | 学号(主键) |
| name         | VARCHAR(50)  | 姓名       |
| password     | VARCHAR(50)  | 登录密码   |
| enroll_date  | DATE         | 入学日期   |

**books 图书表**

| 字段         | 类型          | 说明                     |
| ------------ | ------------- | ------------------------ |
| id           | INT           | 图书ID(主键)             |
| title        | VARCHAR(100)  | 书名                     |
| author       | VARCHAR(100)  | 作者                     |
| publisher    | VARCHAR(100)  | 出版社                   |
| price        | DECIMAL(10,2) | 价格                     |
| is_borrowed  | BOOLEAN       | 是否被借出               |
| borrower_id  | INT           | 借阅者学号(可为空)       |

## 示例数据

脚本内置 10 名学生与 10 本图书作为测试数据，学生默认密码均为 `123456`，可用学号 `1`~`10` 登录体验。
