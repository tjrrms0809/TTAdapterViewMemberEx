package com.kitesoft.adapterviewmemberex;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberAdapter extends BaseAdapter {
	
	ArrayList<Member> datas;
	LayoutInflater inflater;
	
	public MemberAdapter(LayoutInflater inflater, ArrayList<Member> datas) {
		this.datas= datas;
		this.inflater= inflater;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView= inflater.inflate(R.layout.listview_item, null);
		}
		
		String name= datas.get(position).getName();
		String nation= datas.get(position).getNation();
		int g= datas.get(position).getGender();
		int n= datas.get(position).getFlagId();
		String date= datas.get(position).getDate();

		int[] imgs= {R.drawable.man, R.drawable.woman};
		((ImageView)convertView.findViewById(R.id.img_gender)).setImageResource(imgs[g]);
		((TextView)convertView.findViewById(R.id.text_name)).setText(name);
		((TextView)convertView.findViewById(R.id.text_nation)).setText(nation);
		((ImageView)convertView.findViewById(R.id.img_flag)).setImageResource(R.drawable.f_australia+n);
		((TextView)convertView.findViewById(R.id.text_date)).setText(date);

		return convertView;
	}

}
