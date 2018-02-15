package com.quar17esma.service.impl;

import java.util.List;

import com.quar17esma.dao.UserProfileRepository;
import com.quar17esma.model.UserProfile;
import com.quar17esma.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	UserProfileRepository repository;
	
	public UserProfile findById(int id) {
		return repository.findOne((long) id);
	}

	public UserProfile findByType(String type){
		return repository.findByType(type);
	}

	public List<UserProfile> findAll() {
		return repository.findAll();
	}
}
