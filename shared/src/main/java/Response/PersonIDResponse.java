package Response;
import Model.PersonModel;



public class PersonIDResponse {

    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;
    private transient boolean success;
    private transient String message;


    public PersonIDResponse(){

    }


    public PersonIDResponse(PersonModel p){
        this.descendant = p.getDescendant();
        this.personID = p.getPersonID();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.gender = p.getGender();
        this.father = p.getFather();
        this.mother = p.getMother();
        this.spouse = p.getSpouse();
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

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setMessage(String s){
        message = s;
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

        PersonIDResponse secondPerson = (PersonIDResponse) o;

        if (personID!= null && !personID.equals(secondPerson.personID)) {
            return false;
        }
        if (descendant!= null && !descendant.equals(secondPerson.descendant)) {
            return false;
        }
        if (firstName != null && !firstName.equals(secondPerson.firstName)) {
            return false;
        }
        if (lastName != null && !lastName.equals(secondPerson.lastName)) {
            return false;
        }
        if (gender != null && !gender.equals(secondPerson.gender)) {
            return false;
        }
        if (father != null && !father.equals(secondPerson.father)) {
            return false;
        }
        if (mother != null && !mother.equals(secondPerson.mother)) {
            return false;
        }
        if (spouse != null && !spouse.equals(secondPerson.spouse)) {
            return false;
        }
        if (success != secondPerson.success) {
            return false;
        }
        if (message == null && secondPerson.message != null) {
            return false;
        }
        if (message != null && secondPerson.message == null){
            return false;
        }
        if (message != null && secondPerson.message != null){
            if (!message.equals(secondPerson.message)){
                return false;
            }
        }
        return true;
    }

}
