--keys  1.versionKey 2. waitAckName 3.checkFreezeName 4. deadFreezeName 5.statusName 6.bookAmountName7.inventoryKeyListName 8.amountNameSuffix
-- status 1.冻结中 2.等待ack


local status = redis.call("hget", KEYS[1], KEYS[5])
if status == false or tonumber(status) ~= 1 then
    return -1
end


local function doConsumerInventory(inventoryKey, currentInventoryKeyNum)
    redis.call("HINCRBY", inventoryKey, KEYS[6], -tonumber(currentInventoryKeyNum))
end

local function doConsumer()
    local inventoryKeyTable = loadstring("return" .. redis.call("hget", KEYS[1], KEYS[7]))()
    for i, v in ipairs(inventoryKeyTable) do
        local inventoryKey = inventoryKeyTable[i]
        local currentInventoryKeyNum = redis.call("hget", KEYS[1], inventoryKey .. KEYS[8])
        doConsumerInventory(inventoryKey, currentInventoryKeyNum)
    end
    redis.call("hset", KEYS[1], status, 2)
    redis.call("zrem", KEYS[3], KEYS[1])
    redis.call("srem", KEYS[4], KEYS[1])
end

local function joinWaitAckSet()
    redis.call("sadd", KEYS[2], KEYS[1])
end

doConsumer();

joinWaitAckSet();

return 1
