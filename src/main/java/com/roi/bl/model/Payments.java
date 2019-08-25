package com.roi.bl.model;

import com.roi.bl.util.Status;

import javax.persistence.*;

@Entity
@Table(name = "payment_reference")
public class Payments extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="location")
    private String fileLocation;

    @Column(name="type")
    private String paymentType;

    @Column(name="status")
    private Status status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
