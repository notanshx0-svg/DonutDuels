/*    */ package org.ms.donutduels.foliascheduler.folia;
/*    */ 
/*    */ import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
/*    */ import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.ms.donutduels.foliascheduler.RegionSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ 
/*    */ public class FoliaRegionScheduler
/*    */   implements RegionSchedulerImplementation
/*    */ {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   @NotNull
/*    */   private final RegionScheduler regionScheduler;
/*    */   
/*    */   public FoliaRegionScheduler(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ) {
/* 23 */     this.plugin = plugin;
/* 24 */     this.regionScheduler = plugin.getServer().getRegionScheduler();
/* 25 */     this.world = world;
/* 26 */     this.chunkX = chunkX;
/* 27 */     this.chunkZ = chunkZ;
/*    */   }
/*    */   @NotNull
/*    */   private final World world; private final int chunkX; private final int chunkZ;
/*    */   
/*    */   @NotNull
/*    */   private <T> Consumer<ScheduledTask> buildFoliaConsumer(@NotNull FoliaTask<T> taskImplementation, @NotNull Function<TaskImplementation<T>, T> callbackFunction) {
/* 34 */     return scheduledTask -> {
/*    */         taskImplementation.setScheduledTask(scheduledTask);
/*    */         taskImplementation.setCallback(callbackFunction.apply(taskImplementation));
/*    */         taskImplementation.asFuture().complete(taskImplementation);
/*    */       };
/*    */   }
/*    */   
/*    */   public void execute(@NotNull Runnable run) {
/* 42 */     this.regionScheduler.execute(this.plugin, this.world, this.chunkX, this.chunkZ, run);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> function) {
/* 47 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 48 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 49 */     ScheduledTask scheduledTask = this.regionScheduler.run(this.plugin, this.world, this.chunkX, this.chunkZ, foliaConsumer);
/* 50 */     taskImplementation.setScheduledTask(scheduledTask);
/* 51 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long delay) {
/* 56 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 57 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 58 */     ScheduledTask scheduledTask = this.regionScheduler.runDelayed(this.plugin, this.world, this.chunkX, this.chunkZ, foliaConsumer, delay);
/* 59 */     taskImplementation.setScheduledTask(scheduledTask);
/* 60 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long period) {
/* 65 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 66 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 67 */     ScheduledTask scheduledTask = this.regionScheduler.runAtFixedRate(this.plugin, this.world, this.chunkX, this.chunkZ, foliaConsumer, delay, period);
/* 68 */     taskImplementation.setScheduledTask(scheduledTask);
/* 69 */     return taskImplementation;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\folia\FoliaRegionScheduler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */