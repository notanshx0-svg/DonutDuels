/*    */ package org.ms.donutduels.foliascheduler.bukkit;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public class BukkitTask<T>
/*    */   implements TaskImplementation<T>
/*    */ {
/*    */   @NotNull
/*    */   private final Plugin owningPlugin;
/*    */   private final boolean isRepeatingTask;
/*    */   @NotNull
/*    */   private final ReentrantLock lock;
/*    */   
/*    */   @Internal
/*    */   public BukkitTask(@NotNull Plugin owningPlugin, boolean isRepeatingTask) {
/* 25 */     this.owningPlugin = owningPlugin;
/* 26 */     this.isRepeatingTask = isRepeatingTask;
/* 27 */     this.lock = new ReentrantLock();
/* 28 */     this.scheduledTaskRef = new AtomicReference<>();
/* 29 */     this.future = new CompletableFuture<>(); } @NotNull
/*    */   private final AtomicReference<org.bukkit.scheduler.BukkitTask> scheduledTaskRef; @NotNull
/*    */   private final CompletableFuture<TaskImplementation<T>> future; @Nullable
/*    */   private T callback; @Internal
/*    */   public void setScheduledTask(@NotNull org.bukkit.scheduler.BukkitTask task) {
/* 34 */     this.scheduledTaskRef.set(task);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Plugin getOwningPlugin() {
/* 39 */     return this.owningPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 44 */     ((org.bukkit.scheduler.BukkitTask)this.scheduledTaskRef.get()).cancel();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 49 */     return ((org.bukkit.scheduler.BukkitTask)this.scheduledTaskRef.get()).isCancelled();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRunning() {
/* 54 */     return this.owningPlugin.getServer().getScheduler().isCurrentlyRunning(((org.bukkit.scheduler.BukkitTask)this.scheduledTaskRef.get()).getTaskId());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRepeatingTask() {
/* 59 */     return this.isRepeatingTask;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T getCallback() {
/* 64 */     this.lock.lock();
/*    */     try {
/* 66 */       return this.callback;
/*    */     } finally {
/* 68 */       this.lock.unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Internal
/*    */   public void setCallback(@Nullable T callback) {
/* 74 */     this.lock.lock();
/*    */     try {
/* 76 */       this.callback = callback;
/*    */     } finally {
/* 78 */       this.lock.unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public CompletableFuture<TaskImplementation<T>> asFuture() {
/* 84 */     return this.future;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\bukkit\BukkitTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */