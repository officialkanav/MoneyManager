package in.blogspot.ndroidworkshop.moneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
}
