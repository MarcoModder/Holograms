package it.spgiot.skyhash.HologramsPatch;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import de.inventivegames.hologram.view.ViewHandler;
import me.clip.placeholderapi.PlaceholderAPI;

public class HoloCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player) sender;
		if(args.length == 0)
		{
			p.sendMessage("§2Holograms 1.8 Patch made by SkyHash");
			p.sendMessage("§a/hd help to show the commands");
			return true;
		}
		if(args[0].equalsIgnoreCase("help"))
		{
			p.sendMessage("§7§m--------"+"§2"+"Holograms Commands"+"§7§m--------");
			p.sendMessage("§a/hd create <NAME> <TEXT>"); // DONE
			p.sendMessage("§a/hd editline <NAME> <LINE> <TEXT>"); // DONE
			p.sendMessage("§a/hd addline <NAME> <TEXT>"); // DONE
			p.sendMessage("§a/hd delete <NAME>"); // DONE
			p.sendMessage("§a/hd movehere <NAME>"); // DONE
			p.sendMessage("§a/hd list");  // DONE
			p.sendMessage("§7§m---------------------------");
			return true;
		}
		if(args[0].equalsIgnoreCase("create"))
		{
			if(args.length >= 3)
			{
				String name = args[1];
				Location loc = new Location(p.getLocation().getWorld(),p.getLocation().getBlockX(),p.getLocation().getBlockY()+2,p.getLocation().getBlockZ());
				//Check if hologram exist
				if(Main.getHolos().containsKey(name))
				{
					p.sendMessage("§cThis hologram exists already!");
					return true;
				}
				// Get the hologram text
				String holoText = "";
	            
	            for(int i = 2; i != args.length; i++)
	            {
	                if(i == args.length-1)
	                {
	                	holoText += args[i];
	                }
	                else
	                {
	                	holoText += args[i] + " ";
	                }
	            }
	            String holoLine = holoText.replaceAll("&", "§");
				Hologram holo = HologramAPI.createHologram(loc, holoLine);
				Main.getSignStorage().add(holo);
	            holo.addViewHandler(new ViewHandler() {

	                @Override
	                public String onView(Hologram hologram, Player player, String string) {
	                	String parsed = PlaceholderAPI.setPlaceholders(player, string);
	                	return parsed;
	                }
	            });
				holo.spawn();
				p.sendMessage("§aSuccesfully created " + name + " hologram!");
				Main.getHolos().put(name, holo);
				return true;
			}
			else
			{
				p.sendMessage("§cCorrect usage: /hd create <NAME> <TEXT>");
				return true;
			}
		}
		if(args[0].equalsIgnoreCase("editline"))
		{
			if(args.length >= 4)
			{
				String name = args[1];
				int line = 0;
				//Check if hologram exist
				if(!Main.getHolos().containsKey(name))
				{
					p.sendMessage("§cThis hologram does not exist!");
					return true;
				}
				try
				{
					int numb = Integer.parseInt(args[2]);
					line = numb;
					
				}catch(java.lang.NumberFormatException x)
				{
					p.sendMessage("§cLine must be a number!");
					return true;
				}
				// Get the hologram text
				String holoText = "";
	            
	            for(int i = 3; i != args.length; i++)
	            {
	                if(i == args.length-1)
	                {
	                	holoText += args[i];
	                }
	                else
	                {
	                	holoText += args[i] + " ";
	                }
	            }
	            String holoLine = holoText.replaceAll("&", "§");
				editRecursion(Main.getHolos().get(name),line,holoLine,p);
				return true;
			}
			else
			{
				p.sendMessage("§cCorrect usage: /hd editline <NAME> <LINE> <TEXT>");
				return true;
			}
		}
		if(args[0].equalsIgnoreCase("addline"))
		{
			if(args.length >= 3)
			{
				String name = args[1];
				//Check if hologram exist
				if(!Main.getHolos().containsKey(name))
				{
					p.sendMessage("§cThis hologram does not exist!");
					return true;
				}
				// Get the hologram text
				String holoText = "";
	            for(int i = 2; i != args.length; i++)
	            {
	                if(i == args.length-1)
	                {
	                	holoText += args[i];
	                }
	                else
	                {
	                	holoText += args[i] + " ";
	                }
	            }
	            String holoLine = holoText.replaceAll("&", "§");
	            lineRecursion(Main.getHolos().get(name),holoLine);
	            p.sendMessage("§aSuccessfully added a line to your hologram!");
	            return true;
			}
			else
			{
				p.sendMessage("§cCorrect usage: /hd addline <NAME> <TEXT>");
				return true;
			}
		}
		if(args[0].equalsIgnoreCase("delete"))
		{
			if(args.length == 2)
			{
				String name = args[1];
				if(!Main.getHolos().containsKey(name))
				{
					p.sendMessage("§cThis hologram does not exist!");
					return true;
				}
				removeRecursion(Main.getHolos().get(name));
				Main.getHolos().remove(name);
				p.sendMessage("§aSuccesfully deleted " + name + " hologram!");
				return true;
			}
			else
			{
				p.sendMessage("§cCorrect usage: /hd delete <NAME>");
				return true;
			}
		}
		if(args[0].equalsIgnoreCase("movehere"))
		{
			if(args.length == 2)
			{
				String name = args[1];
				if(!Main.getHolos().containsKey(name))
				{
					p.sendMessage("§cThis hologram does not exist!");
					return true;
				}
				moveRecursion(Main.getHolos().get(name),p);
				p.sendMessage("§aSuccesfully moved here " + name + " hologram!");
				return true;
			}
			else
			{
				p.sendMessage("§cCorrect usage: /hd movehere <NAME>");
				return true;
			}
		}
		if(args[0].equalsIgnoreCase("list"))
		{
			if(args.length == 1)
			{
				p.sendMessage("§2Holograms List");
				int count = 0;
				p.sendMessage("§7§m-------------------------------");
				for(String s : Main.getHolos().keySet())
				{
					count++;
					p.sendMessage("§2" + count + "§7: §a" + s);
				}
				if(count == 0)
				{
					p.sendMessage("§cThere are no holograms!");
				}
				p.sendMessage("§7§m-------------------------------");
				return true;
			}
			else
			{
				p.sendMessage("§cCorrect usage: /hd list");
				return true;
			}
		}
		p.sendMessage("§2Holograms 1.8 Patch made by SkyHash");
		p.sendMessage("§a/hd help to show the commands");
		return true;
	}
	
	public void lineRecursion(Hologram h,String s)
	{
		if(h.getLineBelow() != null)
		{
			for(Hologram hg : h.getLinesBelow())
			{
				if(hg.getLineBelow() != null)
				{
					// Nothing 
				}
				else
				{
					hg.addLineBelow(s);
				}
			}
		}
		else
		{
			h.addLineBelow(s);
		}
	}
	
	public void removeRecursion(Hologram h)
	{
		if(h.getLineBelow() != null)
		{
			for(Hologram hg : h.getLinesBelow())
			{
				hg.despawn();
			}
		}
		h.despawn();
	}
	
	public void moveRecursion(Hologram h,Player p)
	{
		double d = 2.0;
		h.move(new Location(p.getLocation().getWorld(),p.getLocation().getBlockX(),p.getLocation().getBlockY()+2.0,p.getLocation().getBlockZ()));;
		if(h.getLineBelow() != null)
		{
			for(Hologram hg : h.getLinesBelow())
			{
				d = d - 0.25;
				hg.move(new Location(p.getLocation().getWorld(),p.getLocation().getBlockX(),p.getLocation().getBlockY()+d,p.getLocation().getBlockZ()));
			}
		}
	}
	
	public void editRecursion(Hologram h,int i,String s,Player p)
	{
		if(h.getLineBelow() != null && i>1)
		{
			boolean somethingDone = false;
			if(i >= 50)
			{
				p.sendMessage("§cYou can't have more then 50 lines!");
				return;
			}
			int count = 3;
			if(h.getLineBelow() != null)
			{
				for(Hologram hg : h.getLinesBelow())
				{
					if(count == i)
					{
						try
						{
							hg.getLineBelow().setText(s);
							somethingDone = true;
							p.sendMessage("§aSuccesfully edited your hologram!");
						}catch(java.lang.NullPointerException x)
						{
							
						}
					}
					count++;
				}
				if(i == 2)
				{
					h.getLineBelow().setText(s);
					somethingDone = true;
					p.sendMessage("§aSuccesfully edited your hologram!");
				}
				if(!somethingDone)
				{
					p.sendMessage("§cThis Line does not exist!");	
				}
			}
		}
		else
		{
			if(i == 1)
			{
				h.setText(s);
				p.sendMessage("§aSuccesfully edited your hologram!");
			}
			else
			{
				p.sendMessage("§cLine number must be greater then 1!");
			}
		}
	}
	
	

}
