package com.example.wims_new.model;

import java.sql.Date;
import java.sql.Time;

public class HawbDetails {
    int id;
    Date dateOfArrival;
    float grossMass;
    String hawbNumber;
    String mawbNumber;
    Long numberOfContainers;
    String numberOfPackages;
    String originCode;
    Time timeOfArrival;
    float volume;

    public HawbDetails() {
    }

    public HawbDetails(int id, Date dateOfArrival, float grossMass, String hawbNumber, String mawbNumber, Long numberOfContainers, String numberOfPackages, String originCode, Time timeOfArrival, float volume) {
        this.id = id;
        this.dateOfArrival = dateOfArrival;
        this.grossMass = grossMass;
        this.hawbNumber = hawbNumber;
        this.mawbNumber = mawbNumber;
        this.numberOfContainers = numberOfContainers;
        this.numberOfPackages = numberOfPackages;
        this.originCode = originCode;
        this.timeOfArrival = timeOfArrival;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(Date dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public float getGrossMass() {
        return grossMass;
    }

    public void setGrossMass(float grossMass) {
        this.grossMass = grossMass;
    }

    public String getHawbNumber() {
        return hawbNumber;
    }

    public void setHawbNumber(String hawbNumber) {
        this.hawbNumber = hawbNumber;
    }

    public String getMawbNumber() {
        return mawbNumber;
    }

    public void setMawbNumber(String mawbNumber) {
        this.mawbNumber = mawbNumber;
    }

    public Long getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(Long numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public String getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(String numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public Time getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(Time timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
