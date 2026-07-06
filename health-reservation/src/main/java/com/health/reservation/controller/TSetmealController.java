package com.health.reservation.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.health.common.annotation.Log;
import com.health.common.core.controller.BaseController;
import com.health.common.core.domain.AjaxResult;
import com.health.common.enums.BusinessType;
import com.health.reservation.domain.TSetmeal;
import com.health.reservation.dto.TSetmealDto;
import com.health.reservation.service.ITSetmealService;
import com.health.common.utils.poi.ExcelUtil;
import com.health.common.core.page.TableDataInfo;

@RestController
@RequestMapping("/reservation/setmeal")
public class TSetmealController extends BaseController
{
    @Autowired
    private ITSetmealService tSetmealService;

    @PreAuthorize("@ss.hasPermi('reservation:setmeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSetmeal tSetmeal)
    {
        startPage();
        List<TSetmeal> list = tSetmealService.selectTSetmealList(tSetmeal);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('reservation:setmeal:export')")
    @Log(title = "套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSetmeal tSetmeal)
    {
        List<TSetmeal> list = tSetmealService.selectTSetmealList(tSetmeal);
        ExcelUtil<TSetmeal> util = new ExcelUtil<TSetmeal>(TSetmeal.class);
        util.exportExcel(response, list, "套餐数据");
    }

    @PreAuthorize("@ss.hasPermi('reservation:setmeal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tSetmealService.selectTSetmealById(id));
    }

    @PreAuthorize("@ss.hasPermi('reservation:setmeal:add')")
    @Log(title = "套餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSetmealDto dto)
    {
        return toAjax(tSetmealService.insertTSetmeal(dto));
    }

    @PreAuthorize("@ss.hasPermi('reservation:setmeal:edit')")
    @Log(title = "套餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSetmealDto dto)
    {
        return toAjax(tSetmealService.updateTSetmeal(dto));
    }

    @PreAuthorize("@ss.hasPermi('reservation:setmeal:remove')")
    @Log(title = "套餐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(tSetmealService.deleteTSetmealById(id));
    }
}
