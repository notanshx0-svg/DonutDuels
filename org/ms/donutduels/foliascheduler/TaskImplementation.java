package org.ms.donutduels.foliascheduler;

import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TaskImplementation<T> {
  @NotNull
  Plugin getOwningPlugin();
  
  void cancel();
  
  boolean isCancelled();
  
  boolean isRunning();
  
  boolean isRepeatingTask();
  
  @Nullable
  T getCallback();
  
  @NotNull
  CompletableFuture<TaskImplementation<T>> asFuture();
}


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\TaskImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */