package inventarios.com.Sistema.Inventarios.Service.Implements;

import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;
import inventarios.com.Sistema.Inventarios.Models.Product;
import inventarios.com.Sistema.Inventarios.Repositories.ProductRepository;
import inventarios.com.Sistema.Inventarios.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplements implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void inputProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public ProductDTO findProduct(Long id) {
        return productRepository.findById(id).map(product -> new ProductDTO(product)).orElse(null);
    }

    @Override
    public void deleteProduct(Long id) {
        Product productTemp = productRepository.findById(id).orElse(null);
        productTemp.setStatusProduct(false);
        productRepository.save(productTemp);
    }

    @Override
    public void modifyProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}

