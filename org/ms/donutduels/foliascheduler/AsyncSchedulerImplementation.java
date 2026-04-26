/*     */ package org.ms.donutduels.foliascheduler;
/*     */ 
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
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
/*     */ public interface AsyncSchedulerImplementation
/*     */ {
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runNow(@NotNull Consumer<TaskImplementation<Void>> consumer) {
/*  25 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/*  29 */     return runNow(wrapperFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runNow(@NotNull Runnable runnable) {
/*  36 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/*  40 */     return runNow(wrapperFunction);
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
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Consumer<TaskImplementation<Void>> consumer, long delay, @NotNull TimeUnit unit) {
/*  70 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/*  74 */     return runDelayed(wrapperFunction, delay, unit);
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
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Runnable runnable, long delay, @NotNull TimeUnit unit) {
/*  90 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/*  94 */     return runDelayed(wrapperFunction, delay, unit);
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
/*     */   @NotNull
/*     */   default <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long ticks) {
/* 109 */     return runDelayed(function, ticks * 50L, TimeUnit.MILLISECONDS);
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
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Consumer<TaskImplementation<Void>> consumer, long ticks) {
/* 124 */     return runDelayed(consumer, ticks * 50L, TimeUnit.MILLISECONDS);
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
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Runnable runnable, long ticks) {
/* 139 */     return runDelayed(runnable, ticks * 50L, TimeUnit.MILLISECONDS);
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
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Consumer<TaskImplementation<Void>> consumer, long delay, long period, @NotNull TimeUnit unit) {
/* 173 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/* 177 */     return runAtFixedRate(wrapperFunction, delay, period, unit);
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
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Runnable runnable, long delay, long period, @NotNull TimeUnit unit) {
/* 195 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/* 199 */     return runAtFixedRate(wrapperFunction, delay, period, unit);
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
/*     */   
/*     */   @NotNull
/*     */   default <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long ticks) {
/* 216 */     return runAtFixedRate(function, delay * 50L, ticks * 50L, TimeUnit.MILLISECONDS);
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
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Consumer<TaskImplementation<Void>> consumer, long delay, long ticks) {
/* 233 */     return runAtFixedRate(consumer, delay * 50L, ticks * 50L, TimeUnit.MILLISECONDS);
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
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Runnable runnable, long delay, long ticks) {
/* 250 */     return runAtFixedRate(runnable, delay * 50L, ticks * 50L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   <T> TaskImplementation<T> runNow(@NotNull Function<TaskImplementation<T>, T> paramFunction);
/*     */   
/*     */   @NotNull
/*     */   <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> paramFunction, long paramLong, @NotNull TimeUnit paramTimeUnit);
/*     */   
/*     */   @NotNull
/*     */   <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> paramFunction, long paramLong1, long paramLong2, @NotNull TimeUnit paramTimeUnit);
/*     */   
/*     */   void cancelTasks();
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\AsyncSchedulerImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */