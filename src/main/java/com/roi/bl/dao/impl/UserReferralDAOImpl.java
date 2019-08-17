package com.roi.bl.dao.impl;

import com.roi.bl.dao.UserReferralDAO;
import com.roi.bl.model.UserReferral;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserReferralDAOImpl extends BaseEntityDAOImpl<UserReferral> implements UserReferralDAO {

    public UserReferralDAOImpl() { }


    @Override
    public List<Long> getChilduserIds(Long userId) {
        List<Long> childuserIds = new ArrayList<>();
        List<UserReferral> userReferrals = getSession().createCriteria(UserReferral.class)
                .add(Restrictions.eq("parentUserId", userId))
                .list();
        for(UserReferral userReferral : userReferrals){
            childuserIds.add(userReferral.getChilduserId());
        }
        return childuserIds;
    }
}
