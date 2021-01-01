package com.muhammadyaseen.classifiedapp;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link garageSale_login_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class garageSale_login_fragment extends Fragment {
    private NavController navController;
    Button register_login,signIn_login;
    EditText email,passwords;
    TextView forget_passwords_login;
    private ProgressDialog progressDialog;
       //Data Base code Here
    private FirebaseAuth firebaseAuth;
    String login_register_email,login_register_pass;
//......................... End Data Base Code ...........................

    //If User Enter Five time Wrong passwords email then Count 5 times then go back to register Fragment
    private int counter=5;

//........................... Validation Code .............................
    private    Pattern Email_Pattern = Pattern.compile( "^(.+)@(.+)$");
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

        String passInput = passwords.getText().toString().trim();

        if (passInput.isEmpty()) {
            passwords.setError("Field can't be empty");
            return false;
        }
        else if(PASS_PATTERN.matcher(passInput).matches()) {
            return true;
        }
        else {
            passwords.setError("Passwords should be at least one number , small & alphabets Minimum  6 charter up to 12 character ");
            return false;
        }

    }
//.....................................End of Validation Code ..........................................

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public garageSale_login_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment garageSale_login_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static garageSale_login_fragment newInstance(String param1, String param2) {
        garageSale_login_fragment fragment = new garageSale_login_fragment();
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
        return inflater.inflate(R.layout.fragment_garage_sale_login_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        firebaseAuth=FirebaseAuth.getInstance();
        email=view.findViewById(R.id.email_login);
        passwords=view.findViewById(R.id.passwords_login);
        progressDialog=new ProgressDialog(getContext());
        signIn_login=view.findViewById(R.id.signIn_login);
        register_login=view.findViewById(R.id.register_login);

        forget_passwords_login=view.findViewById(R.id.forget_passwords_login);
        forget_passwords_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_garageSale_login_fragment_to_forget_Fragment);
            }
        });

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_garageSale_login_fragment_to_garage_Register_fragment);
            }
        });
        signIn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate_email()&&validate_pass()){
                    progressDialog.setMessage(" Processing ...  ");
                    progressDialog.show();
                    signIn_login.setEnabled(false);
                    login_register_email=email.getText().toString();
                    login_register_pass=passwords.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(login_register_email,login_register_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                           progressDialog.dismiss();
                           signIn_login.setEnabled(false);
                           Intent garageSale=new Intent(getActivity(),garageSaleHome.class);
                           startActivity(garageSale);
                           getActivity();


                       }else{
                           Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                           signIn_login.setEnabled(true);

                           progressDialog.dismiss();
                       }
                        }
                    });


                }
                else {

                        progressDialog.dismiss();
                        signIn_login.setEnabled(true);
                }

            }
        });

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_garageSale_login_fragment_to_garage_Register_fragment);



            }
        });
    }
}