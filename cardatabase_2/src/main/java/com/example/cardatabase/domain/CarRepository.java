package com.example.cardatabase.domain;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

// 이 애너테이션이 있으면 굳이 Controller 필요 x
@RepositoryRestResource
public interface CarRepository extends JpaRepository<Car, Long> { // 부모,자식 둘 다 인터페이스인 경우 extends
    // Car와 관련된 SQL 문을 처리하겠다.
    // 이 인터페이스에 메서드들을 구현하지 않아도 JpaRepository를 extends 받으면
    // 쓸 수 있음
    // .findAll() / .findById() / .deleteById()  ...

    // 브랜드로 자동차 검색하는 쿼리 메서드
    List<Car> findByBrand(@Param("brand") String brand);

    // 색상으로 자동차 검색하는 쿼리 메서드
    List<Car> findByColor(@Param("color") String color);


}
