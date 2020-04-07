package cn.chay.service.impl;

import cn.chay.datasource.DataSource;
import cn.chay.datasource.DataSourceEnum;
import cn.chay.mapper.MyBaseMapper;
import cn.chay.service.MyBaseService;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chay
 * @date 2020/4/7 11:24
 */
public class MyBaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements MyBaseService<T> {

    /**
     * 新增和修改我们使用dataSource1
     *
     * @param entity
     * @return
     */
    @DataSource(DataSourceEnum.DB1)
    @Override
    public boolean insert(T entity) {
        return super.insert(entity);
    }

    @DataSource(DataSourceEnum.DB1)
    @Override
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }

    @DataSource(DataSourceEnum.DB1)
    @Override
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }

    @DataSource(DataSourceEnum.DB2)
    @Override
    public T selectById(Serializable id) {
        return super.selectById(id);
    }

    @DataSource(DataSourceEnum.DB2)
    @Override
    public List<T> selectList(Wrapper<T> wrapper) {
        return super.selectList(wrapper);
    }

    @DataSource(DataSourceEnum.DB2)
    @Override
    public Page<T> selectPage(Page<T> page) {
        return super.selectPage(page);
    }

    @DataSource(DataSourceEnum.DB2)
    @Override
    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        return super.selectPage(page, wrapper);
    }
}
