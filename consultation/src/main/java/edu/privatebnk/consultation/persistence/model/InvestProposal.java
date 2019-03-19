package edu.privatebnk.consultation.persistence.model;

import edu.privatebnk.consultation.rest.ProposalStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "investproposal", schema = "private_banking", catalog = "")
public class InvestProposal {
    private int proposalid;
    private Date dateIssued;
    private Date dateStatusUpdated;
    private ProposalStatus status;
    private double commisionrate;
    private ConsultRequest request;
    private JsonDocument document;

    @PrePersist
    private void onCreate(){
        dateIssued = new Date();
        status = ProposalStatus.PENDING;
    }
    @Id
    @Column(name = "proposalid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getProposalid() {
        return proposalid;
    }

    public void setProposalid(int proposalid) {
        this.proposalid = proposalid;
    }

    @Enumerated(EnumType.STRING)
    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }

    public double getCommisionrate() {
        return commisionrate;
    }

    public void setCommisionrate(double commisionrate) {
        this.commisionrate = commisionrate;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getDateStatusUpdated() {
        return dateStatusUpdated;
    }

    public void setDateStatusUpdated(Date dateStatusUpdated) {
        this.dateStatusUpdated = dateStatusUpdated;
    }

    @OneToOne
    @JoinColumn(name = "requestid", referencedColumnName = "requestid", nullable = false)
    public ConsultRequest getRequest() {
        return request;
    }

    public void setRequest(ConsultRequest request) {
        this.request = request;
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
