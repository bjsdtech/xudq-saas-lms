package com.xudq.sd.controller.agency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudq.sd.common.utils.PageResponse;
import com.xudq.sd.common.utils.Result;
import com.xudq.sd.entity.agency.SdFleet;
import com.xudq.sd.service.agency.ISdFleetService;
import com.xudq.sd.DTO.angency.FleetDto;
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
 * FleetController
 */
@RestController
@RequestMapping("sys/agency/fleet")
public class FleetController {
    @Autowired
    private ISdFleetService fleetService;

    /**
     * 添加车队
     *
     * @param dto 车队信息
     * @return 车队信息
     */
    @PostMapping("")
    public FleetDto saveAgencyType(@RequestBody FleetDto dto) {
        SdFleet sdFleet = new SdFleet();
        BeanUtils.copyProperties(dto, sdFleet);
        sdFleet = fleetService.saveFleet(sdFleet);
        BeanUtils.copyProperties(sdFleet, dto);
        return dto;
    }

    /**
     * 根据id获取车队详情
     *
     * @param id 车队id
     * @return 车队信息
     */
    @GetMapping("/{id}")
    public FleetDto fineById(@PathVariable(name = "id") String id) {
        SdFleet sdFleet = fleetService.getById(id);
        FleetDto dto = new FleetDto();
        BeanUtils.copyProperties(sdFleet, dto);
        return dto;
    }

    /**
     * 获取车队分页数据
     *
     * @param page        页码
     * @param pageSize    页尺寸
     * @param name        车队名称
     * @param fleetNumber 车队编号
     * @param manager     负责人id
     * @return 车队分页数据
     */
    @GetMapping("/page")
    public PageResponse<FleetDto> findByPage(@RequestParam(name = "page") Integer page,
                                             @RequestParam(name = "pageSize") Integer pageSize,
                                             @RequestParam(name = "name", required = false) String name,
                                             @RequestParam(name = "fleetNumber", required = false) String fleetNumber,
                                             @RequestParam(name = "manager", required = false) String manager) {
        IPage<SdFleet> fleetPage = fleetService.findByPage(page, pageSize, name, fleetNumber, manager);
        List<FleetDto> dtoList = new ArrayList<>();
        fleetPage.getRecords().forEach(SdFleet -> {
            FleetDto dto = new FleetDto();
            BeanUtils.copyProperties(SdFleet, dto);
            dtoList.add(dto);
        });
        return PageResponse.<FleetDto>builder().items(dtoList).pagesize(pageSize).page(page).counts(fleetPage.getTotal())
                .pages(fleetPage.getPages()).build();
    }

    /**
     * 获取车队列表
     *
     * @param ids 车队Id列表
     * @return 车队列表
     */
    @GetMapping("")
    public List<FleetDto> findAll(@RequestParam(value = "ids", required = false) List<String> ids, @RequestParam(value = "agencyId", required = false) String agencyId) {
        return fleetService.findAll(ids, agencyId).stream().map(sdFleet -> {
            FleetDto dto = new FleetDto();
            BeanUtils.copyProperties(sdFleet, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 更新车队信息
     *
     * @param dto 车队信息
     * @return 车队信息
     */
    @PutMapping("/{id}")
    public FleetDto update(@PathVariable(name = "id") String id, @RequestBody FleetDto dto) {
        dto.setId(id);
        SdFleet sdFleet = new SdFleet();
        BeanUtils.copyProperties(dto, sdFleet);
        fleetService.updateById(sdFleet);
        return dto;
    }

    /**
     * 删除车队
     *
     * @param id 车队id
     * @return 返回信息
     */
    @PutMapping("/{id}/disable")
    public Result disable(@PathVariable(name = "id") String id) {
        fleetService.disableById(id);
        return Result.ok();
    }
}