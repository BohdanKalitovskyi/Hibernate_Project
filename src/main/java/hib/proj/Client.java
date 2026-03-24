package hib.proj;

import jakarta.persistence.*;

import java.util.Date;

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

    @Column(name = "client_name")
    private String name;

    @Column(name = "client_email")
    private String email;

    @Column(name = "registration_year")
    private long registrationYear;

    public Client() {
    }

    public Client(long registrationYear, String email, String name) {
        this.registrationYear = registrationYear;
        this.email = email;
        this.name = name;
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
}
