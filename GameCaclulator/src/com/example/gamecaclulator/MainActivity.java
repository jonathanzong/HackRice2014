package com.example.gamecaclulator;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private static int BASE_WIDTH = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setupGUI();
	}

	private void setupGUI(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while((BASE_WIDTH=MainActivity.this.findViewById(R.id.rlOuter).getWidth())!=0);
			}
			
			private void addButton(String s, int id, ViewGroup parent){
				CalcButton button = new CalcButton(MainActivity.this, s);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(BASE_WIDTH, BASE_WIDTH);
				button.setId(id);
				parent.addView(button, params);
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {}
	}
	
}
