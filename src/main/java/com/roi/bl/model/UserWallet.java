package com.roi.bl.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="user_wallet")
public class UserWallet extends BaseEntity {

    @Column(name="user_id")
    private Long userId;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="transfer_type")
    private String transferType;

    @Column(name = "update_ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
