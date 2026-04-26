/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.enigma;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.ColumnFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.tree.MappingTree;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.tree.MemoryMappingTree;
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
/*     */ public final class EnigmaFileReader
/*     */ {
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/*  44 */     read(reader, "source", "target", visitor);
/*     */   }
/*     */   
/*     */   public static void read(Reader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*  48 */     read(new ColumnFileReader(reader, '\t', ' '), sourceNs, targetNs, visitor);
/*     */   }
/*     */   private static void read(ColumnFileReader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*     */     MemoryMappingTree memoryMappingTree;
/*  52 */     Set<MappingFlag> flags = visitor.getFlags();
/*  53 */     MappingVisitor parentVisitor = null;
/*  54 */     boolean readerMarked = false;
/*     */     
/*  56 */     if (flags.contains(MappingFlag.NEEDS_ELEMENT_UNIQUENESS)) {
/*  57 */       parentVisitor = visitor;
/*  58 */       memoryMappingTree = new MemoryMappingTree();
/*  59 */     } else if (flags.contains(MappingFlag.NEEDS_MULTIPLE_PASSES)) {
/*  60 */       reader.mark();
/*  61 */       readerMarked = true;
/*     */     } 
/*     */     
/*     */     while (true) {
/*  65 */       if (memoryMappingTree.visitHeader()) {
/*  66 */         memoryMappingTree.visitNamespaces(sourceNs, Collections.singletonList(targetNs));
/*     */       }
/*     */       
/*  69 */       if (memoryMappingTree.visitContent()) {
/*  70 */         StringBuilder commentSb = new StringBuilder(200);
/*     */         
/*     */         do {
/*  73 */           if (!reader.nextCol("CLASS"))
/*  74 */             continue;  readClass(reader, 0, null, null, commentSb, (MappingVisitor)memoryMappingTree);
/*     */         }
/*  76 */         while (reader.nextLine(0));
/*     */       } 
/*     */       
/*  79 */       if (memoryMappingTree.visitEnd())
/*     */         break; 
/*  81 */       if (!readerMarked) {
/*  82 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/*  85 */       int markIdx = reader.reset();
/*  86 */       assert markIdx == 1;
/*     */     } 
/*     */     
/*  89 */     if (parentVisitor != null) {
/*  90 */       ((MappingTree)memoryMappingTree).accept(parentVisitor);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void readClass(ColumnFileReader reader, int indent, String outerSrcClass, String outerDstClass, StringBuilder commentSb, MappingVisitor visitor) throws IOException {
/*  95 */     String srcInnerName = reader.nextCol();
/*  96 */     if (srcInnerName == null || srcInnerName.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber());
/*     */     
/*  98 */     String srcName = srcInnerName;
/*     */     
/* 100 */     if (outerSrcClass != null && srcInnerName.indexOf('$') < 0) {
/* 101 */       srcName = String.format("%s$%s", new Object[] { outerSrcClass, srcInnerName });
/*     */     }
/*     */     
/* 104 */     String dstInnerName = reader.nextCol();
/* 105 */     String dstName = dstInnerName;
/*     */ 
/*     */     
/* 108 */     if (outerDstClass != null || (dstInnerName != null && outerSrcClass != null)) {
/*     */       
/* 110 */       if (dstInnerName == null) dstInnerName = srcInnerName; 
/* 111 */       if (outerDstClass == null) outerDstClass = outerSrcClass;
/*     */       
/* 113 */       dstName = String.format("%s$%s", new Object[] { outerDstClass, dstInnerName });
/*     */     } 
/*     */     
/* 116 */     readClassBody(reader, indent, srcName, dstName, commentSb, visitor);
/*     */   }
/*     */   
/*     */   private static void readClassBody(ColumnFileReader reader, int indent, String srcClass, String dstClass, StringBuilder commentSb, MappingVisitor visitor) throws IOException {
/* 120 */     boolean visited = false;
/* 121 */     int state = 0;
/*     */     
/* 123 */     while (reader.nextLine(indent + 1)) {
/*     */ 
/*     */       
/* 126 */       if (reader.nextCol("CLASS")) {
/* 127 */         if (!visited || commentSb.length() > 0) {
/* 128 */           visitClass(srcClass, dstClass, state, commentSb, visitor);
/* 129 */           visited = true;
/*     */         } 
/*     */         
/* 132 */         readClass(reader, indent + 1, srcClass, dstClass, commentSb, visitor);
/* 133 */         state = 0; continue;
/* 134 */       }  if (reader.nextCol("COMMENT")) {
/* 135 */         readComment(reader, commentSb); continue;
/* 136 */       }  boolean isMethod; if ((isMethod = reader.nextCol("METHOD")) || reader.nextCol("FIELD")) {
/* 137 */         String dstName; state = visitClass(srcClass, dstClass, state, commentSb, visitor);
/* 138 */         visited = true;
/* 139 */         if (state < 0)
/*     */           continue; 
/* 141 */         String srcName = reader.nextCol();
/* 142 */         if (srcName == null || srcName.isEmpty()) throw new IOException("missing member-name-a in line " + reader.getLineNumber());
/*     */         
/* 144 */         String dstNameOrSrcDesc = reader.nextCol();
/* 145 */         if (dstNameOrSrcDesc == null || dstNameOrSrcDesc.isEmpty()) throw new IOException("missing member-name-b/member-desc-a in line " + reader.getLineNumber());
/*     */         
/* 147 */         String srcDesc = reader.nextCol();
/*     */ 
/*     */         
/* 150 */         if (srcDesc == null) {
/* 151 */           dstName = null;
/* 152 */           srcDesc = dstNameOrSrcDesc;
/*     */         } else {
/* 154 */           dstName = dstNameOrSrcDesc;
/*     */         } 
/*     */         
/* 157 */         if (isMethod && visitor.visitMethod(srcName, srcDesc)) {
/* 158 */           if (dstName != null && !dstName.isEmpty()) visitor.visitDstName(MappedElementKind.METHOD, 0, dstName); 
/* 159 */           readMethod(reader, indent, commentSb, visitor); continue;
/* 160 */         }  if (!isMethod && visitor.visitField(srcName, srcDesc)) {
/* 161 */           if (dstName != null && !dstName.isEmpty()) visitor.visitDstName(MappedElementKind.FIELD, 0, dstName); 
/* 162 */           readElement(reader, MappedElementKind.FIELD, indent, commentSb, visitor);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if (!visited || commentSb.length() > 0) {
/* 168 */       visitClass(srcClass, dstClass, state, commentSb, visitor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int visitClass(String srcClass, String dstClass, int state, StringBuilder commentSb, MappingVisitor visitor) throws IOException {
/* 178 */     if (state == 0) {
/* 179 */       boolean visitContent = visitor.visitClass(srcClass);
/*     */       
/* 181 */       if (visitContent) {
/* 182 */         if (dstClass != null && !dstClass.isEmpty()) visitor.visitDstName(MappedElementKind.CLASS, 0, dstClass); 
/* 183 */         visitContent = visitor.visitElementContent(MappedElementKind.CLASS);
/*     */       } 
/*     */       
/* 186 */       state = visitContent ? 1 : -1;
/*     */       
/* 188 */       if (commentSb.length() > 0) {
/* 189 */         if (state > 0) visitor.visitComment(MappedElementKind.CLASS, commentSb.toString());
/*     */         
/* 191 */         commentSb.setLength(0);
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     return state;
/*     */   }
/*     */   
/*     */   private static void readMethod(ColumnFileReader reader, int indent, StringBuilder commentSb, MappingVisitor visitor) throws IOException {
/* 199 */     if (!visitor.visitElementContent(MappedElementKind.METHOD))
/*     */       return; 
/* 201 */     while (reader.nextLine(indent + 2)) {
/* 202 */       if (reader.nextCol("COMMENT")) {
/* 203 */         readComment(reader, commentSb); continue;
/*     */       } 
/* 205 */       submitComment(MappedElementKind.METHOD, commentSb, visitor);
/*     */       
/* 207 */       if (reader.nextCol("ARG")) {
/* 208 */         int lvIndex = reader.nextIntCol();
/* 209 */         if (lvIndex < 0) throw new IOException("missing/invalid parameter-lv-index in line " + reader.getLineNumber());
/*     */         
/* 211 */         if (visitor.visitMethodArg(-1, lvIndex, null)) {
/* 212 */           String dstName = reader.nextCol();
/* 213 */           if (dstName != null && !dstName.isEmpty()) visitor.visitDstName(MappedElementKind.METHOD_ARG, 0, dstName);
/*     */           
/* 215 */           readElement(reader, MappedElementKind.METHOD_ARG, indent, commentSb, visitor);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 221 */     submitComment(MappedElementKind.METHOD, commentSb, visitor);
/*     */   }
/*     */   
/*     */   private static void readElement(ColumnFileReader reader, MappedElementKind kind, int indent, StringBuilder commentSb, MappingVisitor visitor) throws IOException {
/* 225 */     if (!visitor.visitElementContent(kind))
/*     */       return; 
/* 227 */     while (reader.nextLine(indent + kind.level + 1)) {
/* 228 */       if (reader.nextCol("COMMENT")) {
/* 229 */         readComment(reader, commentSb);
/*     */       }
/*     */     } 
/*     */     
/* 233 */     submitComment(kind, commentSb, visitor);
/*     */   }
/*     */   
/*     */   private static void readComment(ColumnFileReader reader, StringBuilder commentSb) throws IOException {
/* 237 */     if (commentSb.length() > 0) commentSb.append('\n');
/*     */     
/* 239 */     String comment = reader.nextCols(true);
/*     */     
/* 241 */     if (comment != null) {
/* 242 */       commentSb.append(comment);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void submitComment(MappedElementKind kind, StringBuilder commentSb, MappingVisitor visitor) throws IOException {
/* 247 */     if (commentSb.length() == 0)
/*     */       return; 
/* 249 */     visitor.visitComment(kind, commentSb.toString());
/* 250 */     commentSb.setLength(0);
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\enigma\EnigmaFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */