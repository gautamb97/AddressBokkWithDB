package addressbookwithdb;

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

    public boolean checkAddressBookDataIsSyncWithDB(String firstName){
        List<AddressBookData> addressBookDataList = addressBookDBService.getAddressBookData(firstName);
        return addressBookDataList.get(0).equals(getAddressBookData(firstName));
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

