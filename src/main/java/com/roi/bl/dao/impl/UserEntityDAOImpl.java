package com.roi.bl.dao.impl;

import com.roi.bl.dao.UserDAO;
import com.roi.bl.model.User;
import com.roi.bl.util.Status;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserEntityDAOImpl extends BaseEntityDAOImpl<User> implements UserDAO {

    public UserEntityDAOImpl() { }


    @Override
    public User getUserforLogin(User user) {
         return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("email", user.getEmail()))
                .add(Restrictions.eq("password",user.getPassword()))
                .uniqueResult();


    }

    @Override
    public User getUserbyName(String username) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("name", username))
                .uniqueResult();
    }

    @Override
    public User getUserbyEmail(String email) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public int updateReferralCount(String userEmail){
        return getSession().createSQLQuery("update user set referal_count = referal_count+1 where email= :USEREMAIL").setString("USEREMAIL",userEmail).executeUpdate();
    }

    @Override
    public List<User> getChildUserDetails(List<Long> userIds){
        return getSession().createCriteria(User.class).add(Restrictions.in("id", userIds)).list();
    }

    @Override
    public List<User> getUserByStatus(Status status) {
        return getSession().createCriteria(User.class).add(Restrictions.eq("status", status.getStatusValue())).list();
    }


}
