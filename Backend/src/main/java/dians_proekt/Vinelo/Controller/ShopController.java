package dians_project.vinelo.controller;

import dians_project.vinelo.model.WineShop;
import dians_project.vinelo.model.Review;
import dians_project.vinelo.model.Shop;
import dians_project.vinelo.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/create")
    public WineShop createShop(@RequestBody Shop shop, HttpServletRequest request) throws InterruptedException, ExecutionException {
        if (LoggedInUserCheck.getInstance().check(request.getHeader("loginToken"))) {
            return shopService.createShop(shop);
        } else {
            return null;
        }
    }

    @GetMapping("/all")
    public List<WineShop> getAllShops() throws Exception {
        return shopService.getAllShops();
    }

    @GetMapping("/get/{id}")
    public Shop getShopById(@PathVariable String id) throws InterruptedException, ExecutionException {
        return shopService.getShopById(id);
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return shopService.getCategories();
    }

    @GetMapping("/cities")
    public List<String> getCities() {
        return shopService.getCities();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteShop(@PathVariable String id, HttpServletRequest request) throws ExecutionException, InterruptedException {
        if (LoggedInUserCheck.getInstance().check(request.getHeader("loginToken"))) {
            return shopService.deleteShop(id);
        } else {
            return false;
        }
    }

    @PutMapping("/update/{id}")
    public WineShop updateShop(@PathVariable String id, @RequestBody Map<String, Object> shop, HttpServletRequest request) throws ExecutionException, InterruptedException {
        if (LoggedInUserCheck.getInstance().check(request.getHeader("loginToken"))) {
            shop.remove("id");
            shop.remove("reviewList");
            return shopService.updateShop(shop, id);
        } else {
            return null;
        }
    }

    @GetMapping("/{id}/reviews")
    public List<Review> getReviewList(@PathVariable String id) throws ExecutionException, InterruptedException {
        return shopService.reviewList(id);
    }

    @PostMapping(value = "/{id}/add-review")
    public HttpStatus addShopReview(@PathVariable String id, @RequestBody Review review, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return shopService.addReviews(id, review);
    }

    @DeleteMapping("/{id}/delete-review")
    public List<Review> deleteShopReview(@PathVariable String id, @RequestParam int reviewId, HttpServletRequest request) throws ExecutionException, InterruptedException {
        if (LoggedInUserCheck.getInstance().check(request.getHeader("loginToken"))) {
            return shopService.deleteReview(id, reviewId);
        } else {
            return null;
        }
    }

}