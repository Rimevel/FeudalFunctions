package se.rimevel.FeudalFunctions.core.proxies;

import se.rimevel.FeudalFunctions.modules.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy
{
	@Override
	public void register()
	{
		for (Modules m : Modules.values())
		{
			m.getModule().onClientProxy();
		}
	}
	
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx)
	{
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work because you will be getting a client
		// player even when you are on the server! Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
}
