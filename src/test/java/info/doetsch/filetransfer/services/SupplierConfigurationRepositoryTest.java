package info.doetsch.filetransfer.services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierConfigurationRepositoryTest {
    @Autowired
    SupplierConfigurationRepository supplierConfigurationRepository;

    @Test
    public void testDeleteSave() {
        SupplierConfiguration sc = new SupplierConfiguration();
        sc.setDestinationDirectory("testsupplier/order");
        sc.setDestinationHost("aft.xyz.com");
        sc.setFileNameTemplate("SSSSSS_YYYYMMDDHHMISS.xml");
        sc.setId("791002");
        sc.setSupplierName("Bastianodo");
        supplierConfigurationRepository.delete(sc);
        supplierConfigurationRepository.save(sc);
    }
}
