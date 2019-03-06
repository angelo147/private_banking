package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "investmentprofile", schema = "private_banking", catalog = "")
public class InvestProposal {
    private int proposalid;
    //private UserEntity cro;
    //private Customer customer;
    private Date dateIssued;
    private Date dateAccepted;
    private boolean accepted;
    private ConsultRequest request;
    private JsonDocument document;

    @Id
    @Column(name = "proposalid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getProposalid() {
        return proposalid;
    }

    public void setProposalid(int proposalid) {
        this.proposalid = proposalid;
    }

    /*public UserEntity getCro() {
        return cro;
    }

    public void setCro(UserEntity cro) {
        this.cro = cro;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(Date dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @OneToOne
    @JoinColumn(name = "requestid", referencedColumnName = "requestid", nullable = false)
    public ConsultRequest getRequest() {
        return request;
    }

    public void setRequest(ConsultRequest request) {
        this.request = request;
    }

    @OneToOne
    @JoinColumn(name = "documentid", referencedColumnName = "documentid", nullable = false)
    public JsonDocument getDocument() {
        return document;
    }

    public void setDocument(JsonDocument document) {
        this.document = document;
    }
}
