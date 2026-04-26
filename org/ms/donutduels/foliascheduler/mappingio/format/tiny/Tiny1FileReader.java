/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.tiny;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class Tiny1FileReader
/*     */ {
/*     */   static final String nextIntermediaryClassProperty = "next-intermediary-class";
/*     */   static final String nextIntermediaryFieldProperty = "next-intermediary-field";
/*     */   static final String nextIntermediaryMethodProperty = "next-intermediary-method";
/*     */   
/*     */   public static List<String> getNamespaces(Reader reader) throws IOException {
/*  44 */     return getNamespaces(new ColumnFileReader(reader, '\t', '\t'));
/*     */   }
/*     */   
/*     */   private static List<String> getNamespaces(ColumnFileReader reader) throws IOException {
/*  48 */     if (!reader.nextCol("v1")) {
/*  49 */       throw new IOException("invalid/unsupported tiny file: no tiny 1 header");
/*     */     }
/*     */     
/*  52 */     List<String> ret = new ArrayList<>();
/*     */     
/*     */     String ns;
/*  55 */     while ((ns = reader.nextCol()) != null) {
/*  56 */       ret.add(ns);
/*     */     }
/*     */     
/*  59 */     return ret;
/*     */   }
/*     */   
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/*  63 */     read(new ColumnFileReader(reader, '\t', '\t'), visitor);
/*     */   }
/*     */   private static void read(ColumnFileReader reader, MappingVisitor visitor) throws IOException {
/*     */     MemoryMappingTree memoryMappingTree;
/*  67 */     if (!reader.nextCol("v1")) {
/*  68 */       throw new IOException("invalid/unsupported tiny file: no tiny 1 header");
/*     */     }
/*     */     
/*  71 */     String srcNamespace = reader.nextCol();
/*  72 */     if (srcNamespace == null || srcNamespace.isEmpty()) throw new IOException("no source namespace in Tiny v1 header");
/*     */     
/*  74 */     List<String> dstNamespaces = new ArrayList<>();
/*     */ 
/*     */     
/*  77 */     while (!reader.isAtEol()) {
/*  78 */       String dstNamespace = reader.nextCol();
/*  79 */       if (dstNamespace == null || dstNamespace.isEmpty()) throw new IOException("empty destination namespace in Tiny v1 header"); 
/*  80 */       dstNamespaces.add(dstNamespace);
/*     */     } 
/*     */     
/*  83 */     int dstNsCount = dstNamespaces.size();
/*     */     
/*  85 */     Set<MappingFlag> flags = visitor.getFlags();
/*  86 */     MappingVisitor parentVisitor = null;
/*  87 */     boolean readerMarked = false;
/*     */     
/*  89 */     if (flags.contains(MappingFlag.NEEDS_ELEMENT_UNIQUENESS) || flags.contains(MappingFlag.NEEDS_HEADER_METADATA)) {
/*  90 */       parentVisitor = visitor;
/*  91 */       memoryMappingTree = new MemoryMappingTree();
/*  92 */     } else if (flags.contains(MappingFlag.NEEDS_MULTIPLE_PASSES)) {
/*  93 */       reader.mark();
/*  94 */       readerMarked = true;
/*     */     } 
/*     */     
/*     */     while (true) {
/*  98 */       if (memoryMappingTree.visitHeader()) {
/*  99 */         memoryMappingTree.visitNamespaces(srcNamespace, dstNamespaces);
/*     */       }
/*     */       
/* 102 */       if (memoryMappingTree.visitContent()) {
/* 103 */         String lastClass = null;
/* 104 */         boolean visitLastClass = false;
/*     */         
/* 106 */         while (reader.nextLine(0)) {
/*     */ 
/*     */           
/* 109 */           if (reader.nextCol("CLASS")) {
/* 110 */             String srcName = reader.nextCol();
/* 111 */             if (srcName == null || srcName.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber());
/*     */             
/* 113 */             lastClass = srcName;
/* 114 */             visitLastClass = memoryMappingTree.visitClass(srcName);
/*     */             
/* 116 */             if (visitLastClass) {
/* 117 */               readDstNames(reader, MappedElementKind.CLASS, dstNsCount, (MappingVisitor)memoryMappingTree);
/* 118 */               visitLastClass = memoryMappingTree.visitElementContent(MappedElementKind.CLASS);
/*     */             }  continue;
/* 120 */           }  boolean isMethod; if ((isMethod = reader.nextCol("METHOD")) || reader.nextCol("FIELD")) {
/* 121 */             String srcOwner = reader.nextCol();
/* 122 */             if (srcOwner == null || srcOwner.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber());
/*     */             
/* 124 */             if (!srcOwner.equals(lastClass)) {
/* 125 */               lastClass = srcOwner;
/* 126 */               visitLastClass = (memoryMappingTree.visitClass(srcOwner) && memoryMappingTree.visitElementContent(MappedElementKind.CLASS));
/*     */             } 
/*     */             
/* 129 */             if (visitLastClass) {
/* 130 */               String srcDesc = reader.nextCol();
/* 131 */               if (srcDesc == null || srcDesc.isEmpty()) throw new IOException("missing member-desc-a in line " + reader.getLineNumber()); 
/* 132 */               String srcName = reader.nextCol();
/* 133 */               if (srcName == null || srcName.isEmpty()) throw new IOException("missing member-name-a in line " + reader.getLineNumber());
/*     */               
/* 135 */               if ((isMethod && memoryMappingTree.visitMethod(srcName, srcDesc)) || (!isMethod && memoryMappingTree
/* 136 */                 .visitField(srcName, srcDesc))) {
/* 137 */                 MappedElementKind kind = isMethod ? MappedElementKind.METHOD : MappedElementKind.FIELD;
/* 138 */                 readDstNames(reader, kind, dstNsCount, (MappingVisitor)memoryMappingTree);
/* 139 */                 memoryMappingTree.visitElementContent(kind);
/*     */               } 
/*     */             }  continue;
/*     */           } 
/* 143 */           String line = reader.nextCol();
/* 144 */           String prefix = "# INTERMEDIARY-COUNTER ";
/*     */           
/*     */           String[] parts;
/* 147 */           if (line.startsWith("# INTERMEDIARY-COUNTER ") && (
/* 148 */             parts = line.substring("# INTERMEDIARY-COUNTER ".length()).split(" ")).length == 2) {
/* 149 */             String property = null;
/*     */             
/* 151 */             switch (parts[0]) {
/*     */               case "class":
/* 153 */                 property = "next-intermediary-class";
/*     */                 break;
/*     */               case "field":
/* 156 */                 property = "next-intermediary-field";
/*     */                 break;
/*     */               case "method":
/* 159 */                 property = "next-intermediary-method";
/*     */                 break;
/*     */             } 
/*     */             
/* 163 */             if (property != null) {
/* 164 */               memoryMappingTree.visitMetadata(property, parts[1]);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 171 */       if (memoryMappingTree.visitEnd())
/*     */         break; 
/* 173 */       if (!readerMarked) {
/* 174 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/* 177 */       int markIdx = reader.reset();
/* 178 */       assert markIdx == 1;
/*     */     } 
/*     */     
/* 181 */     if (parentVisitor != null) {
/* 182 */       ((MappingTree)memoryMappingTree).accept(parentVisitor);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void readDstNames(ColumnFileReader reader, MappedElementKind subjectKind, int dstNsCount, MappingVisitor visitor) throws IOException {
/* 187 */     for (int dstNs = 0; dstNs < dstNsCount; dstNs++) {
/* 188 */       String name = reader.nextCol();
/* 189 */       if (name == null) throw new IOException("missing name columns in line " + reader.getLineNumber());
/*     */       
/* 191 */       if (!name.isEmpty()) visitor.visitDstName(subjectKind, dstNs, name); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\tiny\Tiny1FileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */