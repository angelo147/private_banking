package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "document", schema = "private_banking", catalog = "")
public class JsonDocument {
    private int documentid;
    private String json;

    @Id
    @Column(name = "documentid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getDocumentid() {
        return documentid;
    }

    public void setDocumentid(int documentid) {
        this.documentid = documentid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
