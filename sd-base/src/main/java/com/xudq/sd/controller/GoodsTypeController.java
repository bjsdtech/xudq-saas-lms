package com.xudq.sd.controller;

//import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudq.sd.DTO.base.GoodsTypeDto;
import com.xudq.sd.common.utils.Constant;
import com.xudq.sd.common.utils.PageResponse;
import com.xudq.sd.common.utils.Result;
import com.xudq.sd.entity.base.SdGoodsType;
import com.xudq.sd.entity.truck.SdTruckTypeGoodsType;
import com.xudq.sd.service.base.ISdGoodsTypeService;
import com.xudq.sd.service.truck.ISdTruckTypeGoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 货物类型管理
 */
@RestController
@RequestMapping("base/goodsType")
@Api(tags = "货物类型管理")
@RequiredArgsConstructor // ignore @Autowired warning
public class GoodsTypeController {

/*
    @Autowired
    private ISdGoodsTypeService goodsTypeService;

    @Autowired
    private ISdTruckTypeGoodsTypeService truckTypeGoodsTypeService;
*/

    private final ISdGoodsTypeService goodsTypeService;
    private final ISdTruckTypeGoodsTypeService truckTypeGoodsTypeService;

    /**
     * 新增货物类型，同时需要关联车辆类型
     * @param goodsTypeDto 货物类型信息
     * @return 货物类型信息  GoodsTypeDto
     */
    @PostMapping("")
    @ApiOperation(value = "添加货物类型")
    public GoodsTypeDto saveGoodsType(@Validated @RequestBody GoodsTypeDto goodsTypeDto){

        //SdGoodsType sdGoodsType = BeanUtil.copyProperties(goodsTypeDto, SdGoodsType.class);

        SdGoodsType sdGoodsType = new SdGoodsType();
        BeanUtils.copyProperties(goodsTypeDto, sdGoodsType);

        //保存货物类型信息到货物类型表
        sdGoodsType = goodsTypeService.saveGoodsType(sdGoodsType);
        String goodsTypeId = sdGoodsType.getId();//货物类型id

        //保存货物类型和车辆类型关联信息到关联表
        List<String> truckTypeIds = goodsTypeDto.getTruckTypeIds();
        if(truckTypeIds != null && truckTypeIds.size() > 0){
            List<SdTruckTypeGoodsType> list = truckTypeIds.stream().map(truckTypeId -> {
                SdTruckTypeGoodsType sdTruckTypeGoodsType = new SdTruckTypeGoodsType();
                sdTruckTypeGoodsType.setGoodsTypeId(goodsTypeId);//货物类型id
                sdTruckTypeGoodsType.setTruckTypeId(truckTypeId);//车辆类型id
                return sdTruckTypeGoodsType;
            }).collect(Collectors.toList());
            //批量保存货物类型和车辆类型的关联信息
            truckTypeGoodsTypeService.batchSave(list);
        }

        BeanUtils.copyProperties(sdGoodsType,goodsTypeDto);
        return goodsTypeDto;
    }

    /**
     * 根据id查询货物类型
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询货物类型")
    public GoodsTypeDto queryById(@ApiParam("货物类型id") @PathVariable(name = "id") String id){
        SdGoodsType sdGoodsType = goodsTypeService.getById(id);

        GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
        BeanUtils.copyProperties(sdGoodsType,goodsTypeDto);

        System.out.print("开始查询.......................");
        //还需要查询当前货物类型关联的车辆类型的id
        List<SdTruckTypeGoodsType> list = truckTypeGoodsTypeService.findAll(null, id);
        if(list != null && list.size() > 0){
            List<String> truckTypeId = list.stream().map(sdTruckTypeGoodsType ->
                sdTruckTypeGoodsType.getTruckTypeId()
            ).collect(Collectors.toList());
            goodsTypeDto.setTruckTypeIds(truckTypeId);
        }
        else {
            System.out.print("查询为空.......................");
        }

        return goodsTypeDto;
    }

    /**
     * 根据id集合查询货物类型列表
     * @param ids
     * @return
     */
    @GetMapping("")
    @ApiOperation(value = "根据id集合批量查询货物类型")
    public List<GoodsTypeDto> findByIds(@ApiParam("货物类型id集合") @RequestParam(name = "ids", required = false) List<String> ids){

        //1. 查询货物类型PO
        List<SdGoodsType> goodsTypeList= goodsTypeService.findByIds(ids);
        //List<SdGoodsType> goodsTypeList = goodsTypeService.listByIds(ids);

        if(goodsTypeList != null && goodsTypeList.size() > 0){
            List<GoodsTypeDto> goodsTypeDtoList = goodsTypeList.stream().map(sdGoodsType -> {
                List<SdTruckTypeGoodsType> truckTypeGoodsTypes = truckTypeGoodsTypeService.findAll(null, sdGoodsType.getId());
                List<String> truckTypeIds = truckTypeGoodsTypes.stream().map(truckTypeGoodsType -> truckTypeGoodsType.getTruckTypeId()).collect(Collectors.toList());
                GoodsTypeDto goodsTypeDto = new GoodsTypeDto();

                // 2. 把PO拷贝到 VO
                BeanUtils.copyProperties(sdGoodsType, goodsTypeDto);
                goodsTypeDto.setTruckTypeIds(truckTypeIds);
                return goodsTypeDto;
            }).collect(Collectors.toList());
            return goodsTypeDtoList;
        }
        return null;
    }

    /**
     * 查询所有货物类型
     * @return
     */
    @GetMapping("/all")
    @ApiOperation(value = "查询所有货物类型")
    public List<GoodsTypeDto> findAll(){
        List<SdGoodsType> goodsTypeList = goodsTypeService.findAll();
        if(goodsTypeList != null && goodsTypeList.size() > 0){
            List<GoodsTypeDto> goodsTypeDtoList = goodsTypeList.stream().map(goodsType -> {
                GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
                BeanUtils.copyProperties(goodsType, goodsTypeDto);
                return goodsTypeDto;
            }).collect(Collectors.toList());
            return goodsTypeDtoList;
        }
        return null;
    }

    /**
     * 分页查询货物类型
     * @param page
     * @param pageSize
     * @param name
     * @param truckTypeId
     * @param truckTypeName
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询货物类型")
    public PageResponse<GoodsTypeDto> findByPage(@RequestParam(name = "page") Integer page,
                                                 @RequestParam(name = "pageSize") Integer pageSize,
                                                 @RequestParam(name = "name", required = false) String name,
                                                 @RequestParam(name = "truckTypeId", required = false) String truckTypeId,
                                                 @RequestParam(name = "truckTypeName", required = false) String truckTypeName){

        //分页查询
        IPage<SdGoodsType> goodsTypePage = goodsTypeService.findByPage(page, pageSize, name, truckTypeId, truckTypeName);
        List<SdGoodsType> goodsTypePageRecords = goodsTypePage.getRecords();

        if(goodsTypePageRecords != null && goodsTypePageRecords.size() > 0){
            List<GoodsTypeDto> goodsTypeDtoList = goodsTypePageRecords.stream().map(goodsType -> {
                List<SdTruckTypeGoodsType> truckTypeGoodsTypes = truckTypeGoodsTypeService.findAll(null, goodsType.getId());
                List<String> truckTypeIds = truckTypeGoodsTypes.stream().map(sdTruckTypeGoodsType -> sdTruckTypeGoodsType.getTruckTypeId()).collect(Collectors.toList());
                GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
                BeanUtils.copyProperties(goodsType, goodsTypeDto);
                goodsTypeDto.setTruckTypeIds(truckTypeIds);
                return goodsTypeDto;
            }).collect(Collectors.toList());

            return PageResponse.<GoodsTypeDto>builder().items(goodsTypeDtoList).counts(goodsTypePage.getTotal()).page(page).pages(goodsTypePage.getPages()).pagesize(pageSize).build();
        }

        return null;
    }



    /**
     * 更新货物类型信息
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "更新货物类型信息")
    public GoodsTypeDto updateById(@PathVariable(name = "id") String id, @RequestBody GoodsTypeDto dto){
        SdGoodsType sdGoodsType = new SdGoodsType();
        BeanUtils.copyProperties(dto, sdGoodsType);
        sdGoodsType.setId(id);
        //更新货物类型的普通属性
        goodsTypeService.updateById(sdGoodsType);

        //更新关联信息
        List<String> truckTypeIds = dto.getTruckTypeIds();
        if(truckTypeIds != null && truckTypeIds.size() > 0){
            //清理原有关联信息
            truckTypeGoodsTypeService.delete(null,id);
            //重新建立关联关系
            List<SdTruckTypeGoodsType> list = truckTypeIds.stream().map(truckTypeId -> {
                SdTruckTypeGoodsType sdTruckTypeGoodsType = new SdTruckTypeGoodsType();
                sdTruckTypeGoodsType.setGoodsTypeId(id);//货物类型id
                sdTruckTypeGoodsType.setTruckTypeId(truckTypeId);//车辆类型id
                return sdTruckTypeGoodsType;
            }).collect(Collectors.toList());
            //批量保存货物类型和车辆类型的关联信息
            truckTypeGoodsTypeService.batchSave(list);
        }

        dto.setId(id);
        return dto;
    }

    /**
     * 删除货物类型--逻辑删除
     *
     * @param id 货物类型id
     * @return 返回信息
     */
    @PutMapping("/{id}/disable")
    @ApiOperation(value = "删除货物类型")
    public Result disable(@PathVariable(name = "id") String id) {
        SdGoodsType sdGoodsType = new SdGoodsType();
        sdGoodsType.setId(id);
        sdGoodsType.setStatus(Constant.DATA_DISABLE_STATUS);
        goodsTypeService.updateById(sdGoodsType);
        return Result.ok();
    }
}
