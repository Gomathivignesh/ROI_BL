package com.roi.bl.dao;


import com.roi.bl.model.UserReferral;

import java.util.List;

public interface UserReferralDAO extends BaseDAO<UserReferral> {

    List<Long> getChilduserIds(Long userId);


}
