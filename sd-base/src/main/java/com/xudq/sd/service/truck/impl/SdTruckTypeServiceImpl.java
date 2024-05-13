package com.xudq.sd.service.truck.impl;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.common.utils.Constant;
import com.xudq.sd.mapper.truck.SdTruckTypeMapper;
import com.xudq.sd.entity.truck.SdTruckType;
import com.xudq.sd.service.truck.ISdTruckTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * 车辆类型表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTruckTypeServiceImpl extends ServiceImpl<SdTruckTypeMapper, SdTruckType> implements ISdTruckTypeService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdTruckType saveTruckType(SdTruckType sdTruckType) {
        sdTruckType.setId(idGenerator.nextId(sdTruckType) + "");
        baseMapper.insert(sdTruckType);
        return sdTruckType;
    }

    @Override
    public IPage<SdTruckType> findByPage(Integer page, Integer pageSize, String name, BigDecimal allowableLoad,
                                         BigDecimal allowableVolume) {
        Page<SdTruckType> iPage = new Page(page, pageSize);
        LambdaQueryWrapper<SdTruckType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            lambdaQueryWrapper.like(SdTruckType::getName, name);
        }
        if (allowableLoad != null) {
            lambdaQueryWrapper.eq(SdTruckType::getAllowableLoad, allowableLoad);
        }
        if (allowableVolume != null) {
            lambdaQueryWrapper.eq(SdTruckType::getAllowableVolume, allowableVolume);
        }
        lambdaQueryWrapper.eq(SdTruckType::getStatus, Constant.DATA_DEFAULT_STATUS);
        return baseMapper.selectPage(iPage, lambdaQueryWrapper);
    }

    @Override
    public List<SdTruckType> findAll(List<String> ids) {
        LambdaQueryWrapper<SdTruckType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ids != null && ids.size() > 0) {
            lambdaQueryWrapper.in(SdTruckType::getId, ids);
        }
        lambdaQueryWrapper.eq(SdTruckType::getStatus, Constant.DATA_DEFAULT_STATUS);
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
