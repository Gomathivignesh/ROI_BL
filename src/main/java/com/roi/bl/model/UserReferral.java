package com.roi.bl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="user_referral")
public class UserReferral extends BaseEntity implements Serializable {

    @Column(name="child_userId")
    private Long childuserId;

    @Column(name="parent_userids")
    private Long parentUserId;

    public Long getChilduserId() {
        return childuserId;
    }

    public void setChilduserId(Long childuserId) {
        this.childuserId = childuserId;
    }

    public Long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }
}
