package com.example.demo.account;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService  {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Account saveAccount(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		return this.accountRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		/** 유저를 찾지 못한다면 사용자를 찾지 못했다는 UsernameNotFoundException을 던지는 것이 좋다. */
		Account account = accountRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username));
		
		//return new User(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
		return new AccountAdapter(account);
	}

	@SuppressWarnings("unused")
	private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
		// TODO Auto-generated method stub
		return roles.stream().map(role-> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet());
	}

}
