// (c)cndrbrbr 2020 
package de.cndrbrbr.mc;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Material;



public class areaParams {

		private Logger log = null;
		private String  playername = null;
		private String lastcommand = null;
		List<String> friends = new ArrayList<String>();
		private int svposCount = 0;

		private int x1 = 0; private boolean x1set = false;
	    private int x2 = 0; private boolean x2set = false;
	    private int z1 = 0; private boolean z1set = false;
	    private int z2 = 0; private boolean z2set = false;
	    
	    boolean allset () {return (x1set && x2set && z1set && z2set);}
	
		areaParams(Logger alog)
		{
			log = alog;
			 
		}

		
		public void CommandOff ()
		{
			lastcommand = null;
			playername = null;
		}
		
		public String checkName(String str)
		{
			/// 2 Do!
			return str;
		}
		
		public String inviteFriends (String[] args)
		{
			String erg = "Invited Friend(s): ";
			for (int i = 0; i < args.length; i++) {
				String name = checkName(args[i]);
				if (erg!=null) {
					friends.add(name);
					erg.concat(name + ", ");
				}
			}
			return"";
		}
		
		public String CommandOn (String cmd, String[] args, String player)
		{
			String result = "ok";
			lastcommand = cmd;
			String matname = null;
			playername = player;
	//		log.info("args len = " + args.length);			
			
			
			switch (cmd)  {
			
				case "svon":				
				{		
					
				} break;
								
				case "svoff":				
				{		
					
				} break;

				case "svpos":				
				{		
					svposCount = 0;
					result = "Setze nacheinander die 2 Ecken Deines Schutzraumes mit je einem Block";
				} break;
								
				case "svpos1":				
				{		
					result =  "Setze die 1. Ecke Deines Schutzraumes mit einem Block";		
					
				} break;
				
				case "svpos2":				
				{		
					result =  "Setze die 2. Ecke Deines Schutzraumes mit einem Block";										
				} break;
				case "svinvite":				
				{	
					if (args != null) result = inviteFriends(args);
													
				} break;

			} 
			
			
			return result;
			
		}
		public boolean IsPlayerEq (String name)
		{
			if (playername != null) {
				if (playername.equalsIgnoreCase(name))
						return true;
			}
			else log.info("playername is null");
			return false;
			
		}
		
		public boolean IsCommandEq (String name)
		{
			if (lastcommand != null) {
				if (lastcommand.equalsIgnoreCase(name))
						return true;
			}
			else log.info("lastcommand is null");
			return false;
		}
		
		
	    public String getPlayername() {
			return playername;
		}


		public void setPlayername(String playername) {
			this.playername = playername;
		}


		public String getLastcommand() {
			return lastcommand;
		}


		public void setLastcommand(String lastcommand) {
			this.lastcommand = lastcommand;
		}

		String isGood()
		{
			Rectangle r1 = new Rectangle(x1,z1,x2-x1,z2-z1);
			String erg = "ok";
			// isEmpty()
			// Liste aller User Rechtecke ablaufen
		 	// contains(Rectangle r)
			// intersects(Rectangle r)
			return erg;
		}
	
	
		
		String AddPoint(int x, int z) 
		{
			String erg = "ok";
				
			if (allset() == true) { // erweitern/verkleinern des Rechtecks
				
				if (x1 != x) { 
				
					if (x1>x) { // liegt links 
						
						x1 = x;
					}
					
					if (x2<x ) {// liegt rechts
						
						x2 = x;
					}
					
					if ((x1 < x) && (x2 > x)) { //liegt dazwischen
						
						x2 = x;
					}
				}
				
				erg = "erweitert";
				
			}
			else { // ein punkt da
				
				if (x1set) {
					
					if (x1 < x) x2 = x;
					else { x2 = x1; x1 = x;}
					
					if (z1 < z) z2 = z;
					else { z2 = z1; z1 = z;}				 
					 
					x2set = true; 
					z2set = true;
					erg = "zweiten Punkt eingetragen" + x1 + "," + x2 + "|" + z1 + "," + z2 ;
					
					
				}
				else // noch nix gesetzt
				{
					x1 = x; z1 = z;
					x1set = true; 
					z1set = true;
					erg = "ersten Punkt eingetragen" + x1 + "," + x2 + "|" + z1 + "," + z2 ;
				}
			}
			
			return erg;
			
		}
		
		public boolean allowed2enter(String name)
		{
			
			if (name.equalsIgnoreCase(playername)) return true;
			for (int counter = 0; counter < friends.size(); counter++) { 		      
		          String theName = friends.get(counter);
		          if (theName.equalsIgnoreCase(name)) return true;
		    }
		    
			return false;
			
		}
		
		public void resetPoints()
		{
			x1 = 0;  x1set = false;
		    x2 = 0;  x2set = false;
		    z1 = 0;  z1set = false;
		    z2 = 0;  z2set = false;
		}

		public boolean CanMove2 (int x, int z)
		{
			 if ((x > x1) && (x < x2)) {
				if ((z > z1) && (z<z2)) {
					return false;
				}
			 }
			 return true;
		}
		

}
