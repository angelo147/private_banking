package edu.privatebnk.consultation.rest;

public class ProposalRequest {
    private int requestid;
    private String jsondocument;

    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
    }

    public String getJsondocument() {
        return jsondocument;
    }

    public void setJsondocument(String jsondocument) {
        this.jsondocument = jsondocument;
    }
}
