package com.example.gamecaclulator;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

	private static int BASE_WIDTH = 0;
	private static boolean hasInit = false;
	public static HashMap<Integer, Boolean> actMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		actMap = new HashMap<Integer, Boolean>();
	}

	public void onResume() {
		super.onResume();
		/*if(hasInit)
			return;
		hasInit = true;*/
		setupGUI();
	}

	
	
	
	
	
	
	
	
	//Messy as hell, STAY AWAY
	private void setupGUI() {
		Thread t = new Thread(new Runnable() {

			private String[] act = {"C", "( )", "del", "=", "1", "+", "-"};
			
			@Override
			public void run() {
				BASE_WIDTH = 135;
				// while((BASE_WIDTH=MainActivity.this.findViewById(R.id.rlOuter).getWidth())==0);
				
				//setup general
				CalcButton clear = addButton("C", R.id.clear, 
						(RelativeLayout) MainActivity.this.findViewById(R.id.rlInner));
				clear.params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
				
				int[] svIds = {R.id.add, R.id.sub, R.id.mult, R.id.div, R.id.exp, R.id.fact};
				String[] svStrs = {"+", "-", "*", "/", "^", "!"};
				ScrollView sv = new ScrollView(MainActivity.this);
				RelativeLayout rlScroll = new RelativeLayout(MainActivity.this);
				sv.addView(rlScroll);
				addButton(svStrs[0], svIds[0], rlScroll);
				for(int a=1; a<svIds.length; a++){
					CalcButton cb = addButton(svStrs[a], svIds[a], rlScroll);
					cb.params.addRule(RelativeLayout.BELOW, svIds[a-1]);
				}
				RelativeLayout.LayoutParams svpar = new RelativeLayout.LayoutParams(BASE_WIDTH, 4*BASE_WIDTH);
				svpar.addRule(RelativeLayout.BELOW, R.id.equals);
				svpar.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				((ViewGroup)MainActivity.this.findViewById(R.id.rlInner)).addView(sv, svpar);
				
				int[][] ids = { {R.id.clear, R.id.par, R.id.del, R.id.equals}, 
						{R.id.b7, R.id.b8, R.id.b9}, {R.id.b4, R.id.b5, R.id.b6},
						{R.id.b1, R.id.b2, R.id.b3}, {R.id.decimal, R.id.b0, R.id.neg}};
				String[][] strs = {{ "C", "( )", "del", "=" }, 
						{"7", "8", "9"}, {"4", "5", "6"}, 
						{"1", "2", "3"}, {".", "0", "-"}};
				
				for(int s=0; s<ids.length; s++)
					for (int a = 0; a < ids[s].length; a++){
						if(a==0 && s==0) continue;
						CalcButton cb = addButton(strs[s][a], 
								ids[s][a], (RelativeLayout) MainActivity.this.findViewById(R.id.rlInner));
						if(s!=0)
							cb.params.addRule(RelativeLayout.BELOW, ids[s-1][a]);
						if(a==0)
							cb.params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
						else
							cb.params.addRule(RelativeLayout.RIGHT_OF, ids[s][a-1]);
					}
				
				//setupOnClick();
			}

			private CalcButton addButton(String s, int id, ViewGroup parent) {
				CalcButton button = new CalcButton(MainActivity.this, s);
				button.params = new RelativeLayout.LayoutParams(BASE_WIDTH,
						BASE_WIDTH);
				button.setId(id);
				button.setOnClickListener(new Handler(MainActivity.this));
				parent.addView(button, button.params);
				
				boolean activated = false;
				for(int a=0; a<act.length; a++)
					if(act[a].equals(s))
						button.setActivated(activated = true);
				
				actMap.put(id, activated);
				
				return button;
			}
			
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
		}
	}

}
