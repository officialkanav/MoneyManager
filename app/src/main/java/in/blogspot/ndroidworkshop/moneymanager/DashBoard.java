package in.blogspot.ndroidworkshop.moneymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.res.Resources;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashBoard extends AppCompatActivity {

    private ListView mListView;
    private EditText productView;
    private EditText amountView;
    private Button AddButton;
    private DatabaseReference mDatabaseReference;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        setupDisplayName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mListView = (ListView) findViewById(R.id.listViewID);
        productView = (EditText) findViewById(R.id.Product_ID);
        amountView = (EditText) findViewById(R.id.price_ID);
        AddButton = (Button) findViewById(R.id.add_button);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaction();
            }
        });
    }

    private void setupDisplayName(){

    }

    private void addTransaction(){
        String product = productView.getText().toString();
        String cost = amountView.getText().toString();
        if (!product.equals("") && !cost.equals("")) {
            ListStructure obj = new ListStructure(product,cost,"User");
            mDatabaseReference.child("messages").push().setValue(obj);
            productView.setText("");
            amountView.setText("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new ListAdapter(this, mDatabaseReference, "User");
        mListView.setAdapter(mAdapter);
    }
}

