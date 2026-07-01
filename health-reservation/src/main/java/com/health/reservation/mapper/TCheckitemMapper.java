package com.health.reservation.mapper;

import java.util.List;
import com.health.reservation.domain.TCheckitem;

/**
 * 检查项Mapper接口
 * 
 * @author ruoyi
 * @date 2026-06-30
 */
public interface TCheckitemMapper 
{
    /**
     * 查询检查项
     * 
     * @param id 检查项主键
     * @return 检查项
     */
    public TCheckitem selectTCheckitemById(Long id);

    /**
     * 查询检查项列表
     * 
     * @param tCheckitem 检查项
     * @return 检查项集合
     */
    public List<TCheckitem> selectTCheckitemList(TCheckitem tCheckitem);

    /**
     * 新增检查项
     * 
     * @param tCheckitem 检查项
     * @return 结果
     */
    public int insertTCheckitem(TCheckitem tCheckitem);

    /**
     * 修改检查项
     * 
     * @param tCheckitem 检查项
     * @return 结果
     */
    public int updateTCheckitem(TCheckitem tCheckitem);

    /**
     * 删除检查项
     * 
     * @param id 检查项主键
     * @return 结果
     */
    public int deleteTCheckitemById(Long id);

    /**
     * 批量删除检查项
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTCheckitemByIds(Long[] ids);
}
