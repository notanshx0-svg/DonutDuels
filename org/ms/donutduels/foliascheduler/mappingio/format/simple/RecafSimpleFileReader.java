/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.simple;
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
/*     */ public final class RecafSimpleFileReader
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
/*     */         
/*  71 */         String lastClass = null;
/*  72 */         boolean visitClass = false;
/*     */         
/*     */         do {
/*  75 */           String line = reader.nextCols(true);
/*     */ 
/*     */           
/*  78 */           if (line == null || line.trim().isEmpty() || line.trim().startsWith("#"))
/*     */             continue; 
/*  80 */           String[] parts = line.split(" ");
/*     */           
/*  82 */           if (parts.length < 2) {
/*  83 */             insufficientColumnCount(reader);
/*     */           }
/*     */           else {
/*     */             
/*  87 */             int dotPos = parts[0].lastIndexOf('.');
/*     */ 
/*     */             
/*  90 */             String memberSrcName = null;
/*  91 */             String memberSrcDesc = null;
/*     */             
/*  93 */             boolean isMethod = false;
/*     */             
/*  95 */             if (dotPos < 0) {
/*  96 */               String clsSrcName = parts[0];
/*  97 */               String clsDstName = parts[1];
/*     */               
/*  99 */               lastClass = clsSrcName;
/* 100 */               visitClass = memoryMappingTree.visitClass(clsSrcName);
/*     */               
/* 102 */               if (visitClass) {
/* 103 */                 memoryMappingTree.visitDstName(MappedElementKind.CLASS, 0, clsDstName);
/* 104 */                 visitClass = memoryMappingTree.visitElementContent(MappedElementKind.CLASS);
/*     */               } 
/*     */             } else {
/* 107 */               String clsSrcName = parts[0].substring(0, dotPos);
/*     */               
/* 109 */               if (!clsSrcName.equals(lastClass)) {
/* 110 */                 lastClass = clsSrcName;
/* 111 */                 visitClass = (memoryMappingTree.visitClass(clsSrcName) && memoryMappingTree.visitElementContent(MappedElementKind.CLASS));
/*     */               } 
/*     */               
/* 114 */               if (visitClass)
/*     */               
/* 116 */               { String memberIdentifier = parts[0].substring(dotPos + 1);
/* 117 */                 String memberDstName = parts[1];
/*     */                 
/* 119 */                 if (parts.length >= 3) {
/* 120 */                   memberSrcName = memberIdentifier;
/* 121 */                   memberSrcDesc = parts[1];
/* 122 */                   memberDstName = parts[2];
/* 123 */                 } else if (parts.length == 2) {
/* 124 */                   int mthDescPos = memberIdentifier.lastIndexOf("(");
/*     */                   
/* 126 */                   if (mthDescPos < 0) {
/* 127 */                     memberSrcName = memberIdentifier;
/*     */                   } else {
/* 129 */                     isMethod = true;
/* 130 */                     memberSrcName = memberIdentifier.substring(0, mthDescPos);
/* 131 */                     memberSrcDesc = memberIdentifier.substring(mthDescPos);
/*     */                   } 
/*     */                 } else {
/* 134 */                   insufficientColumnCount(reader);
/*     */                 } 
/*     */                 
/* 137 */                 if (!isMethod && memoryMappingTree.visitField(memberSrcName, memberSrcDesc))
/* 138 */                 { memoryMappingTree.visitDstName(MappedElementKind.FIELD, 0, memberDstName);
/* 139 */                   memoryMappingTree.visitElementContent(MappedElementKind.FIELD); }
/* 140 */                 else if (isMethod && memoryMappingTree.visitMethod(memberSrcName, memberSrcDesc))
/* 141 */                 { memoryMappingTree.visitDstName(MappedElementKind.METHOD, 0, memberDstName);
/* 142 */                   memoryMappingTree.visitElementContent(MappedElementKind.METHOD); }  } 
/*     */             } 
/*     */           } 
/* 145 */         } while (reader.nextLine(0));
/*     */       } 
/*     */       
/* 148 */       if (memoryMappingTree.visitEnd())
/*     */         break; 
/* 150 */       if (!readerMarked) {
/* 151 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/* 154 */       int markIdx = reader.reset();
/* 155 */       assert markIdx == 1;
/*     */     } 
/*     */     
/* 158 */     if (parentVisitor != null) {
/* 159 */       ((MappingTree)memoryMappingTree).accept(parentVisitor);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void insufficientColumnCount(ColumnFileReader reader) throws IOException {
/* 164 */     throw new IOException("Invalid Recaf Simple line " + reader.getLineNumber() + ": Insufficient column count!");
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\simple\RecafSimpleFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */