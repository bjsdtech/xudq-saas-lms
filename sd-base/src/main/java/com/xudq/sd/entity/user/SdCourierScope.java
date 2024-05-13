package com.xudq.sd.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 快递员业务范围表
 * </p>
 *
 * @author
 * @since 2023-12-20
 */
@Data
@TableName("sd_courier_scop")
public class SdCourierScope implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 行政区域id
     */
    private String areaId;
    /**
     * 多边形经纬度坐标集合
     */
    private String mutiPoints;
}
