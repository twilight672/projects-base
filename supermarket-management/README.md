# 小型超市管理系统

基于 Java Web（JSP + Servlet）技术栈开发的中小型超市管理系统，涵盖商品管理、购物车、订单处理、库存监控、用户权限等核心业务模块。

## 技术栈

| 层级       | 技术                                   |
| ---------- | -------------------------------------- |
| 后端       | Java Servlet + JSP（MVC 设计模式）     |
| 数据库     | MySQL 5.x / 8.x                       |
| 数据访问层 | Apache Commons DBUtils                 |
| 前端       | HTML5 + CSS3 + JavaScript + jQuery     |
| UI 框架    | Bootstrap + Layer + Layui             |
| 服务器     | Apache Tomcat 9.0                      |
| JDK        | JDK 1.8+                              |

## 功能模块

### 后台管理端

- **用户管理**：管理员对用户进行增删改查，区分管理员与普通用户角色
- **商品管理**：维护商品信息（名称、价格、图片、分类等）
- **商品大类管理**：管理商品分类，便于按类浏览
- **库存管理**：实时查看和更新商品库存数量
- **订单管理**：查看订单明细，跟踪订单状态

### 前台购物端

- **商品浏览**：按分类查看商品列表，查看商品详情
- **购物车**：加入购物车、修改数量、移除商品、查看合计
- **下单结算**：提交订单，自动生成订单编号和明细
- **用户中心**：查看个人订单记录和账户信息

## 项目结构

```
supermarket-management/
├── src/                          # Java 源代码
│   └── com/
│       ├── dao/                  # 数据访问层基类
│       ├── db/                  # 数据库连接工具类
│       ├── entity/              # 实体类（Shangpin、Yonghu、Dingdan 等）
│       ├── service/             # 业务逻辑层
│       │   └── */impl/          # 各业务模块实现类
│       ├── servlet/             # 控制层（Servlet）
│       └── util/                # 工具类（分页、JSON 等）
├── WebContent/                  # Web 资源目录
│   ├── WEB-INF/
│   │   ├── classes/             # 编译后的 class 文件
│   │   ├── lib/                 # 依赖 JAR 包
│   │   └── web.xml              # 部署描述符
│   ├── pages/                   # 后台管理页面
│   ├── css/                     # 前台样式文件
│   ├── js/                      # 前台脚本文件
│   ├── external/                # 第三方插件（Bootstrap、lazyLoad 等）
│   ├── images/                  # 图片资源
│   ├── upload/                  # 上传文件目录
│   ├── index.jsp                # 后台登录入口
│   ├── shopindex.jsp            # 前台购物首页
│   ├── shangpin.jsp             # 商品详情页
│   ├── shoppingcart.jsp         # 购物车页面
│   ├── login.jsp                # 前台登录页
│   ├── account.jsp              # 用户中心
│   └── createaccount.jsp         # 注册页
├── aproject.sql                 # 数据库初始化脚本
└── tomcat/                      # 内置 Tomcat 服务器
    └── apache-tomcat-9.0.109/
```

## 快速开始

### 1. 环境准备

- JDK 1.8 或以上
- MySQL 5.x / 8.x
- Apache Tomcat 9.0（项目已内置）

### 2. 数据库初始化

登录 MySQL 后执行项目根目录下的 SQL 脚本：

```sql
CREATE DATABASE aproject DEFAULT CHARACTER SET utf8;
USE aproject;
SOURCE C:/路径/aproject.sql;
```

数据库连接配置位于 `src/com/db/DBUnitHelper.java`：

- 数据库名：`aproject`
- 用户名：`root`
- 密码：`root`

如需修改，请编辑该文件中的连接参数。

### 3. 部署运行

#### 方式一：使用项目内置 Tomcat

1. 配置 `server.xml`（位于 `tomcat/apache-tomcat-9.0.109/conf/server.xml`），在 `<Host>` 节点内添加：

```xml
<Context path="/supermarket"
    docBase="C:\你的项目路径\supermarket-management\WebContent" />
```

2. 设置环境变量：

```powershell
$env:JAVA_HOME="你的JDK路径"
$env:CATALINA_HOME="Tomcat路径"
$env:CATALINA_OPTS="-Xms256m -Xmx512m"
```

3. 启动 Tomcat：

```powershell
& "$CATALINA_HOME\bin\startup.bat"
```

4. 浏览器访问：`http://localhost:8080/supermarket`

#### 方式二：导入 IDE 运行

1. 使用 Eclipse 导入为现有项目
2. 配置 Server 运行环境为 Tomcat 9.0
3. 将项目部署到 Tomcat 后启动

### 4. 默认账号

| 角色     | 用户名 | 密码 |
| -------- | ------ | ---- |
| 管理员   | sa     | 123  |
| 普通用户 | 123    | 123  |

## 核心业务流程

```
用户登录 → 浏览商品分类 → 查看商品详情 → 加入购物车
                                              ↓
                                        查看购物车 → 修改数量/移除
                                              ↓
                                        提交结算 → 生成订单
                                              ↓
                                        查看订单明细
```

## 关键配置说明

| 配置项             | 文件位置                        | 说明                          |
| ------------------ | ------------------------------- | ----------------------------- |
| 数据库连接         | `src/com/db/DBUnitHelper.java` | 修改数据库名、用户名、密码    |
| Tomcat 上下文路径  | `tomcat/.../conf/server.xml`    | 修改 Context 的 path 和 docBase |
| 欢迎页面           | `WebContent/WEB-INF/web.xml`   | 配置 welcome-file 列表        |
| 内存参数           | 环境变量 CATALINA_OPTS          | 调整 JVM 堆内存大小          |

## 常见问题

**Q: 启动报内存不足？**
设置环境变量 `CATALINA_OPTS="-Xms256m -Xmx512m"`。

**Q: 页面报 404？**
检查 `server.xml` 中 Context 的 `docBase` 路径是否指向正确的 WebContent 目录。

**Q: 数据库连接失败？**
确认 MySQL 已启动，数据库 `aproject` 已创建，连接参数与 `DBUnitHelper.java` 中一致。

**Q: 前台页面样式丢失？**
检查浏览器控制台是否有 404 资源加载失败，确认 `external/` 和 `css/` 目录下的静态文件完整。

## 依赖说明

| 依赖                       | 版本   | 用途                  |
| -------------------------- | ------ | --------------------- |
| mysql-connector-java       | 5.0.8  | MySQL JDBC 驱动       |
| commons-dbutils            | 1.6    | 数据库操作工具        |
| commons-beanutils          | 1.8.0  | JavaBean 操作工具     |
| json-lib                   | 2.4    | JSON 数据处理         |
| jstl                       | 1.2    | JSP 标准标签库        |
| servlet-api                | 4.0    | Servlet API（Tomcat 提供）|
