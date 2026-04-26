/*     */ package org.ms.donutduels.foliascheduler.bukkit;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.AsyncSchedulerImplementation;
/*     */ import org.ms.donutduels.foliascheduler.EntitySchedulerImplementation;
/*     */ import org.ms.donutduels.foliascheduler.GlobalSchedulerImplementation;
/*     */ import org.ms.donutduels.foliascheduler.RegionSchedulerImplementation;
/*     */ import org.ms.donutduels.foliascheduler.ServerImplementation;
/*     */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*     */ import org.ms.donutduels.foliascheduler.util.MethodInvoker;
/*     */ import org.ms.donutduels.foliascheduler.util.ReflectionUtil;
/*     */ import org.ms.donutduels.foliascheduler.util.WrappedReflectiveOperationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public class BukkitServer
/*     */   implements ServerImplementation
/*     */ {
/*     */   @NotNull
/*     */   private final Plugin owningPlugin;
/*     */   @NotNull
/*     */   private final BukkitSyncScheduler sync;
/*     */   
/*     */   @Internal
/*     */   public BukkitServer(@NotNull Plugin owningPlugin) {
/*  39 */     this.owningPlugin = owningPlugin;
/*  40 */     this.sync = new BukkitSyncScheduler(owningPlugin);
/*  41 */     this.region = new BukkitRegionScheduler(owningPlugin);
/*  42 */     this.async = new BukkitAsyncScheduler(owningPlugin);
/*     */     
/*     */     try {
/*  45 */       this.teleportAsyncMethod = ReflectionUtil.getMethod(Entity.class, "teleportAsync", new Class[] { Location.class, PlayerTeleportEvent.TeleportCause.class });
/*  46 */     } catch (WrappedReflectiveOperationException wrappedReflectiveOperationException) {}
/*     */   } @NotNull
/*     */   private final BukkitRegionScheduler region; @NotNull
/*     */   private final BukkitAsyncScheduler async; @Nullable
/*     */   private MethodInvoker teleportAsyncMethod;
/*     */   @NotNull
/*     */   public Plugin getOwningPlugin() {
/*  53 */     return this.owningPlugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location location) {
/*  58 */     return this.owningPlugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location location, int squareRadiusChunks) {
/*  63 */     return this.owningPlugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Block block) {
/*  68 */     return this.owningPlugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World world, int chunkX, int chunkZ) {
/*  73 */     return this.owningPlugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World world, int chunkX, int chunkZ, int squareRadiusChunks) {
/*  78 */     return this.owningPlugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Entity entity) {
/*  83 */     return this.owningPlugin.getServer().isPrimaryThread();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public GlobalSchedulerImplementation global() {
/*  88 */     return this.sync;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public AsyncSchedulerImplementation async() {
/*  93 */     return this.async;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EntitySchedulerImplementation entity(@NotNull Entity entity) {
/*  98 */     return new BukkitEntityScheduler(this.owningPlugin, entity);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public RegionSchedulerImplementation region(@NotNull World world, int chunkX, int chunkZ) {
/* 103 */     return this.region;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelTasks() {
/* 108 */     this.owningPlugin.getServer().getScheduler().cancelTasks(this.owningPlugin);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Boolean> teleportAsync(@NotNull Entity entity, @NotNull Location location, PlayerTeleportEvent.TeleportCause cause) {
/* 114 */     if (this.teleportAsyncMethod != null) {
/* 115 */       Object result = this.teleportAsyncMethod.invoke(entity, new Object[] { location, cause });
/* 116 */       return (CompletableFuture<Boolean>)Objects.<Object>requireNonNull(result);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 121 */     CompletableFuture<Boolean> future = new CompletableFuture<>();
/* 122 */     entity(entity).run(task -> {
/*     */           try {
/*     */             entity.teleport(location);
/*     */             future.complete(Boolean.valueOf(true));
/* 126 */           } catch (Throwable ex) {
/*     */             this.owningPlugin.getLogger().log(Level.SEVERE, "Failed to teleport entity", ex);
/*     */             
/*     */             future.complete(Boolean.valueOf(false));
/*     */           } 
/*     */         });
/* 132 */     return future;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\bukkit\BukkitServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */