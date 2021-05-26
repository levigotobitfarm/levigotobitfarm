package org.levi.mybatis.core;

import org.levi.mybatis.config.Configuration;
import org.levi.mybatis.sqlSession.SqlSession;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {

        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(this.configuration);
    }
}
