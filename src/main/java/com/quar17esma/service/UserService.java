package com.quar17esma.service;

import com.quar17esma.model.User;

import java.util.List;


public interface UserService {
	User findById(int id);
	
	User findByEmail(String email);
	
	void saveUser(User user);
	
	void deleteUserByEmail(String email);

	List<User> findAllUsers(); 
	
	boolean isEmailBusy(String email);

}