package com.roi.bl.dao.impl;

import com.roi.bl.dao.UserWalletDAO;
import com.roi.bl.model.UserWallet;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class UserWalletDAOImpl extends BaseEntityDAOImpl<UserWallet> implements UserWalletDAO {

    public UserWalletDAOImpl(){}

    public BigDecimal getBalanceByUserId(Long userId){
        BigDecimal walletBalance = BigDecimal.ZERO;
        List<UserWallet> userWalletList = getSession().createCriteria(UserWallet.class)
                .add(Restrictions.eq("userId", userId)).list();
        for(UserWallet userWallet: userWalletList){
            walletBalance.add(userWallet.getAmount());
        }
        return walletBalance;




    }




}
