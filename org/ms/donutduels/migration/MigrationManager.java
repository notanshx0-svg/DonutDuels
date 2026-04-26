/*     */ package org.ms.donutduels.migration;
/*     */ 
/*     */ import com.zaxxer.hikari.HikariConfig;
/*     */ import com.zaxxer.hikari.HikariDataSource;
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ import org.ms.donutduels.config.ConfigManager;
/*     */ import org.ms.donutduels.database.Database;
/*     */ 
/*     */ 
/*     */ public class MigrationManager
/*     */ {
/*     */   private final DonutDuels plugin;
/*     */   private final ConfigManager configManager;
/*     */   
/*     */   public MigrationManager(DonutDuels plugin) {
/*  26 */     this.plugin = plugin;
/*  27 */     this.configManager = plugin.getConfigManager();
/*     */   }
/*     */   
/*     */   public boolean migrateArenas() {
/*  31 */     HikariDataSource oldDataSource = null;
/*     */     
/*     */     try {
/*  34 */       String oldDbType = this.configManager.getOldDatabaseType();
/*  35 */       if (oldDbType.equals("mysql")) {
/*  36 */         HikariConfig config = new HikariConfig();
/*  37 */         config.setJdbcUrl("jdbc:mysql://" + this.configManager.getOldMySQLHost() + ":" + this.configManager
/*  38 */             .getOldMySQLPort() + "/" + this.configManager.getOldMySQLDatabase() + "?useSSL=false&allowPublicKeyRetrieval=true");
/*     */         
/*  40 */         config.setUsername(this.configManager.getOldMySQLUser());
/*  41 */         config.setPassword(this.configManager.getOldMySQLPassword());
/*  42 */         config.setDriverClassName("com.mysql.cj.jdbc.Driver");
/*  43 */         oldDataSource = new HikariDataSource(config);
/*     */       } else {
/*  45 */         HikariConfig config = new HikariConfig();
/*  46 */         config.setJdbcUrl("jdbc:sqlite:" + (new File(this.configManager.getOldSQLitePath())).getAbsolutePath());
/*  47 */         config.setDriverClassName("org.sqlite.JDBC");
/*  48 */         oldDataSource = new HikariDataSource(config);
/*     */       } 
/*     */ 
/*     */       
/*  52 */       List<ArenaMigrationData> arenas = loadOldArenas(oldDataSource);
/*  53 */       if (arenas.isEmpty()) {
/*  54 */         this.plugin.getLogger().info("No arenas found in old database to migrate.");
/*  55 */         return false;
/*     */       } 
/*     */ 
/*     */       
/*  59 */       Database newDatabase = this.plugin.getDatabase();
/*  60 */       for (ArenaMigrationData arena : arenas) {
/*  61 */         newDatabase.createArena(arena.name, arena.pos1, arena.pos2);
/*     */       }
/*  63 */       this.plugin.getLogger().info("Successfully migrated " + arenas.size() + " arenas.");
/*  64 */       return true;
/*  65 */     } catch (SQLException e) {
/*  66 */       this.plugin.getLogger().log(Level.SEVERE, "Failed to migrate arenas", e);
/*  67 */       return false;
/*     */     } finally {
/*  69 */       if (oldDataSource != null) {
/*  70 */         oldDataSource.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<ArenaMigrationData> loadOldArenas(HikariDataSource oldDataSource) throws SQLException {
/*  76 */     List<ArenaMigrationData> arenas = new ArrayList<>();
/*  77 */     String sql = "SELECT name, world, pos1_x, pos1_y, pos1_z, pos2_x, pos2_y, pos2_z FROM arenas";
/*  78 */     Connection conn = oldDataSource.getConnection(); 
/*  79 */     try { PreparedStatement stmt = conn.prepareStatement(sql); 
/*  80 */       try { ResultSet rs = stmt.executeQuery(); 
/*  81 */         try { while (rs.next()) {
/*  82 */             String name = rs.getString("name");
/*  83 */             String worldName = rs.getString("world");
/*  84 */             World world = this.plugin.getServer().getWorld(worldName);
/*  85 */             if (world == null) {
/*  86 */               this.plugin.getLogger().warning("World '" + worldName + "' not found for arena '" + name + "'. Skipping.");
/*     */               continue;
/*     */             } 
/*  89 */             Location pos1 = new Location(world, rs.getDouble("pos1_x"), rs.getDouble("pos1_y"), rs.getDouble("pos1_z"));
/*  90 */             Location pos2 = new Location(world, rs.getDouble("pos2_x"), rs.getDouble("pos2_y"), rs.getDouble("pos2_z"));
/*  91 */             arenas.add(new ArenaMigrationData(name, pos1, pos2));
/*     */           } 
/*  93 */           if (rs != null) rs.close();  } catch (Throwable throwable) { if (rs != null) try { rs.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null)
/*  94 */         try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return arenas;
/*     */   }
/*     */   
/*     */   private static class ArenaMigrationData {
/*     */     String name;
/*     */     Location pos1;
/*     */     Location pos2;
/*     */     
/*     */     ArenaMigrationData(String name, Location pos1, Location pos2) {
/* 103 */       this.name = name;
/* 104 */       this.pos1 = pos1;
/* 105 */       this.pos2 = pos2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\migration\MigrationManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */