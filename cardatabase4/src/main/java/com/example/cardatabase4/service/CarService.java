package com.example.cardatabase4.service;

import com.example.cardatabase4.domain.Car;
import com.example.cardatabase4.domain.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service   // 책 보고 서비스 계층 구분 찾아보기
public class CarService {
    private final CarRepository carRepository;   // 이거 final 안 붙이고 생성자 생성 안 하고 사용할 수는 없는 지 질문하기


    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // 1. 모든 자동차 목록을 조회한다고 가정하겠습니다.
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    // 2. 새로운 자동차 저장
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    // 차량 한 대 조회
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);   // 없으면 알아서 Optional로 받아준다.
    }

}
