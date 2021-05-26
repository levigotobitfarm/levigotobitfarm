package org.levi.mybatis.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.levi.mybatis.config.MapperStatement;
import org.levi.mybatis.io.Resource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlMapperParseBuilder {

    private InputStream inputStream;
    private Map<String, MapperStatement> mappers;

    public XmlMapperParseBuilder(InputStream inputStream, Map<String, MapperStatement> mappers) {
        this.inputStream = inputStream;
        this.mappers = mappers;
    }


    public void parse() throws DocumentException, FileNotFoundException {

        SAXReader saxReader = new SAXReader();
        Element root = saxReader.read(inputStream).getRootElement();
        String namespace = root.attributeValue("namespace");
        List<Element> selectElements = root.selectNodes("//select");
        for(Element element: selectElements){
            MapperStatement mapperStatement = new MapperStatement();
            mapperStatement.setId(element.attributeValue("id"));
            mapperStatement.setParamType(element.attributeValue("parameterType"));
            mapperStatement.setResultType(element.attributeValue("resultType"));
            mapperStatement.setSqlTxt(element.getTextTrim());
            mappers.put(namespace+"."+element.attributeValue("id"),mapperStatement);
        }
    }


}
