// (c)cndrbrbr 2018 
package de.cndrbrbr.mc;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Material;

public class stateList {

	private Logger log = null;
	private Map<String, areaParams> playerz = new HashMap<String, areaParams>();

	public  stateList (Logger alog)
	{
		log = alog;
	}
	
	public String CommandOn (String cmd, String[] args, String player) 
	{
		String result;
		areaParams state = GetState(player);
		if (state == null) {
			state = new areaParams(log);
			result = state.CommandOn(cmd,args,player);
			playerz.put(player, state);
		}
		else result = state.CommandOn(cmd,args,player);
		
		return result;
	}
	
	public areaParams GetState(String player)
	{
		areaParams state;
		if (playerz.containsKey(player)) {
			state = playerz.get(player);
			return state;
		} else {
			return null;
		}
	}
	
	public void CommandOff (String player) 
	{
		if (playerz.containsKey(player)) {
			playerz.remove(player);		
		} 		
	}
	
	public boolean CanMove2 (int x, int z, String playername)
	{
		
		 for (Map.Entry<String,areaParams> entry : playerz.entrySet())  
		 {   
			 areaParams st = entry.getValue();
			 String name = entry.getKey();
			// System.out.println("Key = " + name +", Value = " + st);
			if (!st.allowed2enter(playername)){
				 boolean erg = st.CanMove2 (x,z);
				 if (!erg) return false;
			}
		 }
		 return true;
		 
	}
	
	boolean IsCommandActive (String player, String command)
	{
		
		areaParams state;
		 //log.info("internalCommandState "+ player + ", player "+ command );

		if (playerz.containsKey(player)) {
			state = playerz.get(player);
			return state.IsCommandEq(command);
		} else {
			return false;
		}
	}

}
