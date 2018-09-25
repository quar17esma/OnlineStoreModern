package com.quar17esma.service;

import com.quar17esma.model.User;

public interface UserService extends CRUDService<User> {
	User findByEmail(String email);

	void deleteUserByEmail(String email);

	boolean isEmailBusy(String email);
}