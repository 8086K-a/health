package com.health.reservation.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.health.common.annotation.Excel;
import com.health.common.core.domain.BaseEntity;

public class TCheckgroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "编码")
    private String code;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "助记码")
    private String helpCode;

    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=不限")
    private String sex;

    @Excel(name = "注意事项")
    private String attention;

    @Excel(name = "说明")
    private String remark;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setHelpCode(String helpCode)
    {
        this.helpCode = helpCode;
    }

    public String getHelpCode()
    {
        return helpCode;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }

    public void setAttention(String attention)
    {
        this.attention = attention;
    }

    public String getAttention()
    {
        return attention;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("helpCode", getHelpCode())
            .append("sex", getSex())
            .append("attention", getAttention())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
