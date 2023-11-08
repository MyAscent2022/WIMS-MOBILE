package com.example.wims_new.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class MawbModel implements Serializable {
    long id;
    int bolNature;
    String bolType;
    String carrierReference;
    String consigneeAddress;
    String consigneeCode;
    String consigneeName;
    String createdAt;
    String customCurrency;
    float customValue;
    String customsOffice;
    String dateOfArrival;
    String destinationCode;
    String exporterCode;
    String exporterName;
    String flightNumber;
    String freightCurrency;
    String freightIndicator;
    float freightValue;
    String goodsDescription;
    float grossMass;
    String hawbNumber;
    String informationPartA;
    int lineNumber;
    String marksOfSeals;
    String mawbNo;
    String mawbNumber;
    String modifiedAt;
    String notifyPartyAddress;
    String notifyPartyCode;
    String notifyPartyName;
    int numberOfContainers;
    String numberOfPackages;
    int numberOfSeals;
    String operationLocation;
    String operationLocationInformation;
    String originCode;
    String packageTypeCode;
    String placeOfLoading;
    String placeOfUnloading;
    String registryNumber;
    String sealingPartyCode;
    String shippingMarks;
    String timeOfArrival;
    String transportCurrency;
    float transportValue;
    float volume;
    String carrierCode;
    String entryNumber;
    String flightDate;
    String grossTonnage;
    String lastDischarge;
    String masterInformation;
    int mawbSupportingDocsId;
    String modeOfTransport;
    String nameOfTransporter;
    String nationalityOfTransport;
    String netTonnage;
    int numberOfBols;
    String placeOfTransporter;
    String registrationDate;
    String registrationNumber;
    String cargoStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBolNature() {
        return bolNature;
    }

    public void setBolNature(int bolNature) {
        this.bolNature = bolNature;
    }

    public String getBolType() {
        return bolType;
    }

    public void setBolType(String bolType) {
        this.bolType = bolType;
    }

    public String getCarrierReference() {
        return carrierReference;
    }

    public void setCarrierReference(String carrierReference) {
        this.carrierReference = carrierReference;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getCustomCurrency() {
        return customCurrency;
    }

    public void setCustomCurrency(String customCurrency) {
        this.customCurrency = customCurrency;
    }

    public float getCustomValue() {
        return customValue;
    }

    public void setCustomValue(float customValue) {
        this.customValue = customValue;
    }

    public String getCustomsOffice() {
        return customsOffice;
    }

    public void setCustomsOffice(String customsOffice) {
        this.customsOffice = customsOffice;
    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(String dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getExporterCode() {
        return exporterCode;
    }

    public void setExporterCode(String exporterCode) {
        this.exporterCode = exporterCode;
    }

    public String getExporterName() {
        return exporterName;
    }

    public void setExporterName(String exporterName) {
        this.exporterName = exporterName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFreightCurrency() {
        return freightCurrency;
    }

    public void setFreightCurrency(String freightCurrency) {
        this.freightCurrency = freightCurrency;
    }

    public String getFreightIndicator() {
        return freightIndicator;
    }

    public void setFreightIndicator(String freightIndicator) {
        this.freightIndicator = freightIndicator;
    }

    public float getFreightValue() {
        return freightValue;
    }

    public void setFreightValue(float freightValue) {
        this.freightValue = freightValue;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
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

    public String getInformationPartA() {
        return informationPartA;
    }

    public void setInformationPartA(String informationPartA) {
        this.informationPartA = informationPartA;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMarksOfSeals() {
        return marksOfSeals;
    }

    public void setMarksOfSeals(String marksOfSeals) {
        this.marksOfSeals = marksOfSeals;
    }

    public String getMawbNo() {
        return mawbNo;
    }

    public void setMawbNo(String mawbNo) {
        this.mawbNo = mawbNo;
    }

    public String getMawbNumber() {
        return mawbNumber;
    }

    public void setMawbNumber(String mawbNumber) {
        this.mawbNumber = mawbNumber;
    }

    public String getNotifyPartyAddress() {
        return notifyPartyAddress;
    }

    public void setNotifyPartyAddress(String notifyPartyAddress) {
        this.notifyPartyAddress = notifyPartyAddress;
    }

    public String getNotifyPartyCode() {
        return notifyPartyCode;
    }

    public void setNotifyPartyCode(String notifyPartyCode) {
        this.notifyPartyCode = notifyPartyCode;
    }

    public String getNotifyPartyName() {
        return notifyPartyName;
    }

    public void setNotifyPartyName(String notifyPartyName) {
        this.notifyPartyName = notifyPartyName;
    }

    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public String getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(String numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public int getNumberOfSeals() {
        return numberOfSeals;
    }

    public void setNumberOfSeals(int numberOfSeals) {
        this.numberOfSeals = numberOfSeals;
    }

    public String getOperationLocation() {
        return operationLocation;
    }

    public void setOperationLocation(String operationLocation) {
        this.operationLocation = operationLocation;
    }

    public String getOperationLocationInformation() {
        return operationLocationInformation;
    }

    public void setOperationLocationInformation(String operationLocationInformation) {
        this.operationLocationInformation = operationLocationInformation;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getPackageTypeCode() {
        return packageTypeCode;
    }

    public void setPackageTypeCode(String packageTypeCode) {
        this.packageTypeCode = packageTypeCode;
    }

    public String getPlaceOfLoading() {
        return placeOfLoading;
    }

    public void setPlaceOfLoading(String placeOfLoading) {
        this.placeOfLoading = placeOfLoading;
    }

    public String getPlaceOfUnloading() {
        return placeOfUnloading;
    }

    public void setPlaceOfUnloading(String placeOfUnloading) {
        this.placeOfUnloading = placeOfUnloading;
    }

    public String getRegistryNumber() {
        return registryNumber;
    }

    public void setRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;
    }

    public String getSealingPartyCode() {
        return sealingPartyCode;
    }

    public void setSealingPartyCode(String sealingPartyCode) {
        this.sealingPartyCode = sealingPartyCode;
    }

    public String getShippingMarks() {
        return shippingMarks;
    }

    public void setShippingMarks(String shippingMarks) {
        this.shippingMarks = shippingMarks;
    }

    public String getTransportCurrency() {
        return transportCurrency;
    }

    public void setTransportCurrency(String transportCurrency) {
        this.transportCurrency = transportCurrency;
    }

    public float getTransportValue() {
        return transportValue;
    }

    public void setTransportValue(float transportValue) {
        this.transportValue = transportValue;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(String entryNumber) {
        this.entryNumber = entryNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getGrossTonnage() {
        return grossTonnage;
    }

    public void setGrossTonnage(String grossTonnage) {
        this.grossTonnage = grossTonnage;
    }

    public String getLastDischarge() {
        return lastDischarge;
    }

    public void setLastDischarge(String lastDischarge) {
        this.lastDischarge = lastDischarge;
    }

    public String getMasterInformation() {
        return masterInformation;
    }

    public void setMasterInformation(String masterInformation) {
        this.masterInformation = masterInformation;
    }

    public int getMawbSupportingDocsId() {
        return mawbSupportingDocsId;
    }

    public void setMawbSupportingDocsId(int mawbSupportingDocsId) {
        this.mawbSupportingDocsId = mawbSupportingDocsId;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public String getNameOfTransporter() {
        return nameOfTransporter;
    }

    public void setNameOfTransporter(String nameOfTransporter) {
        this.nameOfTransporter = nameOfTransporter;
    }

    public String getNationalityOfTransport() {
        return nationalityOfTransport;
    }

    public void setNationalityOfTransport(String nationalityOfTransport) {
        this.nationalityOfTransport = nationalityOfTransport;
    }

    public String getNetTonnage() {
        return netTonnage;
    }

    public void setNetTonnage(String netTonnage) {
        this.netTonnage = netTonnage;
    }

    public int getNumberOfBols() {
        return numberOfBols;
    }

    public void setNumberOfBols(int numberOfBols) {
        this.numberOfBols = numberOfBols;
    }

    public String getPlaceOfTransporter() {
        return placeOfTransporter;
    }

    public void setPlaceOfTransporter(String placeOfTransporter) {
        this.placeOfTransporter = placeOfTransporter;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(String timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
}
