package org.levi.mybatis.core;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.levi.mybatis.config.Configuration;
import org.levi.mybatis.config.MapperStatement;
import org.levi.mybatis.io.Resource;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class XmlConfigParseBuilder {

    private InputStream inputStream;


    public XmlConfigParseBuilder(InputStream inputStream) {

        this.inputStream = inputStream;
    }

    public Configuration parse() throws DocumentException, PropertyVetoException, FileNotFoundException {

        Configuration configuration = new Configuration();
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(this.inputStream);
        Element root = document.getRootElement();

        List<Element> propertiesElements = root.selectNodes("//property");
        Properties properties = new Properties();
        for(Element element : propertiesElements){
            properties.put(element.attributeValue("name"),element.attributeValue("value"));
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        //解析mapper.xml
        List<Element> mapperElements = root.selectNodes("//mapper");
        Map<String, MapperStatement> mappers = new HashMap<>();
        for(Element element: mapperElements){
            String fileName = element.attributeValue("resource");
            new XmlMapperParseBuilder(Resource.getResource(fileName),mappers).parse();
        }
        configuration.setMapperStatementMap(mappers);
        return configuration;
    }
}
