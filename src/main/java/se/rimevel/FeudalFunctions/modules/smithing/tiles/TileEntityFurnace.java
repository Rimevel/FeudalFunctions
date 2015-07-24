package se.rimevel.FeudalFunctions.modules.smithing.tiles;

import net.minecraft.world.World;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilVector;
import se.rimevel.FeudalFunctions.core.util.helpers.HelperVector;
import se.rimevel.FeudalFunctions.modules.smithing.blocks.BlockFurnace;

public class TileEntityFurnace extends TileEntityContainerBase
{
	private boolean isController;
	private int cX, cY, cZ;
	private boolean isWhole;
	
	private boolean active;
	private int fuel, progress;
	
	public TileEntityFurnace()
	{
		super(3);
	}
	
	@Override
	public void updateEntity()
	{
		resetState();
	}
	
	@Override
	public boolean canUpdate()
	{
		return isController;
	}
	
	public void setAsTileController()
	{
		this.isController = true;
		this.isWhole = true;
	}
	
	public boolean isTileController()
	{
		return isController;
	}
	
	public boolean isWhole()
	{
		return isWhole;
	}
	
	public void setControllerLocation(int x, int y, int z)
	{
		if(isController){return;}
		
		cX = x;
		cY = y;
		cZ = z;
	}
	
	public void checkIfWhole()
	{
		World world = this.worldObj;
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		if(!isTileController()){return;}
		
		for (int h = 1; h > -2; h--)
		{
			for (int l = -1; l < 2; l++)
			{
				for (int w = -1; w < 2; w++)
				{
					if(world.getBlock(x + w, y + h, z + l) instanceof BlockFurnace)
					{
						if(world.getBlockMetadata(x + w, y + h, z + l) == 0)
						{
							this.isWhole = false;
						}
					}
					else
					{
						this.isWhole = false;
					}
				}
			}
		}
		
		this.isWhole = true;
	}
	
	public void resetState()
	{
		if(isWhole || !isTileController()){return;}
		
		World world = this.worldObj;
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		for (int h = 1; h > -2; h--)
		{
			for (int l = -1; l < 2; l++)
			{
				for (int w = -1; w < 2; w++)
				{
					if(world.getBlock(x + w, y + h, z + l) instanceof BlockFurnace)
					{
						if(world.getBlockMetadata(x + w, y + h, z + l) > 0)
						{
							world.setBlockMetadataWithNotify(x + w, y + h, z + l, 0, 2);
						}
					}
				}
			}
		}
	}
	
	public TileEntityFurnace getController()
	{
		if(isTileController())
		{
			return this;
		}
		else
		{
			return (TileEntityFurnace) this.worldObj.getTileEntity(this.cX, this.cY, this.cZ);
		}
	}
}
