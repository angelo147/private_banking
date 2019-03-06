package edu.privatebnk.consultation.rest;

import edu.privatebnk.consultation.persistence.model.*;

import java.util.List;

public class ResponseWrapper extends Response {
    private List<Customer> customers;
    private List<InvestmentProfile> profiles;
    private List<ConsultRequest> requests;
    private List<ConsultReport> reports;
    List<InvestProposal> proposals;

    public ResponseWrapper() {
    }

    public ResponseWrapper(ResponseCode code, List<Customer> customers, List<InvestmentProfile> profiles) {
        super(code);
        this.customers = customers;
        this.profiles = profiles;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<InvestmentProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<InvestmentProfile> profiles) {
        this.profiles = profiles;
    }

    public List<ConsultRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<ConsultRequest> requests) {
        this.requests = requests;
    }

    public List<ConsultReport> getReports() {
        return reports;
    }

    public void setReports(List<ConsultReport> reports) {
        this.reports = reports;
    }

    public List<InvestProposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<InvestProposal> proposals) {
        this.proposals = proposals;
    }
}
