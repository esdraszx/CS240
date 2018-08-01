package Model;

/**
 *An Person model object which represents a person in javacode, contains the information to fully represent a person in the database
 *<pre>
 *<b>Domain</b>:
 *     personID : string
 *     descendant : string
 *     firstName : string
 *     lastName : string
 *     gender : string
 *     father : string
 *     mother : string
 *     spouse : string
 *
 *</pre>
 */
public class PersonModel {

    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

    /**
     *The only constructor for PersonModel
     *<pre>
     *<b>Constraints on the input</b>:
     *  none
     *</pre>
     */
    public PersonModel(){
        //To be implemented later

    }

    public PersonModel(UserModel u){
        this.personID = u.getPersonID();
        this.descendant = u.getUserName();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.gender = u.getGender();
        father = new String();
        mother = new String();
        spouse = new String();
    }

    public String getPersonID() {
        return personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getDescription(){
        String out = new String(firstName + " " + lastName);
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

        PersonModel secondPerson = (PersonModel) o;

        if (!personID.equals(secondPerson.personID)) {
            return false;
        }
        if (!descendant.equals(secondPerson.descendant)) {
            return false;
        }
        if (!firstName.equals(secondPerson.firstName)) {
            return false;
        }
        if (!lastName.equals(secondPerson.lastName)) {
            return false;
        }
        if (!gender.equals(secondPerson.gender)) {
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
        return true;
    }

}
