package edu.privatebnk.consultation.rest;

import edu.privatebnk.consultation.persistence.model.Customer;
import edu.privatebnk.consultation.persistence.model.InvestmentProfile;

import java.util.List;

public class ResponseWrapper extends Response {
    private List<Customer> customers;
    private List<InvestmentProfile> profiles;

    public ResponseWrapper(List<Customer> customers, List<InvestmentProfile> profiles) {
        this.customers = customers;
        this.profiles = profiles;
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
}
