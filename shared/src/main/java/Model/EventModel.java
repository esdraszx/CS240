package Model;

/**
 *An Event model object which represents an Event in javacode, contains the necessary information to represent an event in the database
 *<pre>
 *<b>Domain</b>:
 *     eventID : string
 *     descendant : string
 *     latitude : string
 *     longitude : string
 *     country : string
 *     city : string
 *     type : string
 *     year : string
 *
 *</pre>
 */
public class EventModel {

    private String eventID;
    private String descendant;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;


    public EventModel(){

    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return eventType;
    }

    public void setType(String type) {
        this.eventType = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription(){
        String out = new String(eventType + ": " + city + ", " + country + "(" + year + ")");
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        EventModel secondEvent = (EventModel) o;

        if (!eventID.equals(secondEvent.eventID)) {
            return false;
        }
        if (!descendant.equals(secondEvent.descendant)) {
            return false;
        }
        if (!personID.equals(secondEvent.personID)) {
            return false;
        }
        if (!latitude.equals(secondEvent.latitude)) {
            return false;
        }
        if (!longitude.equals(secondEvent.longitude)) {
            return false;
        }
        if (!country.equals(secondEvent.country)) {
            return false;
        }
        if (!city.equals(secondEvent.city)) {
            return false;
        }
        if (!eventType.equals(secondEvent.eventType)) {
            return false;
        }
        if (year != secondEvent.year){
            return false;
        }
        return true;
    }

}
