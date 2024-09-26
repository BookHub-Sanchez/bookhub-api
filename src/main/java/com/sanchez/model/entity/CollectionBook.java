package com.sanchez.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "collection_books")
@IdClass(CollectionBookPK.class)
public class CollectionBook {
    @Id
    private Integer book;

    @Id
    private Integer collection;

    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;
}
