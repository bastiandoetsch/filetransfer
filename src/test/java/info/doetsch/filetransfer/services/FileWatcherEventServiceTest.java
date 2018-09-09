package info.doetsch.filetransfer.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileWatcherEventServiceTest {
    Path dir =Paths.get("testExec");
    Path tempFile = null;

    @Autowired
    FileWatcherEventService fes;

    @Before
    public void setUp() throws Exception {
        try {
            Files.createDirectory(dir);
        } catch (FileAlreadyExistsException e) {
            // no problem
        }
        tempFile = Files.createFile(Paths.get(dir.toString(), "12345_" + System.currentTimeMillis()));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(dir);
    }

    @Test
    public void testCreateEvents() throws IOException {
            fes.processEvent("ENTRY_CREATE", tempFile);
    }
}
