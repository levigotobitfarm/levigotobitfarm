package org.levi.mybatis.excutor;

import org.levi.mybatis.config.Configuration;
import org.levi.mybatis.config.MapperStatement;
import org.levi.mybatis.core.BinderSql;
import org.levi.mybatis.util.GenericTokenParser;
import org.levi.mybatis.util.ParameterMapping;
import org.levi.mybatis.util.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExcutor implements Excutor {


    @Override
    public <E> List<E> queryList(Configuration configuration, MapperStatement mapperStatement, Object... params) throws Exception {


        Connection connection = configuration.getDataSource().getConnection();

        //解析sql
        String sqltxt = mapperStatement.getSqlTxt();
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sqltxt);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BinderSql binderSql = new BinderSql(parseSql,parameterMappings);

        PreparedStatement preparedStatement = getPreparedStatement(connection,binderSql,mapperStatement,params);
        ResultSet resultSet = preparedStatement.executeQuery();

        //解析一个resultSet
        List<Object> resultList = new ArrayList<>();
        Class<?> resultTypeClass = this.getParamTypeClass(mapperStatement.getResultType());
        while (resultSet.next()){

            Object result = resultTypeClass.getConstructor().newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {

                String cloumnName = metaData.getColumnName(i);
                Object cloumnValue = resultSet.getObject(i);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(cloumnName,resultTypeClass);
                propertyDescriptor.getWriteMethod().invoke(result,cloumnValue);
            }
            resultList.add(result);
        }
        return (List<E>) resultList;
    }

    private PreparedStatement getPreparedStatement(Connection connection, BinderSql binderSql, MapperStatement mapperStatement, Object[] params) throws Exception {

        PreparedStatement preparedStatement = connection.prepareStatement(binderSql.getSqltxt());


        Class<?> paramTypeClass = getParamTypeClass(mapperStatement.getParamType());
        if(paramTypeClass == null){
            return preparedStatement;
        }
        List<ParameterMapping> parameterMappings = binderSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {

            ParameterMapping parameterMapping = parameterMappings.get(i);
            Field declaredField = paramTypeClass.getDeclaredField(parameterMapping.getContent());
            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,fieldValue);

        }
        return preparedStatement;
    }

    private Class<?> getParamTypeClass(String paramType) throws ClassNotFoundException {

        if (paramType == null || paramType == " ") {
            return null;
        }
        return Class.forName(paramType);
    }
}
