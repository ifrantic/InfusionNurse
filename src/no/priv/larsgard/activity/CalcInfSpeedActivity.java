package no.priv.larsgard.activity;

import no.knutinge.Infusionnurse.R;
import no.priv.larsgard.calculation.Utregninger;
import no.priv.larsgard.type.SpeedType;
import no.priv.larsgard.type.TimeType;
import no.priv.larsgard.type.VolumeType;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class CalcInfSpeedActivity extends Activity {
	
	//TextView for å vise fart i mlt, drmin og drsek
	private TextView tvMlT, tvDrMin, tvDrSek;
	
	//textview for å vise valgt verdi av tid og mengde
	private TextView tvTid, tvMengde;
	
	//spinnere for å velge enhet på tid og mengde
	private Spinner spTid, spMengde;
	
	//seekbar for å velge verdi til tid og mengde
	private SeekBar sbTid, sbMengde;
	
	// verdi på startTid
	private int tid;
	
	//verdi på startMengde
	private double mengde;

	//default verdi av type tid
	private TimeType timeType=TimeType.HOUR;
	
	//default verdi av type volum
	private VolumeType volumeType=VolumeType.ML;
	
	//deklagerer klasse for utregning
	private Utregninger utregning;
	
	//max verdi for seekbar Mengde
	private int sbMaxMengde=200;
	
	private int startTid=1;
	private double startMengde=100;
	
	//max verdi for seekbar Tid
	private int sbMaxTid=100;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.calc_inf_speed);
		
		tvMlT=(TextView) findViewById(R.id.textViewCalcInfSpeedMlH);
		tvDrMin=(TextView) findViewById(R.id.textViewCalcInfSpeedDrMin);
		tvDrSek=(TextView) findViewById(R.id.textViewCalcInfSpeedDrSek);
		
		tvTid=(TextView) findViewById(R.id.textViewCalcConvSpeedFart);
		
		tvMengde=(TextView) findViewById(R.id.textViewCalcInfSpeedMengde);
		
		
		spTid=(Spinner) findViewById(R.id.spinnerCalcConvSpeedVelgFart);
		spMengde=(Spinner) findViewById(R.id.spinnerCalcInfSpeedVelgMengde);
		
		sbTid=(SeekBar) findViewById(R.id.SeekBarCalcConvertSpeedVelgFart);
		sbMengde=(SeekBar) findViewById(R.id.SeekBarCalcInfSpeedVelgMengde);
		
		
		utregning=new Utregninger();
		
		populateSpinner();
	preferences();
	seekBarTid();
	seekBarMengde();
		spinnerTid();
		spinnerMengde();
		
		initializeTextViews();
	
		
	
		
	}

	private void initializeTextViews(){
		
		tvTid.setText(getResources().getString(R.string.Infusjonstid)+" "+tid);
		
		tvMengde.setText(getResources().getString(R.string.mengde)+" "+ mengde);
		
	}
	
	
	//Lagrer data i SharedPreferences ved exit av appen. se override av onexit
	//Her hentes lagrede data inn

		public void preferences(){
			//opprettes hvis den ikke finnes ved launsh
			SharedPreferences settings= getSharedPreferences("INFNURSEPREFS", 0);
			
			/*
			
			keyCalcInfSpeedSpTid
			keyCalcInfSpeedSpMengde
			keyCalcInfSpeedSbTidMax
			keyCalcInfSpeedSbMengdeMax
			keyCalcInfSpeedSbTidProgress
			keyCalcInfSpeedSbMengdeProgress
			*/
			
			//Hvis det ikke er lagret noe startfart og maxfart, bruk default
				initiateSeekBars(settings.getInt("keyCalcInfSpeedSbTidProgress", startTid), settings.getInt("keyCalcInfSpeedSbTidMax", sbMaxTid),
						settings.getInt("keyCalcInfSpeedSbMengdeProgress", (int) startMengde), settings.getInt("keyCalcInfSpeedSbMengdeMax", sbMaxMengde));
					
			
				//setter seleced item for Tid, hvis det er noe lagret. hvis ikke, så blir det element 0 som er HOUR
				spTid.setSelection(settings.getInt("keyCalcInfSpeedSpTid", 0));
				
				switch (spTid.getSelectedItemPosition()) {
				case 1:
					timeType=TimeType.MIN;
					
					break;
				case 2:
					timeType=TimeType.SEC;

				default:
					timeType=TimeType.HOUR;
					break;
				}
			
				//setter seleced item for Mengde, hvis det er noe lagret. hvis ikke, så blir det element 0 som er MilliL
				spMengde.setSelection(settings.getInt("keyCalcInfSpeedSpMengde", 0));
				
				switch (spTid.getSelectedItemPosition()) {
				case 1:
					volumeType=VolumeType.DR;
					
					break;
				case 2:
					volumeType=VolumeType.LITER;

				default:
					volumeType=VolumeType.ML;
					break;
				}
			
		}
	public void populateSpinner(){
		  ArrayAdapter<CharSequence> adapterSpTid = ArrayAdapter.createFromResource(
			        this, R.array.Tid_label, R.layout.spinner_item);
			   //  adapterSpTid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			     spTid.setAdapter(adapterSpTid);
   
			     
			     ArrayAdapter<CharSequence> adapterSpMengde = ArrayAdapter.createFromResource(
			    		 this, R.array.mengde_label, R.layout.spinner_item);
			     spMengde.setAdapter(adapterSpMengde);
			  }
	
	public void initiateSeekBars(int _sbTidProgress, int _sbMaxTid, double _sbMengdeProgress, int _sbMaxMengde){
		
		tid=_sbTidProgress;
		mengde=_sbMengdeProgress;
		
		sbTid.setProgress(tid);//Setter til startTid
		sbMengde.setProgress((int) mengde);//setter til startMengde
		sbMengde.setMax(_sbMaxMengde); //setter max verdi seekbar Mengde
		sbTid.setMax(_sbMaxTid);	//setter max verdi for seekbar Tid
		
	
	}


	public void seekBarMengde() {
		sbMengde.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
		
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
		
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				mengde=progress;
				
				if (mengde==0) {
					mengde=0.01;//hindrer 0 verdi for å unngå trøbbel med utregninger
				}
				
				Log.v("SeekBarMengde", "Mengden er "+progress);
				tvMengde.setText(getResources().getString(R.string.mengde)+" " +mengde);
				
				//regn ut fart
				double mlt=utregning.regnHastighet(mengde, tid, volumeType, timeType);
				tvMlT.setText(getResources().getString(R.string.mlT)+" "+mlt);
				tvDrMin.setText(getResources().getString(R.string.drMin)+" " +utregning.konverterMlTTilDrMin(mlt));
				tvDrSek.setText(getResources().getString(R.string.drSek)+" "+ utregning.konverterMlTTilDrSek(mlt));
				
			}
		});
		
	}

	public void seekBarTid() {
		sbTid.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				tid=progress;
				
				if (tid==0) {
					tid=1;	//for å hindre 0 verdi. Setter minste mulige tid til 1.
				}
				
				Log.v("seekBarTid", "Tiden er "+progress);
				tvTid.setText(getResources().getString(R.string.Infusjonstid)+" "+ tid);
				
				//regn ut fart
				double mlt=utregning.regnHastighet(mengde, tid, volumeType, timeType);
				tvMlT.setText(getResources().getString(R.string.mlT)+" "+mlt);
				tvDrMin.setText(getResources().getString(R.string.drMin)+" " +utregning.konverterMlTTilDrMin(mlt));
				tvDrSek.setText(getResources().getString(R.string.drSek)+" "+ utregning.konverterMlTTilDrSek(mlt));
				
			}
		});
		
	}

	public void spinnerMengde() {
		
		
		final VolumeType tempVolType=volumeType;
		spMengde.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("CalcInfSpeed", "valgte int for mengde er "+ arg2);
				switch (arg2) {
				case 0:
					volumeType=VolumeType.ML;
					
					break;
					
				case 1:
					volumeType=VolumeType.DR;
					
					break;
					
				case 2:
					volumeType=VolumeType.LITER;

				default:
					break;
				}
				
				//regn ut fart. OBS! Dropper å endre farten, endrer heller mengden i forhold til ny benevning.
				double mlt=utregning.regnHastighet(mengde, tid, volumeType, timeType);
				tvMlT.setText(getResources().getString(R.string.mlT)+" "+mlt);
				tvDrMin.setText(getResources().getString(R.string.drMin)+" " +utregning.konverterMlTTilDrMin(mlt));
				tvDrSek.setText(getResources().getString(R.string.drSek)+" "+ utregning.konverterMlTTilDrSek(mlt));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
				
			}
		});
		
	}

	public void spinnerTid() {
		Log.v("CalcInfSpeed", "spinnerTid er startet");
		spTid.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("CalcInfSpeed", "valgte int for tid er  "+ arg2);
				
				switch (arg2) {
				case 0:
					timeType=TimeType.HOUR;
					
					break;
					
				case 1:
					timeType=TimeType.MIN;
					break;
					
				case 2:
					timeType=TimeType.SEC;
					break;

				default:
					break;
				}
				
				//regn ut fart
				double mlt=utregning.regnHastighet(mengde, tid, volumeType, timeType);
				tvMlT.setText(getResources().getString(R.string.mlT)+" "+mlt);
				tvDrMin.setText(getResources().getString(R.string.drMin)+" " +utregning.konverterMlTTilDrMin(mlt));
				tvDrSek.setText(getResources().getString(R.string.drSek)+" "+ utregning.konverterMlTTilDrSek(mlt));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
		
				
			}
		});
		
	}
	
	
	
	@Override
	protected void onStop() {

		super.onStop();
		
		//Skriver nåværende verdier til settings
		SharedPreferences settings= getSharedPreferences("INFNURSEPREFS", 0);
		SharedPreferences.Editor editor=settings.edit();
		
		
		//finner tidsbenevningen som er satt av spinner tid
		editor.putInt("keyCalcInfSpeedSpTid", spTid.getSelectedItemPosition());
		
		//finner tidsbenevningen som er satt av spinner tid
		editor.putInt("keyCalcInfSpeedSpMengde", spMengde.getSelectedItemPosition());
		
		//finner max fart som er satt for seekbar tid
		editor.putInt("keyCalcInfSpeedSbTidMax", sbTid.getMax());
		
		//finner max fart som er satt for seekbar mengde
				editor.putInt("keyCalcInfSpeedSbMengdeMax", sbMengde.getMax());
				
		//finner farten som er satt på seekbar tid
				editor.putInt("keyCalcInfSpeedSbTidProgress", tid);
				
				//finner farten som er satt på seekbar tid
				editor.putInt("keyCalcInfSpeedSbMengdeProgress", (int) mengde);
		

		
		editor.commit();
	}

}
