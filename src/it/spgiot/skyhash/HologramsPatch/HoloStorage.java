package it.spgiot.skyhash.HologramsPatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import de.inventivegames.hologram.view.ViewHandler;
import me.clip.placeholderapi.PlaceholderAPI;


public class HoloStorage {
	
	private final List<Hologram> PROTECTED_HOLOS = new ArrayList();
	  
	  public void add(Hologram s)
	  {
	    if (!this.PROTECTED_HOLOS.contains(s)) {
	      this.PROTECTED_HOLOS.add(s);
	    }
	  }
	  
	  public void remove(Hologram s)
	  {
	    this.PROTECTED_HOLOS.remove(s);
	  }
	  
	  public void saveChunks()
	    throws IOException
	  {
	    File file = new File(Main.getSignDataFolder() + File.separator + "protectedholos.flat");
	    if (this.PROTECTED_HOLOS.isEmpty()) {
	      return;
	    }
	    FileWriter fstream = new FileWriter(file);
	    BufferedWriter out = new BufferedWriter(fstream);
	    for (Hologram holo : this.PROTECTED_HOLOS)
	    {
	      out.write("[Holos Data]");
	      out.newLine();
	      String s = holo.getText();
	      String b = s.replaceAll("§", "COLORCODE");
	      out.write("Name:" + getKeyByValue(Main.getHolos(), holo));
	      out.newLine();
	      out.write("WorldLoc:" + holo.getLocation().getWorld().getName());
	      out.newLine();
	      out.write("XLoc:" + holo.getLocation().getBlockX());
	      out.newLine();
	      out.write("YLoc:" + holo.getLocation().getBlockY());
	      out.newLine();
	      out.write("ZLoc:" + holo.getLocation().getBlockZ());
	      out.newLine();
	      out.write("Text:" + b);
	      out.newLine();
	      out.write("LineNumbers:" + holo.getLinesBelow().size());
	      out.newLine();
	      for(Hologram hz : holo.getLinesBelow())
	      {
		      String g = hz.getText();
		      String f = g.replaceAll("§", "COLORCODE");
	    	  out.write("Line:" + f);
		      out.newLine(); 
	      }
	    }
	    out.close();
	  }
	  
	  public void loadHolo()
	    throws IOException
	  {
	    File file = new File(Main.getSignDataFolder() + File.separator + "protectedholos.flat");
	    if (!file.exists()) {
	      return;
	    }
	    FileReader fstream = new FileReader(file);
	    BufferedReader reader = new BufferedReader(fstream);
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	      if (line.startsWith("[Holos Data]"))
	      {
	        String Name = reader.readLine().split(":")[1];
	        String world = reader.readLine().split(":")[1];
	        int x = Integer.parseInt(reader.readLine().split(":")[1]);
	        int y = Integer.parseInt(reader.readLine().split(":")[1]);
	        int z = Integer.parseInt(reader.readLine().split(":")[1]);
	        String f = reader.readLine().split(":")[1];
	        List<String> linesText = new ArrayList();
	        int lines = Integer.parseInt(reader.readLine().split(":")[1]);
	        for(int i = 0;i<lines;i++)
	        {
	        	String color = reader.readLine().split(":")[1];
	        	String defColor = color.replaceAll("COLORCODE", "§");
	        	linesText.add(defColor);
	        }
	        String zDesc = f.replaceAll("COLORCODE", "§");
	        Location loc = new Location(Bukkit.getWorld(world),x,y,z);
	        Hologram h = HologramAPI.createHologram(loc,zDesc);
	        h.addViewHandler(new ViewHandler() {

                @Override
                public String onView(Hologram hologram, Player player, String string) {
                	String parsed = PlaceholderAPI.setPlaceholders(player, string);
                	return parsed;
                }
            });
	        h.spawn();
	        //Worst for cycle i've ever done but that's the only solution without editing the API.
	        for(int i=0;i<lines;i++)
	        {
	        	if(i==0)
	        	{
	        		h.addLineBelow(linesText.get(0));
	        	}
	        	else if(i==1)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1));
	        	}
	        	else if(i==2)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2));
	        	}
	        	else if(i==3)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3));
	        	}
	        	else if(i==4)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4));
	        	}
	        	else if(i==5)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5));
	        	}
	        	else if(i==6)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5)).addLineBelow(linesText.get(6));
	        	}
	        	else if(i==7)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5)).addLineBelow(linesText.get(6)).addLineBelow(linesText.get(7));
	        	}	
	        	else if(i==8)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5)).addLineBelow(linesText.get(6)).addLineBelow(linesText.get(7)).addLineBelow(linesText.get(8));
	        	}	
	        	else if(i==9)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5)).addLineBelow(linesText.get(6)).addLineBelow(linesText.get(7)).addLineBelow(linesText.get(8)).addLineBelow(linesText.get(9));
	        	}	
	        	else if(i==10)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5)).addLineBelow(linesText.get(6)).addLineBelow(linesText.get(7)).addLineBelow(linesText.get(8)).addLineBelow(linesText.get(9)).addLineBelow(linesText.get(10));
	        	}
	        	else if(i==11)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5)).addLineBelow(linesText.get(6)).addLineBelow(linesText.get(7)).addLineBelow(linesText.get(8)).addLineBelow(linesText.get(9)).addLineBelow(linesText.get(10)).addLineBelow(linesText.get(11));
	        	}	
	        	else if(i==12)
	        	{
	        		h.addLineBelow(linesText.get(0)).addLineBelow(linesText.get(1)).addLineBelow(linesText.get(2)).addLineBelow(linesText.get(3)).addLineBelow(linesText.get(4)).addLineBelow(linesText.get(5)).addLineBelow(linesText.get(6)).addLineBelow(linesText.get(7)).addLineBelow(linesText.get(8)).addLineBelow(linesText.get(9)).addLineBelow(linesText.get(10)).addLineBelow(linesText.get(11)).addLineBelow(linesText.get(12));
	        	}	
	        }
	        add(h);
			Main.getHolos().put(Name, h);
	      }
	    }
	    reader.close();
	  }
	  
	  
	  public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		    for (Entry<T, E> entry : map.entrySet()) {
		        if (Objects.equals(value, entry.getValue())) {
		            return entry.getKey();
		        }
		    }
		    return null;
		}

}
