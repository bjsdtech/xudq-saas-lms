package com.xudq.sd.controller.transportline;

import java.util.List;
import java.util.stream.Collectors;

import com.xudq.sd.common.utils.Result;
import com.xudq.sd.DTO.transportline.TransportTripsTruckDriverDto;
import com.xudq.sd.entity.transportline.SdTransportTrips;
import com.xudq.sd.entity.transportline.SdTransportTripsTruckDriver;
import com.xudq.sd.service.transportline.ISdTransportTripsService;
import com.xudq.sd.DTO.transportline.TransportTripsDto;

import com.xudq.sd.service.transportline.ISdTransportTripsTruckDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.BeanUtils;

/**
 * TransportTripsController
 */
@RestController
@RequestMapping("base/transportLine/trips")
public class TransportTripsController {
    @Autowired
    private ISdTransportTripsService transportTripsService;
    @Autowired
    private ISdTransportTripsTruckDriverService transportTripsTruckDriverService;

    /**
     * 添加车次
     *
     * @param dto 车次信息
     * @return 车次信息
     */
    @PostMapping("")
    public TransportTripsDto save(@RequestBody TransportTripsDto dto) {
        SdTransportTrips sdTransportTrips = new SdTransportTrips();
        BeanUtils.copyProperties(dto, sdTransportTrips);
        sdTransportTrips = transportTripsService.saveTransportTrips(sdTransportTrips);
        BeanUtils.copyProperties(sdTransportTrips, dto);
        return dto;
    }

    /**
     * 根据id获取车次详情
     *
     * @param id 车次id
     * @return 车次信息
     */
    @GetMapping("/{id}")
    public TransportTripsDto fineById(@PathVariable(name = "id") String id) {
        SdTransportTrips sdTransportTrips = transportTripsService.getById(id);
        TransportTripsDto dto = new TransportTripsDto();
        if (sdTransportTrips != null) {
            BeanUtils.copyProperties(sdTransportTrips, dto);
        }else{
            dto.setId(id);
        }
        return dto;
    }

    /**
     * 获取车次列表
     *
     * @param transportLineId 线路id
     * @param ids             车次id列表
     * @return 车次列表
     */
    @GetMapping("")
    public List<TransportTripsDto> findAll(@RequestParam(name = "transportLineId", required = false) String transportLineId, @RequestParam(name = "ids", required = false) List<String> ids) {
        return transportTripsService.findAll(transportLineId, ids).stream().map(sdTransportTrips -> {
            TransportTripsDto dto = new TransportTripsDto();
            BeanUtils.copyProperties(sdTransportTrips, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 更新车次信息
     *
     * @param id  车次id
     * @param dto 车次信息
     * @return 车次信息
     */
    @PutMapping("/{id}")
    public TransportTripsDto update(@PathVariable(name = "id") String id, @RequestBody TransportTripsDto dto) {
        dto.setId(id);
        SdTransportTrips sdTransportTrips = new SdTransportTrips();
        BeanUtils.copyProperties(dto, sdTransportTrips);
        transportTripsService.updateById(sdTransportTrips);
        return dto;
    }

    /**
     * 删除车次信息
     *
     * @param id 车次信息
     * @return 返回信息
     */
    @PutMapping("/{id}/disable")
    public Result disable(@PathVariable(name = "id") String id) {
        transportTripsService.disable(id);
        return Result.ok();
    }

    /**
     * 批量保存车次与车辆和司机关联关系
     *
     * @param dtoList 车次与车辆和司机关联关系
     * @return 返回信息
     */
    @PostMapping("{id}/truckDriver")
    public Result batchSaveTruckDriver(@PathVariable("id") String transportTripsId, @RequestBody List<TransportTripsTruckDriverDto> dtoList) {
        transportTripsTruckDriverService.batchSave(transportTripsId, dtoList.stream().map(dto -> {
            dto.setTransportTripsId(transportTripsId);
            SdTransportTripsTruckDriver truckTransportTripsTruckDriver = new SdTransportTripsTruckDriver();
            BeanUtils.copyProperties(dto, truckTransportTripsTruckDriver);
            return truckTransportTripsTruckDriver;
        }).collect(Collectors.toList()));
        return Result.ok();
    }

    /**
     * 获取车次与车辆和司机关联关系列表
     *
     * @param transportTripsId 车次id
     * @param truckId          车辆id
     * @param userId           司机id
     * @return 车次与车辆和司机关联关系列表
     */
    @GetMapping("/truckDriver")
    public List<TransportTripsTruckDriverDto> findAllTruckDriverTransportTrips(@RequestParam(name = "transportTripsId", required = false) String transportTripsId, @RequestParam(name = "truckId", required = false) String truckId, @RequestParam(name = "userId", required = false) String userId) {
        return transportTripsTruckDriverService.findAll(transportTripsId, truckId, userId).stream().map(sdTransportTripsTruck -> {
            TransportTripsTruckDriverDto dto = new TransportTripsTruckDriverDto();
            BeanUtils.copyProperties(sdTransportTripsTruck, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}