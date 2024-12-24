package org.stminaclinic.migration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClinicEvent {
    EventType type;
    int desiredAttendance;
    Date date;
    int id;
    String uid;

    public ClinicEvent(EventType eventType, int desiredAttendance, java.sql.Date date, int id) {
        this.type = eventType;
        this.desiredAttendance = desiredAttendance;
        this.date = date;
        this.id = id;
    }


    public void setUID(String uid) {
        this.uid = uid;
    };

    public Map<String, Object> getObject() {
        return Map.of(
                "desiredAttendance", this.desiredAttendance,
                "date", this.date,
                "id", this.id
        );
    }

    public Map<String, Object> getObj() {
        Map<String, Object> docData = new HashMap<>();
        docData.put("desiredAttendance", this.desiredAttendance);
        docData.put("date", this.date);
        docData.put("id", this.id);
        docData.put("limit", 10);
        if (EventType.BEHAVIORAL == this.type) {
            docData.put("type", "Behavior");
        } else {
            docData.put("type", "General");
        }
        return docData;
    }

    public String toString() {
        if (this.type == EventType.BEHAVIORAL) {
            return "Behavioral " + this.id + " " + this.date.toString();
        }
        return "Clinic " + this.id + " " + this.date.toString();
    }

}
