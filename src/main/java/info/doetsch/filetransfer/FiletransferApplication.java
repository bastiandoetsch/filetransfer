package info.doetsch.filetransfer;

import info.doetsch.filetransfer.services.WatchDir;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FiletransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiletransferApplication.class, args);

    }
}
