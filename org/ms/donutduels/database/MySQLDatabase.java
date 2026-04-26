/*     */ package org.ms.donutduels.database;
/*     */ import com.zaxxer.hikari.HikariConfig;
/*     */ import com.zaxxer.hikari.HikariDataSource;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ 
/*     */ public class MySQLDatabase implements Database {
/*     */   private final DonutDuels plugin;
/*     */   private HikariDataSource dataSource;
/*     */   
/*     */   public MySQLDatabase(DonutDuels plugin, String host, int port, String database, String user, String password) {
/*  21 */     this.plugin = plugin;
/*  22 */     HikariConfig config = new HikariConfig();
/*  23 */     config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true");
/*  24 */     config.setUsername(user);
/*  25 */     config.setPassword(password);
/*  26 */     config.setDriverClassName("com.mysql.cj.jdbc.Driver");
/*  27 */     this.dataSource = new HikariDataSource(config);
/*     */   }
/*     */   
/*     */   public void initialize() {
/*     */     
/*  32 */     try { Connection conn = getConnection(); 
/*  33 */       try { Statement stmt = conn.createStatement(); 
/*  34 */         try { stmt.execute("CREATE TABLE IF NOT EXISTS positions (type VARCHAR(4) PRIMARY KEY, world VARCHAR(255), x DOUBLE, y DOUBLE, z DOUBLE, yaw FLOAT, pitch FLOAT)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  42 */           stmt.execute("CREATE TABLE IF NOT EXISTS arenas (name VARCHAR(255) PRIMARY KEY, pos1_world VARCHAR(255), pos1_x DOUBLE, pos1_y DOUBLE, pos1_z DOUBLE, pos1_yaw FLOAT, pos1_pitch FLOAT, pos2_world VARCHAR(255), pos2_x DOUBLE, pos2_y DOUBLE, pos2_z DOUBLE, pos2_yaw FLOAT, pos2_pitch FLOAT)");
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
/*  53 */     String sql = "CREATE TABLE IF NOT EXISTS player_stats (player_id VARCHAR(36) PRIMARY KEY, wins INT DEFAULT 0, losses INT DEFAULT 0)";
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
/*  67 */     String sql = "INSERT INTO positions (type, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE world=?, x=?, y=?, z=?, yaw=?, pitch=?";
/*     */     
/*  69 */     try { Connection conn = getConnection(); 
/*  70 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/*  71 */         try { pstmt.setString(1, type);
/*  72 */           pstmt.setString(2, location.getWorld().getName());
/*  73 */           pstmt.setDouble(3, location.getX());
/*  74 */           pstmt.setDouble(4, location.getY());
/*  75 */           pstmt.setDouble(5, location.getZ());
/*  76 */           pstmt.setFloat(6, location.getYaw());
/*  77 */           pstmt.setFloat(7, location.getPitch());
/*  78 */           pstmt.setString(8, location.getWorld().getName());
/*  79 */           pstmt.setDouble(9, location.getX());
/*  80 */           pstmt.setDouble(10, location.getY());
/*  81 */           pstmt.setDouble(11, location.getZ());
/*  82 */           pstmt.setFloat(12, location.getYaw());
/*  83 */           pstmt.setFloat(13, location.getPitch());
/*  84 */           pstmt.executeUpdate();
/*  85 */           if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  86 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to set position", e); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getPosition(String type) {
/*  92 */     String sql = "SELECT * FROM positions WHERE type = ?"; 
/*  93 */     try { Connection conn = getConnection(); 
/*  94 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/*  95 */         try { pstmt.setString(1, type);
/*  96 */           ResultSet rs = pstmt.executeQuery();
/*  97 */           if (rs.next())
/*  98 */           { World world = this.plugin.getServer().getWorld(rs.getString("world"));
/*  99 */             if (world != null)
/*     */             
/* 101 */             { Location location = new Location(world, rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch"));
/*     */ 
/*     */               
/* 104 */               if (pstmt != null) pstmt.close();  if (conn != null) conn.close();  return location; }  }  if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 105 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to get position", e); }
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createArena(String name, Location pos1, Location pos2) {
/* 113 */     if (arenaExists(name)) {
/* 114 */       this.plugin.getLogger().log(Level.WARNING, "Arena '" + name + "' already exists. Skipping creation.");
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     String sql = "INSERT INTO arenas (name, pos1_world, pos1_x, pos1_y, pos1_z, pos1_yaw, pos1_pitch, pos2_world, pos2_x, pos2_y, pos2_z, pos2_yaw, pos2_pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */     
/* 120 */     try { Connection conn = getConnection(); 
/* 121 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 122 */         try { pstmt.setString(1, name);
/* 123 */           pstmt.setString(2, pos1.getWorld().getName());
/* 124 */           pstmt.setDouble(3, pos1.getX());
/* 125 */           pstmt.setDouble(4, pos1.getY());
/* 126 */           pstmt.setDouble(5, pos1.getZ());
/* 127 */           pstmt.setFloat(6, pos1.getYaw());
/* 128 */           pstmt.setFloat(7, pos1.getPitch());
/* 129 */           pstmt.setString(8, pos2.getWorld().getName());
/* 130 */           pstmt.setDouble(9, pos2.getX());
/* 131 */           pstmt.setDouble(10, pos2.getY());
/* 132 */           pstmt.setDouble(11, pos2.getZ());
/* 133 */           pstmt.setFloat(12, pos2.getYaw());
/* 134 */           pstmt.setFloat(13, pos2.getPitch());
/* 135 */           pstmt.executeUpdate();
/* 136 */           if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 137 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to create arena", e); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAllArenaNames() {
/* 143 */     List<String> arenaNames = new ArrayList<>();
/* 144 */     String sql = "SELECT name FROM arenas ORDER BY name"; 
/* 145 */     try { Connection conn = getConnection(); 
/* 146 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 147 */         try { ResultSet rs = pstmt.executeQuery();
/* 148 */           while (rs.next()) {
/* 149 */             arenaNames.add(rs.getString("name"));
/*     */           }
/* 151 */           if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 152 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to get arena names", e); }
/*     */     
/* 154 */     return arenaNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean arenaExists(String name) {
/* 159 */     String sql = "SELECT COUNT(*) FROM arenas WHERE name = ?"; 
/* 160 */     try { Connection conn = getConnection(); 
/* 161 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 162 */         try { pstmt.setString(1, name);
/* 163 */           ResultSet rs = pstmt.executeQuery();
/* 164 */           if (rs.next())
/* 165 */           { boolean bool = (rs.getInt(1) > 0) ? true : false;
/*     */             
/* 167 */             if (pstmt != null) pstmt.close();  if (conn != null) conn.close();  return bool; }  if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 168 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to check if arena exists", e); }
/*     */     
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArenas() {
/* 175 */     String sql = "SELECT COUNT(*) FROM arenas"; 
/* 176 */     try { Connection conn = getConnection(); 
/* 177 */       try { PreparedStatement pstmt = conn.prepareStatement(sql); 
/* 178 */         try { ResultSet rs = pstmt.executeQuery();
/* 179 */           if (rs.next())
/* 180 */           { boolean bool = (rs.getInt(1) > 0) ? true : false;
/*     */             
/* 182 */             if (pstmt != null) pstmt.close();  if (conn != null) conn.close();  return bool; }  if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 183 */     { this.plugin.getLogger().log(Level.SEVERE, "Failed to check arenas", e); }
/*     */     
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWin(UUID playerId) {
/* 190 */     String sql = "INSERT INTO player_stats (player_id, wins, losses) VALUES (?, 1, 0) ON DUPLICATE KEY UPDATE wins = wins + 1";
/*     */     
/* 192 */     try { Connection conn = this.dataSource.getConnection(); 
/* 193 */       try { PreparedStatement stmt = conn.prepareStatement(sql); 
/* 194 */         try { stmt.setString(1, playerId.toString());
/* 195 */           stmt.executeUpdate();
/* 196 */           if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 197 */     { this.plugin.getLogger().severe("Failed to add win for player: " + e.getMessage()); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void addLoss(UUID playerId) {
/* 203 */     String sql = "INSERT INTO player_stats (player_id, wins, losses) VALUES (?, 0, 1) ON DUPLICATE KEY UPDATE losses = losses + 1";
/*     */     
/* 205 */     try { Connection conn = this.dataSource.getConnection(); 
/* 206 */       try { PreparedStatement stmt = conn.prepareStatement(sql); 
/* 207 */         try { stmt.setString(1, playerId.toString());
/* 208 */           stmt.executeUpdate();
/* 209 */           if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 210 */     { this.plugin.getLogger().severe("Failed to add loss for player: " + e.getMessage()); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWins(UUID playerId) {
/* 216 */     String sql = "SELECT wins FROM player_stats WHERE player_id = ?"; 
/* 217 */     try { Connection conn = this.dataSource.getConnection(); 
/* 218 */       try { PreparedStatement stmt = conn.prepareStatement(sql); 
/* 219 */         try { stmt.setString(1, playerId.toString());
/* 220 */           ResultSet rs = stmt.executeQuery(); 
/* 221 */           try { if (rs.next())
/* 222 */             { int i = rs.getInt("wins");
/*     */               
/* 224 */               if (rs != null) rs.close(); 
/* 225 */               if (stmt != null) stmt.close();  if (conn != null) conn.close();  return i; }  if (rs != null) rs.close();  } catch (Throwable throwable) { if (rs != null) try { rs.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 226 */     { this.plugin.getLogger().severe("Failed to get wins for player: " + e.getMessage()); }
/*     */     
/* 228 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLosses(UUID playerId) {
/* 233 */     String sql = "SELECT losses FROM player_stats WHERE player_id = ?"; 
/* 234 */     try { Connection conn = this.dataSource.getConnection(); 
/* 235 */       try { PreparedStatement stmt = conn.prepareStatement(sql); 
/* 236 */         try { stmt.setString(1, playerId.toString());
/* 237 */           ResultSet rs = stmt.executeQuery(); 
/* 238 */           try { if (rs.next())
/* 239 */             { int i = rs.getInt("losses");
/*     */               
/* 241 */               if (rs != null) rs.close(); 
/* 242 */               if (stmt != null) stmt.close();  if (conn != null) conn.close();  return i; }  if (rs != null) rs.close();  } catch (Throwable throwable) { if (rs != null) try { rs.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null) try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 243 */     { this.plugin.getLogger().severe("Failed to get losses for player: " + e.getMessage()); }
/*     */     
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWinrate(UUID playerId) {
/* 250 */     int wins = getWins(playerId);
/* 251 */     int losses = getLosses(playerId);
/* 252 */     int totalMatches = wins + losses;
/*     */     
/* 254 */     if (totalMatches == 0) {
/* 255 */       return 0.0D;
/*     */     }
/*     */     
/* 258 */     return wins / totalMatches * 100.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 263 */     if (this.dataSource != null) {
/* 264 */       this.dataSource.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private Connection getConnection() throws SQLException {
/* 269 */     return this.dataSource.getConnection();
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\database\MySQLDatabase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */