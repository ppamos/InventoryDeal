package com.amos.inventory.constant.skuDeal;

public enum SkuDealAckInventoryResultStatus
{
    UNKNOWN((byte)-100,"无法识别"),
    ERROR_STATUS((byte)-1,"错误单据状态"),
    SUCCESS((byte) 1, "成功");

    private Byte code;

    private String desc;

    SkuDealAckInventoryResultStatus(Byte code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public static SkuDealAckInventoryResultStatus getByCode(Byte code)
    {
        SkuDealAckInventoryResultStatus s=UNKNOWN;
        for(SkuDealAckInventoryResultStatus value : values())
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
