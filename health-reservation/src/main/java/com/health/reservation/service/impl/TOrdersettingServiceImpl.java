package com.health.reservation.service.impl;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.health.reservation.vo.OrderSettingVO;
import com.health.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.health.reservation.mapper.TOrdersettingMapper;
import com.health.reservation.domain.TOrdersetting;
import com.health.reservation.service.ITOrdersettingService;

/**
 * 预约设置Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-07-07
 */
@Service
public class TOrdersettingServiceImpl implements ITOrdersettingService 
{
    @Autowired
    private TOrdersettingMapper tOrdersettingMapper;

    /**
     * 查询预约设置
     * 
     * @param id 预约设置主键
     * @return 预约设置
     */
    @Override
    public TOrdersetting selectTOrdersettingById(Long id)
    {
        return tOrdersettingMapper.selectTOrdersettingById(id);
    }

    /**
     * 查询预约设置列表
     * 
     * @param tOrdersetting 预约设置
     * @return 预约设置
     */
    @Override
    public List<TOrdersetting> selectTOrdersettingList(TOrdersetting tOrdersetting)
    {
        return tOrdersettingMapper.selectTOrdersettingList(tOrdersetting);
    }

    /**
     * 新增预约设置
     * 
     * @param tOrdersetting 预约设置
     * @return 结果
     */
    @Override
    public int insertTOrdersetting(TOrdersetting tOrdersetting)
    {
        return tOrdersettingMapper.insertTOrdersetting(tOrdersetting);
    }

    /**
     * 修改预约设置
     * 
     * @param tOrdersetting 预约设置
     * @return 结果
     */
    @Override
    public int updateTOrdersetting(TOrdersetting tOrdersetting)
    {
        return tOrdersettingMapper.updateTOrdersetting(tOrdersetting);
    }

    /**
     * 批量删除预约设置
     * 
     * @param ids 需要删除的预约设置主键集合
     * @return 结果
     */
    @Override
    public int deleteTOrdersettingByIds(Long[] ids)
    {
        return tOrdersettingMapper.deleteTOrdersettingByIds(ids);
    }

    /**
     * 删除预约设置信息
     * 
     * @param id 预约设置主键
     * @return 结果
     */
    @Override
    public int deleteTOrdersettingById(Long id)
    {
        return tOrdersettingMapper.deleteTOrdersettingById(id);
    }

    @Override
    public List<OrderSettingVO> getOrderSettingByMonth(String month)
    {
        List<TOrdersetting> settings = tOrdersettingMapper.selectByYearMonth(month);

        return settings.stream().map(setting -> {
            OrderSettingVO vo = new OrderSettingVO();
            vo.setId(setting.getId());
            vo.setDate(setting.getOrderDate().toInstant()
                    .atZone(ZoneId.of("Asia/Shanghai"))
                    .toLocalDate().getDayOfMonth());
            vo.setNumber(setting.getNumber());
            vo.setReservations(setting.getReservations());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importOrderSetting(MultipartFile file) throws Exception
    {
        ExcelUtil<TOrdersetting> util = new ExcelUtil<TOrdersetting>(TOrdersetting.class);
        List<TOrdersetting> list = util.importExcel(file.getInputStream());

        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Excel 文件内容为空");
        }

        List<TOrdersetting> insertList = new ArrayList<>();
        List<TOrdersetting> updateList = new ArrayList<>();

        for (TOrdersetting setting : list) {
            if (setting == null) {
                continue;
            }
            if (setting.getOrderDate() == null || setting.getNumber() == null) {
                continue;
            }

            TOrdersetting existing = tOrdersettingMapper.selectByOrderDate(setting.getOrderDate());
            if (existing != null) {
                setting.setId(existing.getId());
                updateList.add(setting);
            } else {
                insertList.add(setting);
            }
        }

        if (!insertList.isEmpty()) {
            tOrdersettingMapper.insertBatch(insertList);
        }

        for (TOrdersetting item : updateList) {
            tOrdersettingMapper.updateTOrdersetting(item);
        }
    }
}
