package com.example.cardatabase4.web;

import com.example.cardatabase4.domain.Car;
import com.example.cardatabase4.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // 1. 모든 자동차 정보 조회(GET /api/cars)
    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.getCars();
    }

    // 2. 차량 한 대 추가(POST /api/cars)
    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = carService.addCar(car);  // 제네릭 대신에 savedCar를 넣는 거임

        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);   // 상수 필드? 이게 문데
    }

    // 3. 차량 한 대 조회
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {   // 나오는 자료형이 숫자라면 @PathVariable로 Long 자료형으로 바꿔 주겠다.
        return carService.getCarById(id)
                .map(car -> ResponseEntity.ok().body(car))  // 차량 정보가 있으면 200 OK를 반환할 것   // Optional에 딸려있는 .map() 메서드 !! 여기만 있는 거 아니고 다른 것에서도 있음
                .orElse(ResponseEntity.notFound().build()); // 없으면 404 Not Found를 return 시킬 것
    }




}
