package org.stminaclinic.migration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Patient {
    int id;
    String firstName;
    String lastName;
    Date dateOfBirth;
    int age;
    String name;
    String phone;
    String email;
    int refills;
    boolean online;
    String contactType;
    int clinicId;
    boolean isNew;
    String address;
    String city;
    String zipcode;
    String generalQuestion;

    public Patient(int id, String firstName, String lastName, String dateOfBirth, int age, String name, String phone,
                   String email, int refills, boolean online, String contactType, int clinicId, boolean isNew,
                   String address, String city, String zipcode, String generalQuestion) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("MMMM d, yyyy");
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth != null ? parser.parse(dateOfBirth) : null;
        this.age = age;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.refills = refills;
        this.online = online;
        this.contactType = contactType;
        this.clinicId = clinicId;
        this.isNew = isNew;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.generalQuestion = generalQuestion;
    }

    public String getKey() {
        if (this.phone == null) {
            return null;
        }
        if (this.phone.length() < 10) {
            return null;
        }
        if (this.firstName == null || this.lastName == null) {
            return null;
        }
        return this.firstName.split(" ")[0].toLowerCase() + "-" + this.lastName.split(" ")[0].toLowerCase() + "-" + this.phone.replaceAll("[\\s()-]", "");
    }


    public Map<String, Object> getObject(String eventId) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("firstName", this.firstName);
        docData.put("lastName", this.lastName);
        docData.put("dob", this.dateOfBirth);
        docData.put("name", this.name);
        docData.put("phone", this.phone);
        docData.put("email", this.email != null ? this.email.toLowerCase() : "");
        docData.put("isNewPatient", this.isNew ? "Yes" : "No");
        docData.put("latestEventId", eventId);
        docData.put("address", this.address);
        docData.put("city", this.city);
        docData.put("zipcode", this.zipcode);
        docData.put("officeStaffOnly", this.generalQuestion);
        return docData;
    }

    public Map<String, Object> getObjectWithEventList(ArrayList<String> eventList) {
        Map<String, Object> docData = getObject(eventList.get(eventList.size() - 1));
        docData.put("events", eventList);
        return docData;
    }

    public String toString() {
        return this.firstName + " " + this.lastName + " " + (this.dateOfBirth != null ?this.dateOfBirth.toString() : "null");
    }

}
