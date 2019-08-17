package com.roi.bl.dao;

import com.roi.bl.model.UserWallet;

import java.math.BigDecimal;

public interface UserWalletDAO extends BaseDAO<UserWallet> {

    BigDecimal getBalanceByUserId(Long userId);
}
