package com.health.reservation.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.health.common.annotation.Excel;
import com.health.common.core.domain.BaseEntity;

public class TSetmeal extends BaseEntity
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

    @Excel(name = "年龄范围")
    private String age;

    @Excel(name = "价格")
    private Long price;

    @Excel(name = "注意事项")
    private String attention;

    @Excel(name = "图片路径")
    private String img;

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

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getAge()
    {
        return age;
    }

    public void setPrice(Long price)
    {
        this.price = price;
    }

    public Long getPrice()
    {
        return price;
    }

    public void setAttention(String attention)
    {
        this.attention = attention;
    }

    public String getAttention()
    {
        return attention;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getImg()
    {
        return img;
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
            .append("age", getAge())
            .append("price", getPrice())
            .append("attention", getAttention())
            .append("img", getImg())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
