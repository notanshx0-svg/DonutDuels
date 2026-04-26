package org.ms.donutduels.database;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;

public interface Database {
  void initialize();
  
  void setPosition(String paramString, Location paramLocation);
  
  Location getPosition(String paramString);
  
  void createArena(String paramString, Location paramLocation1, Location paramLocation2);
  
  boolean arenaExists(String paramString);
  
  boolean hasArenas();
  
  List<String> getAllArenaNames();
  
  void addWin(UUID paramUUID);
  
  void addLoss(UUID paramUUID);
  
  int getWins(UUID paramUUID);
  
  int getLosses(UUID paramUUID);
  
  double getWinrate(UUID paramUUID);
  
  void close();
}


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\database\Database.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */