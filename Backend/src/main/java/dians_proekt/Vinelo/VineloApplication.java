package dians_project.vinelo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@ServletComponentScan
public class MapedonijaApplication {

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/serviceAccountKey/serviceAccountKey.json");
        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://mapedonija-default-rtdb.europe-west1.firebasedatabase.app/")
                .build();

        FirebaseApp.initializeApp(options);

        SpringApplication.run(MapedonijaApplication.class, args);
    }

}