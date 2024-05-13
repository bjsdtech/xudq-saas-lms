package com.xudq.sd.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.user.SdCourierScope;

import java.util.List;

/**
 * <p>
 * 快递员业务范围表  服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdCourierScopeService extends IService<SdCourierScope> {
    /**
     * 批量保存快递员业务范围
     *
     * @param scopeList 快递员业务范围信息列表
     */
    void batchSave(List<SdCourierScope> scopeList);

    /**
     * 删除快递员业务范围
     *
     * @param areaId 行政区域id
     * @param userId 快递员id
     */
    void delete(String areaId, String userId);

    List<SdCourierScope> findAll(String areaId, String userId);
}
