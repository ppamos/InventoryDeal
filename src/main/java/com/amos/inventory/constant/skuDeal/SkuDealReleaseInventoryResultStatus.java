package com.amos.inventory.constant.skuDeal;

public enum SkuDealReleaseInventoryResultStatus
{
    UNKNOWN((byte)-100,"无法识别"),
    ERROR_STATUS((byte)-1,"错误单据状态"),
    SUCCESS((byte) 1, "成功");

    private Byte code;

    private String desc;

     SkuDealReleaseInventoryResultStatus(Byte code, String desc)
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

    public static SkuDealReleaseInventoryResultStatus getByCode(Byte code)
    {
        SkuDealReleaseInventoryResultStatus s=UNKNOWN;
        for(SkuDealReleaseInventoryResultStatus value : values())
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
