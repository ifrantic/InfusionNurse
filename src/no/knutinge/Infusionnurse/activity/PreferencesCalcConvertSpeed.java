package no.knutinge.Infusionnurse.activity;

//import no.knutinge.Infusionnurse.R;
import no.knutinge.Infusionnurse.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesCalcConvertSpeed extends PreferenceActivity {

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//les følgende:
		//https://developer.android.com/reference/android/preference/PreferenceActivity.html
		
		addPreferencesFromResource(R.xml.prefs_calc_convert_speed);
	}
	
}
