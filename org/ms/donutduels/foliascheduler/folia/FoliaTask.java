/*    */ package org.ms.donutduels.foliascheduler.folia;
/*    */ 
/*    */ import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public class FoliaTask<T>
/*    */   implements TaskImplementation<T>
/*    */ {
/* 24 */   private final ReentrantLock lock = new ReentrantLock();
/* 25 */   private final AtomicReference<ScheduledTask> scheduledTaskRef = new AtomicReference<>();
/* 26 */   private final CompletableFuture<TaskImplementation<T>> future = new CompletableFuture<>();
/*    */   private T callback;
/*    */   
/*    */   @Internal
/*    */   public void setScheduledTask(@NotNull ScheduledTask task) {
/* 31 */     this.scheduledTaskRef.set(task);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Plugin getOwningPlugin() {
/* 36 */     return ((ScheduledTask)this.scheduledTaskRef.get()).getOwningPlugin();
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 41 */     ((ScheduledTask)this.scheduledTaskRef.get()).cancel();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 46 */     return ((ScheduledTask)this.scheduledTaskRef.get()).isCancelled();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRunning() {
/* 51 */     ScheduledTask.ExecutionState state = ((ScheduledTask)this.scheduledTaskRef.get()).getExecutionState();
/* 52 */     return (state == ScheduledTask.ExecutionState.RUNNING || state == ScheduledTask.ExecutionState.CANCELLED_RUNNING);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRepeatingTask() {
/* 58 */     return ((ScheduledTask)this.scheduledTaskRef.get()).isRepeatingTask();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T getCallback() {
/* 63 */     this.lock.lock();
/*    */     
/*    */     try {
/* 66 */       return this.callback;
/*    */     } finally {
/* 68 */       this.lock.unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Internal
/*    */   public void setCallback(T callback) {
/* 74 */     this.lock.lock();
/*    */     
/*    */     try {
/* 77 */       this.callback = callback;
/*    */     } finally {
/* 79 */       this.lock.unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public CompletableFuture<TaskImplementation<T>> asFuture() {
/* 85 */     return this.future;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\folia\FoliaTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */