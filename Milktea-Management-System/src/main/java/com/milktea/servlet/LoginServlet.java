package com.milktea.servlet;

import com.milktea.entity.Employee;
import com.milktea.service.EmployeeService;
import com.milktea.service.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Employee employee = employeeService.login(username, password);
            if (employee != null) {
                HttpSession session = request.getSession();
                session.setAttribute("employee", employee);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "登录失败，请联系管理员");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}