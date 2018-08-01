package Response;

/**
 *An Clear Response object which represents a clear Repsonse in javacode, relays the success or failure of an attemplted clear
 *<pre>
 *<b>Domain</b>:
 *     success : boolean
 *     message : String
 *</pre>
 */
public class ClearResponse {

    private String message;

    public ClearResponse(){
        //To be implemented later
    }

    public String getMessage(){
        return message;
    }


    public void setMessage(String s){
        message = s;
    }


}
