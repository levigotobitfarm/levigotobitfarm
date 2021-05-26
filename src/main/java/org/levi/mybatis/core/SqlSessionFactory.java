package org.levi.mybatis.core;

import org.levi.mybatis.config.Configuration;
import org.levi.mybatis.sqlSession.SqlSession;

public interface SqlSessionFactory {

    SqlSession openSession();
}
