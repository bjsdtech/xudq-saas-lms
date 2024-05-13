package com.xudq.sd.controller.transportline;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudq.sd.common.utils.PageResponse;
import com.xudq.sd.common.utils.Result;
import com.xudq.sd.entity.transportline.SdTransportLineType;
import com.xudq.sd.service.transportline.ISdTransportLineTypeService;
import com.xudq.sd.DTO.transportline.TransportLineTypeDto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TransportLineTypeController
 */
@RestController
@RequestMapping("base/transportLine/type")
public class TransportLineTypeController {
    @Autowired
    private ISdTransportLineTypeService transportLineTypeService;

    /**
     * 添加线路类型
     *
     * @param dto 线路类型信息
     * @return 线路类型信息
     */
    @PostMapping("")
    public TransportLineTypeDto saveTransportLineType(@RequestBody TransportLineTypeDto dto) {
        SdTransportLineType sdTransportLineType = new SdTransportLineType();
        BeanUtils.copyProperties(dto, sdTransportLineType);
        sdTransportLineType = transportLineTypeService.saveTransportLineType(sdTransportLineType);
        BeanUtils.copyProperties(sdTransportLineType, dto);
        return dto;
    }

    /**
     * 根据id获取线路类型详情
     *
     * @param id 线路类型id
     * @return 线路类型详情
     */
    @GetMapping("/{id}")
    public TransportLineTypeDto fineById(@PathVariable(name = "id") String id) {
        SdTransportLineType sdTransportLineType = transportLineTypeService.getById(id);
        TransportLineTypeDto dto = new TransportLineTypeDto();
        BeanUtils.copyProperties(sdTransportLineType, dto);
        return dto;
    }

    /**
     * 获取线路类型分页数据
     *
     * @param page       页码
     * @param pageSize   页尺寸
     * @param typeNumber 类型编号
     * @param name       类型名称
     * @param agencyType 机构类型
     * @return 线路类型分页数据
     */
    @GetMapping("/page")
    public PageResponse<TransportLineTypeDto> findByPage(@RequestParam(name = "page") Integer page,
                                                         @RequestParam(name = "pageSize") Integer pageSize,
                                                         @RequestParam(name = "typeNumber", required = false) String typeNumber,
                                                         @RequestParam(name = "name", required = false) String name,
                                                         @RequestParam(name = "agencyType", required = false) Integer agencyType) {
        IPage<SdTransportLineType> transportLineTypePage = transportLineTypeService.findByPage(page, pageSize, typeNumber, name, agencyType);
        List<TransportLineTypeDto> dtoList = new ArrayList<>();
        transportLineTypePage.getRecords().forEach(sdTransportLineType -> {
            TransportLineTypeDto dto = new TransportLineTypeDto();
            BeanUtils.copyProperties(sdTransportLineType, dto);
            dtoList.add(dto);
        });
        return PageResponse.<TransportLineTypeDto>builder().items(dtoList).pagesize(pageSize).page(page)
                .counts(transportLineTypePage.getTotal()).pages(transportLineTypePage.getPages()).build();
    }

    /**
     * 获取线路类型列表
     *
     * @param ids 线路类型id列表
     * @return 线路类型列表
     */
    @GetMapping("")
    public List<TransportLineTypeDto> findAll(@RequestParam(name = "ids", required = false) List<String> ids) {
        return transportLineTypeService.findAll(ids).stream().map(sdTransportLineType -> {
            TransportLineTypeDto dto = new TransportLineTypeDto();
            BeanUtils.copyProperties(sdTransportLineType, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 更新线路类型信息
     *
     * @param id  线路类型id
     * @param dto 线路类型信息
     * @return 线路类型信息
     */
    @PutMapping("/{id}")
    public TransportLineTypeDto update(@PathVariable(name = "id") String id, @RequestBody TransportLineTypeDto dto) {
        dto.setId(id);
        SdTransportLineType sdTransportLineType = new SdTransportLineType();
        BeanUtils.copyProperties(dto, sdTransportLineType);
        sdTransportLineType.setLastUpdateTime(LocalDateTime.now());
        transportLineTypeService.updateById(sdTransportLineType);
        return dto;
    }

    /**
     * 删除线路类型
     *
     * @param id 线路类型id
     * @return 返回信息
     */
    @PutMapping("/{id}/disable")
    public Result disable(@PathVariable(name = "id") String id) {
        transportLineTypeService.disableById(id);
        return Result.ok();
    }
}
