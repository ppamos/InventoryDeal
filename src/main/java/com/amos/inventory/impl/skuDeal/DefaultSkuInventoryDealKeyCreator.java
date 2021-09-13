package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.core.InventoryDealKeyCreator;
import com.amos.inventory.core.ProjectDefinition;
import com.amos.inventory.util.Assert;

public class DefaultSkuInventoryDealKeyCreator implements InventoryDealKeyCreator
{
    private String glocalPrefix;
    
    private String inventoryKeyFormat;

    private String inventoryKeyFormatPrefix;

    private String versionKeyFormatPrefix;
    
    private String versionKeyFormat;
    
    private String checkFreezeName;
    
    private String deadCheckFreezeName;
    
    private String waitAckName;
    
    private String deadAckName;

    public DefaultSkuInventoryDealKeyCreator(ProjectDefinition projectDefinition)
    {
        Assert.noNull(projectDefinition,"projectDefinition is null");
        String magicCode = projectDefinition.getMagicCode();
        String moduleEnglishName = projectDefinition.getModuleEnglishName();
        Assert.notEmptyString(magicCode,"magicCode is null");
        Assert.notEmptyString(moduleEnglishName,"moduleEnglish is null");
        this.glocalPrefix=moduleEnglishName+"_"+magicCode+"_";
        this.inventoryKeyFormatPrefix=glocalPrefix+"inventory_";
        this.inventoryKeyFormat=inventoryKeyFormatPrefix+"%s";
        this.versionKeyFormatPrefix=glocalPrefix+"version_";
        this.versionKeyFormat=versionKeyFormatPrefix+"%s";
        this.checkFreezeName=glocalPrefix+"check_freeze";
        this.deadCheckFreezeName=glocalPrefix+"dead_check_freeze";
        this.waitAckName=glocalPrefix+"wait_ack";
        this.deadCheckFreezeName=glocalPrefix+"dead_ack";
    }

    @Override public String getInventoryKey(String inventoryCode)
    {
        return String.format(this.inventoryKeyFormat,inventoryCode);
    }

    @Override public String getVersionKey(String version)
    {
        return String.format(this.versionKeyFormat,version);
    }

    @Override public String getCheckFreezeName()
    {
        return this.checkFreezeName;
    }

    @Override public String getDeadCheckFreezeName()
    {
        return this.deadCheckFreezeName;
    }

    @Override public String getWaitAckName()
    {
        return this.waitAckName;
    }

    @Override public String getDeadAckName()
    {
        return this.deadAckName;
    }

    @Override public String getVersionByVersionKey(String versionKey)
    {
        Assert.notEmptyString(versionKey,"versionKey is null");
       return versionKey.split(this.versionKeyFormatPrefix)[1];
    }

    @Override public String getInventoryCodeByInventoryKey(String inventoryKey)
    {
        Assert.notEmptyString(inventoryKey,"inventoryKey is null");
        return inventoryKey.split(this.inventoryKeyFormatPrefix)[1];
    }
}
