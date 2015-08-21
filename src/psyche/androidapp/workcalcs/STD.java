package psyche.androidapp.workcalcs;

import static psyche.androidapp.workcalcs.Calcs.*;
import static psyche.androidapp.workcalcs.Utilities.*;

public class STD 
{
	public static float newSTD(float siloVol, float rawFat)
	{
		float intFats[] = { 0 };
		float intVols[] = { 0 };
		return newSTD(siloVol, rawFat, intFats, intVols);
	}
	
	public static float newSTD(float siloVol, float rawFat, float[] intFats, float[] intVols)
	{
		float interfaceSTDVol = interfaceAdjust(intFats, intVols, rawFat);
		float newSiloVol = siloVol - interfaceSTDVol;
		rawFat -= 0.01f;
		float totalFCM = newSiloVol * (STD_FAT - SKIM_FAT) / (rawFat - SKIM_FAT);
		
		if(lowInterface(fat(intFats, intVols)))
			totalFCM += (interfaceSTDVol - sumArray(intVols));
		
		return totalFCM;
	}
	
	public static float newSTD(float siloVol, float[] rawFats, float rawVol1)
	{
		float intFats[] = { 0 };
		float intVols[] = { 0 };
		return newSTD(siloVol, rawFats, rawVol1, intFats, intVols);
	}

	
	public static float newSTD(float siloVol, float[] rawFats, float rawVol1, float[] intFats, float[] intVols)
	{
		float interfaceSTDVol = interfaceAdjust(intFats, intVols,rawFats[0]);
		float intFCM;
		if(lowInterface(fat(intFats, intVols)))
			intFCM = interfaceSTDVol - sumArray(intVols);
		else
			intFCM = 0;
		
		rawVol1 -= intFCM;
		
		for(int i = 0; i < rawFats.length; i++)
			rawFats[i] -= 0.01f;
		
		float newSiloVol = siloVol - interfaceSTDVol -(rawVol1 + (float)adjustFat(rawFats[0], rawVol1, STD_FAT, SKIM_FAT));
		float rawVol2 = newSiloVol * (STD_FAT - SKIM_FAT) / (rawFats[1] - SKIM_FAT);
		float totalFCM = rawVol1 + rawVol2 + intFCM;
		return totalFCM;
	}
	
	
	private static float interfaceAdjust(float[] intFats, float[] intVols, float adjFat) 
	{
		if(intVols[0] == 0)
			return 0;
		
		float intFat = fat(intFats,  intVols);
		float intVol = 0;
		for(float i: intVols)
			intVol += i;	
		
		float volToMakeSTD;
		if(highInterface(intFat))
			volToMakeSTD = (float)adjustFat(intFat, intVol, STD_FAT, 0.1f);
		else if(lowInterface(intFat))
			volToMakeSTD = (float)adjustFat(intFat, intVol, STD_FAT, adjFat);
		else
			volToMakeSTD = intVol;
			
		float adjVol = volToMakeSTD + intVol;
		
		return adjVol;
	}
	
	private static boolean highInterface(float f)
	{
		if(f > 3.55f)
			return true;
		else 
			return false;
	}
	
	private static boolean lowInterface(float f)
	{
		if(f <= 3.52f)
			return true;
		else
			return false;
	}
	
	
	
}
