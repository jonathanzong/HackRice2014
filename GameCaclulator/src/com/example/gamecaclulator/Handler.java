package com.example.gamecaclulator;

import java.util.HashMap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Handler implements OnClickListener{

	private Activity context;
	private HashMap<Integer, String> strMap;
	private TextView tv;
	
	private static String[] strs = { "+", "-", "*", "/", ".", "0", "1", "2",
			"3", "4", "5", "6", "7", "8", "9", "-"};
	private static int[] ids = { R.id.add, R.id.sub, R.id.mult, R.id.div,
			R.id.decimal, R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5,
			R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.neg};
	
	public Handler(Activity c){
		context = c;
		strMap = new HashMap<Integer, String>();
		for(int a=0; a<ids.length; a++)
			strMap.put(ids[a], strs[a]);
		tv = (TextView) context.findViewById(R.id.tvOutput);
	}
	
	private void addToOutput(int id){
		if(MainActivity.actMap.get(id))
			tv.setText(tv.getText().toString()+strMap.get(id));
	}
	
	@Override
	public void onClick(View v) {
		Log.wtf("Click", "onClick "+Integer.toString(v.getId(), 16));
		switch(v.getId()){
		case R.id.b0:
		case R.id.b1:
		case R.id.b2:
		case R.id.b3:
		case R.id.b4:
		case R.id.b5:
		case R.id.b6:
		case R.id.b7: 
		case R.id.b8:
		case R.id.b9:
		case R.id.add:
		case R.id.sub:
		case R.id.mult:
		case R.id.div:
		case R.id.decimal: addToOutput(v.getId()); break;
		case R.id.clear: tv.setText(""); break;
		case R.id.del:
			if(tv.getText().toString().length()==0)
				break;
			tv.setText(tv.getText().toString()
					.substring(0, tv.getText().toString().length() - 1));
			break;
		case R.id.equals:
			//evaluate here
			break;
		}
	}

}
