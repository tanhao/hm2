package com.xzy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通用增删改查
 *
 * @param <T> 实体bean
 */
public abstract class BaseService<T> {
    @Autowired
    protected Mapper<T> mapper;

    /**
     * 所有表数据
     *
     * @return 结合分页返回对应条数
     */
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    /**
     * Example查询数据
     * Example example = new Example(T.class);
     * Example.Criteria criteria = example.createCriteria();
     *
     * @return 结合分页返回对应条数
     */
    public List<T> select(Example example) {
        return mapper.selectByExample(example);
    }

    /**
     * 根据实体类返回单个记录
     *
     * @param t
     * @return 最多只有一条记录，如果多条返回会报错
     */
    public T selectOne(T t) {
        if (null == t) return null;
        return mapper.selectOne(t);
    }

    /**
     * 实体类查询多条记录
     *
     * @param t
     * @return 结合分页返回对应条数
     */
    public List<T> select(T t) {
        if (null == t) return null;
        return mapper.select(t);
    }

    /**
     * id查询
     *
     * @param key 表主键
     * @return 最多只有一条记录
     */
    public T selectByPrimaryKey(Object key) {
        if (null == key) return null;
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 保存单个记录
     *
     * @param t
     * @return 保存记录数量
     */
    public int insertSelective(T t) {
        if (null == t) return 0;
        return mapper.insertSelective(t);
    }

    /**
     * id删除(一般不会使用，把记录标记位del=0)
     *
     * @param key 表主键
     * @return 删除记录数量
     */
    public int deleteByPrimaryKey(Object key) {
        if (null == key) return 0;
        return mapper.deleteByPrimaryKey(key);
    }

    public int delete(T t){
        if (null == t) return 0;
        return mapper.delete(t);
    }

    /**
     * 修改实体类中不为空的值
     * @param t
     * @return 修改的条数
     */
    public int updateByPrimaryKeySelective(T t) {
        if (null == t) return 0;
        return mapper.updateByPrimaryKeySelective(t);
    }



    /////还有mapper.selectCount()等方法，如果用到，在本java内添加方法.

}
