package dians_project.vinelo.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import dians_project.vinelo.model.DummyShop;
import dians_project.vinelo.model.User;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Service;
package dians_project.vinelo.service;

import com.google.api.Http;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import dians_project.vinelo.model.WineShop;
import dians_project.vinelo.model.Review;
import dians_project.vinelo.model.Shop;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ShopService {
    Firestore dbFirestore = FirestoreClient.getFirestore();
    private final RestTemplate restTemplate;

    public ShopService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WineShop createShop(Shop shop) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> addedDocRef = dbFirestore.collection("shops").add(shop);
        return getDummyShopById(addedDocRef.get().getId());
    }

    public Shop getShopById(String id) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = dbFirestore.collection("shops").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Shop shop;
        if (document.exists()) {
            shop = document.toObject(Shop.class);
            if (shop != null) {
                shop.setId(id);
                return shop;
            }
        }
        return null;
    }

    public WineShop getWineShopById(String id) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = dbFirestore.collection("shops").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        WineShop wineShop;
        if (document.exists()) {
            wineShop = document.toObject(WineShop.class);
            if (wineShop != null) {
                wineShop.setId(id);
                return wineShop;
            }
        }
        return null;
    }

    public List<WineShop> getAllShops() throws Exception {
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("shops").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents
                .stream()
                 .map(i -> {
                    WineShop shop = i.toObject(WineShop.class);
                    shop.setId(i.getId());
                    return shop;
                }).collect(Collectors.toList());
    }

    public WineShop updateShop(Map<String, Object> shop, String id) {
        dbFirestore.collection("shops").document(id).update(shop);

        return new WineShop
                (
                        id,
                    shop.get("address")==null?null:shop.get("address").toString(),
                    shop.get("name")==null?null:shop.get("name").toString(),
                    shop.get("category")==null?null:shop.get("category").toString(),
                    shop.get("avgGrade")==null?0.0:Double.parseDouble(shop.get("avgGrade").toString()),
                    shop.get("lat")==null?0.0:Double.parseDouble(shop.get("lat").toString()),
                    shop.get("lon")==null?0.0:Double.parseDouble(shop.get("lon").toString())
                );
    }

    public boolean deleteShop(String id) throws ExecutionException, InterruptedException {
        dbFirestore.collection("shops").document(id).delete();
        return true;
    }

    // in the refactor phase of the project this whole backend architecture is getting a makeover :P
    public List<String> getCategories() {
        String[] cats = restTemplate.getForObject("https://vinelo-default-rtdb.europe-west1.firebasedatabase.app/categories.json", String[].class);
        if (cats == null) {
            throw new NullPointerException("No categories found");
        }
        return Arrays.asList(cats);
    }

    public List<String> getCities() {
        String[] cats = restTemplate.getForObject("https://vinelo-default-rtdb.europe-west1.firebasedatabase.app/cities.json", String[].class);
        if (cats == null) {
            throw new NullPointerException("No categories found");
        }
        return Arrays.asList(cats);
    }

    public List<Review> reviewList(String id) throws ExecutionException, InterruptedException {
        Shop shop = getShopById(id);
        return shop.getReviewList();
    }

    public HttpStatus addReviews(String id, Review review) throws ExecutionException, InterruptedException {
        Shop shop = getShopById(id);
        List<Review> reviews = shop.getReviewList();
        reviews.add(review);
        updateReviewList(id, reviews);
        updateAvgGrade(shop);
        return HttpStatus.OK;
    }

    public List<Review> deleteReview(String id, int reviewId) throws ExecutionException, InterruptedException {
        Shop shop = getShopById(id);
        List<Review> reviews = shop.getReviewList();
        reviews.remove(reviewId);
        updateReviewList(id, reviews);
        updateAvgGrade(shop);
        return reviews;
    }

    public void updateAvgGrade(Shop shop) {
        double avgGrade = 0.0;

        if (shop.getReviewList().size() > 0) {
            int grades = shop.getReviewList().stream().mapToInt(Review::getGrade).sum();
            avgGrade = (double) grades / shop.getReviewList().size();
        }
        dbFirestore.collection("shops").document(shop.getId()).update("avgGrade", avgGrade);
    }

    private void updateReviewList(String id, List<Review> reviews) {
        dbFirestore.collection("shops").document(id).update("reviewList", reviews);
    }

}