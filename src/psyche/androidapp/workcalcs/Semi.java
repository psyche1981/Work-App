package psyche.androidapp.workcalcs;

import static psyche.androidapp.workcalcs.Calcs.*;
import static psyche.androidapp.workcalcs.Utilities.*;

public class Semi 
{	
	//just 1 raw
	public static float newSemi(float siloVol, float rawFat)
	{
		float intFats[] = { 0 };
		float intVols[] = { 0 };
		return newSemi(siloVol, rawFat, intFats, intVols);
	}
	
	//1 raw + interface(s)
	public static float newSemi(float siloVol, float rawFat, float[] intFats, float[] intVols)
	{		
		float interfaceSemiVol = interfaceAdjust(intFats, intVols, rawFat); //semi vol made from interface
		float newSiloVol = siloVol - interfaceSemiVol;
		rawFat -= 0.01f; // allowing for FT adjustment
		
		float totalFCM = newSiloVol * (SEMI_FAT - SKIM_FAT) / (rawFat - SKIM_FAT);
		
		if(lowInterface(fat(intFats,  intVols)))
			totalFCM += (interfaceSemiVol - sumArray(intVols));	//if int was adjusted with FCM, add it on to the total	
		return totalFCM;
	}
	
	//just 2 raw
	public static float newSemi(float siloVol, float[] rawFats, float rawVol1)
	{
		float intFats[] = { 0 };
		float intVols[] = { 0 };
		return newSemi(siloVol, rawFats, rawVol1, intFats, intVols);		
	}
	
	//2 raw + interface(s)
	public static float newSemi(float siloVol, float[] rawFats, float rawVol1, float[] intFats, float[] intVols)
	{		
		float interfaceSemiVol = interfaceAdjust(intFats, intVols, rawFats[0]); //semi vol made from interface
		float intFCM;
		if(lowInterface(fat(intFats, intVols)))
			intFCM = interfaceSemiVol - sumArray(intVols);
		else
			intFCM = 0;
		
		rawVol1 -= intFCM;
			
		for(int i = 0; i < rawFats.length; i++)
			rawFats[i] -= 0.01f;
		
		float newSiloVol = siloVol - interfaceSemiVol - (rawVol1 + (float)adjustFat(rawFats[0], rawVol1, SEMI_FAT, SKIM_FAT));		
		float rawVol2 = newSiloVol * (SEMI_FAT - SKIM_FAT) / (rawFats[1] - SKIM_FAT);
		float totalFCM = rawVol1 + rawVol2 + intFCM;
		
		return totalFCM;
	}
	
	//returns the total volume of interface + adjustment to make semi
	
	private static float interfaceAdjust(float[] intFats, float[] intVols, float adjFat) 
	{
		if(intVols[0] == 0)
			return 0;
		
		float intFat = fat(intFats,  intVols);
		float intVol = 0;
		for(float i: intVols)
			intVol += i;	
		
		float volToMakeSemi;
		if(highInterface(intFat))
			volToMakeSemi = (float)adjustFat(intFat, intVol, SEMI_FAT, 0.1f);
		else if(lowInterface(intFat))
			volToMakeSemi = (float)adjustFat(intFat, intVol, SEMI_FAT, adjFat);
		else
			volToMakeSemi = intVol;
			
		float adjVol = volToMakeSemi + intVol;
		
		return adjVol;
	}
	
	private static boolean highInterface(float f)
	{
		if(f > 1.55f)
			return true;
		else 
			return false;
	}
	
	private static boolean lowInterface(float f)
	{
		if(f <= 1.52f)
			return true;
		else
			return false;
	}
	
	
}
