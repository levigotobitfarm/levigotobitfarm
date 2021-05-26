package org.levi.mybatis.io;

import org.levi.mybatis.config.MapperStatement;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Resource {

    public static InputStream getResource(String filePath) throws FileNotFoundException {

        if(filePath == null || " ".equals(filePath)){
            throw new FileNotFoundException("file path is empty!");
        }
        return Resource.class.getClassLoader().getResourceAsStream(filePath);
    }
}
