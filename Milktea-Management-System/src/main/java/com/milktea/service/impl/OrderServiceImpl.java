package com.milktea.service.impl;

import com.milktea.dao.OrderDAO;
import com.milktea.dao.impl.OrderDAOImpl;
import com.milktea.entity.Order;
import com.milktea.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public Order findById(Integer orderId) throws SQLException {
        return orderDAO.findById(orderId);
    }

    @Override
    public Order findByOrderNumber(String orderNumber) throws SQLException {
        return orderDAO.findByOrderNumber(orderNumber);
    }

    @Override
    public List<Order> findAll() throws SQLException {
        return orderDAO.findAll();
    }

    @Override
    public List<Order> findByEmployeeId(Integer employeeId) throws SQLException {
        return orderDAO.findByEmployeeId(employeeId);
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) throws SQLException {
        return orderDAO.findByCustomerId(customerId);
    }

    @Override
    public int createOrder(Order order) throws SQLException {
        return orderDAO.add(order);
    }

    @Override
    public boolean updateOrderStatus(Integer orderId, Integer status) throws SQLException {
        Order order = orderDAO.findById(orderId);
        if (order != null) {
            order.setOrderStatus(status);
            return orderDAO.update(order) > 0;
        }
        return false;
    }

    @Override
    public boolean deleteOrder(Integer orderId) throws SQLException {
        return orderDAO.delete(orderId) > 0;
    }
}