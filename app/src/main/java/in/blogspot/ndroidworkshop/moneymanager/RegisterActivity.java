package in.blogspot.ndroidworkshop.moneymanager;

import android.app.Activity;
import android.app.AlertDialog;
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

        int flag = 0;

        if(email.getText().toString() == ""){
            email.setError("Cannot Be Empty");
            flag = -1;
        }

        if(name.getText().toString().equals("")) {
            name.setError("Cannot be Empty");
            flag = -1;
        }

        if(password.getText().toString() == "") {
            flag = -1;
            password.setError("Cannot be Empty");
        }

        if(confirmPassword.getText().toString() =="" ) {
            flag = -1;
            confirmPassword.setError("Cannot be Empty");
        }

        if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".com") || email.getText().toString().contains(" ")){
            flag = -1;
            email.setError("Invalid Email");
        }

        if(password.getText().toString().length()<=5) {
            password.setError("length of password<6");
            flag = -1;
        }
        if(confirmPassword.getText().toString()!= null && password.getText().toString()!= null){
            if(!confirmPassword.getText().toString().equals(password.getText().toString())) {
                flag = -1;
                confirmPassword.setError("Passwords do not match");
            }
        }

        if(flag==0){

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

                    String exception = task.getException().toString();
                    int i;
                    for(i = 0; i<exception.length(); i++){
                        if(exception.charAt(i) == ':')
                            break;
                    }
                    exception = exception.substring(i+1);
                    new android.support.v7.app.AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Registeration Failed!")
                            .setMessage(exception)
                            .setPositiveButton(android.R.string.ok,null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
//                    Toast toast = Toast.makeText(RegisterActivity.this,"Registeration Failed",Toast.LENGTH_SHORT);
//                    toast.show();
                    Log.d("MoneyManager",""+task.getException());
                }
                else{
                    saveDisplayName();
                    Toast toast = Toast.makeText(RegisterActivity.this,"In Progress...",Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(RegisterActivity.this, DashBoard.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void saveDisplayName(){
        //Method to save name as it is not sent to the server
    }
}
