package com.roi.bl.service.impl;

import com.roi.bl.dao.UserDAO;
import com.roi.bl.model.User;
import com.roi.bl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDAO userDao;

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userDao.getUserbyEmail(userId);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

//	public List<User> findAll() {
//		List<User> list = new ArrayList<>();
//		userDao.findAll().iterator().forEachRemaining(list::add);
//		return list;
//	}
//
//	@Override
//	public void delete(long id) {
//		userDao.delete(id);
//	}
//
//	@Override
//    public User save(User user) {
//        return userDao.save(user);
//    }
}
