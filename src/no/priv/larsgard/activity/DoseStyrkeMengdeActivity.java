package no.priv.larsgard.activity;

import no.knutinge.Infusionnurse.R;
import no.priv.larsgard.calculation.DoseStyrkeMengde;
import no.priv.larsgard.calculation.DoseStyrkeMengdeUtregning;
import no.priv.larsgard.calculation.SnittFart;
import no.priv.larsgard.calculation.Utregninger;
import no.priv.larsgard.type.DoseType;
import no.priv.larsgard.type.MengdeType;
import no.priv.larsgard.type.StyrkeType;
import no.priv.larsgard.type.VolumeType;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class DoseStyrkeMengdeActivity extends Activity{

private TextView tvDose, tvStyrke, tvMengde;
private Spinner spDose, spStyrke, spMengde;
private SeekBar sbDose, sbStyrke, sbMengde;

//holder aktuell verdi av dose, styrke og mengde
private int dose, styrke, mengde;

//startverdi for dose, styrke og mengde
private int startVerdi=1;

//max verdier som dose, styrke og mengde kan ha. Tenkt brukt av eks seeksbar
private int maxDose=50, maxStyrke=50, maxMengde=600;

private Utregninger utregning;
private DoseStyrkeMengde dsm;
private DoseStyrkeMengdeUtregning dsmUtregning;

//default benevninger for dose, styrke og mengde
private DoseType doseType=DoseType.MG;
private StyrkeType styrkeType=StyrkeType.MgMl;
private MengdeType mengdeType=MengdeType.Ml;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dose_styrke_mengde);
		
		tvDose=(TextView) findViewById(R.id.textViewDose);
		tvStyrke=(TextView) findViewById(R.id.textViewStyrke);
		tvMengde=(TextView) findViewById(R.id.textViewMengde);
		
		spDose=(Spinner) findViewById(R.id.spinnerDose);
		spStyrke=(Spinner) findViewById(R.id.spinnerStyrke);
		spMengde=(Spinner) findViewById(R.id.spinnerMengde);
		
		sbDose=(SeekBar) findViewById(R.id.SeekBaDose);
		sbStyrke=(SeekBar) findViewById(R.id.seekBarStyrke);
		sbMengde=(SeekBar) findViewById(R.id.seekBarMengde);
	
		dose=styrke=mengde=startVerdi;
		//instansierer klasse for utregninger
		utregning=new Utregninger();
		
		
		
		dsm=new DoseStyrkeMengde(dose, styrke);
		
		//initierer dsmUtregning med startverdier som er satt
		dsmUtregning= new DoseStyrkeMengdeUtregning(dsm, doseType, styrkeType, mengdeType);

		
		populateSpinners();
		
		initiateSeekbars();
		
		tvDose.setText(getResources().getString(R.string.dose) + dose);
		tvStyrke.setText(getResources().getString(R.string.styrke) + styrke);
		tvMengde.setText(getResources().getString(R.string.mengde) + mengde);
		
		seekBarDose();
		seekBarStyrke();
		seekBarMengde();
		
		spinnerDose();
		spinnerStyrke();
		spinnerMengde();
	
	}

	//populerer nedtrekksmenyer med valg
	private void populateSpinners(){
		ArrayAdapter<CharSequence> adapterSpDose= 
				ArrayAdapter.createFromResource(getApplicationContext(), R.array.doseFree_label, R.layout.spinner_item);
		spDose.setAdapter(adapterSpDose);
		
		ArrayAdapter<CharSequence> adapterSpStyrke= 
				ArrayAdapter.createFromResource(getApplicationContext(), R.array.styrkeFree_label, R.layout.spinner_item);
		spStyrke.setAdapter(adapterSpStyrke);
		
		ArrayAdapter<CharSequence> adapterSpMengde= 
				ArrayAdapter.createFromResource(getApplicationContext(), R.array.mengdeFree_label, R.layout.spinner_item);
		spMengde.setAdapter(adapterSpMengde);
	}
	
	//initierers seekbar for dose, styrke og mengde
	private void initiateSeekbars(){
	
		
		sbDose.setProgress((int) dsm.getDose());
		sbStyrke.setProgress((int) dsm.getStyrke());
		sbMengde.setProgress((int) dsm.getMengde());
		//TODO finne max verdier for seekbar
		sbDose.setMax(maxDose);
		sbStyrke.setMax(maxStyrke);
		sbMengde.setMax(maxMengde);
		
	}
	
	//hva som skjer når seekbar for dose endres (endre verdi av dose)
	private void seekBarDose(){
		
		sbDose.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
	
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				dsm.setDose(progress); //dose får oppdatert verdi
				
				//TODO implementer sjekk av benevning på dose/styrke/mengde
				
				//styrke endres når dose endres, mengde forblir uendret. Dette er et valg jeg har tatt.
				dsm.setStyrke(dsm.getDose(), dsm.getMengde());
				
				

				
					//oppdater tekstview med nye verdier
					tvDose.setText(getResources().getString(R.string.dose)+ utregning.setFireDesimaler(dsm.getDose()));
					tvStyrke.setText(getResources().getString(R.string.styrke)+ utregning.setFireDesimaler(dsm.getStyrke()));
					tvMengde.setText(getResources().getString(R.string.mengde)+ utregning.setFireDesimaler(dsm.getMengde()));
					
					//oppdater seekbars med nye verdier, ta alle selv om kun 1 endres...
					sbDose.setProgress((int) dsm.getDose());
					sbStyrke.setProgress((int) dsm.getStyrke());
					sbMengde.setProgress((int) dsm.getMengde());
				
	
				
				
				
				
				
			}
		});
	}
	
	
	//hva som skjer når seekbar for dose endres (endre verdi av dose)
	private void seekBarStyrke(){
		
		sbStyrke.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
	
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				dsm.setStyrke(progress); //dose får oppdatert verdi
				
				//TODO implementer sjekk av benevning på dose/styrke/mengde
				
				//mengde endres når styrke endres, dose forblir uendret. Dette er et valg jeg har tatt.
				dsm.setMengde(dsm.getDose(), dsm.getStyrke());
				
				//oppdater tekstview med nye verdier
				tvDose.setText(getResources().getString(R.string.dose)+ utregning.setFireDesimaler(dsm.getDose()));
				tvStyrke.setText(getResources().getString(R.string.styrke)+ utregning.setFireDesimaler(dsm.getStyrke()));
				tvMengde.setText(getResources().getString(R.string.mengde)+ utregning.setFireDesimaler(dsm.getMengde()));
				
				//oppdater seekbars med nye verdier, ta alle selv om kun 1 endres...
				sbDose.setProgress((int) dsm.getDose());
				sbStyrke.setProgress((int) dsm.getStyrke());
				sbMengde.setProgress((int) dsm.getMengde());
				
				
				
				
				
			}
		});
	}
	
	
	
	//hva som skjer når seekbar for dose endres (endre verdi av dose)
	private void seekBarMengde(){
		
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
				dsm.setMengde(progress); //dose får oppdatert verdi
				
				//TODO implementer sjekk av benevning på dose/styrke/mengde
				
				//styrke endres når mengde endres, dose forblir uendret. Dette er et valg jeg har tatt.
				dsm.setStyrke(dsm.getDose(), dsm.getMengde());
				
				//oppdater tekstview med nye verdier
				tvDose.setText(getResources().getString(R.string.dose)+ utregning.setFireDesimaler(dsm.getDose()));
				tvStyrke.setText(getResources().getString(R.string.styrke)+ utregning.setFireDesimaler(dsm.getStyrke()));
				tvMengde.setText(getResources().getString(R.string.mengde)+ utregning.setFireDesimaler(dsm.getMengde()));
				
				//oppdater seekbars med nye verdier, ta alle selv om kun 1 endres...
				sbDose.setProgress((int) dsm.getDose());
				sbStyrke.setProgress((int) dsm.getStyrke());
				sbMengde.setProgress((int) dsm.getMengde());
				
				
				
				
				
			}
		});
	}
	
	
	//hva som skjer når spinner for dose endres (endre benevning på dose)
	//TODO spinner dose
	private void spinnerDose(){
		spDose.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("DoseStyrkeMengdeActivity", "valgte int for dose er "+ arg2);
				switch (arg2) {
				case 0:
					doseType=DoseType.MG;
					
					break;
					
				case 1:
					doseType=DoseType.GR;
					
					break;
					
				case 2:
					doseType=DoseType.MicroGR;
					//ikke lagt inn som et valg ennå.
					
					break;

				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
	
				
			}
		});
	}
	
	
	//hva som skjer når spinner for styrke endres (endre benevning på styrke)
	//TODO spinner styrke
	private void spinnerStyrke(){
		spStyrke.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("DoseStyrkeMengdeActivity", "valgte int for Styrke er "+ arg2);
				switch (arg2) {
				case 0:
					styrkeType=StyrkeType.MgMl;
					
					break;
					
				case 1:
					styrkeType=StyrkeType.MgDraape;
					
					break;
					

				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
				
			}
		});
	}
	
	
	//hva som skjer når spinner for styrke endres (endre benevning på styrke)
		//TODO spinner styrke
	private void spinnerMengde(){
		spMengde.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("DoseStyrkeMengdeActivity", "valgte int for Mengde er "+ arg2);
				switch (arg2) {
				case 0:
					mengdeType =MengdeType.Ml;
					
					break;
					
				case 1:
				mengdeType=MengdeType.Draaper;
					
					break;
					
				case 2:
					
				mengdeType=MengdeType.Liter;
				
				break;

				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
				
			}
		});
	}


	
	

}
