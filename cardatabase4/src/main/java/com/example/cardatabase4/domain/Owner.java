package com.example.cardatabase4.domain;

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
    private String firstName;
    @NonNull
    private String lastName;

    // 소유자는 다수의 차들을 가질 수 있기 때문에 Collections를 사용
    @JsonIgnoreProperties  // 이 필드는 직렬화가 되지 않는다(JSON화 되지 않는다)  // Jackson에게 이 필드는 무시하라고 알려주는 애너테이션
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Car> cars;

    /*
        양방향 매핑이에요.
        Owner → Car : List<Car> cars
        Car → Owner : Owner owner

        양방향 매핑과 직렬화(무한루프 문제)

Jackson이 Owner 객체를 JSON으로 바꾸려 할 때,
Owner 안의 cars 리스트를 직렬화하려고 Car 객체를 꺼냅니다.
그런데 Car 객체 안에는 다시 owner 필드가 있죠.
그 owner를 직렬화하려고 하면 다시 cars가 나오고…
무한 반복(순환 참조)으로 빠지면서 StackOverflowError가 발생합니다.

이런 문제를 막으려고 @JsonIgnoreProperties나 @JsonManagedReference / @JsonBackReference를 사용합니다.

@JsonIgnoreProperties({"owner"}) 처럼 속성 이름을 주면,
Car → Owner → Car 이런 식으로 순환되는 필드 중 하나를 직렬화에서 빼 버립니다.
그래서 JSON 변환 시 한쪽만 직렬화되어 무한 루프가 끊깁니다.


@JsonIgnoreProperties({"owner"}) 이렇게 괄호 해서 안에 필드명을 명시적으로 넣어주는 것이 좋음.
     */


}