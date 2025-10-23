package com.example.shoppinglist;

import com.example.shoppinglist.domain.AppUser;
import com.example.shoppinglist.domain.AppUserRepository;
import com.example.shoppinglist.domain.Shopping;
import com.example.shoppinglist.domain.ShoppingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ShoppinglistApplication implements CommandLineRunner {
	private final AppUserRepository userRepository;
	private final ShoppingRepository shoppingRepository;

    public ShoppinglistApplication(AppUserRepository userRepository, ShoppingRepository shoppingRepository) {
        this.userRepository = userRepository;
        this.shoppingRepository = shoppingRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(ShoppinglistApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AppUser user1 = new AppUser("user", "$2b$12$Iu67x80Xz5RCe02ejWfrZO/FR3hYde7oU.PDfgFmdeKSdd7aO3PRu");
		AppUser user2 = new AppUser("admin", "$2b$12$TPy.6.fwzexem0shVWMbeuBudtE6Q1OkSOHrDJ8iQVks.x4PmE1xa");

		userRepository.saveAll(Arrays.asList(user1,user2));

		shoppingRepository.save(new Shopping("안경", 3,user1));
		shoppingRepository.save(new Shopping("지우개", 1,user1));
		shoppingRepository.save(new Shopping("필통", 2, user2));
		shoppingRepository.save(new Shopping("과자", 2, user2));


	}
}
