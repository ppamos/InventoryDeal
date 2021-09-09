package com.amos.inventory.core;

public interface ActualCheck
{
    void doCheckFreeze(String version);

    void doAckCheck(String version);

    void doDeadFreezeCheck(String version);

    void doDeadAckCheck(String version);
}
