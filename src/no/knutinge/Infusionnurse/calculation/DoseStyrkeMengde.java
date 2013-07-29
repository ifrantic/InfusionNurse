package no.knutinge.Infusionnurse.calculation;
/*
 * KLasse for � sette dose/styrke mengde.
 * ALL INPUT ANTAS � V�RE I ML, MG OG TIMER....
 * */
import java.io.Serializable;

public class DoseStyrkeMengde implements Serializable {

	public DoseStyrkeMengde(){
		
	}
	
	//konstrukt�r med 2 startverdier, tenkt brukt for � sette alle 3 til 1
	public DoseStyrkeMengde(double dose, double styrke){
		setDose(dose);
		setStyrke(styrke);
		setMengde(dose, styrke);
	}
		/**
	 * 
	 */
	private static final long serialVersionUID = -2386291518587742728L;
		private double mengde=0;
		private double styrke;
		private double dose;
		
	
	


		public double getMengde() {
			return mengde;
		}


		public void setMengde(double mengde) {
			this.mengde = mengde;
		}

		public void setMengde(double dose, double styrke) {
			this.mengde = dose/styrke;
		}

		public double getStyrke() {
			return styrke;
		}


		public void setStyrke(double styrke) {
			this.styrke = styrke;
		}
		public void setStyrke(double dose, double mengde) {
			this.styrke = dose/mengde;
		}


		public double getDose() {
			return dose;
		}


		public void setDose(double dose) {
			this.dose = dose;
		}
		
		public void setDose(double styrke, double mengde) {
			this.dose = styrke*mengde;
		}


	}


