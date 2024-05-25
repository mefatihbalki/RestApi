package org.ferhat.restapiproject.business.abstracts;

import org.ferhat.restapiproject.entity.Supplier;
import org.springframework.data.domain.Page;

public interface ISupplierService {
    Supplier save(Supplier supplier);
    Supplier update(Supplier supplier);
    Supplier get(int id);
    Page<Supplier> cursor(int page, int size);
    boolean delete(int id);
}
