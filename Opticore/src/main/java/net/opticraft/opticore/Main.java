package net.opticraft.opticore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zaxxer.hikari.HikariDataSource;

import net.opticraft.opticore.announcement.AnnouncementMethods;
import net.opticraft.opticore.commands.RanksCommand;
import net.opticraft.opticore.commands.RulesCommand;
import net.opticraft.opticore.commands.ServerCommand;
import net.opticraft.opticore.commands.SettingsCommand;
import net.opticraft.opticore.commands.StaffCommand;
import net.opticraft.opticore.commands.WildernessCommand;
import net.opticraft.opticore.friend.FriendCommand;
import net.opticraft.opticore.gui.GuiCommand;
import net.opticraft.opticore.gui.GuiListener;
import net.opticraft.opticore.gui.GuiMethods;
import net.opticraft.opticore.player.PlayerInfo;
import net.opticraft.opticore.player.PlayerListener;
import net.opticraft.opticore.rewards.DonateCommand;
import net.opticraft.opticore.rewards.RewardsCommand;
import net.opticraft.opticore.rewards.VoteCommand;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.EventListener;
import net.opticraft.opticore.util.Methods;
import net.opticraft.opticore.util.MySQL;
import net.opticraft.opticore.util.bungeecord.BungeecordMethods;
import net.opticraft.opticore.util.bungeecord.PluginMessageHandler;
import net.opticraft.opticore.warp.DelwarpCommand;
import net.opticraft.opticore.warp.SetwarpCommand;
import net.opticraft.opticore.warp.WarpCommand;
import net.opticraft.opticore.warp.WarpInfo;
import net.opticraft.opticore.warp.WarpMethods;
import net.opticraft.opticore.world.SetspawnCommand;
import net.opticraft.opticore.world.SpawnCommand;
import net.opticraft.opticore.world.WorldCommand;
import net.opticraft.opticore.world.WorldInfo;
import net.opticraft.opticore.world.WorldMethods;

public class Main extends JavaPlugin {

	public Main instance;

	public Config config;
	public Methods methods;
	public BungeecordMethods bungeecordMethods;
	public GuiMethods guiMethods;
	
	public AnnouncementMethods announcementMethods;
	
	public WorldMethods worldMethods;
	public WarpMethods warpMethods;
	
	public MySQL mysql;
	public HikariDataSource ds;
	public Logger log = Logger.getLogger("Minecraft");
	
	//
	
	public final Map<String, Integer> playerCount = new HashMap<>();
	public final Map<String, String> playerList = new HashMap<>();
	
	//public ArrayList<String> playerMidChangeServer = new ArrayList<String>();
	public HashMap<String, String> playerPreChangeServer = new HashMap<>();
	public ArrayList<String> playerPostChangeServer = new ArrayList<String>();
	//public ArrayList<String> isChangingServers = new ArrayList<String>();
	
	public ArrayList<String> playerReport = new ArrayList<String>();
	public HashMap<String, String> playerMessage = new HashMap<>();
	
	public HashMap<String, String> guiSearch = new HashMap<>();
	
	public Map<String, WorldInfo> worlds = new TreeMap<String, WorldInfo>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, WarpInfo> warps = new TreeMap<String, WarpInfo>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, PlayerInfo> players = new TreeMap<String, PlayerInfo>(String.CASE_INSENSITIVE_ORDER);
	
	public void onEnable() {

		this.instance = this;

		//Methods
		config = new Config(this);
		methods = new Methods(this);
		bungeecordMethods = new BungeecordMethods(this);
		guiMethods = new GuiMethods(this);
		
		announcementMethods = new AnnouncementMethods(this);
		
		worldMethods = new WorldMethods(this);
		warpMethods = new WarpMethods(this);
		
		mysql = new MySQL(this);

		//BungeeCord
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageHandler(this));

		//Commands
		//this.getCommand("jail").setExecutor(new JailCommand(this));
		this.getCommand("ranks").setExecutor(new RanksCommand(this));
		this.getCommand("rules").setExecutor(new RulesCommand(this));
		this.getCommand("server").setExecutor(new ServerCommand(this));
		this.getCommand("settings").setExecutor(new SettingsCommand(this));
		this.getCommand("staff").setExecutor(new StaffCommand(this));
		this.getCommand("wilderness").setExecutor(new WildernessCommand(this));
		
		this.getCommand("friend").setExecutor(new FriendCommand(this));
		
		this.getCommand("gui").setExecutor(new GuiCommand(this));
		
		//this.getCommand("player").setExecutor(new PlayerCommand(this));
		
		this.getCommand("delwarp").setExecutor(new DelwarpCommand(this));
		//this.getCommand("delwarp").setTabCompleter(new TabCompleteHandler(this));
		this.getCommand("setwarp").setExecutor(new SetwarpCommand(this));
		//this.getCommand("setwarp").setTabCompleter(new TabCompleteHandler(this));
		this.getCommand("warp").setExecutor(new WarpCommand(this));
		//this.getCommand("warp").setTabCompleter(new TabCompleteHandler(this));
		
		//this.getCommand("challenges").setExecutor(new ChallengesCommand(this));
		//this.getCommand("daily").setExecutor(new DailyCommand(this));
		this.getCommand("donate").setExecutor(new DonateCommand(this));
		//this.getCommand("points").setExecutor(new PointsCommand(this));
		this.getCommand("rewards").setExecutor(new RewardsCommand(this));
		this.getCommand("vote").setExecutor(new VoteCommand(this));
		
		this.getCommand("j").setExecutor(new WorldCommand(this));
		this.getCommand("setspawn").setExecutor(new SetspawnCommand(this));
		this.getCommand("spawn").setExecutor(new SpawnCommand(this));
		
		//Listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GuiListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new EventListener(this), this);

		//Load
		config.loadConfiguration();
		bungeecordMethods.runServerPlayerCountTimer();
		bungeecordMethods.runServerPlayerListTimer();
		
		announcementMethods.loadConfig();
		announcementMethods.startAnnouncement();
		
		worldMethods.loadConfig();
		warpMethods.loadConfig();
		
		mysql.openConnection();
	}

	public void onDisable() {
		
	}
}
