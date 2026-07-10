package com.milktea.service;

import com.milktea.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    /**
     * 根据ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单对象
     * @throws SQLException SQL异常
     */
    Order findById(Integer orderId) throws SQLException;

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单号
     * @return 订单对象
     * @throws SQLException SQL异常
     */
    Order findByOrderNumber(String orderNumber) throws SQLException;

    /**
     * 查询所有订单
     *
     * @return 订单列表
     * @throws SQLException SQL异常
     */
    List<Order> findAll() throws SQLException;

    /**
     * 根据员工ID查询订单
     *
     * @param employeeId 员工ID
     * @return 订单列表
     * @throws SQLException SQL异常
     */
    List<Order> findByEmployeeId(Integer employeeId) throws SQLException;

    /**
     * 根据客户ID查询订单
     *
     * @param customerId 客户ID
     * @return 订单列表
     * @throws SQLException SQL异常
     */
    List<Order> findByCustomerId(Integer customerId) throws SQLException;

    /**
     * 创建订单
     *
     * @param order 订单对象
     * @return 创建成功返回订单ID，失败返回-1
     * @throws SQLException SQL异常
     */
    int createOrder(Order order) throws SQLException;

    /**
     * 更新订单状态
     *
     * @param orderId 订单ID
     * @param status 新状态
     * @return 更新成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean updateOrderStatus(Integer orderId, Integer status) throws SQLException;

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 删除成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean deleteOrder(Integer orderId) throws SQLException;
}