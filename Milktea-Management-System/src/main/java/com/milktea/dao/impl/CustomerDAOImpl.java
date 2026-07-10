package com.milktea.dao.impl;

import com.milktea.dao.CustomerDAO;
import com.milktea.entity.Customer;
import com.milktea.util.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private QueryRunner queryRunner = new QueryRunner(DBUtil.getDataSource());

    @Override
    public Customer findById(Integer customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        return queryRunner.query(sql, new BeanHandler<>(Customer.class), customerId);
    }

    @Override
    public Customer findByPhone(String phone) throws SQLException {
        String sql = "SELECT * FROM customer WHERE phone = ?";
        return queryRunner.query(sql, new BeanHandler<>(Customer.class), phone);
    }

    @Override
    public List<Customer> findAll() throws SQLException {
        String sql = "SELECT * FROM customer ORDER BY customer_id";
        return queryRunner.query(sql, new BeanListHandler<>(Customer.class));
    }

    @Override
    public int add(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (name, gender, phone, email, address, member_level, points) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return queryRunner.update(sql, customer.getName(), customer.getGender(), customer.getPhone(), customer.getEmail(), customer.getAddress(), customer.getMemberLevel(), customer.getPoints());
    }

    @Override
    public int update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name = ?, gender = ?, phone = ?, email = ?, address = ?, member_level = ?, points = ? WHERE customer_id = ?";
        return queryRunner.update(sql, customer.getName(), customer.getGender(), customer.getPhone(), customer.getEmail(), customer.getAddress(), customer.getMemberLevel(), customer.getPoints(), customer.getCustomerId());
    }

    @Override
    public int delete(Integer customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        return queryRunner.update(sql, customerId);
    }
}