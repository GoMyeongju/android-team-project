package com.example.teamdiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberAdapter extends BaseAdapter {
    ArrayList<UserData> items = new ArrayList<>();
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, null, false);

            holder = new CustomViewHolder();
            holder.Name = convertView.findViewById(R.id.info1);
            holder.School = convertView.findViewById(R.id.info2);
            holder.Phone = convertView.findViewById(R.id.info3);

            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        UserData dto = items.get(position);

        holder.Name.setText(dto.getName());
        holder.School.setText(dto.getSchool());
        holder.Phone.setText(dto.getPhone());

        return convertView;
    }

    class CustomViewHolder {
        TextView Name;
        TextView School;
        TextView Phone;
    }

    // MainActivity에서 Adapter에있는 ArrayList에 data를 추가시켜주는 함수

    public void addItem(UserData dto) {
        items.add(dto);
    }
}
