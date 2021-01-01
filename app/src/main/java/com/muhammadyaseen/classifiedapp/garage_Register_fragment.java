package com.muhammadyaseen.classifiedapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link garage_Register_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class garage_Register_fragment extends Fragment {
    NavController navController;
    EditText name,email,passwords;
   Button register_login,signIn_register;
    private ProgressDialog progressDialog;

    //Data Base Code Started..
    private FirebaseAuth firebaseAuth;
    private  DatabaseReference myRef ;


    String user_register_name,user_register_email,user_register_pass;


    //.................End .......................
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{3,10}" +               //at least 4 characters
                    "$");

    private Pattern Email_Pattern = Pattern.compile( "^(.+)@(.+)$");

    private static final Pattern PASS_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,12}" +               //at least 4 characters
                    "$");




    private boolean validate_name() {

        String usernameInput = name.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            name.setError("Field can't be empty");
            return false;


        } else if (NAME_PATTERN.matcher(usernameInput).matches()) {
            name.setError(null);
            return true;
        } else {
            name.setError("Valid Name charter should b atleat 3 maxmum 10 charter");
            return false;
        }
    }


    private boolean validate_email() {
        String emailInput = email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;

        }
        else if(Email_Pattern.matcher(emailInput).matches()){
            email.setError(null);
            return true;
        }else{
            email.setError("Valid Email");
            return false;
        }

    }
    private  boolean validate_pass() {

        String register_passInput = passwords.getText().toString().trim();

        if (register_passInput.isEmpty()) {
            passwords.setError("Field can't be empty");

            return false;
        }
        else if(PASS_PATTERN.matcher(register_passInput).matches()) {

            return true;
        }
        else {
            passwords.setError("Passwords should be at least one number , small & alphabets Minimum  6 charter up to 12 character ");
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

    public garage_Register_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment garage_Register_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static garage_Register_fragment newInstance(String param1, String param2) {
        garage_Register_fragment fragment = new garage_Register_fragment();
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
        return inflater.inflate(R.layout.fragment_garage__register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        //Create Firebase Auth Initialize
        firebaseAuth=FirebaseAuth.getInstance();
        //.........................End......................
         name=view.findViewById(R.id.name_register);
        email=view.findViewById(R.id.email_register);
        passwords=view.findViewById(R.id.passwords_register);
        progressDialog=new ProgressDialog(getContext());
        signIn_register=view.findViewById(R.id.sign_user);
        register_login=view.findViewById(R.id.register_user);
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_garage_Register_fragment_to_garageSale_login_fragment);
            }
        });
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate_name()&&validate_email()&&validate_pass()){
                    progressDialog.setMessage("Register");
                    progressDialog.show();
                    register_login.setEnabled(false);
                    // ..................................DataBase write code here................................
                    user_register_name= name.getText().toString();
                    user_register_email=email.getText().toString();
                    user_register_pass=passwords.getText().toString();


                    firebaseAuth.createUserWithEmailAndPassword(user_register_email,user_register_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendRegisterData();
                                register_login.setEnabled(false);
                                progressDialog.dismiss();
                                navController.navigate(R.id.action_garage_Register_fragment_to_garageSale_login_fragment);
                            }
                            else {
                                Toast.makeText(getActivity(), "Already Register  ", Toast.LENGTH_SHORT).show();
                                register_login.setEnabled(true);


                            }

                        }
                    });




                }else {
                    progressDialog.dismiss();
                    register_login.setEnabled(true);
                }

            }
        });
        signIn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_garage_Register_fragment_to_garageSale_login_fragment);
            }
        });



    }

//Store Data in Data Base by using this Function
    private void sendRegisterData(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference("RegisterData");
        RegisterData registerData=new RegisterData(user_register_name,user_register_email);
        myRef.child(firebaseAuth.getUid()).setValue(registerData);
    }
//..................End of this code ...........................

}