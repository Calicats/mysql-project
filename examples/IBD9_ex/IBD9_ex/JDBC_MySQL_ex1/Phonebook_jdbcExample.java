

package JdbcExample;
import java.sql.*;

public class JdbcExample {
   public static void main(String[] args) {
      try {
          Class.forName("com.mysql.jdbc.Driver").newInstance();
      }
      catch (Exception ex) {
          System.err.println("An Exception occured during JDBC Driver loading." + 
             " Details are provided below:");
          ex.printStackTrace(System.err);
      }
      Connection connection = null;
      Statement selectStatement = null, insertStatement = null;
      ResultSet rs = null;
      ResultSetMetaData rsmd = null;
      try {
          connection = DriverManager.
             getConnection("jdbc:mysql://localhost/PhoneBook?user=root&password=");
          insertStatement = connection.createStatement();
          insertStatement.execute("INSERT INTO Contacts (FirstName, LastName, HomePhone, MobilePhone, EmailAddress) " + 
             "VALUES ('John','Doe','0040264123456','0040722123456','john.doe@example.com')");
          insertStatement.execute("INSERT INTO Contacts (FirstName, LastName, HomePhone, MobilePhone, EmailAddress) " + 
             "VALUES ('Jane','Doe','0040264123456','0040744123456','jane.doe@example.com')");
          selectStatement = connection.createStatement();
          selectStatement.execute("SELECT * FROM Contacts");
          rs = selectStatement.getResultSet();
          rsmd = rs.getMetaData();
          System.out.println("There are " + rsmd.getColumnCount() + " columns in the result set:");
          for (int i = 1; i <= rsmd.getColumnCount(); i++)
             System.out.println("\t Column " + (i) + " is " + rsmd.getColumnName(i));
          int rowCount = 0;
          while(rs.next()){
             System.out.println("Displaying information on row: " + (++rowCount));
             System.out.println("\tUser Identifier: " + rs.getString("UID"));
             System.out.println("\tFirst Name: " + rs.getString("FirstName"));
             System.out.println("\tLast Name: " + rs.getString("LastName"));
             System.out.println("\tHome Phone: " + rs.getString("HomePhone"));
             System.out.println("\tMobile Phone: " + rs.getString("MobilePhone"));
             System.out.println("\tEmail Address: " + rs.getString("EmailAddress"));
          }
      }
      catch(SQLException sqlex) {
          System.err.println("An SQL Exception occured. Details are provided below:");
          sqlex.printStackTrace(System.err);
      }
      finally {
         if (rs != null) { 
            try { 
                rs.close();  
            } 
            catch(SQLException e) {
            }  
            rs = null;  
         }
         if (selectStatement != null) { 
            try { 
                selectStatement.close();  
            } 
            catch(SQLException e) {}  
            selectStatement = null;  
         }
         if (insertStatement != null) { 
            try { 
                insertStatement.close();  
            } 
            catch(SQLException e) {}  
            insertStatement = null;  
         }
         if (connection != null) { 
            try { 
                connection.close();  
            } 
            catch(SQLException e) {}  
            connection = null;  
         }
      }
   } 
}
