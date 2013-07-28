package widget;

import no.knutinge.Infusionnurse.R;

import no.priv.larsgard.calculation.SnittFart;
import no.priv.larsgard.calculation.Utregninger;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DropRateWidget extends Activity {

	
	//For drop rate
	private Button trykk, trykkReset;
	private TextView outputDrSek, outputDrMin, outputMlT;
	
	private SnittFart snittFart;
	double tempFart=0;
	
	private Utregninger utregning;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drop_rate_widget);
	
	//DROP RATE

	trykk=(Button) findViewById(R.id.button1DraapeTakt);
	outputDrSek=(TextView) findViewById(R.id.textViewDrSek);
	outputDrMin=(TextView) findViewById(R.id.textViewDrMin);
	outputMlT=(TextView) findViewById(R.id.textViewMlT);
	
	
	
	
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
	
	//Testing for git
	
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
}
