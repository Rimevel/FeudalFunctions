package se.rimevel.FeudalFunctions.modules.smithing.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import se.rimevel.FeudalFunctions.core.items.ItemBase;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataBloomType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBloom extends ItemBase
{
	private static ArrayList<DataBloomType> bloomData;
	
	public ItemBloom()
	{
		super();
		setNoRepair();
		setMaxStackSize(1);
		setNoTexture(false);
	}
	
	public static void initOreData()
	{
		if(bloomData != null){return;}
		
		bloomData = new ArrayList<DataBloomType>();
		bloomData.add(new DataBloomType("copper", "Copper", new ItemStack(MSmithing.items.ingot.getInstance(), 1, 0), 500, "oreCopper"));
		bloomData.add(new DataBloomType("tin", "Tin", new ItemStack(MSmithing.items.ingot.getInstance(), 1, 1), 500, "oreTin"));
		bloomData.add(new DataBloomType("iron", "Iron", new ItemStack(Items.iron_ingot), 500, "oreIron"));
		bloomData.add(new DataBloomType("gold", "Gold", new ItemStack(Items.gold_ingot), 500, "oreGold"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check)
	{
		if(stack != null)
		{
			DataBloomType data = getBloomData(stack);
			if(data != null)
			{
				list.add((data.amount / 10) + "% purity");
			}
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		if(stack != null)
		{
			DataBloomType data = getBloomData(stack);
			if(data != null)
			{
				return data.name + " Bloom";
			}
		}
		return "Bloom";
	}
	
	public static boolean initCompound(ItemStack stack)
	{
		if(!stack.hasTagCompound())
		{
			stack.stackTagCompound = new NBTTagCompound();
			return true;
		}
		
		return false;
	}
	
	public static boolean isValidOre(ItemStack stack)
	{
		for (DataBloomType data : bloomData)
		{
			for (String oreName : data.oreNames)
			{
				if(UtilOreDict.compareItem(stack, oreName))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static String getMatchingTypeName(ItemStack oreStack)
	{
		for (DataBloomType data : bloomData)
		{
			for (String oreName : data.oreNames)
			{
				if(UtilOreDict.compareItem(oreStack, oreName))
				{
					return data.typeName;
				}
			}
		}
		
		return null;
	}
	
	public static ItemStack createBloom(ItemStack oreStack)
	{
		ItemStack stack = new ItemStack(MSmithing.items.bloom.getInstance(), 1);
		initCompound(stack);
		for (DataBloomType d : bloomData)
		{
			for (String oreName : d.oreNames)
			{
				if(UtilOreDict.compareItem(oreStack, oreName))
				{
					stack.getTagCompound().setString("TypeName", d.typeName);
					stack.getTagCompound().setString("Name", d.name);
					stack.getTagCompound().setInteger("Amount", d.amount);
					
					NBTTagCompound compound = new NBTTagCompound();
					stack.getTagCompound().setTag("Result", d.result.writeToNBT(compound));
					return stack;
				}
			}
		}
		
		return null;
	}
	
	public static DataBloomType getBloomData(ItemStack stack)
	{
		if(stack.getTagCompound().hasKey("TypeName"))
		{
			NBTTagCompound nbt = stack.getTagCompound();
			return new DataBloomType(nbt.getString("TypeName"), nbt.getString("Name"), ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Result")), nbt.getInteger("Amount"));
		}
		
		return null;
	}

	public static boolean increaseAmount(ItemStack stack, int amount)
	{
		if(stack != null && stack.getItem() instanceof ItemBloom)
		{
			DataBloomType data = getBloomData(stack);
			
			int newAmount = data.amount + amount;
			
			if(data.amount >= 1000)
			{
				return false;
			}
			
			if(data.amount + amount > 1000)
			{
				stack.getTagCompound().setInteger("Amount", 1000);
				return true;
			}
			
			stack.getTagCompound().setInteger("Amount", data.amount + amount);
			return true;
		}
		
		return false;
	}
	
	public static boolean isSameType(ItemStack stack, ItemStack bloomStack)
	{
		if(stack != null && bloomStack != null)
		{
			if(!(bloomStack.getItem() instanceof ItemBloom)){return false;}
			
			DataBloomType data = ItemBloom.getBloomData(bloomStack);
			String oreStackType = ItemBloom.getMatchingTypeName(stack);
			
			if(data != null && oreStackType != null)
			{
				if(data.typeName == oreStackType)
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
