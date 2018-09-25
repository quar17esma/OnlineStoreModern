package com.quar17esma.service.impl;

import com.quar17esma.dao.UserRepository;
import com.quar17esma.model.User;
import com.quar17esma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userService")
@Transactional
@ComponentScan({"com.quar17esma.dao"})
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public void deleteUserByEmail(String email) {
		userRepository.deleteByEmail(email);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public boolean isEmailBusy(String email) {
		return findByEmail(email) != null;
	}
}
