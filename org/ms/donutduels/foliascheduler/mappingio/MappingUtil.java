/*    */ package org.ms.donutduels.foliascheduler.mappingio;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MappingUtil
/*    */ {
/*    */   public static final String NS_SOURCE_FALLBACK = "source";
/*    */   public static final String NS_TARGET_FALLBACK = "target";
/*    */   
/*    */   public static String mapDesc(String desc, Map<String, String> clsMap) {
/* 26 */     return mapDesc(desc, 0, desc.length(), clsMap);
/*    */   }
/*    */   
/*    */   public static String mapDesc(String desc, int start, int end, Map<String, String> clsMap) {
/* 30 */     StringBuilder ret = null;
/* 31 */     int searchStart = start;
/*    */     
/*    */     int clsStart;
/* 34 */     while ((clsStart = desc.indexOf('L', searchStart)) >= 0) {
/* 35 */       int clsEnd = desc.indexOf(';', clsStart + 1);
/* 36 */       if (clsEnd < 0) throw new IllegalArgumentException();
/*    */       
/* 38 */       String cls = desc.substring(clsStart + 1, clsEnd);
/* 39 */       String mappedCls = clsMap.get(cls);
/*    */       
/* 41 */       if (mappedCls != null) {
/* 42 */         if (ret == null) ret = new StringBuilder(end - start);
/*    */         
/* 44 */         ret.append(desc, start, clsStart + 1);
/* 45 */         ret.append(mappedCls);
/* 46 */         start = clsEnd;
/*    */       } 
/*    */       
/* 49 */       searchStart = clsEnd + 1;
/*    */     } 
/*    */     
/* 52 */     if (ret == null) return desc.substring(start, end);
/*    */     
/* 54 */     ret.append(desc, start, end);
/*    */     
/* 56 */     return ret.toString();
/*    */   }
/*    */   
/*    */   static String[] toArray(String s) {
/* 60 */     (new String[1])[0] = s; return (s != null) ? new String[1] : null;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\MappingUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */