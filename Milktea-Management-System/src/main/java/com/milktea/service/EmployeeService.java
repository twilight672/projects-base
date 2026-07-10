package com.milktea.service;

import com.milktea.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {
    /**
     * 员工登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的员工对象，失败返回null
     * @throws SQLException SQL异常
     */
    Employee login(String username, String password) throws SQLException;

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
     * @return 添加成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean addEmployee(Employee employee) throws SQLException;

    /**
     * 更新员工信息
     *
     * @param employee 员工对象
     * @return 更新成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean updateEmployee(Employee employee) throws SQLException;

    /**
     * 删除员工
     *
     * @param employeeId 员工ID
     * @return 删除成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean deleteEmployee(Integer employeeId) throws SQLException;

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return 存在返回true，不存在返回false
     * @throws SQLException SQL异常
     */
    boolean checkUsernameExists(String username) throws SQLException;
}