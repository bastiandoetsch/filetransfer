package info.doetsch.filetransfer.services;

import org.springframework.stereotype.Service;

import java.nio.file.WatchEvent;

@Service
public class FileWatcherEventService {
    public void processEvent(WatchEvent<?> event) {
        throw new RuntimeException ("Processing event not implemented yet." + event.kind() + "," + event.context());
    }
}
