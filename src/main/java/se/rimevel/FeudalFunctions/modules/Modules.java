package se.rimevel.FeudalFunctions.modules;

import se.rimevel.FeudalFunctions.core.modules.MModule;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.survival.MSurvival;

/**
 * This enum handles all modules. Do not forget to register your modules here or they won't activate!
 */
public enum Modules
{
	SURVIVAL(new MSurvival()),
	SMITHING(new MSmithing());
	
	private MModule module;
	
	private Modules(MModule module)
	{
		this.module = module;
	}
	
	public MModule getModule()
	{
		return this.module;
	}
}
