package com.xudq.sd.service.truck.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.mapper.truck.SdTruckLicenseMapper;
import com.xudq.sd.entity.truck.SdTruck;
import com.xudq.sd.entity.truck.SdTruckLicense;
import com.xudq.sd.service.truck.ISdTruckLicenseService;
import com.xudq.sd.service.truck.ISdTruckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆行驶证表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTruckLicenseServiceImpl extends ServiceImpl<SdTruckLicenseMapper, SdTruckLicense>
        implements ISdTruckLicenseService {
    @Autowired
    private CustomIdGenerator idGenerator;
    @Autowired
    private ISdTruckService truckService;

    @Override
    public SdTruckLicense saveTruckLicense(SdTruckLicense sdTruckLicense) {
        if (sdTruckLicense.getId() == null) {
            sdTruckLicense.setId(idGenerator.nextId(sdTruckLicense) + "");
            baseMapper.insert(sdTruckLicense);
            // 处理车辆信息中的关联字段
            if (sdTruckLicense.getTruckId() != null) {
                SdTruck sdTruck = truckService.getById(sdTruckLicense.getTruckId());
                sdTruck.setTruckLicenseId(sdTruckLicense.getId());
                truckService.updateById(sdTruck);
            }
        } else {
            baseMapper.updateById(sdTruckLicense);
        }
        return sdTruckLicense;
    }

}
