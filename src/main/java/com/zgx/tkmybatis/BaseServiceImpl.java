package com.zgx.tkmybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    @Autowired
    private MyMapper<T> mapper;

    /**
     * 根据id查询数据
     *
     */
    @Transactional(readOnly = true)
    @Override
    public T findById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     *
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> findAll() {
        return this.mapper.select(null);
    }

    /**
     * 根据条件查询一条数据，如果有多条数据会抛出异常
     *
     */
    @Transactional(readOnly = true)
    @Override
    public T findOne(T record) {
        return this.mapper.selectOne(record);
    }

    /**
     * 根据条件查询数据列表
     *
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> findListByWhere(T record) {
        return this.mapper.select(record);
    }

    /**
     * 分页查询
     *
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> findPageListByWhere(Integer pageNum, Integer pageSize, T record) {
        // 设置分页条件
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = this.findListByWhere(record);
        return new PageInfo<>(list);
    }

    /**
     * 新增数据，返回成功的条数
     *
     */
    @Override
    public Integer save(T record) {
        if (null == record.getCreate_time()) record.setCreate_time(LocalDateTime.now());
        if (null == record.getModify_time()) record.setModify_time(record.getCreate_time());
        return this.mapper.insert(record);
    }

    /**
     * 新增数据，使用不为null的字段，返回成功的条数
     *
     */
    @Override
    public Integer saveSelective(T record) {
        if (null == record.getCreate_time()) record.setCreate_time(LocalDateTime.now());
        if (null == record.getModify_time()) record.setModify_time(record.getCreate_time());
        return this.mapper.insertSelective(record);
    }

    @Override
    public int saveList(List<T> list) {
        return this.mapper.insertList(list);
    }

    /**
     * 修改数据，返回成功的条数
     *
     */
    @Override
    public Integer update(T record) {
        if (null == record.getModify_time()) record.setModify_time(LocalDateTime.now());
        return this.mapper.updateByPrimaryKey(record);
    }

    /**
     * 修改数据，使用不为null的字段，返回成功的条数
     *
     */
    @Override
    public Integer updateSelective(T record) {
        if (null == record.getModify_time()) record.setModify_time(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据id删除数据
     *
     */
    @Override
    public Integer deleteById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     */
    @Override
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
        return this.mapper.deleteByExample(example);
    }

    /**
     * 根据条件做删除
     *
     */
    @Override
    public Integer deleteByWhere(T record) {
        return this.mapper.delete(record);
    }

    /**
     * 自定义查询
     *
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

}

