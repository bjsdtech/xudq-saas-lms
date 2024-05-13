package com.xudq.sd.entity.transportline;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 车次信息表
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Data
@TableName("sd_transport_trips")
public class SdTransportTrips implements Serializable {
    private static final long serialVersionUID = -934311173866081843L;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 车次名称
     */
    private String name;

    /**
     * 发车时间
     */
    private String departureTime;

    /**
     * 所属线路id
     */
    private String transportLineId;

    /**
     * 周期，1为天，2为周，3为月
     */
    private Integer period;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
}
