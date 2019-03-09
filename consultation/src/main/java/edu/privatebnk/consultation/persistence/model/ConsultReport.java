package edu.privatebnk.consultation.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consultreport", schema = "private_banking", catalog = "")
@NamedQuery(name = "ConsultReport.findAll", query = "SELECT cr FROM ConsultReport cr where cr.ma.userid=:userid")
public class ConsultReport {
    private int reportid;
    //private UserEntity cro;
    private UserEntity ma;
    //private InvestmentProfile profile;
    private Date dateReported;
    private Date dateProccessed;
    @JsonIgnore
    private ConsultRequest request;
    private JsonDocument document;

    @PrePersist
    private void onCreate(){
        dateReported = new Date();
    }
    @Id
    @Column(name = "reportid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getReportid() {
        return reportid;
    }

    public void setReportid(int reportid) {
        this.reportid = reportid;
    }

    @OneToOne
    //@PrimaryKeyJoinColumn
    @JoinColumn(name = "requestid", referencedColumnName = "requestid", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "maid", referencedColumnName = "userid", nullable = false)
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

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public Date getDateProccessed() {
        return dateProccessed;
    }

    public void setDateProccessed(Date dateProccessed) {
        this.dateProccessed = dateProccessed;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "documentid", referencedColumnName = "documentid", nullable = false)
    public JsonDocument getDocument() {
        return document;
    }

    public void setDocument(JsonDocument document) {
        this.document = document;
    }
}
