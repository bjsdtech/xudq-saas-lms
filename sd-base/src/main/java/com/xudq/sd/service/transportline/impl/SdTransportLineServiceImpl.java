package com.xudq.sd.service.transportline.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.common.utils.Constant;
import com.xudq.sd.mapper.transportline.SdTransportLineMapper;
import com.xudq.sd.entity.transportline.SdTransportLine;
import com.xudq.sd.service.transportline.ISdTransportLineService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 线路表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTransportLineServiceImpl extends ServiceImpl<SdTransportLineMapper, SdTransportLine>
        implements ISdTransportLineService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdTransportLine saveTransportLine(SdTransportLine sdTransportLine) {
        sdTransportLine.setId(idGenerator.nextId(sdTransportLine) + "");
        baseMapper.insert(sdTransportLine);
        return sdTransportLine;
    }

    @Override
    public IPage<SdTransportLine> findByPage(Integer page, Integer pageSize, String lineNumber, String name,
                                             String transportLineTypeId) {
        Page<SdTransportLine> iPage = new Page(page, pageSize);
        LambdaQueryWrapper<SdTransportLine> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            lambdaQueryWrapper.like(SdTransportLine::getName, name);
        }
        if (StringUtils.isNotEmpty(lineNumber)) {
            lambdaQueryWrapper.like(SdTransportLine::getLineNumber, lineNumber);
        }
        if (StringUtils.isNotEmpty(transportLineTypeId)) {
            lambdaQueryWrapper.eq(SdTransportLine::getTransportLineTypeId, transportLineTypeId);

        }
        lambdaQueryWrapper.eq(SdTransportLine::getStatus, Constant.DATA_DEFAULT_STATUS);
        lambdaQueryWrapper.orderBy(true, false, SdTransportLine::getId);
        return baseMapper.selectPage(iPage, lambdaQueryWrapper);
    }

    @Override
    public List<SdTransportLine> findAll(List<String> ids, String agencyId, List<String> agencyIds) {
        LambdaQueryWrapper<SdTransportLine> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ids != null && ids.size() > 0) {
            lambdaQueryWrapper.in(SdTransportLine::getId, ids);
        }
        if (StringUtils.isNotEmpty(agencyId)) {
            lambdaQueryWrapper.eq(SdTransportLine::getAgencyId, agencyId);
        }
        if (agencyIds != null && agencyIds.size() > 0) {
            lambdaQueryWrapper.in(SdTransportLine::getAgencyId, agencyIds);
        }
        lambdaQueryWrapper.eq(SdTransportLine::getStatus, Constant.DATA_DEFAULT_STATUS);
        lambdaQueryWrapper.orderBy(true, false, SdTransportLine::getId);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void disable(String id) {
        SdTransportLine sdTransportLine = new SdTransportLine();
        sdTransportLine.setId(id);
        sdTransportLine.setStatus(Constant.DATA_DISABLE_STATUS);
        baseMapper.updateById(sdTransportLine);
    }

}
