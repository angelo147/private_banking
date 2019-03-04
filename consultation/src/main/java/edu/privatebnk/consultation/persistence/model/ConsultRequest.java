package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consultrequest", schema = "private_banking", catalog = "")
public class ConsultRequest {
    private int requestid;
    private UserEntity cro;
    private UserEntity ma;
    private Customer customer;
    private Date dateRequested;
    private Date dateProccessed;
    private boolean proccessed;

    @Id
    @Column(name = "requestid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
    }

    public UserEntity getCro() {
        return cro;
    }

    public void setCro(UserEntity cro) {
        this.cro = cro;
    }

    public UserEntity getMa() {
        return ma;
    }

    public void setMa(UserEntity ma) {
        this.ma = ma;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Date getDateProccessed() {
        return dateProccessed;
    }

    public void setDateProccessed(Date dateProccessed) {
        this.dateProccessed = dateProccessed;
    }

    public boolean isProccessed() {
        return proccessed;
    }

    public void setProccessed(boolean proccessed) {
        this.proccessed = proccessed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
