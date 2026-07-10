package com.caijing.test;

import java.util.Date;

/**
 * 学生实体类
 * 对应数据库中的students表
 * 存储学生的基本信息
 */
public class Student {
    private int id;
    private String name;
    private String password;
    private Date enrollDate;

    // 构造函数
    public Student(int id, String name, String password, Date enrollDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.enrollDate = enrollDate;
    }

    // Getter方法（仅提供读取访问）
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public Date getEnrollDate() { return enrollDate; }

    // 重写toString方法，用于调试和打印
    @Override
    public String toString() {
        return String.format("学号：%d, 姓名：%s, 入学日期：%s", id, name, enrollDate);
    }
}