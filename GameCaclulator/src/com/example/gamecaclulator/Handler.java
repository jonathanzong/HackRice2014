package com.example.gamecaclulator;

import java.util.HashMap;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

@SuppressLint("UseSparseArrays")
public class Handler implements OnClickListener{

	private MainActivity context;
	private HashMap<Integer, String> strMap;
	private TextView tv;
	private static boolean clear = false;
	
	private static String[] strs = { "+", "-", "*", "/", "^", "!", "nCr",
			"nPr", ".", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-",
			"(", ")", "π", "τ", "e" };
	private static int[] ids = { R.id.add, R.id.sub, R.id.mult, R.id.div,
			R.id.exp, R.id.fact, R.id.nCr, R.id.nPr, R.id.decimal, R.id.b0,
			R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7,
			R.id.b8, R.id.b9, R.id.neg, R.id.parl, R.id.parr, R.id.pi,
			R.id.tau, R.id.e };

	public static String[] functStrs = { "√", "sin", "cos", "tan", "cot",
			"sec", "csc", "∫", "ln", "log", "abs" };
	private static int[] functIds = { R.id.sqrt, R.id.sin, R.id.cos, R.id.tan,
			R.id.cot, R.id.sec, R.id.csc, R.id.integral, R.id.ln, R.id.log, R.id.abs };

	public Handler(MainActivity c){
		context = c;
		strMap = new HashMap<Integer, String>();
		for(int a=0; a<ids.length; a++)
			strMap.put(ids[a], strs[a]);
		for(int a=0; a<functIds.length; a++)
			strMap.put(functIds[a], functStrs[a]);
		tv = (TextView) context.findViewById(R.id.tvOutput);
	}
	
	private void addToOutput(int id, String s){
		if(MainActivity.actMap.get(id).isActivated())
			tv.setText(tv.getText().toString()+s);
	}
	
	@Override
	public void onClick(View v) {
		Log.wtf("onClick", "here "+clear);
		if (clear && v.getId() != R.id.add && v.getId() != R.id.sub
				&& v.getId() != R.id.mult && v.getId() != R.id.div
				&& v.getId() != R.id.equals){
			tv.setText("");
			Log.wtf("Clear", "yes");
			clear = false;
		}
		Log.wtf("Click", "onClick "+Integer.toString(v.getId(), 16));
		for(int id : ids)
			if(id==v.getId())
				addToOutput(v.getId(), strMap.get(id)); 
		for(int id : functIds)
			if(id==v.getId())
				addToOutput(v.getId(), strMap.get(id)+"("); 
		switch(v.getId()){
		case R.id.del:
			if(tv.getText().toString().length()==0)
				break;
			tv.setText(tv.getText().toString()
					.substring(0, tv.getText().toString().length() - 1));
			break;
		case R.id.equals:
			if(oneExtraLeft(tv.getText().toString()))
				tv.setText(tv.getText()+")");
			Log.wtf("Here", "equals");
			String eval = Evaluator.eval(tv.getText().toString());
			tv.setText(eval);
			clear = true;
			break;
		case R.id.clear: tv.setText(""); break;
		}
	}

	private boolean oneExtraLeft(String s){
		Stack<Integer> left = new Stack<Integer>();
		try{
		for(char c : s.toCharArray())
			if(c=='(')
				left.push(0);
			else if(c==')')
				left.pop();
		}catch(Exception e){
			return false;
		}
		return left.size()==1;
	}
	
}
