/*    */ package org.ms.donutduels.stats;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.ms.donutduels.DonutDuels;
/*    */ 
/*    */ public class PlayerStatsManager
/*    */ {
/*    */   private final DonutDuels plugin;
/*    */   
/*    */   public PlayerStatsManager(DonutDuels plugin) {
/* 11 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFormattedWinrate(UUID playerId) {
/* 20 */     double winrate = this.plugin.getDatabase().getWinrate(playerId);
/* 21 */     return String.format("%.2f%%", new Object[] { Double.valueOf(winrate) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void recordWin(UUID playerId) {
/* 29 */     this.plugin.getDatabase().addWin(playerId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void recordLoss(UUID playerId) {
/* 37 */     this.plugin.getDatabase().addLoss(playerId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTotalMatches(UUID playerId) {
/* 46 */     return this.plugin.getDatabase().getWins(playerId) + this.plugin.getDatabase().getLosses(playerId);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\stats\PlayerStatsManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */