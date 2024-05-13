package com.xudq.sd.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.mapper.user.SdTruckDriverMapper;
import com.xudq.sd.entity.user.SdTruckDriver;
import com.xudq.sd.service.user.ISdTruckDriverService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 司机表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTruckDriverServiceImpl extends ServiceImpl<SdTruckDriverMapper, SdTruckDriver>
        implements ISdTruckDriverService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdTruckDriver saveTruckDriver(SdTruckDriver sdTruckDriver) {
        SdTruckDriver driver = baseMapper.selectOne(new LambdaQueryWrapper<SdTruckDriver>().eq(SdTruckDriver::getUserId, sdTruckDriver.getUserId()));
        if (driver == null) {
            sdTruckDriver.setId(idGenerator.nextId(sdTruckDriver) + "");
        } else {
            sdTruckDriver.setId(driver.getId());
        }
        saveOrUpdate(sdTruckDriver);
        return sdTruckDriver;
    }

    @Override
    public List<SdTruckDriver> findAll(List<String> userIds, String fleetId) {
        boolean hasUserIds = userIds != null && userIds.size() > 0;
        boolean hasFleetId = StringUtils.isNotEmpty(fleetId);
        if (!hasUserIds && !hasFleetId) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SdTruckDriver> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (hasUserIds) {
            lambdaQueryWrapper.in(SdTruckDriver::getUserId, userIds);
        }
        if (hasFleetId) {
            lambdaQueryWrapper.eq(SdTruckDriver::getFleetId, fleetId);
        }
        lambdaQueryWrapper.orderBy(true, false, SdTruckDriver::getId);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public SdTruckDriver findOne(String userId) {
        LambdaQueryWrapper<SdTruckDriver> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(userId)) {
            lambdaQueryWrapper.eq(SdTruckDriver::getUserId, userId);
        }
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public Integer count(String fleetId) {
        LambdaQueryWrapper<SdTruckDriver> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(fleetId)) {
            lambdaQueryWrapper.eq(SdTruckDriver::getFleetId, fleetId);
        }
        return count(lambdaQueryWrapper);
    }

    @Override
    public IPage<SdTruckDriver> findByPage(Integer page, Integer pageSize, String fleetId) {
        Page<SdTruckDriver> iPage = new Page(page, pageSize);
        LambdaQueryWrapper<SdTruckDriver> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(fleetId)) {
            lambdaQueryWrapper.eq(SdTruckDriver::getFleetId, fleetId);
        }
        lambdaQueryWrapper.orderBy(true, false, SdTruckDriver::getId);
        return baseMapper.selectPage(iPage, lambdaQueryWrapper);
    }
}
