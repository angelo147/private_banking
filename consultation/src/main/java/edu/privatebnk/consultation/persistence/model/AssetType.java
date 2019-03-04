package edu.privatebnk.consultation.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "assettype", schema = "private_banking", catalog = "")
public class AssetType {
    private int assettypeid;
    private String type;
    @JsonIgnore
    private List<Asset> assetList;

    @Id
    @Column(name = "assettypeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAssettypeid() {
        return assettypeid;
    }

    public void setAssettypeid(int assettypeid) {
        this.assettypeid = assettypeid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    public List<Asset> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
    }
}
