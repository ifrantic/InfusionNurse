package no.priv.larsgard.activity;

import no.knutinge.Infusionnurse.R;
import no.priv.larsgard.calculation.Utregninger;
import no.priv.larsgard.type.SpeedType;
import no.priv.larsgard.type.MengdeType;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class CalcConvertSpeedActivity extends Activity {

	//Textview for å vise utregnede hastigheter
	private TextView tvMlT, tvDrMin, tvDrSek;
	
	//TextView for å vise verdi for valgt fart
	private TextView tvFart;
	
	//Spinner for å velge benevning på fart
	private Spinner spFart;
	
	//seekbar for å velge verdi på farg
	private SeekBar sbFart;
	
	//deklarerer klasse for utregninger
	private Utregninger utregning;
	
	//deklarerer og initialiserer enum for valg av type fart
	private SpeedType speedType=SpeedType.MLH;
	
	
	//max fart som seekbar kan ha
	private int maxFart=500;
	
	//startFart som seekbar har
	private int startFart=100;
	
	//Den farten som til enhver tid er angitt av seekbar
	private int fart;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.calc_conv_speed);
		utregning= new Utregninger();
		
		tvMlT=(TextView) findViewById(R.id.textViewCalcInfSpeedMlH);
		tvDrMin=(TextView) findViewById(R.id.textViewCalcInfSpeedDrMin);
		tvDrSek=(TextView) findViewById(R.id.textViewCalcInfSpeedDrSek);
		
		tvFart=(TextView) findViewById(R.id.textViewCalcConvSpeedFart);
		
		
		
		spFart=(Spinner)findViewById(R.id.spinnerCalcConvSpeedVelgFart);
		
		sbFart=(SeekBar) findViewById(R.id.SeekBarCalcConvertSpeedVelgFart);
		
		utregning= new Utregninger();

		
		populateSpinner();
		
		//Henter lagrede settings, hvis det er noen
		preferences();
		
		seekbarFart();
		spinnerFart();
		
		initializeTextViews();
		
		
	
		
	}
	
	
	//Lagrer data i SharedPreferences ved exit av appen. se override av onexit
	public void preferences(){
		//opprettes hvis den ikke finnes ved launsh
		SharedPreferences settings= getSharedPreferences("INFNURSEPREFS", 0);
		
		//Hvis det ikke er lagret noe startfart og maxfart, bruk default
			initiateSeekBars(settings.getInt("keyConvSpeedStartFart", startFart), 
					settings.getInt("keyConvSpeedMaxFart", maxFart));
		
			//setter seleced item, hvis det er noe lagret. hvis ikke, så blir det element 0 som er mlT
			spFart.setSelection(settings.getInt("keyConvSpeedSpFart", 0));
			
			switch (spFart.getSelectedItemPosition()) {
			case 1:
				speedType=SpeedType.DRMIN;
				
				break;
			case 2:
				speedType=SpeedType.DRSEK;

			default:
				speedType=SpeedType.MLH;
				break;
			}
		
		//setter default values, hvis det ikke er lagret noe i settigns
	//	tvMlT.setText(settings.getString("KeyConvSpeedMlT", getResources().getString(R.string.mlT)));
	//	tvDrMin.setText(settings.getString("KeyConvSpeedDrMin", getResources().getString(R.string.drMin)));
	//	tvDrSek.setText(settings.getString("KeyConvSpeedDrSek", getResources().getString(R.string.drSek)));
		
		
		
	}

	
	private void initializeTextViews(){
		//fart=startFart;
		tvFart.setText((getResources().getString(R.string.hastighet)+" " +fart));
		//regn ut fart
	/*	tvFart.setText(getResources().getString(R.string.hastighet)+" " +startFart);
		
		//regn ut fart
		double speed=utregning.konverterHastighetTilMlT(startFart, speedType);
		tvMlT.setText(getResources().getString(R.string.mlT)+" "+speed);
		tvDrMin.setText(getResources().getString(R.string.drMin)+" " +utregning.konverterMlTTilDrMin(speed));
		tvDrSek.setText(getResources().getString(R.string.drSek)+" "+ utregning.konverterMlTTilDrSek(speed));
		
		*/
	}
	
	private void populateSpinner(){
		ArrayAdapter<CharSequence> adapterSpFart=
				ArrayAdapter.createFromResource(this, R.array.fart_label, R.layout.spinner_item);
		spFart.setAdapter(adapterSpFart);
		
		
	}
	
	private void initiateSeekBars(int _startFart, int _maxFart){
		fart=_startFart;
		

		sbFart.setMax(_maxFart);
		sbFart.setProgress((int) _startFart);//Setter til startTid
	}
	
	private void seekbarFart(){
		
		sbFart.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
	
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				fart=progress;
			
				Log.v("SeekBarFart", "Farten er "+progress);
				tvFart.setText(getResources().getString(R.string.hastighet)+" " +progress);
				
				//regn ut fart
				double speed=utregning.konverterHastighetTilMlT(progress, speedType);
				tvMlT.setText(getResources().getString(R.string.mlT)+" "+speed);
				tvDrMin.setText(getResources().getString(R.string.drMin)+" " +utregning.konverterMlTTilDrMin(speed));
				tvDrSek.setText(getResources().getString(R.string.drSek)+" "+ utregning.konverterMlTTilDrSek(speed));
				
			}
		});
		
	}
	
	public void spinnerFart(){
		spFart.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				//setter linjeavnstand mellom nedtrekkselementene
				//arg0 er parent
				//((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
		        ((TextView) arg0.getChildAt(0)).setTextSize(14);
		        //((TextView) arg0.getChildAt(0)).setMinimumHeight(20);
				
				
				Log.v("CalcCOnvertSpeed", "valgte int for fart er "+ arg2);
				switch (arg2) {
				case 0:
					speedType=SpeedType.MLH;
					
					break;
					
				case 1:
					speedType=SpeedType.DRMIN;
					
					break;
					
				case 2:
					speedType=SpeedType.DRSEK;

				default:
					break;
				}
				
				//regn ut fart
				double speed=utregning.konverterHastighetTilMlT(fart, speedType);
				tvMlT.setText(getResources().getString(R.string.mlT)+" "+speed);
				tvDrMin.setText(getResources().getString(R.string.drMin)+" " +utregning.konverterMlTTilDrMin(speed));
				tvDrSek.setText(getResources().getString(R.string.drSek)+" "+ utregning.konverterMlTTilDrSek(speed));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	@Override
	protected void onStop() {

		super.onStop();
		
		//Skriver nåværende verdier til settings
		SharedPreferences settings= getSharedPreferences("INFNURSEPREFS", 0);
		SharedPreferences.Editor editor=settings.edit();
		
		//tekstVerdier i textviews
	//	editor.putString("KeyConvSpeedMlT", tvMlT.getText().toString());
	//	editor.putString("KeyConvSpeedDrMin", tvDrMin.getText().toString());
	//	editor.putString("KeyConvSpeedDrSek", tvDrSek.getText().toString());
		
		//finner farten som er satt av seekbar
		editor.putInt("keyConvSpeedStartFart", fart);
		
		//finner max fart som er satt for seekbar
		editor.putInt("keyConvSpeedMaxFart", sbFart.getMax());
		
		//finner type fart som er satt i spinner, ut fra spinner selected item.
		//Dette fordi jeg ikke klarte å lagre enum speedType i sharedpreferences
		editor.putInt("keyConvSpeedSpFart", spFart.getSelectedItemPosition());
		

		
		editor.commit();
	}

}
