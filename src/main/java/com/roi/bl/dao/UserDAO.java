package com.roi.bl.dao;


import com.roi.bl.model.User;

import java.util.List;


public interface UserDAO extends BaseDAO<User> {

    User getUserforLogin(User user);

    User getUserbyName(String username);

    User getUserbyEmail(String email);

    int updateReferralCount(String userEmail);

    List<User> getChildUserDetails(List<Long> userIds);
}
