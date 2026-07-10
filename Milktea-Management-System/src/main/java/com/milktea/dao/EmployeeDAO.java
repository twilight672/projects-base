package com.milktea.dao;

import com.milktea.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {
    /**
     * 根据用户名查询员工
     *
     * @param username 用户名
     * @return 员工对象
     * @throws SQLException SQL异常
     */
    Employee findByUsername(String username) throws SQLException;

    /**
     * 根据ID查询员工
     *
     * @param employeeId 员工ID
     * @return 员工对象
     * @throws SQLException SQL异常
     */
    Employee findById(Integer employeeId) throws SQLException;

    /**
     * 查询所有员工
     *
     * @return 员工列表
     * @throws SQLException SQL异常
     */
    List<Employee> findAll() throws SQLException;

    /**
     * 添加员工
     *
     * @param employee 员工对象
     * @return 添加的记录数
     * @throws SQLException SQL异常
     */
    int add(Employee employee) throws SQLException;

    /**
     * 更新员工信息
     *
     * @param employee 员工对象
     * @return 更新的记录数
     * @throws SQLException SQL异常
     */
    int update(Employee employee) throws SQLException;

    /**
     * 删除员工
     *
     * @param employeeId 员工ID
     * @return 删除的记录数
     * @throws SQLException SQL异常
     */
    int delete(Integer employeeId) throws SQLException;
}