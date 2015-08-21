package psyche.androidapp.workcalcs;


public class Calcs 
{
	public static final float SEMI_FAT = 1.538f;
	public static final float SKIM_FAT = 0.09f;
	public static final float STD_FAT = 3.535f;
	
	public static float fat(float[] fats, float[] vols)
	{
		float volume = 0;
		float x = 0;
		float result;
		
		
		for(int i = 0; i < fats.length; i++ )
		{
			volume += vols[i];
			x += fats[i] * vols[i];
		}
		result = x / volume;
		
		return result;
	}
	
	public static float fpd(float[] fpds, float[] vols)
	{
		float volume = 0;
		float x = 0;
		float result;
			
		for(int i = 0; i < fpds.length; i++ )
		{
			volume += vols[i];
			x += fpds[i] * vols[i];
		}
		result = x / volume;
		
		return result;		
	}
	
	public static String adjustFatString(float curFat, float curVol, float intendedFat, float adjustFat)
	{
		int result = 0;
		String comment ="";
		if(curFat > intendedFat)
		{
			result = (int)(curVol *(curFat - intendedFat)/(intendedFat - adjustFat));
			comment = " Skim Required";
		}
		else if(curFat < intendedFat)
		{
			result = (int)(curVol *(intendedFat - curFat)/(adjustFat - intendedFat));
			comment = " FCM Required";
		}
		
		
		return result + comment;
	}
	
	public static int adjustFat(float curFat, float curVol, float intendedFat, float adjustFat)
	{
		int result = 0;
		
		if(curFat > intendedFat)
		{
			result = (int)(curVol *(curFat - intendedFat)/(intendedFat - adjustFat));
		}
		else if(curFat < intendedFat)
		{
			result = (int)(curVol *(intendedFat - curFat)/(adjustFat - intendedFat));
		}
		
		
		return result;
	}
	
	
}
