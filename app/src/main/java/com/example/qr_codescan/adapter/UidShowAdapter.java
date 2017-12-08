package com.example.qr_codescan.adapter;

import java.util.Observable;
import java.util.Observer;

import com.example.qr_codescan.data.DataManager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UidShowAdapter extends BaseListAdapter<String> implements Observer {

	public UidShowAdapter(Context context) {
		super(context);
		setList(DataManager.getInstance(context).getList());
		DataManager.getInstance(context).addObserver(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TextView(mContext);
			convertView.setPadding(10, 10, 10, 10);
			((TextView) convertView).setTextColor(Color.RED);
		}
		((TextView) convertView).setText(getList().get(position));
		return convertView;
	}

	@Override
	public void update(Observable observable, Object data) {
		notifyDataSetChanged();
	}

}
