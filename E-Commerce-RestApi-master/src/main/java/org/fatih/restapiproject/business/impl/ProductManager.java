package org.ferhat.restapiproject.business.impl;

import org.ferhat.restapiproject.business.abstracts.IProductService;
import org.ferhat.restapiproject.core.exception.NotFoundException;
import org.ferhat.restapiproject.core.utils.Message;
import org.ferhat.restapiproject.dao.ProductRepo;
import org.ferhat.restapiproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductManager implements IProductService {
    private final ProductRepo productRepo;

    public ProductManager(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product save(Product product) {
        return this.productRepo.save(product);
    }

    @Override
    public Product update(Product product) {
        return this.productRepo.save(product);
    }

    @Override
    public Product get(int id) {
        return this.productRepo.findById(id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Page<Product> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.productRepo.findAll(pageable);
    }

    @Override
    public boolean delete(int id) {
        Product product = this.get(id);
        this.productRepo.delete(product);
        return true;
    }
}
