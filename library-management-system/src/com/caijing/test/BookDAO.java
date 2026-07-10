package com.caijing.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 图书数据访问对象(DAO)
 * 负责图书对象与数据库之间的交互
 */
public class BookDAO {
    /**
     * 获取所有图书信息
     * @return 图书列表
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Connection conn = DBUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getDouble("price"),
                    rs.getBoolean("is_borrowed"),
                    rs.getObject("borrower_id") != null ? rs.getInt("borrower_id") : null
                ));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("获取图书列表失败：" + e.getMessage());
        }
        return books;
    }

    /**
     * 根据ID获取图书信息
     * @param id 图书ID
     * @return 图书对象，如果不存在则返回null
     */
    public Book getBookById(int id) {
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getDouble("price"),
                    rs.getBoolean("is_borrowed"),
                    rs.getObject("borrower_id") != null ? rs.getInt("borrower_id") : null
                );
                
                rs.close();
                pstmt.close();
                conn.close();
                return book;
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("获取图书信息失败：" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取指定学生借阅的图书
     * @param studentId 学生ID
     * @return 图书列表
     */
    public List<Book> getBooksBorrowedByStudent(int studentId) {
        List<Book> books = new ArrayList<>();
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE borrower_id = ?");
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getDouble("price"),
                    rs.getBoolean("is_borrowed"),
                    rs.getInt("borrower_id")
                ));
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("获取借阅图书列表失败：" + e.getMessage());
        }
        return books;
    }

    /**
     * 更新图书信息
     * @param book 要更新的图书对象
     * @return 更新成功返回true，失败返回false
     */
    public boolean updateBook(Book book) {
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE books SET is_borrowed = ?, borrower_id = ? WHERE id = ?"
            );
            pstmt.setBoolean(1, book.isBorrowed());
            if (book.getBorrowerId() != null) {
                pstmt.setInt(2, book.getBorrowerId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setInt(3, book.getId());
            
            int rows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("更新图书信息失败：" + e.getMessage());
            return false;
        }
    }
}