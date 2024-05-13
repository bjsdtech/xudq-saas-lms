package com.xudq.sd.entity.truck;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 车辆类型表
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Data
@TableName("sd_truck_type")
public class SdTruckType implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 车辆类型名称
     */
    private String name;

    /**
     * 准载重量
     */
    private BigDecimal allowableLoad;

    /**
     * 准载体积
     */
    private BigDecimal allowableVolume;

    /**
     * 长
     */
    private BigDecimal measureLong;

    /**
     * 宽
     */
    private BigDecimal measureWidth;

    /**
     * 高
     */
    private BigDecimal measureHigh;

    /**
     * 状态 0：禁用 1：正常
     */
    private Integer status;
}
