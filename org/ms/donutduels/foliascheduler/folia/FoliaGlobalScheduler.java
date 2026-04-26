/*    */ package org.ms.donutduels.foliascheduler.folia;
/*    */ 
/*    */ import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
/*    */ import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.ms.donutduels.foliascheduler.GlobalSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ @Internal
/*    */ public class FoliaGlobalScheduler implements GlobalSchedulerImplementation {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   @NotNull
/*    */   private final GlobalRegionScheduler globalRegionScheduler;
/*    */   
/*    */   @Internal
/*    */   public FoliaGlobalScheduler(@NotNull Plugin plugin) {
/* 22 */     this.plugin = plugin;
/* 23 */     this.globalRegionScheduler = plugin.getServer().getGlobalRegionScheduler();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private <T> Consumer<ScheduledTask> buildFoliaConsumer(@NotNull FoliaTask<T> taskImplementation, @NotNull Function<TaskImplementation<T>, T> callbackFunction) {
/* 30 */     return scheduledTask -> {
/*    */         taskImplementation.setScheduledTask(scheduledTask);
/*    */         taskImplementation.setCallback(callbackFunction.apply(taskImplementation));
/*    */         taskImplementation.asFuture().complete(taskImplementation);
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(@NotNull Runnable run) {
/* 39 */     this.globalRegionScheduler.execute(this.plugin, run);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> function) {
/* 44 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 45 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 46 */     ScheduledTask scheduledTask = this.globalRegionScheduler.run(this.plugin, foliaConsumer);
/* 47 */     taskImplementation.setScheduledTask(scheduledTask);
/* 48 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long delay) {
/* 53 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 54 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 55 */     ScheduledTask scheduledTask = this.globalRegionScheduler.runDelayed(this.plugin, foliaConsumer, delay);
/* 56 */     taskImplementation.setScheduledTask(scheduledTask);
/* 57 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long period) {
/* 62 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 63 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 64 */     ScheduledTask scheduledTask = this.globalRegionScheduler.runAtFixedRate(this.plugin, foliaConsumer, delay, period);
/* 65 */     taskImplementation.setScheduledTask(scheduledTask);
/* 66 */     return taskImplementation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancelTasks() {
/* 71 */     this.globalRegionScheduler.cancelTasks(this.plugin);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\folia\FoliaGlobalScheduler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */