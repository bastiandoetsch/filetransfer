package info.doetsch.filetransfer.repositories;

import info.doetsch.filetransfer.entities.SupplierConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierConfigurationRepository extends MongoRepository<SupplierConfiguration, String> {
}
