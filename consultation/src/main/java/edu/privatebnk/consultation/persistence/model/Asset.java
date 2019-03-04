package edu.privatebnk.consultation.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "asset", schema = "private_banking", catalog = "")
public class Asset {
    private int assetid;
    private String name;
    private double value;
    private AssetType type;

    @Id
    @Column(name = "assetid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAssetid() {
        return assetid;
    }

    public void setAssetid(int assetid) {
        this.assetid = assetid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "assettypeid", referencedColumnName = "assettypeid", nullable = false)
    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }
}
