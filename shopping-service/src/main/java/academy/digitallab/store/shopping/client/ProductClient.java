package academy.digitallab.store.shopping.client;

import academy.digitallab.store.shopping.model.Customer;
import academy.digitallab.store.shopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
@FeignClient(name = "product-service", path = "/products")

public interface ProductClient {

    @GetMapping(value = "/{id}")
    @CircuitBreaker(name = "productIns", fallbackMethod = "getFallbackProduct")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id);

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable  Long id ,@RequestParam(name = "quantity", required = true) Double quantity);

    default ResponseEntity<Product> getFallbackProduct(RuntimeException e) {
        return ResponseEntity.ok(null);
    }
}
