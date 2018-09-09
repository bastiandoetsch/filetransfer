package info.doetsch.filetransfer.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class WatchDirTest {

    @Mock
    FileWatcherEventService eventServiceMock;

    @Test
    public void testProcessEvents() throws Exception {
        Path dir = Paths.get("./FileWatcherTestDir");
        Files.deleteIfExists(dir);
        try {
            Files.createDirectory(dir);
        } catch (FileAlreadyExistsException e) {
            // do nothing
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);
        WatchDir watchDir = new WatchDir(dir, true);
        watchDir.setFileWatcherEventService(eventServiceMock);
        final int qty = 100;
        Future f = executor.submit(watchDir);
        Callable c = new Callable() {
            @Override
            public Object call() throws Exception {
                for (int i = 0; i < qty; i++) {
                    Path p = Files.createFile(Paths.get(dir.toString(), "" + System.currentTimeMillis()));
                    Files.delete(p);
                    Thread.sleep(1);
                }
                return "did " + qty * 2 + " file operations";
            }
        };
        executor.submit(c).get();
        executor.awaitTermination(10, TimeUnit.MILLISECONDS);
        Files.delete(dir);
        verify(eventServiceMock, times(qty * 2)).processEvent(Mockito.anyString(), Mockito.any(Path.class));
    }
}
