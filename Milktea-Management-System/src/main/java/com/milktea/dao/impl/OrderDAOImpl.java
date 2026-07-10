package com.milktea.dao.impl;

import com.milktea.dao.OrderDAO;
import com.milktea.entity.Order;
import com.milktea.entity.OrderItem;
import com.milktea.util.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private QueryRunner queryRunner = new QueryRunner(DBUtil.getDataSource());

    @Override
    public Order findById(Integer orderId) throws SQLException {
        String sql = "SELECT * FROM `order` WHERE order_id = ?";
        Order order = queryRunner.query(sql, new BeanHandler<>(Order.class), orderId);
        if (order != null) {
            // 查询订单明细
            String itemSql = "SELECT * FROM order_item WHERE order_id = ?";
            List<OrderItem> orderItems = queryRunner.query(itemSql, new BeanListHandler<>(OrderItem.class), orderId);
            order.setOrderItems(orderItems);
        }
        return order;
    }

    @Override
    public Order findByOrderNumber(String orderNumber) throws SQLException {
        String sql = "SELECT * FROM `order` WHERE order_number = ?";
        Order order = queryRunner.query(sql, new BeanHandler<>(Order.class), orderNumber);
        if (order != null) {
            // 查询订单明细
            String itemSql = "SELECT * FROM order_item WHERE order_id = ?";
            List<OrderItem> orderItems = queryRunner.query(itemSql, new BeanListHandler<>(OrderItem.class), order.getOrderId());
            order.setOrderItems(orderItems);
        }
        return order;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        String sql = "SELECT * FROM `order` ORDER BY create_time DESC";
        return queryRunner.query(sql, new BeanListHandler<>(Order.class));
    }

    @Override
    public List<Order> findByEmployeeId(Integer employeeId) throws SQLException {
        String sql = "SELECT * FROM `order` WHERE employee_id = ? ORDER BY create_time DESC";
        return queryRunner.query(sql, new BeanListHandler<>(Order.class), employeeId);
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) throws SQLException {
        String sql = "SELECT * FROM `order` WHERE customer_id = ? ORDER BY create_time DESC";
        return queryRunner.query(sql, new BeanListHandler<>(Order.class), customerId);
    }

    @Override
    public int add(Order order) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);

            // 添加订单
            String sql = "INSERT INTO `order` (order_number, customer_id, employee_id, total_amount, payment_method, order_status) VALUES (?, ?, ?, ?, ?, ?)";
            Number number = queryRunner.insert(connection, sql, new ScalarHandler<>(),
                    order.getOrderNumber(), order.getCustomerId(), order.getEmployeeId(),
                    order.getTotalAmount(), order.getPaymentMethod(), order.getOrderStatus());
            int orderId = number.intValue();

            // 添加订单明细
            if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                String itemSql = "INSERT INTO order_item (order_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";
                Object[][] params = new Object[order.getOrderItems().size()][5];
                for (int i = 0; i < order.getOrderItems().size(); i++) {
                    OrderItem item = order.getOrderItems().get(i);
                    params[i][0] = orderId;
                    params[i][1] = item.getProductId();
                    params[i][2] = item.getQuantity();
                    params[i][3] = item.getUnitPrice();
                    params[i][4] = item.getSubtotal();
                }
                queryRunner.batch(connection, itemSql, params);
            }

            connection.commit();
            return orderId;
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                DBUtil.closeConnection(connection);
            }
        }
    }

    @Override
    public int update(Order order) throws SQLException {
        String sql = "UPDATE `order` SET customer_id = ?, employee_id = ?, total_amount = ?, payment_method = ?, order_status = ? WHERE order_id = ?";
        return queryRunner.update(sql, order.getCustomerId(), order.getEmployeeId(),
                order.getTotalAmount(), order.getPaymentMethod(), order.getOrderStatus(), order.getOrderId());
    }

    @Override
    public int delete(Integer orderId) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);

            // 删除订单明细
            String itemSql = "DELETE FROM order_item WHERE order_id = ?";
            queryRunner.update(connection, itemSql, orderId);

            // 删除订单
            String orderSql = "DELETE FROM `order` WHERE order_id = ?";
            int result = queryRunner.update(connection, orderSql, orderId);

            connection.commit();
            return result;
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                DBUtil.closeConnection(connection);
            }
        }
    }
}