package crawling;

public class PropertyDetails {

    private String streetNumber;
    private String streetName;
    private String suburbName;
    private String state;
    private String postalCode;
    private String price;
    private String site;

    public PropertyDetails(String streetNumber, String streetName, String suburbName, String state, String postalCode, String price, String site) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburbName = suburbName;
        this.state = state;
        this.postalCode = postalCode;
        this.price = price;
        this.site = site;
    }

    public PropertyDetails() {
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getSuburbName() {
        return suburbName;
    }

    public void setSuburbName(String suburbName) {
        this.suburbName = suburbName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
