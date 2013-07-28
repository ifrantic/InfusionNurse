package widget;

import no.knutinge.Infusionnurse.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class InfusionNurseWidgetProvider extends AppWidgetProvider {
	

	//For drop rate
	private static final String START_DROP_RATE_WIDGET ="no.priv.larsgard.widget.DropRate";
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    if(intent.getAction().equals(START_DROP_RATE_WIDGET)) {

	        Intent i = new Intent(context, DropRateWidget.class);
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(i);

	      }
		super.onReceive(context, intent);
		
		
	}
	
	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		for (int i = 0; i < appWidgetIds.length; i++) {
			int appWidgetID=appWidgetIds[i];
			
			Intent intent=new Intent(context, DropRateWidget.class);
			intent.setAction(START_DROP_RATE_WIDGET);
			PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
			
			RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.drop_rate_widget);
			views.setOnClickPendingIntent(R.id.button1DraapeTakt, pending);
			
			appWidgetManager.updateAppWidget(appWidgetID, views);
				
			
		}
		
		
		
	}




}
