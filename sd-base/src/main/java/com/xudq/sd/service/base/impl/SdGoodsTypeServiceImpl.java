package com.xudq.sd.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.entity.base.SdGoodsType;
import com.xudq.sd.mapper.base.SdGoodsTypeMapper;
import com.xudq.sd.service.base.ISdGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 货物类型管理服务接口实现
 */
@Service
public class SdGoodsTypeServiceImpl extends ServiceImpl<SdGoodsTypeMapper, SdGoodsType> implements ISdGoodsTypeService {
    @Autowired
    private CustomIdGenerator idGenerator;
    /**
     * 保存货物类型
     * @param sdGoodsType
     * @return
     */
    @Override
    public SdGoodsType saveGoodsType(SdGoodsType sdGoodsType) {
        sdGoodsType.setId(idGenerator.nextId(sdGoodsType) + "");
        baseMapper.insert(sdGoodsType);
        return sdGoodsType;
    }

    /**
     * 查询所有货物类型
     * @return
     */
    @Override
    public List<SdGoodsType> findAll() {
        QueryWrapper<SdGoodsType> queryWrapper = new QueryWrapper<>();
        //添加条件，根据status查询
        queryWrapper.eq("status",1);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 分页查询货物类型
     * @param page
     * @param pageSize
     * @param name
     * @param truckTypeId
     * @param truckTypeName
     * @return
     */
    @Override
    public IPage<SdGoodsType> findByPage(Integer page, Integer pageSize, String name, String truckTypeId, String truckTypeName) {
        //封装分页条件
        Page<SdGoodsType> sdPage = new Page<>(page,pageSize);
        //排序条件
        sdPage.addOrder(OrderItem.asc("id"));
        sdPage.setRecords(baseMapper.findByPage(sdPage,name,truckTypeId,truckTypeName));
        return sdPage;
    }


    /**
     * 查询货物类型列表
     * @param ids
     * @return
     */
    @Override
    public List<SdGoodsType> findByIds(List<String> ids) {
        LambdaQueryWrapper<SdGoodsType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(ids != null && ids.size() > 0){
            //添加查询条件
            lambdaQueryWrapper.in(com.xudq.sd.entity.base.SdGoodsType::getId,ids);
        }
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
