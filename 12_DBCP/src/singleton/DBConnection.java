package singleton;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : ShEnUx
 * @time : 11:25 PM
 * @date : 12/19/2022
 * @since : 0.1.0
 **/
public class DBConnection {
    private static DBConnection dbConnection;
    BasicDataSource dataSource;

    private DBConnection() {
        //How to configure DBCP pool
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/JEPOS");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        dataSource.setMaxTotal(2);//how many connection (ඕන connection ගාන දානවා)
        dataSource.setInitialSize(2);//how many connection should be initialized from created connection (දාපු connection ගානින් active කරලා තියන්න ඕන connection ගාන)
    }

    public static DBConnection getDbConnection() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
