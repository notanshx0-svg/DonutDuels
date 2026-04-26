/*     */ package org.ms.donutduels.duel;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*     */ import org.ms.donutduels.utils.ColorUtil;
/*     */ 
/*     */ public class DuelManager implements Listener {
/*     */   private final DonutDuels plugin;
/*  23 */   private final Map<UUID, ActiveDuel> activeDuels = new HashMap<>();
/*  24 */   private final Map<UUID, PlayerState> playerStates = new HashMap<>();
/*  25 */   private final Map<UUID, Boolean> spectatorPlayers = new HashMap<>();
/*  26 */   private final Map<UUID, TaskImplementation<Void>> winnerTimers = new HashMap<>();
/*  27 */   private final Map<String, TaskImplementation<Void>> duelTimers = new HashMap<>();
/*     */   
/*     */   public DuelManager(DonutDuels plugin) {
/*  30 */     this.plugin = plugin;
/*  31 */     plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDuel(Player player1, Player player2, String arenaName) {
/*  36 */     this.playerStates.put(player1.getUniqueId(), new PlayerState(player1.getLocation(), player1.getGameMode()));
/*  37 */     this.playerStates.put(player2.getUniqueId(), new PlayerState(player2.getLocation(), player2.getGameMode()));
/*     */ 
/*     */     
/*  40 */     ActiveDuel duel = new ActiveDuel(player1.getUniqueId(), player2.getUniqueId(), arenaName);
/*  41 */     this.activeDuels.put(player1.getUniqueId(), duel);
/*  42 */     this.activeDuels.put(player2.getUniqueId(), duel);
/*     */ 
/*     */     
/*  45 */     player1.setGameMode(GameMode.SURVIVAL);
/*  46 */     player2.setGameMode(GameMode.SURVIVAL);
/*     */ 
/*     */     
/*  49 */     startDuelTimer(duel, 5);
/*     */   }
/*     */   
/*     */   private void startDuelTimer(ActiveDuel duel, int durationMinutes) {
/*  53 */     String duelId = duel.getDuelId();
/*  54 */     int[] timeLeft = { durationMinutes * 60 };
/*     */     
/*  56 */     TaskImplementation<Void> duelTimer = this.plugin.getSchedulerService().runGlobalRepeating(task -> { Player player1 = Bukkit.getPlayer(duel.getPlayer1()); Player player2 = Bukkit.getPlayer(duel.getPlayer2()); if (timeLeft[0] <= 0 || player1 == null || player2 == null) { if (player1 != null && player2 != null) endDuelAsDraw(player1, player2);  this.duelTimers.remove(duelId); task.cancel(); return; }  timeLeft[0] = timeLeft[0] - 1; }1L, 20L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.duelTimers.put(duelId, duelTimer);
/*     */   }
/*     */ 
/*     */   
/*     */   private void endDuelAsDraw(Player player1, Player player2) {
/*  78 */     String drawTitle = ColorUtil.color("&7Draw!");
/*  79 */     String drawSubtitle = ColorUtil.color("&fYou can now /leave");
/*     */     
/*  81 */     player1.sendTitle(drawTitle, drawSubtitle, 10, 70, 20);
/*  82 */     player2.sendTitle(drawTitle, drawSubtitle, 10, 70, 20);
/*     */ 
/*     */     
/*  85 */     player1.sendActionBar(ColorUtil.color("&7Type /leave to leave the arena"));
/*  86 */     player2.sendActionBar(ColorUtil.color("&7Type /leave to leave the arena"));
/*     */ 
/*     */     
/*  89 */     this.activeDuels.remove(player1.getUniqueId());
/*  90 */     this.activeDuels.remove(player2.getUniqueId());
/*     */   }
/*     */   
/*     */   public boolean isInDuel(Player player) {
/*  94 */     return (this.activeDuels.containsKey(player.getUniqueId()) || this.playerStates.containsKey(player.getUniqueId()));
/*     */   }
/*     */   
/*     */   public boolean isSpectator(Player player) {
/*  98 */     return ((Boolean)this.spectatorPlayers.getOrDefault(player.getUniqueId(), Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDuel(Player winner, Player loser) {
/* 103 */     ActiveDuel duel = this.activeDuels.get(winner.getUniqueId());
/* 104 */     if (duel != null) {
/* 105 */       TaskImplementation<Void> duelTimer = this.duelTimers.get(duel.getDuelId());
/* 106 */       if (duelTimer != null) {
/* 107 */         duelTimer.cancel();
/* 108 */         this.duelTimers.remove(duel.getDuelId());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 113 */     winner.sendTitle(
/* 114 */         ColorUtil.color("#10FF00You Win!"), 
/* 115 */         ColorUtil.color("&fGet your loot before the time runs out"), 10, 70, 20);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     loser.sendTitle(
/* 121 */         ColorUtil.color("&cYou Lost!"), 
/* 122 */         ColorUtil.color("&fYou can now leave"), 10, 70, 20);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     loser.setGameMode(GameMode.SPECTATOR);
/* 128 */     this.spectatorPlayers.put(loser.getUniqueId(), Boolean.valueOf(true));
/* 129 */     loser.sendActionBar(ColorUtil.color("&7Type /leave to leave the arena"));
/*     */ 
/*     */     
/* 132 */     winner.sendMessage(ColorUtil.color("&7You have #009AFF4 minutes &7to collect the loot and leave"));
/* 133 */     startWinnerCountdown(winner);
/*     */ 
/*     */     
/* 136 */     if (this.plugin.getPlayerStatsManager() != null) {
/* 137 */       this.plugin.getPlayerStatsManager().recordWin(winner.getUniqueId());
/* 138 */       this.plugin.getPlayerStatsManager().recordLoss(loser.getUniqueId());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 143 */     this.activeDuels.remove(loser.getUniqueId());
/*     */   }
/*     */   
/*     */   private void startWinnerCountdown(Player winner) {
/* 147 */     int[] timeLeft = { 240 };
/*     */     
/* 149 */     TaskImplementation<Void> timer = this.plugin.getSchedulerService().runEntity((Entity)winner, task -> {
/*     */           if (!winner.isOnline() || timeLeft[0] <= 0) {
/*     */             if (winner.isOnline()) {
/*     */               leaveDuel(winner);
/*     */             }
/*     */             
/*     */             this.winnerTimers.remove(winner.getUniqueId());
/*     */             
/*     */             task.cancel();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           int minutes = timeLeft[0] / 60;
/*     */           
/*     */           int seconds = timeLeft[0] % 60;
/*     */           
/*     */           String timeFormat = String.format("%dm %ds", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
/*     */           winner.sendActionBar(ColorUtil.color("&7You have &b" + timeFormat + " &7to collect the loot"));
/*     */           timeLeft[0] = timeLeft[0] - 1;
/*     */         });
/* 170 */     TaskImplementation<Void> repeatingTimer = this.plugin.getSchedulerService().runGlobalRepeating(task -> { if (!winner.isOnline() || timeLeft[0] <= 0) { if (winner.isOnline()) leaveDuel(winner);  this.winnerTimers.remove(winner.getUniqueId()); task.cancel(); return; }  int minutes = timeLeft[0] / 60; int seconds = timeLeft[0] % 60; String timeFormat = String.format("%dm %ds", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) }); winner.sendActionBar(ColorUtil.color("&7You have &b" + timeFormat + " &7to collect the loot")); timeLeft[0] = timeLeft[0] - 1; }1L, 20L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     this.winnerTimers.put(winner.getUniqueId(), repeatingTimer);
/*     */   }
/*     */   
/*     */   public void leaveDuel(Player player) {
/* 194 */     UUID playerId = player.getUniqueId();
/* 195 */     PlayerState originalState = this.playerStates.get(playerId);
/*     */ 
/*     */     
/* 198 */     TaskImplementation<Void> timer = this.winnerTimers.get(playerId);
/* 199 */     if (timer != null) {
/* 200 */       timer.cancel();
/* 201 */       this.winnerTimers.remove(playerId);
/*     */     } 
/*     */     
/* 204 */     if (originalState != null) {
/*     */       
/* 206 */       player.teleport(originalState.getLocation());
/* 207 */       player.setGameMode(originalState.getGameMode());
/*     */ 
/*     */       
/* 210 */       this.playerStates.remove(playerId);
/* 211 */       this.activeDuels.remove(playerId);
/* 212 */       this.spectatorPlayers.remove(playerId);
/*     */       
/* 214 */       player.sendMessage(ColorUtil.color("&7You have left the arena."));
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent event) {
/* 220 */     Player player = event.getPlayer();
/*     */ 
/*     */     
/* 223 */     if (isInDuel(player) && !isSpectator(player)) {
/* 224 */       ActiveDuel duel = this.activeDuels.get(player.getUniqueId());
/* 225 */       if (duel != null && isOutsideArenaBounds(player, duel.getArenaName())) {
/*     */         
/* 227 */         Location arenaCenter = getArenaCenter(duel.getArenaName());
/* 228 */         if (arenaCenter != null) {
/* 229 */           player.teleport(arenaCenter);
/* 230 */           player.sendMessage(ColorUtil.color("&cYou cannot leave the arena during a duel!"));
/* 231 */           player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerDeath(PlayerDeathEvent event) {
/* 239 */     Player loser = event.getEntity();
/* 240 */     Player killer = loser.getKiller();
/*     */     
/* 242 */     if (killer == null || !isInDuel(loser) || !isInDuel(killer)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 247 */     event.setCancelled(true);
/* 248 */     event.getDrops().clear();
/* 249 */     event.setDroppedExp(0);
/*     */ 
/*     */     
/* 252 */     loser.setHealth(loser.getMaxHealth());
/* 253 */     loser.setFoodLevel(20);
/* 254 */     loser.setSaturation(20.0F);
/*     */ 
/*     */     
/* 257 */     endDuel(killer, loser);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerQuit(PlayerQuitEvent event) {
/* 262 */     Player player = event.getPlayer();
/* 263 */     UUID playerId = player.getUniqueId();
/*     */ 
/*     */     
/* 266 */     TaskImplementation<Void> timer = this.winnerTimers.get(playerId);
/* 267 */     if (timer != null) {
/* 268 */       timer.cancel();
/* 269 */       this.winnerTimers.remove(playerId);
/*     */     } 
/*     */     
/* 272 */     if (isInDuel(player)) {
/* 273 */       ActiveDuel duel = this.activeDuels.get(playerId);
/* 274 */       if (duel != null) {
/*     */         
/* 276 */         UUID opponentId = duel.getPlayer1().equals(playerId) ? duel.getPlayer2() : duel.getPlayer1();
/* 277 */         Player opponent = Bukkit.getPlayer(opponentId);
/*     */         
/* 279 */         if (opponent != null) {
/* 280 */           endDuel(opponent, player);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 286 */     this.playerStates.remove(playerId);
/* 287 */     this.activeDuels.remove(playerId);
/* 288 */     this.spectatorPlayers.remove(playerId);
/*     */   }
/*     */   
/*     */   private boolean isOutsideArenaBounds(Player player, String arenaName) {
/* 292 */     Location pos1 = this.plugin.getDatabase().getPosition(arenaName + "_pos1");
/* 293 */     Location pos2 = this.plugin.getDatabase().getPosition(arenaName + "_pos2");
/*     */     
/* 295 */     if (pos1 == null || pos2 == null) {
/* 296 */       return false;
/*     */     }
/*     */     
/* 299 */     Location playerLoc = player.getLocation();
/*     */ 
/*     */     
/* 302 */     double minX = Math.min(pos1.getX(), pos2.getX()) - 10.0D;
/* 303 */     double maxX = Math.max(pos1.getX(), pos2.getX()) + 10.0D;
/* 304 */     double minZ = Math.min(pos1.getZ(), pos2.getZ()) - 10.0D;
/* 305 */     double maxZ = Math.max(pos1.getZ(), pos2.getZ()) + 10.0D;
/*     */     
/* 307 */     return (playerLoc.getX() < minX || playerLoc.getX() > maxX || playerLoc
/* 308 */       .getZ() < minZ || playerLoc.getZ() > maxZ);
/*     */   }
/*     */   
/*     */   private Location getArenaCenter(String arenaName) {
/* 312 */     Location pos1 = this.plugin.getDatabase().getPosition(arenaName + "_pos1");
/* 313 */     Location pos2 = this.plugin.getDatabase().getPosition(arenaName + "_pos2");
/*     */     
/* 315 */     if (pos1 != null && pos2 != null) {
/* 316 */       double centerX = (pos1.getX() + pos2.getX()) / 2.0D;
/* 317 */       double centerY = Math.max(pos1.getY(), pos2.getY());
/* 318 */       double centerZ = (pos1.getZ() + pos2.getZ()) / 2.0D;
/* 319 */       return new Location(pos1.getWorld(), centerX, centerY, centerZ);
/*     */     } 
/*     */     
/* 322 */     return null;
/*     */   }
/*     */   
/*     */   public static class ActiveDuel
/*     */   {
/*     */     private final UUID player1;
/*     */     private final UUID player2;
/*     */     private final String arenaName;
/*     */     private final long startTime;
/*     */     private final String duelId;
/*     */     
/*     */     public ActiveDuel(UUID player1, UUID player2, String arenaName) {
/* 334 */       this.player1 = player1;
/* 335 */       this.player2 = player2;
/* 336 */       this.arenaName = arenaName;
/* 337 */       this.startTime = System.currentTimeMillis();
/* 338 */       this.duelId = player1.toString() + "_" + player1.toString() + "_" + player2.toString();
/*     */     }
/*     */     
/* 341 */     public UUID getPlayer1() { return this.player1; }
/* 342 */     public UUID getPlayer2() { return this.player2; }
/* 343 */     public String getArenaName() { return this.arenaName; }
/* 344 */     public long getStartTime() { return this.startTime; } public String getDuelId() {
/* 345 */       return this.duelId;
/*     */     } }
/*     */   
/*     */   public static class PlayerState {
/*     */     private final Location location;
/*     */     private final GameMode gameMode;
/*     */     
/*     */     public PlayerState(Location location, GameMode gameMode) {
/* 353 */       this.location = location;
/* 354 */       this.gameMode = gameMode;
/*     */     }
/*     */     
/* 357 */     public Location getLocation() { return this.location; } public GameMode getGameMode() {
/* 358 */       return this.gameMode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\duel\DuelManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */