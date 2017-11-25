package net.opticraft.opticore.util;

import java.util.List;

import net.opticraft.opticore.Main;

public class Config {

	public Main plugin;

	public Config(Main plugin) {
		this.plugin = plugin;
	}

	public void loadConfiguration() {

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();

		// MySQL
		this.setMysqlUser(plugin.getConfig().getString("mysql.user"));
		this.setMysqlPort(plugin.getConfig().getString("mysql.port"));
		this.setMysqlPassword(plugin.getConfig().getString("mysql.password"));
		this.setMysqlHost(plugin.getConfig().getString("mysql.host"));
		this.setMysqlDatabase(plugin.getConfig().getString("mysql.database"));

		// Logging
		this.setLoggingDebug(plugin.getConfig().getBoolean("logging.debug"));

		// Broadcasts
		this.setBroadcastsNetworkConnect(plugin.getConfig().getString("broadcasts.network-connect"));
		this.setBroadcastsNetworkConnectNew(plugin.getConfig().getString("broadcasts.network-connect-new"));
		this.setBroadcastsNetworkDisconnect(plugin.getConfig().getString("broadcasts.network-disconnect"));
		this.setBroadcastsServerMove(plugin.getConfig().getString("broadcasts.server-move"));

		// Chat
		this.setChatFormat(plugin.getConfig().getString("chat.format"));
		this.setServerName(plugin.getConfig().getString("chat.server-name"));
		this.setServerShort(plugin.getConfig().getString("chat.server-short"));

		// Teleport > Wilderness
		this.setTeleportWildernessRadius(plugin.getConfig().getInt("teleport.wilderness.radius"));
		this.setTeleportWildernessCooldown(plugin.getConfig().getInt("teleport.wilderness.cooldown"));
		this.setTeleportWildernessDelay(plugin.getConfig().getInt("teleport.wilderness.delay"));
		this.setTeleportWildernessInterupt(plugin.getConfig().getBoolean("teleport.wilderness.interupt"));
		// Teleport > Tpr
		this.setTeleportTprCooldown(plugin.getConfig().getInt("teleport.tpr.cooldown"));
		this.setTeleportTprTimeout(plugin.getConfig().getInt("teleport.tpr.timeout"));
		this.setTeleportTprDelay(plugin.getConfig().getInt("teleport.tpr.delay"));
		this.setTeleportTprInterupt(plugin.getConfig().getBoolean("teleport.tpr.interupt"));
		// Teleport > Home
		this.setTeleportHomeCooldown(plugin.getConfig().getInt("teleport.home.cooldown"));
		this.setTeleportHomeDelay(plugin.getConfig().getInt("teleport.home.delay"));
		this.setTeleportHomeInterupt(plugin.getConfig().getBoolean("teleport.home.interupt"));
		// Teleport > Warp
		this.setTeleportWarpCooldown(plugin.getConfig().getInt("teleport.warp.cooldown"));
		this.setTeleportWarpDelay(plugin.getConfig().getInt("teleport.warp.delay"));
		this.setTeleportWarpInterupt(plugin.getConfig().getBoolean("teleport.warp.interupt"));
		// Teleport > World
		this.setTeleportWorldCooldown(plugin.getConfig().getInt("teleport.world.cooldown"));
		this.setTeleportWorldDelay(plugin.getConfig().getInt("teleport.world.delay"));
		this.setTeleportWorldInterupt(plugin.getConfig().getBoolean("teleport.world.interupt"));
		// Teleport > Spawn
		this.setTeleportSpawnCooldown(plugin.getConfig().getInt("teleport.spawn.cooldown"));
		this.setTeleportSpawnDelay(plugin.getConfig().getInt("teleport.spawn.delay"));
		this.setTeleportSpawnInterupt(plugin.getConfig().getBoolean("teleport.spawn.interupt"));

		// Announcements
		this.setAnnouncementInterval(plugin.getConfig().getInt("announcement.interval"));
		this.setAnnouncementMessages(plugin.getConfig().getStringList("announcement.messages"));

		// Rules
		this.setRules(plugin.getConfig().getStringList("rules"));

		// Ranks
		this.setRanks(plugin.getConfig().getStringList("ranks"));

		// Home > Settings
		this.setGuiHomeSettingsInventoryName(plugin.getConfig().getString("gui.home.settings.inventory-name"));
		this.setGuiHomeSettingsInventoryRows(plugin.getConfig().getInt("gui.home.settings.inventory-rows"));
		this.setGuiHomeSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.home.settings.toolbar-toolbar-item"));
		this.setGuiHomeSettingsToolbarBackItem(plugin.getConfig().getString("gui.home.settings.toolbar-back-item"));
		this.setGuiHomeSettingsToolbarPageItem(plugin.getConfig().getString("gui.home.settings.toolbar-page-item"));
		this.setGuiHomeSettingsToolbarExitItem(plugin.getConfig().getString("gui.home.settings.toolbar-exit-item"));
		// Home > Contents > Servers
		this.setGuiHomeContentsServersSlot(plugin.getConfig().getInt("gui.home.contents.servers.slot"));
		this.setGuiHomeContentsServersItem(plugin.getConfig().getString("gui.home.contents.servers.item"));
		this.setGuiHomeContentsServersName(plugin.getConfig().getString("gui.home.contents.servers.name"));
		this.setGuiHomeContentsServersLore(plugin.getConfig().getStringList("gui.home.contents.servers.lore"));
		// Home > Contents > Players
		this.setGuiHomeContentsPlayersSlot(plugin.getConfig().getInt("gui.home.contents.players.slot"));
		this.setGuiHomeContentsPlayersItem(plugin.getConfig().getString("gui.home.contents.players.item"));
		this.setGuiHomeContentsPlayersName(plugin.getConfig().getString("gui.home.contents.players.name"));
		this.setGuiHomeContentsPlayersLore(plugin.getConfig().getStringList("gui.home.contents.players.lore"));
		// Home > Contents > Friends
		this.setGuiHomeContentsFriendsSlot(plugin.getConfig().getInt("gui.home.contents.friends.slot"));
		this.setGuiHomeContentsFriendsItem(plugin.getConfig().getString("gui.home.contents.friends.item"));
		this.setGuiHomeContentsFriendsName(plugin.getConfig().getString("gui.home.contents.friends.name"));
		this.setGuiHomeContentsFriendsLore(plugin.getConfig().getStringList("gui.home.contents.friends.lore"));
		// Home > Contents > Rewards
		this.setGuiHomeContentsRewardsSlot(plugin.getConfig().getInt("gui.home.contents.rewards.slot"));
		this.setGuiHomeContentsRewardsItem(plugin.getConfig().getString("gui.home.contents.rewards.item"));
		this.setGuiHomeContentsRewardsName(plugin.getConfig().getString("gui.home.contents.rewards.name"));
		this.setGuiHomeContentsRewardsLore(plugin.getConfig().getStringList("gui.home.contents.rewards.lore"));
		// Home > Contents > Settings
		this.setGuiHomeContentsSettingsSlot(plugin.getConfig().getInt("gui.home.contents.settings.slot"));
		this.setGuiHomeContentsSettingsItem(plugin.getConfig().getString("gui.home.contents.settings.item"));
		this.setGuiHomeContentsSettingsName(plugin.getConfig().getString("gui.home.contents.settings.name"));
		this.setGuiHomeContentsSettingsLore(plugin.getConfig().getStringList("gui.home.contents.settings.lore"));
		// Home > Contents > Staff
		this.setGuiHomeContentsStaffSlot(plugin.getConfig().getInt("gui.home.contents.staff.slot"));
		this.setGuiHomeContentsStaffItem(plugin.getConfig().getString("gui.home.contents.staff.item"));
		this.setGuiHomeContentsStaffName(plugin.getConfig().getString("gui.home.contents.staff.name"));
		this.setGuiHomeContentsStaffLore(plugin.getConfig().getStringList("gui.home.contents.staff.lore"));
		// Home > Contents > Worlds
		this.setGuiHomeContentsWorldsSlot(plugin.getConfig().getInt("gui.home.contents.worlds.slot"));
		this.setGuiHomeContentsWorldsItem(plugin.getConfig().getString("gui.home.contents.worlds.item"));
		this.setGuiHomeContentsWorldsName(plugin.getConfig().getString("gui.home.contents.worlds.name"));
		this.setGuiHomeContentsWorldsLore(plugin.getConfig().getStringList("gui.home.contents.worlds.lore"));
		// Home > Contents > Warps
		this.setGuiHomeContentsWarpsSlot(plugin.getConfig().getInt("gui.home.contents.warps.slot"));
		this.setGuiHomeContentsWarpsItem(plugin.getConfig().getString("gui.home.contents.warps.item"));
		this.setGuiHomeContentsWarpsName(plugin.getConfig().getString("gui.home.contents.warps.name"));
		this.setGuiHomeContentsWarpsLore(plugin.getConfig().getStringList("gui.home.contents.warps.lore"));
		// Home > Contents > Homes
		this.setGuiHomeContentsHomesSlot(plugin.getConfig().getInt("gui.home.contents.homes.slot"));
		this.setGuiHomeContentsHomesItem(plugin.getConfig().getString("gui.home.contents.homes.item"));
		this.setGuiHomeContentsHomesName(plugin.getConfig().getString("gui.home.contents.homes.name"));
		this.setGuiHomeContentsHomesLore(plugin.getConfig().getStringList("gui.home.contents.homes.lore"));
		// Home > Contents > Applications
		this.setGuiHomeContentsApplicationsSlot(plugin.getConfig().getInt("gui.home.contents.applications.slot"));
		this.setGuiHomeContentsApplicationsItem(plugin.getConfig().getString("gui.home.contents.applications.item"));
		this.setGuiHomeContentsApplicationsName(plugin.getConfig().getString("gui.home.contents.applications.name"));
		this.setGuiHomeContentsApplicationsLore(plugin.getConfig().getStringList("gui.home.contents.applications.lore"));
		// Home > Contents > Rules
		this.setGuiHomeContentsRulesSlot(plugin.getConfig().getInt("gui.home.contents.rules.slot"));
		this.setGuiHomeContentsRulesItem(plugin.getConfig().getString("gui.home.contents.rules.item"));
		this.setGuiHomeContentsRulesName(plugin.getConfig().getString("gui.home.contents.rules.name"));
		this.setGuiHomeContentsRulesLore(plugin.getConfig().getStringList("gui.home.contents.rules.lore"));
		// Home > Contents > Ranks
		this.setGuiHomeContentsRanksSlot(plugin.getConfig().getInt("gui.home.contents.ranks.slot"));
		this.setGuiHomeContentsRanksItem(plugin.getConfig().getString("gui.home.contents.ranks.item"));
		this.setGuiHomeContentsRanksName(plugin.getConfig().getString("gui.home.contents.ranks.name"));
		this.setGuiHomeContentsRanksLore(plugin.getConfig().getStringList("gui.home.contents.ranks.lore"));

		// Servers > Settings
		this.setGuiServersSettingsInventoryName(plugin.getConfig().getString("gui.servers.settings.inventory-name"));
		this.setGuiServersSettingsInventoryRows(plugin.getConfig().getInt("gui.servers.settings.inventory-rows"));
		this.setGuiServersSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.servers.settings.toolbar-toolbar-item"));
		this.setGuiServersSettingsToolbarBackItem(plugin.getConfig().getString("gui.servers.settings.toolbar-back-item"));
		this.setGuiServersSettingsToolbarPageItem(plugin.getConfig().getString("gui.servers.settings.toolbar-page-item"));
		this.setGuiServersSettingsToolbarExitItem(plugin.getConfig().getString("gui.servers.settings.toolbar-exit-item"));
		// Servers > Contents > Hub
		this.setGuiServersContentsHubSlot(plugin.getConfig().getInt("gui.servers.contents.hub.slot"));
		this.setGuiServersContentsHubItem(plugin.getConfig().getString("gui.servers.contents.hub.item"));
		this.setGuiServersContentsHubName(plugin.getConfig().getString("gui.servers.contents.hub.name"));
		this.setGuiServersContentsHubLore(plugin.getConfig().getStringList("gui.servers.contents.hub.lore"));
		// Servers > Contents > Survival
		this.setGuiServersContentsSurvivalSlot(plugin.getConfig().getInt("gui.servers.contents.survival.slot"));
		this.setGuiServersContentsSurvivalItem(plugin.getConfig().getString("gui.servers.contents.survival.item"));
		this.setGuiServersContentsSurvivalName(plugin.getConfig().getString("gui.servers.contents.survival.name"));
		this.setGuiServersContentsSurvivalLore(plugin.getConfig().getStringList("gui.servers.contents.survival.lore"));
		// Servers > Contents > Creative
		this.setGuiServersContentsCreativeSlot(plugin.getConfig().getInt("gui.servers.contents.creative.slot"));
		this.setGuiServersContentsCreativeItem(plugin.getConfig().getString("gui.servers.contents.creative.item"));
		this.setGuiServersContentsCreativeName(plugin.getConfig().getString("gui.servers.contents.creative.name"));
		this.setGuiServersContentsCreativeLore(plugin.getConfig().getStringList("gui.servers.contents.creative.lore"));
		// Servers > Contents > Quest
		this.setGuiServersContentsQuestSlot(plugin.getConfig().getInt("gui.servers.contents.quest.slot"));
		this.setGuiServersContentsQuestItem(plugin.getConfig().getString("gui.servers.contents.quest.item"));
		this.setGuiServersContentsQuestName(plugin.getConfig().getString("gui.servers.contents.quest.name"));
		this.setGuiServersContentsQuestLore(plugin.getConfig().getStringList("gui.servers.contents.quest.lore"));
		// Servers > Contents > Legacy
		this.setGuiServersContentsLegacySlot(plugin.getConfig().getInt("gui.servers.contents.legacy.slot"));
		this.setGuiServersContentsLegacyItem(plugin.getConfig().getString("gui.servers.contents.legacy.item"));
		this.setGuiServersContentsLegacyName(plugin.getConfig().getString("gui.servers.contents.legacy.name"));
		this.setGuiServersContentsLegacyLore(plugin.getConfig().getStringList("gui.servers.contents.legacy.lore"));

		// Players > Settings
		this.setGuiPlayersSettingsInventoryName(plugin.getConfig().getString("gui.players.settings.inventory-name"));
		this.setGuiPlayersSettingsInventoryRows(plugin.getConfig().getInt("gui.players.settings.inventory-rows"));
		this.setGuiPlayersSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.players.settings.toolbar-toolbar-item"));
		this.setGuiPlayersSettingsToolbarBackItem(plugin.getConfig().getString("gui.players.settings.toolbar-back-item"));
		this.setGuiPlayersSettingsToolbarSearchItem(plugin.getConfig().getString("gui.players.settings.toolbar-search-item"));
		this.setGuiPlayersSettingsToolbarPageItem(plugin.getConfig().getString("gui.players.settings.toolbar-page-item"));
		this.setGuiPlayersSettingsToolbarExitItem(plugin.getConfig().getString("gui.players.settings.toolbar-exit-item"));

		// Friends > Settings
		this.setGuiFriendsSettingsInventoryName(plugin.getConfig().getString("gui.friends.settings.inventory-name"));
		this.setGuiFriendsSettingsInventoryRows(plugin.getConfig().getInt("gui.friends.settings.inventory-rows"));
		this.setGuiFriendsSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.friends.settings.toolbar-toolbar-item"));
		this.setGuiFriendsSettingsToolbarBackItem(plugin.getConfig().getString("gui.friends.settings.toolbar-back-item"));
		this.setGuiFriendsSettingsToolbarSearchItem(plugin.getConfig().getString("gui.friends.settings.toolbar-search-item"));
		this.setGuiFriendsSettingsToolbarPageItem(plugin.getConfig().getString("gui.friends.settings.toolbar-page-item"));
		this.setGuiFriendsSettingsToolbarExitItem(plugin.getConfig().getString("gui.friends.settings.toolbar-exit-item"));

		// Rewards > Settings
		this.setGuiRewardsSettingsInventoryName(plugin.getConfig().getString("gui.rewards.settings.inventory-name"));
		this.setGuiRewardsSettingsInventoryRows(plugin.getConfig().getInt("gui.rewards.settings.inventory-rows"));
		this.setGuiRewardsSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.rewards.settings.toolbar-toolbar-item"));
		this.setGuiRewardsSettingsToolbarBackItem(plugin.getConfig().getString("gui.rewards.settings.toolbar-back-item"));
		this.setGuiRewardsSettingsToolbarPageItem(plugin.getConfig().getString("gui.rewards.settings.toolbar-page-item"));
		this.setGuiRewardsSettingsToolbarExitItem(plugin.getConfig().getString("gui.rewards.settings.toolbar-exit-item"));
		// Rewards > Contents > Points
		this.setGuiRewardsContentsPointsSlot(plugin.getConfig().getInt("gui.rewards.contents.points.slot"));
		this.setGuiRewardsContentsPointsItem(plugin.getConfig().getString("gui.rewards.contents.points.item"));
		this.setGuiRewardsContentsPointsName(plugin.getConfig().getString("gui.rewards.contents.points.name"));
		this.setGuiRewardsContentsPointsLore(plugin.getConfig().getStringList("gui.rewards.contents.points.lore"));
		// Rewards > Contents > Vote
		this.setGuiRewardsContentsVoteSlot(plugin.getConfig().getInt("gui.rewards.contents.vote.slot"));
		this.setGuiRewardsContentsVoteItem(plugin.getConfig().getString("gui.rewards.contents.vote.item"));
		this.setGuiRewardsContentsVoteName(plugin.getConfig().getString("gui.rewards.contents.vote.name"));
		this.setGuiRewardsContentsVoteLore(plugin.getConfig().getStringList("gui.rewards.contents.vote.lore"));
		// Rewards > Contents > Donate
		this.setGuiRewardsContentsDonateSlot(plugin.getConfig().getInt("gui.rewards.contents.donate.slot"));
		this.setGuiRewardsContentsDonateItem(plugin.getConfig().getString("gui.rewards.contents.donate.item"));
		this.setGuiRewardsContentsDonateName(plugin.getConfig().getString("gui.rewards.contents.donate.name"));
		this.setGuiRewardsContentsDonateLore(plugin.getConfig().getStringList("gui.rewards.contents.donate.lore"));
		// Rewards > Contents > Challenges
		this.setGuiRewardsContentsChallengesSlot(plugin.getConfig().getInt("gui.rewards.contents.challenges.slot"));
		this.setGuiRewardsContentsChallengesItem(plugin.getConfig().getString("gui.rewards.contents.challenges.item"));
		this.setGuiRewardsContentsChallengesName(plugin.getConfig().getString("gui.rewards.contents.challenges.name"));
		this.setGuiRewardsContentsChallengesLore(plugin.getConfig().getStringList("gui.rewards.contents.challenges.lore"));
		// Rewards > Contents > Daily
		this.setGuiRewardsContentsDailySlot(plugin.getConfig().getInt("gui.rewards.contents.daily.slot"));
		this.setGuiRewardsContentsDailyItem(plugin.getConfig().getString("gui.rewards.contents.daily.item"));
		this.setGuiRewardsContentsDailyName(plugin.getConfig().getString("gui.rewards.contents.daily.name"));
		this.setGuiRewardsContentsDailyLore(plugin.getConfig().getStringList("gui.rewards.contents.daily.lore"));

		// Settings > Settings
		this.setGuiSettingsSettingsInventoryName(plugin.getConfig().getString("gui.settings.settings.inventory-name"));
		this.setGuiSettingsSettingsInventoryRows(plugin.getConfig().getInt("gui.settings.settings.inventory-rows"));
		this.setGuiSettingsSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.settings.settings.toolbar-toolbar-item"));
		this.setGuiSettingsSettingsToolbarBackItem(plugin.getConfig().getString("gui.settings.settings.toolbar-back-item"));
		this.setGuiSettingsSettingsToolbarPageItem(plugin.getConfig().getString("gui.settings.settings.toolbar-page-item"));
		this.setGuiSettingsSettingsToolbarExitItem(plugin.getConfig().getString("gui.settings.settings.toolbar-exit-item"));
		// Settings > Contents > Connect-Disconnect
		this.setGuiSettingsContentsConnectDisconnectSlot(plugin.getConfig().getInt("gui.settings.contents.connect-disconnect.slot"));
		this.setGuiSettingsContentsConnectDisconnectItem(plugin.getConfig().getString("gui.settings.contents.connect-disconnect.item"));
		this.setGuiSettingsContentsConnectDisconnectName(plugin.getConfig().getString("gui.settings.contents.connect-disconnect.name"));
		this.setGuiSettingsContentsConnectDisconnectLore(plugin.getConfig().getStringList("gui.settings.contents.connect-disconnect.lore"));
		// Settings > Contents > Connect-Disconnect-Control
		this.setGuiSettingsContentsConnectDisconnectControlSlot(plugin.getConfig().getInt("gui.settings.contents.connect-disconnect-control.slot"));
		this.setGuiSettingsContentsConnectDisconnectControlItemOn(plugin.getConfig().getString("gui.settings.contents.connect-disconnect-control.item-on"));
		this.setGuiSettingsContentsConnectDisconnectControlNameOn(plugin.getConfig().getString("gui.settings.contents.connect-disconnect-control.name-on"));
		this.setGuiSettingsContentsConnectDisconnectControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.connect-disconnect-control.lore-on"));
		this.setGuiSettingsContentsConnectDisconnectControlItemOff(plugin.getConfig().getString("gui.settings.contents.connect-disconnect-control.item-off"));
		this.setGuiSettingsContentsConnectDisconnectControlNameOff(plugin.getConfig().getString("gui.settings.contents.connect-disconnect-control.name-off"));
		this.setGuiSettingsContentsConnectDisconnectControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.connect-disconnect-control.lore-off"));
		// Settings > Contents > Server-Change
		this.setGuiSettingsContentsServerChangeSlot(plugin.getConfig().getInt("gui.settings.contents.server-change.slot"));
		this.setGuiSettingsContentsServerChangeItem(plugin.getConfig().getString("gui.settings.contents.server-change.item"));
		this.setGuiSettingsContentsServerChangeName(plugin.getConfig().getString("gui.settings.contents.server-change.name"));
		this.setGuiSettingsContentsServerChangeLore(plugin.getConfig().getStringList("gui.settings.contents.server-change.lore"));
		// Settings > Contents > Server-Change-Control
		this.setGuiSettingsContentsServerChangeControlSlot(plugin.getConfig().getInt("gui.settings.contents.server-change-control.slot"));
		this.setGuiSettingsContentsServerChangeControlItemOn(plugin.getConfig().getString("gui.settings.contents.server-change-control.item-on"));
		this.setGuiSettingsContentsServerChangeControlNameOn(plugin.getConfig().getString("gui.settings.contents.server-change-control.name-on"));
		this.setGuiSettingsContentsServerChangeControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.server-change-control.lore-on"));
		this.setGuiSettingsContentsServerChangeControlItemOff(plugin.getConfig().getString("gui.settings.contents.server-change-control.item-off"));
		this.setGuiSettingsContentsServerChangeControlNameOff(plugin.getConfig().getString("gui.settings.contents.server-change-control.name-off"));
		this.setGuiSettingsContentsServerChangeControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.server-change-control.lore-off"));
		// Settings > Contents > Player-Chat
		this.setGuiSettingsContentsPlayerChatSlot(plugin.getConfig().getInt("gui.settings.contents.player-chat.slot"));
		this.setGuiSettingsContentsPlayerChatItem(plugin.getConfig().getString("gui.settings.contents.player-chat.item"));
		this.setGuiSettingsContentsPlayerChatName(plugin.getConfig().getString("gui.settings.contents.player-chat.name"));
		this.setGuiSettingsContentsPlayerChatLore(plugin.getConfig().getStringList("gui.settings.contents.player-chat.lore"));
		// Settings > Contents > Player-Chat-Control
		this.setGuiSettingsContentsPlayerChatControlSlot(plugin.getConfig().getInt("gui.settings.contents.player-chat-control.slot"));
		this.setGuiSettingsContentsPlayerChatControlItemOn(plugin.getConfig().getString("gui.settings.contents.player-chat-control.item-on"));
		this.setGuiSettingsContentsPlayerChatControlNameOn(plugin.getConfig().getString("gui.settings.contents.player-chat-control.name-on"));
		this.setGuiSettingsContentsPlayerChatControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.player-chat-control.lore-on"));
		this.setGuiSettingsContentsPlayerChatControlItemOff(plugin.getConfig().getString("gui.settings.contents.player-chat-control.item-off"));
		this.setGuiSettingsContentsPlayerChatControlNameOff(plugin.getConfig().getString("gui.settings.contents.player-chat-control.name-off"));
		this.setGuiSettingsContentsPlayerChatControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.player-chat-control.lore-off"));
		// Settings > Contents > Server-Announcement
		this.setGuiSettingsContentsServerAnnouncementSlot(plugin.getConfig().getInt("gui.settings.contents.server-announcement.slot"));
		this.setGuiSettingsContentsServerAnnouncementItem(plugin.getConfig().getString("gui.settings.contents.server-announcement.item"));
		this.setGuiSettingsContentsServerAnnouncementName(plugin.getConfig().getString("gui.settings.contents.server-announcement.name"));
		this.setGuiSettingsContentsServerAnnouncementLore(plugin.getConfig().getStringList("gui.settings.contents.server-announcement.lore"));
		// Settings > Contents > Server-Announcement-Control
		this.setGuiSettingsContentsServerAnnouncementControlSlot(plugin.getConfig().getInt("gui.settings.contents.server-announcement-control.slot"));
		this.setGuiSettingsContentsServerAnnouncementControlItemOn(plugin.getConfig().getString("gui.settings.contents.server-announcement-control.item-on"));
		this.setGuiSettingsContentsServerAnnouncementControlNameOn(plugin.getConfig().getString("gui.settings.contents.server-announcement-control.name-on"));
		this.setGuiSettingsContentsServerAnnouncementControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.server-announcement-control.lore-on"));
		this.setGuiSettingsContentsServerAnnouncementControlItemOff(plugin.getConfig().getString("gui.settings.contents.server-announcement-control.item-off"));
		this.setGuiSettingsContentsServerAnnouncementControlNameOff(plugin.getConfig().getString("gui.settings.contents.server-announcement-control.name-off"));
		this.setGuiSettingsContentsServerAnnouncementControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.server-announcement-control.lore-off"));
		// Settings > Contents > Friend-Request
		this.setGuiSettingsContentsFriendRequestSlot(plugin.getConfig().getInt("gui.settings.contents.friend-request.slot"));
		this.setGuiSettingsContentsFriendRequestItem(plugin.getConfig().getString("gui.settings.contents.friend-request.item"));
		this.setGuiSettingsContentsFriendRequestName(plugin.getConfig().getString("gui.settings.contents.friend-request.name"));
		this.setGuiSettingsContentsFriendRequestLore(plugin.getConfig().getStringList("gui.settings.contents.friend-request.lore"));
		// Settings > Contents > Friend-Request-Control
		this.setGuiSettingsContentsFriendRequestControlSlot(plugin.getConfig().getInt("gui.settings.contents.friend-request-control.slot"));
		this.setGuiSettingsContentsFriendRequestControlItemOn(plugin.getConfig().getString("gui.settings.contents.friend-request-control.item-on"));
		this.setGuiSettingsContentsFriendRequestControlNameOn(plugin.getConfig().getString("gui.settings.contents.friend-request-control.name-on"));
		this.setGuiSettingsContentsFriendRequestControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.friend-request-control.lore-on"));
		this.setGuiSettingsContentsFriendRequestControlItemOff(plugin.getConfig().getString("gui.settings.contents.friend-request-control.item-off"));
		this.setGuiSettingsContentsFriendRequestControlNameOff(plugin.getConfig().getString("gui.settings.contents.friend-request-control.name-off"));
		this.setGuiSettingsContentsFriendRequestControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.friend-request-control.lore-off"));
		// Settings > Contents > Direct-Message
		this.setGuiSettingsContentsDirectMessageSlot(plugin.getConfig().getInt("gui.settings.contents.direct-message.slot"));
		this.setGuiSettingsContentsDirectMessageItem(plugin.getConfig().getString("gui.settings.contents.direct-message.item"));
		this.setGuiSettingsContentsDirectMessageName(plugin.getConfig().getString("gui.settings.contents.direct-message.name"));
		this.setGuiSettingsContentsDirectMessageLore(plugin.getConfig().getStringList("gui.settings.contents.direct-message.lore"));
		// Settings > Contents > Direct-Message-Control
		this.setGuiSettingsContentsDirectMessageControlSlot(plugin.getConfig().getInt("gui.settings.contents.direct-message-control.slot"));
		this.setGuiSettingsContentsDirectMessageControlItemOn(plugin.getConfig().getString("gui.settings.contents.direct-message-control.item-on"));
		this.setGuiSettingsContentsDirectMessageControlNameOn(plugin.getConfig().getString("gui.settings.contents.direct-message-control.name-on"));
		this.setGuiSettingsContentsDirectMessageControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.direct-message-control.lore-on"));
		this.setGuiSettingsContentsDirectMessageControlItemOff(plugin.getConfig().getString("gui.settings.contents.direct-message-control.item-off"));
		this.setGuiSettingsContentsDirectMessageControlNameOff(plugin.getConfig().getString("gui.settings.contents.direct-message-control.name-off"));
		this.setGuiSettingsContentsDirectMessageControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.direct-message-control.lore-off"));
		this.setGuiSettingsContentsDirectMessageControlItemFriend(plugin.getConfig().getString("gui.settings.contents.direct-message-control.item-friend"));
		this.setGuiSettingsContentsDirectMessageControlNameFriend(plugin.getConfig().getString("gui.settings.contents.direct-message-control.name-friend"));
		this.setGuiSettingsContentsDirectMessageControlLoreFriend(plugin.getConfig().getStringList("gui.settings.contents.direct-message-control.lore-friend"));
		// Settings > Contents > Teleport-Request
		this.setGuiSettingsContentsTeleportRequestSlot(plugin.getConfig().getInt("gui.settings.contents.teleport-request.slot"));
		this.setGuiSettingsContentsTeleportRequestItem(plugin.getConfig().getString("gui.settings.contents.teleport-request.item"));
		this.setGuiSettingsContentsTeleportRequestName(plugin.getConfig().getString("gui.settings.contents.teleport-request.name"));
		this.setGuiSettingsContentsTeleportRequestLore(plugin.getConfig().getStringList("gui.settings.contents.teleport-request.lore"));
		// Settings > Contents > Teleport-Request-Control
		this.setGuiSettingsContentsTeleportRequestControlSlot(plugin.getConfig().getInt("gui.settings.contents.teleport-request-control.slot"));
		this.setGuiSettingsContentsTeleportRequestControlItemOn(plugin.getConfig().getString("gui.settings.contents.teleport-request-control.item-on"));
		this.setGuiSettingsContentsTeleportRequestControlNameOn(plugin.getConfig().getString("gui.settings.contents.teleport-request-control.name-on"));
		this.setGuiSettingsContentsTeleportRequestControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.teleport-request-control.lore-on"));
		this.setGuiSettingsContentsTeleportRequestControlItemOff(plugin.getConfig().getString("gui.settings.contents.teleport-request-control.item-off"));
		this.setGuiSettingsContentsTeleportRequestControlNameOff(plugin.getConfig().getString("gui.settings.contents.teleport-request-control.name-off"));
		this.setGuiSettingsContentsTeleportRequestControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.teleport-request-control.lore-off"));
		this.setGuiSettingsContentsTeleportRequestControlItemFriend(plugin.getConfig().getString("gui.settings.contents.teleport-request-control.item-friend"));
		this.setGuiSettingsContentsTeleportRequestControlNameFriend(plugin.getConfig().getString("gui.settings.contents.teleport-request-control.name-friend"));
		this.setGuiSettingsContentsTeleportRequestControlLoreFriend(plugin.getConfig().getStringList("gui.settings.contents.teleport-request-control.lore-friend"));
		// Settings > Contents > Spectate-Request
		this.setGuiSettingsContentsSpectateRequestSlot(plugin.getConfig().getInt("gui.settings.contents.spectate-request.slot"));
		this.setGuiSettingsContentsSpectateRequestItem(plugin.getConfig().getString("gui.settings.contents.spectate-request.item"));
		this.setGuiSettingsContentsSpectateRequestName(plugin.getConfig().getString("gui.settings.contents.spectate-request.name"));
		this.setGuiSettingsContentsSpectateRequestLore(plugin.getConfig().getStringList("gui.settings.contents.spectate-request.lore"));
		// Settings > Contents > Spectate-Request-Control
		this.setGuiSettingsContentsSpectateRequestControlSlot(plugin.getConfig().getInt("gui.settings.contents.spectate-request-control.slot"));
		this.setGuiSettingsContentsSpectateRequestControlItemOn(plugin.getConfig().getString("gui.settings.contents.spectate-request-control.item-on"));
		this.setGuiSettingsContentsSpectateRequestControlNameOn(plugin.getConfig().getString("gui.settings.contents.spectate-request-control.name-on"));
		this.setGuiSettingsContentsSpectateRequestControlLoreOn(plugin.getConfig().getStringList("gui.settings.contents.spectate-request-control.lore-on"));
		this.setGuiSettingsContentsSpectateRequestControlItemOff(plugin.getConfig().getString("gui.settings.contents.spectate-request-control.item-off"));
		this.setGuiSettingsContentsSpectateRequestControlNameOff(plugin.getConfig().getString("gui.settings.contents.spectate-request-control.name-off"));
		this.setGuiSettingsContentsSpectateRequestControlLoreOff(plugin.getConfig().getStringList("gui.settings.contents.spectate-request-control.lore-off"));
		this.setGuiSettingsContentsSpectateRequestControlItemFriend(plugin.getConfig().getString("gui.settings.contents.spectate-request-control.item-friend"));
		this.setGuiSettingsContentsSpectateRequestControlNameFriend(plugin.getConfig().getString("gui.settings.contents.spectate-request-control.name-friend"));
		this.setGuiSettingsContentsSpectateRequestControlLoreFriend(plugin.getConfig().getStringList("gui.settings.contents.spectate-request-control.lore-friend"));

		// Worlds > Settings
		this.setGuiWorldsSettingsInventoryName(plugin.getConfig().getString("gui.worlds.settings.inventory-name"));
		this.setGuiWorldsSettingsInventoryRows(plugin.getConfig().getInt("gui.worlds.settings.inventory-rows"));
		this.setGuiWorldsSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.worlds.settings.toolbar-toolbar-item"));
		this.setGuiWorldsSettingsToolbarBackItem(plugin.getConfig().getString("gui.worlds.settings.toolbar-back-item"));
		this.setGuiWorldsSettingsToolbarSearchItem(plugin.getConfig().getString("gui.worlds.settings.toolbar-search-item"));
		this.setGuiWorldsSettingsToolbarPageItem(plugin.getConfig().getString("gui.worlds.settings.toolbar-page-item"));
		this.setGuiWorldsSettingsToolbarExitItem(plugin.getConfig().getString("gui.worlds.settings.toolbar-exit-item"));

		// Warps > Settings
		this.setGuiWarpsSettingsInventoryName(plugin.getConfig().getString("gui.warps.settings.inventory-name"));
		this.setGuiWarpsSettingsInventoryRows(plugin.getConfig().getInt("gui.warps.settings.inventory-rows"));
		this.setGuiWarpsSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.warps.settings.toolbar-toolbar-item"));
		this.setGuiWarpsSettingsToolbarBackItem(plugin.getConfig().getString("gui.warps.settings.toolbar-back-item"));
		this.setGuiWarpsSettingsToolbarSearchItem(plugin.getConfig().getString("gui.warps.settings.toolbar-search-item"));
		this.setGuiWarpsSettingsToolbarPageItem(plugin.getConfig().getString("gui.warps.settings.toolbar-page-item"));
		this.setGuiWarpsSettingsToolbarExitItem(plugin.getConfig().getString("gui.warps.settings.toolbar-exit-item"));

		// Homes > Settings
		this.setGuiHomesSettingsInventoryName(plugin.getConfig().getString("gui.homes.settings.inventory-name"));
		this.setGuiHomesSettingsInventoryRows(plugin.getConfig().getInt("gui.homes.settings.inventory-rows"));
		this.setGuiHomesSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.homes.settings.toolbar-toolbar-item"));
		this.setGuiHomesSettingsToolbarBackItem(plugin.getConfig().getString("gui.homes.settings.toolbar-back-item"));
		this.setGuiHomesSettingsToolbarSearchItem(plugin.getConfig().getString("gui.homes.settings.toolbar-search-item"));
		this.setGuiHomesSettingsToolbarPageItem(plugin.getConfig().getString("gui.homes.settings.toolbar-page-item"));
		this.setGuiHomesSettingsToolbarExitItem(plugin.getConfig().getString("gui.homes.settings.toolbar-exit-item"));

		// Applications > Settings
		this.setGuiApplicationsSettingsInventoryName(plugin.getConfig().getString("gui.applications.settings.inventory-name"));
		this.setGuiApplicationsSettingsInventoryRows(plugin.getConfig().getInt("gui.applications.settings.inventory-rows"));
		this.setGuiApplicationsSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.applications.settings.toolbar-toolbar-item"));
		this.setGuiApplicationsSettingsToolbarBackItem(plugin.getConfig().getString("gui.applications.settings.toolbar-back-item"));
		this.setGuiApplicationsSettingsToolbarPageItem(plugin.getConfig().getString("gui.applications.settings.toolbar-page-item"));
		this.setGuiApplicationsSettingsToolbarExitItem(plugin.getConfig().getString("gui.applications.settings.toolbar-exit-item"));

		// Rules > Settings
		this.setGuiRulesSettingsInventoryName(plugin.getConfig().getString("gui.rules.settings.inventory-name"));
		this.setGuiRulesSettingsInventoryRows(plugin.getConfig().getInt("gui.rules.settings.inventory-rows"));
		this.setGuiRulesSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.rules.settings.toolbar-toolbar-item"));
		this.setGuiRulesSettingsToolbarBackItem(plugin.getConfig().getString("gui.rules.settings.toolbar-back-item"));
		this.setGuiRulesSettingsToolbarListItem(plugin.getConfig().getString("gui.rules.settings.toolbar-list-item"));
		this.setGuiRulesSettingsToolbarPageItem(plugin.getConfig().getString("gui.rules.settings.toolbar-page-item"));
		this.setGuiRulesSettingsToolbarExitItem(plugin.getConfig().getString("gui.rules.settings.toolbar-exit-item"));

		// Ranks > Settings
		this.setGuiRanksSettingsInventoryName(plugin.getConfig().getString("gui.ranks.settings.inventory-name"));
		this.setGuiRanksSettingsInventoryRows(plugin.getConfig().getInt("gui.ranks.settings.inventory-rows"));
		this.setGuiRanksSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.ranks.settings.toolbar-toolbar-item"));
		this.setGuiRanksSettingsToolbarBackItem(plugin.getConfig().getString("gui.ranks.settings.toolbar-back-item"));
		this.setGuiRanksSettingsToolbarListItem(plugin.getConfig().getString("gui.ranks.settings.toolbar-list-item"));
		this.setGuiRanksSettingsToolbarPageItem(plugin.getConfig().getString("gui.ranks.settings.toolbar-page-item"));
		this.setGuiRanksSettingsToolbarExitItem(plugin.getConfig().getString("gui.ranks.settings.toolbar-exit-item"));

		// Player > Settings
		this.setGuiPlayerSettingsInventoryName(plugin.getConfig().getString("gui.player.settings.inventory-name"));
		this.setGuiPlayerSettingsInventoryRows(plugin.getConfig().getInt("gui.player.settings.inventory-rows"));
		this.setGuiPlayerSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.player.settings.toolbar-toolbar-item"));
		this.setGuiPlayerSettingsToolbarBackItem(plugin.getConfig().getString("gui.player.settings.toolbar-back-item"));
		this.setGuiPlayerSettingsToolbarPageItem(plugin.getConfig().getString("gui.player.settings.toolbar-page-item"));
		this.setGuiPlayerSettingsToolbarExitItem(plugin.getConfig().getString("gui.player.settings.toolbar-exit-item"));
		// Player > Contents > Friend
		this.setGuiPlayerContentsFriendSlot(plugin.getConfig().getInt("gui.player.contents.friend.slot"));
		this.setGuiPlayerContentsFriendItem(plugin.getConfig().getString("gui.player.contents.friend.item"));
		this.setGuiPlayerContentsFriendName(plugin.getConfig().getString("gui.player.contents.friend.name"));
		this.setGuiPlayerContentsFriendLore(plugin.getConfig().getStringList("gui.player.contents.friend.lore"));
		// Player > Contents > Message
		this.setGuiPlayerContentsMessageSlot(plugin.getConfig().getInt("gui.player.contents.message.slot"));
		this.setGuiPlayerContentsMessageItem(plugin.getConfig().getString("gui.player.contents.message.item"));
		this.setGuiPlayerContentsMessageName(plugin.getConfig().getString("gui.player.contents.message.name"));
		this.setGuiPlayerContentsMessageLore(plugin.getConfig().getStringList("gui.player.contents.message.lore"));
		// Player > Contents > Teleport
		this.setGuiPlayerContentsTeleportSlot(plugin.getConfig().getInt("gui.player.contents.teleport.slot"));
		this.setGuiPlayerContentsTeleportItem(plugin.getConfig().getString("gui.player.contents.teleport.item"));
		this.setGuiPlayerContentsTeleportName(plugin.getConfig().getString("gui.player.contents.teleport.name"));
		this.setGuiPlayerContentsTeleportLore(plugin.getConfig().getStringList("gui.player.contents.teleport.lore"));
		// Player > Contents > Spectate
		this.setGuiPlayerContentsSpectateSlot(plugin.getConfig().getInt("gui.player.contents.spectate.slot"));
		this.setGuiPlayerContentsSpectateItem(plugin.getConfig().getString("gui.player.contents.spectate.item"));
		this.setGuiPlayerContentsSpectateName(plugin.getConfig().getString("gui.player.contents.spectate.name"));
		this.setGuiPlayerContentsSpectateLore(plugin.getConfig().getStringList("gui.player.contents.spectate.lore"));
		// Player > Contents > Report
		this.setGuiPlayerContentsReportSlot(plugin.getConfig().getInt("gui.player.contents.report.slot"));
		this.setGuiPlayerContentsReportItem(plugin.getConfig().getString("gui.player.contents.report.item"));
		this.setGuiPlayerContentsReportName(plugin.getConfig().getString("gui.player.contents.report.name"));
		this.setGuiPlayerContentsReportLore(plugin.getConfig().getStringList("gui.player.contents.report.lore"));

		// Staff > Settings
		this.setGuiStaffSettingsInventoryName(plugin.getConfig().getString("gui.staff.settings.inventory-name"));
		this.setGuiStaffSettingsInventoryRows(plugin.getConfig().getInt("gui.staff.settings.inventory-rows"));
		this.setGuiStaffSettingsToolbarToolbarItem(plugin.getConfig().getString("gui.staff.settings.toolbar-toolbar-item"));
		this.setGuiStaffSettingsToolbarBackItem(plugin.getConfig().getString("gui.staff.settings.toolbar-back-item"));
		this.setGuiStaffSettingsToolbarPageItem(plugin.getConfig().getString("gui.staff.settings.toolbar-page-item"));
		this.setGuiStaffSettingsToolbarExitItem(plugin.getConfig().getString("gui.staff.settings.toolbar-exit-item"));
		// Staff > Content > Warn
		this.setGuiStaffContentsWarnSlot(plugin.getConfig().getInt("gui.staff.contents.warn.slot"));
		this.setGuiStaffContentsWarnItem(plugin.getConfig().getString("gui.staff.contents.warn.item"));
		this.setGuiStaffContentsWarnName(plugin.getConfig().getString("gui.staff.contents.warn.name"));
		this.setGuiStaffContentsWarnLore(plugin.getConfig().getStringList("gui.staff.contents.warn.lore"));
		// Staff > Content > Mute
		this.setGuiStaffContentsMuteSlot(plugin.getConfig().getInt("gui.staff.contents.mute.slot"));
		this.setGuiStaffContentsMuteItem(plugin.getConfig().getString("gui.staff.contents.mute.item"));
		this.setGuiStaffContentsMuteName(plugin.getConfig().getString("gui.staff.contents.mute.name"));
		this.setGuiStaffContentsMuteLore(plugin.getConfig().getStringList("gui.staff.contents.mute.lore"));
		// Staff > Content > Freeze
		this.setGuiStaffContentsFreezeSlot(plugin.getConfig().getInt("gui.staff.contents.freeze.slot"));
		this.setGuiStaffContentsFreezeItem(plugin.getConfig().getString("gui.staff.contents.freeze.item"));
		this.setGuiStaffContentsFreezeName(plugin.getConfig().getString("gui.staff.contents.freeze.name"));
		this.setGuiStaffContentsFreezeLore(plugin.getConfig().getStringList("gui.staff.contents.freeze.lore"));
		// Staff > Content > Kick
		this.setGuiStaffContentsKickSlot(plugin.getConfig().getInt("gui.staff.contents.kick.slot"));
		this.setGuiStaffContentsKickItem(plugin.getConfig().getString("gui.staff.contents.kick.item"));
		this.setGuiStaffContentsKickName(plugin.getConfig().getString("gui.staff.contents.kick.name"));
		this.setGuiStaffContentsKickLore(plugin.getConfig().getStringList("gui.staff.contents.kick.lore"));
		// Staff > Content > Ban
		this.setGuiStaffContentsBanSlot(plugin.getConfig().getInt("gui.staff.contents.ban.slot"));
		this.setGuiStaffContentsBanItem(plugin.getConfig().getString("gui.staff.contents.ban.item"));
		this.setGuiStaffContentsBanName(plugin.getConfig().getString("gui.staff.contents.ban.name"));
		this.setGuiStaffContentsBanLore(plugin.getConfig().getStringList("gui.staff.contents.ban.lore"));
		// Staff > Content > Tickets
		this.setGuiStaffContentsTicketsSlot(plugin.getConfig().getInt("gui.staff.contents.tickets.slot"));
		this.setGuiStaffContentsTicketsItem(plugin.getConfig().getString("gui.staff.contents.tickets.item"));
		this.setGuiStaffContentsTicketsName(plugin.getConfig().getString("gui.staff.contents.tickets.name"));
		this.setGuiStaffContentsTicketsLore(plugin.getConfig().getStringList("gui.staff.contents.tickets.lore"));
		// Staff > Content > Notes
		this.setGuiStaffContentsNotesSlot(plugin.getConfig().getInt("gui.staff.contents.notes.slot"));
		this.setGuiStaffContentsNotesItem(plugin.getConfig().getString("gui.staff.contents.notes.item"));
		this.setGuiStaffContentsNotesName(plugin.getConfig().getString("gui.staff.contents.notes.name"));
		this.setGuiStaffContentsNotesLore(plugin.getConfig().getStringList("gui.staff.contents.notes.lore"));
		// Staff > Content > Bans
		this.setGuiStaffContentsBansSlot(plugin.getConfig().getInt("gui.staff.contents.bans.slot"));
		this.setGuiStaffContentsBansItem(plugin.getConfig().getString("gui.staff.contents.bans.item"));
		this.setGuiStaffContentsBansName(plugin.getConfig().getString("gui.staff.contents.bans.name"));
		this.setGuiStaffContentsBansLore(plugin.getConfig().getStringList("gui.staff.contents.bans.lore"));
	}

	// MySQL

	private String mysqlUser;
	private String mysqlPort;
	private String mysqlPassword;
	private String mysqlHost;
	private String mysqlDatabase;

	public void setMysqlUser(String mysqlUser) {
		this.mysqlUser = mysqlUser;
	}
	public String getMysqlUser() {
		return this.mysqlUser;
	}

	public void setMysqlPort(String mysqlPort) {
		this.mysqlPort = mysqlPort;
	}
	public String getMysqlPort() {
		return this.mysqlPort;
	}

	public void setMysqlPassword(String mysqlPassword) {
		this.mysqlPassword = mysqlPassword;
	}
	public String getMysqlPassword() {
		return this.mysqlPassword;
	}

	public void setMysqlHost(String mysqlHost) {
		this.mysqlHost = mysqlHost;
	}
	public String getMysqlHost() {
		return this.mysqlHost;
	}

	public void setMysqlDatabase(String mysqlDatabase) {
		this.mysqlDatabase = mysqlDatabase;
	}
	public String getMysqlDatabase() {
		return this.mysqlDatabase;
	}

	// Logging

	private boolean loggingDebug;

	public void setLoggingDebug(boolean loggingDebug) {
		this.loggingDebug = loggingDebug;
	}
	public boolean getLoggingDebug() {
		return this.loggingDebug;
	}

	// Broadcasts

	private String broadcastsNetworkConnect;
	private String broadcastsNetworkConnectNew;
	private String broadcastsNetworkDisconnect;
	private String broadcastsServerMove;

	public void setBroadcastsNetworkConnect(String broadcastsNetworkConnect) {
		this.broadcastsNetworkConnect = broadcastsNetworkConnect;
	}
	public String getBroadcastsNetworkConnect() {
		return this.broadcastsNetworkConnect;
	}

	public void setBroadcastsNetworkConnectNew(String broadcastsNetworkConnectNew) {
		this.broadcastsNetworkConnectNew = broadcastsNetworkConnectNew;
	}
	public String getBroadcastsNetworkConnectNew() {
		return this.broadcastsNetworkConnectNew;
	}

	public void setBroadcastsNetworkDisconnect(String broadcastsNetworkDisconnect) {
		this.broadcastsNetworkDisconnect = broadcastsNetworkDisconnect;
	}
	public String getBroadcastsNetworkDisconnect() {
		return this.broadcastsNetworkDisconnect;
	}

	public void setBroadcastsServerMove(String broadcastsServerMove) {
		this.broadcastsServerMove = broadcastsServerMove;
	}
	public String getBroadcastsServerMove() {
		return this.broadcastsServerMove;
	}

	// Chat

	private String chatFormat;
	private String serverName;
	private String serverShort;

	public void setChatFormat(String chatFormat) {
		this.chatFormat = chatFormat;
	}
	public String getChatFormat() {
		return this.chatFormat;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerName() {
		return this.serverName;
	}

	public void setServerShort(String serverShort) {
		this.serverShort = serverShort;
	}
	public String getServerShort() {
		return this.serverShort;
	}

	// Teleport > Wilderness

	private int teleportWildernessRadius;
	private int teleportWildernessCooldown;
	private int teleportWildernessDelay;
	private boolean teleportWildernessInterupt;

	public void setTeleportWildernessRadius(int teleportWildernessRadius) {
		this.teleportWildernessRadius = teleportWildernessRadius;
	}
	public int getTeleportWildernessRadius() {
		return this.teleportWildernessRadius;
	}
	
	public void setTeleportWildernessCooldown(int teleportWildernessCooldown) {
		this.teleportWildernessCooldown = teleportWildernessCooldown;
	}
	public int getTeleportWildernessCooldown() {
		return this.teleportWildernessCooldown;
	}
	
	public void setTeleportWildernessDelay(int teleportWildernessDelay) {
		this.teleportWildernessDelay = teleportWildernessDelay;
	}
	public int getTeleportWildernessDelay() {
		return this.teleportWildernessDelay;
	}
	
	public void setTeleportWildernessInterupt(boolean teleportWildernessInterupt) {
		this.teleportWildernessInterupt = teleportWildernessInterupt;
	}
	public boolean getTeleportWildernessInterupt() {
		return this.teleportWildernessInterupt;
	}

	// Teleport > Tpr

	private int teleportTprCooldown;
	private int teleportTprTimeout;
	private int teleportTprDelay;
	private boolean teleportTprInterupt;

	public void setTeleportTprCooldown(int teleportTprCooldown) {
		this.teleportTprCooldown = teleportTprCooldown;
	}
	public int getTeleportTprCooldown() {
		return this.teleportTprCooldown;
	}
	
	public void setTeleportTprTimeout(int teleportTprTimeout) {
		this.teleportTprTimeout = teleportTprTimeout;
	}
	public int getTeleportTprTimeout() {
		return this.teleportTprTimeout;
	}

	public void setTeleportTprDelay(int teleportTprDelay) {
		this.teleportTprDelay = teleportTprDelay;
	}
	public int getTeleportTprDelay() {
		return this.teleportTprDelay;
	}

	public void setTeleportTprInterupt(boolean teleportTprInterupt) {
		this.teleportTprInterupt = teleportTprInterupt;
	}
	public boolean getTeleportTprInterupt() {
		return this.teleportTprInterupt;
	}
	
	// Teleport > Home

	private int teleportHomeCooldown;
	private int teleportHomeDelay;
	private boolean teleportHomeInterupt;

	public void setTeleportHomeCooldown(int teleportHomeCooldown) {
		this.teleportHomeCooldown = teleportHomeCooldown;
	}
	public int getTeleportHomeCooldown() {
		return this.teleportHomeCooldown;
	}

	public void setTeleportHomeDelay(int teleportHomeDelay) {
		this.teleportHomeDelay = teleportHomeDelay;
	}
	public int getTeleportHomeDelay() {
		return this.teleportHomeDelay;
	}

	public void setTeleportHomeInterupt(boolean teleportHomeInterupt) {
		this.teleportHomeInterupt = teleportHomeInterupt;
	}
	public boolean getTeleportHomeInterupt() {
		return this.teleportHomeInterupt;
	}
	
	// Teleport > Warp

	private int teleportWarpCooldown;
	private int teleportWarpDelay;
	private boolean teleportWarpInterupt;

	public void setTeleportWarpCooldown(int teleportWarpCooldown) {
		this.teleportWarpCooldown = teleportWarpCooldown;
	}
	public int getTeleportWarpCooldown() {
		return this.teleportWarpCooldown;
	}

	public void setTeleportWarpDelay(int teleportWarpDelay) {
		this.teleportWarpDelay = teleportWarpDelay;
	}
	public int getTeleportWarpDelay() {
		return this.teleportWarpDelay;
	}

	public void setTeleportWarpInterupt(boolean teleportWarpInterupt) {
		this.teleportWarpInterupt = teleportWarpInterupt;
	}
	public boolean getTeleportWarpInterupt() {
		return this.teleportWarpInterupt;
	}
	
	// Teleport > World

	private int teleportWorldCooldown;
	private int teleportWorldDelay;
	private boolean teleportWorldInterupt;

	public void setTeleportWorldCooldown(int teleportWorldCooldown) {
		this.teleportWorldCooldown = teleportWorldCooldown;
	}
	public int getTeleportWorldCooldown() {
		return this.teleportWorldCooldown;
	}

	public void setTeleportWorldDelay(int teleportWorldDelay) {
		this.teleportWorldDelay = teleportWorldDelay;
	}
	public int getTeleportWorldDelay() {
		return this.teleportWorldDelay;
	}

	public void setTeleportWorldInterupt(boolean teleportWorldInterupt) {
		this.teleportWorldInterupt = teleportWorldInterupt;
	}
	public boolean getTeleportWorldInterupt() {
		return this.teleportWorldInterupt;
	}
	
	// Teleport > Spawn

	private int teleportSpawnCooldown;
	private int teleportSpawnDelay;
	private boolean teleportSpawnInterupt;

	public void setTeleportSpawnCooldown(int teleportSpawnCooldown) {
		this.teleportSpawnCooldown = teleportSpawnCooldown;
	}
	public int getTeleportSpawnCooldown() {
		return this.teleportSpawnCooldown;
	}

	public void setTeleportSpawnDelay(int teleportSpawnDelay) {
		this.teleportSpawnDelay = teleportSpawnDelay;
	}
	public int getTeleportSpawnDelay() {
		return this.teleportSpawnDelay;
	}

	public void setTeleportSpawnInterupt(boolean teleportSpawnInterupt) {
		this.teleportSpawnInterupt = teleportSpawnInterupt;
	}
	public boolean getTeleportSpawnInterupt() {
		return this.teleportSpawnInterupt;
	}

	// Announcement

	private int announcementInterval;
	private List<String> announcementMessages;

	public void setAnnouncementInterval(int announcementInterval) {
		this.announcementInterval = announcementInterval;
	}
	public int getAnnouncementInterval() {
		return this.announcementInterval;
	}

	public void setAnnouncementMessages(List<String> announcementMessages) {
		this.announcementMessages = announcementMessages;
	}
	public List<String> getAnnouncementMessages() {
		return this.announcementMessages;
	}

	// Rules

	private List<String> rules;

	public void setRules(List<String> rules) {
		this.rules = rules;
	}
	public List<String> getRules() {
		return this.rules;
	}

	// Ranks

	private List<String> ranks;

	public void setRanks(List<String> ranks) {
		this.ranks = ranks;
	}
	public List<String> getRanks() {
		return this.ranks;
	}

	// Gui > Home > Settings

	private String guiHomeSettingsInventoryName;
	private int guiHomeSettingsInventoryRows;
	private String guiHomeSettingsToolbarToolbarItem;
	private String guiHomeSettingsToolbarBackItem;
	private String guiHomeSettingsToolbarPageItem;
	private String guiHomeSettingsToolbarExitItem;

	public void setGuiHomeSettingsInventoryName(String guiHomeSettingsInventoryName) {
		this.guiHomeSettingsInventoryName = guiHomeSettingsInventoryName;
	}
	public String getGuiHomeSettingsInventoryName() {
		return this.guiHomeSettingsInventoryName;
	}

	public void setGuiHomeSettingsInventoryRows(int guiHomeSettingsInventoryRows) {
		this.guiHomeSettingsInventoryRows = guiHomeSettingsInventoryRows;
	}
	public int getGuiHomeSettingsInventoryRows() {
		return this.guiHomeSettingsInventoryRows;
	}

	public void setGuiHomeSettingsToolbarToolbarItem(String guiHomeSettingsToolbarToolbarItem) {
		this.guiHomeSettingsToolbarToolbarItem = guiHomeSettingsToolbarToolbarItem;
	}
	public String getGuiHomeSettingsToolbarToolbarItem() {
		return this.guiHomeSettingsToolbarToolbarItem;
	}

	public void setGuiHomeSettingsToolbarBackItem(String guiHomeSettingsToolbarBackItem) {
		this.guiHomeSettingsToolbarBackItem = guiHomeSettingsToolbarBackItem;
	}
	public String getGuiHomeSettingsToolbarBackItem() {
		return this.guiHomeSettingsToolbarBackItem;
	}

	public void setGuiHomeSettingsToolbarPageItem(String guiHomeSettingsToolbarPageItem) {
		this.guiHomeSettingsToolbarPageItem = guiHomeSettingsToolbarPageItem;
	}
	public String getGuiHomeSettingsToolbarPageItem() {
		return this.guiHomeSettingsToolbarPageItem;
	}

	public void setGuiHomeSettingsToolbarExitItem(String guiHomeSettingsToolbarExitItem) {
		this.guiHomeSettingsToolbarExitItem = guiHomeSettingsToolbarExitItem;
	}
	public String getGuiHomeSettingsToolbarExitItem() {
		return this.guiHomeSettingsToolbarExitItem;
	}

	// Gui > Home > Contents > Servers

	private int guiHomeContentsServersSlot;
	private String guiHomeContentsServersItem;
	private String guiHomeContentsServersName;
	private List<String> guiHomeContentsServersLore;

	public void setGuiHomeContentsServersSlot(int guiHomeContentsServersSlot) {
		this.guiHomeContentsServersSlot = guiHomeContentsServersSlot;
	}
	public int getGuiHomeContentsServersSlot() {
		return this.guiHomeContentsServersSlot;
	}

	public void setGuiHomeContentsServersItem(String guiHomeContentsServersItem) {
		this.guiHomeContentsServersItem = guiHomeContentsServersItem;
	}
	public String getGuiHomeContentsServersItem() {
		return this.guiHomeContentsServersItem;
	}

	public void setGuiHomeContentsServersName(String guiHomeContentsServersName) {
		this.guiHomeContentsServersName = guiHomeContentsServersName;
	}
	public String getGuiHomeContentsServersName() {
		return this.guiHomeContentsServersName;
	}

	public void setGuiHomeContentsServersLore(List<String> guiHomeContentsServersLore) {
		this.guiHomeContentsServersLore = guiHomeContentsServersLore;
	}
	public List<String> getGuiHomeContentsServersLore() {
		return this.guiHomeContentsServersLore;
	}

	// Gui > Home > Contents > Players

	private int guiHomeContentsPlayersSlot;
	private String guiHomeContentsPlayersItem;
	private String guiHomeContentsPlayersName;
	private List<String> guiHomeContentsPlayersLore;

	public void setGuiHomeContentsPlayersSlot(int guiHomeContentsPlayersSlot) {
		this.guiHomeContentsPlayersSlot = guiHomeContentsPlayersSlot;
	}
	public int getGuiHomeContentsPlayersSlot() {
		return this.guiHomeContentsPlayersSlot;
	}

	public void setGuiHomeContentsPlayersItem(String guiHomeContentsPlayersItem) {
		this.guiHomeContentsPlayersItem = guiHomeContentsPlayersItem;
	}
	public String getGuiHomeContentsPlayersItem() {
		return this.guiHomeContentsPlayersItem;
	}

	public void setGuiHomeContentsPlayersName(String guiHomeContentsPlayersName) {
		this.guiHomeContentsPlayersName = guiHomeContentsPlayersName;
	}
	public String getGuiHomeContentsPlayersName() {
		return this.guiHomeContentsPlayersName;
	}

	public void setGuiHomeContentsPlayersLore(List<String> guiHomeContentsPlayersLore) {
		this.guiHomeContentsPlayersLore = guiHomeContentsPlayersLore;
	}
	public List<String> getGuiHomeContentsPlayersLore() {
		return this.guiHomeContentsPlayersLore;
	}

	// Gui > Home > Contents > Friends

	private int guiHomeContentsFriendsSlot;
	private String guiHomeContentsFriendsItem;
	private String guiHomeContentsFriendsName;
	private List<String> guiHomeContentsFriendsLore;

	public void setGuiHomeContentsFriendsSlot(int guiHomeContentsFriendsSlot) {
		this.guiHomeContentsFriendsSlot = guiHomeContentsFriendsSlot;
	}
	public int getGuiHomeContentsFriendsSlot() {
		return this.guiHomeContentsFriendsSlot;
	}

	public void setGuiHomeContentsFriendsItem(String guiHomeContentsFriendsItem) {
		this.guiHomeContentsFriendsItem = guiHomeContentsFriendsItem;
	}
	public String getGuiHomeContentsFriendsItem() {
		return this.guiHomeContentsFriendsItem;
	}

	public void setGuiHomeContentsFriendsName(String guiHomeContentsFriendsName) {
		this.guiHomeContentsFriendsName = guiHomeContentsFriendsName;
	}
	public String getGuiHomeContentsFriendsName() {
		return this.guiHomeContentsFriendsName;
	}

	public void setGuiHomeContentsFriendsLore(List<String> guiHomeContentsFriendsLore) {
		this.guiHomeContentsFriendsLore = guiHomeContentsFriendsLore;
	}
	public List<String> getGuiHomeContentsFriendsLore() {
		return this.guiHomeContentsFriendsLore;
	}

	// Gui > Home > Contents > Rewards

	private int guiHomeContentsRewardsSlot;
	private String guiHomeContentsRewardsItem;
	private String guiHomeContentsRewardsName;
	private List<String> guiHomeContentsRewardsLore;

	public void setGuiHomeContentsRewardsSlot(int guiHomeContentsRewardsSlot) {
		this.guiHomeContentsRewardsSlot = guiHomeContentsRewardsSlot;
	}
	public int getGuiHomeContentsRewardsSlot() {
		return this.guiHomeContentsRewardsSlot;
	}

	public void setGuiHomeContentsRewardsItem(String guiHomeContentsRewardsItem) {
		this.guiHomeContentsRewardsItem = guiHomeContentsRewardsItem;
	}
	public String getGuiHomeContentsRewardsItem() {
		return this.guiHomeContentsRewardsItem;
	}

	public void setGuiHomeContentsRewardsName(String guiHomeContentsRewardsName) {
		this.guiHomeContentsRewardsName = guiHomeContentsRewardsName;
	}
	public String getGuiHomeContentsRewardsName() {
		return this.guiHomeContentsRewardsName;
	}

	public void setGuiHomeContentsRewardsLore(List<String> guiHomeContentsRewardsLore) {
		this.guiHomeContentsRewardsLore = guiHomeContentsRewardsLore;
	}
	public List<String> getGuiHomeContentsRewardsLore() {
		return this.guiHomeContentsRewardsLore;
	}

	// Gui > Home > Contents > Settings

	private int guiHomeContentsSettingsSlot;
	private String guiHomeContentsSettingsItem;
	private String guiHomeContentsSettingsName;
	private List<String> guiHomeContentsSettingsLore;

	public void setGuiHomeContentsSettingsSlot(int guiHomeContentsSettingsSlot) {
		this.guiHomeContentsSettingsSlot = guiHomeContentsSettingsSlot;
	}
	public int getGuiHomeContentsSettingsSlot() {
		return this.guiHomeContentsSettingsSlot;
	}

	public void setGuiHomeContentsSettingsItem(String guiHomeContentsSettingsItem) {
		this.guiHomeContentsSettingsItem = guiHomeContentsSettingsItem;
	}
	public String getGuiHomeContentsSettingsItem() {
		return this.guiHomeContentsSettingsItem;
	}

	public void setGuiHomeContentsSettingsName(String guiHomeContentsSettingsName) {
		this.guiHomeContentsSettingsName = guiHomeContentsSettingsName;
	}
	public String getGuiHomeContentsSettingsName() {
		return this.guiHomeContentsSettingsName;
	}

	public void setGuiHomeContentsSettingsLore(List<String> guiHomeContentsSettingsLore) {
		this.guiHomeContentsSettingsLore = guiHomeContentsSettingsLore;
	}
	public List<String> getGuiHomeContentsSettingsLore() {
		return this.guiHomeContentsSettingsLore;
	}

	// Gui > Home > Contents > Staff

	private int guiHomeContentsStaffSlot;
	private String guiHomeContentsStaffItem;
	private String guiHomeContentsStaffName;
	private List<String> guiHomeContentsStaffLore;

	public void setGuiHomeContentsStaffSlot(int guiHomeContentsStaffSlot) {
		this.guiHomeContentsStaffSlot = guiHomeContentsStaffSlot;
	}
	public int getGuiHomeContentsStaffSlot() {
		return this.guiHomeContentsStaffSlot;
	}

	public void setGuiHomeContentsStaffItem(String guiHomeContentsStaffItem) {
		this.guiHomeContentsStaffItem = guiHomeContentsStaffItem;
	}
	public String getGuiHomeContentsStaffItem() {
		return this.guiHomeContentsStaffItem;
	}

	public void setGuiHomeContentsStaffName(String guiHomeContentsStaffName) {
		this.guiHomeContentsStaffName = guiHomeContentsStaffName;
	}
	public String getGuiHomeContentsStaffName() {
		return this.guiHomeContentsStaffName;
	}

	public void setGuiHomeContentsStaffLore(List<String> guiHomeContentsStaffLore) {
		this.guiHomeContentsStaffLore = guiHomeContentsStaffLore;
	}
	public List<String> getGuiHomeContentsStaffLore() {
		return this.guiHomeContentsStaffLore;
	}

	// Gui > Home > Contents > Worlds

	private int guiHomeContentsWorldsSlot;
	private String guiHomeContentsWorldsItem;
	private String guiHomeContentsWorldsName;
	private List<String> guiHomeContentsWorldsLore;

	public void setGuiHomeContentsWorldsSlot(int guiHomeContentsWorldsSlot) {
		this.guiHomeContentsWorldsSlot = guiHomeContentsWorldsSlot;
	}
	public int getGuiHomeContentsWorldsSlot() {
		return this.guiHomeContentsWorldsSlot;
	}

	public void setGuiHomeContentsWorldsItem(String guiHomeContentsWorldsItem) {
		this.guiHomeContentsWorldsItem = guiHomeContentsWorldsItem;
	}
	public String getGuiHomeContentsWorldsItem() {
		return this.guiHomeContentsWorldsItem;
	}

	public void setGuiHomeContentsWorldsName(String guiHomeContentsWorldsName) {
		this.guiHomeContentsWorldsName = guiHomeContentsWorldsName;
	}
	public String getGuiHomeContentsWorldsName() {
		return this.guiHomeContentsWorldsName;
	}

	public void setGuiHomeContentsWorldsLore(List<String> guiHomeContentsWorldsLore) {
		this.guiHomeContentsWorldsLore = guiHomeContentsWorldsLore;
	}
	public List<String> getGuiHomeContentsWorldsLore() {
		return this.guiHomeContentsWorldsLore;
	}

	// Gui > Home > Contents > Warps

	private int guiHomeContentsWarpsSlot;
	private String guiHomeContentsWarpsItem;
	private String guiHomeContentsWarpsName;
	private List<String> guiHomeContentsWarpsLore;

	public void setGuiHomeContentsWarpsSlot(int guiHomeContentsWarpsSlot) {
		this.guiHomeContentsWarpsSlot = guiHomeContentsWarpsSlot;
	}
	public int getGuiHomeContentsWarpsSlot() {
		return this.guiHomeContentsWarpsSlot;
	}

	public void setGuiHomeContentsWarpsItem(String guiHomeContentsWarpsItem) {
		this.guiHomeContentsWarpsItem = guiHomeContentsWarpsItem;
	}
	public String getGuiHomeContentsWarpsItem() {
		return this.guiHomeContentsWarpsItem;
	}

	public void setGuiHomeContentsWarpsName(String guiHomeContentsWarpsName) {
		this.guiHomeContentsWarpsName = guiHomeContentsWarpsName;
	}
	public String getGuiHomeContentsWarpsName() {
		return this.guiHomeContentsWarpsName;
	}

	public void setGuiHomeContentsWarpsLore(List<String> guiHomeContentsWarpsLore) {
		this.guiHomeContentsWarpsLore = guiHomeContentsWarpsLore;
	}
	public List<String> getGuiHomeContentsWarpsLore() {
		return this.guiHomeContentsWarpsLore;
	}
	
	// Gui > Home > Contents > Homes

	private int guiHomeContentsHomesSlot;
	private String guiHomeContentsHomesItem;
	private String guiHomeContentsHomesName;
	private List<String> guiHomeContentsHomesLore;

	public void setGuiHomeContentsHomesSlot(int guiHomeContentsHomesSlot) {
		this.guiHomeContentsHomesSlot = guiHomeContentsHomesSlot;
	}
	public int getGuiHomeContentsHomesSlot() {
		return this.guiHomeContentsHomesSlot;
	}

	public void setGuiHomeContentsHomesItem(String guiHomeContentsHomesItem) {
		this.guiHomeContentsHomesItem = guiHomeContentsHomesItem;
	}
	public String getGuiHomeContentsHomesItem() {
		return this.guiHomeContentsHomesItem;
	}

	public void setGuiHomeContentsHomesName(String guiHomeContentsHomesName) {
		this.guiHomeContentsHomesName = guiHomeContentsHomesName;
	}
	public String getGuiHomeContentsHomesName() {
		return this.guiHomeContentsHomesName;
	}

	public void setGuiHomeContentsHomesLore(List<String> guiHomeContentsHomesLore) {
		this.guiHomeContentsHomesLore = guiHomeContentsHomesLore;
	}
	public List<String> getGuiHomeContentsHomesLore() {
		return this.guiHomeContentsHomesLore;
	}

	// Gui > Home > Contents > Applications

	private int guiHomeContentsApplicationsSlot;
	private String guiHomeContentsApplicationsItem;
	private String guiHomeContentsApplicationsName;
	private List<String> guiHomeContentsApplicationsLore;

	public void setGuiHomeContentsApplicationsSlot(int guiHomeContentsApplicationsSlot) {
		this.guiHomeContentsApplicationsSlot = guiHomeContentsApplicationsSlot;
	}
	public int getGuiHomeContentsApplicationsSlot() {
		return this.guiHomeContentsApplicationsSlot;
	}

	public void setGuiHomeContentsApplicationsItem(String guiHomeContentsApplicationsItem) {
		this.guiHomeContentsApplicationsItem = guiHomeContentsApplicationsItem;
	}
	public String getGuiHomeContentsApplicationsItem() {
		return this.guiHomeContentsApplicationsItem;
	}

	public void setGuiHomeContentsApplicationsName(String guiHomeContentsApplicationsName) {
		this.guiHomeContentsApplicationsName = guiHomeContentsApplicationsName;
	}
	public String getGuiHomeContentsApplicationsName() {
		return this.guiHomeContentsApplicationsName;
	}

	public void setGuiHomeContentsApplicationsLore(List<String> guiHomeContentsApplicationsLore) {
		this.guiHomeContentsApplicationsLore = guiHomeContentsApplicationsLore;
	}
	public List<String> getGuiHomeContentsApplicationsLore() {
		return this.guiHomeContentsApplicationsLore;
	}

	// Gui > Home > Contents > Rules

	private int guiHomeContentsRulesSlot;
	private String guiHomeContentsRulesItem;
	private String guiHomeContentsRulesName;
	private List<String> guiHomeContentsRulesLore;

	public void setGuiHomeContentsRulesSlot(int guiHomeContentsRulesSlot) {
		this.guiHomeContentsRulesSlot = guiHomeContentsRulesSlot;
	}
	public int getGuiHomeContentsRulesSlot() {
		return this.guiHomeContentsRulesSlot;
	}

	public void setGuiHomeContentsRulesItem(String guiHomeContentsRulesItem) {
		this.guiHomeContentsRulesItem = guiHomeContentsRulesItem;
	}
	public String getGuiHomeContentsRulesItem() {
		return this.guiHomeContentsRulesItem;
	}

	public void setGuiHomeContentsRulesName(String guiHomeContentsRulesName) {
		this.guiHomeContentsRulesName = guiHomeContentsRulesName;
	}
	public String getGuiHomeContentsRulesName() {
		return this.guiHomeContentsRulesName;
	}

	public void setGuiHomeContentsRulesLore(List<String> guiHomeContentsRulesLore) {
		this.guiHomeContentsRulesLore = guiHomeContentsRulesLore;
	}
	public List<String> getGuiHomeContentsRulesLore() {
		return this.guiHomeContentsRulesLore;
	}

	// Gui > Home > Contents > Ranks

	private int guiHomeContentsRanksSlot;
	private String guiHomeContentsRanksItem;
	private String guiHomeContentsRanksName;
	private List<String> guiHomeContentsRanksLore;

	public void setGuiHomeContentsRanksSlot(int guiHomeContentsRanksSlot) {
		this.guiHomeContentsRanksSlot = guiHomeContentsRanksSlot;
	}
	public int getGuiHomeContentsRanksSlot() {
		return this.guiHomeContentsRanksSlot;
	}

	public void setGuiHomeContentsRanksItem(String guiHomeContentsRanksItem) {
		this.guiHomeContentsRanksItem = guiHomeContentsRanksItem;
	}
	public String getGuiHomeContentsRanksItem() {
		return this.guiHomeContentsRanksItem;
	}

	public void setGuiHomeContentsRanksName(String guiHomeContentsRanksName) {
		this.guiHomeContentsRanksName = guiHomeContentsRanksName;
	}
	public String getGuiHomeContentsRanksName() {
		return this.guiHomeContentsRanksName;
	}

	public void setGuiHomeContentsRanksLore(List<String> guiHomeContentsRanksLore) {
		this.guiHomeContentsRanksLore = guiHomeContentsRanksLore;
	}
	public List<String> getGuiHomeContentsRanksLore() {
		return this.guiHomeContentsRanksLore;
	}

	// Gui > Servers > Settings

	private String guiServersSettingsInventoryName;
	private int guiServersSettingsInventoryRows;
	private String guiServersSettingsToolbarToolbarItem;
	private String guiServersSettingsToolbarBackItem;
	private String guiServersSettingsToolbarPageItem;
	private String guiServersSettingsToolbarExitItem;

	public void setGuiServersSettingsInventoryName(String guiServersSettingsInventoryName) {
		this.guiServersSettingsInventoryName = guiServersSettingsInventoryName;
	}
	public String getGuiServersSettingsInventoryName() {
		return this.guiServersSettingsInventoryName;
	}

	public void setGuiServersSettingsInventoryRows(int guiServersSettingsInventoryRows) {
		this.guiServersSettingsInventoryRows = guiServersSettingsInventoryRows;
	}
	public int getGuiServersSettingsInventoryRows() {
		return this.guiServersSettingsInventoryRows;
	}

	public void setGuiServersSettingsToolbarToolbarItem(String guiServersSettingsToolbarToolbarItem) {
		this.guiServersSettingsToolbarToolbarItem = guiServersSettingsToolbarToolbarItem;
	}
	public String getGuiServersSettingsToolbarToolbarItem() {
		return this.guiServersSettingsToolbarToolbarItem;
	}

	public void setGuiServersSettingsToolbarBackItem(String guiServersSettingsToolbarBackItem) {
		this.guiServersSettingsToolbarBackItem = guiServersSettingsToolbarBackItem;
	}
	public String getGuiServersSettingsToolbarBackItem() {
		return this.guiServersSettingsToolbarBackItem;
	}

	public void setGuiServersSettingsToolbarPageItem(String guiServersSettingsToolbarPageItem) {
		this.guiServersSettingsToolbarPageItem = guiServersSettingsToolbarPageItem;
	}
	public String getGuiServersSettingsToolbarPageItem() {
		return this.guiServersSettingsToolbarPageItem;
	}

	public void setGuiServersSettingsToolbarExitItem(String guiServersSettingsToolbarExitItem) {
		this.guiServersSettingsToolbarExitItem = guiServersSettingsToolbarExitItem;
	}
	public String getGuiServersSettingsToolbarExitItem() {
		return this.guiServersSettingsToolbarExitItem;
	}

	// Gui > Servers > Contents > Hub

	private int guiServersContentsHubSlot;
	private String guiServersContentsHubItem;
	private String guiServersContentsHubName;
	private List<String> guiServersContentsHubLore;

	public void setGuiServersContentsHubSlot(int guiServersContentsHubSlot) {
		this.guiServersContentsHubSlot = guiServersContentsHubSlot;
	}
	public int getGuiServersContentsHubSlot() {
		return this.guiServersContentsHubSlot;
	}

	public void setGuiServersContentsHubItem(String guiServersContentsHubItem) {
		this.guiServersContentsHubItem = guiServersContentsHubItem;
	}
	public String getGuiServersContentsHubItem() {
		return this.guiServersContentsHubItem;
	}

	public void setGuiServersContentsHubName(String guiServersContentsHubName) {
		this.guiServersContentsHubName = guiServersContentsHubName;
	}
	public String getGuiServersContentsHubName() {
		return this.guiServersContentsHubName;
	}

	public void setGuiServersContentsHubLore(List<String> guiServersContentsHubLore) {
		this.guiServersContentsHubLore = guiServersContentsHubLore;
	}
	public List<String> getGuiServersContentsHubLore() {
		return this.guiServersContentsHubLore;
	}

	// Gui > Servers > Contents > Survival

	private int guiServersContentsSurvivalSlot;
	private String guiServersContentsSurvivalItem;
	private String guiServersContentsSurvivalName;
	private List<String> guiServersContentsSurvivalLore;

	public void setGuiServersContentsSurvivalSlot(int guiServersContentsSurvivalSlot) {
		this.guiServersContentsSurvivalSlot = guiServersContentsSurvivalSlot;
	}
	public int getGuiServersContentsSurvivalSlot() {
		return this.guiServersContentsSurvivalSlot;
	}

	public void setGuiServersContentsSurvivalItem(String guiServersContentsSurvivalItem) {
		this.guiServersContentsSurvivalItem = guiServersContentsSurvivalItem;
	}
	public String getGuiServersContentsSurvivalItem() {
		return this.guiServersContentsSurvivalItem;
	}

	public void setGuiServersContentsSurvivalName(String guiServersContentsSurvivalName) {
		this.guiServersContentsSurvivalName = guiServersContentsSurvivalName;
	}
	public String getGuiServersContentsSurvivalName() {
		return this.guiServersContentsSurvivalName;
	}

	public void setGuiServersContentsSurvivalLore(List<String> guiServersContentsSurvivalLore) {
		this.guiServersContentsSurvivalLore = guiServersContentsSurvivalLore;
	}
	public List<String> getGuiServersContentsSurvivalLore() {
		return this.guiServersContentsSurvivalLore;
	}

	// Gui > Servers > Contents > Creative

	private int guiServersContentsCreativeSlot;
	private String guiServersContentsCreativeItem;
	private String guiServersContentsCreativeName;
	private List<String> guiServersContentsCreativeLore;

	public void setGuiServersContentsCreativeSlot(int guiServersContentsCreativeSlot) {
		this.guiServersContentsCreativeSlot = guiServersContentsCreativeSlot;
	}
	public int getGuiServersContentsCreativeSlot() {
		return this.guiServersContentsCreativeSlot;
	}

	public void setGuiServersContentsCreativeItem(String guiServersContentsCreativeItem) {
		this.guiServersContentsCreativeItem = guiServersContentsCreativeItem;
	}
	public String getGuiServersContentsCreativeItem() {
		return this.guiServersContentsCreativeItem;
	}

	public void setGuiServersContentsCreativeName(String guiServersContentsCreativeName) {
		this.guiServersContentsCreativeName = guiServersContentsCreativeName;
	}
	public String getGuiServersContentsCreativeName() {
		return this.guiServersContentsCreativeName;
	}

	public void setGuiServersContentsCreativeLore(List<String> guiServersContentsCreativeLore) {
		this.guiServersContentsCreativeLore = guiServersContentsCreativeLore;
	}
	public List<String> getGuiServersContentsCreativeLore() {
		return this.guiServersContentsCreativeLore;
	}

	// Gui > Servers > Contents > Quest

	private int guiServersContentsQuestSlot;
	private String guiServersContentsQuestItem;
	private String guiServersContentsQuestName;
	private List<String> guiServersContentsQuestLore;

	public void setGuiServersContentsQuestSlot(int guiServersContentsQuestSlot) {
		this.guiServersContentsQuestSlot = guiServersContentsQuestSlot;
	}
	public int getGuiServersContentsQuestSlot() {
		return this.guiServersContentsQuestSlot;
	}

	public void setGuiServersContentsQuestItem(String guiServersContentsQuestItem) {
		this.guiServersContentsQuestItem = guiServersContentsQuestItem;
	}
	public String getGuiServersContentsQuestItem() {
		return this.guiServersContentsQuestItem;
	}

	public void setGuiServersContentsQuestName(String guiServersContentsQuestName) {
		this.guiServersContentsQuestName = guiServersContentsQuestName;
	}
	public String getGuiServersContentsQuestName() {
		return this.guiServersContentsQuestName;
	}

	public void setGuiServersContentsQuestLore(List<String> guiServersContentsQuestLore) {
		this.guiServersContentsQuestLore = guiServersContentsQuestLore;
	}
	public List<String> getGuiServersContentsQuestLore() {
		return this.guiServersContentsQuestLore;
	}

	// Gui > Servers > Contents > Legacy

	private int guiServersContentsLegacySlot;
	private String guiServersContentsLegacyItem;
	private String guiServersContentsLegacyName;
	private List<String> guiServersContentsLegacyLore;

	public void setGuiServersContentsLegacySlot(int guiServersContentsLegacySlot) {
		this.guiServersContentsLegacySlot = guiServersContentsLegacySlot;
	}
	public int getGuiServersContentsLegacySlot() {
		return this.guiServersContentsLegacySlot;
	}

	public void setGuiServersContentsLegacyItem(String guiServersContentsLegacyItem) {
		this.guiServersContentsLegacyItem = guiServersContentsLegacyItem;
	}
	public String getGuiServersContentsLegacyItem() {
		return this.guiServersContentsLegacyItem;
	}

	public void setGuiServersContentsLegacyName(String guiServersContentsLegacyName) {
		this.guiServersContentsLegacyName = guiServersContentsLegacyName;
	}
	public String getGuiServersContentsLegacyName() {
		return this.guiServersContentsLegacyName;
	}

	public void setGuiServersContentsLegacyLore(List<String> guiServersContentsLegacyLore) {
		this.guiServersContentsLegacyLore = guiServersContentsLegacyLore;
	}
	public List<String> getGuiServersContentsLegacyLore() {
		return this.guiServersContentsLegacyLore;
	}

	// Gui > Players > Settings

	private String guiPlayersSettingsInventoryName;
	private int guiPlayersSettingsInventoryRows;
	private String guiPlayersSettingsToolbarToolbarItem;
	private String guiPlayersSettingsToolbarBackItem;
	private String guiPlayersSettingsToolbarSearchItem;
	private String guiPlayersSettingsToolbarPageItem;
	private String guiPlayersSettingsToolbarExitItem;

	public void setGuiPlayersSettingsInventoryName(String guiPlayersSettingsInventoryName) {
		this.guiPlayersSettingsInventoryName = guiPlayersSettingsInventoryName;
	}
	public String getGuiPlayersSettingsInventoryName() {
		return this.guiPlayersSettingsInventoryName;
	}

	public void setGuiPlayersSettingsInventoryRows(int guiPlayersSettingsInventoryRows) {
		this.guiPlayersSettingsInventoryRows = guiPlayersSettingsInventoryRows;
	}
	public int getGuiPlayersSettingsInventoryRows() {
		return this.guiPlayersSettingsInventoryRows;
	}

	public void setGuiPlayersSettingsToolbarToolbarItem(String guiPlayersSettingsToolbarToolbarItem) {
		this.guiPlayersSettingsToolbarToolbarItem = guiPlayersSettingsToolbarToolbarItem;
	}
	public String getGuiPlayersSettingsToolbarToolbarItem() {
		return this.guiPlayersSettingsToolbarToolbarItem;
	}

	public void setGuiPlayersSettingsToolbarBackItem(String guiPlayersSettingsToolbarBackItem) {
		this.guiPlayersSettingsToolbarBackItem = guiPlayersSettingsToolbarBackItem;
	}
	public String getGuiPlayersSettingsToolbarBackItem() {
		return this.guiPlayersSettingsToolbarBackItem;
	}

	public void setGuiPlayersSettingsToolbarSearchItem(String guiPlayersSettingsToolbarSearchItem) {
		this.guiPlayersSettingsToolbarSearchItem = guiPlayersSettingsToolbarSearchItem;
	}
	public String getGuiPlayersSettingsToolbarSearchItem() {
		return this.guiPlayersSettingsToolbarSearchItem;
	}

	public void setGuiPlayersSettingsToolbarPageItem(String guiPlayersSettingsToolbarPageItem) {
		this.guiPlayersSettingsToolbarPageItem = guiPlayersSettingsToolbarPageItem;
	}
	public String getGuiPlayersSettingsToolbarPageItem() {
		return this.guiPlayersSettingsToolbarPageItem;
	}

	public void setGuiPlayersSettingsToolbarExitItem(String guiPlayersSettingsToolbarExitItem) {
		this.guiPlayersSettingsToolbarExitItem = guiPlayersSettingsToolbarExitItem;
	}
	public String getGuiPlayersSettingsToolbarExitItem() {
		return this.guiPlayersSettingsToolbarExitItem;
	}

	// Gui > Friends > Settings

	private String guiFriendsSettingsInventoryName;
	private int guiFriendsSettingsInventoryRows;
	private String guiFriendsSettingsToolbarToolbarItem;
	private String guiFriendsSettingsToolbarBackItem;
	private String guiFriendsSettingsToolbarSearchItem;
	private String guiFriendsSettingsToolbarPageItem;
	private String guiFriendsSettingsToolbarExitItem;

	public void setGuiFriendsSettingsInventoryName(String guiFriendsSettingsInventoryName) {
		this.guiFriendsSettingsInventoryName = guiFriendsSettingsInventoryName;
	}
	public String getGuiFriendsSettingsInventoryName() {
		return this.guiFriendsSettingsInventoryName;
	}

	public void setGuiFriendsSettingsInventoryRows(int guiFriendsSettingsInventoryRows) {
		this.guiFriendsSettingsInventoryRows = guiFriendsSettingsInventoryRows;
	}
	public int getGuiFriendsSettingsInventoryRows() {
		return this.guiFriendsSettingsInventoryRows;
	}

	public void setGuiFriendsSettingsToolbarToolbarItem(String guiFriendsSettingsToolbarToolbarItem) {
		this.guiFriendsSettingsToolbarToolbarItem = guiFriendsSettingsToolbarToolbarItem;
	}
	public String getGuiFriendsSettingsToolbarToolbarItem() {
		return this.guiFriendsSettingsToolbarToolbarItem;
	}

	public void setGuiFriendsSettingsToolbarBackItem(String guiFriendsSettingsToolbarBackItem) {
		this.guiFriendsSettingsToolbarBackItem = guiFriendsSettingsToolbarBackItem;
	}
	public String getGuiFriendsSettingsToolbarBackItem() {
		return this.guiFriendsSettingsToolbarBackItem;
	}

	public void setGuiFriendsSettingsToolbarSearchItem(String guiFriendsSettingsToolbarSearchItem) {
		this.guiFriendsSettingsToolbarSearchItem = guiFriendsSettingsToolbarSearchItem;
	}
	public String getGuiFriendsSettingsToolbarSearchItem() {
		return this.guiFriendsSettingsToolbarSearchItem;
	}

	public void setGuiFriendsSettingsToolbarPageItem(String guiFriendsSettingsToolbarPageItem) {
		this.guiFriendsSettingsToolbarPageItem = guiFriendsSettingsToolbarPageItem;
	}
	public String getGuiFriendsSettingsToolbarPageItem() {
		return this.guiFriendsSettingsToolbarPageItem;
	}

	public void setGuiFriendsSettingsToolbarExitItem(String guiFriendsSettingsToolbarExitItem) {
		this.guiFriendsSettingsToolbarExitItem = guiFriendsSettingsToolbarExitItem;
	}
	public String getGuiFriendsSettingsToolbarExitItem() {
		return this.guiFriendsSettingsToolbarExitItem;
	}

	// Gui > Rewards > Settings

	private String guiRewardsSettingsInventoryName;
	private int guiRewardsSettingsInventoryRows;
	private String guiRewardsSettingsToolbarToolbarItem;
	private String guiRewardsSettingsToolbarBackItem;
	private String guiRewardsSettingsToolbarPageItem;
	private String guiRewardsSettingsToolbarExitItem;

	public void setGuiRewardsSettingsInventoryName(String guiRewardsSettingsInventoryName) {
		this.guiRewardsSettingsInventoryName = guiRewardsSettingsInventoryName;
	}
	public String getGuiRewardsSettingsInventoryName() {
		return this.guiRewardsSettingsInventoryName;
	}

	public void setGuiRewardsSettingsInventoryRows(int guiRewardsSettingsInventoryRows) {
		this.guiRewardsSettingsInventoryRows = guiRewardsSettingsInventoryRows;
	}
	public int getGuiRewardsSettingsInventoryRows() {
		return this.guiRewardsSettingsInventoryRows;
	}

	public void setGuiRewardsSettingsToolbarToolbarItem(String guiRewardsSettingsToolbarToolbarItem) {
		this.guiRewardsSettingsToolbarToolbarItem = guiRewardsSettingsToolbarToolbarItem;
	}
	public String getGuiRewardsSettingsToolbarToolbarItem() {
		return this.guiRewardsSettingsToolbarToolbarItem;
	}

	public void setGuiRewardsSettingsToolbarBackItem(String guiRewardsSettingsToolbarBackItem) {
		this.guiRewardsSettingsToolbarBackItem = guiRewardsSettingsToolbarBackItem;
	}
	public String getGuiRewardsSettingsToolbarBackItem() {
		return this.guiRewardsSettingsToolbarBackItem;
	}

	public void setGuiRewardsSettingsToolbarPageItem(String guiRewardsSettingsToolbarPageItem) {
		this.guiRewardsSettingsToolbarPageItem = guiRewardsSettingsToolbarPageItem;
	}
	public String getGuiRewardsSettingsToolbarPageItem() {
		return this.guiRewardsSettingsToolbarPageItem;
	}

	public void setGuiRewardsSettingsToolbarExitItem(String guiRewardsSettingsToolbarExitItem) {
		this.guiRewardsSettingsToolbarExitItem = guiRewardsSettingsToolbarExitItem;
	}
	public String getGuiRewardsSettingsToolbarExitItem() {
		return this.guiRewardsSettingsToolbarExitItem;
	}

	// Gui > Rewards > Contents > Points

	private int guiRewardsContentsPointsSlot;
	private String guiRewardsContentsPointsItem;
	private String guiRewardsContentsPointsName;
	private List<String> guiRewardsContentsPointsLore;

	public void setGuiRewardsContentsPointsSlot(int guiRewardsContentsPointsSlot) {
		this.guiRewardsContentsPointsSlot = guiRewardsContentsPointsSlot;
	}
	public int getGuiRewardsContentsPointsSlot() {
		return this.guiRewardsContentsPointsSlot;
	}

	public void setGuiRewardsContentsPointsItem(String guiRewardsContentsPointsItem) {
		this.guiRewardsContentsPointsItem = guiRewardsContentsPointsItem;
	}
	public String getGuiRewardsContentsPointsItem() {
		return this.guiRewardsContentsPointsItem;
	}

	public void setGuiRewardsContentsPointsName(String guiRewardsContentsPointsName) {
		this.guiRewardsContentsPointsName = guiRewardsContentsPointsName;
	}
	public String getGuiRewardsContentsPointsName() {
		return this.guiRewardsContentsPointsName;
	}

	public void setGuiRewardsContentsPointsLore(List<String> guiRewardsContentsPointsLore) {
		this.guiRewardsContentsPointsLore = guiRewardsContentsPointsLore;
	}
	public List<String> getGuiRewardsContentsPointsLore() {
		return this.guiRewardsContentsPointsLore;
	}

	// Gui > Rewards > Contents > Vote

	private int guiRewardsContentsVoteSlot;
	private String guiRewardsContentsVoteItem;
	private String guiRewardsContentsVoteName;
	private List<String> guiRewardsContentsVoteLore;

	public void setGuiRewardsContentsVoteSlot(int guiRewardsContentsVoteSlot) {
		this.guiRewardsContentsVoteSlot = guiRewardsContentsVoteSlot;
	}
	public int getGuiRewardsContentsVoteSlot() {
		return this.guiRewardsContentsVoteSlot;
	}

	public void setGuiRewardsContentsVoteItem(String guiRewardsContentsVoteItem) {
		this.guiRewardsContentsVoteItem = guiRewardsContentsVoteItem;
	}
	public String getGuiRewardsContentsVoteItem() {
		return this.guiRewardsContentsVoteItem;
	}

	public void setGuiRewardsContentsVoteName(String guiRewardsContentsVoteName) {
		this.guiRewardsContentsVoteName = guiRewardsContentsVoteName;
	}
	public String getGuiRewardsContentsVoteName() {
		return this.guiRewardsContentsVoteName;
	}

	public void setGuiRewardsContentsVoteLore(List<String> guiRewardsContentsVoteLore) {
		this.guiRewardsContentsVoteLore = guiRewardsContentsVoteLore;
	}
	public List<String> getGuiRewardsContentsVoteLore() {
		return this.guiRewardsContentsVoteLore;
	}

	// Gui > Rewards > Contents > Donate

	private int guiRewardsContentsDonateSlot;
	private String guiRewardsContentsDonateItem;
	private String guiRewardsContentsDonateName;
	private List<String> guiRewardsContentsDonateLore;

	public void setGuiRewardsContentsDonateSlot(int guiRewardsContentsDonateSlot) {
		this.guiRewardsContentsDonateSlot = guiRewardsContentsDonateSlot;
	}
	public int getGuiRewardsContentsDonateSlot() {
		return this.guiRewardsContentsDonateSlot;
	}

	public void setGuiRewardsContentsDonateItem(String guiRewardsContentsDonateItem) {
		this.guiRewardsContentsDonateItem = guiRewardsContentsDonateItem;
	}
	public String getGuiRewardsContentsDonateItem() {
		return this.guiRewardsContentsDonateItem;
	}

	public void setGuiRewardsContentsDonateName(String guiRewardsContentsDonateName) {
		this.guiRewardsContentsDonateName = guiRewardsContentsDonateName;
	}
	public String getGuiRewardsContentsDonateName() {
		return this.guiRewardsContentsDonateName;
	}

	public void setGuiRewardsContentsDonateLore(List<String> guiRewardsContentsDonateLore) {
		this.guiRewardsContentsDonateLore = guiRewardsContentsDonateLore;
	}
	public List<String> getGuiRewardsContentsDonateLore() {
		return this.guiRewardsContentsDonateLore;
	}

	// Gui > Rewards > Contents > Challenges

	private int guiRewardsContentsChallengesSlot;
	private String guiRewardsContentsChallengesItem;
	private String guiRewardsContentsChallengesName;
	private List<String> guiRewardsContentsChallengesLore;

	public void setGuiRewardsContentsChallengesSlot(int guiRewardsContentsChallengesSlot) {
		this.guiRewardsContentsChallengesSlot = guiRewardsContentsChallengesSlot;
	}
	public int getGuiRewardsContentsChallengesSlot() {
		return this.guiRewardsContentsChallengesSlot;
	}

	public void setGuiRewardsContentsChallengesItem(String guiRewardsContentsChallengesItem) {
		this.guiRewardsContentsChallengesItem = guiRewardsContentsChallengesItem;
	}
	public String getGuiRewardsContentsChallengesItem() {
		return this.guiRewardsContentsChallengesItem;
	}

	public void setGuiRewardsContentsChallengesName(String guiRewardsContentsChallengesName) {
		this.guiRewardsContentsChallengesName = guiRewardsContentsChallengesName;
	}
	public String getGuiRewardsContentsChallengesName() {
		return this.guiRewardsContentsChallengesName;
	}

	public void setGuiRewardsContentsChallengesLore(List<String> guiRewardsContentsChallengesLore) {
		this.guiRewardsContentsChallengesLore = guiRewardsContentsChallengesLore;
	}
	public List<String> getGuiRewardsContentsChallengesLore() {
		return this.guiRewardsContentsChallengesLore;
	}

	// Gui > Rewards > Contents > Daily

	private int guiRewardsContentsDailySlot;
	private String guiRewardsContentsDailyItem;
	private String guiRewardsContentsDailyName;
	private List<String> guiRewardsContentsDailyLore;

	public void setGuiRewardsContentsDailySlot(int guiRewardsContentsDailySlot) {
		this.guiRewardsContentsDailySlot = guiRewardsContentsDailySlot;
	}
	public int getGuiRewardsContentsDailySlot() {
		return this.guiRewardsContentsDailySlot;
	}

	public void setGuiRewardsContentsDailyItem(String guiRewardsContentsDailyItem) {
		this.guiRewardsContentsDailyItem = guiRewardsContentsDailyItem;
	}
	public String getGuiRewardsContentsDailyItem() {
		return this.guiRewardsContentsDailyItem;
	}

	public void setGuiRewardsContentsDailyName(String guiRewardsContentsDailyName) {
		this.guiRewardsContentsDailyName = guiRewardsContentsDailyName;
	}
	public String getGuiRewardsContentsDailyName() {
		return this.guiRewardsContentsDailyName;
	}

	public void setGuiRewardsContentsDailyLore(List<String> guiRewardsContentsDailyLore) {
		this.guiRewardsContentsDailyLore = guiRewardsContentsDailyLore;
	}
	public List<String> getGuiRewardsContentsDailyLore() {
		return this.guiRewardsContentsDailyLore;
	}

	// Gui > Settings > Settings

	private String guiSettingsSettingsInventoryName;
	private int guiSettingsSettingsInventoryRows;
	private String guiSettingsSettingsToolbarToolbarItem;
	private String guiSettingsSettingsToolbarBackItem;
	private String guiSettingsSettingsToolbarPageItem;
	private String guiSettingsSettingsToolbarExitItem;

	public void setGuiSettingsSettingsInventoryName(String guiSettingsSettingsInventoryName) {
		this.guiSettingsSettingsInventoryName = guiSettingsSettingsInventoryName;
	}
	public String getGuiSettingsSettingsInventoryName() {
		return this.guiSettingsSettingsInventoryName;
	}

	public void setGuiSettingsSettingsInventoryRows(int guiSettingsSettingsInventoryRows) {
		this.guiSettingsSettingsInventoryRows = guiSettingsSettingsInventoryRows;
	}
	public int getGuiSettingsSettingsInventoryRows() {
		return this.guiSettingsSettingsInventoryRows;
	}

	public void setGuiSettingsSettingsToolbarToolbarItem(String guiSettingsSettingsToolbarToolbarItem) {
		this.guiSettingsSettingsToolbarToolbarItem = guiSettingsSettingsToolbarToolbarItem;
	}
	public String getGuiSettingsSettingsToolbarToolbarItem() {
		return this.guiSettingsSettingsToolbarToolbarItem;
	}

	public void setGuiSettingsSettingsToolbarBackItem(String guiSettingsSettingsToolbarBackItem) {
		this.guiSettingsSettingsToolbarBackItem = guiSettingsSettingsToolbarBackItem;
	}
	public String getGuiSettingsSettingsToolbarBackItem() {
		return this.guiSettingsSettingsToolbarBackItem;
	}

	public void setGuiSettingsSettingsToolbarPageItem(String guiSettingsSettingsToolbarPageItem) {
		this.guiSettingsSettingsToolbarPageItem = guiSettingsSettingsToolbarPageItem;
	}
	public String getGuiSettingsSettingsToolbarPageItem() {
		return this.guiSettingsSettingsToolbarPageItem;
	}

	public void setGuiSettingsSettingsToolbarExitItem(String guiSettingsSettingsToolbarExitItem) {
		this.guiSettingsSettingsToolbarExitItem = guiSettingsSettingsToolbarExitItem;
	}
	public String getGuiSettingsSettingsToolbarExitItem() {
		return this.guiSettingsSettingsToolbarExitItem;
	}

	// Gui > Settings > Contents > Connect-Disconnect

	private int guiSettingsContentsConnectDisconnectSlot;
	private String guiSettingsContentsConnectDisconnectItem;
	private String guiSettingsContentsConnectDisconnectName;
	private List<String> guiSettingsContentsConnectDisconnectLore;

	public void setGuiSettingsContentsConnectDisconnectSlot(int guiSettingsContentsConnectDisconnectSlot) {
		this.guiSettingsContentsConnectDisconnectSlot = guiSettingsContentsConnectDisconnectSlot;
	}
	public int getGuiSettingsContentsConnectDisconnectSlot() {
		return this.guiSettingsContentsConnectDisconnectSlot;
	}

	public void setGuiSettingsContentsConnectDisconnectItem(String guiSettingsContentsConnectDisconnectItem) {
		this.guiSettingsContentsConnectDisconnectItem = guiSettingsContentsConnectDisconnectItem;
	}
	public String getGuiSettingsContentsConnectDisconnectItem() {
		return this.guiSettingsContentsConnectDisconnectItem;
	}

	public void setGuiSettingsContentsConnectDisconnectName(String guiSettingsContentsConnectDisconnectName) {
		this.guiSettingsContentsConnectDisconnectName = guiSettingsContentsConnectDisconnectName;
	}
	public String getGuiSettingsContentsConnectDisconnectName() {
		return this.guiSettingsContentsConnectDisconnectName;
	}

	public void setGuiSettingsContentsConnectDisconnectLore(List<String> guiSettingsContentsConnectDisconnectLore) {
		this.guiSettingsContentsConnectDisconnectLore = guiSettingsContentsConnectDisconnectLore;
	}
	public List<String> getGuiSettingsContentsConnectDisconnectLore() {
		return this.guiSettingsContentsConnectDisconnectLore;
	}

	// Gui > Settings > Contents > Connect-Disconnect-Control

	private int guiSettingsContentsConnectDisconnectControlSlot;
	private String guiSettingsContentsConnectDisconnectControlItemOn;
	private String guiSettingsContentsConnectDisconnectControlNameOn;
	private List<String> guiSettingsContentsConnectDisconnectControlLoreOn;
	private String guiSettingsContentsConnectDisconnectControlItemOff;
	private String guiSettingsContentsConnectDisconnectControlNameOff;
	private List<String> guiSettingsContentsConnectDisconnectControlLoreOff;

	public void setGuiSettingsContentsConnectDisconnectControlSlot(int guiSettingsContentsConnectDisconnectControlSlot) {
		this.guiSettingsContentsConnectDisconnectControlSlot = guiSettingsContentsConnectDisconnectControlSlot;
	}
	public int getGuiSettingsContentsConnectDisconnectControlSlot() {
		return this.guiSettingsContentsConnectDisconnectControlSlot;
	}

	public void setGuiSettingsContentsConnectDisconnectControlItemOn(String guiSettingsContentsConnectDisconnectControlItemOn) {
		this.guiSettingsContentsConnectDisconnectControlItemOn = guiSettingsContentsConnectDisconnectControlItemOn;
	}
	public String getGuiSettingsContentsConnectDisconnectControlItemOn() {
		return this.guiSettingsContentsConnectDisconnectControlItemOn;
	}

	public void setGuiSettingsContentsConnectDisconnectControlNameOn(String guiSettingsContentsConnectDisconnectControlNameOn) {
		this.guiSettingsContentsConnectDisconnectControlNameOn = guiSettingsContentsConnectDisconnectControlNameOn;
	}
	public String getGuiSettingsContentsConnectDisconnectControlNameOn() {
		return this.guiSettingsContentsConnectDisconnectControlNameOn;
	}

	public void setGuiSettingsContentsConnectDisconnectControlLoreOn(List<String> guiSettingsContentsConnectDisconnectControlLoreOn) {
		this.guiSettingsContentsConnectDisconnectControlLoreOn = guiSettingsContentsConnectDisconnectControlLoreOn;
	}
	public List<String> getGuiSettingsContentsConnectDisconnectControlLoreOn() {
		return this.guiSettingsContentsConnectDisconnectControlLoreOn;
	}

	public void setGuiSettingsContentsConnectDisconnectControlItemOff(String guiSettingsContentsConnectDisconnectControlItemOff) {
		this.guiSettingsContentsConnectDisconnectControlItemOff = guiSettingsContentsConnectDisconnectControlItemOff;
	}
	public String getGuiSettingsContentsConnectDisconnectControlItemOff() {
		return this.guiSettingsContentsConnectDisconnectControlItemOff;
	}

	public void setGuiSettingsContentsConnectDisconnectControlNameOff(String guiSettingsContentsConnectDisconnectControlNameOff) {
		this.guiSettingsContentsConnectDisconnectControlNameOff = guiSettingsContentsConnectDisconnectControlNameOff;
	}
	public String getGuiSettingsContentsConnectDisconnectControlNameOff() {
		return this.guiSettingsContentsConnectDisconnectControlNameOff;
	}

	public void setGuiSettingsContentsConnectDisconnectControlLoreOff(List<String> guiSettingsContentsConnectDisconnectControlLoreOff) {
		this.guiSettingsContentsConnectDisconnectControlLoreOff = guiSettingsContentsConnectDisconnectControlLoreOff;
	}
	public List<String> getGuiSettingsContentsConnectDisconnectControlLoreOff() {
		return this.guiSettingsContentsConnectDisconnectControlLoreOff;
	}

	// Gui > Settings > Contents > Server-Change

	private int guiSettingsContentsServerChangeSlot;
	private String guiSettingsContentsServerChangeItem;
	private String guiSettingsContentsServerChangeName;
	private List<String> guiSettingsContentsServerChangeLore;

	public void setGuiSettingsContentsServerChangeSlot(int guiSettingsContentsServerChangeSlot) {
		this.guiSettingsContentsServerChangeSlot = guiSettingsContentsServerChangeSlot;
	}
	public int getGuiSettingsContentsServerChangeSlot() {
		return this.guiSettingsContentsServerChangeSlot;
	}

	public void setGuiSettingsContentsServerChangeItem(String guiSettingsContentsServerChangeItem) {
		this.guiSettingsContentsServerChangeItem = guiSettingsContentsServerChangeItem;
	}
	public String getGuiSettingsContentsServerChangeItem() {
		return this.guiSettingsContentsServerChangeItem;
	}

	public void setGuiSettingsContentsServerChangeName(String guiSettingsContentsServerChangeName) {
		this.guiSettingsContentsServerChangeName = guiSettingsContentsServerChangeName;
	}
	public String getGuiSettingsContentsServerChangeName() {
		return this.guiSettingsContentsServerChangeName;
	}

	public void setGuiSettingsContentsServerChangeLore(List<String> guiSettingsContentsServerChangeLore) {
		this.guiSettingsContentsServerChangeLore = guiSettingsContentsServerChangeLore;
	}
	public List<String> getGuiSettingsContentsServerChangeLore() {
		return this.guiSettingsContentsServerChangeLore;
	}

	// Gui > Settings > Contents > Server-Change-Control

	private int guiSettingsContentsServerChangeControlSlot;
	private String guiSettingsContentsServerChangeControlItemOn;
	private String guiSettingsContentsServerChangeControlNameOn;
	private List<String> guiSettingsContentsServerChangeControlLoreOn;
	private String guiSettingsContentsServerChangeControlItemOff;
	private String guiSettingsContentsServerChangeControlNameOff;
	private List<String> guiSettingsContentsServerChangeControlLoreOff;

	public void setGuiSettingsContentsServerChangeControlSlot(int guiSettingsContentsServerChangeControlSlot) {
		this.guiSettingsContentsServerChangeControlSlot = guiSettingsContentsServerChangeControlSlot;
	}
	public int getGuiSettingsContentsServerChangeControlSlot() {
		return this.guiSettingsContentsServerChangeControlSlot;
	}

	public void setGuiSettingsContentsServerChangeControlItemOn(String guiSettingsContentsServerChangeControlItemOn) {
		this.guiSettingsContentsServerChangeControlItemOn = guiSettingsContentsServerChangeControlItemOn;
	}
	public String getGuiSettingsContentsServerChangeControlItemOn() {
		return this.guiSettingsContentsServerChangeControlItemOn;
	}

	public void setGuiSettingsContentsServerChangeControlNameOn(String guiSettingsContentsServerChangeControlNameOn) {
		this.guiSettingsContentsServerChangeControlNameOn = guiSettingsContentsServerChangeControlNameOn;
	}
	public String getGuiSettingsContentsServerChangeControlNameOn() {
		return this.guiSettingsContentsServerChangeControlNameOn;
	}

	public void setGuiSettingsContentsServerChangeControlLoreOn(List<String> guiSettingsContentsServerChangeControlLoreOn) {
		this.guiSettingsContentsServerChangeControlLoreOn = guiSettingsContentsServerChangeControlLoreOn;
	}
	public List<String> getGuiSettingsContentsServerChangeControlLoreOn() {
		return this.guiSettingsContentsServerChangeControlLoreOn;
	}

	public void setGuiSettingsContentsServerChangeControlItemOff(String guiSettingsContentsServerChangeControlItemOff) {
		this.guiSettingsContentsServerChangeControlItemOff = guiSettingsContentsServerChangeControlItemOff;
	}
	public String getGuiSettingsContentsServerChangeControlItemOff() {
		return this.guiSettingsContentsServerChangeControlItemOff;
	}

	public void setGuiSettingsContentsServerChangeControlNameOff(String guiSettingsContentsServerChangeControlNameOff) {
		this.guiSettingsContentsServerChangeControlNameOff = guiSettingsContentsServerChangeControlNameOff;
	}
	public String getGuiSettingsContentsServerChangeControlNameOff() {
		return this.guiSettingsContentsServerChangeControlNameOff;
	}

	public void setGuiSettingsContentsServerChangeControlLoreOff(List<String> guiSettingsContentsServerChangeControlLoreOff) {
		this.guiSettingsContentsServerChangeControlLoreOff = guiSettingsContentsServerChangeControlLoreOff;
	}
	public List<String> getGuiSettingsContentsServerChangeControlLoreOff() {
		return this.guiSettingsContentsServerChangeControlLoreOff;
	}

	// Gui > Settings > Contents > Player-Chat

	private int guiSettingsContentsPlayerChatSlot;
	private String guiSettingsContentsPlayerChatItem;
	private String guiSettingsContentsPlayerChatName;
	private List<String> guiSettingsContentsPlayerChatLore;

	public void setGuiSettingsContentsPlayerChatSlot(int guiSettingsContentsPlayerChatSlot) {
		this.guiSettingsContentsPlayerChatSlot = guiSettingsContentsPlayerChatSlot;
	}
	public int getGuiSettingsContentsPlayerChatSlot() {
		return this.guiSettingsContentsPlayerChatSlot;
	}

	public void setGuiSettingsContentsPlayerChatItem(String guiSettingsContentsPlayerChatItem) {
		this.guiSettingsContentsPlayerChatItem = guiSettingsContentsPlayerChatItem;
	}
	public String getGuiSettingsContentsPlayerChatItem() {
		return this.guiSettingsContentsPlayerChatItem;
	}

	public void setGuiSettingsContentsPlayerChatName(String guiSettingsContentsPlayerChatName) {
		this.guiSettingsContentsPlayerChatName = guiSettingsContentsPlayerChatName;
	}
	public String getGuiSettingsContentsPlayerChatName() {
		return this.guiSettingsContentsPlayerChatName;
	}

	public void setGuiSettingsContentsPlayerChatLore(List<String> guiSettingsContentsPlayerChatLore) {
		this.guiSettingsContentsPlayerChatLore = guiSettingsContentsPlayerChatLore;
	}
	public List<String> getGuiSettingsContentsPlayerChatLore() {
		return this.guiSettingsContentsPlayerChatLore;
	}

	// Gui > Settings > Contents > Player-Chat-Control

	private int guiSettingsContentsPlayerChatControlSlot;
	private String guiSettingsContentsPlayerChatControlItemOn;
	private String guiSettingsContentsPlayerChatControlNameOn;
	private List<String> guiSettingsContentsPlayerChatControlLoreOn;
	private String guiSettingsContentsPlayerChatControlItemOff;
	private String guiSettingsContentsPlayerChatControlNameOff;
	private List<String> guiSettingsContentsPlayerChatControlLoreOff;

	public void setGuiSettingsContentsPlayerChatControlSlot(int guiSettingsContentsPlayerChatControlSlot) {
		this.guiSettingsContentsPlayerChatControlSlot = guiSettingsContentsPlayerChatControlSlot;
	}
	public int getGuiSettingsContentsPlayerChatControlSlot() {
		return this.guiSettingsContentsPlayerChatControlSlot;
	}

	public void setGuiSettingsContentsPlayerChatControlItemOn(String guiSettingsContentsPlayerChatControlItemOn) {
		this.guiSettingsContentsPlayerChatControlItemOn = guiSettingsContentsPlayerChatControlItemOn;
	}
	public String getGuiSettingsContentsPlayerChatControlItemOn() {
		return this.guiSettingsContentsPlayerChatControlItemOn;
	}

	public void setGuiSettingsContentsPlayerChatControlNameOn(String guiSettingsContentsPlayerChatControlNameOn) {
		this.guiSettingsContentsPlayerChatControlNameOn = guiSettingsContentsPlayerChatControlNameOn;
	}
	public String getGuiSettingsContentsPlayerChatControlNameOn() {
		return this.guiSettingsContentsPlayerChatControlNameOn;
	}

	public void setGuiSettingsContentsPlayerChatControlLoreOn(List<String> guiSettingsContentsPlayerChatControlLoreOn) {
		this.guiSettingsContentsPlayerChatControlLoreOn = guiSettingsContentsPlayerChatControlLoreOn;
	}
	public List<String> getGuiSettingsContentsPlayerChatControlLoreOn() {
		return this.guiSettingsContentsPlayerChatControlLoreOn;
	}

	public void setGuiSettingsContentsPlayerChatControlItemOff(String guiSettingsContentsPlayerChatControlItemOff) {
		this.guiSettingsContentsPlayerChatControlItemOff = guiSettingsContentsPlayerChatControlItemOff;
	}
	public String getGuiSettingsContentsPlayerChatControlItemOff() {
		return this.guiSettingsContentsPlayerChatControlItemOff;
	}

	public void setGuiSettingsContentsPlayerChatControlNameOff(String guiSettingsContentsPlayerChatControlNameOff) {
		this.guiSettingsContentsPlayerChatControlNameOff = guiSettingsContentsPlayerChatControlNameOff;
	}
	public String getGuiSettingsContentsPlayerChatControlNameOff() {
		return this.guiSettingsContentsPlayerChatControlNameOff;
	}

	public void setGuiSettingsContentsPlayerChatControlLoreOff(List<String> guiSettingsContentsPlayerChatControlLoreOff) {
		this.guiSettingsContentsPlayerChatControlLoreOff = guiSettingsContentsPlayerChatControlLoreOff;
	}
	public List<String> getGuiSettingsContentsPlayerChatControlLoreOff() {
		return this.guiSettingsContentsPlayerChatControlLoreOff;
	}

	// Gui > Settings > Contents > Server-Announcement

	private int guiSettingsContentsServerAnnouncementSlot;
	private String guiSettingsContentsServerAnnouncementItem;
	private String guiSettingsContentsServerAnnouncementName;
	private List<String> guiSettingsContentsServerAnnouncementLore;

	public void setGuiSettingsContentsServerAnnouncementSlot(int guiSettingsContentsServerAnnouncementSlot) {
		this.guiSettingsContentsServerAnnouncementSlot = guiSettingsContentsServerAnnouncementSlot;
	}
	public int getGuiSettingsContentsServerAnnouncementSlot() {
		return this.guiSettingsContentsServerAnnouncementSlot;
	}

	public void setGuiSettingsContentsServerAnnouncementItem(String guiSettingsContentsServerAnnouncementItem) {
		this.guiSettingsContentsServerAnnouncementItem = guiSettingsContentsServerAnnouncementItem;
	}
	public String getGuiSettingsContentsServerAnnouncementItem() {
		return this.guiSettingsContentsServerAnnouncementItem;
	}

	public void setGuiSettingsContentsServerAnnouncementName(String guiSettingsContentsServerAnnouncementName) {
		this.guiSettingsContentsServerAnnouncementName = guiSettingsContentsServerAnnouncementName;
	}
	public String getGuiSettingsContentsServerAnnouncementName() {
		return this.guiSettingsContentsServerAnnouncementName;
	}

	public void setGuiSettingsContentsServerAnnouncementLore(List<String> guiSettingsContentsServerAnnouncementLore) {
		this.guiSettingsContentsServerAnnouncementLore = guiSettingsContentsServerAnnouncementLore;
	}
	public List<String> getGuiSettingsContentsServerAnnouncementLore() {
		return this.guiSettingsContentsServerAnnouncementLore;
	}

	// Gui > Settings > Contents > Server-Announcement-Control

	private int guiSettingsContentsServerAnnouncementControlSlot;
	private String guiSettingsContentsServerAnnouncementControlItemOn;
	private String guiSettingsContentsServerAnnouncementControlNameOn;
	private List<String> guiSettingsContentsServerAnnouncementControlLoreOn;
	private String guiSettingsContentsServerAnnouncementControlItemOff;
	private String guiSettingsContentsServerAnnouncementControlNameOff;
	private List<String> guiSettingsContentsServerAnnouncementControlLoreOff;

	public void setGuiSettingsContentsServerAnnouncementControlSlot(int guiSettingsContentsServerAnnouncementControlSlot) {
		this.guiSettingsContentsServerAnnouncementControlSlot = guiSettingsContentsServerAnnouncementControlSlot;
	}
	public int getGuiSettingsContentsServerAnnouncementControlSlot() {
		return this.guiSettingsContentsServerAnnouncementControlSlot;
	}

	public void setGuiSettingsContentsServerAnnouncementControlItemOn(String guiSettingsContentsServerAnnouncementControlItemOn) {
		this.guiSettingsContentsServerAnnouncementControlItemOn = guiSettingsContentsServerAnnouncementControlItemOn;
	}
	public String getGuiSettingsContentsServerAnnouncementControlItemOn() {
		return this.guiSettingsContentsServerAnnouncementControlItemOn;
	}

	public void setGuiSettingsContentsServerAnnouncementControlNameOn(String guiSettingsContentsServerAnnouncementControlNameOn) {
		this.guiSettingsContentsServerAnnouncementControlNameOn = guiSettingsContentsServerAnnouncementControlNameOn;
	}
	public String getGuiSettingsContentsServerAnnouncementControlNameOn() {
		return this.guiSettingsContentsServerAnnouncementControlNameOn;
	}

	public void setGuiSettingsContentsServerAnnouncementControlLoreOn(List<String> guiSettingsContentsServerAnnouncementControlLoreOn) {
		this.guiSettingsContentsServerAnnouncementControlLoreOn = guiSettingsContentsServerAnnouncementControlLoreOn;
	}
	public List<String> getGuiSettingsContentsServerAnnouncementControlLoreOn() {
		return this.guiSettingsContentsServerAnnouncementControlLoreOn;
	}

	public void setGuiSettingsContentsServerAnnouncementControlItemOff(String guiSettingsContentsServerAnnouncementControlItemOff) {
		this.guiSettingsContentsServerAnnouncementControlItemOff = guiSettingsContentsServerAnnouncementControlItemOff;
	}
	public String getGuiSettingsContentsServerAnnouncementControlItemOff() {
		return this.guiSettingsContentsServerAnnouncementControlItemOff;
	}

	public void setGuiSettingsContentsServerAnnouncementControlNameOff(String guiSettingsContentsServerAnnouncementControlNameOff) {
		this.guiSettingsContentsServerAnnouncementControlNameOff = guiSettingsContentsServerAnnouncementControlNameOff;
	}
	public String getGuiSettingsContentsServerAnnouncementControlNameOff() {
		return this.guiSettingsContentsServerAnnouncementControlNameOff;
	}

	public void setGuiSettingsContentsServerAnnouncementControlLoreOff(List<String> guiSettingsContentsServerAnnouncementControlLoreOff) {
		this.guiSettingsContentsServerAnnouncementControlLoreOff = guiSettingsContentsServerAnnouncementControlLoreOff;
	}
	public List<String> getGuiSettingsContentsServerAnnouncementControlLoreOff() {
		return this.guiSettingsContentsServerAnnouncementControlLoreOff;
	}

	// Gui > Settings > Contents > Friend-Request

	private int guiSettingsContentsFriendRequestSlot;
	private String guiSettingsContentsFriendRequestItem;
	private String guiSettingsContentsFriendRequestName;
	private List<String> guiSettingsContentsFriendRequestLore;

	public void setGuiSettingsContentsFriendRequestSlot(int guiSettingsContentsFriendRequestSlot) {
		this.guiSettingsContentsFriendRequestSlot = guiSettingsContentsFriendRequestSlot;
	}
	public int getGuiSettingsContentsFriendRequestSlot() {
		return this.guiSettingsContentsFriendRequestSlot;
	}

	public void setGuiSettingsContentsFriendRequestItem(String guiSettingsContentsFriendRequestItem) {
		this.guiSettingsContentsFriendRequestItem = guiSettingsContentsFriendRequestItem;
	}
	public String getGuiSettingsContentsFriendRequestItem() {
		return this.guiSettingsContentsFriendRequestItem;
	}

	public void setGuiSettingsContentsFriendRequestName(String guiSettingsContentsFriendRequestName) {
		this.guiSettingsContentsFriendRequestName = guiSettingsContentsFriendRequestName;
	}
	public String getGuiSettingsContentsFriendRequestName() {
		return this.guiSettingsContentsFriendRequestName;
	}

	public void setGuiSettingsContentsFriendRequestLore(List<String> guiSettingsContentsFriendRequestLore) {
		this.guiSettingsContentsFriendRequestLore = guiSettingsContentsFriendRequestLore;
	}
	public List<String> getGuiSettingsContentsFriendRequestLore() {
		return this.guiSettingsContentsFriendRequestLore;
	}

	// Gui > Settings > Contents > Friend-Request-Control

	private int guiSettingsContentsFriendRequestControlSlot;
	private String guiSettingsContentsFriendRequestControlItemOn;
	private String guiSettingsContentsFriendRequestControlNameOn;
	private List<String> guiSettingsContentsFriendRequestControlLoreOn;
	private String guiSettingsContentsFriendRequestControlItemOff;
	private String guiSettingsContentsFriendRequestControlNameOff;
	private List<String> guiSettingsContentsFriendRequestControlLoreOff;

	public void setGuiSettingsContentsFriendRequestControlSlot(int guiSettingsContentsFriendRequestControlSlot) {
		this.guiSettingsContentsFriendRequestControlSlot = guiSettingsContentsFriendRequestControlSlot;
	}
	public int getGuiSettingsContentsFriendRequestControlSlot() {
		return this.guiSettingsContentsFriendRequestControlSlot;
	}

	public void setGuiSettingsContentsFriendRequestControlItemOn(String guiSettingsContentsFriendRequestControlItemOn) {
		this.guiSettingsContentsFriendRequestControlItemOn = guiSettingsContentsFriendRequestControlItemOn;
	}
	public String getGuiSettingsContentsFriendRequestControlItemOn() {
		return this.guiSettingsContentsFriendRequestControlItemOn;
	}

	public void setGuiSettingsContentsFriendRequestControlNameOn(String guiSettingsContentsFriendRequestControlNameOn) {
		this.guiSettingsContentsFriendRequestControlNameOn = guiSettingsContentsFriendRequestControlNameOn;
	}
	public String getGuiSettingsContentsFriendRequestControlNameOn() {
		return this.guiSettingsContentsFriendRequestControlNameOn;
	}

	public void setGuiSettingsContentsFriendRequestControlLoreOn(List<String> guiSettingsContentsFriendRequestControlLoreOn) {
		this.guiSettingsContentsFriendRequestControlLoreOn = guiSettingsContentsFriendRequestControlLoreOn;
	}
	public List<String> getGuiSettingsContentsFriendRequestControlLoreOn() {
		return this.guiSettingsContentsFriendRequestControlLoreOn;
	}

	public void setGuiSettingsContentsFriendRequestControlItemOff(String guiSettingsContentsFriendRequestControlItemOff) {
		this.guiSettingsContentsFriendRequestControlItemOff = guiSettingsContentsFriendRequestControlItemOff;
	}
	public String getGuiSettingsContentsFriendRequestControlItemOff() {
		return this.guiSettingsContentsFriendRequestControlItemOff;
	}

	public void setGuiSettingsContentsFriendRequestControlNameOff(String guiSettingsContentsFriendRequestControlNameOff) {
		this.guiSettingsContentsFriendRequestControlNameOff = guiSettingsContentsFriendRequestControlNameOff;
	}
	public String getGuiSettingsContentsFriendRequestControlNameOff() {
		return this.guiSettingsContentsFriendRequestControlNameOff;
	}

	public void setGuiSettingsContentsFriendRequestControlLoreOff(List<String> guiSettingsContentsFriendRequestControlLoreOff) {
		this.guiSettingsContentsFriendRequestControlLoreOff = guiSettingsContentsFriendRequestControlLoreOff;
	}
	public List<String> getGuiSettingsContentsFriendRequestControlLoreOff() {
		return this.guiSettingsContentsFriendRequestControlLoreOff;
	}

	// Gui > Settings > Contents > Direct-Message

	private int guiSettingsContentsDirectMessageSlot;
	private String guiSettingsContentsDirectMessageItem;
	private String guiSettingsContentsDirectMessageName;
	private List<String> guiSettingsContentsDirectMessageLore;

	public void setGuiSettingsContentsDirectMessageSlot(int guiSettingsContentsDirectMessageSlot) {
		this.guiSettingsContentsDirectMessageSlot = guiSettingsContentsDirectMessageSlot;
	}
	public int getGuiSettingsContentsDirectMessageSlot() {
		return this.guiSettingsContentsDirectMessageSlot;
	}

	public void setGuiSettingsContentsDirectMessageItem(String guiSettingsContentsDirectMessageItem) {
		this.guiSettingsContentsDirectMessageItem = guiSettingsContentsDirectMessageItem;
	}
	public String getGuiSettingsContentsDirectMessageItem() {
		return this.guiSettingsContentsDirectMessageItem;
	}

	public void setGuiSettingsContentsDirectMessageName(String guiSettingsContentsDirectMessageName) {
		this.guiSettingsContentsDirectMessageName = guiSettingsContentsDirectMessageName;
	}
	public String getGuiSettingsContentsDirectMessageName() {
		return this.guiSettingsContentsDirectMessageName;
	}

	public void setGuiSettingsContentsDirectMessageLore(List<String> guiSettingsContentsDirectMessageLore) {
		this.guiSettingsContentsDirectMessageLore = guiSettingsContentsDirectMessageLore;
	}
	public List<String> getGuiSettingsContentsDirectMessageLore() {
		return this.guiSettingsContentsDirectMessageLore;
	}

	// Gui > Settings > Contents > Direct-Message-Control

	private int guiSettingsContentsDirectMessageControlSlot;
	private String guiSettingsContentsDirectMessageControlItemOn;
	private String guiSettingsContentsDirectMessageControlNameOn;
	private List<String> guiSettingsContentsDirectMessageControlLoreOn;
	private String guiSettingsContentsDirectMessageControlItemOff;
	private String guiSettingsContentsDirectMessageControlNameOff;
	private List<String> guiSettingsContentsDirectMessageControlLoreOff;
	private String guiSettingsContentsDirectMessageControlItemFriend;
	private String guiSettingsContentsDirectMessageControlNameFriend;
	private List<String> guiSettingsContentsDirectMessageControlLoreFriend;

	public void setGuiSettingsContentsDirectMessageControlSlot(int guiSettingsContentsDirectMessageControlSlot) {
		this.guiSettingsContentsDirectMessageControlSlot = guiSettingsContentsDirectMessageControlSlot;
	}
	public int getGuiSettingsContentsDirectMessageControlSlot() {
		return this.guiSettingsContentsDirectMessageControlSlot;
	}

	public void setGuiSettingsContentsDirectMessageControlItemOn(String guiSettingsContentsDirectMessageControlItemOn) {
		this.guiSettingsContentsDirectMessageControlItemOn = guiSettingsContentsDirectMessageControlItemOn;
	}
	public String getGuiSettingsContentsDirectMessageControlItemOn() {
		return this.guiSettingsContentsDirectMessageControlItemOn;
	}

	public void setGuiSettingsContentsDirectMessageControlNameOn(String guiSettingsContentsDirectMessageControlNameOn) {
		this.guiSettingsContentsDirectMessageControlNameOn = guiSettingsContentsDirectMessageControlNameOn;
	}
	public String getGuiSettingsContentsDirectMessageControlNameOn() {
		return this.guiSettingsContentsDirectMessageControlNameOn;
	}

	public void setGuiSettingsContentsDirectMessageControlLoreOn(List<String> guiSettingsContentsDirectMessageControlLoreOn) {
		this.guiSettingsContentsDirectMessageControlLoreOn = guiSettingsContentsDirectMessageControlLoreOn;
	}
	public List<String> getGuiSettingsContentsDirectMessageControlLoreOn() {
		return this.guiSettingsContentsDirectMessageControlLoreOn;
	}

	public void setGuiSettingsContentsDirectMessageControlItemOff(String guiSettingsContentsDirectMessageControlItemOff) {
		this.guiSettingsContentsDirectMessageControlItemOff = guiSettingsContentsDirectMessageControlItemOff;
	}
	public String getGuiSettingsContentsDirectMessageControlItemOff() {
		return this.guiSettingsContentsDirectMessageControlItemOff;
	}

	public void setGuiSettingsContentsDirectMessageControlNameOff(String guiSettingsContentsDirectMessageControlNameOff) {
		this.guiSettingsContentsDirectMessageControlNameOff = guiSettingsContentsDirectMessageControlNameOff;
	}
	public String getGuiSettingsContentsDirectMessageControlNameOff() {
		return this.guiSettingsContentsDirectMessageControlNameOff;
	}

	public void setGuiSettingsContentsDirectMessageControlLoreOff(List<String> guiSettingsContentsDirectMessageControlLoreOff) {
		this.guiSettingsContentsDirectMessageControlLoreOff = guiSettingsContentsDirectMessageControlLoreOff;
	}
	public List<String> getGuiSettingsContentsDirectMessageControlLoreOff() {
		return this.guiSettingsContentsDirectMessageControlLoreOff;
	}

	public void setGuiSettingsContentsDirectMessageControlItemFriend(String guiSettingsContentsDirectMessageControlItemFriend) {
		this.guiSettingsContentsDirectMessageControlItemFriend = guiSettingsContentsDirectMessageControlItemFriend;
	}
	public String getGuiSettingsContentsDirectMessageControlItemFriend() {
		return this.guiSettingsContentsDirectMessageControlItemFriend;
	}

	public void setGuiSettingsContentsDirectMessageControlNameFriend(String guiSettingsContentsDirectMessageControlNameFriend) {
		this.guiSettingsContentsDirectMessageControlNameFriend = guiSettingsContentsDirectMessageControlNameFriend;
	}
	public String getGuiSettingsContentsDirectMessageControlNameFriend() {
		return this.guiSettingsContentsDirectMessageControlNameFriend;
	}

	public void setGuiSettingsContentsDirectMessageControlLoreFriend(List<String> guiSettingsContentsDirectMessageControlLoreFriend) {
		this.guiSettingsContentsDirectMessageControlLoreFriend = guiSettingsContentsDirectMessageControlLoreFriend;
	}
	public List<String> getGuiSettingsContentsDirectMessageControlLoreFriend() {
		return this.guiSettingsContentsDirectMessageControlLoreFriend;
	}

	// Gui > Settings > Contents > Teleport-Request

	private int guiSettingsContentsTeleportRequestSlot;
	private String guiSettingsContentsTeleportRequestItem;
	private String guiSettingsContentsTeleportRequestName;
	private List<String> guiSettingsContentsTeleportRequestLore;

	public void setGuiSettingsContentsTeleportRequestSlot(int guiSettingsContentsTeleportRequestSlot) {
		this.guiSettingsContentsTeleportRequestSlot = guiSettingsContentsTeleportRequestSlot;
	}
	public int getGuiSettingsContentsTeleportRequestSlot() {
		return this.guiSettingsContentsTeleportRequestSlot;
	}

	public void setGuiSettingsContentsTeleportRequestItem(String guiSettingsContentsTeleportRequestItem) {
		this.guiSettingsContentsTeleportRequestItem = guiSettingsContentsTeleportRequestItem;
	}
	public String getGuiSettingsContentsTeleportRequestItem() {
		return this.guiSettingsContentsTeleportRequestItem;
	}

	public void setGuiSettingsContentsTeleportRequestName(String guiSettingsContentsTeleportRequestName) {
		this.guiSettingsContentsTeleportRequestName = guiSettingsContentsTeleportRequestName;
	}
	public String getGuiSettingsContentsTeleportRequestName() {
		return this.guiSettingsContentsTeleportRequestName;
	}

	public void setGuiSettingsContentsTeleportRequestLore(List<String> guiSettingsContentsTeleportRequestLore) {
		this.guiSettingsContentsTeleportRequestLore = guiSettingsContentsTeleportRequestLore;
	}
	public List<String> getGuiSettingsContentsTeleportRequestLore() {
		return this.guiSettingsContentsTeleportRequestLore;
	}

	// Gui > Settings > Contents > Teleport-Request-Control

	private int guiSettingsContentsTeleportRequestControlSlot;
	private String guiSettingsContentsTeleportRequestControlItemOn;
	private String guiSettingsContentsTeleportRequestControlNameOn;
	private List<String> guiSettingsContentsTeleportRequestControlLoreOn;
	private String guiSettingsContentsTeleportRequestControlItemOff;
	private String guiSettingsContentsTeleportRequestControlNameOff;
	private List<String> guiSettingsContentsTeleportRequestControlLoreOff;
	private String guiSettingsContentsTeleportRequestControlItemFriend;
	private String guiSettingsContentsTeleportRequestControlNameFriend;
	private List<String> guiSettingsContentsTeleportRequestControlLoreFriend;

	public void setGuiSettingsContentsTeleportRequestControlSlot(int guiSettingsContentsTeleportRequestControlSlot) {
		this.guiSettingsContentsTeleportRequestControlSlot = guiSettingsContentsTeleportRequestControlSlot;
	}
	public int getGuiSettingsContentsTeleportRequestControlSlot() {
		return this.guiSettingsContentsTeleportRequestControlSlot;
	}

	public void setGuiSettingsContentsTeleportRequestControlItemOn(String guiSettingsContentsTeleportRequestControlItemOn) {
		this.guiSettingsContentsTeleportRequestControlItemOn = guiSettingsContentsTeleportRequestControlItemOn;
	}
	public String getGuiSettingsContentsTeleportRequestControlItemOn() {
		return this.guiSettingsContentsTeleportRequestControlItemOn;
	}

	public void setGuiSettingsContentsTeleportRequestControlNameOn(String guiSettingsContentsTeleportRequestControlNameOn) {
		this.guiSettingsContentsTeleportRequestControlNameOn = guiSettingsContentsTeleportRequestControlNameOn;
	}
	public String getGuiSettingsContentsTeleportRequestControlNameOn() {
		return this.guiSettingsContentsTeleportRequestControlNameOn;
	}

	public void setGuiSettingsContentsTeleportRequestControlLoreOn(List<String> guiSettingsContentsTeleportRequestControlLoreOn) {
		this.guiSettingsContentsTeleportRequestControlLoreOn = guiSettingsContentsTeleportRequestControlLoreOn;
	}
	public List<String> getGuiSettingsContentsTeleportRequestControlLoreOn() {
		return this.guiSettingsContentsTeleportRequestControlLoreOn;
	}

	public void setGuiSettingsContentsTeleportRequestControlItemOff(String guiSettingsContentsTeleportRequestControlItemOff) {
		this.guiSettingsContentsTeleportRequestControlItemOff = guiSettingsContentsTeleportRequestControlItemOff;
	}
	public String getGuiSettingsContentsTeleportRequestControlItemOff() {
		return this.guiSettingsContentsTeleportRequestControlItemOff;
	}

	public void setGuiSettingsContentsTeleportRequestControlNameOff(String guiSettingsContentsTeleportRequestControlNameOff) {
		this.guiSettingsContentsTeleportRequestControlNameOff = guiSettingsContentsTeleportRequestControlNameOff;
	}
	public String getGuiSettingsContentsTeleportRequestControlNameOff() {
		return this.guiSettingsContentsTeleportRequestControlNameOff;
	}

	public void setGuiSettingsContentsTeleportRequestControlLoreOff(List<String> guiSettingsContentsTeleportRequestControlLoreOff) {
		this.guiSettingsContentsTeleportRequestControlLoreOff = guiSettingsContentsTeleportRequestControlLoreOff;
	}
	public List<String> getGuiSettingsContentsTeleportRequestControlLoreOff() {
		return this.guiSettingsContentsTeleportRequestControlLoreOff;
	}

	public void setGuiSettingsContentsTeleportRequestControlItemFriend(String guiSettingsContentsTeleportRequestControlItemFriend) {
		this.guiSettingsContentsTeleportRequestControlItemFriend = guiSettingsContentsTeleportRequestControlItemFriend;
	}
	public String getGuiSettingsContentsTeleportRequestControlItemFriend() {
		return this.guiSettingsContentsTeleportRequestControlItemFriend;
	}

	public void setGuiSettingsContentsTeleportRequestControlNameFriend(String guiSettingsContentsTeleportRequestControlNameFriend) {
		this.guiSettingsContentsTeleportRequestControlNameFriend = guiSettingsContentsTeleportRequestControlNameFriend;
	}
	public String getGuiSettingsContentsTeleportRequestControlNameFriend() {
		return this.guiSettingsContentsTeleportRequestControlNameFriend;
	}

	public void setGuiSettingsContentsTeleportRequestControlLoreFriend(List<String> guiSettingsContentsTeleportRequestControlLoreFriend) {
		this.guiSettingsContentsTeleportRequestControlLoreFriend = guiSettingsContentsTeleportRequestControlLoreFriend;
	}
	public List<String> getGuiSettingsContentsTeleportRequestControlLoreFriend() {
		return this.guiSettingsContentsTeleportRequestControlLoreFriend;
	}

	// Gui > Settings > Contents > Spectate-Request

	private int guiSettingsContentsSpectateRequestSlot;
	private String guiSettingsContentsSpectateRequestItem;
	private String guiSettingsContentsSpectateRequestName;
	private List<String> guiSettingsContentsSpectateRequestLore;

	public void setGuiSettingsContentsSpectateRequestSlot(int guiSettingsContentsSpectateRequestSlot) {
		this.guiSettingsContentsSpectateRequestSlot = guiSettingsContentsSpectateRequestSlot;
	}
	public int getGuiSettingsContentsSpectateRequestSlot() {
		return this.guiSettingsContentsSpectateRequestSlot;
	}

	public void setGuiSettingsContentsSpectateRequestItem(String guiSettingsContentsSpectateRequestItem) {
		this.guiSettingsContentsSpectateRequestItem = guiSettingsContentsSpectateRequestItem;
	}
	public String getGuiSettingsContentsSpectateRequestItem() {
		return this.guiSettingsContentsSpectateRequestItem;
	}

	public void setGuiSettingsContentsSpectateRequestName(String guiSettingsContentsSpectateRequestName) {
		this.guiSettingsContentsSpectateRequestName = guiSettingsContentsSpectateRequestName;
	}
	public String getGuiSettingsContentsSpectateRequestName() {
		return this.guiSettingsContentsSpectateRequestName;
	}

	public void setGuiSettingsContentsSpectateRequestLore(List<String> guiSettingsContentsSpectateRequestLore) {
		this.guiSettingsContentsSpectateRequestLore = guiSettingsContentsSpectateRequestLore;
	}
	public List<String> getGuiSettingsContentsSpectateRequestLore() {
		return this.guiSettingsContentsSpectateRequestLore;
	}

	// Gui > Settings > Contents > Spectate-Request-Control

	private int guiSettingsContentsSpectateRequestControlSlot;
	private String guiSettingsContentsSpectateRequestControlItemOn;
	private String guiSettingsContentsSpectateRequestControlNameOn;
	private List<String> guiSettingsContentsSpectateRequestControlLoreOn;
	private String guiSettingsContentsSpectateRequestControlItemOff;
	private String guiSettingsContentsSpectateRequestControlNameOff;
	private List<String> guiSettingsContentsSpectateRequestControlLoreOff;
	private String guiSettingsContentsSpectateRequestControlItemFriend;
	private String guiSettingsContentsSpectateRequestControlNameFriend;
	private List<String> guiSettingsContentsSpectateRequestControlLoreFriend;

	public void setGuiSettingsContentsSpectateRequestControlSlot(int guiSettingsContentsSpectateRequestControlSlot) {
		this.guiSettingsContentsSpectateRequestControlSlot = guiSettingsContentsSpectateRequestControlSlot;
	}
	public int getGuiSettingsContentsSpectateRequestControlSlot() {
		return this.guiSettingsContentsSpectateRequestControlSlot;
	}

	public void setGuiSettingsContentsSpectateRequestControlItemOn(String guiSettingsContentsSpectateRequestControlItemOn) {
		this.guiSettingsContentsSpectateRequestControlItemOn = guiSettingsContentsSpectateRequestControlItemOn;
	}
	public String getGuiSettingsContentsSpectateRequestControlItemOn() {
		return this.guiSettingsContentsSpectateRequestControlItemOn;
	}

	public void setGuiSettingsContentsSpectateRequestControlNameOn(String guiSettingsContentsSpectateRequestControlNameOn) {
		this.guiSettingsContentsSpectateRequestControlNameOn = guiSettingsContentsSpectateRequestControlNameOn;
	}
	public String getGuiSettingsContentsSpectateRequestControlNameOn() {
		return this.guiSettingsContentsSpectateRequestControlNameOn;
	}

	public void setGuiSettingsContentsSpectateRequestControlLoreOn(List<String> guiSettingsContentsSpectateRequestControlLoreOn) {
		this.guiSettingsContentsSpectateRequestControlLoreOn = guiSettingsContentsSpectateRequestControlLoreOn;
	}
	public List<String> getGuiSettingsContentsSpectateRequestControlLoreOn() {
		return this.guiSettingsContentsSpectateRequestControlLoreOn;
	}

	public void setGuiSettingsContentsSpectateRequestControlItemOff(String guiSettingsContentsSpectateRequestControlItemOff) {
		this.guiSettingsContentsSpectateRequestControlItemOff = guiSettingsContentsSpectateRequestControlItemOff;
	}
	public String getGuiSettingsContentsSpectateRequestControlItemOff() {
		return this.guiSettingsContentsSpectateRequestControlItemOff;
	}

	public void setGuiSettingsContentsSpectateRequestControlNameOff(String guiSettingsContentsSpectateRequestControlNameOff) {
		this.guiSettingsContentsSpectateRequestControlNameOff = guiSettingsContentsSpectateRequestControlNameOff;
	}
	public String getGuiSettingsContentsSpectateRequestControlNameOff() {
		return this.guiSettingsContentsSpectateRequestControlNameOff;
	}

	public void setGuiSettingsContentsSpectateRequestControlLoreOff(List<String> guiSettingsContentsSpectateRequestControlLoreOff) {
		this.guiSettingsContentsSpectateRequestControlLoreOff = guiSettingsContentsSpectateRequestControlLoreOff;
	}
	public List<String> getGuiSettingsContentsSpectateRequestControlLoreOff() {
		return this.guiSettingsContentsSpectateRequestControlLoreOff;
	}

	public void setGuiSettingsContentsSpectateRequestControlItemFriend(String guiSettingsContentsSpectateRequestControlItemFriend) {
		this.guiSettingsContentsSpectateRequestControlItemFriend = guiSettingsContentsSpectateRequestControlItemFriend;
	}
	public String getGuiSettingsContentsSpectateRequestControlItemFriend() {
		return this.guiSettingsContentsSpectateRequestControlItemFriend;
	}

	public void setGuiSettingsContentsSpectateRequestControlNameFriend(String guiSettingsContentsSpectateRequestControlNameFriend) {
		this.guiSettingsContentsSpectateRequestControlNameFriend = guiSettingsContentsSpectateRequestControlNameFriend;
	}
	public String getGuiSettingsContentsSpectateRequestControlNameFriend() {
		return this.guiSettingsContentsSpectateRequestControlNameFriend;
	}

	public void setGuiSettingsContentsSpectateRequestControlLoreFriend(List<String> guiSettingsContentsSpectateRequestControlLoreFriend) {
		this.guiSettingsContentsSpectateRequestControlLoreFriend = guiSettingsContentsSpectateRequestControlLoreFriend;
	}
	public List<String> getGuiSettingsContentsSpectateRequestControlLoreFriend() {
		return this.guiSettingsContentsSpectateRequestControlLoreFriend;
	}

	// Gui > Worlds > Settings

	private String guiWorldsSettingsInventoryName;
	private int guiWorldsSettingsInventoryRows;
	private String guiWorldsSettingsToolbarToolbarItem;
	private String guiWorldsSettingsToolbarBackItem;
	private String guiWorldsSettingsToolbarSearchItem;
	private String guiWorldsSettingsToolbarPageItem;
	private String guiWorldsSettingsToolbarExitItem;

	public void setGuiWorldsSettingsInventoryName(String guiWorldsSettingsInventoryName) {
		this.guiWorldsSettingsInventoryName = guiWorldsSettingsInventoryName;
	}
	public String getGuiWorldsSettingsInventoryName() {
		return this.guiWorldsSettingsInventoryName;
	}

	public void setGuiWorldsSettingsInventoryRows(int guiWorldsSettingsInventoryRows) {
		this.guiWorldsSettingsInventoryRows = guiWorldsSettingsInventoryRows;
	}
	public int getGuiWorldsSettingsInventoryRows() {
		return this.guiWorldsSettingsInventoryRows;
	}

	public void setGuiWorldsSettingsToolbarToolbarItem(String guiWorldsSettingsToolbarToolbarItem) {
		this.guiWorldsSettingsToolbarToolbarItem = guiWorldsSettingsToolbarToolbarItem;
	}
	public String getGuiWorldsSettingsToolbarToolbarItem() {
		return this.guiWorldsSettingsToolbarToolbarItem;
	}

	public void setGuiWorldsSettingsToolbarBackItem(String guiWorldsSettingsToolbarBackItem) {
		this.guiWorldsSettingsToolbarBackItem = guiWorldsSettingsToolbarBackItem;
	}
	public String getGuiWorldsSettingsToolbarBackItem() {
		return this.guiWorldsSettingsToolbarBackItem;
	}

	public void setGuiWorldsSettingsToolbarSearchItem(String guiWorldsSettingsToolbarSearchItem) {
		this.guiWorldsSettingsToolbarSearchItem = guiWorldsSettingsToolbarSearchItem;
	}
	public String getGuiWorldsSettingsToolbarSearchItem() {
		return this.guiWorldsSettingsToolbarSearchItem;
	}

	public void setGuiWorldsSettingsToolbarPageItem(String guiWorldsSettingsToolbarPageItem) {
		this.guiWorldsSettingsToolbarPageItem = guiWorldsSettingsToolbarPageItem;
	}
	public String getGuiWorldsSettingsToolbarPageItem() {
		return this.guiWorldsSettingsToolbarPageItem;
	}

	public void setGuiWorldsSettingsToolbarExitItem(String guiWorldsSettingsToolbarExitItem) {
		this.guiWorldsSettingsToolbarExitItem = guiWorldsSettingsToolbarExitItem;
	}
	public String getGuiWorldsSettingsToolbarExitItem() {
		return this.guiWorldsSettingsToolbarExitItem;
	}

	// Gui > Warps > Settings

	private String guiWarpsSettingsInventoryName;
	private int guiWarpsSettingsInventoryRows;
	private String guiWarpsSettingsToolbarToolbarItem;
	private String guiWarpsSettingsToolbarBackItem;
	private String guiWarpsSettingsToolbarSearchItem;
	private String guiWarpsSettingsToolbarPageItem;
	private String guiWarpsSettingsToolbarExitItem;

	public void setGuiWarpsSettingsInventoryName(String guiWarpsSettingsInventoryName) {
		this.guiWarpsSettingsInventoryName = guiWarpsSettingsInventoryName;
	}
	public String getGuiWarpsSettingsInventoryName() {
		return this.guiWarpsSettingsInventoryName;
	}

	public void setGuiWarpsSettingsInventoryRows(int guiWarpsSettingsInventoryRows) {
		this.guiWarpsSettingsInventoryRows = guiWarpsSettingsInventoryRows;
	}
	public int getGuiWarpsSettingsInventoryRows() {
		return this.guiWarpsSettingsInventoryRows;
	}

	public void setGuiWarpsSettingsToolbarToolbarItem(String guiWarpsSettingsToolbarToolbarItem) {
		this.guiWarpsSettingsToolbarToolbarItem = guiWarpsSettingsToolbarToolbarItem;
	}
	public String getGuiWarpsSettingsToolbarToolbarItem() {
		return this.guiWarpsSettingsToolbarToolbarItem;
	}

	public void setGuiWarpsSettingsToolbarBackItem(String guiWarpsSettingsToolbarBackItem) {
		this.guiWarpsSettingsToolbarBackItem = guiWarpsSettingsToolbarBackItem;
	}
	public String getGuiWarpsSettingsToolbarBackItem() {
		return this.guiWarpsSettingsToolbarBackItem;
	}

	public void setGuiWarpsSettingsToolbarSearchItem(String guiWarpsSettingsToolbarSearchItem) {
		this.guiWarpsSettingsToolbarSearchItem = guiWarpsSettingsToolbarSearchItem;
	}
	public String getGuiWarpsSettingsToolbarSearchItem() {
		return this.guiWarpsSettingsToolbarSearchItem;
	}

	public void setGuiWarpsSettingsToolbarPageItem(String guiWarpsSettingsToolbarPageItem) {
		this.guiWarpsSettingsToolbarPageItem = guiWarpsSettingsToolbarPageItem;
	}
	public String getGuiWarpsSettingsToolbarPageItem() {
		return this.guiWarpsSettingsToolbarPageItem;
	}

	public void setGuiWarpsSettingsToolbarExitItem(String guiWarpsSettingsToolbarExitItem) {
		this.guiWarpsSettingsToolbarExitItem = guiWarpsSettingsToolbarExitItem;
	}
	public String getGuiWarpsSettingsToolbarExitItem() {
		return this.guiWarpsSettingsToolbarExitItem;
	}
	
	// Gui > Homes > Settings

	private String guiHomesSettingsInventoryName;
	private int guiHomesSettingsInventoryRows;
	private String guiHomesSettingsToolbarToolbarItem;
	private String guiHomesSettingsToolbarBackItem;
	private String guiHomesSettingsToolbarSearchItem;
	private String guiHomesSettingsToolbarPageItem;
	private String guiHomesSettingsToolbarExitItem;

	public void setGuiHomesSettingsInventoryName(String guiHomesSettingsInventoryName) {
		this.guiHomesSettingsInventoryName = guiHomesSettingsInventoryName;
	}
	public String getGuiHomesSettingsInventoryName() {
		return this.guiHomesSettingsInventoryName;
	}

	public void setGuiHomesSettingsInventoryRows(int guiHomesSettingsInventoryRows) {
		this.guiHomesSettingsInventoryRows = guiHomesSettingsInventoryRows;
	}
	public int getGuiHomesSettingsInventoryRows() {
		return this.guiHomesSettingsInventoryRows;
	}

	public void setGuiHomesSettingsToolbarToolbarItem(String guiHomesSettingsToolbarToolbarItem) {
		this.guiHomesSettingsToolbarToolbarItem = guiHomesSettingsToolbarToolbarItem;
	}
	public String getGuiHomesSettingsToolbarToolbarItem() {
		return this.guiHomesSettingsToolbarToolbarItem;
	}

	public void setGuiHomesSettingsToolbarBackItem(String guiHomesSettingsToolbarBackItem) {
		this.guiHomesSettingsToolbarBackItem = guiHomesSettingsToolbarBackItem;
	}
	public String getGuiHomesSettingsToolbarBackItem() {
		return this.guiHomesSettingsToolbarBackItem;
	}

	public void setGuiHomesSettingsToolbarSearchItem(String guiHomesSettingsToolbarSearchItem) {
		this.guiHomesSettingsToolbarSearchItem = guiHomesSettingsToolbarSearchItem;
	}
	public String getGuiHomesSettingsToolbarSearchItem() {
		return this.guiHomesSettingsToolbarSearchItem;
	}

	public void setGuiHomesSettingsToolbarPageItem(String guiHomesSettingsToolbarPageItem) {
		this.guiHomesSettingsToolbarPageItem = guiHomesSettingsToolbarPageItem;
	}
	public String getGuiHomesSettingsToolbarPageItem() {
		return this.guiHomesSettingsToolbarPageItem;
	}

	public void setGuiHomesSettingsToolbarExitItem(String guiHomesSettingsToolbarExitItem) {
		this.guiHomesSettingsToolbarExitItem = guiHomesSettingsToolbarExitItem;
	}
	public String getGuiHomesSettingsToolbarExitItem() {
		return this.guiHomesSettingsToolbarExitItem;
	}

	// Gui > Applications > Settings

	private String guiApplicationsSettingsInventoryName;
	private int guiApplicationsSettingsInventoryRows;
	private String guiApplicationsSettingsToolbarToolbarItem;
	private String guiApplicationsSettingsToolbarBackItem;
	private String guiApplicationsSettingsToolbarPageItem;
	private String guiApplicationsSettingsToolbarExitItem;

	public void setGuiApplicationsSettingsInventoryName(String guiApplicationsSettingsInventoryName) {
		this.guiApplicationsSettingsInventoryName = guiApplicationsSettingsInventoryName;
	}
	public String getGuiApplicationsSettingsInventoryName() {
		return this.guiApplicationsSettingsInventoryName;
	}

	public void setGuiApplicationsSettingsInventoryRows(int guiApplicationsSettingsInventoryRows) {
		this.guiApplicationsSettingsInventoryRows = guiApplicationsSettingsInventoryRows;
	}
	public int getGuiApplicationsSettingsInventoryRows() {
		return this.guiApplicationsSettingsInventoryRows;
	}

	public void setGuiApplicationsSettingsToolbarToolbarItem(String guiApplicationsSettingsToolbarToolbarItem) {
		this.guiApplicationsSettingsToolbarToolbarItem = guiApplicationsSettingsToolbarToolbarItem;
	}
	public String getGuiApplicationsSettingsToolbarToolbarItem() {
		return this.guiApplicationsSettingsToolbarToolbarItem;
	}

	public void setGuiApplicationsSettingsToolbarBackItem(String guiApplicationsSettingsToolbarBackItem) {
		this.guiApplicationsSettingsToolbarBackItem = guiApplicationsSettingsToolbarBackItem;
	}
	public String getGuiApplicationsSettingsToolbarBackItem() {
		return this.guiApplicationsSettingsToolbarBackItem;
	}

	public void setGuiApplicationsSettingsToolbarPageItem(String guiApplicationsSettingsToolbarPageItem) {
		this.guiApplicationsSettingsToolbarPageItem = guiApplicationsSettingsToolbarPageItem;
	}
	public String getGuiApplicationsSettingsToolbarPageItem() {
		return this.guiApplicationsSettingsToolbarPageItem;
	}

	public void setGuiApplicationsSettingsToolbarExitItem(String guiApplicationsSettingsToolbarExitItem) {
		this.guiApplicationsSettingsToolbarExitItem = guiApplicationsSettingsToolbarExitItem;
	}
	public String getGuiApplicationsSettingsToolbarExitItem() {
		return this.guiApplicationsSettingsToolbarExitItem;
	}

	// Gui > Rules > Settings

	private String guiRulesSettingsInventoryName;
	private int guiRulesSettingsInventoryRows;
	private String guiRulesSettingsToolbarToolbarItem;
	private String guiRulesSettingsToolbarBackItem;
	private String guiRulesSettingsToolbarListItem;
	private String guiRulesSettingsToolbarPageItem;
	private String guiRulesSettingsToolbarExitItem;

	public void setGuiRulesSettingsInventoryName(String guiRulesSettingsInventoryName) {
		this.guiRulesSettingsInventoryName = guiRulesSettingsInventoryName;
	}
	public String getGuiRulesSettingsInventoryName() {
		return this.guiRulesSettingsInventoryName;
	}

	public void setGuiRulesSettingsInventoryRows(int guiRulesSettingsInventoryRows) {
		this.guiRulesSettingsInventoryRows = guiRulesSettingsInventoryRows;
	}
	public int getGuiRulesSettingsInventoryRows() {
		return this.guiRulesSettingsInventoryRows;
	}

	public void setGuiRulesSettingsToolbarToolbarItem(String guiRulesSettingsToolbarToolbarItem) {
		this.guiRulesSettingsToolbarToolbarItem = guiRulesSettingsToolbarToolbarItem;
	}
	public String getGuiRulesSettingsToolbarToolbarItem() {
		return this.guiRulesSettingsToolbarToolbarItem;
	}

	public void setGuiRulesSettingsToolbarBackItem(String guiRulesSettingsToolbarBackItem) {
		this.guiRulesSettingsToolbarBackItem = guiRulesSettingsToolbarBackItem;
	}
	public String getGuiRulesSettingsToolbarBackItem() {
		return this.guiRulesSettingsToolbarBackItem;
	}

	public void setGuiRulesSettingsToolbarListItem(String guiRulesSettingsToolbarListItem) {
		this.guiRulesSettingsToolbarListItem = guiRulesSettingsToolbarListItem;
	}
	public String getGuiRulesSettingsToolbarListItem() {
		return this.guiRulesSettingsToolbarListItem;
	}

	public void setGuiRulesSettingsToolbarPageItem(String guiRulesSettingsToolbarPageItem) {
		this.guiRulesSettingsToolbarPageItem = guiRulesSettingsToolbarPageItem;
	}
	public String getGuiRulesSettingsToolbarPageItem() {
		return this.guiRulesSettingsToolbarPageItem;
	}

	public void setGuiRulesSettingsToolbarExitItem(String guiRulesSettingsToolbarExitItem) {
		this.guiRulesSettingsToolbarExitItem = guiRulesSettingsToolbarExitItem;
	}
	public String getGuiRulesSettingsToolbarExitItem() {
		return this.guiRulesSettingsToolbarExitItem;
	}

	// Gui > Ranks > Settings

	private String guiRanksSettingsInventoryName;
	private int guiRanksSettingsInventoryRows;
	private String guiRanksSettingsToolbarToolbarItem;
	private String guiRanksSettingsToolbarBackItem;
	private String guiRanksSettingsToolbarListItem;
	private String guiRanksSettingsToolbarPageItem;
	private String guiRanksSettingsToolbarExitItem;

	public void setGuiRanksSettingsInventoryName(String guiRanksSettingsInventoryName) {
		this.guiRanksSettingsInventoryName = guiRanksSettingsInventoryName;
	}
	public String getGuiRanksSettingsInventoryName() {
		return this.guiRanksSettingsInventoryName;
	}

	public void setGuiRanksSettingsInventoryRows(int guiRanksSettingsInventoryRows) {
		this.guiRanksSettingsInventoryRows = guiRanksSettingsInventoryRows;
	}
	public int getGuiRanksSettingsInventoryRows() {
		return this.guiRanksSettingsInventoryRows;
	}

	public void setGuiRanksSettingsToolbarToolbarItem(String guiRanksSettingsToolbarToolbarItem) {
		this.guiRanksSettingsToolbarToolbarItem = guiRanksSettingsToolbarToolbarItem;
	}
	public String getGuiRanksSettingsToolbarToolbarItem() {
		return this.guiRanksSettingsToolbarToolbarItem;
	}

	public void setGuiRanksSettingsToolbarBackItem(String guiRanksSettingsToolbarBackItem) {
		this.guiRanksSettingsToolbarBackItem = guiRanksSettingsToolbarBackItem;
	}
	public String getGuiRanksSettingsToolbarBackItem() {
		return this.guiRanksSettingsToolbarBackItem;
	}

	public void setGuiRanksSettingsToolbarListItem(String guiRanksSettingsToolbarListItem) {
		this.guiRanksSettingsToolbarListItem = guiRanksSettingsToolbarListItem;
	}
	public String getGuiRanksSettingsToolbarListItem() {
		return this.guiRanksSettingsToolbarListItem;
	}

	public void setGuiRanksSettingsToolbarPageItem(String guiRanksSettingsToolbarPageItem) {
		this.guiRanksSettingsToolbarPageItem = guiRanksSettingsToolbarPageItem;
	}
	public String getGuiRanksSettingsToolbarPageItem() {
		return this.guiRanksSettingsToolbarPageItem;
	}

	public void setGuiRanksSettingsToolbarExitItem(String guiRanksSettingsToolbarExitItem) {
		this.guiRanksSettingsToolbarExitItem = guiRanksSettingsToolbarExitItem;
	}
	public String getGuiRanksSettingsToolbarExitItem() {
		return this.guiRanksSettingsToolbarExitItem;
	}

	// Gui > Player > Settings

	private String guiPlayerSettingsInventoryName;
	private int guiPlayerSettingsInventoryRows;
	private String guiPlayerSettingsToolbarToolbarItem;
	private String guiPlayerSettingsToolbarBackItem;
	private String guiPlayerSettingsToolbarPageItem;
	private String guiPlayerSettingsToolbarExitItem;

	public void setGuiPlayerSettingsInventoryName(String guiPlayerSettingsInventoryName) {
		this.guiPlayerSettingsInventoryName = guiPlayerSettingsInventoryName;
	}
	public String getGuiPlayerSettingsInventoryName() {
		return this.guiPlayerSettingsInventoryName;
	}

	public void setGuiPlayerSettingsInventoryRows(int guiPlayerSettingsInventoryRows) {
		this.guiPlayerSettingsInventoryRows = guiPlayerSettingsInventoryRows;
	}
	public int getGuiPlayerSettingsInventoryRows() {
		return this.guiPlayerSettingsInventoryRows;
	}

	public void setGuiPlayerSettingsToolbarToolbarItem(String guiPlayerSettingsToolbarToolbarItem) {
		this.guiPlayerSettingsToolbarToolbarItem = guiPlayerSettingsToolbarToolbarItem;
	}
	public String getGuiPlayerSettingsToolbarToolbarItem() {
		return this.guiPlayerSettingsToolbarToolbarItem;
	}

	public void setGuiPlayerSettingsToolbarBackItem(String guiPlayerSettingsToolbarBackItem) {
		this.guiPlayerSettingsToolbarBackItem = guiPlayerSettingsToolbarBackItem;
	}
	public String getGuiPlayerSettingsToolbarBackItem() {
		return this.guiPlayerSettingsToolbarBackItem;
	}

	public void setGuiPlayerSettingsToolbarPageItem(String guiPlayerSettingsToolbarPageItem) {
		this.guiPlayerSettingsToolbarPageItem = guiPlayerSettingsToolbarPageItem;
	}
	public String getGuiPlayerSettingsToolbarPageItem() {
		return this.guiPlayerSettingsToolbarPageItem;
	}

	public void setGuiPlayerSettingsToolbarExitItem(String guiPlayerSettingsToolbarExitItem) {
		this.guiPlayerSettingsToolbarExitItem = guiPlayerSettingsToolbarExitItem;
	}
	public String getGuiPlayerSettingsToolbarExitItem() {
		return this.guiPlayerSettingsToolbarExitItem;
	}

	// Gui > Player > Contents > Friend

	private int guiPlayerContentsFriendSlot;
	private String guiPlayerContentsFriendItem;
	private String guiPlayerContentsFriendName;
	private List<String> guiPlayerContentsFriendLore;

	public void setGuiPlayerContentsFriendSlot(int guiPlayerContentsFriendSlot) {
		this.guiPlayerContentsFriendSlot = guiPlayerContentsFriendSlot;
	}
	public int getGuiPlayerContentsFriendSlot() {
		return this.guiPlayerContentsFriendSlot;
	}

	public void setGuiPlayerContentsFriendItem(String guiPlayerContentsFriendItem) {
		this.guiPlayerContentsFriendItem = guiPlayerContentsFriendItem;
	}
	public String getGuiPlayerContentsFriendItem() {
		return this.guiPlayerContentsFriendItem;
	}

	public void setGuiPlayerContentsFriendName(String guiPlayerContentsFriendName) {
		this.guiPlayerContentsFriendName = guiPlayerContentsFriendName;
	}
	public String getGuiPlayerContentsFriendName() {
		return this.guiPlayerContentsFriendName;
	}

	public void setGuiPlayerContentsFriendLore(List<String> guiPlayerContentsFriendLore) {
		this.guiPlayerContentsFriendLore = guiPlayerContentsFriendLore;
	}
	public List<String> getGuiPlayerContentsFriendLore() {
		return this.guiPlayerContentsFriendLore;
	}

	// Gui > Player > Contents > Message

	private int guiPlayerContentsMessageSlot;
	private String guiPlayerContentsMessageItem;
	private String guiPlayerContentsMessageName;
	private List<String> guiPlayerContentsMessageLore;

	public void setGuiPlayerContentsMessageSlot(int guiPlayerContentsMessageSlot) {
		this.guiPlayerContentsMessageSlot = guiPlayerContentsMessageSlot;
	}
	public int getGuiPlayerContentsMessageSlot() {
		return this.guiPlayerContentsMessageSlot;
	}

	public void setGuiPlayerContentsMessageItem(String guiPlayerContentsMessageItem) {
		this.guiPlayerContentsMessageItem = guiPlayerContentsMessageItem;
	}
	public String getGuiPlayerContentsMessageItem() {
		return this.guiPlayerContentsMessageItem;
	}

	public void setGuiPlayerContentsMessageName(String guiPlayerContentsMessageName) {
		this.guiPlayerContentsMessageName = guiPlayerContentsMessageName;
	}
	public String getGuiPlayerContentsMessageName() {
		return this.guiPlayerContentsMessageName;
	}

	public void setGuiPlayerContentsMessageLore(List<String> guiPlayerContentsMessageLore) {
		this.guiPlayerContentsMessageLore = guiPlayerContentsMessageLore;
	}
	public List<String> getGuiPlayerContentsMessageLore() {
		return this.guiPlayerContentsMessageLore;
	}

	// Gui > Player > Contents > Teleport

	private int guiPlayerContentsTeleportSlot;
	private String guiPlayerContentsTeleportItem;
	private String guiPlayerContentsTeleportName;
	private List<String> guiPlayerContentsTeleportLore;

	public void setGuiPlayerContentsTeleportSlot(int guiPlayerContentsTeleportSlot) {
		this.guiPlayerContentsTeleportSlot = guiPlayerContentsTeleportSlot;
	}
	public int getGuiPlayerContentsTeleportSlot() {
		return this.guiPlayerContentsTeleportSlot;
	}

	public void setGuiPlayerContentsTeleportItem(String guiPlayerContentsTeleportItem) {
		this.guiPlayerContentsTeleportItem = guiPlayerContentsTeleportItem;
	}
	public String getGuiPlayerContentsTeleportItem() {
		return this.guiPlayerContentsTeleportItem;
	}

	public void setGuiPlayerContentsTeleportName(String guiPlayerContentsTeleportName) {
		this.guiPlayerContentsTeleportName = guiPlayerContentsTeleportName;
	}
	public String getGuiPlayerContentsTeleportName() {
		return this.guiPlayerContentsTeleportName;
	}

	public void setGuiPlayerContentsTeleportLore(List<String> guiPlayerContentsTeleportLore) {
		this.guiPlayerContentsTeleportLore = guiPlayerContentsTeleportLore;
	}
	public List<String> getGuiPlayerContentsTeleportLore() {
		return this.guiPlayerContentsTeleportLore;
	}

	// Gui > Player > Contents > Spectate

	private int guiPlayerContentsSpectateSlot;
	private String guiPlayerContentsSpectateItem;
	private String guiPlayerContentsSpectateName;
	private List<String> guiPlayerContentsSpectateLore;

	public void setGuiPlayerContentsSpectateSlot(int guiPlayerContentsSpectateSlot) {
		this.guiPlayerContentsSpectateSlot = guiPlayerContentsSpectateSlot;
	}
	public int getGuiPlayerContentsSpectateSlot() {
		return this.guiPlayerContentsSpectateSlot;
	}

	public void setGuiPlayerContentsSpectateItem(String guiPlayerContentsSpectateItem) {
		this.guiPlayerContentsSpectateItem = guiPlayerContentsSpectateItem;
	}
	public String getGuiPlayerContentsSpectateItem() {
		return this.guiPlayerContentsSpectateItem;
	}

	public void setGuiPlayerContentsSpectateName(String guiPlayerContentsSpectateName) {
		this.guiPlayerContentsSpectateName = guiPlayerContentsSpectateName;
	}
	public String getGuiPlayerContentsSpectateName() {
		return this.guiPlayerContentsSpectateName;
	}

	public void setGuiPlayerContentsSpectateLore(List<String> guiPlayerContentsSpectateLore) {
		this.guiPlayerContentsSpectateLore = guiPlayerContentsSpectateLore;
	}
	public List<String> getGuiPlayerContentsSpectateLore() {
		return this.guiPlayerContentsSpectateLore;
	}

	// Gui > Player > Contents > Report

	private int guiPlayerContentsReportSlot;
	private String guiPlayerContentsReportItem;
	private String guiPlayerContentsReportName;
	private List<String> guiPlayerContentsReportLore;

	public void setGuiPlayerContentsReportSlot(int guiPlayerContentsReportSlot) {
		this.guiPlayerContentsReportSlot = guiPlayerContentsReportSlot;
	}
	public int getGuiPlayerContentsReportSlot() {
		return this.guiPlayerContentsReportSlot;
	}

	public void setGuiPlayerContentsReportItem(String guiPlayerContentsReportItem) {
		this.guiPlayerContentsReportItem = guiPlayerContentsReportItem;
	}
	public String getGuiPlayerContentsReportItem() {
		return this.guiPlayerContentsReportItem;
	}

	public void setGuiPlayerContentsReportName(String guiPlayerContentsReportName) {
		this.guiPlayerContentsReportName = guiPlayerContentsReportName;
	}
	public String getGuiPlayerContentsReportName() {
		return this.guiPlayerContentsReportName;
	}

	public void setGuiPlayerContentsReportLore(List<String> guiPlayerContentsReportLore) {
		this.guiPlayerContentsReportLore = guiPlayerContentsReportLore;
	}
	public List<String> getGuiPlayerContentsReportLore() {
		return this.guiPlayerContentsReportLore;
	}

	// Gui > Staff > Settings

	private String guiStaffSettingsInventoryName;
	private int guiStaffSettingsInventoryRows;
	private String guiStaffSettingsToolbarToolbarItem;
	private String guiStaffSettingsToolbarBackItem;
	private String guiStaffSettingsToolbarPageItem;
	private String guiStaffSettingsToolbarExitItem;

	public void setGuiStaffSettingsInventoryName(String guiStaffSettingsInventoryName) {
		this.guiStaffSettingsInventoryName = guiStaffSettingsInventoryName;
	}
	public String getGuiStaffSettingsInventoryName() {
		return this.guiStaffSettingsInventoryName;
	}

	public void setGuiStaffSettingsInventoryRows(int guiStaffSettingsInventoryRows) {
		this.guiStaffSettingsInventoryRows = guiStaffSettingsInventoryRows;
	}
	public int getGuiStaffSettingsInventoryRows() {
		return this.guiStaffSettingsInventoryRows;
	}

	public void setGuiStaffSettingsToolbarToolbarItem(String guiStaffSettingsToolbarToolbarItem) {
		this.guiStaffSettingsToolbarToolbarItem = guiStaffSettingsToolbarToolbarItem;
	}
	public String getGuiStaffSettingsToolbarToolbarItem() {
		return this.guiStaffSettingsToolbarToolbarItem;
	}

	public void setGuiStaffSettingsToolbarBackItem(String guiStaffSettingsToolbarBackItem) {
		this.guiStaffSettingsToolbarBackItem = guiStaffSettingsToolbarBackItem;
	}
	public String getGuiStaffSettingsToolbarBackItem() {
		return this.guiStaffSettingsToolbarBackItem;
	}

	public void setGuiStaffSettingsToolbarPageItem(String guiStaffSettingsToolbarPageItem) {
		this.guiStaffSettingsToolbarPageItem = guiStaffSettingsToolbarPageItem;
	}
	public String getGuiStaffSettingsToolbarPageItem() {
		return this.guiStaffSettingsToolbarPageItem;
	}

	public void setGuiStaffSettingsToolbarExitItem(String guiStaffSettingsToolbarExitItem) {
		this.guiStaffSettingsToolbarExitItem = guiStaffSettingsToolbarExitItem;
	}
	public String getGuiStaffSettingsToolbarExitItem() {
		return this.guiStaffSettingsToolbarExitItem;
	}

	// Gui > Staff > Contents > Warn

	private int guiStaffContentsWarnSlot;
	private String guiStaffContentsWarnItem;
	private String guiStaffContentsWarnName;
	private List<String> guiStaffContentsWarnLore;

	public void setGuiStaffContentsWarnSlot(int guiStaffContentsWarnSlot) {
		this.guiStaffContentsWarnSlot = guiStaffContentsWarnSlot;
	}
	public int getGuiStaffContentsWarnSlot() {
		return this.guiStaffContentsWarnSlot;
	}

	public void setGuiStaffContentsWarnItem(String guiStaffContentsWarnItem) {
		this.guiStaffContentsWarnItem = guiStaffContentsWarnItem;
	}
	public String getGuiStaffContentsWarnItem() {
		return this.guiStaffContentsWarnItem;
	}

	public void setGuiStaffContentsWarnName(String guiStaffContentsWarnName) {
		this.guiStaffContentsWarnName = guiStaffContentsWarnName;
	}
	public String getGuiStaffContentsWarnName() {
		return this.guiStaffContentsWarnName;
	}

	public void setGuiStaffContentsWarnLore(List<String> guiStaffContentsWarnLore) {
		this.guiStaffContentsWarnLore = guiStaffContentsWarnLore;
	}
	public List<String> getGuiStaffContentsWarnLore() {
		return this.guiStaffContentsWarnLore;
	}

	// Gui > Staff > Contents > Mute

	private int guiStaffContentsMuteSlot;
	private String guiStaffContentsMuteItem;
	private String guiStaffContentsMuteName;
	private List<String> guiStaffContentsMuteLore;

	public void setGuiStaffContentsMuteSlot(int guiStaffContentsMuteSlot) {
		this.guiStaffContentsMuteSlot = guiStaffContentsMuteSlot;
	}
	public int getGuiStaffContentsMuteSlot() {
		return this.guiStaffContentsMuteSlot;
	}

	public void setGuiStaffContentsMuteItem(String guiStaffContentsMuteItem) {
		this.guiStaffContentsMuteItem = guiStaffContentsMuteItem;
	}
	public String getGuiStaffContentsMuteItem() {
		return this.guiStaffContentsMuteItem;
	}

	public void setGuiStaffContentsMuteName(String guiStaffContentsMuteName) {
		this.guiStaffContentsMuteName = guiStaffContentsMuteName;
	}
	public String getGuiStaffContentsMuteName() {
		return this.guiStaffContentsMuteName;
	}

	public void setGuiStaffContentsMuteLore(List<String> guiStaffContentsMuteLore) {
		this.guiStaffContentsMuteLore = guiStaffContentsMuteLore;
	}
	public List<String> getGuiStaffContentsMuteLore() {
		return this.guiStaffContentsMuteLore;
	}

	// Gui > Staff > Contents > Freeze

	private int guiStaffContentsFreezeSlot;
	private String guiStaffContentsFreezeItem;
	private String guiStaffContentsFreezeName;
	private List<String> guiStaffContentsFreezeLore;

	public void setGuiStaffContentsFreezeSlot(int guiStaffContentsFreezeSlot) {
		this.guiStaffContentsFreezeSlot = guiStaffContentsFreezeSlot;
	}
	public int getGuiStaffContentsFreezeSlot() {
		return this.guiStaffContentsFreezeSlot;
	}

	public void setGuiStaffContentsFreezeItem(String guiStaffContentsFreezeItem) {
		this.guiStaffContentsFreezeItem = guiStaffContentsFreezeItem;
	}
	public String getGuiStaffContentsFreezeItem() {
		return this.guiStaffContentsFreezeItem;
	}

	public void setGuiStaffContentsFreezeName(String guiStaffContentsFreezeName) {
		this.guiStaffContentsFreezeName = guiStaffContentsFreezeName;
	}
	public String getGuiStaffContentsFreezeName() {
		return this.guiStaffContentsFreezeName;
	}

	public void setGuiStaffContentsFreezeLore(List<String> guiStaffContentsFreezeLore) {
		this.guiStaffContentsFreezeLore = guiStaffContentsFreezeLore;
	}
	public List<String> getGuiStaffContentsFreezeLore() {
		return this.guiStaffContentsFreezeLore;
	}

	// Gui > Staff > Contents > Kick

	private int guiStaffContentsKickSlot;
	private String guiStaffContentsKickItem;
	private String guiStaffContentsKickName;
	private List<String> guiStaffContentsKickLore;

	public void setGuiStaffContentsKickSlot(int guiStaffContentsKickSlot) {
		this.guiStaffContentsKickSlot = guiStaffContentsKickSlot;
	}
	public int getGuiStaffContentsKickSlot() {
		return this.guiStaffContentsKickSlot;
	}

	public void setGuiStaffContentsKickItem(String guiStaffContentsKickItem) {
		this.guiStaffContentsKickItem = guiStaffContentsKickItem;
	}
	public String getGuiStaffContentsKickItem() {
		return this.guiStaffContentsKickItem;
	}

	public void setGuiStaffContentsKickName(String guiStaffContentsKickName) {
		this.guiStaffContentsKickName = guiStaffContentsKickName;
	}
	public String getGuiStaffContentsKickName() {
		return this.guiStaffContentsKickName;
	}

	public void setGuiStaffContentsKickLore(List<String> guiStaffContentsKickLore) {
		this.guiStaffContentsKickLore = guiStaffContentsKickLore;
	}
	public List<String> getGuiStaffContentsKickLore() {
		return this.guiStaffContentsKickLore;
	}

	// Gui > Staff > Contents > Ban

	private int guiStaffContentsBanSlot;
	private String guiStaffContentsBanItem;
	private String guiStaffContentsBanName;
	private List<String> guiStaffContentsBanLore;

	public void setGuiStaffContentsBanSlot(int guiStaffContentsBanSlot) {
		this.guiStaffContentsBanSlot = guiStaffContentsBanSlot;
	}
	public int getGuiStaffContentsBanSlot() {
		return this.guiStaffContentsBanSlot;
	}

	public void setGuiStaffContentsBanItem(String guiStaffContentsBanItem) {
		this.guiStaffContentsBanItem = guiStaffContentsBanItem;
	}
	public String getGuiStaffContentsBanItem() {
		return this.guiStaffContentsBanItem;
	}

	public void setGuiStaffContentsBanName(String guiStaffContentsBanName) {
		this.guiStaffContentsBanName = guiStaffContentsBanName;
	}
	public String getGuiStaffContentsBanName() {
		return this.guiStaffContentsBanName;
	}

	public void setGuiStaffContentsBanLore(List<String> guiStaffContentsBanLore) {
		this.guiStaffContentsBanLore = guiStaffContentsBanLore;
	}
	public List<String> getGuiStaffContentsBanLore() {
		return this.guiStaffContentsBanLore;
	}

	// Gui > Staff > Contents > Tickets

	private int guiStaffContentsTicketsSlot;
	private String guiStaffContentsTicketsItem;
	private String guiStaffContentsTicketsName;
	private List<String> guiStaffContentsTicketsLore;

	public void setGuiStaffContentsTicketsSlot(int guiStaffContentsTicketsSlot) {
		this.guiStaffContentsTicketsSlot = guiStaffContentsTicketsSlot;
	}
	public int getGuiStaffContentsTicketsSlot() {
		return this.guiStaffContentsTicketsSlot;
	}

	public void setGuiStaffContentsTicketsItem(String guiStaffContentsTicketsItem) {
		this.guiStaffContentsTicketsItem = guiStaffContentsTicketsItem;
	}
	public String getGuiStaffContentsTicketsItem() {
		return this.guiStaffContentsTicketsItem;
	}

	public void setGuiStaffContentsTicketsName(String guiStaffContentsTicketsName) {
		this.guiStaffContentsTicketsName = guiStaffContentsTicketsName;
	}
	public String getGuiStaffContentsTicketsName() {
		return this.guiStaffContentsTicketsName;
	}

	public void setGuiStaffContentsTicketsLore(List<String> guiStaffContentsTicketsLore) {
		this.guiStaffContentsTicketsLore = guiStaffContentsTicketsLore;
	}
	public List<String> getGuiStaffContentsTicketsLore() {
		return this.guiStaffContentsTicketsLore;
	}

	// Gui > Staff > Contents > Notes

	private int guiStaffContentsNotesSlot;
	private String guiStaffContentsNotesItem;
	private String guiStaffContentsNotesName;
	private List<String> guiStaffContentsNotesLore;

	public void setGuiStaffContentsNotesSlot(int guiStaffContentsNotesSlot) {
		this.guiStaffContentsNotesSlot = guiStaffContentsNotesSlot;
	}
	public int getGuiStaffContentsNotesSlot() {
		return this.guiStaffContentsNotesSlot;
	}

	public void setGuiStaffContentsNotesItem(String guiStaffContentsNotesItem) {
		this.guiStaffContentsNotesItem = guiStaffContentsNotesItem;
	}
	public String getGuiStaffContentsNotesItem() {
		return this.guiStaffContentsNotesItem;
	}

	public void setGuiStaffContentsNotesName(String guiStaffContentsNotesName) {
		this.guiStaffContentsNotesName = guiStaffContentsNotesName;
	}
	public String getGuiStaffContentsNotesName() {
		return this.guiStaffContentsNotesName;
	}

	public void setGuiStaffContentsNotesLore(List<String> guiStaffContentsNotesLore) {
		this.guiStaffContentsNotesLore = guiStaffContentsNotesLore;
	}
	public List<String> getGuiStaffContentsNotesLore() {
		return this.guiStaffContentsNotesLore;
	}

	// Gui > Staff > Contents > Bans

	private int guiStaffContentsBansSlot;
	private String guiStaffContentsBansItem;
	private String guiStaffContentsBansName;
	private List<String> guiStaffContentsBansLore;

	public void setGuiStaffContentsBansSlot(int guiStaffContentsBansSlot) {
		this.guiStaffContentsBansSlot = guiStaffContentsBansSlot;
	}
	public int getGuiStaffContentsBansSlot() {
		return this.guiStaffContentsBansSlot;
	}

	public void setGuiStaffContentsBansItem(String guiStaffContentsBansItem) {
		this.guiStaffContentsBansItem = guiStaffContentsBansItem;
	}
	public String getGuiStaffContentsBansItem() {
		return this.guiStaffContentsBansItem;
	}

	public void setGuiStaffContentsBansName(String guiStaffContentsBansName) {
		this.guiStaffContentsBansName = guiStaffContentsBansName;
	}
	public String getGuiStaffContentsBansName() {
		return this.guiStaffContentsBansName;
	}

	public void setGuiStaffContentsBansLore(List<String> guiStaffContentsBansLore) {
		this.guiStaffContentsBansLore = guiStaffContentsBansLore;
	}
	public List<String> getGuiStaffContentsBansLore() {
		return this.guiStaffContentsBansLore;
	}
}
