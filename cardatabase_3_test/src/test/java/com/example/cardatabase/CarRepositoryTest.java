package com.example.cardatabase;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;   // car에 보면 owner까지 생성자로 돼있어서
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("차량 저장 메서드 테스트")
    void saveCar() {
        // given - 제반 준비 사항
        // Car Entity를 확인해봤을 때 field로 Owner를 요구하기 때문에
        // 얘부터 만들고 -> ownerRepository에 저장
        Owner owner = new Owner("Gemini", "GPT");  // 일단 Car에 집어넣어야 하니깐 만듦
        ownerRepository.save(owner);
        // 그리고 Car 객체를 만들겁니다.
        Car car = new Car("Ford", "Mustang", "Red", "ABCEDF",2021, 567890,owner);


        // when - 테스트 실행
        // 저장이 됐는가를 확인하기 위한 부분
        carRepository.save(car);

        // then - 그 결과가 어떠할지
        assertThat(carRepository.findById(car.getId())).isPresent();  // 이건 그냥 결과값이 하나일테니까.

        assertThat(carRepository.findById(car.getId()).get().getBrand()).isEqualTo("Ford");
        // car.getId()).get() id를 get하고 뒤에 .get하면 그 아이디에 해당하는 객체를 통째로 가져옴
    }


    @Test
    @DisplayName("차량 삭제 메서드 테스트")
    void deleteCar() {
        // 제반 상황
        Owner owner = new Owner("Gemini", "GPT");  // 일단 Car에 집어넣어야 하니깐 만들었음
        ownerRepository.save(owner);
        // 그리고 Car 객체를 만들겁니다.
        Car car = new Car("Ford", "Mustang", "Red", "ABCEDF",2021, 567890,owner);


        // when - 테스트 실행
        carRepository.deleteById(car.getId());
//        carRepository.deleteAll();

        // then -그 결과가 어떠할지
         assertThat(carRepository.count()).isEqualTo(3);
//        assertThat(carRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("차량 브랜드 조회 메서드 테스트")
    void findByBrandShouldReturnCar() {
        // 제반 상황
        Owner owner = new Owner("Gemini", "GPT");
        ownerRepository.save(owner);

        carRepository.save(new Car("Toyota", "Treno", "Black-White", "123456", 2021, 30000000, owner));
        carRepository.save(new Car("Toyota", "GR86", "Blue", "37078635", 2023, 30000000, owner));
        carRepository.save(new Car("Hyundai", "casper", "Green", "98765447", 2025, 45000000, owner));

        // when - carRepository.findByBrand("브랜드명")  -> 얘 결과 자료형은 list
        List<Car> toyotas = carRepository.findByBrand("Toyota");

        // then에서의 검증은 list 내부의 element의 자료형이 Car 객체일테니까,
        // 그 객체의 getBrand()의 결과값이 우리가 findByBrand() 의 argument로 쓴 값과 동일한 지를 체크할 수 있겠네요.

        assertThat(toyotas.get(0).getBrand()).isEqualTo("Toyota");
        // 혹은, 현재 저희가 Toyota 차량을 두 대 만들었으니까 size()의 결과값이 2겠죠.
        assertThat(toyotas.size()).isEqualTo(2);
    }




}
