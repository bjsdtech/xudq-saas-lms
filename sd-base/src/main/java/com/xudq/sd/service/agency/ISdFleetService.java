package com.xudq.sd.service.agency;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.agency.SdFleet;

/**
 * <p>
 * 车队表 服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdFleetService extends IService<SdFleet> {
    /**
     * 添加车队
     *
     * @param fleet 车队信息
     * @return 车队信息
     */
    SdFleet saveFleet(SdFleet fleet);

    /**
     * 车队分页数据
     *
     * @param page     页码
     * @param pageSize 页尺寸
     * @return 车队分页数据
     */
    IPage<SdFleet> findByPage(Integer page, Integer pageSize, String name, String fleetNumber, String manager);

    /**
     * 获取车队列表
     * @param ids 车队id列表
     * @return 车队列表
     */
    List<SdFleet> findAll(List<String> ids, String agencyId);

    /**
     * 删除车队
     *
     * @param id
     */
    void disableById(String id);
}
