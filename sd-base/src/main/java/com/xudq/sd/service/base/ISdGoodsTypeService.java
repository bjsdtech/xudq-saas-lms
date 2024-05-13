package com.xudq.sd.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.base.SdGoodsType;

import java.util.List;

/**
 * 货物类型操作接口
 */
public interface ISdGoodsTypeService extends IService<SdGoodsType>{
    /**
     * 保存货物类型
     * @param sdGoodsType
     * @return
     */
    public SdGoodsType saveGoodsType(SdGoodsType sdGoodsType);

    /**
     * 查询所有货物类型
     * @return
     */
    public List<SdGoodsType> findAll();

    /**
     * 分页查询货物类型
     * @param page
     * @param pageSize
     * @param name
     * @param truckTypeId
     * @param truckTypeName
     * @return
     */
    public IPage<SdGoodsType> findByPage(Integer page, Integer pageSize, String name, String truckTypeId, String truckTypeName);

    /**
     * 查询货物类型列表
     * @param ids
     * @return
     */
    public List<SdGoodsType> findByIds(List<String> ids);

}
