/*    */ package org.ms.donutduels.service;
/*    */ 
/*    */ import com.sk89q.worldedit.LocalSession;
/*    */ import com.sk89q.worldedit.WorldEdit;
/*    */ import com.sk89q.worldedit.bukkit.BukkitAdapter;
/*    */ import com.sk89q.worldedit.math.BlockVector3;
/*    */ import com.sk89q.worldedit.regions.Region;
/*    */ import com.sk89q.worldedit.regions.RegionSelector;
/*    */ import com.sk89q.worldedit.session.SessionOwner;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldEditService
/*    */ {
/* 17 */   private final WorldEdit worldEdit = WorldEdit.getInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Location[] getPlayerSelection(Player player) {
/*    */     try {
/* 27 */       LocalSession session = this.worldEdit.getSessionManager().get((SessionOwner)BukkitAdapter.adapt(player));
/* 28 */       RegionSelector selector = session.getRegionSelector(BukkitAdapter.adapt(player.getWorld()));
/*    */       
/* 30 */       if (!selector.isDefined()) {
/* 31 */         return null;
/*    */       }
/*    */       
/* 34 */       Region region = selector.getRegion();
/* 35 */       BlockVector3 min = region.getMinimumPoint();
/* 36 */       BlockVector3 max = region.getMaximumPoint();
/*    */       
/* 38 */       Location pos1 = new Location(player.getWorld(), min.getX(), min.getY(), min.getZ());
/* 39 */       Location pos2 = new Location(player.getWorld(), max.getX(), max.getY(), max.getZ());
/*    */       
/* 41 */       return new Location[] { pos1, pos2 };
/* 42 */     } catch (Exception e) {
/* 43 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasSelection(Player player) {
/* 53 */     return (getPlayerSelection(player) != null);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\service\WorldEditService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */