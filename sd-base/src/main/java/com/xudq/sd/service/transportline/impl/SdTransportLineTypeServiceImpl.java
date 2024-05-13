package com.xudq.sd.service.transportline.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.CustomIdGenerator;
import com.xudq.sd.common.utils.Constant;
import com.xudq.sd.mapper.transportline.SdTransportLineTypeMapper;
import com.xudq.sd.entity.transportline.SdTransportLineType;
import com.xudq.sd.service.transportline.ISdTransportLineTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * 线路类型表 服务实现类
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Service
public class SdTransportLineTypeServiceImpl extends ServiceImpl<SdTransportLineTypeMapper, SdTransportLineType>
        implements ISdTransportLineTypeService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public SdTransportLineType saveTransportLineType(SdTransportLineType sdTransportLineType) {
        sdTransportLineType.setId(idGenerator.nextId(sdTransportLineType) + "");
        sdTransportLineType.setLastUpdateTime(LocalDateTime.now());
        baseMapper.insert(sdTransportLineType);
        return sdTransportLineType;
    }

    @Override
    public IPage<SdTransportLineType> findByPage(Integer page, Integer pageSize, String typeNumber, String name,
                                                 Integer agencyType) {
        Page<SdTransportLineType> iPage = new Page(page, pageSize);
        LambdaQueryWrapper<SdTransportLineType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            lambdaQueryWrapper.like(SdTransportLineType::getName, name);
        }
        if (StringUtils.isNotEmpty(typeNumber)) {
            lambdaQueryWrapper.like(SdTransportLineType::getTypeNumber, typeNumber);
        }
        if (agencyType != null) {
            lambdaQueryWrapper.and(i -> i.eq(SdTransportLineType::getStartAgencyType, agencyType).or()
                    .eq(SdTransportLineType::getEndAgencyType, agencyType));

        }
        lambdaQueryWrapper.eq(SdTransportLineType::getStatus, Constant.DATA_DEFAULT_STATUS);
        lambdaQueryWrapper.orderBy(true, true, SdTransportLineType::getId);
        return baseMapper.selectPage(iPage, lambdaQueryWrapper);
    }

    @Override
    public List<SdTransportLineType> findAll(List<String> ids) {
        LambdaQueryWrapper<SdTransportLineType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ids != null && ids.size() > 0) {
            lambdaQueryWrapper.in(SdTransportLineType::getId, ids);
        }
        lambdaQueryWrapper.orderBy(true, true, SdTransportLineType::getId);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void disableById(String id) {
        SdTransportLineType sdTransportLineType = new SdTransportLineType();
        sdTransportLineType.setId(id);
        sdTransportLineType.setStatus(Constant.DATA_DISABLE_STATUS);
        baseMapper.updateById(sdTransportLineType);
    }

}
