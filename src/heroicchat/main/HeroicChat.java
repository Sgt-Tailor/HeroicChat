package heroicchat.main;

import heroicchat.executors.GeneralCommand;
import heroicchat.listeners.PlayerChatListener;
import heroicchat.listeners.PlayerDisconnectListener;
import heroicchat.listeners.PlayerJoinListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroicChat extends JavaPlugin {
	public ArrayList<String> cnames = new ArrayList<String>();
	public HashMap<String, Channel> channels = new HashMap<String,Channel>();
	public HashMap<String, String> players = new HashMap<String,String>();
	
	public static HeroicChat plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public GeneralCommand GeneralExecutor;
	public File configFile;
	public FileConfiguration config;

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		deSerialiseObjects();
		removeOfflineReceivers(); 
		if(cnames.size() == 0) {
			createDefaultChannel();
		}
		config = new YamlConfiguration();
		configFile = new File(getDataFolder().getAbsolutePath()+ "/config.yml");
		if(!configFile.exists()){
			firstrun();
		}
		loadYaml();
		
		GeneralExecutor = new GeneralCommand(this);
		getCommand("heroicchat").setExecutor(GeneralExecutor);
		
		pm.registerEvents(new PlayerJoinListener(this), this);
		pm.registerEvents(new PlayerDisconnectListener(this), this);
		pm.registerEvents(new PlayerChatListener(this), this);
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled.");
	}
	
	



	





	@Override
	public void onDisable() {
		serialiseObjects();
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is now disabled ");
	}
	private void createDefaultChannel() {
		Channel c = new Channel("default", "default", null, false, true);
		channels.put("default", c);
		cnames.add("default");
		players.put("Player", c.getName());
		return;
		
	}
	private void serialiseObjects() {
		try {
			FileOutputStream f_out = new FileOutputStream(getDataFolder().getAbsolutePath() + "/channels.data");
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
			obj_out.writeObject ( channels );
			}catch(Exception e) {
				e.printStackTrace();
		}
		try {
			FileOutputStream f_out = new FileOutputStream(getDataFolder().getAbsolutePath() + "/cnames.data");
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
			obj_out.writeObject ( cnames );
		    }catch(Exception e) {
		    	e.printStackTrace();
		}
		try {
			FileOutputStream f_out = new FileOutputStream(getDataFolder().getAbsolutePath() + "/players.data");
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
			obj_out.writeObject ( players );
		    }catch(Exception e) {
		    	e.printStackTrace();
		}
		
	}
	public void removeOfflineReceivers() {
		if(cnames != null) {
			for(int i=0; i < cnames.size(); i++) {
				Channel c = channels.get(cnames.get(i));
				ArrayList<String> receivers = c.getReceivers();
				if(receivers != null) {
					for(int j=0; j<receivers.size();j++) {
						Player[] op = Bukkit.getOnlinePlayers();
						for(int k = 0; k<op.length; k++) {
							if(op[k].getName().equals(receivers.get(j))) {
								
							}
							else {
							c.removeReceiver(receivers.get(j));
							}
						}
						
						
					}
				}
			}
		}
		
	}
	private void firstrun() {//create the config.yml
		PluginDescriptionFile pdfFile = this.getDescription();
		if(!configFile.exists()){
			
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				
			}
			//config.set("default-channel-name", "default");//add the default value to the config.yml
			//config.set("default-channel-prefix", "default");
			
			this.logger.info("[" + pdfFile.getName()+ "]" + " config.yml successfully created");
		}
		
		saveYaml();
		
		
	}
	@SuppressWarnings("unchecked")
	public void deSerialiseObjects() {
		try {
			FileInputStream f_in = new FileInputStream(getDataFolder().getAbsolutePath() + "/channels.data");
			// Read object using ObjectInputStream
			ObjectInputStream obj_in = 	new ObjectInputStream (f_in);
			Object obj = obj_in.readObject();
			if (obj instanceof HashMap) {
				channels = (HashMap<String,Channel>) obj;
			}
			f_in = new FileInputStream(getDataFolder().getAbsolutePath() + "/cnames.data");
			// Read object using ObjectInputStream
			obj_in = 	new ObjectInputStream (f_in);
			obj = obj_in.readObject();
			if (obj instanceof ArrayList) {
				
				cnames = (ArrayList<String>) obj;
			}
			
			f_in = new FileInputStream(getDataFolder().getAbsolutePath() + "/players.data");
			// Read object using ObjectInputStream
			obj_in = 	new ObjectInputStream (f_in);
			obj = obj_in.readObject();
			if (obj instanceof HashMap) {
				players = (HashMap<String,String>) obj;
			}
		}catch(Exception e) {
			
				
		}
		
	}
	public void saveYaml() {
		try {
			config.save(configFile);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void loadYaml(){
		try {
			config.load(configFile);
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
