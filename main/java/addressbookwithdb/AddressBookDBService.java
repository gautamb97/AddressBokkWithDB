package addressbookwithdb;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookDataStatement;

    public AddressBookDBService(){}

    public static AddressBookDBService getInstance(){
        if(addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
        String userName = "root";
        String password = "gautam971997";
        Connection connection;
        System.out.println("Connecting to database:"+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful!!!!");
        return connection;
    }

    public List<AddressBookData> readData(){
        String sql = "SELECT * FROM address_book; ";
        return getContactDataUsingDB(sql);
    }

    public List<AddressBookData> getAddressBookData(String name){
        List<AddressBookData> addressBookDataList = null;
        if(this.addressBookDataStatement == null)
            this.prepareStatementForContactData();
        try{
            addressBookDataStatement.setString(1, name);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            addressBookDataList = this.getAddressBookData(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    private List<AddressBookData> getAddressBookData(ResultSet resultSet){
        List<AddressBookData> addressBookDataList = new ArrayList<>();
        try{
            while(resultSet.next()){
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                int zipCode = resultSet.getInt("ZipCode");
                long phoneNumber = resultSet.getLong("PhoneNumber");
                String email = resultSet.getString("Email");
                addressBookDataList.add(new AddressBookData(firstName, lastName, address, city, state, zipCode, phoneNumber, email));
        }
    } catch (SQLException e){
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    private void prepareStatementForContactData(){
        try{
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM address_book WHERE FirstName = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int updateContactData(String name, String address){
        return this.updateContactDataUsingStatement(name, address);
    }

    private int updateContactDataUsingStatement(String name, String address) {
        String sql = String.format("update address_book set address = '%s' where FirstName = '%s';", address, name);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<AddressBookData> getContactForDateRange(LocalDate addedDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM address_book where date BETWEEN '%s' AND '%s';",
                Date.valueOf(addedDate), Date.valueOf(endDate));
        return getContactDataUsingDB(sql);
    }

    private List<AddressBookData> getContactDataUsingDB(String sql) {
        List<AddressBookData> addressBookContactList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookContactList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookContactList;
    }

    public List<AddressBookData> getContactByState(String state) {
        String sql = String.format("SELECT * FROM address_book where state = '%s';", state);
        return getContactDataUsingDB(sql);
    }

    public List<AddressBookData> getContactByCity(String city) {
        String sql = String.format("SELECT * FROM address_book where city = '%s';",city);
        return getContactDataUsingDB(sql);
    }

    public AddressBookData addingNewContactToBook(String firstName, String lastName, String address, String city, String state, int zip, long phoneNumber, String email, LocalDate addedDate) {
        AddressBookData addressBookData = null;
        String sql = String.format("INSERT INTO address_book (firstName, lastName, Address, City, State, ZipCode, PhoneNumber, Email , Date)" +
                "VALUES ( '%s', '%s', '%s', '%s', '%s', '%s', %s, '%s', '%s' );", firstName, lastName, address, city, state, zip, phoneNumber, email, Date.valueOf(addedDate));
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            addressBookData = new AddressBookData(firstName, lastName, address, city, state, zip, phoneNumber, email, addedDate);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return addressBookData;
    }
}
