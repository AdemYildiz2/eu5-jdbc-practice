package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example {

    String dbUrl="jdbc:oracle:thin:@54.237.100.89:1521:xe";
    String dbUsername="hr";
    String dbPassword="hr";

    @Test
    public void test1() throws SQLException {
        //Create Connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from departments");

        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        System.out.println("===========");
        //(We can use different queries in the same test with the resultSet object

        resultSet=statement.executeQuery("Select * from regions");

        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }
    @Test
    public void countNavigate() throws SQLException {
        //Create Connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from departments");

        //EX- how to find how many rows for the query(we use two methods to find it)
        //Go to first row
        resultSet.last();
        //then,  get the row count
        System.out.println(resultSet.getRow());

        //EX- get all departments name
        //we need to move before first row to get full list since we are at the last row right now
        resultSet.beforeFirst(); // use it whenever you want to go all the way up.

        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }
    @Test
    public void MetaDataExample() throws SQLException {
        //Create Connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from departments");

        //Get the database related data inside the dbMetadata object
        DatabaseMetaData dbMetadata = connection.getMetaData();

        System.out.println("User = "+ dbMetadata.getUserName());
        System.out.println("Database Product Name = "+ dbMetadata.getDatabaseProductName());
        System.out.println("Database Product Version = "+ dbMetadata.getDatabaseProductVersion());
        System.out.println("Driver Name = "+ dbMetadata.getDriverName());
        System.out.println("Driver Version = "+ dbMetadata.getDriverVersion());

        //get the resultset object metadata -->gives very useful info from our query
        ResultSetMetaData rsMetadata = resultSet.getMetaData();

        //how many columns we have ?
        int colCount = rsMetadata.getColumnCount();
        System.out.println(colCount);

        //columns names
        System.out.println(rsMetadata.getColumnName(1));
        System.out.println(rsMetadata.getColumnName(2));

        //Print all the column names dynamically.
        for (int i = 1; i <= colCount; i++) {
            System.out.println(rsMetadata.getColumnName(i));
        }



        //close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }


}
