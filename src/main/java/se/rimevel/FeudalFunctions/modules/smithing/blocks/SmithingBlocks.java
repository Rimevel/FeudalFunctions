package se.rimevel.FeudalFunctions.modules.smithing.blocks;

import se.rimevel.FeudalFunctions.core.blocks.ModBlock;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemBlockOres;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityForge;

public class SmithingBlocks
{
	public static final ModBlock<BlockCrucible> crucible = new ModBlock<BlockCrucible>("crucible", BlockCrucible.class);
	public static final ModBlock<BlockAnvil> anvil = new ModBlock<BlockAnvil>("anvil_iron", BlockAnvil.class, TileEntityAnvil.class);
	public static final ModBlock<BlockForge> forge = new ModBlock<BlockForge>("forge", BlockForge.class, TileEntityForge.class);
	public static final ModBlock<BlockForge> forge_active = new ModBlock<BlockForge>("forge_active", BlockForge.class, TileEntityForge.class);
	public static final ModBlock<BlockOres> ore = new ModBlock<BlockOres>("ore", BlockOres.class, ItemBlockOres.class);
}
