package se.rimevel.FeudalFunctions.modules.survival.blocks;

import se.rimevel.FeudalFunctions.core.blocks.ModBlock;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityCampfire;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityGround;

public class SurvivalBlocks
{
	public static final ModBlock<BlockCampfire> campfire = new ModBlock<BlockCampfire>("campfire", BlockCampfire.class, TileEntityCampfire.class);
	public static final ModBlock<BlockCampfire> campfire_active = new ModBlock<BlockCampfire>("campfire_active", BlockCampfire.class, TileEntityCampfire.class);
	public static final ModBlock<BlockGround> groundPickup = new ModBlock<BlockGround>("ground", BlockGround.class, TileEntityGround.class);
}