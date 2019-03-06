package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consultrequest", schema = "private_banking", catalog = "")
@NamedQueries({
        @NamedQuery(name = "ConsultRequest.findAllByStatusCRO", query = "SELECT cr FROM ConsultRequest cr where cr.proccessed=:proccessed and cr.cro.userid=:userid"),
        @NamedQuery(name = "ConsultRequest.findAllCRO", query = "SELECT cr FROM ConsultRequest cr where cr.cro.userid=:userid"),
        @NamedQuery(name = "ConsultRequest.findAllByStatusMA", query = "SELECT cr FROM ConsultRequest cr where cr.proccessed=:proccessed and cr.cro.userid=:userid and cr.marequired=true"),
        @NamedQuery(name = "ConsultRequest.findAllMA", query = "SELECT cr FROM ConsultRequest cr where cr.cro.userid=:userid and cr.marequired=true")
})
public class ConsultRequest {
    private int requestid;
    private UserEntity cro;
    private UserEntity ma;
    private Customer customer;
    private Date dateRequested;
    private Date dateProccessed;
    private boolean proccessed;
    private boolean marequired;
    private ConsultReport report;
    private InvestProposal proposal;
    private JsonDocument document;

    @Id
    @Column(name = "requestid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
    }

    @ManyToOne
    @JoinColumn(name = "croid", referencedColumnName = "userid", nullable = false)
    public UserEntity getCro() {
        return cro;
    }

    public void setCro(UserEntity cro) {
        this.cro = cro;
    }

    @ManyToOne
    @JoinColumn(name = "maid", referencedColumnName = "userid", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @OneToOne(mappedBy = "request")
    public ConsultReport getReport() {
        return report;
    }

    public void setReport(ConsultReport report) {
        this.report = report;
    }

    @OneToOne
    @JoinColumn(name = "documentid", referencedColumnName = "documentid", nullable = false)
    public JsonDocument getDocument() {
        return document;
    }

    public void setDocument(JsonDocument document) {
        this.document = document;
    }

    public boolean isMarequired() {
        return marequired;
    }

    public void setMarequired(boolean marequired) {
        this.marequired = marequired;
    }

    @OneToOne(mappedBy = "request")
    public InvestProposal getProposal() {
        return proposal;
    }

    public void setProposal(InvestProposal proposal) {
        this.proposal = proposal;
    }
}
