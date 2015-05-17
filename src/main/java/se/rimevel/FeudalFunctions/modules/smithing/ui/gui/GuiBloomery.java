package se.rimevel.FeudalFunctions.modules.smithing.ui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.ui.gui.GuiContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerBloomery;

public class GuiBloomery extends GuiContainerBase
{
	private TileEntityBloomery tile;
	
	public GuiBloomery(InventoryPlayer invPlayer, TileEntityBloomery tile)
	{
		super(new ContainerBloomery(invPlayer, tile), 176, 186);
		this.tile = tile;
	}
	
	private static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/gui/bloomery_gui.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		drawBase(texture);
		if(tile.isBurning())
		{
			drawTexturedModalRect(guiLeft + 71, guiTop + 15, 176, 0, 34, 14);
		}
		if(tile.progress > 0)
		{
			drawTexturedModalRect(guiLeft + 81, guiTop + 54, 0, 186, 14, UtilMath.scaleFloat(tile.progress, 0, 800, 13));
		}
	}
}
