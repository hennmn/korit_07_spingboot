package com.example.shoppinglist.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(force = true)
@Getter
@Setter
@RequiredArgsConstructor
public class Shopping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private final String product;

    @Column
    private final int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private final AppUser user;


}
