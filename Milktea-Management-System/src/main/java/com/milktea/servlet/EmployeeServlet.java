package com.milktea.servlet;

import com.milktea.entity.Employee;
import com.milktea.service.EmployeeService;
import com.milktea.service.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeServlet extends HttpServlet {
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listEmployees(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteEmployee(request, response);
                break;
            default:
                listEmployees(request, response);
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
                addEmployee(request, response);
                break;
            case "edit":
                updateEmployee(request, response);
                break;
            default:
                listEmployees(request, response);
                break;
        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Employee> employees = employeeService.findAll();
            request.setAttribute("employees", employees);
            request.getRequestDispatcher("/employee/list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "查询员工列表失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/employee/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer employeeId = Integer.parseInt(idStr);
                Employee employee = employeeService.findById(employeeId);
                if (employee != null) {
                    request.setAttribute("employee", employee);
                    request.getRequestDispatcher("/employee/edit.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "员工不存在");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的员工ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "查询员工失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "员工ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String position = request.getParameter("position");
        String statusStr = request.getParameter("status");

        try {
            Employee employee = new Employee();
            employee.setUsername(username);
            employee.setPassword(password);
            employee.setName(name);
            employee.setGender(genderStr != null ? Integer.parseInt(genderStr) : 1);
            employee.setPhone(phone);
            employee.setEmail(email);
            employee.setPosition(position != null ? position : "服务员");
            employee.setJoinDate(new java.sql.Date(new java.util.Date().getTime()));
            employee.setRole(position != null && position.equals("管理员") ? 1 : 0);
            employee.setStatus(statusStr != null ? Integer.parseInt(statusStr) : 1);

            boolean result = employeeService.addEmployee(employee);
            if (result) {
                response.sendRedirect(request.getContextPath() + "/employee?action=list");
            } else {
                request.setAttribute("error", "添加员工失败，用户名可能已存在");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "添加员工失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的参数格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String position = request.getParameter("position");
        String statusStr = request.getParameter("status");

        try {
            Integer employeeId = Integer.parseInt(idStr);
            Integer status = Integer.parseInt(statusStr);

            Employee employee = employeeService.findById(employeeId);
            if (employee != null) {
                employee.setUsername(username);
                employee.setName(name);
                employee.setGender(genderStr != null ? Integer.parseInt(genderStr) : 1);
                employee.setPhone(phone);
                employee.setEmail(email);
                employee.setPosition(position != null ? position : "服务员");
                employee.setRole(position != null && position.equals("管理员") ? 1 : 0);
                employee.setStatus(status);

                String password = request.getParameter("password");
                if (password != null && !password.isEmpty()) {
                    employee.setPassword(password);
                }

                boolean result = employeeService.updateEmployee(employee);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/employee?action=list");
                } else {
                    request.setAttribute("error", "更新员工失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "员工不存在");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的参数格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "更新员工失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer employeeId = Integer.parseInt(idStr);
                boolean result = employeeService.deleteEmployee(employeeId);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/employee?action=list");
                } else {
                    request.setAttribute("error", "删除员工失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的员工ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "删除员工失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "员工ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}