package com.example.madhu.emailapp_original;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by madhu on 16-04-2017.
 * Custom Row adapter to display Email instances.
 */

public class RowAdapter extends ArrayAdapter<Email> {

    List<Email> mData;
    Context mContext;
    int mResource;
    String from_addr;

    public RowAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Email> objects, String from_addr) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mData = objects;
        this.from_addr = from_addr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Email email = mData.get(position);
        TextView to = (TextView) convertView.findViewById(R.id.tv_to_val);
        to.setText(email.getTo());

        TextView from = (TextView) convertView.findViewById(R.id.tv_from_val);
        from.setText(from_addr);

        TextView sub = (TextView) convertView.findViewById(R.id.tv_sub_val);
        sub.setText(email.getSubject());

        TextView body = (TextView) convertView.findViewById(R.id.tv_body_val);
        body.setText(email.getBody());

        return convertView;
    }
}
