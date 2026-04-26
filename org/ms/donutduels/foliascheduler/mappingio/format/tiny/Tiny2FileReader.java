/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.tiny;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class Tiny2FileReader
/*     */ {
/*     */   public static List<String> getNamespaces(Reader reader) throws IOException {
/*  41 */     return getNamespaces(new ColumnFileReader(reader, '\t', '\t'));
/*     */   }
/*     */   
/*     */   private static List<String> getNamespaces(ColumnFileReader reader) throws IOException {
/*  45 */     if (!reader.nextCol("tiny") || reader
/*  46 */       .nextIntCol() != 2 || reader
/*  47 */       .nextIntCol() < 0) {
/*  48 */       throw new IOException("invalid/unsupported tiny file: no tiny 2 header");
/*     */     }
/*     */     
/*  51 */     List<String> ret = new ArrayList<>();
/*     */     
/*     */     String ns;
/*  54 */     while ((ns = reader.nextCol()) != null) {
/*  55 */       ret.add(ns);
/*     */     }
/*     */     
/*  58 */     return ret;
/*     */   }
/*     */   
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/*  62 */     read(new ColumnFileReader(reader, '\t', '\t'), visitor);
/*     */   }
/*     */   
/*     */   private static void read(ColumnFileReader reader, MappingVisitor visitor) throws IOException {
/*  66 */     if (!reader.nextCol("tiny") || reader
/*  67 */       .nextIntCol() != 2 || reader
/*  68 */       .nextIntCol() < 0) {
/*  69 */       throw new IOException("invalid/unsupported tiny file: no tiny 2 header");
/*     */     }
/*     */     
/*  72 */     String srcNamespace = reader.nextCol();
/*  73 */     if (srcNamespace == null || srcNamespace.isEmpty()) throw new IOException("no source namespace in Tiny v2 header");
/*     */     
/*  75 */     List<String> dstNamespaces = new ArrayList<>();
/*     */ 
/*     */     
/*  78 */     while (!reader.isAtEol()) {
/*  79 */       String dstNamespace = reader.nextCol();
/*  80 */       if (dstNamespace == null || dstNamespace.isEmpty()) throw new IOException("empty destination namespace in Tiny v2 header"); 
/*  81 */       dstNamespaces.add(dstNamespace);
/*     */     } 
/*     */     
/*  84 */     int dstNsCount = dstNamespaces.size();
/*  85 */     boolean readerMarked = false;
/*     */     
/*  87 */     if (visitor.getFlags().contains(MappingFlag.NEEDS_MULTIPLE_PASSES)) {
/*  88 */       reader.mark();
/*  89 */       readerMarked = true;
/*     */     } 
/*     */     
/*  92 */     boolean firstIteration = true;
/*  93 */     boolean escapeNames = false;
/*     */     
/*     */     while (true) {
/*  96 */       boolean visitHeader = visitor.visitHeader();
/*     */       
/*  98 */       if (visitHeader) {
/*  99 */         visitor.visitNamespaces(srcNamespace, dstNamespaces);
/*     */       }
/*     */       
/* 102 */       if (visitHeader || firstIteration) {
/* 103 */         while (reader.nextLine(1)) {
/* 104 */           if (!visitHeader) {
/* 105 */             if (!escapeNames && reader.nextCol("escaped-names"))
/* 106 */               escapeNames = true; 
/*     */             continue;
/*     */           } 
/* 109 */           String key = reader.nextCol();
/* 110 */           if (key == null) throw new IOException("missing property key in line " + reader.getLineNumber()); 
/* 111 */           String value = reader.nextCol(true);
/*     */           
/* 113 */           if (key.equals("escaped-names")) {
/* 114 */             escapeNames = true;
/*     */           }
/*     */           
/* 117 */           visitor.visitMetadata(key, value);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 122 */       if (visitor.visitContent()) {
/* 123 */         while (reader.nextLine(0)) {
/* 124 */           if (reader.nextCol("c")) {
/* 125 */             String srcName = reader.nextCol(escapeNames);
/* 126 */             if (srcName == null || srcName.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber());
/*     */             
/* 128 */             if (visitor.visitClass(srcName)) {
/* 129 */               readClass(reader, dstNsCount, escapeNames, visitor);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 135 */       if (visitor.visitEnd())
/*     */         break; 
/* 137 */       if (!readerMarked) {
/* 138 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/* 141 */       firstIteration = false;
/* 142 */       int markIdx = reader.reset();
/* 143 */       assert markIdx == 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void readClass(ColumnFileReader reader, int dstNsCount, boolean escapeNames, MappingVisitor visitor) throws IOException {
/* 148 */     readDstNames(reader, MappedElementKind.CLASS, dstNsCount, escapeNames, visitor);
/* 149 */     if (!visitor.visitElementContent(MappedElementKind.CLASS))
/*     */       return; 
/* 151 */     while (reader.nextLine(1)) {
/* 152 */       if (reader.nextCol("f")) {
/* 153 */         String srcDesc = reader.nextCol(escapeNames);
/* 154 */         if (srcDesc == null || srcDesc.isEmpty()) throw new IOException("missing field-desc-a in line " + reader.getLineNumber()); 
/* 155 */         String srcName = reader.nextCol(escapeNames);
/* 156 */         if (srcName == null || srcName.isEmpty()) throw new IOException("missing field-name-a in line " + reader.getLineNumber());
/*     */         
/* 158 */         if (visitor.visitField(srcName, srcDesc))
/* 159 */           readElement(reader, MappedElementKind.FIELD, dstNsCount, escapeNames, visitor);  continue;
/*     */       } 
/* 161 */       if (reader.nextCol("m")) {
/* 162 */         String srcDesc = reader.nextCol(escapeNames);
/* 163 */         if (srcDesc == null || srcDesc.isEmpty()) throw new IOException("missing method-desc-a in line " + reader.getLineNumber()); 
/* 164 */         String srcName = reader.nextCol(escapeNames);
/* 165 */         if (srcName == null || srcName.isEmpty()) throw new IOException("missing method-name-a in line " + reader.getLineNumber());
/*     */         
/* 167 */         if (visitor.visitMethod(srcName, srcDesc))
/* 168 */           readMethod(reader, dstNsCount, escapeNames, visitor);  continue;
/*     */       } 
/* 170 */       if (reader.nextCol("c")) {
/* 171 */         readComment(reader, MappedElementKind.CLASS, visitor);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void readMethod(ColumnFileReader reader, int dstNsCount, boolean escapeNames, MappingVisitor visitor) throws IOException {
/* 177 */     readDstNames(reader, MappedElementKind.METHOD, dstNsCount, escapeNames, visitor);
/* 178 */     if (!visitor.visitElementContent(MappedElementKind.METHOD))
/*     */       return; 
/* 180 */     while (reader.nextLine(2)) {
/* 181 */       if (reader.nextCol("p")) {
/* 182 */         int lvIndex = reader.nextIntCol();
/* 183 */         if (lvIndex < 0) throw new IOException("missing/invalid parameter-lv-index in line " + reader.getLineNumber()); 
/* 184 */         String srcName = reader.nextCol(escapeNames);
/* 185 */         if (srcName == null) throw new IOException("missing parameter-name-a column in line " + reader.getLineNumber()); 
/* 186 */         if (srcName.isEmpty()) srcName = null;
/*     */         
/* 188 */         if (visitor.visitMethodArg(-1, lvIndex, srcName))
/* 189 */           readElement(reader, MappedElementKind.METHOD_ARG, dstNsCount, escapeNames, visitor);  continue;
/*     */       } 
/* 191 */       if (reader.nextCol("v")) {
/* 192 */         int lvIndex = reader.nextIntCol();
/* 193 */         if (lvIndex < 0) throw new IOException("missing/invalid variable-lv-index in line " + reader.getLineNumber()); 
/* 194 */         int startOpIdx = reader.nextIntCol();
/* 195 */         if (startOpIdx < 0) throw new IOException("missing/invalid variable-lv-start-offset in line " + reader.getLineNumber()); 
/* 196 */         int lvtRowIndex = reader.nextIntCol();
/* 197 */         String srcName = reader.nextCol(escapeNames);
/* 198 */         if (srcName == null) throw new IOException("missing variable-name-a column in line " + reader.getLineNumber()); 
/* 199 */         if (srcName.isEmpty()) srcName = null;
/*     */         
/* 201 */         if (visitor.visitMethodVar(lvtRowIndex, lvIndex, startOpIdx, -1, srcName))
/* 202 */           readElement(reader, MappedElementKind.METHOD_VAR, dstNsCount, escapeNames, visitor);  continue;
/*     */       } 
/* 204 */       if (reader.nextCol("c")) {
/* 205 */         readComment(reader, MappedElementKind.METHOD, visitor);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void readElement(ColumnFileReader reader, MappedElementKind kind, int dstNsCount, boolean escapeNames, MappingVisitor visitor) throws IOException {
/* 211 */     readDstNames(reader, kind, dstNsCount, escapeNames, visitor);
/* 212 */     if (!visitor.visitElementContent(kind))
/*     */       return; 
/* 214 */     while (reader.nextLine(kind.level + 1)) {
/* 215 */       if (reader.nextCol("c")) {
/* 216 */         readComment(reader, kind, visitor);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void readComment(ColumnFileReader reader, MappedElementKind subjectKind, MappingVisitor visitor) throws IOException {
/* 222 */     String comment = reader.nextCol(true);
/* 223 */     if (comment == null) throw new IOException("missing comment in line " + reader.getLineNumber());
/*     */     
/* 225 */     visitor.visitComment(subjectKind, comment);
/*     */   }
/*     */   
/*     */   private static void readDstNames(ColumnFileReader reader, MappedElementKind subjectKind, int dstNsCount, boolean escapeNames, MappingVisitor visitor) throws IOException {
/* 229 */     for (int dstNs = 0; dstNs < dstNsCount; dstNs++) {
/* 230 */       String name = reader.nextCol(escapeNames);
/* 231 */       if (name == null) throw new IOException("missing name columns in line " + reader.getLineNumber());
/*     */       
/* 233 */       if (!name.isEmpty()) visitor.visitDstName(subjectKind, dstNs, name); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\tiny\Tiny2FileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */