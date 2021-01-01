package com.muhammadyaseen.classifiedapp;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    NavController navController;
    ImageView post_image,img_post_android;

    EditText input_title, input_description, input_price, input_country, input_state_province, input_city, input_number;
    Button btn_post, chooseImgbtn;
    //Img URI
    private Uri imgUri;

    private static final String FB_STORAGE_PATH = "image/*";
    private static final String FB_DataBase_PATH = "image";
    private static final int REQUEST_CODE = 1234;
    //Storage Reference
    private StorageReference mStorageRef;
    //Real-Time DataBase Reference
    private DatabaseReference mDataBaseRef;

    private ProgressDialog progressDialog;
    private StorageTask mUploadTask;

//.....................................Data Base Code Value...................................................


    //.................................Validation Code....................................
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{3,50}" +               //at least 4 characters
                    "$");


    private static final Pattern DESCRIPTION_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{3,500}" +               //at least 4 characters
                    "$");

    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    // "(?=\\S+$)" +           //no white spaces
                    ".{11,11}" +               //at least 4 characters
                    "$");


    private static final Pattern PRICE_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    // "(?=\\S+$)" +           //no white spaces
                    ".{1,10000}" +               //at least 4 characters
                    "$");


    private boolean validate_Tittle() {

        String usernameInput = input_title.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            input_title.setError("Field can't be empty");
            return false;


        } else if (NAME_PATTERN.matcher(usernameInput).matches()) {
            input_title.setError(null);
            return true;
        } else {
            input_title.setError("Valid name character should b atleast  3 maximum 50 character");
            return false;
        }
    }

    private boolean validate_Description() {

        String postDescription = input_title.getText().toString().trim();
        if (postDescription.isEmpty()) {
            input_title.setError("Field can't be empty");
            return false;


        } else if (DESCRIPTION_PATTERN.matcher(postDescription).matches()) {
            input_title.setError(null);
            return true;
        } else {
            input_title.setError(" Minimum 3 character and maximum 500 character ");
            return false;
        }
    }


    private boolean validate_City() {

        String cityInput = input_city.getText().toString().trim();
        if (cityInput.isEmpty()) {
            input_city.setError("Field can't be empty");
            return false;
        } else if (NAME_PATTERN.matcher(cityInput).matches()) {
            input_city.setError(null);
            return true;
        } else {
            input_city.setError("Valid City");
            return false;
        }

    }



    private boolean validate_phonenumber() {


        String numberInput = input_number.getText().toString().trim();
        if (numberInput.isEmpty()) {
            input_number.setError("Field can't be empty");
            return false;
        } else if (NUMBER_PATTERN.matcher(numberInput).matches()) {
            input_number.setError(null);
            return true;
        } else {
            input_number.setError("Valid Number");
            return false;
        }

    }

    private boolean validate_price() {

        String priceInput = input_price.getText().toString().trim();
        if (priceInput.isEmpty()) {
            input_number.setError("Field can't be empty");
            return false;
        } else if (PRICE_PATTERN.matcher(priceInput).matches()) {
            input_price.setError(null);
            return true;
        } else {
            input_price.setError("valid Price");
            return false;
        }

    }


    private boolean validate_Country() {

        String countryInput = input_country.getText().toString().trim();
        if (countryInput.isEmpty()) {
            input_country.setError("Field can't be empty");
            return false;
        } else if (NAME_PATTERN.matcher(countryInput).matches()) {
            input_country.setError(null);
            return true;
        } else {
            input_country.setError("Valid Country");
            return false;
        }

    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        progressDialog = new ProgressDialog(getContext());
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference(FB_DataBase_PATH);


        progressDialog.setMessage("Images Uploading please wait ....");
        post_image = view.findViewById(R.id.img_post);
        img_post_android=view.findViewById(R.id.img_post_android);
        img_post_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

                post_image.setVisibility(View.VISIBLE);


            }
        });

        input_title = view.findViewById(R.id.input_title);
        input_description = view.findViewById(R.id.input_description);
        input_price = view.findViewById(R.id.input_price);
        input_country = view.findViewById(R.id.input_country);
        input_city = view.findViewById(R.id.input_city);
        input_number = view.findViewById(R.id.input_number);
        btn_post = view.findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                    btn_post.setEnabled(false);

                } else {
                    upLoadFile();
                }

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            /*try {

                Bitmap bm= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imgUri);
                post_image.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

            Picasso.get().load(imgUri).into(post_image);
            post_image.setImageURI(imgUri);
        }

    }

    public String getImgExt(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType(FB_STORAGE_PATH);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), REQUEST_CODE);
        img_post_android.setVisibility(View.INVISIBLE);
    }

    public void upLoadFile() {
if(imgUri!=null && validate_Tittle() && validate_Description() && validate_price() &&
        validate_Country()  && validate_City() && validate_phonenumber() ){
        StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImgExt(imgUri));
        progressDialog.setTitle(" Post Uploading in Progress.....");
        progressDialog.show();
        mUploadTask=ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), " Post Uploaded ", Toast.LENGTH_SHORT).show();
                // save images in FireBase Data_base
                UploadPost uploadPost = new UploadPost(
                        taskSnapshot.getUploadSessionUri().toString(),
                        input_title.getText().toString().trim(),
                        input_description.getText().toString().trim(),
                        input_price.getText().toString().trim(),
                        input_country.getText().toString().trim(),
                        input_city.getText().toString().trim(),
                        input_number.getText().toString().trim());

                String uploadId = mDataBaseRef.push().getKey();
                mDataBaseRef.child(uploadId).setValue(uploadPost);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                // show up_Load Progress
                double progress = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded" + (int) progress + "%");
            }
        });


    }
          else{

             progressDialog.dismiss();
             img_post_android.setVisibility(View.VISIBLE);

        }

        }
    }
