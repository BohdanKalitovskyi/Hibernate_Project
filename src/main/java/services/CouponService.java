package services;

import hib.proj.Coupon;
import hib.proj.TransactionHelper;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;


    public CouponService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Coupon saveCoupon(Coupon coupon){
        return transactionHelper.executeInTransaction(session -> {
            session.persist(coupon);
            return coupon;
        });
    }

    public void addCouponToUser(Long couponId, Long clientId){
        transactionHelper.executeInTransaction(session -> {

            String sql = """
                    INSERT INTO client_coupons(client_id, coupon_id)
                    VALUES (:clientId, :couponId);
                    """;
            session.createNativeQuery(sql,void.class)
                    .setParameter("clientId", clientId)
                    .setParameter("couponId", couponId)
                    .executeUpdate();
        });
    }
}
