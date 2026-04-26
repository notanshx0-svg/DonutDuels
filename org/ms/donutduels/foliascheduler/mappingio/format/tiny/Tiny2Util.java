/*    */ package org.ms.donutduels.foliascheduler.mappingio.format.tiny;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*    */ @Internal
/*    */ public final class Tiny2Util
/*    */ {
/*    */   private static final String toEscape = "\\\n\r\000\t";
/*    */   private static final String escaped = "\\nr0t";
/*    */   static final String escapedNamesProperty = "escaped-names";
/*    */   
/*    */   public static boolean needEscape(String s) {
/* 30 */     for (int pos = 0, len = s.length(); pos < len; pos++) {
/* 31 */       char c = s.charAt(pos);
/* 32 */       if ("\\\n\r\000\t".indexOf(c) >= 0) return true;
/*    */     
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */   
/*    */   public static void writeEscaped(String s, Writer out) throws IOException {
/* 39 */     int len = s.length();
/* 40 */     int start = 0;
/*    */     
/* 42 */     for (int pos = 0; pos < len; pos++) {
/* 43 */       char c = s.charAt(pos);
/* 44 */       int idx = "\\\n\r\000\t".indexOf(c);
/*    */       
/* 46 */       if (idx >= 0) {
/* 47 */         out.write(s, start, pos - start);
/* 48 */         out.write(92);
/* 49 */         out.write("\\nr0t".charAt(idx));
/* 50 */         start = pos + 1;
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     out.write(s, start, len - start);
/*    */   }
/*    */   
/*    */   public static String unescape(String str) {
/* 58 */     int type, pos = str.indexOf('\\');
/* 59 */     if (pos < 0) return str;
/*    */     
/* 61 */     StringBuilder ret = new StringBuilder(str.length() - 1);
/* 62 */     int start = 0;
/*    */     
/*    */     do {
/* 65 */       ret.append(str, start, pos);
/* 66 */       pos++;
/*    */ 
/*    */       
/* 69 */       if (pos >= str.length())
/* 70 */         throw new RuntimeException("incomplete escape sequence at the end"); 
/* 71 */       if ((type = "\\nr0t".indexOf(str.charAt(pos))) < 0) {
/* 72 */         throw new RuntimeException("invalid escape character: \\" + str.charAt(pos));
/*    */       }
/* 74 */       ret.append("\\\n\r\000\t".charAt(type));
/*    */ 
/*    */       
/* 77 */       start = pos + 1;
/* 78 */     } while ((pos = str.indexOf('\\', start)) >= 0);
/*    */     
/* 80 */     ret.append(str, start, str.length());
/*    */     
/* 82 */     return ret.toString();
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\tiny\Tiny2Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */