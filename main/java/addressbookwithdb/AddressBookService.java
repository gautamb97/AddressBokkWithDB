package addressbookwithdb;

import java.time.LocalDate;
import java.util.List;

public class AddressBookService {
    private AddressBookDBService addressBookDBService;

    public enum IOService{DB_IO};
    public List<AddressBookData> addressBookContactList;

    public AddressBookService(){
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public AddressBookService(List<AddressBookData> addressBookContactList){
        this();
        this.addressBookContactList = addressBookContactList;
    }


    public List<AddressBookData> readAddressBookData(IOService ioService){
        if(ioService.equals(IOService.DB_IO))
            this.addressBookContactList = new AddressBookDBService().readData();
        return this.addressBookContactList;
    }

    public List<AddressBookData> readAddressBookDataByState(IOService ioService, String state) {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getContactByState(state);
        return null;
    }

    public List<AddressBookData> readAddressBookDataByCity(IOService ioService, String city) {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getContactByCity(city);
        return null;
    }

    public void addingNewContactToBook(String firstName, String lastName, String address, String city, String state, int zip, long phoneNumber, String email, LocalDate addedDate) {
        addressBookContactList.add(addressBookDBService.addingNewContactToBook(firstName, lastName, address, city, state, zip, phoneNumber, email, addedDate));
    }

    public boolean checkAddressBookDataIsSyncWithDB(String firstName){
        List<AddressBookData> addressBookDataList = addressBookDBService.getAddressBookData(firstName);
        return addressBookDataList.get(0).equals(getAddressBookData(firstName));
    }

    public List<AddressBookData> readContactDateRange(IOService ioService, LocalDate addedDate, LocalDate endDate) {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getContactForDateRange(addedDate, endDate);
        return null;
    }

    public void updatePersonByAddress(String firstName, String address){
        int result = addressBookDBService.updateContactData(firstName, address);
        if(result == 0) return;
        AddressBookData addressBookData = this.getAddressBookData(firstName);
        if(addressBookData != null) addressBookData.address = address;
    }

    private AddressBookData getAddressBookData(String name){
        return  this.addressBookContactList.stream()
                .filter(addressBookData -> addressBookData.firstName.equals(name))
                .findFirst()
                .orElse(null);
    }
}

