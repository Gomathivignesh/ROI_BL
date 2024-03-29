package com.roi.bl.model;

import com.roi.bl.util.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 123456789L;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="mobile")
    private String mobile;

    @Column(name="password")
    private String password;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="pin")
    private String pin;

    @Transient
    private String referedBy;


    @Column(name="role_id")
    private int userRole;

    @Column(name="referal_count")
    private int referalCount;

    @Column(name="status")
    private Status status;


    @OneToMany(mappedBy="user")
    private Set<Payments> Payments;

    public int getReferalCount() {
        return referalCount;
    }

    public void setReferalCount(int referalCount) {
        this.referalCount = referalCount;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }


    public String getReferedBy() {
        return referedBy;
    }

    public void setReferedBy(String referedBy) {
        this.referedBy = referedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
