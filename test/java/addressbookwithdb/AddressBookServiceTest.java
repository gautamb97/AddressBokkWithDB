package addressbookwithdb;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static addressbookwithdb.AddressBookService.IOService.DB_IO;
public class AddressBookServiceTest {

    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount(){
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(DB_IO);
        Assert.assertEquals(2,addressBookData.size());
    }
}
