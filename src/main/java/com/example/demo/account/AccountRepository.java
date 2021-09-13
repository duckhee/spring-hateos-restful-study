package com.example.demo.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
	/** null return을 막기 위해서 */
	Optional<Account> findByEmail(String username);

	

}
