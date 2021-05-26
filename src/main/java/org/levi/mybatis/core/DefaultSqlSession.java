package org.levi.mybatis.core;

import org.levi.mybatis.config.Configuration;
import org.levi.mybatis.config.MapperStatement;
import org.levi.mybatis.excutor.SimpleExcutor;
import org.levi.mybatis.sqlSession.SqlSession;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {

        this.configuration = configuration;
    }


    @Override
    public <E> List<E> selectList(String statementId, Object... param) throws Exception {

        MapperStatement mapperStatement = this.configuration.getMapperStatementMap().get(statementId);
        return new SimpleExcutor().queryList(configuration,mapperStatement,param);
    }

    @Override
    public <T> T selectOne(String statementId, Object... param) throws Exception {

        List<T> result = this.selectList(statementId,param);
        if(result.size()>1){
            throw new SQLException("more result foud!");
        }
        return result.get(0);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {

        Object objectProxy = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String classname = method.getDeclaringClass().getName();
                String methodName = method.getName();
                String statementId = classname+"."+methodName;
                MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
                if(mapperStatement.getSqlTxt().trim().startsWith("select")){
                    Type genericReturnType = method.getGenericReturnType();
                    if(genericReturnType instanceof ParameterizedType){
                        return selectList(statementId,args);
                    }
                    return selectOne(statementId,args);
                }
                return null;
            }
        });
        return (T) objectProxy;
    }
}
