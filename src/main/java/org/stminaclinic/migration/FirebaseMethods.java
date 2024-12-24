package org.stminaclinic.migration;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FirebaseMethods {

    public static void Connect() {
        try {
            URL resource =
                FirebaseMethods.class.getClassLoader().getResource("<>.json");

            FileInputStream serviceAccount = new FileInputStream(
                resource.getPath()
            );

            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void GetEvents()
        throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("events").get();

        QuerySnapshot querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            System.out.println(document.getId() + " => " + document.getData());
        }
    }

    public static String AddEvent(Object event) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<DocumentReference> addedDocRef = db
                .collection("events")
                .add(event);
            return addedDocRef.get().getId();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String AddPatientToEvent(Object patient, String eventUID) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<DocumentReference> addedDocRef = db
                .collection("events/" + eventUID + "/patients")
                .add(patient);
            return addedDocRef.get().getId();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String AddPatient(String patientKey, Object patient) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> addedDocRef = db
                .collection("patients")
                .document(patientKey)
                .set(patient);
            return addedDocRef.get().getUpdateTime().toString();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void UpdateEvent(String eventId, int patientTotal) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = db
                .collection("events")
                .document(eventId)
                .update("patientTotal", patientTotal);
            System.out.println(
                "Update time : " + writeResult.get().getUpdateTime()
            );
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
        throws IOException, ExecutionException, InterruptedException {
        Connect();
        GetEvents();
        System.out.println("Hello, World!");
    }
}
