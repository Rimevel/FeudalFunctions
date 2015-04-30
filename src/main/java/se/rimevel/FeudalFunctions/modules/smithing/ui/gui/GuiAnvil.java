package se.rimevel.FeudalFunctions.modules.smithing.ui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.ui.gui.GuiContainerBase;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerAnvil;

public class GuiAnvil extends GuiContainerBase
{
	TileEntityAnvil tile;
	
	public GuiAnvil(InventoryPlayer invPlayer, TileEntityAnvil tile)
	{
		super(new ContainerAnvil(invPlayer, tile), 176, 186);
		this.tile = tile;
	}
	
	private static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/gui/anvil_gui.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		drawBase(texture);
	}
}
