package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;
import inventarios.com.Sistema.Inventarios.Models.Product;
import inventarios.com.Sistema.Inventarios.Repositories.ProductRepository;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @Override
    public Product finProducById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}

