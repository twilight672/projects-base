# 项目集合仓库

本仓库包含多个独立的管理系统与应用项目，涵盖 Java Web、PHP、前端等多种技术栈，适合学习参考和二次开发。

## 项目概览

| 项目名称 | 技术栈 | 类型 | 简介 |
| :--- | :--- | :--- | :--- |
| 学生图书管理系统 | Java + MySQL | 控制台应用 | 面向学生的图书借阅管理系统 |
| 奶茶管理系统 | Java Web + MySQL | Web应用 | 奶茶店产品、订单、客户、员工管理 |
| 小型超市管理系统 | Java Web + MySQL | Web应用 | 商品管理、购物车、订单处理、库存监控 |
| 五子棋游戏 | PHP + JavaScript | Web应用 | 网页版五子棋，支持AI对战、积分排行 |
| 书店管理系统 | Vue.js | 前端项目 | 基于Vue的书店管理系统 |

---

## 学生图书管理系统

基于 Java + MySQL 的控制台版图书管理系统，支持学生登录、浏览图书、借阅与归还功能。

**技术栈**：Java + MySQL + DAO 分层设计

**运行方式**：
```bash
javac -encoding UTF-8 -cp "lib/mysql-connector-j-9.3.0.jar" -d out src/com/caijing/test/*.java
java -cp "out;lib/mysql-connector-j-9.3.0.jar" com.caijing.test.Main
```

---

## 奶茶管理系统

基于 Java Web 开发的奶茶店管理系统，实现产品、订单、客户、员工和类别管理功能。

**技术栈**：Java 17 + Servlet + JSP + MySQL + C3P0

**运行方式**：
```bash
mvn clean package
```

---

## 小型超市管理系统

基于 Java Web（JSP + Servlet）的中小型超市管理系统，涵盖商品管理、购物车、订单处理、库存监控。

**技术栈**：Java Servlet + JSP + MySQL + Bootstrap + Layui

---

## 五子棋游戏

基于 PHP 和原生 JavaScript 的网页版五子棋游戏，支持用户注册登录、AI 对战、积分排行榜。

**技术栈**：PHP + HTML5 + CSS3 + JavaScript

**运行方式**：
```bash
cd goban-game
php -S localhost:8000
```

---

## 书店管理系统

基于 Vue.js 开发的书店管理系统。

**技术栈**：Vue.js + Vue CLI

**运行方式**：
```bash
npm install && npm run serve
```

---

## 联系我

如果你对某个项目感兴趣，或有合作机会，欢迎联系我！

GitHub: [twillight672](https://github.com/twillight672)
邮箱: aski672@foxmail.com

---

## 许可证

MIT License
