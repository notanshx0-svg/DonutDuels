/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.srg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.ColumnFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.MappingFormat;
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
/*     */ 
/*     */ public final class TsrgFileReader
/*     */ {
/*     */   public static List<String> getNamespaces(Reader reader) throws IOException {
/*  46 */     return getNamespaces(new ColumnFileReader(reader, '\t', ' '));
/*     */   }
/*     */   
/*     */   private static List<String> getNamespaces(ColumnFileReader reader) throws IOException {
/*  50 */     if (reader.nextCol("tsrg2")) {
/*  51 */       List<String> ret = new ArrayList<>();
/*     */       
/*     */       String ns;
/*  54 */       while ((ns = reader.nextCol()) != null) {
/*  55 */         ret.add(ns);
/*     */       }
/*     */       
/*  58 */       return ret;
/*     */     } 
/*  60 */     return Arrays.asList(new String[] { "source", "target" });
/*     */   }
/*     */ 
/*     */   
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/*  65 */     read(reader, "source", "target", visitor);
/*     */   }
/*     */   
/*     */   public static void read(Reader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*  69 */     read(new ColumnFileReader(reader, '\t', ' '), sourceNs, targetNs, visitor);
/*     */   } private static void read(ColumnFileReader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*     */     String srcNamespace;
/*     */     List<String> dstNamespaces;
/*  73 */     MappingFormat format = reader.nextCol("tsrg2") ? MappingFormat.TSRG_2_FILE : MappingFormat.TSRG_FILE;
/*     */ 
/*     */     
/*  76 */     boolean readerMarked = false;
/*     */     
/*  78 */     if (format == MappingFormat.TSRG_2_FILE) {
/*  79 */       srcNamespace = reader.nextCol();
/*  80 */       if (srcNamespace == null || srcNamespace.isEmpty()) throw new IOException("no source namespace in TSRG v2 header");
/*     */       
/*  82 */       dstNamespaces = new ArrayList<>();
/*     */ 
/*     */       
/*  85 */       while (!reader.isAtEol()) {
/*  86 */         String dstNamespace = reader.nextCol();
/*  87 */         if (dstNamespace == null || dstNamespace.isEmpty()) throw new IOException("empty destination namespace in TSRG v2 header"); 
/*  88 */         dstNamespaces.add(dstNamespace);
/*     */       } 
/*     */       
/*  91 */       reader.nextLine(0);
/*     */     } else {
/*  93 */       if (sourceNs == null || sourceNs.isEmpty()) throw new IllegalArgumentException("provided source namespace must not be null or empty"); 
/*  94 */       srcNamespace = sourceNs;
/*     */       
/*  96 */       if (targetNs == null || targetNs.isEmpty()) throw new IllegalArgumentException("provided target namespace must not be null or empty"); 
/*  97 */       dstNamespaces = Collections.singletonList(targetNs);
/*     */     } 
/*     */     
/* 100 */     if (visitor.getFlags().contains(MappingFlag.NEEDS_MULTIPLE_PASSES)) {
/* 101 */       reader.mark();
/* 102 */       readerMarked = true;
/*     */     } 
/*     */     
/* 105 */     int dstNsCount = dstNamespaces.size();
/* 106 */     List<String> nameTmp = (dstNamespaces.size() > 1) ? new ArrayList<>(dstNamespaces.size() - 1) : null;
/*     */     
/*     */     while (true) {
/* 109 */       if (visitor.visitHeader()) {
/* 110 */         visitor.visitNamespaces(srcNamespace, dstNamespaces);
/*     */       }
/*     */       
/* 113 */       if (visitor.visitContent()) {
/* 114 */         String lastClass = null;
/* 115 */         boolean visitLastClass = false;
/*     */         
/*     */         do {
/* 118 */           if (reader.hasExtraIndents())
/* 119 */             continue;  reader.mark();
/* 120 */           String line = reader.nextCols(false);
/*     */           
/* 122 */           if ((line == null || line.isEmpty()) && reader.isAtEof()) {
/* 123 */             reader.discardMark();
/*     */           }
/*     */           else {
/*     */             
/* 127 */             reader.reset();
/* 128 */             reader.discardMark();
/* 129 */             String[] parts = line.split("((?<= )|(?= ))");
/*     */             
/* 131 */             if (format != MappingFormat.TSRG_2_FILE && parts.length >= 4 && !parts[3].startsWith("#"))
/* 132 */             { format = MappingFormat.CSRG_FILE;
/* 133 */               String clsName = parts[0];
/* 134 */               if (clsName.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber());
/*     */               
/* 136 */               if (!clsName.equals(lastClass)) {
/* 137 */                 lastClass = clsName;
/* 138 */                 visitLastClass = (visitor.visitClass(clsName) && visitor.visitElementContent(MappedElementKind.CLASS));
/*     */               } 
/*     */               
/* 141 */               if (visitLastClass)
/*     */               {
/*     */                 
/* 144 */                 if (parts.length >= 6 && !parts[5].startsWith("#")) {
/* 145 */                   String dstName = (parts.length == 6) ? null : parts[6];
/*     */                   
/* 147 */                   if (dstName == null || dstName.isEmpty() || dstName.startsWith("#")) {
/* 148 */                     throw new IOException("missing method-name-b in line " + reader.getLineNumber());
/*     */                   }
/*     */                   
/* 151 */                   if (visitor.visitMethod(parts[2], parts[4])) {
/* 152 */                     visitor.visitDstName(MappedElementKind.METHOD, 0, dstName);
/* 153 */                     visitor.visitElementContent(MappedElementKind.METHOD);
/*     */                   }
/*     */                 
/*     */                 }
/* 157 */                 else if (parts.length >= 4) {
/* 158 */                   String dstName = (parts.length == 4) ? null : parts[4];
/*     */                   
/* 160 */                   if (dstName == null || dstName.isEmpty() || dstName.startsWith("#")) {
/* 161 */                     throw new IOException("missing field-name-b in line " + reader.getLineNumber());
/*     */                   }
/*     */                   
/* 164 */                   if (visitor.visitField(parts[2], null)) {
/* 165 */                     visitor.visitDstName(MappedElementKind.FIELD, 0, dstName);
/* 166 */                     visitor.visitElementContent(MappedElementKind.FIELD);
/*     */                   }
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 172 */                   throw new IllegalStateException("invalid CSRG line: " + line);
/*     */                 }  }  }
/*     */             else
/* 175 */             { String srcName = reader.nextCol();
/* 176 */               if (srcName != null && !srcName.endsWith("/"))
/* 177 */               { if (srcName.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber());
/*     */                 
/* 179 */                 lastClass = srcName;
/* 180 */                 visitLastClass = visitor.visitClass(srcName);
/*     */                 
/* 182 */                 if (visitLastClass)
/* 183 */                   visitLastClass = readClass(reader, (format == MappingFormat.TSRG_2_FILE), dstNsCount, nameTmp, visitor);  }  } 
/*     */           } 
/* 185 */         } while (reader.nextLine(0));
/*     */       } 
/*     */       
/* 188 */       if (visitor.visitEnd())
/*     */         break; 
/* 190 */       if (!readerMarked) {
/* 191 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/* 194 */       int markIdx = reader.reset();
/* 195 */       assert markIdx == 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean readClass(ColumnFileReader reader, boolean isTsrg2, int dstNsCount, List<String> nameTmp, MappingVisitor visitor) throws IOException {
/* 200 */     readDstNames(reader, MappedElementKind.CLASS, 0, dstNsCount, visitor);
/* 201 */     if (!visitor.visitElementContent(MappedElementKind.CLASS)) return false;
/*     */     
/* 203 */     while (reader.nextLine(1)) {
/* 204 */       int offset; String desc; if (reader.hasExtraIndents())
/*     */         continue; 
/* 206 */       String srcName = reader.nextCol();
/* 207 */       if (srcName == null || srcName.isEmpty()) throw new IOException("missing member-name-a in line " + reader.getLineNumber());
/*     */       
/* 209 */       String arg = reader.nextCol();
/* 210 */       if (arg == null) throw new IOException("missing member-desc-a/member-name-b in line " + reader.getLineNumber());
/*     */       
/* 212 */       if (arg.startsWith("(")) {
/* 213 */         if (visitor.visitMethod(srcName, arg))
/* 214 */           readMethod(reader, dstNsCount, visitor);  continue;
/*     */       } 
/* 216 */       if (!isTsrg2) {
/* 217 */         if (visitor.visitField(srcName, null)) {
/* 218 */           if (arg.isEmpty()) throw new IOException("missing field-name-b in line " + reader.getLineNumber()); 
/* 219 */           visitor.visitDstName(MappedElementKind.FIELD, 0, arg);
/* 220 */           readElement(reader, MappedElementKind.FIELD, 1, dstNsCount, visitor);
/*     */         }  continue;
/*     */       } 
/* 223 */       for (int i = 0; i < dstNsCount - 1; i++) {
/* 224 */         String name = reader.nextCol();
/* 225 */         if (name == null) throw new IOException("missing name columns in line " + reader.getLineNumber()); 
/* 226 */         if (name.isEmpty()) throw new IOException("missing field-name-b in line " + reader.getLineNumber()); 
/* 227 */         nameTmp.add(name);
/*     */       } 
/*     */       
/* 230 */       String lastName = reader.nextCol();
/*     */ 
/*     */ 
/*     */       
/* 234 */       if (lastName == null) {
/* 235 */         offset = 1;
/* 236 */         desc = null;
/*     */       } else {
/* 238 */         offset = 0;
/* 239 */         desc = arg;
/* 240 */         if (desc.isEmpty()) throw new IOException("empty field-desc-a in line " + reader.getLineNumber());
/*     */       
/*     */       } 
/* 243 */       if (visitor.visitField(srcName, desc)) {
/*     */         
/* 245 */         if (lastName == null && !arg.isEmpty()) visitor.visitDstName(MappedElementKind.FIELD, 0, arg);
/*     */ 
/*     */         
/* 248 */         for (int j = 0; j < dstNsCount - 1; j++) {
/* 249 */           String name = nameTmp.get(j);
/* 250 */           if (!name.isEmpty()) visitor.visitDstName(MappedElementKind.FIELD, j + offset, name);
/*     */         
/*     */         } 
/*     */         
/* 254 */         if (lastName != null && !lastName.isEmpty()) visitor.visitDstName(MappedElementKind.FIELD, dstNsCount - 1, lastName);
/*     */         
/* 256 */         visitor.visitElementContent(MappedElementKind.FIELD);
/*     */       } 
/*     */       
/* 259 */       if (nameTmp != null) nameTmp.clear();
/*     */     
/*     */     } 
/*     */     
/* 263 */     return true;
/*     */   }
/*     */   
/*     */   private static void readMethod(ColumnFileReader reader, int dstNsCount, MappingVisitor visitor) throws IOException {
/* 267 */     readDstNames(reader, MappedElementKind.METHOD, 0, dstNsCount, visitor);
/* 268 */     if (!visitor.visitElementContent(MappedElementKind.METHOD))
/*     */       return; 
/* 270 */     while (reader.nextLine(2)) {
/* 271 */       if (reader.hasExtraIndents())
/*     */         continue; 
/* 273 */       if (reader.nextCol("static")) {
/*     */         continue;
/*     */       }
/* 276 */       int lvIndex = reader.nextIntCol();
/* 277 */       if (lvIndex < 0) throw new IOException("missing/invalid parameter-lv-index in line " + reader.getLineNumber());
/*     */       
/* 279 */       String srcName = reader.nextCol();
/* 280 */       if (srcName == null) throw new IOException("missing parameter-name-a column in line " + reader.getLineNumber()); 
/* 281 */       if (srcName.isEmpty()) srcName = null;
/*     */       
/* 283 */       if (visitor.visitMethodArg(-1, lvIndex, srcName)) {
/* 284 */         readElement(reader, MappedElementKind.METHOD_ARG, 0, dstNsCount, visitor);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void readElement(ColumnFileReader reader, MappedElementKind kind, int dstNsOffset, int dstNsCount, MappingVisitor visitor) throws IOException {
/* 291 */     readDstNames(reader, kind, dstNsOffset, dstNsCount, visitor);
/* 292 */     visitor.visitElementContent(kind);
/*     */   }
/*     */   
/*     */   private static void readDstNames(ColumnFileReader reader, MappedElementKind subjectKind, int dstNsOffset, int dstNsCount, MappingVisitor visitor) throws IOException {
/* 296 */     for (int dstNs = dstNsOffset; dstNs < dstNsCount; dstNs++) {
/* 297 */       String name = reader.nextCol();
/*     */       
/* 299 */       if (name == null) throw new IOException("missing name columns in line " + reader.getLineNumber()); 
/* 300 */       if (name.isEmpty()) throw new IOException("missing destination name in line " + reader.getLineNumber());
/*     */       
/* 302 */       visitor.visitDstName(subjectKind, dstNs, name);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\srg\TsrgFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */