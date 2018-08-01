package Response;

import Model.EventModel;

/**
 *An EventGetAll Response object which represents a EventGetAll Repsonse in javacode, it has an EventModel array of all events associated with the desired user
 *<pre>
 *<b>Domain</b>:
 *     success : boolean
 *     message : String
 *     data : EventModel[]
 *</pre>
 */
public class EventGetAllResponse {

    private EventModel[] data;
    private transient boolean success;
    private transient String message;


    public EventGetAllResponse(){

    }

    public void setSuccess (boolean b){
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

    public void setData(EventModel[] input){
        data = new EventModel[input.length];

        for (int i = 0; i < input.length; i++){
            data[i] = input[i];
        }
    }

    public EventModel[] getData(){
        return data;
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

        EventGetAllResponse secondResponse = (EventGetAllResponse) o;

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
        if (data!= null){
            for (int i = 0; i < data.length; i++){
                if (!data[i].equals(secondResponse.data[i])){
                    return false;
                }
            }
        }

        return true;
    }
}
