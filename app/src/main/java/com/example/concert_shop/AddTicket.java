package com.example.concert_shop;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddTicket extends AppCompatActivity {
    EditText artistEditText, venueEditText, descriptionEditText, priceEditText;
    private Uri imageUri;
    private ImageView imageViewSelected;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ticket);

        artistEditText = findViewById(R.id.editTextArtist);
        venueEditText = findViewById(R.id.editTextVenue);
        descriptionEditText = findViewById(R.id.editTextDescription);
        priceEditText = findViewById(R.id.editTextPrice);
        imageViewSelected = findViewById(R.id.imageViewSelected);
        Button selectImageButton = findViewById(R.id.buttonSelectImage);
        selectImageButton.setOnClickListener(v -> selectImage());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewSelected.setImageURI(imageUri);
        }
    }

    public void submitTicketClicked(View view) {
        String artist = artistEditText.getText().toString().trim();
        String venue = venueEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        if (artist.isEmpty() || venue.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (description.length() > 25) {
            Toast.makeText(this, "Description is too long, keep it shorter!", Toast.LENGTH_SHORT).show();
            return;
        }

        uploadImageToStorage(artist, venue, description, priceStr);
    }

    private void uploadImageToStorage(String artist, String venue, String description, String price) {
        if (imageUri != null) {
            StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        saveTicket(artist, venue, description, price, imageUrl);
                    }))
                    .addOnFailureListener(e -> Toast.makeText(AddTicket.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            saveTicket(artist, venue, description, price, null);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void saveTicket(String artist, String venue, String description, String price, String imageUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Ticket newTicket = new Ticket(artist, venue, description, price + " usd", imageUrl);
        db.collection("Tickets").add(newTicket)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Ticket added successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Mainpage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save ticket: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }


    public void cancelButtonClicked(View view) {
        finish();
    }
}
