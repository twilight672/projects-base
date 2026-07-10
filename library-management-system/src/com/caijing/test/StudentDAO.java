package com.caijing.test;

import java.sql.*;

/**
 * 学生数据访问对象(DAO)
 * 负责学生对象与数据库之间的交互
 */
public class StudentDAO {
    /**
     * 根据ID获取学生信息
     * @param id 学生ID
     * @return 学生对象，如果不存在则返回null
     */
    public Student getStudentById(int id) {
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getDate("enroll_date")
                );
                
                rs.close();
                pstmt.close();
                conn.close();
                return student;
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("获取学生信息失败：" + e.getMessage());
        }
        return null;
    }
}