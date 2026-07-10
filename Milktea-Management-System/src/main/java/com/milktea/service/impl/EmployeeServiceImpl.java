package com.milktea.service.impl;

import com.milktea.dao.EmployeeDAO;
import com.milktea.dao.impl.EmployeeDAOImpl;
import com.milktea.entity.Employee;
import com.milktea.service.EmployeeService;
import com.milktea.util.StringUtil;

import java.sql.SQLException;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Override
    public Employee login(String username, String password) throws SQLException {
        Employee employee = employeeDAO.findByUsername(username);
        if (employee != null) {
            // 验证密码（使用MD5加密）
            String encryptedPassword = StringUtil.md5(password);
            if (employee.getPassword().equals(encryptedPassword)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public Employee findById(Integer employeeId) throws SQLException {
        return employeeDAO.findById(employeeId);
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        return employeeDAO.findAll();
    }

    @Override
    public boolean addEmployee(Employee employee) throws SQLException {
        // 检查用户名是否已存在
        if (checkUsernameExists(employee.getUsername())) {
            return false;
        }
        // 加密密码
        String encryptedPassword = StringUtil.md5(employee.getPassword());
        employee.setPassword(encryptedPassword);
        return employeeDAO.add(employee) > 0;
    }

    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        // 如果密码不为空，说明需要更新密码
        if (StringUtil.isNotEmpty(employee.getPassword())) {
            String encryptedPassword = StringUtil.md5(employee.getPassword());
            employee.setPassword(encryptedPassword);
        } else {
            // 不更新密码，获取原密码
            Employee oldEmployee = employeeDAO.findById(employee.getEmployeeId());
            employee.setPassword(oldEmployee.getPassword());
        }
        return employeeDAO.update(employee) > 0;
    }

    @Override
    public boolean deleteEmployee(Integer employeeId) throws SQLException {
        return employeeDAO.delete(employeeId) > 0;
    }

    @Override
    public boolean checkUsernameExists(String username) throws SQLException {
        Employee employee = employeeDAO.findByUsername(username);
        return employee != null;
    }
}