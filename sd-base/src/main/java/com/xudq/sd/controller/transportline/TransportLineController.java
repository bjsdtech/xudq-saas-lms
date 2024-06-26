package com.xudq.sd.controller.transportline;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudq.sd.DTO.transportline.TransportLineDto;
import com.xudq.sd.common.utils.PageResponse;
import com.xudq.sd.common.utils.Result;
import com.xudq.sd.entity.transportline.SdTransportLine;
import com.xudq.sd.service.transportline.ISdTransportLineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TransportLineController
 */
@RestController
@RequestMapping("base/transportLine")
public class TransportLineController {
    @Autowired
    private ISdTransportLineService transportLineService;

    /**
     * 添加线路
     *
     * @param dto 线路信息
     * @return 线路信息
     */
    @PostMapping("")
    public TransportLineDto saveTransportLine(@RequestBody TransportLineDto dto) {
        SdTransportLine sdTransportLine = new SdTransportLine();
        BeanUtils.copyProperties(dto, sdTransportLine);
        sdTransportLine = transportLineService.saveTransportLine(sdTransportLine);
        BeanUtils.copyProperties(sdTransportLine, dto);
        return dto;
    }

    /**
     * 根据id获取线路详情
     *
     * @param id 线路id
     * @return 线路详情
     */
    @GetMapping("/{id}")
    public TransportLineDto fineById(@PathVariable(name = "id") String id) {
        SdTransportLine sdTransportLine = transportLineService.getById(id);
        TransportLineDto dto = new TransportLineDto();
        if (sdTransportLine != null) {
            BeanUtils.copyProperties(sdTransportLine, dto);
        }else {
            dto.setId(id);
        }
        return dto;
    }

    /**
     * 获取线路分页信息
     *
     * @param page                页码
     * @param pageSize            页尺寸
     * @param lineNumber          线路编号
     * @param name                线路名称
     * @param transportLineTypeId 线路类型id
     * @return 线路分页信息
     */
    @GetMapping("/page")
    public PageResponse<TransportLineDto> findByPage(@RequestParam(name = "page") Integer page,
                                                     @RequestParam(name = "pageSize") Integer pageSize,
                                                     @RequestParam(name = "lineNumber", required = false) String lineNumber,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "transportLineTypeId", required = false) String transportLineTypeId) {
        IPage<SdTransportLine> transportLinePage = transportLineService.findByPage(page, pageSize, lineNumber, name, transportLineTypeId);
        List<TransportLineDto> dtoList = new ArrayList<>();
        transportLinePage.getRecords().forEach(sdTransportLine -> {
            TransportLineDto dto = new TransportLineDto();
            BeanUtils.copyProperties(sdTransportLine, dto);
            dtoList.add(dto);
        });
        return PageResponse.<TransportLineDto>builder().items(dtoList).pagesize(pageSize).page(page)
                .counts(transportLinePage.getTotal()).pages(transportLinePage.getPages()).build();
    }

    /**
     * 获取线路列表
     *
     * @param ids 线路id列表
     * @return 线路列表
     */
    @GetMapping("")
    public List<TransportLineDto> findAll(@RequestParam(name = "ids", required = false) List<String> ids,
                                          @RequestParam(name = "agencyId", required = false) String agencyId,
                                          @RequestParam(name = "agencyIds", required = false) List<String> agencyIds) {
        return transportLineService.findAll(ids, agencyId, agencyIds).stream().map(sdTransportLine -> {
            TransportLineDto dto = new TransportLineDto();
            BeanUtils.copyProperties(sdTransportLine, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 更新线路信息
     *
     * @param id  线路id
     * @param dto 线路信息
     * @return 线路信息
     */
    @PutMapping("/{id}")
    public TransportLineDto update(@PathVariable(name = "id") String id, @RequestBody TransportLineDto dto) {
        dto.setId(id);
        SdTransportLine sdTransportLine = new SdTransportLine();
        BeanUtils.copyProperties(dto, sdTransportLine);
        transportLineService.updateById(sdTransportLine);
        return dto;
    }

    /**
     * 删除线路
     *
     * @param id 线路id
     * @return 返回信息
     */
    @PutMapping("/{id}/disable")
    public Result disable(@PathVariable(name = "id") String id) {
        transportLineService.disable(id);
        return Result.ok();
    }


    /**
     * 获取线路列表
     *
     * @return 线路列表
     */
    @PostMapping("list")
    public List<TransportLineDto> list(@RequestBody TransportLineDto transportLineDto) {
        LambdaQueryWrapper<SdTransportLine> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(StringUtils.isNotEmpty(transportLineDto.getStartAgencyId()), SdTransportLine::getStartAgencyId, transportLineDto.getStartAgencyId());
        wrapper.eq(StringUtils.isNotEmpty(transportLineDto.getEndAgencyId()), SdTransportLine::getEndAgencyId, transportLineDto.getEndAgencyId());
        wrapper.eq(StringUtils.isNotEmpty(transportLineDto.getAgencyId()), SdTransportLine::getAgencyId, transportLineDto.getAgencyId());
        wrapper.eq(null != (transportLineDto.getStatus()), SdTransportLine::getStatus, transportLineDto.getStatus());

        return transportLineService.list(wrapper).stream().map(sdTransportLine -> {
            TransportLineDto dto = new TransportLineDto();
            BeanUtils.copyProperties(sdTransportLine, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}