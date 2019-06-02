package org.humanResources.security.services;


import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.repository.AccountRepository;
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

/*		AccountImpl user = userRepository.findByName(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
*/
		AccountImpl user = userRepository.loadByName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));


		return UserPrinciple.build(user);
	}
}