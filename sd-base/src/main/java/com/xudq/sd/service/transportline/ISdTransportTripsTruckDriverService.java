package com.xudq.sd.service.transportline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.transportline.SdTransportTripsTruckDriver;

import java.util.List;

/**
 * <p>
 * 车次与车辆关联信息表 服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdTransportTripsTruckDriverService extends IService<SdTransportTripsTruckDriver> {
    /**
     * 批量保存车次与车辆关联信息
     *
     * @param truckTransportTrips 车次与车辆关联信息
     */
    void batchSave(String truckTransportTripsId, List<SdTransportTripsTruckDriver> truckTransportTrips);

    /**
     * 获取车次与车辆关联列表
     *
     * @param transportTripsId 车次id
     * @param truckId          车辆Id
     * @param userId           司机id
     * @return 车次与车辆关联列表
     */
    List<SdTransportTripsTruckDriver> findAll(String transportTripsId, String truckId, String userId);
}
