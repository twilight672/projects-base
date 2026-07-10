package com.milktea.dao.impl;

import com.milktea.dao.EmployeeDAO;
import com.milktea.entity.Employee;
import com.milktea.util.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private QueryRunner queryRunner = new QueryRunner(DBUtil.getDataSource());

    @Override
    public Employee findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM employee WHERE username = ?";
        return queryRunner.query(sql, new BeanHandler<>(Employee.class), username);
    }

    @Override
    public Employee findById(Integer employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        return queryRunner.query(sql, new BeanHandler<>(Employee.class), employeeId);
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT * FROM employee ORDER BY employee_id";
        return queryRunner.query(sql, new BeanListHandler<>(Employee.class));
    }

    @Override
    public int add(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (username, password, name, gender, phone, email, position, join_date, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return queryRunner.update(sql, employee.getUsername(), employee.getPassword(), employee.getName(),
                employee.getGender(), employee.getPhone(), employee.getEmail(), employee.getPosition(),
                employee.getJoinDate(), employee.getRole(), employee.getStatus());
    }

    @Override
    public int update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET username = ?, password = ?, name = ?, gender = ?, phone = ?, email = ?, position = ?, join_date = ?, role = ?, status = ? WHERE employee_id = ?";
        return queryRunner.update(sql, employee.getUsername(), employee.getPassword(), employee.getName(),
                employee.getGender(), employee.getPhone(), employee.getEmail(), employee.getPosition(),
                employee.getJoinDate(), employee.getRole(), employee.getStatus(), employee.getEmployeeId());
    }

    @Override
    public int delete(Integer employeeId) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        return queryRunner.update(sql, employeeId);
    }
}