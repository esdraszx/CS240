package Response;

import Model.PersonModel;


public class PersonGetAllResponse {

    private transient String message;
    private PersonModel[] data;
    private transient boolean success;


    public PersonGetAllResponse(){

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

    public void setArray(PersonModel[] in){
        data = new PersonModel[in.length];

        for (int i = 0; i < in.length; i++){
            data[i] = in[i];
        }
        return;
    }

    public PersonModel[] getData(){
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

        PersonGetAllResponse secondResponse = (PersonGetAllResponse) o;

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
