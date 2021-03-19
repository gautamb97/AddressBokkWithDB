package addressbookwithdb;

import java.time.LocalDate;
import java.util.Objects;

public class AddressBookData {

    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public String email;
    public int zipCode;
    public long phoneNumber;
    public LocalDate addedDate;

    public AddressBookData(String firstName, String lastName, String address, String city, String state,
                   int zipCode, long phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public AddressBookData(String firstName, String lastName, String address, String city, String state, int zipCode, long phoneNumber, String email, LocalDate addedDate){
        this(firstName,lastName,address,city,state,zipCode,phoneNumber,email);
        this.addedDate = addedDate;
    }


    @Override
    public String toString() {
        return "[firstName =" + firstName + ", lastName =" + lastName + ", Address =" + address + ", city =" + city + ", state =" + state
                + ", zip =" + zipCode + ", phone =" + phoneNumber + ", email address =" + email + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookData that = (AddressBookData) o;
        return zipCode == that.zipCode &&
                phoneNumber == that.phoneNumber &&
                Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) && Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) && Objects.equals(email, that.email);
    }

}

