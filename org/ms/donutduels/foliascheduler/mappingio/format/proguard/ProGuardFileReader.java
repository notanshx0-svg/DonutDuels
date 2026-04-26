/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.proguard;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Collections;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.ColumnFileReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ProGuardFileReader
/*     */ {
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/*  41 */     read(reader, "source", "target", visitor);
/*     */   }
/*     */   
/*     */   public static void read(Reader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*  45 */     read(new ColumnFileReader(reader, ';', ' '), sourceNs, targetNs, visitor);
/*     */   }
/*     */   
/*     */   private static void read(ColumnFileReader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*  49 */     boolean readerMarked = false;
/*     */     
/*  51 */     if (visitor.getFlags().contains(MappingFlag.NEEDS_MULTIPLE_PASSES)) {
/*  52 */       reader.mark();
/*  53 */       readerMarked = true;
/*     */     } 
/*     */     
/*  56 */     StringBuilder descSb = null;
/*     */     
/*     */     while (true) {
/*  59 */       if (visitor.visitHeader()) {
/*  60 */         visitor.visitNamespaces(sourceNs, Collections.singletonList(targetNs));
/*     */       }
/*     */       
/*  63 */       if (visitor.visitContent()) {
/*  64 */         if (descSb == null) descSb = new StringBuilder();
/*     */ 
/*     */         
/*  67 */         boolean visitClass = false;
/*     */         do {
/*     */           String line;
/*  70 */           if ((line = reader.nextCols(false)) == null)
/*     */             continue; 
/*  72 */           line = line.trim();
/*  73 */           if (line.isEmpty() || line.startsWith("#"))
/*     */             continue; 
/*  75 */           if (line.endsWith(":")) {
/*  76 */             int pos = line.indexOf(" -> ");
/*  77 */             if (pos < 0) throw new IOException("invalid proguard line (invalid separator): " + line); 
/*  78 */             if (pos == 0) throw new IOException("invalid proguard line (empty src class): " + line); 
/*  79 */             if (pos + 4 + 1 >= line.length()) throw new IOException("invalid proguard line (empty dst class): " + line);
/*     */             
/*  81 */             String name = line.substring(0, pos).replace('.', '/');
/*  82 */             visitClass = visitor.visitClass(name);
/*     */             
/*  84 */             if (visitClass) {
/*  85 */               String mappedName = line.substring(pos + 4, line.length() - 1).replace('.', '/');
/*  86 */               visitor.visitDstName(MappedElementKind.CLASS, 0, mappedName);
/*  87 */               visitClass = visitor.visitElementContent(MappedElementKind.CLASS);
/*     */             } 
/*  89 */           } else if (visitClass) {
/*  90 */             String[] parts = line.split(" ");
/*     */             
/*  92 */             if (parts.length != 4) throw new IOException("invalid proguard line (extra columns): " + line); 
/*  93 */             if (parts[0].isEmpty()) throw new IOException("invalid proguard line (empty type): " + line); 
/*  94 */             if (parts[1].isEmpty()) throw new IOException("invalid proguard line (empty src member): " + line); 
/*  95 */             if (!parts[2].equals("->")) throw new IOException("invalid proguard line (invalid separator): " + line); 
/*  96 */             if (parts[3].isEmpty()) throw new IOException("invalid proguard line (empty dst member): " + line);
/*     */             
/*  98 */             if (parts[1].indexOf('(') < 0) {
/*  99 */               String name = parts[1];
/* 100 */               String desc = pgTypeToAsm(parts[0], descSb);
/*     */               
/* 102 */               if (visitor.visitField(name, desc)) {
/* 103 */                 String mappedName = parts[3];
/* 104 */                 visitor.visitDstName(MappedElementKind.FIELD, 0, mappedName);
/* 105 */                 visitor.visitElementContent(MappedElementKind.FIELD);
/*     */               } 
/*     */             } else {
/*     */               
/* 109 */               String retType, part0 = parts[0];
/* 110 */               int pos = part0.indexOf(':');
/*     */ 
/*     */ 
/*     */               
/* 114 */               if (pos == -1) {
/* 115 */                 retType = part0;
/*     */               } else {
/* 117 */                 int pos2 = part0.indexOf(':', pos + 1);
/* 118 */                 assert pos2 != -1;
/*     */                 
/* 120 */                 retType = part0.substring(pos2 + 1);
/*     */               } 
/*     */ 
/*     */               
/* 124 */               String part1 = parts[1];
/* 125 */               pos = part1.indexOf('(');
/* 126 */               int pos3 = part1.indexOf(')', pos + 1);
/* 127 */               assert pos3 != -1;
/*     */               
/* 129 */               if (part1.lastIndexOf('.', pos - 1) < 0 && part1.length() == pos3 + 1) {
/* 130 */                 String name = part1.substring(0, pos);
/* 131 */                 String argDesc = part1.substring(pos, pos3 + 1);
/* 132 */                 String desc = pgDescToAsm(argDesc, retType, descSb);
/*     */                 
/* 134 */                 if (visitor.visitMethod(name, desc)) {
/* 135 */                   String mappedName = parts[3];
/* 136 */                   visitor.visitDstName(MappedElementKind.METHOD, 0, mappedName);
/* 137 */                   visitor.visitElementContent(MappedElementKind.METHOD);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/* 142 */         } while (reader.nextLine(0));
/*     */       } 
/*     */       
/* 145 */       if (visitor.visitEnd())
/*     */         break; 
/* 147 */       if (!readerMarked) {
/* 148 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/* 151 */       int markIdx = reader.reset();
/* 152 */       assert markIdx == 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String pgDescToAsm(String pgArgDesc, String pgRetType, StringBuilder tmp) {
/* 157 */     tmp.setLength(0);
/* 158 */     tmp.append('(');
/*     */     
/* 160 */     if (pgArgDesc.length() > 2) {
/* 161 */       int startPos = 1;
/* 162 */       boolean abort = false;
/*     */       
/*     */       do {
/* 165 */         int endPos = pgArgDesc.indexOf(',', startPos);
/*     */         
/* 167 */         if (endPos < 0) {
/* 168 */           endPos = pgArgDesc.length() - 1;
/* 169 */           abort = true;
/*     */         } 
/*     */         
/* 172 */         appendPgTypeToAsm(pgArgDesc.substring(startPos, endPos), tmp);
/* 173 */         startPos = endPos + 1;
/* 174 */       } while (!abort);
/*     */     } 
/*     */     
/* 177 */     tmp.append(')');
/* 178 */     if (pgRetType != null) appendPgTypeToAsm(pgRetType, tmp);
/*     */     
/* 180 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   private static String pgTypeToAsm(String type, StringBuilder tmp) {
/* 184 */     tmp.setLength(0);
/* 185 */     appendPgTypeToAsm(type, tmp);
/*     */     
/* 187 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   private static void appendPgTypeToAsm(String type, StringBuilder out) {
/* 191 */     assert !type.isEmpty();
/*     */     
/* 193 */     int arrayStart = type.indexOf('[');
/*     */     
/* 195 */     if (arrayStart != -1) {
/* 196 */       assert type.substring(arrayStart).matches("(\\[])+");
/*     */       
/* 198 */       int arrayDimensions = (type.length() - arrayStart) / 2;
/*     */       
/* 200 */       for (int i = 0; i < arrayDimensions; i++) {
/* 201 */         out.append('[');
/*     */       }
/*     */       
/* 204 */       type = type.substring(0, arrayStart);
/*     */     } 
/*     */     
/* 207 */     switch (type) { case "void":
/* 208 */         out.append('V'); return;
/* 209 */       case "boolean": out.append('Z'); return;
/* 210 */       case "char": out.append('C'); return;
/* 211 */       case "byte": out.append('B'); return;
/* 212 */       case "short": out.append('S'); return;
/* 213 */       case "int": out.append('I'); return;
/* 214 */       case "float": out.append('F'); return;
/* 215 */       case "long": out.append('J'); return;
/* 216 */       case "double": out.append('D'); return; }
/*     */     
/* 218 */     out.append('L');
/* 219 */     out.append(type.replace('.', '/'));
/* 220 */     out.append(';');
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\proguard\ProGuardFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */