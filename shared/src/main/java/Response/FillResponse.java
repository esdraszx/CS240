package Response;


public class FillResponse {

    private transient boolean success;
    private transient String message;
    private transient int numPersons = 0;
    private transient int numEvents = 0;


    public FillResponse(){
        success = false;
        message = new String();
    }

    public void setSuccess(boolean b){
        success = b;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String s){
        message = s;
    }

    public void setNumPersons(int i){
        numPersons = i;
    }

    public void setNumEvents(int i){
        numEvents = i;
    }

    public int getNumPersons(){
        return numPersons;
    }

    public int getNumEvents(){
        return numEvents;
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

        FillResponse secondResponse = (FillResponse) o;

        if(numEvents != secondResponse.numEvents){
            return false;
        }
        if(numPersons != secondResponse.numPersons){
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
