package com.xudq.sd.service.agency.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.common.utils.Constant;
import com.xudq.sd.mapper.agency.SdFleetMapper;
import com.xudq.sd.entity.agency.SdFleet;
import com.xudq.sd.service.agency.ISdFleetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * 车队表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdFleetServiceImpl extends ServiceImpl<SdFleetMapper, SdFleet> implements ISdFleetService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdFleet saveFleet(SdFleet fleet) {
        fleet.setId(idGenerator.nextId(fleet) + "");
        baseMapper.insert(fleet);
        return fleet;
    }

    @Override
    public IPage<SdFleet> findByPage(Integer page, Integer pageSize, String name, String fleetNumber, String manager) {
        Page<SdFleet> iPage = new Page(page, pageSize);
        LambdaQueryWrapper<SdFleet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            lambdaQueryWrapper.like(SdFleet::getName, name);
        }
        if (StringUtils.isNotEmpty(fleetNumber)) {
            lambdaQueryWrapper.like(SdFleet::getFleetNumber, fleetNumber);
        }
        if (StringUtils.isNotEmpty(manager)) {
            lambdaQueryWrapper.eq(SdFleet::getManager, manager);
        }
        lambdaQueryWrapper.eq(SdFleet::getStatus, Constant.DATA_DEFAULT_STATUS);
        lambdaQueryWrapper.orderBy(true, true, SdFleet::getId);
        return baseMapper.selectPage(iPage, lambdaQueryWrapper);
    }

    @Override
    public List<SdFleet> findAll(List<String> ids, String agencyId) {
        LambdaQueryWrapper<SdFleet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ids != null && ids.size() > 0) {
            lambdaQueryWrapper.in(SdFleet::getId, ids);
        }
        if (StringUtils.isNotEmpty(agencyId)) {
            lambdaQueryWrapper.eq(SdFleet::getAgencyId, agencyId);
        }
        lambdaQueryWrapper.orderBy(true, true, SdFleet::getId);
        lambdaQueryWrapper.eq(SdFleet::getStatus, Constant.DATA_DEFAULT_STATUS);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void disableById(String id) {
        SdFleet fleet = new SdFleet();
        fleet.setId(id);
        fleet.setStatus(Constant.DATA_DISABLE_STATUS);
        baseMapper.updateById(fleet);
    }

}
