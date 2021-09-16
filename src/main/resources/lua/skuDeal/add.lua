-- KEY 1.inventoryKey 2.bookAmountName 3.stockAmountName
-- argv 1.库存数量


local havenInitInventory = redis.call('EXISTS',KEYS[1])

local bookAmount = KEYS[2]

local stockAmount = KEYS[3]

local initBookAmount = '0'

local function doAddInventory()
    local currentStockAmount = redis.call('hget',KEYS[1],stockAmount)
    local newStockAmount = tonumber(currentStockAmount) + tonumber(ARGV[1])
    redis.call('hset',KEYS[1],stockAmount,newStockAmount)
end

local function doCreateInventory()
    redis.call("HMSET", KEYS[1], bookAmount, initBookAmount, stockAmount, ARGV[1])
end

if havenInitInventory == 1 then
    doAddInventory()
else
    doCreateInventory()
end

return 1

