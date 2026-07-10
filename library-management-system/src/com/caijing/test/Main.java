package com.caijing.test;

import java.util.List;
import java.util.Scanner;

/**
 * 学生图书管理系统
 * 功能：学生登录、图书浏览、借阅和归还
 */
public class Main {
    // 系统核心组件
    private static Scanner scanner = new Scanner(System.in);  // 控制台输入
    private static StudentDAO studentDAO = new StudentDAO();  // 学生数据访问
    private static BookDAO bookDAO = new BookDAO();          // 图书数据访问
    private static Student currentStudent = null;             // 当前登录学生

    public static void main(String[] args) {
        System.out.println("=== 学生图书管理系统 ===");
        
        // 主菜单循环
        while (true) {
            System.out.println("\n1. 学生登录");
            System.out.println("2. 查看图书列表");
            System.out.println("3. 退出");
            System.out.print("请选择: ");
            
            String choice = scanner.next();
            
            switch (choice) {
                case "1":
                    login();  // 学生登录
                    if (currentStudent != null) {
                        showStudentMenu();  // 登录成功后显示学生菜单
                    }
                    break;
                case "2":
                    viewBooks();  // 查看所有图书
                    break;
                case "3":
                    System.out.println("感谢使用，再见！");
                    return;  // 退出程序
                default:
                    System.out.println("无效选择");
            }
        }
    }

    /**
     * 学生登录功能
     * 验证学号和密码，设置当前登录学生
     */
    private static void login() {
        System.out.print("请输入学号：");
        int id = scanner.nextInt();
        System.out.print("请输入密码：");
        String password = scanner.next();

        Student student = studentDAO.getStudentById(id);
        if (student != null && student.getPassword().equals(password)) {
            currentStudent = student;
            System.out.println("登录成功！欢迎 " + student.getName());
        } else {
            System.out.println("学号或密码错误！");
        }
    }

    /**
     * 学生菜单
     * 提供借阅管理相关功能
     */
    private static void showStudentMenu() {
        while (true) {
            System.out.println("\n=== " + currentStudent.getName() + "的菜单 ===");
            System.out.println("1. 查看我的借阅情况");
            System.out.println("2. 借阅图书");
            System.out.println("3. 归还图书");
            System.out.println("4. 退出登录");
            System.out.print("请选择: ");
            
            String choice = scanner.next();
            
            switch (choice) {
                case "1":
                    viewMyBorrowedBooks();  // 查看已借阅图书
                    break;
                case "2":
                    borrowBook();  // 借阅新图书
                    break;
                case "3":
                    returnBook();  // 归还图书
                    break;
                case "4":
                    System.out.println("已退出登录");
                    currentStudent = null;  // 清除当前登录学生
                    return;
                default:
                    System.out.println("无效选择");
            }
        }
    }

    /**
     * 查看所有图书
     * 从数据库获取所有图书信息并显示
     */
    private static void viewBooks() {
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("暂无图书信息");
            return;
        }

        System.out.println("\n=== 图书列表 ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * 查看我的借阅情况
     * 显示当前学生已借阅的所有图书
     */
    private static void viewMyBorrowedBooks() {
        List<Book> books = bookDAO.getBooksBorrowedByStudent(currentStudent.getId());
        if (books.isEmpty()) {
            System.out.println("您当前没有借阅任何图书");
            return;
        }

        System.out.println("\n=== 我的借阅 ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * 借阅图书
     * 1. 显示所有图书
     * 2. 用户选择要借阅的图书
     * 3. 检查图书状态并更新
     */
    private static void borrowBook() {
        // 显示所有图书
        viewBooks();

        System.out.print("\n请输入要借阅的图书ID：");
        int bookId = scanner.nextInt();

        // 获取图书信息
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("图书不存在！");
            return;
        }

        // 检查图书是否可借
        if (book.isBorrowed()) {
            System.out.println("该图书已被借出！");
            return;
        }

        // 更新图书状态
        book.setBorrowed(true);
        book.setBorrowerId(currentStudent.getId());
        if (bookDAO.updateBook(book)) {
            System.out.println("借阅成功！");
        } else {
            System.out.println("借阅失败，请重试！");
        }
    }

    /**
     * 归还图书
     * 1. 显示当前学生借阅的图书
     * 2. 用户选择要归还的图书
     * 3. 检查图书状态并更新
     */
    private static void returnBook() {
        // 显示已借阅图书
        viewMyBorrowedBooks();

        System.out.print("\n请输入要归还的图书ID：");
        int bookId = scanner.nextInt();

        // 获取图书信息
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("图书不存在！");
            return;
        }

        // 检查是否是当前学生借阅的图书
        if (!book.isBorrowed() || book.getBorrowerId() != currentStudent.getId()) {
            System.out.println("这不是您借阅的图书！");
            return;
        }

        // 更新图书状态
        book.setBorrowed(false);
        book.setBorrowerId(null);
        if (bookDAO.updateBook(book)) {
            System.out.println("归还成功！");
        } else {
            System.out.println("归还失败，请重试！");
        }
    }
}