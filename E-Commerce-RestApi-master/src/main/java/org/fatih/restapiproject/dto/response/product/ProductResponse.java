package org.ferhat.restapiproject.dto.response.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ferhat.restapiproject.entity.Category;
import org.ferhat.restapiproject.entity.Supplier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int supplierId;
    private int categoryId;
}
