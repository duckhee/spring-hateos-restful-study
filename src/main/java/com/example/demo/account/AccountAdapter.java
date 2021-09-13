package com.example.demo.account;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class AccountAdapter extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8442251731947592359L;
	
	private Account account;
	
	public AccountAdapter(Account account) {
		// TODO Auto-generated constructor stub
		super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
		this.account = account;
	}
	
	public Account getAccount() {
		return this.account;
	}

	private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> role){
		return role.stream().map(r->new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toList());
	}
}
