package org.stminaclinic.migration;


import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.SQLException;

public class MergeMethods {

    public static ArrayList<ClinicEvent> getEvents(Statement statement) throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery(
                "select * from clinic ORDER BY id DESC");
        ArrayList<ClinicEvent> events = new ArrayList<>();
        while (resultSet.next()) {
            EventType eventType = resultSet.getBoolean("psychiatrist") ? EventType.BEHAVIORAL : EventType.CLINIC;
            events.add(
                    new ClinicEvent(
                        eventType,
                        resultSet.getInt("desiredAttendance"),
                        resultSet.getDate("date"),
                        resultSet.getInt("id")
                    )
            );
        }
        resultSet.close();
        return events;
    }

    public static ArrayList<Patient> getPatients(Statement statement) throws SQLException, ParseException {
        ResultSet resultSet;
        resultSet = statement.executeQuery(
                "select * from patients ORDER BY clinicId DESC");
        ArrayList<Patient> patients = new ArrayList<>();
        while (resultSet.next()) {
            patients.add(
                    new Patient(
                            resultSet.getInt("id"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("dateOfBirth"),
                            resultSet.getInt("age"),
                            resultSet.getString("name"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getInt("refills"),
                            resultSet.getBoolean("online"),
                            resultSet.getString("contactType"),
                            resultSet.getInt("clinicId"),
                            resultSet.getBoolean("isNew"),
                            resultSet.getString("address"),
                            resultSet.getString("city"),
                            resultSet.getString("zipcode"),
                            resultSet.getString("generalQuestion")
                    )
            );
        }
        resultSet.close();
        return patients;
    }
}
