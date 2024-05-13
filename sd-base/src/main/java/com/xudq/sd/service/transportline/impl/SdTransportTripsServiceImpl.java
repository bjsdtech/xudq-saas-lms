package com.xudq.sd.service.transportline.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.common.utils.Constant;
import com.xudq.sd.mapper.transportline.SdTransportTripsMapper;
import com.xudq.sd.entity.transportline.SdTransportTrips;
import com.xudq.sd.service.transportline.ISdTransportTripsService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 车次信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTransportTripsServiceImpl extends ServiceImpl<SdTransportTripsMapper, SdTransportTrips>
        implements ISdTransportTripsService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdTransportTrips saveTransportTrips(SdTransportTrips sdTransportTrips) {
        sdTransportTrips.setId(idGenerator.nextId(sdTransportTrips) + "");
        baseMapper.insert(sdTransportTrips);
        return sdTransportTrips;
    }

    @Override
    public List<SdTransportTrips> findAll(String transportLineId, List<String> ids) {
        LambdaQueryWrapper<SdTransportTrips> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(transportLineId)) {
            lambdaQueryWrapper.eq(SdTransportTrips::getTransportLineId, transportLineId);
        }
        if (ids != null && ids.size() > 0) {
            lambdaQueryWrapper.in(SdTransportTrips::getId, ids);
        }
        lambdaQueryWrapper.orderBy(true, true, SdTransportTrips::getId);
        lambdaQueryWrapper.eq(SdTransportTrips::getStatus, Constant.DATA_DEFAULT_STATUS);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void disable(String id) {
        SdTransportTrips sdTransportTrips = new SdTransportTrips();
        sdTransportTrips.setId(id);
        sdTransportTrips.setStatus(Constant.DATA_DISABLE_STATUS);
        baseMapper.updateById(sdTransportTrips);
    }

}
