package com.example.cardatabase;

import com.example.cardatabase.domain.*;
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
	// CommandLineRunner를 implements를 하면 run() 메서드를 반드시 구현해야 하는데 이거 예외를 던지려고 implements 하는게 아니라,
	// 애플리케이션이 시작되면, run(String... args) 메서드가 자동으로 호출
	// 보통 초기 데이터 세팅, 테스트용 출력, DB 준비 같은 작업에 사용됩니다.

	// 여기에 생성자 주입 부분 적겠습니다.(그리고 .md 파일로 옮기는 것도 함께 하겠습니다.)
	private final CarRepository repository;  // DB 접근 기능을 쓸 수 있게 됨
	private final OwnerRepository ownerRepository;
	private final AppUserRepository userRepository;


    public CardatabaseApplication(CarRepository repository, OwnerRepository ownerRepository, AppUserRepository userRepository) {
        this.repository = repository;
		this.ownerRepository = ownerRepository;
		this.userRepository = userRepository;
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
		repository.save(new Car("Kia", "Seltos", "Chacol", "370SU5690A1B2C3D4", 2020, 30000000, owner1));
		repository.save(new Car("Hyundai", "Sonata", "White", "1234567890ABCDEFG", 2025, 25000000, owner2));
//		repository.save(new Car("Honda", "CR-V", "Black-White", "987654321ZYXWVUTS", 2024, 45000000, owner2));
//		repository.save(new Car("Toyota", "RAV4", "White", "TGRV42024ABC12345", 2024, 35500000, owner1));
//		repository.save(new Car("Kia", "Sportage", "Grey", "KISP0023XYZ987654", 2023, 32000000, owner1));
//		repository.save(new Car("Hyundai", "Tucson", "Black", "HNTUC2022DEF67890", 2022, 29000000, owner2));
//		repository.save(new Car("Tesla", "Model Y", "White", "TSLYY25GHJ4567890", 2025, 65000000, owner2));
//		repository.save(new Car("Honda", "Civic", "Red", "HNCIV24KLM1234567", 2024, 28000000, owner1));
//		repository.save(new Car("Ford", "Mustang", "Blue", "FRMUST25NPR890123", 2025, 60000000, owner1));
//		repository.save(new Car("Chevrolet", "Malibu", "Silver", "CHMAL21STU3456789", 2021, 23000000, owner2));
//		repository.save(new Car("Toyota", "Camry", "Black", "TOCAM23VWX7890123", 2023, 31000000, owner1));
//		repository.save(new Car("Kia", "K5", "White", "KIAK524YZA2345678", 2024, 28500000, owner2));
//		repository.save(new Car("Hyundai", "Santa Fe", "Grey", "HNSNF25BCD6789012", 2025, 42000000, owner1));
//		repository.save(new Car("Honda", "Accord", "Black", "HNACC23EFG1234567", 2023, 33000000, owner2));
//		repository.save(new Car("Tesla", "Model 3", "Red", "TSLM324HIJ5678901", 2024, 58000000, owner1));
//		repository.save(new Car("Kia", "Carnival", "White", "KIACNV23KLM901234", 2023, 40000000, owner2));
//		repository.save(new Car("Hyundai", "Palisade", "Black", "HNPAL24NPR3456789", 2024, 48000000, owner1));
//		repository.save(new Car("Toyota", "Corolla", "Silver", "TOCOR22STU7890123", 2022, 26000000, owner2));
//		repository.save(new Car("Honda", "Pilot", "White", "HNPIL25VWX1234567", 2025, 52000000, owner1));
//		repository.save(new Car("Kia", "Mohave", "Black", "KIMHV21YZA5678901", 2021, 46000000, owner2));
//		repository.save(new Car("Chevrolet", "Trailblazer", "Blue", "CHTRB23BCD9012345", 2023, 29500000, owner1));
//		repository.save(new Car("Ford", "Explorer", "White", "FREXP24EFG3456789", 2024, 50000000, owner2));
//		repository.save(new Car("Toyota", "Sienna", "Silver", "TOSIE25HIJ7890123", 2025, 55000000, owner1));
//		repository.save(new Car("Hyundai", "Kona", "Red", "HNKON22KLM1234567", 2022, 24000000, owner2));
//		repository.save(new Car("Kia", "Ray", "White", "KIARAY23NPR5678901", 2023, 18000000, owner1));
//		repository.save(new Car("Honda", "HR-V", "Grey", "HNHRV25STU9012345", 2025, 29000000, owner2));
//		repository.save(new Car("Tesla", "Model S", "Black", "TSLMS21VWX3456789", 2021, 95000000, owner1));
//		repository.save(new Car("Kia", "Stinger", "Blue", "KISTG22YZA7890123", 2022, 45000000, owner2));
//		repository.save(new Car("Hyundai", "Grandeur", "Black", "HNGRN25BCD1234567", 2025, 38000000, owner1));
//		repository.save(new Car("Toyota", "Highlander", "White", "TOHGH24EFG5678901", 2024, 49000000, owner2));
//		repository.save(new Car("Kia", "Morning", "Silver", "KIMRN23HIJ9012345", 2023, 15000000, owner1));
//		repository.save(new Car("Honda", "Passport", "Red", "HNPAS25KLM3456789", 2025, 48000000, owner2));
//		repository.save(new Car("Hyundai", "Venue", "Grey", "HNVNU22NPR7890123", 2022, 21000000, owner1));
//		repository.save(new Car("Chevrolet", "Colorado", "Black", "CHCOL24STU1234567", 2024, 40000000, owner2));
//		repository.save(new Car("Ford", "Bronco", "Green", "FRBNC25VWX5678901", 2025, 55000000, owner1));
//		repository.save(new Car("Toyota", "Prius", "White", "TOPRI23YZA9012345", 2023, 34000000, owner2));
//		repository.save(new Car("Kia", "Niro", "Silver", "KINIR24BCD3456789", 2024, 31000000, owner1));
//		repository.save(new Car("Hyundai", "Ioniq 5", "Blue", "HNINQ25EFG7890123", 2025, 55000000, owner2));
//		repository.save(new Car("Honda", "Odyssey", "Black", "HNODY23HIJ1234567", 2023, 44000000, owner1));
//		repository.save(new Car("Tesla", "Model X", "White", "TSLMX24KLM5678901", 2024, 110000000, owner2));
//		repository.save(new Car("Kia", "Telluride", "Grey", "KITEL22NPR9012345", 2022, 47000000, owner1));
//		repository.save(new Car("Hyundai", "Elantra", "Silver", "HNELA25STU3456789", 2025, 26000000, owner2));
//		repository.save(new Car("Toyota", "Tacoma", "Black", "TOTAC24VWX7890123", 2024, 41000000, owner1));
//		repository.save(new Car("Honda", "Ridgeline", "Red", "HNRDG23YZA1234567", 2023, 45000000, owner2));
//		repository.save(new Car("Kia", "Soul", "Blue", "KISOUL21BCD5678901", 2021, 20000000, ow                         ner1));
//		repository.save(new Car("Hyundai", "Veloster", "White", "HNVLS22EFG9012345", 2022, 22000000, owner2));
//		repository.save(new Car("Chevrolet", "Equinox", "Grey", "CHEQN24HIJ3456789", 2024, 33000000, owner1));
//		repository.save(new Car("Ford", "Ranger", "Black", "FRRGR23KLM7890123", 2023, 36000000, owner2));
//		repository.save(new Car("Toyota", "4Runner", "Silver", "TO4RN25NPR1234567", 2025, 51000000, owner1));
//		repository.save(new Car("Kia", "Ceed", "White", "KICED24STU5678901", 2024, 27500000, owner2));

		// -> 이상의 코드는 testdb내의 CAR테이블 내에 3개의 row를 추가하여 저장한다는 의미입니다.
		// Java 기준으로는 객체 세 개를 만들어서 저장했다고도 볼 수 있겠네요.

		// 모든 자동차를 가져와서 Console에 로깅해보도록 하겠습니다.
		for(Car car : repository.findAll()) {
			logger.info("brand : {}, model : {}", car.getBrand(), car.getModel());   // 일종의 f-string
		}

		//AppUser 더미데이터 추가
		// 저 위에 보시면 Owner에 경우에는 owner1 / owner2 만들어가지고 ownerRepository에 저장했었습니다.
		// username : user / password : user
		userRepository.save(new AppUser("user", "$2y$04$7XSGgfbTIwenxylkuDCGeez7ETZv3HGCZnYXa9rt01dD3FrXoVPni","USER"));
		// username : admin / password : admin
		userRepository.save(new AppUser("admin", "$2y$04$0yJEG6qW/Q/.03WfMjmSDOiKJR8zZsWRzamL6n9Jg4plwZ.o6gV8K", "ADMIN"));

//		System.out.println(repository.findByBrand("Kia"));
//		System.out.println(repository.findByColor("White"));
//		System.out.println(repository.findByModelYear(2024));
//		System.out.println(repository.findByBrandOrColor("Honda","Black"));
//		System.out.println(repository.findByBrandAndModel("Kia","K5"));

	}
}