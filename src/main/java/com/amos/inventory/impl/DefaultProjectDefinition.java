package com.amos.inventory.impl;

import com.amos.inventory.core.ProjectDefinition;

public class DefaultProjectDefinition implements ProjectDefinition
{
    private String magicCode;

    private String moduleEnglishName;

    public DefaultProjectDefinition(String magicCode, String moduleEnglishName)
    {
        this.magicCode = magicCode;
        this.moduleEnglishName = moduleEnglishName;
    }

    @Override public String getMagicCode()
    {
        return magicCode;
    }

    public void setMagicCode(String magicCode)
    {
        this.magicCode = magicCode;
    }

    @Override public String getModuleEnglishName()
    {
        return moduleEnglishName;
    }

    public void setModuleEnglishName(String moduleEnglishName)
    {
        this.moduleEnglishName = moduleEnglishName;
    }
}
