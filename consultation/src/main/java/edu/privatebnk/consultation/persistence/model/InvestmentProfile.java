package edu.privatebnk.consultation.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "investmentprofile", schema = "private_banking", catalog = "")
@NamedQuery(name = "InvestmentProfile.findAll", query = "SELECT ip FROM InvestmentProfile ip where ip.customer.customerid=:customerid")
public class InvestmentProfile {
    private int profileid;
    @JsonIgnore
    private Customer customer;
    private Date dateCreated;
    private Strategy strategy;
    private boolean active;

    @Id
    @Column(name = "profileid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getProfileid() {
        return profileid;
    }

    public void setProfileid(int profileid) {
        this.profileid = profileid;
    }

    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @ManyToOne
    @JoinColumn(name = "strategyid", referencedColumnName = "strategyid", nullable = false)
    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
