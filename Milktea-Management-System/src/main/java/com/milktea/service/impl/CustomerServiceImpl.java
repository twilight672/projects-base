package com.milktea.service.impl;

import com.milktea.dao.CustomerDAO;
import com.milktea.dao.impl.CustomerDAOImpl;
import com.milktea.entity.Customer;
import com.milktea.service.CustomerService;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public Customer findById(Integer customerId) throws SQLException {
        return customerDAO.findById(customerId);
    }

    @Override
    public Customer findByPhone(String phone) throws SQLException {
        return customerDAO.findByPhone(phone);
    }

    @Override
    public List<Customer> findAll() throws SQLException {
        return customerDAO.findAll();
    }

    @Override
    public boolean addCustomer(Customer customer) throws SQLException {
        // 检查手机号是否已存在
        Customer existingCustomer = customerDAO.findByPhone(customer.getPhone());
        if (existingCustomer != null) {
            return false; // 手机号已存在
        }
        return customerDAO.add(customer) > 0;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        return customerDAO.update(customer) > 0;
    }

    @Override
    public boolean deleteCustomer(Integer customerId) throws SQLException {
        return customerDAO.delete(customerId) > 0;
    }

    @Override
    public boolean addPoints(Integer customerId, Integer points) throws SQLException {
        Customer customer = customerDAO.findById(customerId);
        if (customer != null) {
            customer.setPoints(customer.getPoints() + points);
            return customerDAO.update(customer) > 0;
        }
        return false;
    }
}