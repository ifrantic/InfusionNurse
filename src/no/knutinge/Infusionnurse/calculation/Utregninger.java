package no.knutinge.Infusionnurse.calculation;

import no.knutinge.Infusionnurse.type.DoseType;
import no.knutinge.Infusionnurse.type.MengdeType;
import no.knutinge.Infusionnurse.type.SpeedType;
import no.knutinge.Infusionnurse.type.StyrkeType;
import no.knutinge.Infusionnurse.type.TimeType;
import no.knutinge.Infusionnurse.type.VolumeType;

public class Utregninger {
	

	public DoseStyrkeMengde dsm;
	private double diluent, dilutionFluid;//diluent er fortynnet oppløsning, dilutionFluid er fortynningsvæske som tilsettes
	
	//variabler til regnHastighet
	private double ml, time, mlT, drMin, drSec;

	public Utregninger(){
	//	dsm=new 
		
	}
	
	//tar inn dose, styrke, mengde, samt fortynningsvæske i angitt volumtype som input
	//returnerer ny dose/styrke/mengde
	//dilutionFluid er fortynningsvæsken som tilstettes
	//ALL INPUT GJØRES OM TIL ML OG HOUR ETTER INPUT
public DoseStyrkeMengde fortynnLosning(DoseStyrkeMengde dsm, double dilutionFluid, VolumeType vType){
//TODO
	DoseStyrkeMengde _dsm=dsm;
	double volum=konverterVolumTilMl(dilutionFluid, vType)+_dsm.getMengde();
	
	_dsm.setMengde(volum);
	_dsm.setStyrke(_dsm.getDose(), _dsm.getMengde());
	return _dsm;	
}


//Infusjonshastighet. putt inn volum og tid, samt type volum og tid. regn ut hastigheter. 
//returnerer i ml/t
public double regnHastighet(double volum, double tid, VolumeType vType, TimeType tType){
	//0 verdier blir tatt hånd om i class som bruker denne metoden, så de to sjekkene
	//under brukes faktisk ikke. Lar de stå som en påminnelse.
	if (volum==0) {
		volum=0.01; //hindrer 0 verdi, fordi det blir krøll i utregningene
	}
	if (tid==0) {
		tid=0.01; //hindrer 0 verdi, fordi det blir krøll i utregningene
	}
	

	volum=konverterVolumTilMl(volum, vType);
	tid=konverterTidTilTime(tid, tType);
	
	return setDesimaler(volum/tid);//returnerer hastigheten i ml/t
	
	
}

//Returnerer gitt hastighet til ml / t
public double konverterHastighetTilMlT(double speed, SpeedType type){
	switch(type){
	case MLH:
	break;
		
	case DRMIN:
	
		speed=(speed/20)*60; //del på 20 for å få dr til ml, gange med 60 for å få min til time
		break;
		
	case DRSEK:
		
		speed=(speed/20)*3600; //del på 20 for å få dr til ml, gange med 3600 for å få sek til time
	
		break;
		
	default: 
		break;

	}
	
	return speed;
}

public double konverterMlTTilDrMin(double mlT){
	double drMin=(mlT/60)*20;
	
	return setDesimaler(drMin);
	
}
public double konverterMlTTilDrSek(double mlT){
	double drSek=(mlT/3600)*20;
	
	return setDesimaler(drSek);
}
public double konverterTidTilTime(double tid, TimeType tType){
	
	switch(tType){
	case HOUR:
		break;
	case MIN:
		tid=tid/60;
		break;
	case SEC:
		tid=tid/3600;
		break;
		
		default:
		break;
		
	}
	return tid;
	
}

public double konverterVolumTilMl(double volum, VolumeType vType){
	
	switch(vType){
	case DR:
		volum=volum/20;
		break;
	case LITER:
		volum=volum*1000;
		break;
	case ML:
		break;
	default:
		break;
	}
	
	
	
	return volum;
	
}

public double konverterDoseTilMg(double dose, DoseType dType){
	
	switch (dType) {
	case GR:
		dose=dose*1000;
		break;
	case MG:
		break;
	case MicroGR:
		dose=dose/1000;
		break;

	default:
		break;
	}
	
	return dose;
	
}


public double getDiluent() {
	return diluent;
}

//Må være ml
public void setDiluent(double diluent){
	this.diluent=diluent;
}

//Tar fortynningsvæske og type volum som argument, setter fortynningsvæske i ml
public void setDiluent(double diluent, VolumeType vType) {
	
	this.diluent=konverterVolumTilMl(diluent, vType);
}

public double getMlT() {
	return mlT;
}

public void setMlT(double mlT) {
	this.mlT = mlT;
}

public void setMlT(double volum, VolumeType vType, double tid, TimeType tType) {
	

volum=konverterVolumTilMl(volum, vType);
tid=konverterTidTilTime(tid, tType);
	
	this.mlT = volum/tid;
}

public double getDrMin() {
	return drMin;
}

public void setDrMin(double drMin) {
	this.drMin = drMin;
}

public void setDrMin(double volum, VolumeType vType, double tid, TimeType tType) {
	
	volum=konverterVolumTilMl(volum, vType);
	tid=konverterTidTilTime(tid, tType);
	
	this.drMin = (volum*20)/(tid*60);
}

public double getDrSec() {
	return drSec;
}

public void setDrSec(double drSec) {
	this.drSec = drSec;
}

public void setDrSec(double volum, VolumeType vType, double tid, TimeType tType) {
	
	volum=konverterVolumTilMl(volum, vType);
	tid=konverterTidTilTime(tid, tType);
	
	this.drSec = (volum*20)/(tid*3600);
}

public double getMl() {
	return ml;
}

public void setMl(double ml) {
	this.ml = ml;
}

public void setMl(double volum, VolumeType vType) {
	
	this.ml = konverterVolumTilMl(volum, vType);
}

public double getTime() {
	return time;
}

public void setTime(double time) {
	
	this.time = time;
}

public void setTime(double tid, TimeType tType) {
	
	this.time = konverterTidTilTime(tid, tType);
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

//Setter 4 desimaler på tallet
public double setFireDesimaler(double d) {
	double result=0.0000;
	try{
result = d * 10000;
result = Math.round(result);
result = result / 10000;
	}
	catch(Exception e){
		
	}
return result;
}

//MENGDE : KOnverterer fra en benevning til en annen, returnerer den nye verdien
public double konverterVolum(VolumeType fraType, VolumeType tilType, double fraVerdi){
	//TODO
	double tilVerdi=0;
	
	return tilVerdi;
	
}

//TID : Konverter fra en benevning til en annen, returnerer den nye verdien
public double konverterTid(TimeType fraType, TimeType tilType, double fraVerdi){
	//TODO
	double tilVerdi=0;
	
	return tilVerdi;
}


//TODO lage utregninger for dose, styrke og mengde
public void dsmTypes(DoseStyrkeMengde _dsm, DoseType doseType, StyrkeType styrkeType, MengdeType mengdeType){

	
	switch (styrkeType) {
	case MgMl:
		
		break;

	case MgDraape:
		break;
		
	default:
		break;
	}
	
	switch (mengdeType) {
	case Ml:
		
		break;
		
	case Draaper:
		break;
		
	case Liter:
		
		break;

	default:
		break;
	}
	
	
	
	
}































}
