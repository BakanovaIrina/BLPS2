package com.blps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "advertisement")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private long id;

    @Column(name = "owner_id")
    @Getter
    @Setter
    private long owner_id;

    @Column(name = "address")
    @Getter
    @Setter
    private String address;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "price")
    @Getter
    @Setter
    private Integer price;
}
