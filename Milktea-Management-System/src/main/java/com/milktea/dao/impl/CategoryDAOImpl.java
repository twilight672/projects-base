package com.milktea.dao.impl;

import com.milktea.dao.CategoryDAO;
import com.milktea.entity.Category;
import com.milktea.util.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    private QueryRunner queryRunner = new QueryRunner(DBUtil.getDataSource());

    @Override
    public Category findById(Integer categoryId) throws SQLException {
        String sql = "SELECT * FROM category WHERE category_id = ?";
        return queryRunner.query(sql, new BeanHandler<>(Category.class), categoryId);
    }

    @Override
    public List<Category> findAll() throws SQLException {
        String sql = "SELECT * FROM category ORDER BY category_id";
        return queryRunner.query(sql, new BeanListHandler<>(Category.class));
    }

    @Override
    public int add(Category category) throws SQLException {
        String sql = "INSERT INTO category (category_name, description) VALUES (?, ?)";
        return queryRunner.update(sql, category.getCategoryName(), category.getDescription());
    }

    @Override
    public int update(Category category) throws SQLException {
        String sql = "UPDATE category SET category_name = ?, description = ? WHERE category_id = ?";
        return queryRunner.update(sql, category.getCategoryName(), category.getDescription(), category.getCategoryId());
    }

    @Override
    public int delete(Integer categoryId) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id = ?";
        return queryRunner.update(sql, categoryId);
    }
}