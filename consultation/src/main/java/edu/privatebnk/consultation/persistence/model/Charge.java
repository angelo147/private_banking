package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "charge", schema = "private_banking", catalog = "")
@NamedQuery(name = "Charge.findAll", query = "SELECT c FROM Charge c")
public class Charge {
    private int chargeid;
    private double investamount_lowlimit;
    private double investamount_upperlimit;
    private double percentagecommisionrate;

    @Id
    @Column(name = "chargeid")
    public int getChargeid() {
        return chargeid;
    }

    public void setChargeid(int chargeid) {
        this.chargeid = chargeid;
    }

    public double getInvestamount_lowlimit() {
        return investamount_lowlimit;
    }

    public void setInvestamount_lowlimit(double investamount_lowlimit) {
        this.investamount_lowlimit = investamount_lowlimit;
    }

    public double getInvestamount_upperlimit() {
        return investamount_upperlimit;
    }

    public void setInvestamount_upperlimit(double investamount_upperlimit) {
        this.investamount_upperlimit = investamount_upperlimit;
    }

    public double getPercentagecommisionrate() {
        return percentagecommisionrate;
    }

    public void setPercentagecommisionrate(double percentagecommisionrate) {
        this.percentagecommisionrate = percentagecommisionrate;
    }
}
