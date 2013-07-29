package no.knutinge.Infusionnurse.widget;


//Dette er broadcastreciever for widgeten
import java.util.Random;

import no.knutinge.Infusionnurse.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class InfusionNurseWidgetProvider extends AppWidgetProvider {
	

	//For drop rate
	//private static final String START_DROP_RATE_WIDGET ="no.priv.larsgard.widget.DropRate";
	
	private static final String CLICKED_DROP_RATE="CLICKED_DROP_RATE";
	
	
	
	//Denne må være her tror jeg, tror er ok?
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		// Get all ids
	    ComponentName thisWidget = new ComponentName(context,
	        InfusionNurseWidgetProvider.class);
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	    for (int widgetId : allWidgetIds) {
	      // Create some random data
	      int number = (new Random().nextInt(100));
		      
	      Log.v("WidgetExample", String.valueOf(number));
			//Intent intent=new Intent(context, DropRateWidget.class);
			//intent.setAction(START_DROP_RATE_WIDGET);
			//PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
			
			RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.drop_rate_widget);
		//	views.setOnClickPendingIntent(R.id.button1DraapeTakt, pending);
			
			//finner ikke denne ID !!!!!!! argh
			remoteViews.setTextViewText(R.id.wTextViewDrSek, "Hei "+ number);
			Log.v("widget", "trykket noe");
		    
		      
			
			//registrer en OnClickListener, dette er standard?
			Intent intent=new Intent(context, InfusionNurseWidgetProvider.class);
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			
			   PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				      remoteViews.setOnClickPendingIntent(R.id.wButton1DraapeTakt, pendingIntent);
				    appWidgetManager.updateAppWidget(widgetId, remoteViews);
				    
			//appWidgetManager.updateAppWidget(appWidgetID, views);
				
			
		}
		
		
		
	}



	@Override
	public void onEnabled(Context context) {
		
		super.onEnabled(context);
		Log.v("widgdet", "onEnabled");
	}




}
