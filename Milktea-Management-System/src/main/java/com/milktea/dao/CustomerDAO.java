package com.milktea.dao;

import com.milktea.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    /**
     * 根据ID查询客户
     *
     * @param customerId 客户ID
     * @return 客户对象
     * @throws SQLException SQL异常
     */
    Customer findById(Integer customerId) throws SQLException;

    /**
     * 根据手机号查询客户
     *
     * @param phone 手机号
     * @return 客户对象
     * @throws SQLException SQL异常
     */
    Customer findByPhone(String phone) throws SQLException;

    /**
     * 查询所有客户
     *
     * @return 客户列表
     * @throws SQLException SQL异常
     */
    List<Customer> findAll() throws SQLException;

    /**
     * 添加客户
     *
     * @param customer 客户对象
     * @return 添加的记录数
     * @throws SQLException SQL异常
     */
    int add(Customer customer) throws SQLException;

    /**
     * 更新客户信息
     *
     * @param customer 客户对象
     * @return 更新的记录数
     * @throws SQLException SQL异常
     */
    int update(Customer customer) throws SQLException;

    /**
     * 删除客户
     *
     * @param customerId 客户ID
     * @return 删除的记录数
     * @throws SQLException SQL异常
     */
    int delete(Integer customerId) throws SQLException;
}