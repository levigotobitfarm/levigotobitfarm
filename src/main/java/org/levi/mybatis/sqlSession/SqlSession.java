package org.levi.mybatis.sqlSession;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {

   <E> List<E> selectList(String statementId,Object ... param) throws SQLException, Exception;

   <T> T selectOne(String statementId,Object ... param) throws SQLException, Exception;

   <T> T getMapper(Class<?> mapperClass);

}
