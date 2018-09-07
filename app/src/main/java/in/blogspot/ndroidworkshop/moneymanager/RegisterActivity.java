package in.blogspot.ndroidworkshop.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        name = (EditText) findViewById(R.id.regieterName_ID);
        email = (EditText) findViewById(R.id.registerEmail_ID);
        password = (EditText) findViewById(R.id.registerPassword_ID);
        confirmPassword = (EditText) findViewById(R.id.registerConfirmPassword_ID);
        registerButton = (Button) findViewById(R.id.registeringButton_ID);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegisteration();
            }
        });
    }



    private void attemptRegisteration(){
        email.setError(null);
        password.setError(null);
        confirmPassword.setError(null);
        name.setError(null);

        if(email.getText().toString() == ""){
            email.setError("Cannot Be Empty");
        }

        if(name.getText().toString().equals(""))
            name.setError("Cannot be Empty");

        if(password.getText().toString() == "")
            password.setError("Cannot be Empty");

        if(confirmPassword.getText().toString() =="" )
            confirmPassword.setError("Cannot be Empty");

        if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".com")){
            email.setError("Invalid Email");
        }

        if(password.getText().toString().length()<=5)
            password.setError("password too short");

        if(!confirmPassword.getText().toString().equals(password.getText().toString()))
            confirmPassword.setError("Passwords do not match");
        else{
            createFirebaseUser();
        }
    }

    private void createFirebaseUser(){
        Log.d("MoneyManager","createFirebaseUser()");
        String email_text = email.getText().toString();
        String password_text = password.getText().toString();
        String name_text = name.getText().toString();

        mAuth.createUserWithEmailAndPassword(email_text,password_text).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast toast = Toast.makeText(RegisterActivity.this,"Registeration Failed",Toast.LENGTH_SHORT);
                    toast.show();
                    Log.d("MoneyManager",""+task.getException());
                }
                else{
                    saveDisplayName();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void saveDisplayName(){

    }
}
