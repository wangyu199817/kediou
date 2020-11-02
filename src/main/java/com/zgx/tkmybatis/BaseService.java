package com.zgx.tkmybatis;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    /**
     * 根据id查询数据
     *
     */
    T findById(Long id);

    /**
     * 查询所有数据
     *
     */
    List<T> findAll();

    /**
     * 根据条件查询一条数据，如果有多条数据会抛出异常
     *
     */
    T findOne(T record);

    /**
     * 根据条件查询数据列表
     *
     */
    List<T> findListByWhere(T record);

    /**
     * 分页查询
     *
     */
    PageInfo<T> findPageListByWhere(Integer pageNum, Integer pageSize, T record);

    /**
     * 新增数据，返回成功的条数
     *
     */
    Integer save(T record);

    /**
     * 新增数据，使用不为null的字段，返回成功的条数
     *
     */
    Integer saveSelective(T record);

    /**
     * 批量保存，返回保存的条数
     *
     */
    int saveList(List<T> list);

    /**
     * 修改数据，返回成功的条数
     *
     */
    Integer update(T record);

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     *
     */
    Integer updateSelective(T record);

    /**
     * 根据id删除数据
     *
     */
    Integer deleteById(Long id);

    /**
     * 批量删除
     *
     */
    Integer deleteByIds(Class<T> clazz, String property, List<Object> values);

    /**
     * 根据条件做删除
     *
     */
    Integer deleteByWhere(T record);

    /**
     * 自定义查询
     *
     */
    List<T> selectByExample(Object example);
}

