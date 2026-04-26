/*    */ package org.ms.donutduels.foliascheduler.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerVersions
/*    */ {
/*  8 */   private static Boolean isPaper = null;
/*  9 */   private static Boolean isFolia = null;
/* 10 */   private static int javaVersion = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isPaper() {
/* 22 */     if (isPaper == null) {
/*    */       try {
/* 24 */         Class.forName("com.destroystokyo.paper.PaperConfig");
/* 25 */         isPaper = Boolean.valueOf(true);
/* 26 */       } catch (ClassNotFoundException e) {
/* 27 */         isPaper = Boolean.valueOf(false);
/*    */       } 
/*    */     }
/* 30 */     return isPaper.booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isFolia() {
/* 42 */     if (isFolia == null) {
/*    */       try {
/* 44 */         Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
/* 45 */         isFolia = Boolean.valueOf(true);
/* 46 */       } catch (ClassNotFoundException e) {
/* 47 */         isFolia = Boolean.valueOf(false);
/*    */       } 
/*    */     }
/* 50 */     return isFolia.booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getJavaVersion() {
/* 63 */     if (javaVersion == -1) {
/*    */       try {
/* 65 */         String version = System.getProperty("java.version");
/* 66 */         if (version.startsWith("1.")) {
/* 67 */           version = version.substring(2, 3);
/*    */         } else {
/* 69 */           int dot = version.indexOf(".");
/* 70 */           if (dot != -1) {
/* 71 */             version = version.substring(0, dot);
/*    */           }
/*    */         } 
/* 74 */         version = version.split("-")[0];
/*    */         
/* 76 */         javaVersion = Integer.parseInt(version);
/* 77 */       } catch (Throwable throwable) {
/* 78 */         throw new RuntimeException("Failed to determine Java version", throwable);
/*    */       } 
/*    */     }
/*    */     
/* 82 */     return javaVersion;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliaschedule\\util\ServerVersions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */