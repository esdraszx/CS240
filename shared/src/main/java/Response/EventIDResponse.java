package Response;
import Model.EventModel;


/**
 *An EventID Response object which represents a EvenntID Repsonse in javacode, it has an EventModel which contains the information of the desired event
 *<pre>
 *<b>Domain</b>:
 *     success : boolean
 *     message : String
 *     descendant : String
 *     eventID : String
 *     personID : String
 *     latitude : String
 *     longitude : String
 *     country : String
 *     city : String
 *     eventType : String
 *     year : String
 *</pre>
 */
public class EventIDResponse {

    private String descendant;
    private String eventID;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private transient boolean success;
    private transient String message;


    public EventIDResponse(){
        //To be implemented later
    }


    public EventIDResponse(EventModel e){
        this.descendant = e.getDescendant();
        this.eventID = e.getEventID();
        this.personID = e.getPersonID();
        this.latitude = e.getLatitude();
        this.longitude = e.getLongitude();
        this.country = e.getCountry();
        this.city = e.getCity();
        this.eventType = e.getType();
        this.year = e.getYear();
    }

    public void setSuccess(boolean b){
        success = b;
    }

    public void setMessage(String s){
        message = s;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
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

        EventIDResponse secondResponse = (EventIDResponse) o;

        if (eventID!= null && !eventID.equals(secondResponse.eventID)) {
            return false;
        }
        if (descendant != null && !descendant.equals(secondResponse.descendant)) {
            return false;
        }
        if (personID != null && !personID.equals(secondResponse.personID)) {
            return false;
        }
        if (latitude != null && !latitude.equals(secondResponse.latitude)) {
            return false;
        }
        if (longitude != null && !longitude.equals(secondResponse.longitude)) {
            return false;
        }
        if (country != null && !country.equals(secondResponse.country)) {
            return false;
        }
        if (city != null && !city.equals(secondResponse.city)) {
            return false;
        }
        if (eventType != null && !eventType.equals(secondResponse.eventType)) {
            return false;
        }
        if (year != secondResponse.year){
            return false;
        }
        if (success != secondResponse.success) {
            return false;
        }
        if (message == null && secondResponse.message != null) {
            return false;
        }
        if (message != null && secondResponse.message == null){
            return false;
        }
        if (message != null && secondResponse.message != null){
            if (!message.equals(secondResponse.message)){
                return false;
            }
        }
        return true;
    }

}
