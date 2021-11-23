package jdbctests;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        //here we have added connection string, ip address and user name with password to intellij
        String dbUrl="jdbc:oracle:thin:@54.237.100.89:1521:xe";
        String dbUsername="hr";
        String dbPassword="hr";

        //Create Connection(which db you will connect) --> comes from java.sql,
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object --> comes from java.sql
        Statement statement = connection.createStatement();
        //run query and get the result in resultset object --> comes from java.sql
        ResultSet resultSet = statement.executeQuery("Select first_name,last_name,salary from employees");


        // move the pointer to the first row
         resultSet.next();
//
//        //get "Europe"  , to get value of column with two ways
//        System.out.println(resultSet.getString("region_name")); //1st way with column name
//        System.out.println(resultSet.getString(2));             //2nd way with column index, index starts 1
//
//        // get "1 - Europe"
//        System.out.println(resultSet.getInt(1)+ " - "+resultSet.getString(2));
//        //or
//        System.out.println(resultSet.getInt(1)+ " - "+resultSet.getString("region_name"));
//
//        //move the pointer to the second row
//        resultSet.next();
//
//        //get "Americas"
//        System.out.println(resultSet.getString("region_name")); //1st way  with column name
//        System.out.println(resultSet.getString(2));             //2nd way-with column index start 1
//        //get  "2 - Americas"
//        System.out.println(resultSet.getInt(1)+ "- "+ resultSet.getString("region_name"));
        while(resultSet.next()){
            System.out.println(resultSet.getString(1) +" - "+ resultSet.getString(2)+ " - " +resultSet.getInt(3));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();



    }
}
