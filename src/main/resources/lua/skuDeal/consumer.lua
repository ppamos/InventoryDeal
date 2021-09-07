--keys  1.versionKey 2. waitAckName 3.checkFreezeName 4. deadFreezeName
-- status 1.冻结中 2.等待ack


local status = redis.call("hget", KEYS[1], "status")
if status == false or tonumber(status) ~= 1 then
    return -1
end


local function doConsumerInventory(inventoryKey, currentInventoryKeyNum)
    redis.call("HINCRBY", inventoryKey, "book_amount", -tonumber(currentInventoryKeyNum))
end

local function doConsumer()
    local inventoryKeyTable = loadstring("return" .. redis.call("hget", KEYS[1], "inventoryKeyList"))()
    for i, v in ipairs(inventoryKeyTable) do
        local inventoryKey = inventoryKeyTable[i]
        local currentInventoryKeyNum = redis.call("hget", KEYS[1], inventoryKey .. "_amount")
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
