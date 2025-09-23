package com.example.cardatabase;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(
			CardatabaseApplication.class
	);
	// implements를 하면 run() 메서드를 반드시 구현해야 하는데 이거 예외를 던지려고 implements 하는게 아니라,
	// 앱 시작시 실행할 코드를 넣기 위해 implements하는 거임
	// 보통 초기 데이터 넣기, 간단한 테스트 로그 찍기 등에 사용

	// 여기에 생성자 주입 부분 적겠습니다.(그리고 .md 파일로 옮기는 것도 함께 하겠습니다.)
	private final CarRepository repository;  // DB 접근 기능을 쓸 수 있게 됨
	private final OwnerRepository ownerRepository;


    public CardatabaseApplication(CarRepository repository, OwnerRepository ownerRepository) {
        this.repository = repository;
		this.ownerRepository = ownerRepository;
    }  // 위에 필드와 이 생성자가 있어야 CarRepository의 메서드 사용 가능

    public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
	}

	// CommandLineRunner 인터페이스의 추상메서드인 run()을 여기서 구현하는 거네요.
	@Override
	public void run(String... args) throws Exception {
		// 소유자 객체를 추가
		Owner owner1 = new Owner("일", "김");
		Owner owner2 = new Owner("이","강");
		// 다수의 객체를 한 번에 저장하는 메서드 처음 사용해보겠습니다.
		ownerRepository.saveAll(Arrays.asList(owner1, owner2));

		// 그리고 추가
		// 내부에서 CarRepository의 객체의 repository의 메서드를 호출할겁니다.
		repository.save(new Car("Kia", "Seltos", "Chacol", "370SU5690", 2020, 30000000, owner1));
		repository.save(new Car("Hyundai", "Sonata", "White", "123456", 2025, 25000000, owner2));
		repository.save(new Car("Honda", "CR-V", "Black-White", "987654", 2024, 45000000, owner2));

		// -> 이상의 코드는 testdb내의 CAR테이블 내에 3개의 row를 추가하여 저장한다는 의미입니다.
		// Java 기준으로는 객체 세 개를 만들어서 저장했다고도 볼 수 있겠네요.

		// 모든 자동차를 가져와서 Console에 로깅해보도록 하겠습니다.
		for(Car car : repository.findAll()) {
			logger.info("brand : {}, model : {}", car.getBrand(), car.getModel());   // 일종의 f-string
		}

//		System.out.println(repository.findByBrand("Kia"));
//		System.out.println(repository.findByColor("White"));
//		System.out.println(repository.findByModelYear(2024));
//		System.out.println(repository.findByBrandOrColor("Honda","Black"));
//		System.out.println(repository.findByBrandAndModel("Kia","K5"));

	}
}
