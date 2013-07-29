package no.knutinge.Infusionnurse.calculation;
//Denne er ikke i bruk, fikk ikke helt til.


//testing i DoseStyrkeMengdeActivity
/*
double d=dsmUtregning.setDose(dsm.getDose(), doseType);
//Dette er dosen som skal vises. Tar høyde for benevning.
d=utregning.setFireDesimaler(d);
double s=dsmUtregning.setStyrke(dsm.getStyrke(), styrkeType);
//Dette er styrken som skal vises. Tar høyde for benevning.
s=utregning.setFireDesimaler(s);
double m=dsmUtregning.setMengde(dsm.getMengde(), mengdeType);
m=utregning.setFireDesimaler(m);
*/

//		tvDose.setText(getResources().getString(R.string.dose)+ d);
//		tvStyrke.setText(getResources().getString(R.string.styrke)+ s);
//		tvMengde.setText(getResources().getString(R.string.mengde)+ m);

import no.knutinge.Infusionnurse.type.DoseType;
import no.knutinge.Infusionnurse.type.MengdeType;
import no.knutinge.Infusionnurse.type.StyrkeType;

public class DoseStyrkeMengdeUtregning {
	private DoseStyrkeMengde dsm;
	private Utregninger utregning;
	
	private double dose=1;
	private double styrke=1;
	private double mengde=1;
	
	private DoseType dType=DoseType.MG;
	private StyrkeType sType=StyrkeType.MgMl;
	private MengdeType mType=MengdeType.Ml;

	
	public DoseStyrkeMengdeUtregning(
			//Kall denne helt i starten av programmet, slik at man har et utgangspunkt / får initialisert
			//benevningene med startverdier, slik at man ser endringer.
			DoseStyrkeMengde _dsm, DoseType doseType, StyrkeType styrkeType, MengdeType mengdeType){
		setdType(doseType);
		setsType(styrkeType);
		setmType(mengdeType);
		setDsm(_dsm);
		
	}




	public double getDose() {
		return dose;
	}



//endrer fra den typen som er satt her fra før, til ny type
	public double setDose(double dose, DoseType tilType) {
	
		if (dType==tilType) {
			this.dose = dose;
		}
		else if (dType==DoseType.MG && tilType==DoseType.GR) {
			this.dose=dose/1000;
		}
		else if (dType==DoseType.GR && tilType==DoseType.MG) {
			this.dose=dose*1000;
		}
		
		//Endrer "nåværende" type til den nye typen det er endret til
		dType=tilType;
		
		return this.dose;
		
	}




	public double getStyrke() {
		return styrke;
	}




	public double setStyrke(double styrke, StyrkeType tilType) {
		
		if (sType==tilType) {
			//Hvis det ikke er endringer i benevning
			this.styrke=styrke;
		}
		else if (sType==StyrkeType.MgMl && tilType==StyrkeType.MgDraape) {
			//endrer fra ml til dråper
			 this.styrke=styrke*20;
		}
		
		else if (sType==StyrkeType.MgDraape && tilType==StyrkeType.MgMl) {
			//endrer fra dråper til ml
			 this.styrke=styrke/20;
		}
		
		sType=tilType;
		
		return this.styrke;
	}




	public double getMengde() {
		return mengde;
	}




	public double setMengde(double mengde, MengdeType tilType ) {
		
		if (mType==tilType) {
			//ikke gjør noe
			this.mengde = mengde;
		}
		else if (mType ==MengdeType.Ml && tilType==MengdeType.Draaper) {
			this.mengde=mengde*20;
		} 
		else if(mType ==MengdeType.Ml && tilType==MengdeType.Liter) {
			this.mengde=mengde/1000;
		}
		
		//setter nåværende type til den nye typen
		mType=tilType;
		
		return this.mengde;
	}




	public DoseStyrkeMengde getDsm() {
		return dsm;
	}




	public void setDsm(DoseStyrkeMengde dsm) {
		this.dsm = dsm;
	}




	public DoseType getdType() {
		return dType;
	}




	public void setdType(DoseType dType) {
		this.dType = dType;
	}




	public StyrkeType getsType() {
		return sType;
	}




	public void setsType(StyrkeType sType) {
		this.sType = sType;
	}




	public MengdeType getmType() {
		return mType;
	}




	public void setmType(MengdeType mType) {
		this.mType = mType;
	}

}
