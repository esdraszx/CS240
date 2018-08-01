package com.example.client;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.EventModel;
import Model.PersonModel;

public class ClientModel {

    private static ClientModel myObj;

    private Map<String, EventModel> eventMap;
    private Map<String,PersonModel> peopleMap;
    private Map<String, List<EventModel>> peopleEventMap;
    private PersonModel user;
    private Set<PersonModel> paternalAncestors;
    private Set<PersonModel> maternalAncestors;
    private List<String> eventTypesForUser;
    private List<String> eventTypesForFemaleAncestors;
    private List<String> eventTypesForMaleAncestors;
    private List<String> allEventTypes;
    private Map<String, List<PersonModel>> childrenMap;
    private String authToken;
    private boolean loggedIn = false;
    private boolean showFemaleEvents = true;
    private boolean showMaleEvents = true;




    /**
     * Create private constructor
     */
    private ClientModel() {
        peopleMap = new HashMap<>();
        peopleEventMap = new HashMap<>();
        eventMap = new HashMap<>();
        user = new PersonModel();
        paternalAncestors = new HashSet<>();
        maternalAncestors = new HashSet<>();
        eventTypesForUser= new ArrayList<>();
        eventTypesForFemaleAncestors = new ArrayList<>();
        eventTypesForMaleAncestors = new ArrayList<>();
        childrenMap = new HashMap<>();
        authToken = new String();
        loggedIn = false;
    }

    /**
     * Create a static method to get instance.
     */
    public static ClientModel getInstance() {
        if (myObj == null) {
            myObj = new ClientModel();
        }
        return myObj;
    }

    public void setPeople(PersonModel[] input) {
        user = input[0];

        for (int i = 0; i < input.length; i++){
            peopleMap.put(input[i].getPersonID(), input[i]);
        }

        createPaternalAndMaternalSets();
    }

    public void createPaternalAndMaternalSets(){
        PersonModel motherOfUser = peopleMap.get(user.getMother());
        PersonModel fatherOfUser = peopleMap.get(user.getFather());


        addParentsToOneSetOrTheOther(motherOfUser,true);
        addParentsToOneSetOrTheOther(fatherOfUser,false);


        for (Map.Entry<String, PersonModel> entry : peopleMap.entrySet()) {
            String possibleParentPersonID = new String(entry.getValue().getPersonID());
            List<PersonModel> childrenOfPossible = new ArrayList<>();

            for(Map.Entry<String, PersonModel> entryTwo : peopleMap.entrySet()){
                if (possibleParentPersonID.equals(entryTwo.getValue().getFather()) || possibleParentPersonID.equals(entryTwo.getValue().getMother())){
                    childrenOfPossible.add(entryTwo.getValue());
                }
            }
            childrenMap.put(possibleParentPersonID, childrenOfPossible);
        }
    }

    private void addParentsToOneSetOrTheOther(PersonModel personReceived, Boolean isMaternalSide){
        if (isMaternalSide){
            maternalAncestors.add(personReceived);
        } else {
            paternalAncestors.add(personReceived);
        }

        if (personReceived.getFather() == null || personReceived.getFather().equals("")){ //baseCase
            return;
        } else {
            if (isMaternalSide){
                PersonModel motherOfPersonReceived = peopleMap.get(personReceived.getMother());
                PersonModel fatherOfPersonReceived = peopleMap.get(personReceived.getFather());
                addParentsToOneSetOrTheOther(motherOfPersonReceived, true); //true indicates this person belongs to maternal set
                addParentsToOneSetOrTheOther(fatherOfPersonReceived, true);
            } else {
                PersonModel motherOfPersonReceived = peopleMap.get(personReceived.getMother());
                PersonModel fatherOfPersonReceived = peopleMap.get(personReceived.getFather());
                addParentsToOneSetOrTheOther(motherOfPersonReceived,false);
                addParentsToOneSetOrTheOther(fatherOfPersonReceived,false);
            }
        }
    }

    public void setEvents(EventModel[] input){

        for (int i = 0; i < input.length; i++){

            eventMap.put(input[i].getEventID(), input[i]);

            if (peopleEventMap.containsKey(input[i].getPersonID())){ //we know that the List<EventModel> associated with the person exists
                //lets get it
                List<EventModel> eventListFromMap = peopleEventMap.get(input[i].getPersonID());

                if (input[i].getYear() < eventListFromMap.get(0).getYear()){

                    eventListFromMap.add(0, input[i]);
                } else if (input[i].getYear() > eventListFromMap.get(eventListFromMap.size()-1).getYear()){
                    eventListFromMap.add(input[i]);
                } else {
                    for (int j = 0; j < eventListFromMap.size() - 1; ++j){
                        if ((input[i].getYear() > eventListFromMap.get(j).getYear()) && (!(input[i].getYear() > eventListFromMap.get(j+1).getYear()))){
                            eventListFromMap.add(j+1, input[i]);
                        }
                    }
                }
                peopleEventMap.put(input[i].getPersonID(), eventListFromMap);
            }
            else { // The list<EventModel> doesn't exist so this event is the first one associated with the person
                List<EventModel> in = new ArrayList<>();
                in.add(input[i]);
                peopleEventMap.put(input[i].getPersonID(), in);
            }
        } //By here we've made a map of personID to event type and events are sorted already! :D

        createEventLists();
    }

    public void createEventLists(){

        Boolean handledMaleCase = false;
        Boolean handledFemaleCase = false;
        Boolean handledUserCase = false;

        for (Map.Entry<String, List<EventModel>> entry: peopleEventMap.entrySet()){
            if (handledFemaleCase && handledMaleCase && handledUserCase) {
                break;
            } else if(entry.getKey().equals(user.getPersonID())){
                for (int i = 0; i < entry.getValue().size(); i++){ //initializes users event types
                    eventTypesForUser.add(entry.getValue().get(i).getType().toLowerCase());
                }
                handledUserCase = true;
            } else if (peopleMap.get(entry.getKey()).getGender().equals("f") && !handledFemaleCase) { //initiliziaes female event types
                for (int i = 0; i < entry.getValue().size(); i++){
                    eventTypesForFemaleAncestors.add(entry.getValue().get(i).getType().toLowerCase());
                }
                handledFemaleCase = true;
            } else if (peopleMap.get(entry.getKey()).getGender().equals("m") && !handledMaleCase){
                for (int i = 0; i < entry.getValue().size(); i++){  //initiliazes male event types
                    eventTypesForMaleAncestors.add(entry.getValue().get(i).getType().toLowerCase());
                }
                handledMaleCase = true;
            }

        }
    }

    public List<PersonModel> findParentsByPersonID(PersonModel potentialChild){
        List<PersonModel> parents = new ArrayList<>();

        for (Map.Entry<String, List<PersonModel>> entryTwo: childrenMap.entrySet()){
            String possibleParent = new String(entryTwo.getKey());
            List<PersonModel> children = entryTwo.getValue();

            for (int i = 0; i < children.size(); i++){
                if (potentialChild.getPersonID().equals(children.get(i).getPersonID())){
                    parents.add(peopleMap.get(possibleParent));
                }
            }
        }
        return parents;
    }

    public PersonModel findSpouseByPersonID(PersonModel potentialSpouse){
        PersonModel foundSpouse = new PersonModel();

        for (Map.Entry<String, PersonModel> entry: peopleMap.entrySet()){
            if (entry.getValue().getSpouse().equals(potentialSpouse.getPersonID())){
                return entry.getValue();
            }
        }
        return foundSpouse;
    }


    public Map<String,PersonModel> getPeopleMap(){
        return peopleMap;
    }

    public PersonModel getPersonById(String id){
        return peopleMap.get(id);
    }

    public Map<String, List<EventModel>> getPeopleEventMap() {
        return peopleEventMap;
    }

    public Map<String, EventModel> getEventMap(){
        return eventMap;
    }

    public List<EventModel> getEventsOfPersonByPersonId(String id){
        return peopleEventMap.get(id);
    }

    public EventModel getEventById(String id){
        return eventMap.get(id);
    }

    public PersonModel getUser(){
        return user;
    }

    public Set<PersonModel> getMaternalAncestors(){
        return maternalAncestors;
    }

    public Set<PersonModel> getPaternalAncestors(){
        return paternalAncestors;
    }

    public List<String> getEventTypesForUser() {
        return eventTypesForUser;
    }

    public List<String> getEventTypesForFemaleAncestors() {
        return eventTypesForFemaleAncestors;
    }

    public List<String> getEventTypesForMaleAncestors() {
        return eventTypesForMaleAncestors;
    }

    public Map<String, List<PersonModel>> getChildrenMap(){
        return childrenMap;
    }

    public void setEventMap(Map<String, EventModel> eventMap) {
        this.eventMap = eventMap;
    }

    public void setPeopleMap(Map<String, PersonModel> peopleMap) {
        this.peopleMap = peopleMap;
    }

    public void setPeopleEventMap(Map<String, List<EventModel>> peopleEventMap) {
        this.peopleEventMap = peopleEventMap;
    }

    public void setUser(PersonModel user) {
        this.user = user;
    }

    public void setPaternalAncestors(Set<PersonModel> paternalAncestors) {
        this.paternalAncestors = paternalAncestors;
    }

    public void setMaternalAncestors(Set<PersonModel> maternalAncestors) {
        this.maternalAncestors = maternalAncestors;
    }

    public void setEventTypesForUser(List<String> eventTypesForUser) {
        this.eventTypesForUser = eventTypesForUser;
    }

    public void setEventTypesForFemaleAncestors(List<String> eventTypesForFemaleAncestors) {
        this.eventTypesForFemaleAncestors = eventTypesForFemaleAncestors;
    }

    public void setEventTypesForMaleAncestors(List<String> eventTypesForMaleAncestors) {
        this.eventTypesForMaleAncestors = eventTypesForMaleAncestors;
    }

    public void setChildrenMap(Map<String, List<PersonModel>> childrenMap) {
        this.childrenMap = childrenMap;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public List<String> getAllEventTypes() {
        return allEventTypes;
    }

    public void setAllEventTypes(List<String> allEventTypes) {
        this.allEventTypes = allEventTypes;
    }

    public boolean isShowMaleEvents() {
        return showMaleEvents;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        this.showMaleEvents = showMaleEvents;
    }

    public boolean isShowFemaleEvents() {
        return showFemaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        this.showFemaleEvents = showFemaleEvents;
    }


    public void clearClientForTesting() {
        eventMap.clear();
        eventMap = null;
        peopleMap.clear();
        peopleMap = null;
        peopleEventMap.clear();
        peopleEventMap = null;
        user = null;
        paternalAncestors.clear();
        paternalAncestors = null;
        maternalAncestors.clear();
        maternalAncestors = null;
        eventTypesForUser.clear();
        eventTypesForUser = null;
        eventTypesForFemaleAncestors.clear();
        eventTypesForFemaleAncestors = null;
        eventTypesForMaleAncestors.clear();
        eventTypesForMaleAncestors = null;
        childrenMap.clear();
        childrenMap = null;
        authToken = null;
        myObj = null;
    }

}