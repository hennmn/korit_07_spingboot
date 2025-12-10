//package com.example.cardatabase.web;
//
//
//import com.example.cardatabase.domain.Car;
//import com.example.cardatabase.domain.CarRepository;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//// 해당 클래스가 RESTful API 웹 서비스 상의 Controller가 될 것을 지정함.
//@RestController
//public class CarController {
//    private final CarRepository carRepository;
//
//    public CarController(CarRepository carRepository) {
//        this.carRepository = carRepository;
//    }
//
//    // 클라이언트가 특정 URL로 요청을 보냈을 때 이 메서드를 실행하도록 연결(매핑) 해주는 어노테이션
//    @GetMapping("/cars")
//    public Iterable<Car> getCars() {
//        // 복잡하게 할 거 없이 자동차들이 저장된 테이블에서 전체 명단을 가지고 올겁니다.
//        return carRepository.findAll();
//    }
//    // @GetMapping
//    // HTTP 요청 방식 중 GET 요청을 처리하겠다는 의미입니다.
//    // 예: 브라우저 주소창에 URL을 입력하거나, 프론트엔드에서 데이터를 조회하려고 GET 방식으로 요청을 보낼 때 사용
//
//    /*
//       ("/cars")  이 메서드가 실행될 URL 경로를 지정하는 부분
//       http://localhost:8080/cars 로 요청을 보내면, Spring이 이 메서드를 실행
//    */
//
//}
// Spring Data REST 어쩌구로 이 기능을 다 만들어놨기 때문에 굳이 Controller 필요 x
