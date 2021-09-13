package com.amos.inventory.constant.skuDeal;

public enum SkuDealFreezeInventoryResultStatus
{
	UNKNOWN((byte)-100,"无法识别"),
	NOT_ENOUGH_STOCK((byte) -1, "库存不足"),
	HAVEN_EXIT((byte)-2,"单据已存在"),
	SUCCESS((byte) 1, "成功");

	private Byte code;

	private String desc;

	SkuDealFreezeInventoryResultStatus(Byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Byte getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}


	public static SkuDealFreezeInventoryResultStatus getByCode(Byte code)
	{
		SkuDealFreezeInventoryResultStatus s=UNKNOWN;
		for(SkuDealFreezeInventoryResultStatus value : values())
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
