package Request;
import Model.UserModel;
import Model.PersonModel;
import Model.EventModel;


/**
 *An Load Request object which represents a load Request in javacode, contains all the users, persons, and events to be put into the database
 *<pre>
 *<b>Domain</b>:
 *     users : UserModel[]
 *     persons : PersonModel[]
 *     events : EventModel[]
 *</pre>
 */
public class LoadRequest {

    private UserModel[] users;
    private PersonModel[] persons;
    private EventModel[] events;

    public LoadRequest(){
        //To be implemented later
    }

    public UserModel[] getUsers(){
        return users;
    }

    public PersonModel[] getPersons(){
        return persons;
    }

    public EventModel[] getEvents(){
        return events;
    }

    public void setUsers(UserModel[] input){
        users = new UserModel[input.length];
        for (int i = 0; i < input.length; i++){
            users[i] = input[i];
        }
    }

    public void setPersons(PersonModel[] input){
        persons = new PersonModel[input.length];
        for (int i = 0; i < input.length; i++){
            persons[i] = input[i];
        }
    }

    public void setEvents(EventModel[] input){
        events = new EventModel[input.length];
        for (int i = 0; i < input.length; i++){
            events[i] = input[i];
        }
    }

}
