package com.dingdonginc.zhangfang;

public class Check {
    private int cLog;
    private String cName;
    private int cIcon;
    private float cCost;


    public Check(int cLog, String cName, int cIcon, float cCost)
    {
        this.cLog=cLog;
        this.cName=cName;
        this.cIcon=cIcon;
        this.cCost=cCost;
    }

    public int getcLog()
    {
        return cLog;
    }

    public String getcName()
    {
        return cName;
    }

    public int getcIcon()
    {
        return cIcon;
    }

    public float getcCost()
    {
        return  cCost;
    }

    public void setcLog(int cLog)
    {
        this.cLog=cLog;
    }

    public void setcName(String cName)
    {
        this.cName=cName;
    }

    public void setcIcon(int cIcon)
    {
        this.cIcon=cIcon;
    }

    public void setcCost(float cCost)
    {
        this.cCost=cCost;
    }

}

