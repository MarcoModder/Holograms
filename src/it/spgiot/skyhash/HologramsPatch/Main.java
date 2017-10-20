package it.spgiot.skyhash.HologramsPatch;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.inventivegames.hologram.Hologram;

public class Main extends JavaPlugin implements Listener {
	
	public static Main instance;
	private static File HOLOS_DATA_FOLDER;
    private static HoloStorage storage;
	
	public static Map<String,Hologram> holosMap;
	
	public static Main getInstance()
	{
		return instance;
	}
	
	@Override
	public void onEnable()
	{
		System.out.println("[SkyHash] Loading holograms...");
		instance = this;
		holosMap = new HashMap<>();
		Events();
		Commands();
		//Storage
		HOLOS_DATA_FOLDER = new File(getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator);
		storage = new HoloStorage();
		runLoad();
		System.out.println("[SkyHash] Succesfully loaded holograms!");
	}
	
	@Override
	public void onDisable()
	{
		try
	    {
	      storage.saveChunks();
	    }
	    catch (IOException localIOException) {
	    	System.out.println("There was an error saving holos!");
		}
		instance = null;
	}
	
	public static Map<String,Hologram> getHolos()
	{
		return holosMap;
	}
	
	public void Commands()
	{
		getCommand("hd").setExecutor(new HoloCMD());
	}
	
	private void Events()
	{
		registerEvent(this);
	}
	
	private void registerEvent(Listener listener)
	{
	   Bukkit.getServer().getPluginManager().registerEvents(listener, this);
	}
	
	public static HoloStorage getSignStorage()
	{
	    return storage;
	}
	
	public static File getSignDataFolder()
	{
	    return HOLOS_DATA_FOLDER;
	}
	
	public void runLoad()
	{
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getInstance(), new Runnable()
        {
          public void run()
          {
      		if (!HOLOS_DATA_FOLDER.exists())
    		{
    			if (HOLOS_DATA_FOLDER.mkdirs()) 
    			{
    				getLogger().info("[SkyHash]Created holos data folder.");
    			}
    		}
    		else 
    		{
    			try
    			{
    				storage.loadHolo();
    			}catch (IOException localIOException) {}
    		}
          }
        }, 20);
	}
}
