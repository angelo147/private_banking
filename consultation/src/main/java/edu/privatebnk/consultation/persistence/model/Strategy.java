package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "startegy", schema = "private_banking", catalog = "")
public class Strategy {
    private int strategyid;
    private String description;
    private double risk;

    @Id
    @Column(name = "strategyid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getStrategyid() {
        return strategyid;
    }

    public void setStrategyid(int strategyid) {
        this.strategyid = strategyid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRisk() {
        return risk;
    }

    public void setRisk(double risk) {
        this.risk = risk;
    }
}
