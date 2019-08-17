package com.roi.bl.data;

import java.math.BigDecimal;
import java.util.List;

public class UserData {

    private String userName;

    private Integer referalCount;

    private Integer roleId;

    private BigDecimal walletBalance;

    private List<UserData> childuserIds;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getReferalCount() {
        return referalCount;
    }

    public void setReferalCount(Integer referalCount) {
        this.referalCount = referalCount;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public List<UserData> getChilduserIds() {
        return childuserIds;
    }

    public void setChilduserIds(List<UserData> childuserIds) {
        this.childuserIds = childuserIds;
    }
}
