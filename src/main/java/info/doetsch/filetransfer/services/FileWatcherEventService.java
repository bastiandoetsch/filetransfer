package info.doetsch.filetransfer.services;

import info.doetsch.filetransfer.entities.SupplierConfiguration;
import info.doetsch.filetransfer.repositories.SupplierConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

@Service
public class FileWatcherEventService {

    @Autowired
    public SupplierConfigurationRepository repository;

    public void processEvent(String kind, Path path) {
        if ("ENTRY_CREATE".equals(kind)) {
            getConfiguration(path);
        }
    }

    /**
     * This assumes the generated file name follows the following convention
     * <code>supplierNo_type_timestamp.dat</code>
     * @param path
     * @return
     */
    public Optional<SupplierConfiguration> getConfiguration(Path path) {
        String[] split = path.toAbsolutePath().toString().split("_");
        return repository.findById(split[0]);
    }
}
