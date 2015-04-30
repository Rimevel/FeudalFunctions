package se.rimevel.FeudalFunctions.core.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

/**
 * Use this class to create a new command.
 */
public class ComBase extends CommandBase
{
	private String name;
	private String desc;
	
	public ComBase(String name, String desc)
	{
		this.name = name;
		this.desc = desc;
		
	}
	
	/**
	 * Register a command to the servers command manager. Need to be run once and only once for each command!
	 */
	public static void registerCommand(ComBase command)
	{
		ServerCommandManager manager = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();
		manager.registerCommand(command);
	}
	
	/**
	 * Get the name of the command.
	 */
	@Override
	public String getCommandName()
	{
		return name;
	}

	/**
	 * Get the description of the command.
	 */
	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return desc;
	}

	/**
	 * Called by the game when the command is triggered. args is an array of extra arguments added after the command name.
	 * Example: /commandname arg1 arg2
	 */
	@Override
	public void processCommand(ICommandSender icommandsender, String[] args)
	{
		
	}
	
	@Override
	public int compareTo(Object o)
	{
		return 0;
	}
}
