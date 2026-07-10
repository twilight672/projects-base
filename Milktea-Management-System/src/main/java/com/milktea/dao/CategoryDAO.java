package com.milktea.dao;

import com.milktea.entity.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    /**
     * 根据ID查询分类
     *
     * @param categoryId 分类ID
     * @return 分类对象
     * @throws SQLException SQL异常
     */
    Category findById(Integer categoryId) throws SQLException;

    /**
     * 查询所有分类
     *
     * @return 分类列表
     * @throws SQLException SQL异常
     */
    List<Category> findAll() throws SQLException;

    /**
     * 添加分类
     *
     * @param category 分类对象
     * @return 添加的记录数
     * @throws SQLException SQL异常
     */
    int add(Category category) throws SQLException;

    /**
     * 更新分类
     *
     * @param category 分类对象
     * @return 更新的记录数
     * @throws SQLException SQL异常
     */
    int update(Category category) throws SQLException;

    /**
     * 删除分类
     *
     * @param categoryId 分类ID
     * @return 删除的记录数
     * @throws SQLException SQL异常
     */
    int delete(Integer categoryId) throws SQLException;
}