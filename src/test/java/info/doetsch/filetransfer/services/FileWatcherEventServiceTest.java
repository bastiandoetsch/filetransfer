package info.doetsch.filetransfer.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FileWatcherEventServiceTest {
    Path dir =Paths.get("testExec");
    Path tempFile = null;

    @Before
    public void setUp() throws Exception {
        try {
            Files.createDirectory(dir);
        } catch (FileAlreadyExistsException e) {
            // no problem
        }
        tempFile = Files.createFile(Paths.get(dir.toString(), "" + System.currentTimeMillis()));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(dir);
    }

    @Test
    public void testCreateEvents() throws IOException {
            FileWatcherEventService fes = new FileWatcherEventService();
            fes.processEvent("ENTRY_CREATE", tempFile);
    }
}
