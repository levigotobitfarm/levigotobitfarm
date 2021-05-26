package org.levi.mybatis.excutor;

import org.levi.mybatis.config.Configuration;
import org.levi.mybatis.config.MapperStatement;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Excutor {

    <E> List<E> queryList(Configuration configuration, MapperStatement mapperStatement, Object ... params) throws Exception;

}
