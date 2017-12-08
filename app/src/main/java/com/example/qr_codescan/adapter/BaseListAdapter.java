package com.example.qr_codescan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mList;
	protected LayoutInflater mInflate;

	public BaseListAdapter(Context context) {
		mContext = context;
		if (mContext != null) {
			mInflate = LayoutInflater.from(mContext);
		}
	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		}
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (mList == null || mList.size() <= position) {
			return null;
		}
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<T> getList() {
		return mList;
	}

	public void setList(List<T> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public void remove(T item) {
		if (mList != null) {
			mList.remove(item);
			notifyDataSetChanged();
		}
	}

	public void add(T item) {
		if (mList != null) {
			mList.add(item);
			notifyDataSetChanged();
		}
	}

}
