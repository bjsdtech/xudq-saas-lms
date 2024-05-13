package com.xudq.sd.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.user.SdTruckDriverLicense;

/**
 * <p>
 * 司机驾驶证表  服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdTruckDriverLicenseService extends IService<SdTruckDriverLicense> {
    /**
     * 保存司机驾驶证信息
     *
     * @param sdTruckDriverLicense 司机驾驶证信息
     * @return 司机驾驶证信息
     */
    SdTruckDriverLicense saveTruckDriverLicense(SdTruckDriverLicense sdTruckDriverLicense);

    /**
     * 获取司机驾驶证信息
     *
     * @param userId 司机id
     * @return 司机驾驶证信息
     */
    SdTruckDriverLicense findOne(String userId);
}
