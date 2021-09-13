package com.amos.inventory.constant.skuDeal;

public enum SkuDealAddInventoryResultStatus
{
    UNKNOWN((byte)-100,"无法识别"),
    SUCCESS((byte) 1, "成功"),;

    private Byte code;

    private String desc;

    SkuDealAddInventoryResultStatus(Byte code, String desc)
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

    public static SkuDealAddInventoryResultStatus getByCode(Byte code)
    {
        SkuDealAddInventoryResultStatus s=UNKNOWN;
        for(SkuDealAddInventoryResultStatus value : values())
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
