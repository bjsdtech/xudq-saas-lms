package com.xudq.sd.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudq.sd.entity.base.SdGoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 货物类型Mapper接口
 */
@Mapper
public interface SdGoodsTypeMapper extends BaseMapper<SdGoodsType>{
    List<SdGoodsType> findByPage(Page<SdGoodsType> page,
                                 @Param("name")String name,
                                 @Param("truckTypeId")String truckTypeId,
                                 @Param("truckTypeName")String truckTypeName);
}
