package com.milktea.service;

import com.milktea.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
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
     * @return 添加成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean addCustomer(Customer customer) throws SQLException;

    /**
     * 更新客户信息
     *
     * @param customer 客户对象
     * @return 更新成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean updateCustomer(Customer customer) throws SQLException;

    /**
     * 删除客户
     *
     * @param customerId 客户ID
     * @return 删除成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean deleteCustomer(Integer customerId) throws SQLException;

    /**
     * 为客户添加积分
     *
     * @param customerId 客户ID
     * @param points 要添加的积分
     * @return 操作成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean addPoints(Integer customerId, Integer points) throws SQLException;
}