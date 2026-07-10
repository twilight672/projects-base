# 奶茶管理系统

## 项目介绍
这是一个基于Java Web开发的奶茶店管理系统，实现了产品管理、订单管理、客户管理、员工管理和类别管理等核心功能。

## 技术栈
- Java 17
- Servlet API 4.0
- JSP 2.3
- MySQL 8.0
- JDBC + C3P0连接池
- Apache Commons DbUtils
- JSTL 1.2
- CSS 3

## 项目结构
```
milktea-system/
├── src/main/
│   ├── java/com/milktea/
│   │   ├── dao/         # 数据访问层
│   │   ├── entity/      # 实体类
│   │   ├── filter/      # 过滤器
│   │   ├── service/     # 业务逻辑层
│   │   ├── servlet/     # 控制器
│   │   └── util/        # 工具类
│   ├── resources/       # 资源文件
│   │   └── db.properties # 数据库配置
│   └── webapp/          # Web资源
│       ├── css/         # 样式文件
│       ├── js/          # JavaScript文件
│       ├── images/      # 图片资源
│       ├── product/     # 产品管理页面
│       ├── order/       # 订单管理页面
│       ├── customer/    # 客户管理页面
│       ├── employee/    # 员工管理页面
│       ├── category/    # 类别管理页面
│       ├── index.jsp    # 首页
│       ├── login.jsp    # 登录页面
│       └── WEB-INF/
│           └── web.xml  # Web应用配置
├── target/              # 构建输出目录
├── milktea_system.sql   # 数据库脚本
├── pom.xml              # Maven配置
└── README.md            # 项目说明
```

## 功能模块

### 1. 产品管理
- 产品列表展示
- 添加新产品
- 编辑产品信息
- 删除产品
- 产品状态管理（上架/下架）

### 2. 订单管理
- 订单列表展示
- 创建新订单
- 订单详情查看
- 编辑订单信息
- 删除订单
- 订单状态管理

### 3. 客户管理
- 客户列表展示
- 添加新客户
- 编辑客户信息
- 删除客户
- 会员等级管理

### 4. 员工管理
- 员工列表展示
- 添加新员工
- 编辑员工信息
- 删除员工
- 员工状态管理

### 5. 类别管理
- 类别列表展示
- 添加新类别
- 编辑类别信息
- 删除类别
- 类别排序管理

## 数据库配置

### 创建数据库
```sql
CREATE DATABASE milktea_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 导入数据
执行`milktea_system.sql`文件，创建表结构并导入初始数据。

### 配置文件
修改`src/main/resources/db.properties`文件，配置数据库连接信息：

```properties
# 数据库连接配置
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/milktea_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=123456

# C3P0连接池配置
c3p0.initialPoolSize=5
c3p0.maxPoolSize=20
c3p0.minPoolSize=5
c3p0.maxIdleTime=1800
c3p0.acquireIncrement=5
c3p0.maxStatements=100
```

## 部署方式

### 使用Maven构建
1. 确保已安装Maven 3.6+和Java 17
2. 执行构建命令：
   ```bash
   mvn clean package
   ```
3. 将生成的WAR文件部署到Tomcat 9+服务器

### 手动部署
1. 编译Java源代码
2. 将编译后的class文件放入WEB-INF/classes目录
3. 将依赖的JAR文件放入WEB-INF/lib目录
4. 部署到Tomcat服务器

## 运行项目

1. 启动Tomcat服务器
2. 访问：http://localhost:8080/milktea-system/login.jsp
3. 使用默认账号登录：
   - 用户名：admin
   - 密码：123456

## 系统特点

1. **分层架构**：采用MVC架构模式，分层清晰，便于维护和扩展
2. **连接池技术**：使用C3P0连接池管理数据库连接，提高性能
3. **事务管理**：关键操作支持事务，确保数据一致性
4. **响应式设计**：前端页面采用响应式设计，适配不同设备
5. **安全性**：使用MD5加密存储密码，防止明文泄露
6. **用户友好**：界面简洁直观，操作方便

## 开发工具
- IntelliJ IDEA 2023.2
- MySQL Workbench 8.0
- Tomcat 9.0
- Maven 3.8.6

## 注意事项

1. 确保MySQL服务已启动
2. 确保Tomcat版本兼容（推荐9.0+）
3. 首次运行前请先导入数据库脚本
4. 如有问题，请检查数据库连接配置

## 许可证
MIT License
