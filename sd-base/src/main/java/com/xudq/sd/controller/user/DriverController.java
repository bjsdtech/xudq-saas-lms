package com.xudq.sd.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xudq.sd.DTO.user.TruckDriverDto;
import com.xudq.sd.DTO.user.TruckDriverLicenseDto;
import com.xudq.sd.common.utils.PageResponse;
import com.xudq.sd.entity.user.SdTruckDriver;
import com.xudq.sd.entity.user.SdTruckDriverLicense;
import com.xudq.sd.service.user.ISdTruckDriverLicenseService;
import com.xudq.sd.service.user.ISdTruckDriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 司机相关
 */
@RestController
@Api(tags = "司机管理")
@RequestMapping("sys/driver")
public class DriverController {
    @Autowired
    private ISdTruckDriverService truckDriverService;
    @Autowired
    private ISdTruckDriverLicenseService truckDriverLicenseService;

    /**
     * 保存司机基本信息
     *
     * @param dto 司机基本信息
     * @return 返回信息
     */
    @PostMapping("")
    @ApiOperation(value = "保存司机基本信息")
    public TruckDriverDto saveDriver(@RequestBody TruckDriverDto dto) {
        SdTruckDriver driver = new SdTruckDriver();
        BeanUtils.copyProperties(dto, driver);
        truckDriverService.saveTruckDriver(driver);
        BeanUtils.copyProperties(driver, dto);
        return dto;
    }

    /**
     * 获取司机基本信息列表
     *
     * @param userIds 司机id列表
     * @return 司机基本信息列表
     */
    @GetMapping("")
    @ApiOperation(value = "获取司机基本信息列表")
    public List<TruckDriverDto> findAllDriver(@RequestParam(name = "userIds", required = false) List<String> userIds, @RequestParam(name = "fleetId", required = false) String fleetId) {
        return truckDriverService.findAll(userIds, fleetId).stream().map(sdTruckDriver -> {
            TruckDriverDto dto = new TruckDriverDto();
            BeanUtils.copyProperties(sdTruckDriver, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 获取司机基本信息
     *
     * @param id 司机id
     * @return 司机基本信息
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取司机基本信息")
    public TruckDriverDto findOneDriver(@ApiParam("司机id") @PathVariable(name = "id") String id) {
        SdTruckDriver sdTruckDriver = truckDriverService.findOne(id);
        TruckDriverDto dto = new TruckDriverDto();
        if (sdTruckDriver != null) {
            BeanUtils.copyProperties(sdTruckDriver, dto);
        }
        return dto;
    }

    /**
     * 保存司机驾驶证信息
     *
     * @param dto 司机驾驶证信息
     * @return 返回信息
     */
    @PostMapping("/driverLicense")
    @ApiOperation(value = "保存司机驾驶证信息")
    public TruckDriverLicenseDto saveDriverLicense(@RequestBody TruckDriverLicenseDto dto) {
        SdTruckDriverLicense driverLicense = new SdTruckDriverLicense();
        BeanUtils.copyProperties(dto, driverLicense);
        truckDriverLicenseService.saveTruckDriverLicense(driverLicense);
        BeanUtils.copyProperties(driverLicense, dto);
        return dto;
    }

    /**
     * 获取司机驾驶证信息
     *
     * @param id 司机id
     * @return 司机驾驶证信息
     */
    @ApiOperation(value = "根据司机id获取司机驾驶证信息")
    @GetMapping("/{id}/driverLicense")
    public TruckDriverLicenseDto findOneDriverLicense( @ApiParam("司机id") @PathVariable(name = "id") String id) {
        SdTruckDriverLicense driverLicense = truckDriverLicenseService.findOne(id);
        TruckDriverLicenseDto dto = new TruckDriverLicenseDto();
        if (driverLicense != null) {
            BeanUtils.copyProperties(driverLicense, dto);
        }
        return dto;
    }

    /**
     * 统计司机数量
     *
     * @param fleetId 车队id
     * @return 司机数量
     */
    @GetMapping("/count")
    @ApiOperation(value = "统计司机数量")
    public Integer count(@ApiParam("车队id") @RequestParam(name = "fleetId", required = false) String fleetId) {
        return truckDriverService.count(fleetId);
    }

    /**
     * 获取司机分页数据
     *
     * @param page     页码
     * @param pageSize 页尺寸
     * @param fleetId  车队id
     * @return 司机分页数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询司机信息")
    public PageResponse<TruckDriverDto> findByPage(@RequestParam(name = "page") Integer page,
                                                   @RequestParam(name = "pageSize") Integer pageSize,
                                                   @RequestParam(name = "fleetId", required = false) String fleetId) {
        IPage<SdTruckDriver> truckPage = truckDriverService.findByPage(page, pageSize, fleetId);
        List<TruckDriverDto> dtoList = new ArrayList<>();
        truckPage.getRecords().forEach(sdTruckDriver -> {
            TruckDriverDto dto = new TruckDriverDto();
            BeanUtils.copyProperties(sdTruckDriver, dto);
            dtoList.add(dto);
        });
        return PageResponse.<TruckDriverDto>builder().items(dtoList).pagesize(pageSize).page(page).counts(truckPage.getTotal())
                .pages(truckPage.getPages()).build();
    }


    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有司机")
    public List<TruckDriverDto> findAll(@RequestParam(name = "ids", required = false) List<String> ids) {
        LambdaQueryWrapper<SdTruckDriver> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SdTruckDriver::getId, ids);
        return truckDriverService.list(wrapper).stream().map(sdTruckDriver -> {
            TruckDriverDto dto = new TruckDriverDto();
            BeanUtils.copyProperties(sdTruckDriver, dto);
            return dto;
        }).collect(Collectors.toList());
    }

}
