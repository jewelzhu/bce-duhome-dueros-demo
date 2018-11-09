package com.baidubce.iot.duhome.demo.util;

import java.io.Serializable;

public class Hello implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    public Hello(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}