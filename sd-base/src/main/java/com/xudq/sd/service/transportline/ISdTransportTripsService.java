package com.xudq.sd.service.transportline;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.transportline.SdTransportTrips;

import java.util.List;

/**
 * <p>
 * 车次信息表 服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdTransportTripsService extends IService<SdTransportTrips> {
    /**
     * 添加车次
     *
     * @param sdTransportTrips 车次信息
     * @return 车次信息
     */
    SdTransportTrips saveTransportTrips(SdTransportTrips sdTransportTrips);

    /**
     * 获取车次列表
     *
     * @param transportLineId 线路id
     * @param ids             车次id列表
     * @return 车次列表
     */
    List<SdTransportTrips> findAll(String transportLineId, List<String> ids);

    /**
     * 删除车次
     *
     * @param id
     */
    void disable(String id);
}
