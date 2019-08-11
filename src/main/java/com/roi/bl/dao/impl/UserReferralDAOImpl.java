package com.roi.bl.dao.impl;

import com.roi.bl.dao.UserReferralDAO;
import com.roi.bl.model.UserReferral;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class UserReferralDAOImpl extends BaseEntityDAOImpl<UserReferral> implements UserReferralDAO {

    public UserReferralDAOImpl() { }


}
