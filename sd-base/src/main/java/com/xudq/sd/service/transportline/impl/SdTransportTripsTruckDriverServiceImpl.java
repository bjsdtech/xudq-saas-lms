package com.xudq.sd.service.transportline.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.entity.transportline.SdTransportTripsTruckDriver;
import com.xudq.sd.mapper.transportline.SdTransportTripsTruckDriverMapper;
import com.xudq.sd.service.transportline.ISdTransportTripsTruckDriverService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车次与车辆关联信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTransportTripsTruckDriverServiceImpl extends ServiceImpl<SdTransportTripsTruckDriverMapper, SdTransportTripsTruckDriver>
        implements ISdTransportTripsTruckDriverService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public void batchSave(String truckTransportTripsId, List<SdTransportTripsTruckDriver> truckTransportTripsTruckDriverList) {
        LambdaQueryWrapper<SdTransportTripsTruckDriver> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SdTransportTripsTruckDriver::getTransportTripsId, truckTransportTripsId);
        //查出操作前关系列表
        List<SdTransportTripsTruckDriver> transportTripsTruckDriverList = baseMapper.selectList(lambdaQueryWrapper);
        Map<String, SdTransportTripsTruckDriver> sourceTruckKeyMap = new HashMap<>();
        for (SdTransportTripsTruckDriver sdTransportTripsTruckDriver :transportTripsTruckDriverList){
            sourceTruckKeyMap.put(sdTransportTripsTruckDriver.getTransportTripsId() + "_" + sdTransportTripsTruckDriver.getTruckId(), sdTransportTripsTruckDriver);
        }
        //清除关系
        baseMapper.delete(lambdaQueryWrapper);
        List<SdTransportTripsTruckDriver> saveList = new ArrayList<>();
        //遍历传入数据
        truckTransportTripsTruckDriverList.forEach(sdTransportTripsTruckDriver -> {
            SdTransportTripsTruckDriver saveData = new SdTransportTripsTruckDriver();
            BeanUtils.copyProperties(sdTransportTripsTruckDriver, saveData);
            saveData.setId(idGenerator.nextId(saveData) + "");
            if (sourceTruckKeyMap.containsKey(sdTransportTripsTruckDriver.getTransportTripsId() + "_" + sdTransportTripsTruckDriver.getTruckId())) {
                SdTransportTripsTruckDriver source = sourceTruckKeyMap.get(sdTransportTripsTruckDriver.getTransportTripsId() + "_" + sdTransportTripsTruckDriver.getTruckId());
                if (saveData.getUserId() == null) {
                    saveData.setUserId(source.getUserId());
                }
            }
            saveList.add(saveData);
        });
        saveBatch(saveList);
    }

    @Override
    public List<SdTransportTripsTruckDriver> findAll(String transportTripsId, String truckId, String userId) {
        LambdaQueryWrapper<SdTransportTripsTruckDriver> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(transportTripsId)) {
            lambdaQueryWrapper.eq(SdTransportTripsTruckDriver::getTransportTripsId, transportTripsId);
        }
        if (StringUtils.isNotEmpty(truckId)) {
            lambdaQueryWrapper.eq(SdTransportTripsTruckDriver::getTruckId, truckId);
        }
        if (StringUtils.isNotEmpty(userId)) {
            lambdaQueryWrapper.eq(SdTransportTripsTruckDriver::getUserId, userId);
        }
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
