/*    */ package org.ms.donutduels.foliascheduler.folia;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.player.PlayerTeleportEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.ms.donutduels.foliascheduler.AsyncSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.EntitySchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.GlobalSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.RegionSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.ServerImplementation;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public class FoliaServer
/*    */   implements ServerImplementation
/*    */ {
/*    */   @NotNull
/*    */   private final Plugin owningPlugin;
/*    */   
/*    */   @Internal
/*    */   public FoliaServer(@NotNull Plugin owningPlugin) {
/* 29 */     this.owningPlugin = owningPlugin;
/* 30 */     this.globalScheduler = new FoliaGlobalScheduler(owningPlugin);
/* 31 */     this.asyncScheduler = new FoliaAsyncScheduler(owningPlugin);
/*    */   } @NotNull
/*    */   private final FoliaGlobalScheduler globalScheduler; @NotNull
/*    */   private final FoliaAsyncScheduler asyncScheduler; @NotNull
/*    */   public Plugin getOwningPlugin() {
/* 36 */     return this.owningPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOwnedByCurrentRegion(@NotNull Location location) {
/* 41 */     return Bukkit.isOwnedByCurrentRegion(location);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOwnedByCurrentRegion(@NotNull Location location, int squareRadiusChunks) {
/* 46 */     return Bukkit.isOwnedByCurrentRegion(location, squareRadiusChunks);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOwnedByCurrentRegion(@NotNull Block block) {
/* 51 */     return Bukkit.isOwnedByCurrentRegion(block);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOwnedByCurrentRegion(@NotNull World world, int chunkX, int chunkZ) {
/* 56 */     return Bukkit.isOwnedByCurrentRegion(world, chunkX, chunkZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOwnedByCurrentRegion(@NotNull World world, int chunkX, int chunkZ, int squareRadiusChunks) {
/* 61 */     return Bukkit.isOwnedByCurrentRegion(world, chunkX, chunkZ, squareRadiusChunks);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOwnedByCurrentRegion(@NotNull Entity entity) {
/* 66 */     return Bukkit.isOwnedByCurrentRegion(entity);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public GlobalSchedulerImplementation global() {
/* 71 */     return this.globalScheduler;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public AsyncSchedulerImplementation async() {
/* 76 */     return this.asyncScheduler;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public EntitySchedulerImplementation entity(@NotNull Entity entity) {
/* 81 */     return new FoliaEntityScheduler(this.owningPlugin, entity);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public RegionSchedulerImplementation region(@NotNull World world, int chunkX, int chunkZ) {
/* 86 */     return new FoliaRegionScheduler(this.owningPlugin, world, chunkX, chunkZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancelTasks() {
/* 91 */     this.globalScheduler.cancelTasks();
/* 92 */     this.asyncScheduler.cancelTasks();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public CompletableFuture<Boolean> teleportAsync(@NotNull Entity entity, @NotNull Location location, @NotNull PlayerTeleportEvent.TeleportCause cause) {
/* 97 */     return entity.teleportAsync(location, cause);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\folia\FoliaServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */