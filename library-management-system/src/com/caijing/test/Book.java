package com.caijing.test;

/**
 * todo
 * 图书实体类
 * 对应数据库中的books表
 * 存储图书的基本信息和借阅状态
 * varchar/char---> String
 * int ---> int
 * decimal(10,2) ---> double
 * boolean
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private double price;
    private boolean isBorrowed;
    private Integer borrowerId;

    // 构造函数
    public Book(int id, String title, String author, String publisher, double price, boolean isBorrowed, Integer borrowerId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.isBorrowed = isBorrowed;
        this.borrowerId = borrowerId;
    }

    // Getter方法
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public double getPrice() { return price; }
    public boolean isBorrowed() { return isBorrowed; }
    public Integer getBorrowerId() { return borrowerId; }

    // Setter方法
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }
    public void setBorrowerId(Integer borrowerId) { this.borrowerId = borrowerId; }

    @Override
    public String toString() {
        return String.format("图书ID：%d, 书名：%s, 作者：%s, 出版社：%s, 价格：%.2f, 状态：%s",
                id, title, author, publisher, price, isBorrowed ? "已借出" : "可借阅");
    }
}