/*    */ package org.ms.donutduels.foliascheduler;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ public class FoliaCompatibility
/*    */ {
/*    */   @NotNull
/*    */   private final ServerImplementation serverImplementation;
/*    */   
/*    */   public FoliaCompatibility(@NotNull Plugin plugin) {
/*    */     ServerImplementation scheduler;
/* 16 */     assertRelocated(plugin);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/*    */       try {
/* 23 */         Server.class.getMethod("isOwnedByCurrentRegion", new Class[] { Location.class });
/*    */ 
/*    */ 
/*    */         
/* 27 */         scheduler = Class.forName(getClass().getPackage().getName() + ".folia.FoliaServer").<ServerImplementation>asSubclass(ServerImplementation.class).getConstructor(new Class[] { Plugin.class }).newInstance(new Object[] { plugin });
/* 28 */       } catch (NoSuchMethodException ex) {
/*    */ 
/*    */ 
/*    */         
/* 32 */         scheduler = Class.forName(getClass().getPackage().getName() + ".bukkit.BukkitServer").<ServerImplementation>asSubclass(ServerImplementation.class).getConstructor(new Class[] { Plugin.class }).newInstance(new Object[] { plugin });
/*    */       } 
/* 34 */     } catch (Throwable ex) {
/* 35 */       throw new RuntimeException("Failed to initialize scheduler", ex);
/*    */     } 
/*    */     
/* 38 */     this.serverImplementation = scheduler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public ServerImplementation getServerImplementation() {
/* 47 */     return this.serverImplementation;
/*    */   }
/*    */   
/*    */   private static void assertRelocated(@NotNull Plugin plugin) {
/* 51 */     String originalPackage = String.join(".", new CharSequence[] { "com", "cjcrafter", "scheduler" });
/* 52 */     String relocatedPackage = FoliaCompatibility.class.getPackage().getName();
/* 53 */     if (originalPackage.equals(relocatedPackage)) {
/* 54 */       String authors = String.join(", ", plugin.getDescription().getAuthors());
/* 55 */       String possiblePackage = plugin.getClass().getPackage().getName() + ".libs.scheduler";
/* 56 */       plugin.getLogger().warning("The FoliaScheduler lib has not been relocated!");
/* 57 */       plugin.getLogger().warning("The package is still in '" + originalPackage + "'");
/* 58 */       plugin.getLogger().warning("Please relocate the lib to '" + possiblePackage + "'");
/* 59 */       plugin.getLogger().warning("Please warn authors " + authors + " about this issue.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\FoliaCompatibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */