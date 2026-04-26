/*     */ package org.ms.donutduels.foliascheduler;
/*     */ 
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ServerImplementation
/*     */ {
/*     */   @NotNull
/*     */   Plugin getOwningPlugin();
/*     */   
/*     */   boolean isOwnedByCurrentRegion(@NotNull Location paramLocation);
/*     */   
/*     */   boolean isOwnedByCurrentRegion(@NotNull Location paramLocation, int paramInt);
/*     */   
/*     */   boolean isOwnedByCurrentRegion(@NotNull Block paramBlock);
/*     */   
/*     */   boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2);
/*     */   
/*     */   boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean isOwnedByCurrentRegion(@NotNull Entity paramEntity);
/*     */   
/*     */   @NotNull
/*     */   GlobalSchedulerImplementation global();
/*     */   
/*     */   @NotNull
/*     */   AsyncSchedulerImplementation async();
/*     */   
/*     */   @NotNull
/*     */   EntitySchedulerImplementation entity(@NotNull Entity paramEntity);
/*     */   
/*     */   @NotNull
/*     */   RegionSchedulerImplementation region(@NotNull World paramWorld, int paramInt1, int paramInt2);
/*     */   
/*     */   @NotNull
/*     */   default RegionSchedulerImplementation region(@NotNull Location location) {
/* 107 */     World world = location.getWorld();
/* 108 */     if (world == null) {
/* 109 */       throw new IllegalArgumentException("Location world cannot be null");
/*     */     }
/* 111 */     return region(world, location.getBlockX() >> 4, location.getBlockZ() >> 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default RegionSchedulerImplementation region(@NotNull Block block) {
/* 119 */     return region(block.getWorld(), block.getX() >> 4, block.getZ() >> 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default RegionSchedulerImplementation region(@NotNull Chunk chunk) {
/* 127 */     return region(chunk.getWorld(), chunk.getX(), chunk.getZ());
/*     */   }
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
/*     */   void cancelTasks();
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
/*     */   @NotNull
/*     */   default CompletableFuture<Boolean> teleportAsync(@NotNull Entity entity, @NotNull Location location) {
/* 155 */     return teleportAsync(entity, location, PlayerTeleportEvent.TeleportCause.PLUGIN);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   CompletableFuture<Boolean> teleportAsync(@NotNull Entity paramEntity, @NotNull Location paramLocation, @NotNull PlayerTeleportEvent.TeleportCause paramTeleportCause);
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\ServerImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */