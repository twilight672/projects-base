package com.milktea.servlet;

import com.milktea.entity.Customer;
import com.milktea.service.CustomerService;
import com.milktea.service.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCustomers(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            default:
                listCustomers(request, response);
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
                addCustomer(request, response);
                break;
            case "edit":
                updateCustomer(request, response);
                break;
            default:
                listCustomers(request, response);
                break;
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Customer> customers = customerService.findAll();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/customer/list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "查询客户列表失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/customer/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer customerId = Integer.parseInt(idStr);
                Customer customer = customerService.findById(customerId);
                if (customer != null) {
                    request.setAttribute("customer", customer);
                    request.getRequestDispatcher("/customer/edit.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "客户不存在");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的客户ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "查询客户失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "客户ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String memberLevel = request.getParameter("memberLevel");
        String pointsStr = request.getParameter("points");

        try {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setGender(genderStr != null ? Integer.parseInt(genderStr) : 1);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setMemberLevel(memberLevel != null ? memberLevel : "普通会员");
            customer.setPoints(pointsStr != null ? Integer.parseInt(pointsStr) : 0);

            boolean result = customerService.addCustomer(customer);
            if (result) {
                response.sendRedirect(request.getContextPath() + "/customer?action=list");
            } else {
                request.setAttribute("error", "添加客户失败，手机号可能已存在");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "添加客户失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的参数格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String memberLevel = request.getParameter("memberLevel");
        String pointsStr = request.getParameter("points");

        try {
            Integer customerId = Integer.parseInt(idStr);

            Customer customer = customerService.findById(customerId);
            if (customer != null) {
                customer.setName(name);
                customer.setGender(genderStr != null ? Integer.parseInt(genderStr) : 1);
                customer.setPhone(phone);
                customer.setEmail(email);
                customer.setAddress(address);
                customer.setMemberLevel(memberLevel != null ? memberLevel : "普通会员");
                customer.setPoints(pointsStr != null ? Integer.parseInt(pointsStr) : 0);

                boolean result = customerService.updateCustomer(customer);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/customer?action=list");
                } else {
                    request.setAttribute("error", "更新客户失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "客户不存在");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的参数格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "更新客户失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer customerId = Integer.parseInt(idStr);
                boolean result = customerService.deleteCustomer(customerId);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/customer?action=list");
                } else {
                    request.setAttribute("error", "删除客户失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的客户ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "删除客户失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "客户ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}