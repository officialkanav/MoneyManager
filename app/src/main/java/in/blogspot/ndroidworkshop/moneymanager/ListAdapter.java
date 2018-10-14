package in.blogspot.ndroidworkshop.moneymanager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;
    static public String date;

    private static class ViewHolder{
        TextView name_of_product;
        TextView price;
        LinearLayout.LayoutParams params;
    }

    public ListAdapter(Activity activity,DatabaseReference ref, String name,String datestring){
        mActivity = activity;
        mDisplayName = name;
        // common error: typo in the db location. Needs to match what's in MainChatActivity.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = ref.child(user.getUid());
        mDatabaseReference.addChildEventListener(mListener);
        date = datestring;
        mSnapshotList = new ArrayList<>();
        ViewTransactions.total = 0;
    }

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            ListStructure temp = dataSnapshot.getValue(ListStructure.class);

            if(temp.getDate().equals(date)){
                ViewTransactions.total += temp.getCash();
                mSnapshotList.add(dataSnapshot);
                notifyDataSetChanged();
                Log.d("MoneyManager","onChildAdded called,total = "+ViewTransactions.total);
            }

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    //ChildEventListener Ends

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public ListStructure getItem(int position) {
        DataSnapshot snap = mSnapshotList.get(position);
        return snap.getValue(ListStructure.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewTransactions.totalView.setText("Total:"+ViewTransactions.total);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout,parent,false);

            final ViewHolder holder = new ViewHolder();
            holder.name_of_product = (TextView)convertView.findViewById(R.id.product);
            holder.price = (TextView) convertView.findViewById(R.id.cash);
            holder.params = (LinearLayout.LayoutParams) holder.name_of_product.getLayoutParams();
            convertView.setTag(holder);
            Log.d("MoneyManager","getView == null");
        }

        final ListStructure transaction = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        setAppearance(holder);
        holder.price.setText(transaction.getCash()+"");
        holder.name_of_product.setText(transaction.getProduct());

        return convertView;
    }
    //Adapter Methods End

    public void setAppearance(ViewHolder holder){
        holder.name_of_product.setLayoutParams(holder.params);
        holder.price.setLayoutParams(holder.params);
    }

}
