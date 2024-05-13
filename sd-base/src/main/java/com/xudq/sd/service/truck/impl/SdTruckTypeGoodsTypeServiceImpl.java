package com.xudq.sd.service.truck.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.mapper.truck.SdTruckTypeGoodsTypeMapper;
import com.xudq.sd.entity.truck.SdTruckTypeGoodsType;

import com.xudq.sd.service.truck.ISdTruckTypeGoodsTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 车辆类型与货物类型关联表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTruckTypeGoodsTypeServiceImpl extends ServiceImpl<SdTruckTypeGoodsTypeMapper, SdTruckTypeGoodsType>
        implements ISdTruckTypeGoodsTypeService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public void saveTruckTypeGoodsType(SdTruckTypeGoodsType sdTruckTypeGoodsType) {
        sdTruckTypeGoodsType.setId(idGenerator.nextId(sdTruckTypeGoodsType) + "");
        baseMapper.insert(sdTruckTypeGoodsType);
    }

    @Override
    public void batchSave(List<SdTruckTypeGoodsType> truckTypeGoodsTypeList) {
        truckTypeGoodsTypeList.forEach(sdTruckTypeGoodsType -> sdTruckTypeGoodsType.setId(idGenerator.nextId(sdTruckTypeGoodsType) + ""));
        saveBatch(truckTypeGoodsTypeList);
    }

    @Override
    public void delete(String truckTypeId, String goodsTypeId) {
        LambdaQueryWrapper<SdTruckTypeGoodsType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        boolean canExecute = false;
        if (StringUtils.isNotEmpty(truckTypeId)) {
            lambdaQueryWrapper.eq(SdTruckTypeGoodsType::getTruckTypeId, truckTypeId);
            canExecute = true;
        }
        if (StringUtils.isNotEmpty(goodsTypeId)) {
            lambdaQueryWrapper.eq(SdTruckTypeGoodsType::getGoodsTypeId, goodsTypeId);
            canExecute = true;
        }
        if (canExecute) {
            baseMapper.delete(lambdaQueryWrapper);
        }
    }

    @Override
    public List<SdTruckTypeGoodsType> findAll(String truckTypeId, String goodsTypeId) {
        LambdaQueryWrapper<SdTruckTypeGoodsType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(truckTypeId)) {
            lambdaQueryWrapper.eq(SdTruckTypeGoodsType::getTruckTypeId, truckTypeId);
        }
        if (StringUtils.isNotEmpty(goodsTypeId)) {
            lambdaQueryWrapper.eq(SdTruckTypeGoodsType::getGoodsTypeId, goodsTypeId);
        }
        return baseMapper.selectList(lambdaQueryWrapper);
    }

}
