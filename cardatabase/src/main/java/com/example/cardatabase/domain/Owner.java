package com.example.cardatabase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)   // AUTO_INCREMENT 랑 같은 뜻
    private Long ownerId;

    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;

    // 소유자는 다수의 차들을 가질 수 있기 때문에 Collections를 사용
    @JsonIgnoreProperties  // 이 필드는 직렬화가 되지 않는다(JSON화 되지 않는다)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Car> cars;


}
