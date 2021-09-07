-- keys 1.versionKey 2.checkFreezeName 3.deadFreezeName

local status = redis.call("hget", KEYS[1], "status")
if status == false or tonumber(status) ~= 1 then
    return -1
end

local function doReleaseInventory(inventoryKey,currentInventoryKeyNum)
    redis.call("HINCRBY", inventoryKey, "stock_amount", tonumber(currentInventoryKeyNum))
end

local function doRelease()
    local inventoryKeyTable=loadstring("return" .. redis.call("hget",KEYS[1],"inventoryKeyList"))()
    for i, v in ipairs(inventoryKeyTable) do
        local inventoryKey=inventoryKeyTable[i]
        local currentInventoryKeyNum = redis.call("hget", KEYS[1], inventoryKey .. "_amount")
        doReleaseInventory(inventoryKey,currentInventoryKeyNum)
    end
    redis.call("zrem", KEYS[2], KEYS[1])
    redis.call("srem", KEYS[3], KEYS[1])
end

local function removeOrder()
    redis.call("del",KEYS[1])
end

doRelease()

removeOrder()

return 1