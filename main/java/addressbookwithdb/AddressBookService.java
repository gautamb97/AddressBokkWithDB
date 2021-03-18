package addressbookwithdb;

import java.util.List;

public class AddressBookService {
    public AddressBookService(){}

    public enum IOService{DB_IO};
    public List<AddressBookData> addressBookContactList;

    public List<AddressBookData> readAddressBookData(IOService ioService){
        if(ioService.equals(IOService.DB_IO))
            this.addressBookContactList = new AddressBookDBService().readData();
        return this.addressBookContactList;
    }
}
