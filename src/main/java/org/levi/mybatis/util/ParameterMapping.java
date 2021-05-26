package org.levi.mybatis.util;

public class ParameterMapping {

    /**
     * 参数名：id, name...
     */
    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
