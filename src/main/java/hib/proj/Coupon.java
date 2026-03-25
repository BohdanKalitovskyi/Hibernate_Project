package hib.proj;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "discount")
    private float discount;

    @ManyToMany(mappedBy = "couponList")
    private List<Client> clients;

    public long getId() {
        return id;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Coupon(float discount, String code) {
        this.discount = discount;
        this.code = code;
    }

    public Coupon() {
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", discount=" + discount +
                '}';
    }
}
