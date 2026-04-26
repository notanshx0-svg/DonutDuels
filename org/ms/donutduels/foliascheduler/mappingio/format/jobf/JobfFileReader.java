/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.jobf;
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
/*     */ public class JobfFileReader
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
/*  70 */         String lastClass = null;
/*  71 */         boolean visitLastClass = false;
/*     */ 
/*     */ 
/*     */         
/*     */         do {
/*  76 */           if (reader.nextCol("c"))
/*  77 */           { String srcName = reader.nextCol();
/*  78 */             if (srcName == null || srcName.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber()); 
/*  79 */             srcName = srcName.replace('.', '/');
/*     */             
/*  81 */             lastClass = srcName;
/*  82 */             visitLastClass = memoryMappingTree.visitClass(srcName);
/*     */             
/*  84 */             if (visitLastClass) {
/*  85 */               readSeparator(reader);
/*     */               
/*  87 */               String dstName = reader.nextCol();
/*  88 */               if (dstName == null || dstName.isEmpty()) throw new IOException("missing class-name-b in line " + reader.getLineNumber());
/*     */               
/*  90 */               String pkg = srcName.substring(0, srcName.lastIndexOf('/') + 1);
/*  91 */               dstName = pkg + dstName;
/*     */               
/*  93 */               memoryMappingTree.visitDstName(MappedElementKind.CLASS, 0, dstName);
/*  94 */               visitLastClass = memoryMappingTree.visitElementContent(MappedElementKind.CLASS);
/*     */             }  }
/*  96 */           else { boolean isField; if ((isField = reader.nextCol("f")) || reader.nextCol("m"))
/*     */             
/*     */             { 
/*  99 */               String src = reader.nextCol();
/* 100 */               if (src == null || src.isEmpty()) throw new IOException("missing class-/name-/desc-a in line " + reader.getLineNumber());
/*     */               
/* 102 */               int nameSepPos = src.lastIndexOf('.');
/* 103 */               if (nameSepPos <= 0 || nameSepPos == src.length() - 1) throw new IOException("invalid class-/name-/desc-a in line " + reader.getLineNumber());
/*     */               
/* 105 */               int descSepPos = src.lastIndexOf(isField ? 58 : 40);
/* 106 */               if (descSepPos <= 0 || descSepPos == src.length() - 1) throw new IOException("invalid name-/desc-a in line " + reader.getLineNumber());
/*     */               
/* 108 */               readSeparator(reader);
/*     */               
/* 110 */               String dstName = reader.nextCol();
/* 111 */               if (dstName == null || dstName.isEmpty()) throw new IOException("missing name-b in line " + reader.getLineNumber());
/*     */               
/* 113 */               String srcOwner = src.substring(0, nameSepPos).replace('.', '/');
/*     */               
/* 115 */               if (!srcOwner.equals(lastClass)) {
/* 116 */                 lastClass = srcOwner;
/* 117 */                 visitLastClass = (memoryMappingTree.visitClass(srcOwner) && memoryMappingTree.visitElementContent(MappedElementKind.CLASS));
/*     */               } 
/*     */               
/* 120 */               if (visitLastClass) {
/* 121 */                 String srcName = src.substring(nameSepPos + 1, descSepPos);
/* 122 */                 String srcDesc = src.substring(descSepPos + (isField ? 1 : 0));
/*     */                 
/* 124 */                 if ((isField && memoryMappingTree.visitField(srcName, srcDesc)) || (!isField && memoryMappingTree
/* 125 */                   .visitMethod(srcName, srcDesc))) {
/* 126 */                   MappedElementKind kind = isField ? MappedElementKind.FIELD : MappedElementKind.METHOD;
/* 127 */                   memoryMappingTree.visitDstName(kind, 0, dstName);
/* 128 */                   memoryMappingTree.visitElementContent(kind);
/*     */                 } 
/*     */               }  }
/* 131 */             else if (reader.nextCol("p")) {  }
/*     */              }
/*     */         
/* 134 */         } while (reader.nextLine(0));
/*     */       } 
/*     */       
/* 137 */       if (memoryMappingTree.visitEnd())
/*     */         break; 
/* 139 */       if (!readerMarked) {
/* 140 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/* 143 */       int markIdx = reader.reset();
/* 144 */       assert markIdx == 1;
/*     */     } 
/*     */     
/* 147 */     if (parentVisitor != null) {
/* 148 */       ((MappingTree)memoryMappingTree).accept(parentVisitor);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void readSeparator(ColumnFileReader reader) throws IOException {
/* 153 */     if (!reader.nextCol("="))
/* 154 */       throw new IOException("missing separator in line " + reader.getLineNumber() + " (expected \" = \")"); 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\jobf\JobfFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */