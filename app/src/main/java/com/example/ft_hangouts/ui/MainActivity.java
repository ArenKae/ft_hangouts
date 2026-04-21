package com.example.ft_hangouts.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.ft_hangouts.R;
import com.example.ft_hangouts.model.Contact;
import com.example.ft_hangouts.data.ContactRepository;

import java.util.List;


// When a top-level class is made public, it can be imported
// from any package. Otherwise, it's package-private.
public class MainActivity extends AppCompatActivity {

    // Print the database for debug
    private void debugPrintContacts(ContactRepository repository) {
        List<Contact> contacts = repository.getAllContacts();

        Log.d("MainActivity", "----- CONTACTS IN DB: " + contacts.size() + " -----");

        for (Contact c : contacts) {
            Log.d("MainActivity",
                    "id=" + c.getId()
                            + ", name=" + c.getDisplayName()
                            + ", phone=" + c.getPhoneNumber()
                            + ", email=" + c.getEmail()
                            + ", nickname=" + c.getNickname());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     // Call the onCreate() method from the parent class AppCompatActivity
        EdgeToEdge.enable(this);
        // R is a class auto-created by android to store the app's main data.
        // It allows linkage between the .xml files, the resources, and the java code.
        setContentView(R.layout.activity_main);     // Load the main layout from the .xml file.

        FloatingActionButton addNewContact = findViewById(R.id.add_button); // Floating "Add contact" button
        addNewContact.setOnClickListener(v -> {     // Attach an event listener
            Log.d("MainActivity", "Add contact");
        });

        // Test contact in database
        ContactRepository repository = new ContactRepository(this);
        debugPrintContacts(repository);


        // Handle padding to fit the system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}