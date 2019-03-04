package edu.privatebnk.consultation.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer", schema = "private_banking", catalog = "")
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c where cro.userid=:croid")
public class Customer {
    private int customerid;
    private String name;
    private String surname;
    private String address;
    private Date birthdate;
    private String phone;
    private String email;
    private Date date_added;
    private String status;
    @JsonIgnore
    private UserEntity cro;
    private Account account;
    @JsonIgnore
    private List<InvestmentProfile> investmentProfiles;
    private InvestmentProfile investmentProfile;

    @Id
    @Column(name = "customerid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "croid", referencedColumnName = "userid")
    public UserEntity getCro() {
        return cro;
    }

    public void setCro(UserEntity cro) {
        this.cro = cro;
    }

    @OneToOne(mappedBy = "customer")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    public List<InvestmentProfile> getInvestmentProfiles() {
        return investmentProfiles;
    }

    public void setInvestmentProfiles(List<InvestmentProfile> investmentProfiles) {
        this.investmentProfiles = investmentProfiles;
    }

    @Transient
    public InvestmentProfile getInvestmentProfile() {
        return investmentProfiles.stream().filter(InvestmentProfile::isActive).findFirst().orElse(null);
    }

    public void setInvestmentProfile(InvestmentProfile investmentProfile) {
        this.investmentProfile = investmentProfile;
    }
}
