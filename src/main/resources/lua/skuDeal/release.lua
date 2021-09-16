-- keys 1.versionKey 2.checkFreezeName 3.deadFreezeName 4.status 5.stockAmountName  6.inventoryKeyListName 7.amountNameSuffix


local status = redis.call("hget", KEYS[1], KEYS[4])
if status == false or tonumber(status) ~= 1 then
    return -1
end

local function doReleaseInventory(inventoryKey,currentInventoryKeyNum)
    redis.call("HINCRBY", inventoryKey, KEYS[5], tonumber(currentInventoryKeyNum))
end

local function doRelease()
    local inventoryKeyTable=loadstring("return" .. redis.call("hget",KEYS[1],KEYS[6]))()
    for i, v in ipairs(inventoryKeyTable) do
        local inventoryKey=inventoryKeyTable[i]
        local currentInventoryKeyNum = redis.call("hget", KEYS[1], inventoryKey .. KEYS[7])
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