package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consultreport", schema = "private_banking", catalog = "")
public class ConsultReport {
    private int reportid;
    //private UserEntity cro;
    private UserEntity ma;
    //private InvestmentProfile profile;
    private Date dateRequested;
    private Date dateProccessed;
    private ConsultRequest request;

    @Id
    @Column(name = "reportid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getReportid() {
        return reportid;
    }

    public void setReportid(int reportid) {
        this.reportid = reportid;
    }

    public ConsultRequest getRequest() {
        return request;
    }

    public void setRequest(ConsultRequest request) {
        this.request = request;
    }

    /*public UserEntity getCro() {
        return cro;
    }

    public void setCro(UserEntity cro) {
        this.cro = cro;
    }*/

    public UserEntity getMa() {
        return ma;
    }

    public void setMa(UserEntity ma) {
        this.ma = ma;
    }

    /*public InvestmentProfile getProfile() {
        return profile;
    }

    public void setProfile(InvestmentProfile profile) {
        this.profile = profile;
    }*/

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
}
