package com.roi.bl.dao.impl;

import com.roi.bl.dao.PaymentDAO;
import com.roi.bl.model.Payments;
import com.roi.bl.util.Status;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentDAOImpl extends BaseEntityDAOImpl<Payments> implements PaymentDAO {


    @Override
    public List<Payments> getPaymentsbyStatus(Status status) {
        Criteria criteria = getSession().createCriteria(Payments.class, "payment").createAlias("payment.user", "user").add(Restrictions.eq("status", status ));
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("payment.fileLocation"), "fileLocation");
        proList.add(Projections.property("payment.paymentType"), "paymentType");
        proList.add(Projections.property("user.email"), "email");
        criteria.setProjection(proList);
         return criteria.list();

    }
}
