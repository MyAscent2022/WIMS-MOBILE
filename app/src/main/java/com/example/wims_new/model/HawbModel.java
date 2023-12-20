package com.example.wims_new.model;

import java.io.Serializable;

public class HawbModel implements Serializable {

    int id;
    String hawbNumber;
    String mawbNumber;
    String flightNumber;
    String registryNumber;
    String originCode;
    float volume;
    float grossMass;
    int numberOfContainers;
    int numberOfPackages;
    String dateOfArrival;
    String created_at;
    String timeOfArrival;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getRegistryNumber() {
        return registryNumber;
    }

    public void setRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getGrossMass() {
        return grossMass;
    }

    public void setGrossMass(float grossMass) {
        this.grossMass = grossMass;
    }

    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public int getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(String dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(String timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }
}
