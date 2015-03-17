
package com.gao.client_server_data_exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gao.client_server_data_exchange.R;
import com.gao.client_server_data_exchange.entity.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private List<Student> mStudentList;
    private LayoutInflater mInflater;
    private int mResource;

    public StudentAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
        mStudentList = objects;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = null;
        if (convertView == null) {
            view = (LinearLayout) mInflater.inflate(mResource, null);
        } else {
            view = (LinearLayout) convertView;
        }

        Student student = getItem(position);
        TextView txtId = (TextView) view.findViewById(R.id.txt_id);
        TextView txtName = (TextView) view.findViewById(R.id.txt_name);
        TextView txtAge = (TextView) view.findViewById(R.id.txt_age);

        txtId.setText(student.getId().toString());
        txtName.setText(student.getName());
        txtAge.setText(String.valueOf(student.getAge()));

        return view;
    }

}
