package net.opticraft.opticore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zaxxer.hikari.HikariDataSource;

import net.opticraft.opticore.announcement.AnnouncementUtil;
import net.opticraft.opticore.commands.OpticraftCommand;
import net.opticraft.opticore.commands.RanksCommand;
import net.opticraft.opticore.commands.RulesCommand;
import net.opticraft.opticore.commands.SettingsCommand;
import net.opticraft.opticore.commands.WildernessCommand;
import net.opticraft.opticore.friend.FriendCommand;
import net.opticraft.opticore.gui.GuiListener;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.home.DelhomeCommand;
import net.opticraft.opticore.home.GivehomeCommand;
import net.opticraft.opticore.home.HomeCommand;
import net.opticraft.opticore.home.HomeUtil;
import net.opticraft.opticore.home.HomesCommand;
import net.opticraft.opticore.home.LockhomeCommand;
import net.opticraft.opticore.home.SethomeCommand;
import net.opticraft.opticore.home.TakehomeCommand;
import net.opticraft.opticore.message.MessageCommand;
import net.opticraft.opticore.message.MessageUtil;
import net.opticraft.opticore.message.ReplyCommand;
import net.opticraft.opticore.player.Player;
import net.opticraft.opticore.player.PlayerListener;
import net.opticraft.opticore.rewards.ChallengesCommand;
import net.opticraft.opticore.rewards.DailyCommand;
import net.opticraft.opticore.rewards.DonateCommand;
import net.opticraft.opticore.rewards.PointsCommand;
import net.opticraft.opticore.rewards.RewardsCommand;
import net.opticraft.opticore.rewards.VoteCommand;
import net.opticraft.opticore.server.ServerCommand;
import net.opticraft.opticore.server.Server;
import net.opticraft.opticore.staff.StaffCommand;
import net.opticraft.opticore.staff.ban.Ban;
import net.opticraft.opticore.staff.ban.BanCommand;
import net.opticraft.opticore.staff.ban.BanUtil;
import net.opticraft.opticore.staff.ban.BansCommand;
import net.opticraft.opticore.staff.ban.UnbanCommand;
import net.opticraft.opticore.staff.freeze.Freeze;
import net.opticraft.opticore.staff.freeze.FreezeCommand;
import net.opticraft.opticore.staff.freeze.FreezesCommand;
import net.opticraft.opticore.staff.freeze.UnfreezeCommand;
import net.opticraft.opticore.staff.kick.Kick;
import net.opticraft.opticore.staff.kick.KickCommand;
import net.opticraft.opticore.staff.kick.KickUtil;
import net.opticraft.opticore.staff.kick.KicksCommand;
import net.opticraft.opticore.staff.mute.Mute;
import net.opticraft.opticore.staff.mute.MuteCommand;
import net.opticraft.opticore.staff.mute.MutesCommand;
import net.opticraft.opticore.staff.mute.UnmuteCommand;
import net.opticraft.opticore.staff.note.Note;
import net.opticraft.opticore.staff.note.NoteCommand;
import net.opticraft.opticore.staff.note.NoteUtil;
import net.opticraft.opticore.staff.note.NotesCommand;
import net.opticraft.opticore.staff.ticket.TicketCommand;
import net.opticraft.opticore.staff.ticket.TicketsCommand;
import net.opticraft.opticore.teleport.TpacceptCommand;
import net.opticraft.opticore.teleport.TpcancelCommand;
import net.opticraft.opticore.teleport.TpCommand;
import net.opticraft.opticore.teleport.TpdenyCommand;
import net.opticraft.opticore.teleport.TphereCommand;
import net.opticraft.opticore.teleport.TeleportUtil;
import net.opticraft.opticore.teleport.TprequestCommand;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.EventListener;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.MySQL;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;
import net.opticraft.opticore.util.bungeecord.PluginMessageHandler;
import net.opticraft.opticore.util.wither.Wither;
import net.opticraft.opticore.util.wither.WitherCommand;
import net.opticraft.opticore.warp.DelwarpCommand;
import net.opticraft.opticore.warp.SetwarpCommand;
import net.opticraft.opticore.warp.WarpCommand;
import net.opticraft.opticore.warp.Warp;
import net.opticraft.opticore.warp.WarpUtil;
import net.opticraft.opticore.world.SetspawnCommand;
import net.opticraft.opticore.world.SpawnCommand;
import net.opticraft.opticore.world.WorldCommand;
import net.opticraft.opticore.world.World;
import net.opticraft.opticore.world.WorldUtil;

public class Main extends JavaPlugin {

	public Main main;

	public Config config;
	public BungeecordUtil bungeecordUtil;

	public MessageUtil messageUtil;
	public HomeUtil homeUtil;
	public GuiUtil guiUtil;

	public AnnouncementUtil announcementUtil;

	public WorldUtil worldUtil;
	public WarpUtil warpUtil;

	public Util util;

	public TeleportUtil teleportUtil;
	
	public BanUtil banUtil;
	public KickUtil kickUtil;
	public NoteUtil noteUtil;

	public MySQL mysql;
	public HikariDataSource ds;

	//
	
	public Map<String, Ban> bans = new TreeMap<String, Ban>();
	public Map<String, Freeze> freezes = new TreeMap<String, Freeze>();
	public Map<String, Kick> kicks = new TreeMap<String, Kick>();
	public Map<String, Mute> mutes = new TreeMap<String, Mute>();
	public Map<String, Note> notes = new TreeMap<String, Note>();

	public final Map<String, Integer> playerCount = new HashMap<>();
	public final Map<String, String> playerList = new HashMap<>();

	public HashMap<String, String> playerIsChangingServer = new HashMap<>();
	public ArrayList<String> playerHasChangedServer = new ArrayList<String>();

	public ArrayList<String> playerReport = new ArrayList<String>();
	public HashMap<String, String> playerMessage = new HashMap<>();

	public HashMap<String, String> guiSearch = new HashMap<>();

	public HashMap<String, String> teleport = new HashMap<>();
	
	public Map<String, World> worlds = new TreeMap<String, World>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, Warp> warps = new TreeMap<String, Warp>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, Player> players = new TreeMap<String, Player>(String.CASE_INSENSITIVE_ORDER);

	public Map<String, Server> servers = new TreeMap<String, Server>(String.CASE_INSENSITIVE_ORDER);
	
	public ArrayList<String> wither = new ArrayList<String>();
	public Map<String, Wither> withers = new TreeMap<String, Wither>(String.CASE_INSENSITIVE_ORDER);

	public void onEnable() {

		this.main = this;

		// Methods
		config = new Config(this);
		bungeecordUtil = new BungeecordUtil(this);

		//friendUtil = new FriendUtil(this);
		messageUtil = new MessageUtil(this);
		homeUtil = new HomeUtil(this);
		guiUtil = new GuiUtil(this);

		announcementUtil = new AnnouncementUtil(this);

		worldUtil = new WorldUtil(this);
		warpUtil = new WarpUtil(this);

		util = new Util(this);

		teleportUtil = new TeleportUtil(this);
		
		banUtil = new BanUtil(this);
		kickUtil = new KickUtil(this);
		noteUtil = new NoteUtil(this);

		mysql = new MySQL(this);

		// BungeeCord
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageHandler(this));

		// Commands
		//this.getCommand("list").setExecutor(new ListCommand(this));
		this.getCommand("opticraft").setExecutor(new OpticraftCommand(this));
		this.getCommand("ranks").setExecutor(new RanksCommand(this));
		this.getCommand("rules").setExecutor(new RulesCommand(this));
		this.getCommand("settings").setExecutor(new SettingsCommand(this));
		this.getCommand("wilderness").setExecutor(new WildernessCommand(this));

		this.getCommand("friend").setExecutor(new FriendCommand(this));

		this.getCommand("delhome").setExecutor(new DelhomeCommand(this));
		this.getCommand("givehome").setExecutor(new GivehomeCommand(this));
		this.getCommand("home").setExecutor(new HomeCommand(this));
		this.getCommand("homes").setExecutor(new HomesCommand(this));
		this.getCommand("lockhome").setExecutor(new LockhomeCommand(this));
		this.getCommand("sethome").setExecutor(new SethomeCommand(this));
		this.getCommand("takehome").setExecutor(new TakehomeCommand(this));

		this.getCommand("message").setExecutor(new MessageCommand(this));
		this.getCommand("reply").setExecutor(new ReplyCommand(this));
		//this.getCommand("socialspy").setExecutor(new SocialspyCommand(this));

		//this.getCommand("player").setExecutor(new PlayerCommand(this));

		this.getCommand("challenges").setExecutor(new ChallengesCommand(this));
		this.getCommand("daily").setExecutor(new DailyCommand(this));
		this.getCommand("donate").setExecutor(new DonateCommand(this));
		this.getCommand("points").setExecutor(new PointsCommand(this));
		this.getCommand("rewards").setExecutor(new RewardsCommand(this));
		this.getCommand("vote").setExecutor(new VoteCommand(this));

		this.getCommand("server").setExecutor(new ServerCommand(this));
		
		this.getCommand("staff").setExecutor(new StaffCommand(this));
		
		this.getCommand("ban").setExecutor(new BanCommand(this));
		this.getCommand("bans").setExecutor(new BansCommand(this));
		this.getCommand("unban").setExecutor(new UnbanCommand(this));
		
		this.getCommand("freeze").setExecutor(new FreezeCommand(this));
		this.getCommand("freezes").setExecutor(new FreezesCommand(this));
		this.getCommand("unfreeze").setExecutor(new UnfreezeCommand(this));
		
		this.getCommand("kick").setExecutor(new KickCommand(this));
		this.getCommand("kicks").setExecutor(new KicksCommand(this));
		
		this.getCommand("mute").setExecutor(new MuteCommand(this));
		this.getCommand("mutes").setExecutor(new MutesCommand(this));
		this.getCommand("unmute").setExecutor(new UnmuteCommand(this));
		
		this.getCommand("note").setExecutor(new NoteCommand(this));
		this.getCommand("notes").setExecutor(new NotesCommand(this));
		
		this.getCommand("ticket").setExecutor(new TicketCommand(this));
		this.getCommand("tickets").setExecutor(new TicketsCommand(this));

		this.getCommand("tpaccept").setExecutor(new TpacceptCommand(this));
		this.getCommand("tpcancel").setExecutor(new TpcancelCommand(this));
		this.getCommand("tp").setExecutor(new TpCommand(this));
		this.getCommand("tpdeny").setExecutor(new TpdenyCommand(this));
		this.getCommand("tphere").setExecutor(new TphereCommand(this));
		this.getCommand("tprequest").setExecutor(new TprequestCommand(this));

		this.getCommand("delwarp").setExecutor(new DelwarpCommand(this));
		this.getCommand("setwarp").setExecutor(new SetwarpCommand(this));
		this.getCommand("warp").setExecutor(new WarpCommand(this));

		this.getCommand("setspawn").setExecutor(new SetspawnCommand(this));
		this.getCommand("spawn").setExecutor(new SpawnCommand(this));
		this.getCommand("j").setExecutor(new WorldCommand(this));
		
		this.getCommand("wither").setExecutor(new WitherCommand(this));

		//Listeners
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new GuiListener(this), this);
		
		pm.registerEvents(new PlayerListener(this), this);
		
		pm.registerEvents(new EventListener(this), this);

		//Load
		config.loadConfiguration();
		
		bungeecordUtil.runServerPlayerCountTimer();
		bungeecordUtil.runServerPlayerListTimer();

		announcementUtil.loadConfig();
		announcementUtil.startAnnouncement();

		homeUtil.loadConfig();

		warpUtil.loadConfig();
		
		worldUtil.loadConfig();

		mysql.openConnection();
		mysql.createTables();
	}

	public void onDisable() {

	}
}
