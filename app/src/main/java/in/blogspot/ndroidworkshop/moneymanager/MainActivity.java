package in.blogspot.ndroidworkshop.moneymanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailTextField;
    private EditText passwordTextField;
    private Button signInButton;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailTextField = (EditText) findViewById(R.id.loginEmail_ID);
        passwordTextField = (EditText) findViewById(R.id.loginPassword_ID);
        signInButton = (Button) findViewById(R.id.loginButton_ID);
        registerButton = (Button) findViewById(R.id.registerButton_ID);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });
    }

    private void startRegisterActivity(){
        Intent intent = new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void attemptSignIn(){
        String email = emailTextField.getText().toString();
        final String password = passwordTextField.getText().toString();

        emailTextField.setError(null);
        passwordTextField.setError(null);

        if(email != null && (!email.contains("@") || !email.contains(".com") || email.contains(" ")))
            emailTextField.setError("Invalid Email");
        else{
            if(email == null || password == null || password .equals("") || email.equals("")){
                if(password.equals("") || password == null)
                    passwordTextField.setError("Cannot be empty");

                if(email == "")
                    emailTextField.setError("Cannot be Empty");
            }
            else{
                if(!email.equals("") && !password.equals("")){
                    Log.d("MoneyManager","Inside else,email,password"+email+password);
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Oops")
                                        .setMessage("Problem Signing in, check email and password")
                                        .setPositiveButton(android.R.string.ok,null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                                emailTextField.setText("");
                                passwordTextField.setText("");
                            }
                            else{
                                Intent intent = new Intent(MainActivity.this,DashBoard.class);
                                finish();
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        }

    }
}
