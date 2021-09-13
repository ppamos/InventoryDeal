package com.amos.inventory.constant.skuDeal;

public enum SkuDealConsumerInventoryResultStatus
{
    UNKNOWN((byte)-100,"无法识别"),
    ERROR_STATUS((byte)-1,"错误单据状态"),
    SUCCESS((byte) 1, "成功");

    private Byte code;

    private String desc;

    SkuDealConsumerInventoryResultStatus(Byte code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }

    public static SkuDealConsumerInventoryResultStatus getByCode(Byte code)
    {
        SkuDealConsumerInventoryResultStatus s=UNKNOWN;
        for(SkuDealConsumerInventoryResultStatus value : values())
        {
            if(value.code.equals(code))
            {
                s=value;
                break;
            }
        }
        return s;
    }
}
