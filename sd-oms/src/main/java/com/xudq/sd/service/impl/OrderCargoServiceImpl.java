package com.xudq.sd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.common.utils.CustomIdGenerator;
import com.xudq.sd.entity.OrderCargo;
import com.xudq.sd.mapper.OrderCargoMapper;
import com.xudq.sd.service.IOrderCargoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 货物服务实现类
 */
@Service
public class OrderCargoServiceImpl extends ServiceImpl<OrderCargoMapper, OrderCargo> implements IOrderCargoService {
    @Autowired
    private CustomIdGenerator idGenerator;

    @Override
    public OrderCargo saveSelective(OrderCargo record) {
        if (record.getId() != null) {
            this.baseMapper.updateByPrimaryKey(record);
        } else {
            record.setId(idGenerator.nextId(record) + "");
            this.baseMapper.insertSelective(record);
        }
        return record;
    }

    @Override
    public List<OrderCargo> findAll(String tranOrderId, String orderId) {
        LambdaQueryWrapper<OrderCargo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(tranOrderId)) {
            queryWrapper.eq(OrderCargo::getTranOrderId, tranOrderId);
        }
        if (StringUtils.isNotEmpty(orderId)) {
            queryWrapper.eq(OrderCargo::getOrderId, orderId);
        }
        queryWrapper.orderBy(true, true, OrderCargo::getId);
        return baseMapper.selectList(queryWrapper);
    }
}
