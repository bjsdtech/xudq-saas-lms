package com.xudq.sd.service.truck.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.common.utils.Constant;
import com.xudq.sd.mapper.truck.SdTruckMapper;
import com.xudq.sd.entity.truck.SdTruck;
import com.xudq.sd.service.truck.ISdTruckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTruckServiceImpl extends ServiceImpl<SdTruckMapper, SdTruck> implements ISdTruckService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdTruck saveTruck(SdTruck sdTruck) {
        sdTruck.setId(idGenerator.nextId(sdTruck) + "");
        baseMapper.insert(sdTruck);
        return sdTruck;
    }

    @Override
    public IPage<SdTruck> findByPage(Integer page, Integer pageSize, String truckTypeId, String licensePlate, String fleetId) {
        Page<SdTruck> iPage = new Page(page, pageSize);
        LambdaQueryWrapper<SdTruck> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(licensePlate)) {
            lambdaQueryWrapper.like(SdTruck::getLicensePlate, licensePlate);
        }
        if (StringUtils.isNotEmpty(truckTypeId)) {
            lambdaQueryWrapper.eq(SdTruck::getTruckTypeId, truckTypeId);

        }
        if (StringUtils.isNotEmpty(fleetId)) {
            lambdaQueryWrapper.eq(SdTruck::getFleetId, fleetId);

        }
        lambdaQueryWrapper.eq(SdTruck::getStatus, Constant.DATA_DEFAULT_STATUS);
        lambdaQueryWrapper.orderBy(true, false, SdTruck::getId);
        return baseMapper.selectPage(iPage, lambdaQueryWrapper);
    }

    @Override
    public List<SdTruck> findAll(List<String> ids, String fleetId) {
        LambdaQueryWrapper<SdTruck> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ids != null && ids.size() > 0) {
            lambdaQueryWrapper.in(SdTruck::getId, ids);
        }
        if (StringUtils.isNotEmpty(fleetId)) {
            lambdaQueryWrapper.eq(SdTruck::getFleetId, fleetId);
        }
        lambdaQueryWrapper.eq(SdTruck::getStatus, Constant.DATA_DEFAULT_STATUS);
        lambdaQueryWrapper.orderBy(true, false, SdTruck::getId);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Integer count(String fleetId) {
        LambdaQueryWrapper<SdTruck> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(fleetId)) {
            lambdaQueryWrapper.eq(SdTruck::getId, fleetId);
        }
        lambdaQueryWrapper.eq(SdTruck::getStatus, Constant.DATA_DEFAULT_STATUS);
        return baseMapper.selectCount(lambdaQueryWrapper);
    }

    @Override
    public void disableById(String id) {
        SdTruck sdTruck = new SdTruck();
        sdTruck.setId(id);
        sdTruck.setStatus(Constant.DATA_DISABLE_STATUS);
        baseMapper.updateById(sdTruck);
    }

}
