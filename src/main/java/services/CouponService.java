package services;

import hib.proj.Client;
import hib.proj.Coupon;
import hib.proj.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Coupon> findAll(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT c from Coupon c ",Coupon.class).list();
        }
    }
}
