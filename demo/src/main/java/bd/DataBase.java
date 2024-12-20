package bd;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataBase {
    public void openConnection(); 
    public void closeConnection();
    public ResultSet executeSelect() throws SQLException;
    public int executeUpdate() throws SQLException;
    public void initPreparedStatement(String sql) throws SQLException;


    
} 