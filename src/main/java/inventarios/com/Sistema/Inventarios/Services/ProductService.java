package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;
import inventarios.com.Sistema.Inventarios.Models.Product;

import java.util.List;

public interface ProductService {
    void inputProduct(Product product);
    ProductDTO findProduct(Long id);
    void deleteProduct(Long id);
    void modifyProduct(Product product);
    List<Product> findAllProducts();

    List<ProductDTO> getProducts();

    Product finProducById(Long id);
}
