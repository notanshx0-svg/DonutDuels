/*     */ package org.ms.donutduels.database;
/*     */ import com.zaxxer.hikari.HikariConfig;
/*     */ import com.zaxxer.hikari.HikariDataSource;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ 
/*     */ public class SQLiteDatabase implements Database {
/*     */   private final DonutDuels plugin;
/*     */   private HikariDataSource dataSource;
/*     */   
/*     */   public SQLiteDatabase(DonutDuels plugin) {
/*  22 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  27 */     HikariConfig config = new HikariConfig();
/*  28 */     config.setJdbcUrl("jdbc:sqlite:" + (new File(this.plugin.getDataFolder(), "database.db")).getAbsolutePath());
/*  29 */     config.setDriverClassName("org.sqlite.JDBC");
/*  30 */     this.dataSource = new HikariDataSource(config);
/*     */     
/*  32 */     try { Connection conn = getConnection(); 
/*  33 */       try { Statement stmt = conn.createStatement(); 
/*  34 */         try { stmt.execute("CREATE TABLE IF NOT EXISTS positions (type TEXT PRIMARY KEY, world TEXT, x REAL, y REAL, z REAL, yaw REAL, pitch REAL)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  42 */           stmt.execute("CREATE TABLE IF NOT EXISTS arenas (name TEXT PRIMARY KEY, pos1_world TEXT, pos1_x REAL, pos1_y REAL, pos1_z REAL, pos1_yaw REAL, pos1_pitch REAL, pos2_world TEXT, pos2_x REAL, pos2_y REAL, pos2_z REAL, pos2_yaw REAL, pos2_pitch REAL)");
/*     */ 
/*     */ 
/*     */           
/*  46 */           createPlayerStatsTable();
/*  47 */           if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  48 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to create tables", e); }
/*     */   
/*     */   }
/*     */   
/*     */   private void createPlayerStatsTable() {
/*  53 */     String sql = "CREATE TABLE IF NOT EXISTS player_stats (player_id TEXT PRIMARY KEY, wins INTEGER DEFAULT 0, losses INTEGER DEFAULT 0)";
/*     */ 
/*     */ 
/*     */     
/*  57 */     try { Connection conn = this.dataSource.getConnection(); 
/*  58 */       try { Statement stmt = conn.createStatement(); 
/*  59 */         try { stmt.execute(sql);
/*  60 */           if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  61 */     { this.plugin.getLogger().severe("Failed to create player_stats table: " + e.getMessage()); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(String type, Location location) {
/*  67 */     String sql = "INSERT OR REPLACE INTO positions (type, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
/*  68 */     try { Connection conn = getConnection(); 
/*  69 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/*  70 */         try { pstmt.setString(1, type);
/*  71 */           pstmt.setString(2, location.getWorld().getName());
/*  72 */           pstmt.setDouble(3, location.getX());
/*  73 */           pstmt.setDouble(4, location.getY());
/*  74 */           pstmt.setDouble(5, location.getZ());
/*  75 */           pstmt.setFloat(6, location.getYaw());
/*  76 */           pstmt.setFloat(7, location.getPitch());
/*  77 */           pstmt.executeUpdate();
/*  78 */           if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  79 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to set position", e); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getPosition(String type) {
/*  85 */     String sql = "SELECT * FROM positions WHERE type = ?"; 
/*  86 */     try { Connection conn = getConnection(); 
/*  87 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/*  88 */         try { pstmt.setString(1, type);
/*  89 */           ResultSet rs = pstmt.executeQuery();
/*  90 */           if (rs.next())
/*  91 */           { World world = this.plugin.getServer().getWorld(rs.getString("world"));
/*  92 */             if (world != null)
/*     */             
/*  94 */             { Location location = new Location(world, rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch"));
/*     */ 
/*     */               
/*  97 */               if (pstmt != null) pstmt.close();  if (conn != null) conn.close();  return location; }  }  if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  98 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to get position", e); }
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createArena(String name, Location pos1, Location pos2) {
/* 106 */     if (arenaExists(name)) {
/* 107 */       this.plugin.getLogger().warning("Arena '" + name + "' already exists. Use a different name.");
/*     */       
/*     */       return;
/*     */     } 
/* 111 */     String sql = "INSERT INTO arenas (name, pos1_world, pos1_x, pos1_y, pos1_z, pos1_yaw, pos1_pitch, pos2_world, pos2_x, pos2_y, pos2_z, pos2_yaw, pos2_pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */     
/* 113 */     try { Connection conn = getConnection(); 
/* 114 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 115 */         try { pstmt.setString(1, name);
/* 116 */           pstmt.setString(2, pos1.getWorld().getName());
/* 117 */           pstmt.setDouble(3, pos1.getX());
/* 118 */           pstmt.setDouble(4, pos1.getY());
/* 119 */           pstmt.setDouble(5, pos1.getZ());
/* 120 */           pstmt.setFloat(6, pos1.getYaw());
/* 121 */           pstmt.setFloat(7, pos1.getPitch());
/* 122 */           pstmt.setString(8, pos2.getWorld().getName());
/* 123 */           pstmt.setDouble(9, pos2.getX());
/* 124 */           pstmt.setDouble(10, pos2.getY());
/* 125 */           pstmt.setDouble(11, pos2.getZ());
/* 126 */           pstmt.setFloat(12, pos2.getYaw());
/* 127 */           pstmt.setFloat(13, pos2.getPitch());
/* 128 */           pstmt.executeUpdate();
/* 129 */           this.plugin.getLogger().info("Arena '" + name + "' created successfully.");
/* 130 */           if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 131 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to create arena", e); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean arenaExists(String name) {
/* 137 */     String sql = "SELECT COUNT(*) FROM arenas WHERE name = ?"; 
/* 138 */     try { Connection conn = getConnection(); 
/* 139 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 140 */         try { pstmt.setString(1, name);
/* 141 */           ResultSet rs = pstmt.executeQuery();
/* 142 */           if (rs.next())
/* 143 */           { boolean bool = (rs.getInt(1) > 0) ? true : false;
/*     */             
/* 145 */             if (pstmt != null) pstmt.close();  if (conn != null) conn.close();  return bool; }  if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 146 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to check if arena exists", e); }
/*     */     
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAllArenaNames() {
/* 153 */     List<String> arenaNames = new ArrayList<>();
/* 154 */     String sql = "SELECT name FROM arenas ORDER BY name"; 
/* 155 */     try { Connection conn = getConnection(); 
/* 156 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 157 */         try { ResultSet rs = pstmt.executeQuery();
/* 158 */           while (rs.next()) {
/* 159 */             arenaNames.add(rs.getString("name"));
/*     */           }
/* 161 */           if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 162 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to get arena names", e); }
/*     */     
/* 164 */     return arenaNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArenas() {
/* 169 */     String sql = "SELECT COUNT(*) FROM arenas"; 
/* 170 */     try { Connection conn = getConnection(); 
/* 171 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 172 */         try { ResultSet rs = pstmt.executeQuery();
/* 173 */           if (rs.next())
/* 174 */           { boolean bool = (rs.getInt(1) > 0) ? true : false;
/*     */             
/* 176 */             if (pstmt != null) pstmt.close();  if (conn != null) conn.close();  return bool; }  if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 177 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to check arenas", e); }
/*     */     
/* 179 */     return false;
/*     */   }
/*     */   
/*     */   public void addWin(UUID playerId) {
/*     */     
/* 184 */     try { Connection conn = this.dataSource.getConnection();
/*     */       
/* 186 */       try { String insertSql = "INSERT OR IGNORE INTO player_stats (player_id, wins, losses) VALUES (?, 0, 0)";
/* 187 */         PreparedStatement insertStmt = conn.prepareStatement(insertSql); 
/* 188 */         try { insertStmt.setString(1, playerId.toString());
/* 189 */           insertStmt.executeUpdate();
/* 190 */           if (insertStmt != null) insertStmt.close();  } catch (Throwable throwable) { if (insertStmt != null)
/*     */             try { insertStmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */               throw throwable; }
/* 193 */          String updateSql = "UPDATE player_stats SET wins = wins + 1 WHERE player_id = ?";
/* 194 */         PreparedStatement updateStmt = conn.prepareStatement(updateSql); 
/* 195 */         try { updateStmt.setString(1, playerId.toString());
/* 196 */           updateStmt.executeUpdate();
/* 197 */           if (updateStmt != null) updateStmt.close();  } catch (Throwable throwable) { if (updateStmt != null)
/* 198 */             try { updateStmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 199 */     { this.plugin.getLogger().severe("Failed to add win for player: " + e.getMessage()); }
/*     */   
/*     */   }
/*     */   
/*     */   public void addLoss(UUID playerId) {
/*     */     
/* 205 */     try { Connection conn = this.dataSource.getConnection();
/*     */       
/* 207 */       try { String insertSql = "INSERT OR IGNORE INTO player_stats (player_id, wins, losses) VALUES (?, 0, 0)";
/* 208 */         PreparedStatement insertStmt = conn.prepareStatement(insertSql); 
/* 209 */         try { insertStmt.setString(1, playerId.toString());
/* 210 */           insertStmt.executeUpdate();
/* 211 */           if (insertStmt != null) insertStmt.close();  } catch (Throwable throwable) { if (insertStmt != null)
/*     */             try { insertStmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */               throw throwable; }
/* 214 */          String updateSql = "UPDATE player_stats SET losses = losses + 1 WHERE player_id = ?";
/* 215 */         PreparedStatement updateStmt = conn.prepareStatement(updateSql); 
/* 216 */         try { updateStmt.setString(1, playerId.toString());
/* 217 */           updateStmt.executeUpdate();
/* 218 */           if (updateStmt != null) updateStmt.close();  } catch (Throwable throwable) { if (updateStmt != null)
/* 219 */             try { updateStmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 220 */     { this.plugin.getLogger().severe("Failed to add loss for player: " + e.getMessage()); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWins(UUID playerId) {
/* 226 */     String sql = "SELECT wins FROM player_stats WHERE player_id = ?"; 
/* 227 */     try { Connection conn = this.dataSource.getConnection(); 
/* 228 */       try { PreparedStatement stmt = conn.prepareStatement(sql); 
/* 229 */         try { stmt.setString(1, playerId.toString());
/* 230 */           ResultSet rs = stmt.executeQuery(); 
/* 231 */           try { if (rs.next())
/* 232 */             { int i = rs.getInt("wins");
/*     */               
/* 234 */               if (rs != null) rs.close(); 
/* 235 */               if (stmt != null) stmt.close();  if (conn != null) conn.close();  return i; }  if (rs != null) rs.close();  } catch (Throwable throwable) { if (rs != null) try { rs.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 236 */     { this.plugin.getLogger().severe("Failed to get wins for player: " + e.getMessage()); }
/*     */     
/* 238 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLosses(UUID playerId) {
/* 243 */     String sql = "SELECT losses FROM player_stats WHERE player_id = ?"; 
/* 244 */     try { Connection conn = this.dataSource.getConnection(); 
/* 245 */       try { PreparedStatement stmt = conn.prepareStatement(sql); 
/* 246 */         try { stmt.setString(1, playerId.toString());
/* 247 */           ResultSet rs = stmt.executeQuery(); 
/* 248 */           try { if (rs.next())
/* 249 */             { int i = rs.getInt("losses");
/*     */               
/* 251 */               if (rs != null) rs.close(); 
/* 252 */               if (stmt != null) stmt.close();  if (conn != null) conn.close();  return i; }  if (rs != null) rs.close();  } catch (Throwable throwable) { if (rs != null) try { rs.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 253 */     { this.plugin.getLogger().severe("Failed to get losses for player: " + e.getMessage()); }
/*     */     
/* 255 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWinrate(UUID playerId) {
/* 260 */     int wins = getWins(playerId);
/* 261 */     int losses = getLosses(playerId);
/* 262 */     int totalMatches = wins + losses;
/*     */     
/* 264 */     if (totalMatches == 0) {
/* 265 */       return 0.0D;
/*     */     }
/*     */     
/* 268 */     return wins / totalMatches * 100.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 273 */     if (this.dataSource != null) {
/* 274 */       this.dataSource.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private Connection getConnection() throws SQLException {
/* 279 */     return this.dataSource.getConnection();
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\database\SQLiteDatabase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */