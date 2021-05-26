package org.levi.mybatis.core;

import org.dom4j.DocumentException;
import org.levi.mybatis.config.Configuration;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException, FileNotFoundException {

        Configuration configuration = new XmlConfigParseBuilder(in).parse();
        return new DefaultSqlSessionFactory(configuration);
    }
}
