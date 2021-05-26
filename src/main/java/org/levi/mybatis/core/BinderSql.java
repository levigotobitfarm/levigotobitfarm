package org.levi.mybatis.core;

import org.levi.mybatis.util.ParameterMapping;

import java.util.List;

public class BinderSql {

    private String sqltxt;
    private List<ParameterMapping> parameterMappings;

    public BinderSql(String sqltxt, List<ParameterMapping> parameterMappings) {

        this.sqltxt = sqltxt;
        this.parameterMappings = parameterMappings;
    }

    public String getSqltxt() {
        return sqltxt;
    }

    public void setSqltxt(String sqltxt) {
        this.sqltxt = sqltxt;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
