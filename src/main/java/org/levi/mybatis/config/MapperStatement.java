package org.levi.mybatis.config;

public class MapperStatement {

    private String sqlTxt;

    private String id;

    private String paramType;

    private String resultType;

    public String getSqlTxt() {
        return sqlTxt;
    }

    public void setSqlTxt(String sqlTxt) {
        this.sqlTxt = sqlTxt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
