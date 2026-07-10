package com.milktea.servlet;

import com.milktea.entity.Category;
import com.milktea.service.CategoryService;
import com.milktea.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoryServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCategories(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCategory(request, response);
                break;
            default:
                listCategories(request, response);
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
                addCategory(request, response);
                break;
            case "edit":
                updateCategory(request, response);
                break;
            default:
                listCategories(request, response);
                break;
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categories = categoryService.findAll();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/category/list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "查询分类列表失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/category/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer categoryId = Integer.parseInt(idStr);
                Category category = categoryService.findById(categoryId);
                if (category != null) {
                    request.setAttribute("category", category);
                    request.getRequestDispatcher("/category/edit.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "分类不存在");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的分类ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "查询分类失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "分类ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");

        try {
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setDescription(description);

            boolean result = categoryService.addCategory(category);
            if (result) {
                response.sendRedirect(request.getContextPath() + "/category?action=list");
            } else {
                request.setAttribute("error", "添加分类失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "添加分类失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("categoryId");
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");

        try {
            Integer categoryId = Integer.parseInt(idStr);

            Category category = categoryService.findById(categoryId);
            if (category != null) {
                category.setCategoryName(categoryName);
                category.setDescription(description);

                boolean result = categoryService.updateCategory(category);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/category?action=list");
                } else {
                    request.setAttribute("error", "更新分类失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "分类不存在");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的参数格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "更新分类失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer categoryId = Integer.parseInt(idStr);
                boolean result = categoryService.deleteCategory(categoryId);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/category?action=list");
                } else {
                    request.setAttribute("error", "删除分类失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的分类ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "删除分类失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "分类ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}