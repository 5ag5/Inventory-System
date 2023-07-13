package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.DTOs.CategoryDTO;
import inventarios.com.Sistema.Inventarios.Models.Category;
import inventarios.com.Sistema.Inventarios.Repositories.CategoryRepository;
import inventarios.com.Sistema.Inventarios.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplements implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<CategoryDTO> getAllCategoryDTO() {
        return categoryRepository.findAll().stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
