package org.stminaclinic.migration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        Connection connection = DBConnection.CreateConnection();
        Statement statement = connection.createStatement();
        ArrayList<ClinicEvent> events =  MergeMethods.getEvents(statement);
        ArrayList<Patient> patients = MergeMethods.getPatients(statement);
        HashMap<String, ArrayList<String>> patientEvents = new HashMap<>();

        FirebaseMethods.Connect();
        for (ClinicEvent event : events) {
            String ref = FirebaseMethods.AddEvent(event.getObj());
            event.setUID(ref);
            System.out.println("--- Adding Patients to Event ---");
            AtomicInteger count = new AtomicInteger();
            patients.stream().filter(p -> p.clinicId == event.id).forEach(p -> {
                if (p.getKey() != null) {
                    if (patientEvents.containsKey(p.getKey())) {
                        patientEvents.get(p.getKey()).add(ref);
                    } else {
                        ArrayList<String> eventsList = new ArrayList<>();
                        eventsList.add(ref);
                        patientEvents.put(p.getKey(), eventsList);
                    }
                }
                count.getAndIncrement();
                FirebaseMethods.AddPatientToEvent(p.getObject(ref), ref);
            });
            FirebaseMethods.UpdateEvent(ref, count.get());
            System.out.println("----------");
        }

        System.out.println("Completed Adding Patients to events");

        for (Patient patient : patients) {
            if (patient.getKey() != null) {
                if (patientEvents.containsKey(patient.getKey())) {
                    String uid = FirebaseMethods.AddPatient(patient.getKey(), patient.getObjectWithEventList(patientEvents.get(patient.getKey())));
                    System.out.println("Added Patient: " + uid + " - " + patient.getKey());
                }
            }
        }

        statement.close();
        connection.close();
    }
}