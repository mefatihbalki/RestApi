package org.ferhat.restapiproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int id;

    @Column(name = "supplier_address")
    private String address;

    @Column(name = "supplier_company")
    private String company;

    @Column(name = "supplier_mail")
    private String mail;

    @Column(name = "supplier_contact")
    private String contact;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;
}
