package org.ferhat.restapiproject.business.abstracts;

import org.ferhat.restapiproject.entity.Category;
import org.springframework.data.domain.Page;

public interface ICategoryService {

    Category save(Category category);
    Category update(Category category);
    Category get(int id);
    Page<Category> cursor(int page, int pageSize);
    boolean delete(int id);
}
