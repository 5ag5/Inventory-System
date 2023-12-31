package inventarios.com.Sistema.Inventarios.Services;


import inventarios.com.Sistema.Inventarios.DTOs.CategoryDTO;
import inventarios.com.Sistema.Inventarios.Models.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    List<CategoryDTO> getAllCategoryDTO();

    void saveCategory(Category category);

    List<Category> findAllCategories();
}
