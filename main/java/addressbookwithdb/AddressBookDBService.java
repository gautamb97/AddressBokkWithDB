package addressbookwithdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
        String userName = "root";
        String password = "gautam971997";
        Connection con;
        System.out.println("Connecting to database:"+jdbcURL);
        con = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful!!!!");
        return con;
    }

    public List<AddressBookData> readData(){
        String sql = "SELECT * FROM address_book; ";
        List<AddressBookData> addressBookContactList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                int zipCode = resultSet.getInt("ZipCode");
                long phoneNumber = resultSet.getLong("PhoneNumber");
                String email = resultSet.getString("Email");
                addressBookContactList.add(new AddressBookData(firstName, lastName, address, city, state, zipCode, phoneNumber, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookContactList;
    }
}
