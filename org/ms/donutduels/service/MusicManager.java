/*     */ package org.ms.donutduels.service;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*     */ 
/*     */ 
/*     */ public class MusicManager
/*     */ {
/*     */   private final DonutDuels plugin;
/*  17 */   private final Map<UUID, TaskImplementation<Void>> activeMusicTasks = new HashMap<>();
/*  18 */   private final Random random = new Random();
/*     */ 
/*     */   
/*  21 */   private final Sound[] MUSIC_DISCS = new Sound[] { Sound.MUSIC_DISC_BLOCKS, Sound.MUSIC_DISC_CAT, Sound.MUSIC_DISC_CHIRP, Sound.MUSIC_DISC_FAR, Sound.MUSIC_DISC_MALL, Sound.MUSIC_DISC_MELLOHI, Sound.MUSIC_DISC_STAL, Sound.MUSIC_DISC_STRAD, Sound.MUSIC_DISC_WARD, Sound.MUSIC_DISC_11, Sound.MUSIC_DISC_WAIT };
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
/*     */   public MusicManager(DonutDuels plugin) {
/*  36 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playVictoryMusic(Player winner) {
/*  45 */     stopMusic(winner);
/*     */ 
/*     */     
/*  48 */     Sound musicToPlay = Sound.MUSIC_DISC_BLOCKS;
/*     */ 
/*     */     
/*  51 */     TaskImplementation<Void> musicTask = this.plugin.getSchedulerService().runEntity((Entity)winner, task -> {
/*     */           if (winner.isOnline()) {
/*     */             winner.playSound(winner.getLocation(), musicToPlay, 0.7F, 1.0F);
/*     */           } else {
/*     */             task.cancel();
/*     */ 
/*     */             
/*     */             this.activeMusicTasks.remove(winner.getUniqueId());
/*     */           } 
/*     */         });
/*     */     
/*  62 */     TaskImplementation<Void> repeatingMusicTask = this.plugin.getSchedulerService().runGlobalRepeating(task -> { if (winner.isOnline()) { winner.playSound(winner.getLocation(), musicToPlay, 0.7F, 1.0F); } else { task.cancel(); this.activeMusicTasks.remove(winner.getUniqueId()); }  }0L, 20L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     this.activeMusicTasks.put(winner.getUniqueId(), repeatingMusicTask);
/*     */ 
/*     */     
/*  75 */     winner.sendMessage("§6§l🎵 Victory music is now playing! §6§l🎵");
/*  76 */     winner.sendActionBar("§e♪ ♫ Victory Music Playing ♫ ♪");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopMusic(Player player) {
/*  84 */     TaskImplementation<Void> task = this.activeMusicTasks.remove(player.getUniqueId());
/*  85 */     if (task != null) {
/*  86 */       task.cancel();
/*  87 */       player.sendActionBar("§7Music stopped.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopMusic(UUID playerId) {
/*  96 */     TaskImplementation<Void> task = this.activeMusicTasks.remove(playerId);
/*  97 */     if (task != null) {
/*  98 */       task.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMusicPlaying(Player player) {
/* 108 */     return this.activeMusicTasks.containsKey(player.getUniqueId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopAllMusic() {
/* 115 */     for (TaskImplementation<Void> task : this.activeMusicTasks.values()) {
/* 116 */       task.cancel();
/*     */     }
/* 118 */     this.activeMusicTasks.clear();
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\service\MusicManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */