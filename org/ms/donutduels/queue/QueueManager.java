/*    */ package org.ms.donutduels.queue;
/*    */ import java.util.Map;
/*    */ import java.util.Queue;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.ms.donutduels.DonutDuels;
/*    */ import org.ms.donutduels.gui.QueueGUI;
/*    */ 
/*    */ public class QueueManager {
/* 13 */   private final Queue<UUID> queuedPlayers = new ConcurrentLinkedQueue<>(); private final DonutDuels plugin;
/* 14 */   private final Map<UUID, Long> queueJoinTimes = new ConcurrentHashMap<>();
/*    */   
/*    */   public QueueManager(DonutDuels plugin) {
/* 17 */     this.plugin = plugin;
/*    */ 
/*    */     
/* 20 */     plugin.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)plugin, this::processQueue, 20L, 20L);
/*    */   }
/*    */   
/*    */   public void addPlayerToQueue(Player player) {
/* 24 */     UUID playerId = player.getUniqueId();
/* 25 */     if (!this.queuedPlayers.contains(playerId)) {
/* 26 */       this.queuedPlayers.offer(playerId);
/* 27 */       this.queueJoinTimes.put(playerId, Long.valueOf(System.currentTimeMillis()));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void removePlayerFromQueue(Player player) {
/* 32 */     UUID playerId = player.getUniqueId();
/* 33 */     this.queuedPlayers.remove(playerId);
/* 34 */     this.queueJoinTimes.remove(playerId);
/*    */ 
/*    */     
/* 37 */     Player onlinePlayer = this.plugin.getServer().getPlayer(playerId);
/* 38 */     if (onlinePlayer != null && onlinePlayer.isOnline()) {
/* 39 */       onlinePlayer.stopSound(this.plugin.getQueueGUIConfig().getSearchSound());
/*    */     }
/* 41 */     QueueGUI.stopSearchForPlayer(playerId);
/*    */   }
/*    */   
/*    */   public boolean isPlayerInQueue(Player player) {
/* 45 */     return this.queuedPlayers.contains(player.getUniqueId());
/*    */   }
/*    */   
/*    */   public int getQueueSize() {
/* 49 */     return this.queuedPlayers.size();
/*    */   }
/*    */   
/*    */   public String getEstimatedWaitTime() {
/* 53 */     int queueSize = getQueueSize();
/* 54 */     if (queueSize == 0)
/* 55 */       return "<1 minute"; 
/* 56 */     if (queueSize <= 5)
/* 57 */       return "<1 minute"; 
/* 58 */     if (queueSize <= 10) {
/* 59 */       return "1-2 minutes";
/*    */     }
/* 61 */     return "2-5 minutes";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void processQueue() {
/* 70 */     this.queuedPlayers.removeIf(playerId -> {
/*    */           Player player = this.plugin.getServer().getPlayer(playerId);
/*    */           if (player == null || !player.isOnline()) {
/*    */             this.queueJoinTimes.remove(playerId);
/*    */             if (player != null)
/*    */               player.stopSound(this.plugin.getQueueGUIConfig().getSearchSound()); 
/*    */             QueueGUI.stopSearchForPlayer(playerId);
/*    */             return true;
/*    */           } 
/*    */           return false;
/*    */         });
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\queue\QueueManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */