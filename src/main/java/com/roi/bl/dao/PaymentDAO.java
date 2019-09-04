package com.roi.bl.dao;

import com.roi.bl.model.Payments;
import com.roi.bl.util.Status;

import java.util.List;


public interface PaymentDAO extends BaseDAO<Payments> {

    List<Payments> getPaymentsbyStatus(Status status);
}
