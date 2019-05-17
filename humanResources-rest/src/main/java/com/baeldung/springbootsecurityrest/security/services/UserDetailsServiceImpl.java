package com.baeldung.springbootsecurityrest.security.services;


import org.humanResources.security.repository.AccountRepository;
import org.humanResources.security.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account user = userRepository.findByName(username).orElseThrow(
				() -> new UsernameNotFoundException("User2 Not Found with -> username or email : " + username));

		return UserPrinciple.build(user);
	}
}