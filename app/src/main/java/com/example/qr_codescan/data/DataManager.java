package com.example.qr_codescan.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.example.qr_codescan.util.StrUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

@SuppressLint("SdCardPath")
public class DataManager extends Observable {
	private static DataManager mInstance;
	private List<String> mList;
	private String mFilePath;

	private DataManager(Context context) {
		mFilePath = getSavePath();
		try {
			openFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getSavePath() {
		String filePath;
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}

		if (sdDir != null) {
			filePath = sdDir.toString() + "/uids.txt";
		} else {
			filePath = "/sdcard/uids.txt";
		}
		return filePath;

	}

	// ����ģʽ���̰߳�ȫ
	public synchronized static DataManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DataManager(context);
		}
		return mInstance;
	}

	public List<String> getList() {
		return mList;
	}

	/**
	 * ���ļ�����UID
	 * 
	 * @throws Exception
	 */
	public void openFile() throws Exception {
		mList = new LinkedList<String>();
		File file = new File(mFilePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileInputStream stream = new FileInputStream(file);
		int tempChar;
		int num = 0;
		StringBuffer strBuffer = new StringBuffer();
		while ((tempChar = stream.read()) != -1) {
			if (((char) tempChar) == '\r') {
				mList.add(strBuffer.toString());
				++num;
				strBuffer.delete(0, strBuffer.length());
			} else {
				if (((char) tempChar) == '\n') {
					continue;
				}
				strBuffer.append((char) tempChar);
			}
		}
		stream.close();
		System.out.println("������ϣ�һ��" + (num + 1) + "��ID");
		for (String str : mList) {
			System.out.println(str);
		}
	}

	public void deleteFile() {
		File file = new File(mFilePath);
		if (file.exists()) {
			file.delete();
		}
		clearUid();
	}

	private void clearUid() {
		mList.clear();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * ���UID
	 * @param uid
	 * @return
	 */
	public boolean addUid(String uid) {
		//������˾ͷ���false
		if(haveUid(uid))
		{
			return false;
		}
		//���
		mList.add(uid);
		//���浽�ļ�
		try {
			saveToFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//֪ͨ�۲���
		setChanged();
		notifyObservers();
		return true;
	}

	private boolean haveUid(String uid) {
		for (String str : mList) {
			if(StrUtil.isEquals(str, uid))
			{
				return true;
			}
		}
		return false;
	}

	public void deleteUid(int position) {
		mList.remove(position);
		setChanged();
		notifyObservers();
	}

	/**
	 * ��list�����ֵ���浽�ļ�
	 * 
	 * @throws FileNotFoundException
	 */
	public void saveToFile() throws Exception {
		synchronized (mList) {
			File file = new File(mFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(file);
			for (String str : mList) {
				char[] chars = str.toCharArray();
				for (char c : chars) {
					stream.write((int) c);
				}
				// д�뻻��
				stream.write((int) '\r');
				stream.write((int) '\n');
				stream.flush();
			}
			stream.close();
		}
	}

}
