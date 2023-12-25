package dians_project.mapedonija.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import dians_project.mapedonija.model.DummyShop;
import dians_project.mapedonija.model.User;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class AuthService {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    public User login(String username, String password) throws ExecutionException, InterruptedException, InvalidCredentialsException {

        if (username == null || username.isEmpty() || password == null || password.isEmpty() ) {
            throw new InvalidCredentialsException();
        }

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("users").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<User> users = documents.stream().map(i -> i.toObject(User.class)).collect(Collectors.toList());
        return users
                .stream()
                .filter(i -> i.getUsername().equals(username) && i.getPassword().equals(password))
                .findFirst()
                .orElseThrow(InvalidCredentialsException::new);
    }


    private boolean isLoggedIn() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;

        HttpServletRequest request = attributes.getRequest();
        HttpSession httpSession = request.getSession(true);

        User userObject = (User) httpSession.getAttribute("user");

        return userObject == null;
    }

}