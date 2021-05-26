package org.levi.mybatis.config;



import javax.sql.DataSource;
import java.util.Map;

public class Configuration {

    private DataSource dataSource;

    private Map<String,MapperStatement> mapperStatementMap;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSources) {
        this.dataSource = dataSources;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void setMapperStatementMap(Map<String, MapperStatement> mapperStatementMap) {
        this.mapperStatementMap = mapperStatementMap;
    }
}
