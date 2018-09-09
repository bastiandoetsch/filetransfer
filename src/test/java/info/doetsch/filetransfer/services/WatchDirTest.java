package info.doetsch.filetransfer.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
        WatchDir watchDir = new WatchDir(dir,true);
        watchDir.setFileWatcherEventService(eventServiceMock);
        executor.submit(watchDir);
        Path p = Files.createFile(Paths.get(dir.toString(),""+System.currentTimeMillis()));
        Files.delete(p);
        Files.delete(dir);
        executor.shutdown();
    }
}
