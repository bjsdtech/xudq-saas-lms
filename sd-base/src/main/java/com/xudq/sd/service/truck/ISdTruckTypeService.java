package com.xudq.sd.service.truck;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.truck.SdTruckType;

/**
 * <p>
 * 车辆类型表 服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdTruckTypeService extends IService<SdTruckType> {
    /**
     * 添加车辆类型
     *
     * @param sdTruckType 车辆类型信息
     * @return 车辆类型信息
     */
    SdTruckType saveTruckType(SdTruckType sdTruckType);

    /**
     * 获取车辆类型分页数据
     *
     * @param page            页码
     * @param pageSize        页尺寸
     * @param name            类型名称
     * @param allowableLoad   车型载重
     * @param allowableVolume 车型体积
     * @return 线路类型分页数据
     */
    IPage<SdTruckType> findByPage(Integer page, Integer pageSize, String name, BigDecimal allowableLoad,
                                  BigDecimal allowableVolume);

    /**
     * 获取车辆类型列表
     * @return 车辆类型列表
     */
    List<SdTruckType> findAll(List<String> ids);
}
