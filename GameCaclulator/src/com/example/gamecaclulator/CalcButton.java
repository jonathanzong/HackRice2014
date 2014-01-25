package com.example.gamecaclulator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalcButton extends RelativeLayout{

	private Context context;
	private TextView tv;
	public boolean activated;
	
	public CalcButton(Context c) {
		super(c);
		context = c;
		setupGUI();
		activated = false;
	}
	
	public CalcButton(Context c, AttributeSet attrs) {
		super(c, attrs);
		
		context = c;
		setupGUI();
		activated = false;
	}
	
	public CalcButton(Context c, String text) {
		this(c);
		setText(text);
	}

	private void setupGUI(){
		ImageView outline = new ImageView(context);
		outline.setBackground(context.getResources().getDrawable(R.drawable.outline));
		addView(outline, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setBackgroundColor(getResources().getColor(android.R.color.background_light));
		
		tv = new TextView(context);
		LayoutParams ptv = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ptv.addRule(CENTER_IN_PARENT, TRUE);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(40);
		tv.setTextColor(getResources().getColor(android.R.color.background_dark));
		addView(tv, ptv);
	}
	
	public void setText(String s){
		tv.setText(s);
	}
	
}
