package com.milktea.servlet;

import com.milktea.entity.Order;
import com.milktea.service.CustomerService;
import com.milktea.service.EmployeeService;
import com.milktea.service.OrderService;
import com.milktea.service.ProductService;
import com.milktea.service.impl.CustomerServiceImpl;
import com.milktea.service.impl.EmployeeServiceImpl;
import com.milktea.service.impl.OrderServiceImpl;
import com.milktea.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderServlet extends HttpServlet {
    private OrderService orderService = new OrderServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();
    private EmployeeService employeeService = new EmployeeServiceImpl();
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listOrders(request, response);
                break;
            case "view":
                viewOrder(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "updateStatus":
                updateOrderStatus(request, response);
                break;
            case "delete":
                deleteOrder(request, response);
                break;
            default:
                listOrders(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                addOrder(request, response);
                break;
            default:
                listOrders(request, response);
                break;
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Order> orders = orderService.findAll();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/order/list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "查询订单列表失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void viewOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer orderId = Integer.parseInt(idStr);
                Order order = orderService.findById(orderId);
                if (order != null) {
                    request.setAttribute("order", order);
                    request.getRequestDispatcher("/order/view.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "订单不存在");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的订单ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "查询订单失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "订单ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("customers", customerService.findAll());
            request.setAttribute("employees", employeeService.findAll());
            request.setAttribute("products", productService.findActiveProducts());
            request.getRequestDispatcher("/order/add.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "加载添加表单失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 这里需要实现订单的添加逻辑，包括订单基本信息和订单明细
        // 由于订单添加涉及到复杂的表单处理，这里只提供基本框架
        request.setAttribute("error", "订单添加功能正在开发中");
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String statusStr = request.getParameter("status");
        if (idStr != null && !idStr.isEmpty() && statusStr != null && !statusStr.isEmpty()) {
            try {
                Integer orderId = Integer.parseInt(idStr);
                Integer status = Integer.parseInt(statusStr);
                boolean result = orderService.updateOrderStatus(orderId, status);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/order?action=list");
                } else {
                    request.setAttribute("error", "更新订单状态失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的参数格式");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "更新订单状态失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "参数不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer orderId = Integer.parseInt(idStr);
                boolean result = orderService.deleteOrder(orderId);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/order?action=list");
                } else {
                    request.setAttribute("error", "删除订单失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的订单ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "删除订单失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "订单ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}