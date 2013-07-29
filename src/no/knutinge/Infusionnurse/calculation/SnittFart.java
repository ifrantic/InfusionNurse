package no.knutinge.Infusionnurse.calculation;


//Denne har en metode .addFart som tar en String som argument
//Dette er en long som er gjort om til en String

//Metoden .getSnittFart returnerer snittfarten representert i long.
//Det er snitt av de 4 siste fartene som er lagt til via .addFart, begynner ved 3. tastetrykk
import java.util.Iterator;
import java.util.LinkedList;


public class SnittFart{

	private double fart=0;
	//liste for å oppbevare tidspunkter, angitt i String. Disse er konvertert fra long...
	private LinkedList<String> tid;

	public SnittFart() {
		tid=new LinkedList<String>();
	}


	public void addFart(String milliSec){
	

		tid.add(milliSec);

		if (tid.size()>4) {
			String dummy=tid.remove();	//Brukes ikke
		}


	}

	public void setSnittFart(double fartInn){
		this.fart=fartInn;
		
	}
//fungerer
	public double getSnittFart(){
		
		double y=0;
		double z=0;
		double snitt=0;

		
		if (tid.size()>2) {//begynner utregning hvis 3 eller flere oppføringer i listen
			
	
			y=Double.parseDouble(tid.getFirst());
			z=Double.parseDouble(tid.getLast());
			snitt=(z-y)/1000;
			

			System.out.println("first "+y);
			System.out.println("last "+ z);

			System.out.println(snitt+" z-y");//TODO fjernes når testing er ferdig.

			fart=(tid.size()/snitt);//hvis det er mer enn 2 oppføringer å ta snittfart av. Oppgitt i sec
			System.out.println("antall "+ tid.size());
			System.out.println(fart+ " fart");
		}
		else{
			fart=0;//hvis mindre enn 2 oppføringer å ta snittfart av
		}

		fart=setDesimaler(fart);



		return fart;

	}


	//Denne er kun for testing
	public void skrivFarter(){
		Iterator<String> it=tid.iterator();
		System.out.println(tid.size()+" ant tider");
		while (it.hasNext()) {
			String string = (String) it.next();
			System.out.println(string);

		}
	}
	
	//Setter 2 desimaler på tallet
	public double setDesimaler(double d) {
		double result=0.00;
		try{
	result = d * 100;
	result = Math.round(result);
	result = result / 100;
		}
		catch(Exception e){
			
		}
	return result;
}


	public LinkedList<String> getTid() {
		return tid;
	}


	public void setTid(LinkedList<String> tid) {
		this.tid = tid;
	}


	public void reset() {
		tid.clear();
		
	}
}
