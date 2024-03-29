package de.flozo.common.dto.content;

public class Address {

    private int id;
    private String label;
    private Person person;
    private String company;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private String phoneNumber;
    private String mobileNumber;
    private String eMailAddress;
    private String webPage;

    public Address(int id, String label, Person person, String company, String street, String houseNumber, String postalCode, String city, String country, String phoneNumber, String mobileNumber, String eMailAddress, String webPage) {
        this.id = id;
        this.label = label;
        this.person = person;
        this.company = company;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.eMailAddress = eMailAddress;
        this.webPage = webPage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEMailAddress() {
        return eMailAddress;
    }

    public void setEMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", person=" + person +
                ", company='" + company + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", eMailAddress='" + eMailAddress + '\'' +
                ", webPage='" + webPage + '\'' +
                '}';
    }
}
