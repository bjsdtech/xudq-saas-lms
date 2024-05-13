package com.xudq.sd.service.truck;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.truck.SdTruckTypeGoodsType;

import java.util.List;

/**
 * <p>
 * 车辆类型与货物类型关联表 服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdTruckTypeGoodsTypeService extends IService<SdTruckTypeGoodsType> {
    /**
     * 添加车辆类型与货物类型关联
     *
     * @param sdTruckTypeGoodsType 车辆类型与货物类型信息
     */
    void saveTruckTypeGoodsType(SdTruckTypeGoodsType sdTruckTypeGoodsType);

    /**
     * 批量添加车辆类型与货物类型关联
     *
     * @param truckTypeGoodsTypeList 车辆类型与货物类型信息
     */
    void batchSave(List<SdTruckTypeGoodsType> truckTypeGoodsTypeList);

    /**
     * 删除关联关系
     *
     * @param truckTypeId 车辆类型id
     * @param goodsTypeId 货物类型id
     */
    void delete(String truckTypeId, String goodsTypeId);

    /**
     * 获取车辆类型与货物类型关联
     *
     * @param truckTypeId 车辆类型id
     * @param goodsTypeId 货物类型id
     * @return 车辆类型与货物类型关联
     */
    List<SdTruckTypeGoodsType> findAll(String truckTypeId, String goodsTypeId);
}
