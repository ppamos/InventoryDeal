-- keys 1.versionKey 2.waitAckName 3.deadAckName 4.stautsName


local status = redis.call("hget", KEYS[1], KEYS[4])
if status == false or tonumber(status) ~= 2 then
    return -1
end

local function removeOrder()
    redis.call("del",KEYS[1])
    redis.call("srem", KEYS[2], KEYS[1])
    redis.call("srem", KEYS[3], KEYS[1])
end

removeOrder()
return 1