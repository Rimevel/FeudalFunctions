package se.rimevel.FeudalFunctions.core.proxies;

import se.rimevel.FeudalFunctions.modules.Modules;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy
{
    public void register()
    {
    	for (Modules m : Modules.values())
		{
			m.getModule().onCommonProxy();
		}
    }
    
    /**
     * Returns a side-appropriate EntityPlayer for use during message handling
     */
	public EntityPlayer getPlayerEntity(MessageContext ctx)
	{
		return ctx.getServerHandler().playerEntity;
	}

	public void registerKeys()
	{
		
	}
}
