package com.xudq.sd.controller.truck;

import com.xudq.sd.entity.truck.SdTruck;
import com.xudq.sd.entity.truck.SdTruckLicense;
import com.xudq.sd.service.truck.ISdTruckLicenseService;
import com.xudq.sd.DTO.truck.TruckLicenseDto;

import com.xudq.sd.service.truck.ISdTruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.BeanUtils;

/**
 * TruckLicenseController
 */
@RestController
@RequestMapping("base/truck/license")
public class TruckLicenseController {
    @Autowired
    private ISdTruckLicenseService truckLicenseService;
    @Autowired
    private ISdTruckService truckService;

    /**
     * 保存车辆行驶证信息
     *
     * @param dto 车辆行驶证信息
     * @return 车辆行驶证信息
     */
    @PostMapping("")
    public TruckLicenseDto saveTruckLicense(@RequestBody TruckLicenseDto dto) {
        SdTruckLicense sdTruckLicense = new SdTruckLicense();
        BeanUtils.copyProperties(dto, sdTruckLicense);
        sdTruckLicense = truckLicenseService.saveTruckLicense(sdTruckLicense);
        if (dto.getId() == null) {
            SdTruck truck = new SdTruck();
            truck.setId(dto.getId());
            truck.setTruckLicenseId(sdTruckLicense.getId());
            truckService.saveTruck(truck);
        }
        BeanUtils.copyProperties(sdTruckLicense, dto);
        return dto;
    }

    /**
     * 根据id获取车辆行驶证详情
     *
     * @param id 车辆行驶证id
     * @return 车辆行驶证信息
     */
    @GetMapping("/{id}")
    public TruckLicenseDto fineById(@PathVariable(name = "id") String id) {
        SdTruckLicense sdTruckLicense = truckLicenseService.getById(id);
        TruckLicenseDto dto = new TruckLicenseDto();
        BeanUtils.copyProperties(sdTruckLicense, dto);
        return dto;
    }
}