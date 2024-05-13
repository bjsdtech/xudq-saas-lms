package com.xudq.sd.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.mapper.user.SdTruckDriverLicenseMapper;
import com.xudq.sd.entity.user.SdTruckDriverLicense;
import com.xudq.sd.service.user.ISdTruckDriverLicenseService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 司机驾驶证表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTruckDriverLicenseServiceImpl extends ServiceImpl<SdTruckDriverLicenseMapper, SdTruckDriverLicense> implements ISdTruckDriverLicenseService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdTruckDriverLicense saveTruckDriverLicense(SdTruckDriverLicense sdTruckDriverLicense) {
        SdTruckDriverLicense driverLicense = baseMapper.selectOne(new LambdaQueryWrapper<SdTruckDriverLicense>().eq(SdTruckDriverLicense::getUserId, sdTruckDriverLicense.getUserId()));
        if (driverLicense == null) {
            sdTruckDriverLicense.setId(idGenerator.nextId(sdTruckDriverLicense) + "");
        } else {
            sdTruckDriverLicense.setId(driverLicense.getId());
        }
        saveOrUpdate(sdTruckDriverLicense);
        return sdTruckDriverLicense;
    }

    @Override
    public SdTruckDriverLicense findOne(String userId) {
        LambdaQueryWrapper<SdTruckDriverLicense> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(userId)) {
            lambdaQueryWrapper.eq(SdTruckDriverLicense::getUserId, userId);
        }
        return getOne(lambdaQueryWrapper);
    }

}
