package com.xudq.sd.service.truck;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.truck.SdTruckLicense;

/**
 * <p>
 * 车辆行驶证表  服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdTruckLicenseService extends IService<SdTruckLicense> {
    /**
     * 保存车辆行驶证信息
     *
     * @param sdTruckLicense 车辆行驶证信息
     * @return 车辆行驶证信息
     */
    SdTruckLicense saveTruckLicense(SdTruckLicense sdTruckLicense);
}
