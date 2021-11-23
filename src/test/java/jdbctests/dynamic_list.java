package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dynamic_list {
    String dbUrl="jdbc:oracle:thin:@54.237.100.89:1521:xe";
    String dbUsername="hr";
    String dbPassword="hr";
    @Test
    public void dynmaic_list() throws SQLException {
        //Create Connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select*from countries");

        //get the resultset object metadata -->gives very useful info from our query
        ResultSetMetaData rsMetadata = resultSet.getMetaData();

        //WE WANT EACH ROW ONE BY ONE GO INSIDE THE MAP, AND MAPS WILL BE ADDED THE LIST
        //IT MUST BE INDEPENDENT FROM COLUMN NUM AND ROW NUM, IT NEEDS WORKING FOR EACH QUERY
        //List for keeping all rows a map
        List<Map<String,Object>> queryData = new ArrayList<>();

        //Number of columns
        int colCount= rsMetadata.getColumnCount();

        //loop through each row
        while(resultSet.next()){
            Map<String,Object> row = new HashMap<>();

            for (int i = 1; i <= colCount; i++) {
                row.put(rsMetadata.getColumnName(i),resultSet.getObject(i));

            }

            //add your map to your list
            queryData.add(row);
        }

        //print the result
        for (Map<String, Object> row : queryData) {
            System.out.println(row.toString());
        }


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }
}
