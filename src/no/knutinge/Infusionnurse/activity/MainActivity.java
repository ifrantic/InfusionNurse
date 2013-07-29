package no.knutinge.Infusionnurse.activity;

import java.util.ArrayList;



import no.knutinge.Infusionnurse.R;
import no.knutinge.Infusionnurse.calculation.SnittFart;
import no.knutinge.Infusionnurse.calculation.Utregninger;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity{
	

	//For meny-listen
	private ArrayList<String> list;
	private ListAdapter adapter;

	//For drop rate
	private Button trykk, trykkReset;
	private TextView outputDrSek, outputDrMin, outputMlT;
	
	private SnittFart snittFart;
	double tempFart=0;
	
	private Utregninger utregning;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		utregning=new Utregninger();
		
		//Lager en liten menyliste til forsiden
		list=new ArrayList<String>();
		list.add(0, getResources().getString(R.string.main_Button_til_infHastighet));
		list.add(1, getResources().getString(R.string.main_Button_til_convHastighet));
		list.add(2, getResources().getString(R.string.main_Button_til_dsm));
		
		//Tar ikke med fortynning i gratisversjonen
	//	list.add(3, getResources().getString(R.string.main_Button_til_fortynning));
		
		ListView view=(ListView) findViewById(R.id.list);
		
		adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1 , list);
		
		view.setAdapter(adapter);
		
		 view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("Trykket på int " +arg2);
				switch (arg2) {
				
				case 0:
					Intent i = new Intent(getApplicationContext(), CalcInfSpeedActivity.class);
					startActivity(i);
					
					break;
				
				case 1:
					Intent i2 = new Intent(getApplicationContext(), CalcConvertSpeedActivity.class);
					startActivity(i2);
					break;
	

				case 2:
					Intent i3=new Intent(getApplicationContext(), DoseStyrkeMengdeActivity.class);
					startActivity(i3);
					break;
					
				case 3:
					//Her kommer fortynning
					break;
					
				default:
					break;
				}
				
			}
		});
		
		
		
	
		
		//DROP RATE

		trykk=(Button) findViewById(R.id.button1DraapeTakt);
		outputDrSek=(TextView) findViewById(R.id.textViewDrSek);
		outputDrMin=(TextView) findViewById(R.id.textViewDrMin);
		outputMlT=(TextView) findViewById(R.id.textViewMlT);
		
		
		//henter inn preferences, skal ikke brukes... Ikke laget ferdig.
		preferences();
		
		
		snittFart=new SnittFart();
		
		trykk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Long time=System.currentTimeMillis();
				String tidspunkt=time.toString();
				
				snittFart.addFart(tidspunkt);
				if (snittFart.getSnittFart()>0) {
					outputDrSek.setText(getResources().getString(R.string.drSek)+": "+ utregning.setDesimaler(snittFart.getSnittFart()));
					//TODO lag skikkelige utregninger
					outputDrMin.setText(getResources().getString(R.string.drMin) + ": " + utregning.setDesimaler(snittFart.getSnittFart()*60));
					outputMlT.setText(getResources().getString(R.string.mlT) + ": " + utregning.setDesimaler((snittFart.getSnittFart()/20)*3600));
					
				}
				else {
					//tatt med for at det ikke skal stå eks dr/ sek: 0
					outputDrSek.setText(getResources().getString(R.string.drSek)+": ");
					//TODO lag skikkelige utregninger
					outputDrMin.setText(getResources().getString(R.string.drMin) + ": ");
					outputMlT.setText(getResources().getString(R.string.mlT) + ": ");
					
				}
				
			}
		});

		//DROP RATE FINISH
		
		
		//RESET DROP RATE
		trykkReset=(Button)findViewById(R.id.button1Reset);
		
		trykkReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				snittFart.reset();
				outputDrSek.setText(getResources().getString(R.string.drSek));
				//TODO lag skikkelige utregninger
				outputDrMin.setText(getResources().getString(R.string.drMin));
				outputMlT.setText(getResources().getString(R.string.mlT));
				
			}
		});
		
		//RESET DROP RATE finish
		
		
	}

	
	//Denne er for å lage / huske data fra sist exit.
	//Er egentlig ikke i bruk, bare laget for testing.
	public void preferences(){
		//opprettes hvis den ikke finnes ved launsh
		SharedPreferences settings= getSharedPreferences("INFNURSEPREFS", 0);
		
		//setter default values, hvis det ikke er lagret noe i settigns
		outputDrMin.setText(settings.getString("KeyDrMin", getResources().getString(R.string.drMin)));
		outputDrSek.setText(settings.getString("KeyDrSek", getResources().getString(R.string.drSek)));
		outputMlT.setText(settings.getString("KeyMlT", getResources().getString(R.string.mlT)));
		
		
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	   super.onSaveInstanceState(outState);

	 //TODO legg tabell i SaveInstanceState når skjerm snues
	 
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onStop() {
		super.onStop();
		//Skriver til settings
		
		SharedPreferences settings= getSharedPreferences("INFNURSEPREFS", 0);
		SharedPreferences.Editor editor=settings.edit();
		//Ikke vits i å lagre dråpetakt, da denne må regnes ut on-the-fly
	//	editor.putString("KeyDrMin", outputDrMin.getText().toString());
		
		editor.commit();
	
	
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_about:
			new AlertDialog.Builder(this)
		    .setTitle(getResources().getString(R.string.about_button))
		    .setMessage(getResources().getString(R.string.about_me))
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	dialog.cancel();
		        }
		     })
		  
		     .show();
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	
  
	
	
	
	
}
