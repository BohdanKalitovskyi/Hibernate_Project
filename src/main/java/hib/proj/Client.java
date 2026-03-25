package hib.proj;

import jakarta.persistence.*;
import org.hibernate.annotations.JavaType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "client_name", nullable = false)
    private String name;

    @Column(name = "client_email", nullable = false, unique = true)
    private String email;

    @Column(name = "registration_year", nullable = false)
    private long registrationYear;

    @OneToOne(mappedBy = "client", cascade = CascadeType.REMOVE)
    private Profile profile;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_coupons", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "coupon_id", referencedColumnName = "id"))
    private List<Coupon> couponList = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Client() {
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public Client(long registrationYear, String email, String name) {
        this.registrationYear = registrationYear;
        this.email = email;
        this.name = name;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

    public long getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(long registrationYear) {
        this.registrationYear = registrationYear;
    }

    public List<Coupon> getCoupons() {
        return couponList;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", registrationYear=" + registrationYear +
                '}';
    }

}
