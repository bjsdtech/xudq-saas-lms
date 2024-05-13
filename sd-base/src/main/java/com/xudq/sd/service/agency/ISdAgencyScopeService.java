package com.xudq.sd.service.agency;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudq.sd.entity.agency.SdAgencyScope;

import java.util.List;

/**
 * <p>
 * 机构业务范围表  服务类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
public interface ISdAgencyScopeService extends IService<SdAgencyScope> {
    /**
     * 批量保存机构业务范围
     *
     * @param scopeList 机构业务范围信息列表
     */
    void batchSave(List<SdAgencyScope> scopeList);

    /**
     * 删除机构业务范围
     *
     * @param areaId   行政区域id
     * @param agencyId 机构id
     */
    void delete(String areaId, String agencyId);

    List<SdAgencyScope> findAll(String areaId, String agencyId, List<String> agencyIds, List<String> areaIds);
}
