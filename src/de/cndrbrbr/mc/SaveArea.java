package de.cndrbrbr.mc;
// (c)cndrbrbr 2020

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class SaveArea extends JavaPlugin implements Listener{
	
	Logger log = null;
	boolean IsActive = false;
	stateList state = null;
	
	public void onEnable(){ 
				
		log = this.getLogger();
		state = new stateList(log);
		
		log.info("cndrbrbr Savearea has been enabled.");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable(){ 
		this.getLogger().info("cndrbrbr Savearea has been disabled.");
	}


	 @EventHandler
	 public void onPlayerMove(PlayerMoveEvent event) {
		 if (IsActive == true) {
			 Player player = event.getPlayer();
			 String playername = player.getName();
	 		 Location to = event.getTo();
			 Location from = event.getFrom();
			 int x = to.getBlockX();	
			 int z = to.getBlockZ();
			 
			 log.info(playername + " moves to " + x + "," + z);
			 
			 if (!state.CanMove2(x, z, playername)) {			 
					 event.setCancelled(true);
				 }
			 }
			 
			 
	 }

	 
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

		this.getLogger().info("Activted "+ cmd.getName() + " for " + sender);
		if (sender instanceof Player) {
			String playername = sender.getName();
			Player player = (Player)sender;
				
			String cmdname = cmd.getName();
			
			if (!IsValidCommand(cmdname)) return false;
			
			switch (cmdname)  {
				case "svon":				
				{
					IsActive =true;
					sender.sendMessage("Schutzzonen an");	
					return true;
							
				} 
				case "svoff":				
				{
					IsActive =false;
					sender.sendMessage("Schutzzonen aus");	
					return true;
							
				} 
				case "svpos": 
				{
					String result = state.CommandOn(cmd.getName(),null,playername);
					areaParams istate = state.GetState(playername); 
					istate.resetPoints();
		
					sender.sendMessage(result);	
					return true;
							
				} 
				case "svpos1":
				case "svpos2":
				{
					String result = state.CommandOn(cmd.getName(),null,playername);
		
					sender.sendMessage(result);	
					return true;
							
				} 
				case "invite":
				{
					String result = state.CommandOn(cmd.getName(),args,playername);
					sender.sendMessage(result);				
					return true;
				}
			}
		}
		return false;
			
	}
	public boolean IsValidCommand (String name)
	{
		switch (name)  {
		case "svpos":
		case "svpos1":
		case "svpos2":
		case "svinvite":
		case "svon":
		case "svoff": return true;
		default: return false;
		}
	
	}

	
	 @EventHandler
	 public void onBlockPlaceEvent (BlockPlaceEvent event) {
		 
		 this.getLogger().info("onBlockPlaceEvent !!!");	
		 this.getLogger().info(".");
		 Player player = event.getPlayer(); if (player == null) return;
		 String playername = player.getName(); if (playername == null) return;
		 Location playerplace = player.getLocation(); if (playerplace == null) return;

		 this.getLogger().info("..");
	
		 Block clickedblock =  event.getBlockPlaced(); if (clickedblock == null) return;
		 
		 Location blockpos = clickedblock.getLocation(); if (blockpos == null) return;
		 areaParams istate = state.GetState(playername); if (istate == null) return;
		 Location clickpos =  clickedblock.getLocation();
		 if (clickpos == null) {
			 this.getLogger().info("clickpos is null");
			 return;
		 }
		 int x = clickpos.getBlockX();
		 int z = clickpos.getBlockZ();
		 
		 this.getLogger().info("...");
		 this.getLogger().info("onBlockPlaceEvent !!!");	
			

		 
		 if  (  (istate.IsCommandEq("svpos")) 
			 || (istate.IsCommandEq("svpos1")) 
			 || (istate.IsCommandEq("svpos2")))
		 {
			 this.getLogger().info(istate.getLastcommand() + " selected !!!");
			 String result = istate.AddPoint(x, z);
			 player.sendMessage(result);			
		 } 
			
		 

				 


		 
	 }
	

}