/*    */ package org.ms.donutduels.model;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class DuelRequest
/*    */ {
/*    */   private final UUID senderId;
/*    */   private final String senderName;
/*    */   private final UUID targetId;
/*    */   private final String targetName;
/*    */   private final String arena;
/*    */   private final int durationMinutes;
/*    */   private final long timestamp;
/*    */   
/*    */   public DuelRequest(Player sender, Player target, String arena, int durationMinutes) {
/* 17 */     this.senderId = sender.getUniqueId();
/* 18 */     this.senderName = sender.getName();
/* 19 */     this.targetId = target.getUniqueId();
/* 20 */     this.targetName = target.getName();
/* 21 */     this.arena = arena;
/* 22 */     this.durationMinutes = durationMinutes;
/* 23 */     this.timestamp = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public UUID getSenderId() {
/* 27 */     return this.senderId;
/*    */   }
/*    */   
/*    */   public String getSenderName() {
/* 31 */     return this.senderName;
/*    */   }
/*    */   
/*    */   public UUID getTargetId() {
/* 35 */     return this.targetId;
/*    */   }
/*    */   
/*    */   public String getTargetName() {
/* 39 */     return this.targetName;
/*    */   }
/*    */   
/*    */   public String getArena() {
/* 43 */     return this.arena;
/*    */   }
/*    */   
/*    */   public int getDurationMinutes() {
/* 47 */     return this.durationMinutes;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 51 */     return this.timestamp;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isExpired() {
/* 56 */     return (System.currentTimeMillis() - this.timestamp > 60000L);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\model\DuelRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */