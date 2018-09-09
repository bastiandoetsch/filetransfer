package info.doetsch.filetransfer.services;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierConfigurationRepository extends MongoRepository<SupplierConfiguration, String> {
}
