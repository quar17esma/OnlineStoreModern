package com.quar17esma.service.impl;

import com.quar17esma.dao.UserRepository;
import com.quar17esma.model.User;
import com.quar17esma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
@ComponentScan({"com.quar17esma.dao"})
public class UserServiceImpl extends AbstractCRUDService<User> implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public void deleteUserByEmail(String email) {
		userRepository.deleteByEmail(email);
	}

	public boolean isEmailBusy(String email) {
		return findByEmail(email) != null;
	}
}
