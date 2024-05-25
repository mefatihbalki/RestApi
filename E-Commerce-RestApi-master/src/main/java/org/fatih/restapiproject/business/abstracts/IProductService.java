package org.ferhat.restapiproject.business.abstracts;

import org.ferhat.restapiproject.entity.Category;
import org.ferhat.restapiproject.entity.Product;
import org.springframework.data.domain.Page;

public interface IProductService {

    Product save(Product product);

    Product update(Product product);

    Product get(int id);

    Page<Product> cursor(int page, int pageSize);

    boolean delete(int id);
}
