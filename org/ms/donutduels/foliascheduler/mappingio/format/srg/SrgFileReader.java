/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.srg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.ColumnFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.MappingFormat;
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
/*     */ public final class SrgFileReader
/*     */ {
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/*  45 */     read(reader, "source", "target", visitor);
/*     */   }
/*     */   
/*     */   public static void read(Reader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*  49 */     read(new ColumnFileReader(reader, '\t', ' '), sourceNs, targetNs, visitor);
/*     */   }
/*     */   private static void read(ColumnFileReader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*     */     MemoryMappingTree memoryMappingTree;
/*  53 */     MappingFormat format = MappingFormat.SRG_FILE;
/*  54 */     Set<MappingFlag> flags = visitor.getFlags();
/*  55 */     MappingVisitor parentVisitor = null;
/*  56 */     boolean readerMarked = false;
/*     */     
/*  58 */     if (flags.contains(MappingFlag.NEEDS_ELEMENT_UNIQUENESS)) {
/*  59 */       parentVisitor = visitor;
/*  60 */       memoryMappingTree = new MemoryMappingTree();
/*  61 */     } else if (flags.contains(MappingFlag.NEEDS_MULTIPLE_PASSES)) {
/*  62 */       reader.mark();
/*  63 */       readerMarked = true;
/*     */     } 
/*     */     
/*     */     while (true) {
/*  67 */       if (memoryMappingTree.visitHeader()) {
/*  68 */         memoryMappingTree.visitNamespaces(sourceNs, Collections.singletonList(targetNs));
/*     */       }
/*     */       
/*  71 */       if (memoryMappingTree.visitContent()) {
/*  72 */         String lastClassSrcName = null;
/*  73 */         String lastClassDstName = null;
/*  74 */         boolean classContentVisitPending = false;
/*     */ 
/*     */ 
/*     */         
/*     */         do {
/*  79 */           if (reader.nextCol("CL:")) {
/*  80 */             String srcName = reader.nextCol();
/*  81 */             if (srcName == null || srcName.isEmpty()) throw new IOException("missing class-name-a in line " + reader.getLineNumber());
/*     */             
/*  83 */             if (classContentVisitPending) {
/*  84 */               memoryMappingTree.visitElementContent(MappedElementKind.CLASS);
/*  85 */               classContentVisitPending = false;
/*     */             } 
/*     */             
/*  88 */             lastClassSrcName = srcName;
/*     */             
/*  90 */             if (memoryMappingTree.visitClass(srcName)) {
/*  91 */               String dstName = reader.nextCol();
/*  92 */               if (dstName == null || dstName.isEmpty()) throw new IOException("missing class-name-b in line " + reader.getLineNumber());
/*     */               
/*  94 */               lastClassDstName = dstName;
/*  95 */               memoryMappingTree.visitDstName(MappedElementKind.CLASS, 0, dstName);
/*  96 */               classContentVisitPending = true;
/*     */             }  continue;
/*  98 */           }  boolean isMethod; if ((isMethod = reader.nextCol("MD:")) || reader.nextCol("FD:")) {
/*  99 */             String srcDesc, dstName, dstDesc, src = reader.nextCol();
/* 100 */             if (src == null) throw new IOException("missing class-/name-a in line " + reader.getLineNumber());
/*     */             
/* 102 */             int srcSepPos = src.lastIndexOf('/');
/* 103 */             if (srcSepPos <= 0 || srcSepPos == src.length() - 1) throw new IOException("invalid class-/name-a in line " + reader.getLineNumber());
/*     */             
/* 105 */             String[] cols = new String[3];
/*     */             
/* 107 */             for (int i = 0; i < 3; i++) {
/* 108 */               cols[i] = reader.nextCol();
/*     */             }
/*     */             
/* 111 */             if (!isMethod && cols[1] != null && cols[2] != null) format = MappingFormat.XSRG_FILE;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 116 */             if (isMethod || format == MappingFormat.XSRG_FILE) {
/* 117 */               srcDesc = cols[0];
/* 118 */               if (srcDesc == null || srcDesc.isEmpty()) throw new IOException("missing desc-a in line " + reader.getLineNumber()); 
/* 119 */               dstName = cols[1];
/* 120 */               dstDesc = cols[2];
/* 121 */               if (dstDesc == null || dstDesc.isEmpty()) throw new IOException("missing desc-b in line " + reader.getLineNumber()); 
/*     */             } else {
/* 123 */               srcDesc = null;
/* 124 */               dstName = cols[0];
/* 125 */               dstDesc = null;
/*     */             } 
/*     */             
/* 128 */             if (dstName == null) throw new IOException("missing class-/name-b in line " + reader.getLineNumber());
/*     */             
/* 130 */             int dstSepPos = dstName.lastIndexOf('/');
/* 131 */             if (dstSepPos <= 0 || dstSepPos == dstName.length() - 1) throw new IOException("invalid class-/name-b in line " + reader.getLineNumber());
/*     */             
/* 133 */             String srcOwner = src.substring(0, srcSepPos);
/* 134 */             String dstOwner = dstName.substring(0, dstSepPos);
/* 135 */             boolean classVisitRequired = (!srcOwner.equals(lastClassSrcName) || !dstOwner.equals(lastClassDstName));
/*     */             
/* 137 */             if (classVisitRequired) {
/* 138 */               if (classContentVisitPending) {
/* 139 */                 memoryMappingTree.visitElementContent(MappedElementKind.CLASS);
/* 140 */                 classContentVisitPending = false;
/*     */               } 
/*     */               
/* 143 */               if (!memoryMappingTree.visitClass(srcOwner)) {
/* 144 */                 lastClassSrcName = srcOwner;
/*     */                 
/*     */                 continue;
/*     */               } 
/* 148 */               classContentVisitPending = true;
/*     */             } 
/*     */             
/* 151 */             lastClassSrcName = srcOwner;
/*     */             
/* 153 */             if (classVisitRequired) {
/* 154 */               memoryMappingTree.visitDstName(MappedElementKind.CLASS, 0, dstOwner);
/* 155 */               lastClassDstName = dstOwner;
/*     */             } 
/*     */             
/* 158 */             if (classContentVisitPending) {
/* 159 */               classContentVisitPending = false;
/* 160 */               if (!memoryMappingTree.visitElementContent(MappedElementKind.CLASS))
/*     */                 continue; 
/*     */             } 
/* 163 */             String srcName = src.substring(srcSepPos + 1);
/*     */             
/* 165 */             if ((isMethod && memoryMappingTree.visitMethod(srcName, srcDesc)) || (!isMethod && memoryMappingTree
/* 166 */               .visitField(srcName, srcDesc))) {
/* 167 */               MappedElementKind kind = isMethod ? MappedElementKind.METHOD : MappedElementKind.FIELD;
/* 168 */               memoryMappingTree.visitDstName(kind, 0, dstName.substring(dstSepPos + 1));
/* 169 */               memoryMappingTree.visitDstDesc(kind, 0, dstDesc);
/* 170 */               memoryMappingTree.visitElementContent(kind);
/*     */             } 
/*     */           } 
/* 173 */         } while (reader.nextLine(0));
/*     */         
/* 175 */         if (classContentVisitPending) {
/* 176 */           memoryMappingTree.visitElementContent(MappedElementKind.CLASS);
/*     */         }
/*     */       } 
/*     */       
/* 180 */       if (memoryMappingTree.visitEnd())
/*     */         break; 
/* 182 */       if (!readerMarked) {
/* 183 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/*     */       
/* 186 */       int markIdx = reader.reset();
/* 187 */       assert markIdx == 1;
/*     */     } 
/*     */     
/* 190 */     if (parentVisitor != null)
/* 191 */       ((MappingTree)memoryMappingTree).accept(parentVisitor); 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\srg\SrgFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */