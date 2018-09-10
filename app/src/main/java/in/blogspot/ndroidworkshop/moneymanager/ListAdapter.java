package in.blogspot.ndroidworkshop.moneymanager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.res.Resources;
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

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
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

    public ListAdapter(Activity activity,DatabaseReference ref, String name){
        mActivity = activity;
        mDisplayName = name;
        // common error: typo in the db location. Needs to match what's in MainChatActivity.
        mDatabaseReference = ref.child("messages");
        mDatabaseReference.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView name_of_product;
        TextView price;
        LinearLayout.LayoutParams params;
    }

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
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_layout,parent,false);

            final ViewHolder holder = new ViewHolder();
            holder.name_of_product = (TextView)convertView.findViewById(R.id.product);
            holder.price = (TextView) convertView.findViewById(R.id.cash);
            holder.params = (LinearLayout.LayoutParams) holder.name_of_product.getLayoutParams();
            convertView.setTag(holder);
        }

        final ListStructure transaction = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        boolean isMe = transaction.getUsername().equals(mDisplayName);

        if(isMe){
            setAppearance(holder);
            holder.price.setText(transaction.getCash());
            holder.name_of_product.setText(transaction.getProduct());
        }

        return convertView;
    }

    public void setAppearance(ViewHolder holder){
        holder.name_of_product.setLayoutParams(holder.params);
        holder.price.setLayoutParams(holder.params);
    }
}
