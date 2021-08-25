package net.opticraft.opticore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.zaxxer.hikari.HikariDataSource;

import net.opticraft.opticore.announcement.AnnouncementUtil;
import net.opticraft.opticore.challenge.AddChallengeCommand;
import net.opticraft.opticore.challenge.Challenge;
import net.opticraft.opticore.challenge.ChallengesCommand;
import net.opticraft.opticore.challenge.ChallengeListener;
import net.opticraft.opticore.challenge.ChallengeUtil;
import net.opticraft.opticore.challenge.DelChallengeCommand;
import net.opticraft.opticore.commands.InformationCommand;
import net.opticraft.opticore.commands.LivemapCommand;
import net.opticraft.opticore.commands.RanksCommand;
import net.opticraft.opticore.commands.RulesCommand;
import net.opticraft.opticore.elytra.ElytraCommand;
import net.opticraft.opticore.elytra.ElytraListener;
import net.opticraft.opticore.friend.FriendCommand;
import net.opticraft.opticore.friend.FriendUtil;
import net.opticraft.opticore.gui.ExitCommand;
import net.opticraft.opticore.gui.Gui;
import net.opticraft.opticore.gui.GuiListener;
import net.opticraft.opticore.gui.GuiUtil;
import net.opticraft.opticore.gui.OpticraftCommand;
import net.opticraft.opticore.gui.Slot;
import net.opticraft.opticore.home.DelhomeCommand;
import net.opticraft.opticore.home.GivehomeCommand;
import net.opticraft.opticore.home.HomeCommand;
import net.opticraft.opticore.home.HomeUtil;
import net.opticraft.opticore.home.HomesCommand;
import net.opticraft.opticore.home.LockhomeCommand;
import net.opticraft.opticore.home.MovehomeCommand;
import net.opticraft.opticore.home.SethomeCommand;
import net.opticraft.opticore.home.TakehomeCommand;
import net.opticraft.opticore.ignore.IgnoreCommand;
import net.opticraft.opticore.ignore.IgnoreUtil;
import net.opticraft.opticore.ignore.IgnoredCommand;
import net.opticraft.opticore.ignore.UnignoreCommand;
import net.opticraft.opticore.message.MessageCommand;
import net.opticraft.opticore.message.MessageUtil;
import net.opticraft.opticore.message.ReplyCommand;
import net.opticraft.opticore.player.Player;
import net.opticraft.opticore.player.PlayerCommand;
import net.opticraft.opticore.player.PlayerListener;
import net.opticraft.opticore.player.PlayersCommand;
import net.opticraft.opticore.rewards.DonateCommand;
import net.opticraft.opticore.rewards.GivepointsCommand;
import net.opticraft.opticore.rewards.Reward;
import net.opticraft.opticore.rewards.RewardCommand;
import net.opticraft.opticore.rewards.RewardUtil;
import net.opticraft.opticore.rewards.TakepointsCommand;
import net.opticraft.opticore.rewards.VoteCommand;
import net.opticraft.opticore.rewards.VoteListener;
import net.opticraft.opticore.server.CreativeCommand;
import net.opticraft.opticore.server.HubCommand;
import net.opticraft.opticore.server.Server;
import net.opticraft.opticore.server.ServerCommand;
import net.opticraft.opticore.server.ServerUtil;
import net.opticraft.opticore.server.SurvivalCommand;
import net.opticraft.opticore.settings.SettingCommand;
import net.opticraft.opticore.settings.SettingUtil;
import net.opticraft.opticore.social.SocialUtil;
import net.opticraft.opticore.staff.StaffCommand;
import net.opticraft.opticore.staff.ban.Ban;
import net.opticraft.opticore.staff.ban.BanCommand;
import net.opticraft.opticore.staff.ban.BanHistoryCommand;
import net.opticraft.opticore.staff.ban.BanListener;
import net.opticraft.opticore.staff.ban.BanUtil;
import net.opticraft.opticore.staff.ban.UnbanCommand;
import net.opticraft.opticore.staff.chat.StaffchatCommand;
import net.opticraft.opticore.staff.freeze.Freeze;
import net.opticraft.opticore.staff.freeze.FreezeCommand;
import net.opticraft.opticore.staff.freeze.FreezeListener;
import net.opticraft.opticore.staff.freeze.FreezeUtil;
import net.opticraft.opticore.staff.freeze.UnfreezeCommand;
import net.opticraft.opticore.staff.kick.KickCommand;
import net.opticraft.opticore.staff.kick.KickUtil;
import net.opticraft.opticore.staff.mute.Mute;
import net.opticraft.opticore.staff.mute.MuteCommand;
import net.opticraft.opticore.staff.mute.MuteListener;
import net.opticraft.opticore.staff.mute.MuteUtil;
import net.opticraft.opticore.staff.mute.UnmuteCommand;
import net.opticraft.opticore.staff.note.NoteCommand;
import net.opticraft.opticore.staff.note.NoteUtil;
import net.opticraft.opticore.staff.ticket.TicketCommand;
import net.opticraft.opticore.staff.ticket.TicketUtil;
import net.opticraft.opticore.staff.warn.WarnCommand;
import net.opticraft.opticore.staff.warn.WarnUtil;
import net.opticraft.opticore.team.Team;
import net.opticraft.opticore.team.TeamCommand;
import net.opticraft.opticore.team.TeamListener;
import net.opticraft.opticore.team.TeamUtil;
import net.opticraft.opticore.teleport.RandomTpCommand;
import net.opticraft.opticore.teleport.TeleportUtil;
import net.opticraft.opticore.teleport.TpAcceptCommand;
import net.opticraft.opticore.teleport.TpCancelCommand;
import net.opticraft.opticore.teleport.TpCommand;
import net.opticraft.opticore.teleport.TpDenyCommand;
import net.opticraft.opticore.teleport.TpHereCommand;
import net.opticraft.opticore.teleport.TpRequestCommand;
import net.opticraft.opticore.teleport.TpRequestHereCommand;
import net.opticraft.opticore.trade.Trade;
import net.opticraft.opticore.trade.TradeCommand;
import net.opticraft.opticore.trade.TradeUtil;
import net.opticraft.opticore.util.Config;
import net.opticraft.opticore.util.EventListener;
import net.opticraft.opticore.util.MySQL;
import net.opticraft.opticore.util.Util;
import net.opticraft.opticore.util.bungeecord.BungeecordUtil;
import net.opticraft.opticore.util.bungeecord.PluginMessageHandler;
import net.opticraft.opticore.warp.DelwarpCommand;
import net.opticraft.opticore.warp.SetwarpCommand;
import net.opticraft.opticore.warp.Warp;
import net.opticraft.opticore.warp.WarpCommand;
import net.opticraft.opticore.warp.WarpUtil;
import net.opticraft.opticore.world.JoinCommand;
import net.opticraft.opticore.world.SetspawnCommand;
import net.opticraft.opticore.world.SpawnCommand;
import net.opticraft.opticore.world.World;
import net.opticraft.opticore.world.WorldCommand;
import net.opticraft.opticore.world.WorldListener;
import net.opticraft.opticore.world.WorldUtil;

public class Main extends JavaPlugin {

	public Main main;

	public Config config;
	public MySQL mysql;
	public Util util;
	public BungeecordUtil bungeecordUtil;

	public AnnouncementUtil announcementUtil;

	public FriendUtil friendUtil;

	public HomeUtil homeUtil;

	public IgnoreUtil ignoreUtil;

	public ServerUtil serverUtil;

	public GuiUtil guiUtil;

	public MessageUtil messageUtil;

	public SettingUtil settingUtil;

	public SocialUtil socialUtil;

	public BanUtil banUtil;
	public FreezeUtil freezeUtil;
	public KickUtil kickUtil;
	public MuteUtil muteUtil;
	public NoteUtil noteUtil;
	public TicketUtil ticketUtil;
	public WarnUtil warnUtil;

	public TeamUtil teamUtil;

	public TeleportUtil teleportUtil;

	public TradeUtil tradeUtil;

	public WarpUtil warpUtil;

	public WorldUtil worldUtil;

	public RewardUtil rewardUtil;

	public ChallengeUtil challengeUtil;

	public HikariDataSource ds;

	// Maps and Arrays

	public HashMap<String, ArrayList<Ban>> bans = new HashMap<String, ArrayList<Ban>>();
	public HashMap<String, ArrayList<Mute>> mutes = new HashMap<String, ArrayList<Mute>>();
	public HashMap<String, ArrayList<Freeze>> freezes = new HashMap<String, ArrayList<Freeze>>();

	public Map<String, String> playerIsChangingServer = new HashMap<>();
	public ArrayList<String> playerHasChangedServer = new ArrayList<String>();

	public ArrayList<String> playerReport = new ArrayList<String>();
	public Map<String, String> playerMessage = new HashMap<>();

	public Map<String, String> guiSearch = new HashMap<>();
	public Map<String, Gui> gui = new HashMap<String, Gui>();
	public Map<String, Slot> slot = new HashMap<String, Slot>();

	public Map<String, String> teleport = new HashMap<>();

	public Map<String, Team> teams = new TreeMap<String, Team>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, World> worlds = new TreeMap<String, World>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, Warp> warps = new TreeMap<String, Warp>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, Player> players = new TreeMap<String, Player>(String.CASE_INSENSITIVE_ORDER);
	public Map<String, Reward> rewards = new TreeMap<String, Reward>(String.CASE_INSENSITIVE_ORDER);
	public ConcurrentHashMap<String, Challenge> challenges = new ConcurrentHashMap<String, Challenge>();

	public Map<String, List<Trade>> trades = new HashMap<String, List<Trade>>();

	public Map<String, Server> servers = new TreeMap<String, Server>(String.CASE_INSENSITIVE_ORDER);

	public ArrayList<String> elytra = new ArrayList<String>();

	public ArrayList<String> staffchat = new ArrayList<String>();

	public Map<String, List<Location>> witherBlocks = new HashMap<String, List<Location>>();
	
	public Map<String, Integer> temporaryWorldBlocks = new HashMap<>();

	public int witherCount = 0;

	public Scoreboard scoreboard;

	public void onEnable() {

		this.main = this;

		// Methods
		config = new Config(this);
		util = new Util(this);
		bungeecordUtil = new BungeecordUtil(this);
		mysql = new MySQL(this);

		announcementUtil = new AnnouncementUtil(this);

		//friendUtil = new FriendUtil(this);

		homeUtil = new HomeUtil(this);

		ignoreUtil = new IgnoreUtil(this);

		serverUtil = new ServerUtil(this);

		guiUtil = new GuiUtil(this);

		messageUtil = new MessageUtil(this);

		settingUtil = new SettingUtil(this);

		socialUtil = new SocialUtil(this);

		banUtil = new BanUtil(this);
		//staffchatUtil = new StaffchatUtil(this);
		freezeUtil = new FreezeUtil(this);
		kickUtil = new KickUtil(this);
		muteUtil = new MuteUtil(this);
		noteUtil = new NoteUtil(this);
		//ticketUtil = new TicketUtil(this);
		warnUtil = new WarnUtil(this);

		teamUtil = new TeamUtil(this);

		teleportUtil = new TeleportUtil(this);

		tradeUtil = new TradeUtil(this);

		warpUtil = new WarpUtil(this);

		worldUtil = new WorldUtil(this);

		rewardUtil = new RewardUtil(this);

		challengeUtil = new ChallengeUtil(this);

		// BungeeCord
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageHandler(this));

		// Commands
		this.getCommand("addchallenge").setExecutor(new AddChallengeCommand(this));
		this.getCommand("challenges").setExecutor(new ChallengesCommand(this));
		this.getCommand("delchallenge").setExecutor(new DelChallengeCommand(this));

		this.getCommand("information").setExecutor(new InformationCommand(this));
		this.getCommand("livemap").setExecutor(new LivemapCommand(this));
		this.getCommand("opticraft").setExecutor(new OpticraftCommand(this));
		this.getCommand("ranks").setExecutor(new RanksCommand(this));
		this.getCommand("rules").setExecutor(new RulesCommand(this));

		this.getCommand("elytra").setExecutor(new ElytraCommand(this));

		this.getCommand("friend").setExecutor(new FriendCommand(this));

		this.getCommand("exit").setExecutor(new ExitCommand(this));

		this.getCommand("delhome").setExecutor(new DelhomeCommand(this));
		this.getCommand("givehome").setExecutor(new GivehomeCommand(this));
		this.getCommand("home").setExecutor(new HomeCommand(this));
		this.getCommand("homes").setExecutor(new HomesCommand(this));
		this.getCommand("lockhome").setExecutor(new LockhomeCommand(this));
		this.getCommand("movehome").setExecutor(new MovehomeCommand(this));
		this.getCommand("sethome").setExecutor(new SethomeCommand(this));
		this.getCommand("takehome").setExecutor(new TakehomeCommand(this));

		this.getCommand("ignore").setExecutor(new IgnoreCommand(this));
		this.getCommand("ignored").setExecutor(new IgnoredCommand(this));
		this.getCommand("unignore").setExecutor(new UnignoreCommand(this));

		this.getCommand("message").setExecutor(new MessageCommand(this));
		this.getCommand("reply").setExecutor(new ReplyCommand(this));
		//this.getCommand("socialspy").setExecutor(new SocialspyCommand(this));

		this.getCommand("player").setExecutor(new PlayerCommand(this));
		this.getCommand("players").setExecutor(new PlayersCommand(this));

		this.getCommand("donate").setExecutor(new DonateCommand(this));
		this.getCommand("givepoints").setExecutor(new GivepointsCommand(this));
		this.getCommand("reward").setExecutor(new RewardCommand(this));
		this.getCommand("takepoints").setExecutor(new TakepointsCommand(this));
		this.getCommand("vote").setExecutor(new VoteCommand(this));

		this.getCommand("creative").setExecutor(new CreativeCommand(this));
		this.getCommand("hub").setExecutor(new HubCommand(this));
		this.getCommand("server").setExecutor(new ServerCommand(this));
		this.getCommand("survival").setExecutor(new SurvivalCommand(this));

		this.getCommand("setting").setExecutor(new SettingCommand(this));

		//this.getCommand("social").setExecutor(new SocialCommand(this));

		this.getCommand("staff").setExecutor(new StaffCommand(this));

		this.getCommand("ban").setExecutor(new BanCommand(this));
		this.getCommand("banhistory").setExecutor(new BanHistoryCommand(this));
		this.getCommand("unban").setExecutor(new UnbanCommand(this));

		this.getCommand("staffchat").setExecutor(new StaffchatCommand(this));

		this.getCommand("freeze").setExecutor(new FreezeCommand(this));
		this.getCommand("unfreeze").setExecutor(new UnfreezeCommand(this));

		this.getCommand("kick").setExecutor(new KickCommand(this));

		this.getCommand("mute").setExecutor(new MuteCommand(this));
		this.getCommand("unmute").setExecutor(new UnmuteCommand(this));

		this.getCommand("note").setExecutor(new NoteCommand(this));

		this.getCommand("ticket").setExecutor(new TicketCommand(this));

		this.getCommand("warn").setExecutor(new WarnCommand(this));

		this.getCommand("team").setExecutor(new TeamCommand(this));

		this.getCommand("randomtp").setExecutor(new RandomTpCommand(this));
		this.getCommand("tpaccept").setExecutor(new TpAcceptCommand(this));
		this.getCommand("tpcancel").setExecutor(new TpCancelCommand(this));
		this.getCommand("tp").setExecutor(new TpCommand(this));
		this.getCommand("tpdeny").setExecutor(new TpDenyCommand(this));
		this.getCommand("tphere").setExecutor(new TpHereCommand(this));
		this.getCommand("tprequest").setExecutor(new TpRequestCommand(this));
		this.getCommand("tprequesthere").setExecutor(new TpRequestHereCommand(this));

		this.getCommand("delwarp").setExecutor(new DelwarpCommand(this));
		this.getCommand("setwarp").setExecutor(new SetwarpCommand(this));
		this.getCommand("warp").setExecutor(new WarpCommand(this));

		this.getCommand("j").setExecutor(new JoinCommand(this));
		this.getCommand("setspawn").setExecutor(new SetspawnCommand(this));
		this.getCommand("spawn").setExecutor(new SpawnCommand(this));
		this.getCommand("world").setExecutor(new WorldCommand(this));

		this.getCommand("trade").setExecutor(new TradeCommand(this));

		// Listeners
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new ElytraListener(this), this);

		pm.registerEvents(new GuiListener(this), this);

		//pm.registerEvents(new MessageListener(this), this);

		pm.registerEvents(new PlayerListener(this), this);

		pm.registerEvents(new BanListener(this), this);
		pm.registerEvents(new FreezeListener(this), this);
		pm.registerEvents(new MuteListener(this), this);

		//pm.registerEvents(new SocialListener(this), this);

		pm.registerEvents(new TeamListener(this), this);

		pm.registerEvents(new EventListener(this), this);

		pm.registerEvents(new WorldListener(this), this);

		pm.registerEvents(new VoteListener(this), this);

		pm.registerEvents(new ChallengeListener(this), this);

		// Loading
		config.loadConfig();

		mysql.openConnection();
		mysql.createTables();

		bungeecordUtil.runServerPlayerListTimer();

		announcementUtil.startAnnouncement();

		guiUtil.loadConfig();

		homeUtil.loadConfig();

		rewardUtil.loadConfig();

		rewardUtil.startVoteReminder();
		rewardUtil.startDailyReminder();

		serverUtil.loadConfig();

		teamUtil.loadConfig();

		warpUtil.loadConfig();

		worldUtil.loadConfig();

		tradeUtil.loadConfig();

		challengeUtil.loadConfig();
		challengeUtil.startExpiryCheck();

		// coloured name scoreboard
		// Set player name colour above head

		ScoreboardManager manager = getServer().getScoreboardManager();
		scoreboard = manager.getNewScoreboard();

		scoreboard.registerNewTeam("Owner");
		scoreboard.registerNewTeam("Admin");
		scoreboard.registerNewTeam("Operator");
		scoreboard.registerNewTeam("Moderator");
		scoreboard.registerNewTeam("Champion");
		scoreboard.registerNewTeam("Veteran");
		scoreboard.registerNewTeam("Citizen");
		scoreboard.registerNewTeam("Resident");
		scoreboard.registerNewTeam("Member");
		scoreboard.registerNewTeam("Guest");

		scoreboard.getTeam("Owner").setColor(org.bukkit.ChatColor.RED);
		scoreboard.getTeam("Admin").setColor(org.bukkit.ChatColor.BLUE);
		scoreboard.getTeam("Operator").setColor(org.bukkit.ChatColor.DARK_AQUA);
		scoreboard.getTeam("Moderator").setColor(org.bukkit.ChatColor.AQUA);
		scoreboard.getTeam("Champion").setColor(org.bukkit.ChatColor.DARK_PURPLE);
		scoreboard.getTeam("Veteran").setColor(org.bukkit.ChatColor.GREEN);
		scoreboard.getTeam("Citizen").setColor(org.bukkit.ChatColor.GOLD);
		scoreboard.getTeam("Resident").setColor(org.bukkit.ChatColor.YELLOW);
		scoreboard.getTeam("Member").setColor(org.bukkit.ChatColor.GRAY);
		scoreboard.getTeam("Guest").setColor(org.bukkit.ChatColor.WHITE);

		for (org.bukkit.scoreboard.Team team : scoreboard.getTeams()) {
			team.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
		}
	}

	@SuppressWarnings("deprecation")
	public void addPlayer(org.bukkit.entity.Player player, String team) {
		if (!getScoreboard().getTeam(team).hasPlayer(player)) {
			getScoreboard().getTeam(team).addPlayer(player);
		}
		player.setScoreboard(getScoreboard());
		System.out.println(player.getName() + "'s scoreboard set and added to team " + team);
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void onDisable() {

	}
}
