/*    */ package org.ms.donutduels.service;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ArenaSelectionManager
/*    */ {
/* 10 */   private final Map<UUID, String> selectedArenas = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSelectedArena(Player player, String arenaName) {
/* 18 */     this.selectedArenas.put(player.getUniqueId(), arenaName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSelectedArena(Player player) {
/* 27 */     return this.selectedArenas.get(player.getUniqueId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasSelectedArena(Player player) {
/* 36 */     return this.selectedArenas.containsKey(player.getUniqueId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearSelectedArena(Player player) {
/* 44 */     this.selectedArenas.remove(player.getUniqueId());
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\service\ArenaSelectionManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */