-- keys 1.versionKey 2.checkFreezeName 3.delayTime 4.inventoryKeyList 5.stockAmountName 6.bookAmountName 7.inventoryKeyListName
-- 8.checkFreezeTimesName 9.statusName 10waitAckTimesName 11.amountNameSuffix
-- argv 1.inventoryNumList

local inventoryKeyList = loadstring("return" .. KEYS[4])()

local inventoryNumList = loadstring("return" .. ARGV[1])()

local stockAmount = KEYS[5]
local bookAmount = KEYS[6]

local result = {}

local inventoryNumTemp = {}

local havenInitInventory = redis.call('EXISTS', KEYS[1])

if havenInitInventory == 1 then

    return { -2 }
end

-- 先判断所有库存是否足够
local function isStoreEnough()
    for i, v in ipairs(inventoryKeyList) do
        local inventoryKey = inventoryKeyList[i]
        local currentStockAmount = redis.call("HGET", inventoryKey, stockAmount)
        local currentNeedInventoryNum = inventoryNumList[i]
        if currentStockAmount then
            if tonumber(currentStockAmount) < tonumber(currentNeedInventoryNum) then
                table.insert(result, 1, -1)
                table.insert(result, 2, i)
                return false
            end
            table.insert(inventoryNumTemp, i, currentStockAmount)
        else
            table.insert(result, 1, -1)
            table.insert(result, 2, i)
            return false;
        end
    end
    return true
end

local function doJoinWaitFreezeSet()
    redis.call("zadd", KEYS[2], KEYS[3], KEYS[1])
end

local function doCreatOrder()
    redis.call("HMSET", KEYS[1], KEYS[7], KEYS[4], KEYS[8], 0, KEYS[9], 1, KEYS[10], 0)
    for i, v in ipairs(inventoryKeyList) do
        local inventoryKeyNum = inventoryKeyList[i] .. KEYS[11]
        redis.call("hset", KEYS[1], inventoryKeyNum, inventoryNumList[i])
    end
end

if isStoreEnough() == false then
    return result
end

local function doFreezeStock()
    for i, v in ipairs(inventoryKeyList) do
        local inventoryKey = inventoryKeyList[i]
        local currentStoreNum = inventoryNumTemp[i]
        local afterStockNum = tonumber(currentStoreNum) - tonumber(inventoryNumList[i])
        local currentBookAmount = redis.call("HGET", inventoryKey, bookAmount)
        local afterBookAmount = tonumber(currentBookAmount) + tonumber(inventoryNumList[i])
        redis.call('hmset', inventoryKey, stockAmount, afterStockNum, bookAmount, afterBookAmount)
    end
end

doJoinWaitFreezeSet()

doFreezeStock()

doCreatOrder()

return { 1 }