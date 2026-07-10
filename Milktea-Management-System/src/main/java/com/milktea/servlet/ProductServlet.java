package com.milktea.servlet;

import com.milktea.entity.Product;
import com.milktea.service.CategoryService;
import com.milktea.service.ProductService;
import com.milktea.service.impl.CategoryServiceImpl;
import com.milktea.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductServlet extends HttpServlet {
    private ProductService productService = new ProductServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listProducts(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            case "activate":
                activateProduct(request, response);
                break;
            case "deactivate":
                deactivateProduct(request, response);
                break;
            default:
                listProducts(request, response);
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
                addProduct(request, response);
                break;
            case "edit":
                updateProduct(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> products = productService.findAll();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/product/list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "查询产品列表失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("categories", categoryService.findAll());
            request.getRequestDispatcher("/product/add.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "加载添加表单失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer productId = Integer.parseInt(idStr);
                Product product = productService.findById(productId);
                if (product != null) {
                    request.setAttribute("product", product);
                    request.setAttribute("categories", categoryService.findAll());
                    request.getRequestDispatcher("/product/edit.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "产品不存在");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的产品ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "查询产品失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "产品ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String categoryIdStr = request.getParameter("categoryId");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");

        try {
            Integer categoryId = Integer.parseInt(categoryIdStr);
            Double price = Double.parseDouble(priceStr);

            Product product = new Product();
            product.setProductName(productName);
            product.setCategoryId(categoryId);
            product.setPrice(price);
            product.setDescription(description);
            product.setImageUrl(imageUrl);
            product.setStatus(1); // 默认在售

            boolean result = productService.addProduct(product);
            if (result) {
                response.sendRedirect(request.getContextPath() + "/product?action=list");
            } else {
                request.setAttribute("error", "添加产品失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的数字格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "添加产品失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String categoryIdStr = request.getParameter("categoryId");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");

        try {
            Integer productId = Integer.parseInt(idStr);
            Integer categoryId = Integer.parseInt(categoryIdStr);
            Double price = Double.parseDouble(priceStr);

            Product product = productService.findById(productId);
            if (product != null) {
                product.setProductName(productName);
                product.setCategoryId(categoryId);
                product.setPrice(price);
                product.setDescription(description);
                product.setImageUrl(imageUrl);

                boolean result = productService.updateProduct(product);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/product?action=list");
                } else {
                    request.setAttribute("error", "更新产品失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "产品不存在");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的数字格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "更新产品失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer productId = Integer.parseInt(idStr);
                boolean result = productService.deleteProduct(productId);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/product?action=list");
                } else {
                    request.setAttribute("error", "删除产品失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的产品ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "删除产品失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "产品ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void activateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer productId = Integer.parseInt(idStr);
                boolean result = productService.activateProduct(productId);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/product?action=list");
                } else {
                    request.setAttribute("error", "激活产品失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的产品ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "激活产品失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "产品ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void deactivateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Integer productId = Integer.parseInt(idStr);
                boolean result = productService.deactivateProduct(productId);
                if (result) {
                    response.sendRedirect(request.getContextPath() + "/product?action=list");
                } else {
                    request.setAttribute("error", "下架产品失败");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "无效的产品ID");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "下架产品失败");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "产品ID不能为空");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}