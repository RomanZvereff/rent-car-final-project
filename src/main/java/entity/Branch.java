package entity;

import java.io.Serializable;
import java.util.List;

public class Branch implements Serializable {

    private int branchId;
    private String branchName;
    private String cityName;
    private String address;
    private Profile profile;
    private List<Profile> managers;

    public Branch() {
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Profile> getManagers() {
        return managers;
    }

    public void setManagers(List<Profile> managers) {
        this.managers = managers;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", profile=" + profile +
                ", managers=" + managers +
                '}';
    }

}
