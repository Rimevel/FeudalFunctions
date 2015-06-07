package se.rimevel.FeudalFunctions.core.potions;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.items.ItemBase;
import se.rimevel.FeudalFunctions.core.util.UtilReflection;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

/**
 * Handler for easier creation and registering of new potions.
 */
public class PotionHandler
{
	private static PotionHandler instance;
	
	private static int currentId;
	private static int customIdAmount;
	
	private static final ResourceLocation potionTexture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/misc/potions.png");
	
	private PotionHandler(){}
	
	public static PotionHandler getInstance()
	{
		if(instance == null)
		{
			instance = new PotionHandler();
		}
		return instance;
	}
	
	public static ResourceLocation getPotionTexture()
	{
		return potionTexture;
	}
	
	/**
	 * Initialize all the important data needed to register potions. THIS SHOULD ONLY BE CALLED ONCE FROM ModCore!
	 */
	public void initPotions()
	{
		int offset = Potion.potionTypes.length;
		
		if(offset < 128 - customIdAmount)
		{
			Potion[] potionTypes = new Potion[offset + customIdAmount];
			System.arraycopy(Potion.potionTypes, 0, potionTypes, 0, offset);
			UtilReflection.setPrivateFinalValue(Potion.class, null, potionTypes, new String[]{"potionTypes", "field_76425_a", "a"});
			currentId = offset++ -1;
		}
		else
		{
			currentId = -1;
		}
	}
	
	/**
	 * Increases the custom id count. This should be increased by one for each potion.
	 * @param amount
	 */
	public static void increaseCustomIdCount(int amount)
	{
		if(amount >= 0)
		{
			customIdAmount += amount;
		}
	}
	
	public static int getNextPotionId()
	{
		if(Potion.potionTypes != null && currentId > 0 && currentId < Potion.potionTypes.length && Potion.potionTypes[currentId] == null)
		{
			return currentId;
		}
		
		currentId += 1;
		
		if(currentId < 128)
		{
			currentId = getNextPotionId();
		}
		else
		{
			currentId = -1;
		}
		return currentId;
	}
}
