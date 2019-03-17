package edu.privatebnk.consultation.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Entity
@Table(name = "account", schema = "private_banking", catalog = "")
public class Account {
    private int accountid;
    private String accountnumber;
    @JsonIgnore
    private Customer customer;
    private Date date_opened;
    private Set<Asset> assets;

    @Id
    @Column(name = "accountid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    @OneToOne
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate_opened() {
        return date_opened;
    }

    public void setDate_opened(Date date_opened) {
        this.date_opened = date_opened;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_asset", joinColumns = @JoinColumn(name = "accountid"), inverseJoinColumns = @JoinColumn(name = "assetid"))
    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }
}
