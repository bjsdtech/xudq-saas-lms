package com.xudq.sd.controller.scope;

import com.google.gson.Gson;
import com.xudq.sd.entity.agency.SdAgencyScope;
import com.xudq.sd.entity.user.SdCourierScope;
import com.xudq.sd.service.agency.ISdAgencyScopeService;
import com.xudq.sd.service.user.ISdCourierScopeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.xudq.sd.DTO.angency.AgencyScopeDto;
import com.xudq.sd.DTO.user.CourierScopeDto;
import com.xudq.sd.common.utils.Result;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务范围表 前端控制器
 * </p>
 *
 * @author
 * @since 2023-12-23
 */
@RestController
@RequestMapping("scope")
@Log
public class ScopeController {
    @Autowired
    private ISdAgencyScopeService agencyScopService;
    @Autowired
    private ISdCourierScopeService courierScopeService;

    /**
     * 批量保存机构业务范围
     *
     * @param dtoList 机构业务范围信息
     * @return 返回信息
     */
    @PostMapping("/agency/batch")
    public Result batchSaveAgencyScope(@RequestBody List<AgencyScopeDto> dtoList) {
        agencyScopService.batchSave(dtoList.stream().map(dto -> {
            SdAgencyScope scope = new SdAgencyScope();
            BeanUtils.copyProperties(dto, scope);
            if (!ObjectUtils.isEmpty(dto.getMutiPoints())) {
                scope.setMutiPoints(JSON.toJSONString(dto.getMutiPoints()));
            }
            return scope;
        }).collect(Collectors.toList()));
        return Result.ok();
    }

    /**
     * 删除机构业务范围信息
     *
     * @param dto 参数
     * @return 返回信息
     */
    @DeleteMapping("/agency")
    public Result deleteAgencyScope(@RequestBody AgencyScopeDto dto) {
        agencyScopService.delete(dto.getAreaId(), dto.getAgencyId());
        return Result.ok();
    }

    /**
     * 获取机构业务范围列表
     *
     * @param areaId   行政区域id
     * @param agencyId 机构id
     * @return 机构业务范围列表
     */
    @GetMapping("/agency")
    public List<AgencyScopeDto> findAllAgencyScope(@RequestParam(name = "areaId", required = false) String areaId, @RequestParam(name = "agencyId", required = false) String agencyId, @RequestParam(name = "agencyIds", required = false) List<String> agencyIds, @RequestParam(name = "areaIds", required = false) List<String> areaIds) {
        return agencyScopService.findAll(areaId, agencyId, agencyIds, areaIds).stream().map(scope -> {
            AgencyScopeDto dto = new AgencyScopeDto();
            BeanUtils.copyProperties(scope, dto);
            if (StringUtils.isNotBlank(scope.getMutiPoints())) {
                Gson gson = new Gson();
                List<List<Map>> json = gson.fromJson(scope.getMutiPoints(),List.class);
                dto.setMutiPoints(json);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 批量保存快递员业务范围
     *
     * @param dtoList 快递员业务范围信息
     * @return 返回信息
     */
    @PostMapping("/courier/batch")
    public Result batchSaveCourierScope(@RequestBody List<CourierScopeDto> dtoList) {
        courierScopeService.batchSave(dtoList.stream().map(dto -> {
            SdCourierScope scope = new SdCourierScope();
            BeanUtils.copyProperties(dto, scope);
            if (!ObjectUtils.isEmpty(dto.getMutiPoints())) {
                scope.setMutiPoints(JSON.toJSONString(dto.getMutiPoints()));
            }
            return scope;
        }).collect(Collectors.toList()));
        return Result.ok();
    }

    /**
     * 删除快递员业务范围信息
     *
     * @param dto 参数
     * @return 返回信息
     */
    @DeleteMapping("/courier")
    public Result deleteCourierScope(@RequestBody CourierScopeDto dto) {
        courierScopeService.delete(dto.getAreaId(), dto.getUserId());
        return Result.ok();
    }

    /**
     * 获取快递员业务范围列表
     *
     * @param areaId 行政区域id
     * @param userId 快递员id
     * @return 快递员业务范围列表
     */
    @GetMapping("/courier")
    public List<CourierScopeDto> findAllCourierScope(@RequestParam(name = "areaId", required = false) String areaId, @RequestParam(name = "userId", required = false) String userId) {
        return courierScopeService.findAll(areaId, userId).stream().map(scope -> {
            CourierScopeDto dto = new CourierScopeDto();
            BeanUtils.copyProperties(scope, dto);
            if (StringUtils.isNotBlank(scope.getMutiPoints())) {
                Gson gson = new Gson();
                List<List<Map>> json = gson.fromJson(scope.getMutiPoints(),List.class);
                dto.setMutiPoints(json);
            }
            return dto;
        }).collect(Collectors.toList());
    }

//    public static void main(String[] args) {
//        String str = "[[{\"lng\":\"1\",\"lat\":\"2\"},{\"lng\":\"3\",\"lat\":\"4\"}],[{\"lng\":\"5\",\"lat\":\"6\"},{\"lng\":\"7\",\"lat\":\"84\"}]]";
//        List<List> mutiPoints = JSON.parseArray(str,List.class);
//        System.out.println(JSON.toJSONString(mutiPoints));
//        Gson gson = new Gson();
//        List<List<Map>> json = gson.fromJson(str,List.class);
//        System.out.println(JSON.toJSONString(json));
//    }
}