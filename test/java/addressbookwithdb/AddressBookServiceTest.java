package addressbookwithdb;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static addressbookwithdb.AddressBookService.IOService.DB_IO;
public class AddressBookServiceTest {

    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount(){
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(DB_IO);
        Assert.assertEquals(2,addressBookData.size());
    }

    @Test
    public void givenNewAddressForContact_WhenUpdated_ShouldSyncWithDB(){
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookDataList = addressBookService.readAddressBookData(DB_IO);
        addressBookService.updatePersonByAddress("Gautam","Sector 4");
        boolean result = addressBookService.checkAddressBookDataIsSyncWithDB("Gautam");
        Assert.assertTrue(result);
    }

    @Test
    public void givenDateRangeForContact_WhenRetrieved_ShouldMatchContactCount(){
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        LocalDate addedDate = LocalDate.of(2020, 01,01);
        LocalDate endDate = LocalDate.now();
        List<AddressBookData> addressBookData =
                addressBookService.readContactDateRange(DB_IO, addedDate, endDate);
        Assert.assertEquals(2, addressBookData.size());
    }
}
