/*     */ package org.ms.donutduels.service;
/*     */ 
/*     */ import com.sk89q.worldedit.EditSession;
/*     */ import com.sk89q.worldedit.WorldEdit;
/*     */ import com.sk89q.worldedit.bukkit.BukkitAdapter;
/*     */ import com.sk89q.worldedit.extent.Extent;
/*     */ import com.sk89q.worldedit.extent.clipboard.Clipboard;
/*     */ import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
/*     */ import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
/*     */ import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
/*     */ import com.sk89q.worldedit.function.operation.Operation;
/*     */ import com.sk89q.worldedit.function.operation.Operations;
/*     */ import com.sk89q.worldedit.math.BlockVector3;
/*     */ import com.sk89q.worldedit.session.ClipboardHolder;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ 
/*     */ public class SchematicManager
/*     */ {
/*     */   private final DonutDuels plugin;
/*     */   private final File schematicsFolder;
/*     */   
/*     */   public SchematicManager(DonutDuels plugin) {
/*  32 */     this.plugin = plugin;
/*  33 */     this.schematicsFolder = new File(plugin.getDataFolder(), "schematics");
/*     */ 
/*     */     
/*  36 */     if (!this.schematicsFolder.exists()) {
/*  37 */       this.schematicsFolder.mkdirs();
/*  38 */       plugin.getLogger().info("Created schematics folder at: " + this.schematicsFolder.getAbsolutePath());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadAllSchematics() {
/*  46 */     File[] schematicFiles = this.schematicsFolder.listFiles((dir, name) -> 
/*  47 */         (name.toLowerCase().endsWith(".schem") || name.toLowerCase().endsWith(".schematic")));
/*     */     
/*  49 */     if (schematicFiles == null || schematicFiles.length == 0) {
/*  50 */       this.plugin.getLogger().info("No schematic files found in schematics folder.");
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     World world = Bukkit.getWorlds().get(0);
/*  55 */     int loadedCount = 0;
/*     */     
/*  57 */     for (File schematicFile : schematicFiles) {
/*  58 */       String arenaName = getArenaNameFromFile(schematicFile);
/*     */ 
/*     */       
/*  61 */       if (this.plugin.getDatabase().arenaExists(arenaName)) {
/*  62 */         this.plugin.getLogger().info("Arena '" + arenaName + "' already exists, skipping schematic load.");
/*     */ 
/*     */       
/*     */       }
/*  66 */       else if (loadSchematicAsArena(schematicFile, arenaName, world)) {
/*  67 */         loadedCount++;
/*  68 */         this.plugin.getLogger().info("Successfully loaded schematic '" + schematicFile.getName() + "' as arena '" + arenaName + "'");
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     this.plugin.getLogger().info("Loaded " + loadedCount + " schematic arenas.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean loadSchematicAsArena(File schematicFile, String arenaName, World world) {
/*     */     try {
/*     */       Clipboard clipboard;
/*  81 */       ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
/*  82 */       if (format == null) {
/*  83 */         this.plugin.getLogger().warning("Unknown schematic format for file: " + schematicFile.getName());
/*  84 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  89 */       ClipboardReader reader = format.getReader(new FileInputStream(schematicFile)); 
/*  90 */       try { clipboard = reader.read();
/*  91 */         if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null)
/*     */           try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/*  94 */        Location pasteLocation = findSuitableLocation(world, clipboard);
/*     */ 
/*     */       
/*  97 */       EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world));
/*     */ 
/*     */ 
/*     */       
/* 101 */       try { Operation operation = (new ClipboardHolder(clipboard)).createPaste((Extent)editSession).to(BlockVector3.at(pasteLocation.getX(), pasteLocation.getY(), pasteLocation.getZ())).build();
/* 102 */         Operations.complete(operation);
/* 103 */         if (editSession != null) editSession.close();  } catch (Throwable throwable) { if (editSession != null)
/*     */           try { editSession.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/* 106 */        BlockVector3 schematicSize = clipboard.getDimensions();
/* 107 */       Location pos1 = pasteLocation.clone();
/* 108 */       Location pos2 = pasteLocation.clone().add(schematicSize.getX(), schematicSize.getY(), schematicSize.getZ());
/*     */ 
/*     */       
/* 111 */       pos1.add(-5.0D, 0.0D, -5.0D);
/* 112 */       pos2.add(5.0D, 10.0D, 5.0D);
/*     */ 
/*     */       
/* 115 */       Location spawn1 = pasteLocation.clone().add(schematicSize.getX() * 0.25D, 1.0D, schematicSize.getZ() * 0.25D);
/* 116 */       Location spawn2 = pasteLocation.clone().add(schematicSize.getX() * 0.75D, 1.0D, schematicSize.getZ() * 0.75D);
/*     */ 
/*     */       
/* 119 */       this.plugin.getDatabase().createArena(arenaName, spawn1, spawn2);
/*     */       
/* 121 */       return true;
/*     */     }
/* 123 */     catch (IOException e) {
/* 124 */       this.plugin.getLogger().log(Level.SEVERE, "Failed to load schematic: " + schematicFile.getName(), e);
/* 125 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Location findSuitableLocation(World world, Clipboard clipboard) {
/* 133 */     BlockVector3 size = clipboard.getDimensions();
/*     */ 
/*     */     
/* 136 */     int spacing = Math.max(100, Math.max(size.getX(), size.getZ()) + 50);
/*     */ 
/*     */     
/* 139 */     List<String> existingArenas = this.plugin.getDatabase().getAllArenaNames();
/* 140 */     int arenaCount = existingArenas.size();
/*     */ 
/*     */     
/* 143 */     int gridSize = 5;
/* 144 */     int x = arenaCount % gridSize * spacing;
/* 145 */     int z = arenaCount / gridSize * spacing;
/*     */ 
/*     */     
/* 148 */     int baseX = 10000;
/* 149 */     int baseZ = 10000;
/*     */     
/* 151 */     return new Location(world, (baseX + x), 100.0D, (baseZ + z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getArenaNameFromFile(File file) {
/* 158 */     String fileName = file.getName();
/*     */     
/* 160 */     int lastDot = fileName.lastIndexOf('.');
/* 161 */     if (lastDot > 0) {
/* 162 */       fileName = fileName.substring(0, lastDot);
/*     */     }
/*     */ 
/*     */     
/* 166 */     return fileName.replaceAll("[^a-zA-Z0-9_-]", "_").toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getAvailableSchematicFiles() {
/* 173 */     List<String> files = new ArrayList<>();
/* 174 */     File[] schematicFiles = this.schematicsFolder.listFiles((dir, name) -> 
/* 175 */         (name.toLowerCase().endsWith(".schem") || name.toLowerCase().endsWith(".schematic")));
/*     */     
/* 177 */     if (schematicFiles != null) {
/* 178 */       for (File file : schematicFiles) {
/* 179 */         files.add(file.getName());
/*     */       }
/*     */     }
/*     */     
/* 183 */     return files;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getSchematicsFolder() {
/* 190 */     return this.schematicsFolder;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\service\SchematicManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */