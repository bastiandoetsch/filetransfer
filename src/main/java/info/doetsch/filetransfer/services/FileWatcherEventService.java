package info.doetsch.filetransfer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

@EnableMongoRepositories
@Service
public class FileWatcherEventService {

    @Autowired
    public SupplierConfigurationRepository repository;

    public void processEvent(String kind, Path path) {
        if ("ENTRY_CREATE".equals(kind)) {
            transferFileToDestination(getConfiguration(path), path);
        }
    }

    private void transferFileToDestination(Optional<SupplierConfiguration> configuration, Path path) {
        // now convert the filename to the destination filename
        // use sftp (JSch) to transfer file
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
