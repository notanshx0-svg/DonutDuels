/*    */ package org.ms.donutduels.utils;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.kyori.adventure.text.format.TextColor;
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorUtil
/*    */ {
/* 12 */   private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
/*    */   
/*    */   public static String color(String message) {
/* 15 */     if (message == null) {
/* 16 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 20 */     Matcher matcher = HEX_PATTERN.matcher(message);
/* 21 */     StringBuffer buffer = new StringBuffer();
/*    */     
/* 23 */     while (matcher.find()) {
/* 24 */       String hex = matcher.group();
/*    */       
/*    */       try {
/* 27 */         TextColor textColor = TextColor.fromHexString(hex);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 34 */         String replacement = "§x§" + hex.charAt(1) + "§" + hex.charAt(2) + "§" + hex.charAt(3) + "§" + hex.charAt(4) + "§" + hex.charAt(5) + "§" + hex.charAt(6);
/* 35 */         matcher.appendReplacement(buffer, replacement);
/* 36 */       } catch (Exception e) {
/*    */         
/* 38 */         matcher.appendReplacement(buffer, hex);
/*    */       } 
/*    */     } 
/* 41 */     matcher.appendTail(buffer);
/*    */ 
/*    */     
/* 44 */     return ChatColor.translateAlternateColorCodes('&', buffer.toString());
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduel\\utils\ColorUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */