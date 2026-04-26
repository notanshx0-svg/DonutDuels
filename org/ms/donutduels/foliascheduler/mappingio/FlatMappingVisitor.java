/*     */ package org.ms.donutduels.foliascheduler.mappingio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.adapter.FlatAsRegularMappingVisitor;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.adapter.RegularAsFlatMappingVisitor;
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
/*     */ public interface FlatMappingVisitor
/*     */ {
/*     */   default Set<MappingFlag> getFlags() {
/*  36 */     return MappingFlag.NONE;
/*     */   }
/*     */   
/*     */   default void reset() {
/*  40 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitHeader() throws IOException {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void visitNamespaces(String paramString, List<String> paramList) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   default void visitMetadata(String key, @Nullable String value) throws IOException {}
/*     */ 
/*     */   
/*     */   default boolean visitContent() throws IOException {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitClass(String paramString, @Nullable String[] paramArrayOfString) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   void visitClassComment(String paramString1, @Nullable String[] paramArrayOfString, String paramString2) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitField(String paramString1, String paramString2, @Nullable String paramString3, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   void visitFieldComment(String paramString1, String paramString2, @Nullable String paramString3, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3, String paramString4) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitMethod(String paramString1, String paramString2, @Nullable String paramString3, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   void visitMethodComment(String paramString1, String paramString2, @Nullable String paramString3, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3, String paramString4) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitMethodArg(String paramString1, String paramString2, @Nullable String paramString3, int paramInt1, int paramInt2, @Nullable String paramString4, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3, String[] paramArrayOfString4) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   void visitMethodArgComment(String paramString1, String paramString2, @Nullable String paramString3, int paramInt1, int paramInt2, @Nullable String paramString4, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3, @Nullable String[] paramArrayOfString4, String paramString5) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitMethodVar(String paramString1, String paramString2, @Nullable String paramString3, int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable String paramString4, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3, String[] paramArrayOfString4) throws IOException;
/*     */ 
/*     */   
/*     */   void visitMethodVarComment(String paramString1, String paramString2, @Nullable String paramString3, int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable String paramString4, @Nullable String[] paramArrayOfString1, @Nullable String[] paramArrayOfString2, @Nullable String[] paramArrayOfString3, @Nullable String[] paramArrayOfString4, String paramString5) throws IOException;
/*     */ 
/*     */   
/*     */   default boolean visitEnd() throws IOException {
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default MappingVisitor asRegularVisitor() {
/* 112 */     return (MappingVisitor)new FlatAsRegularMappingVisitor(this);
/*     */   }
/*     */   
/*     */   static FlatMappingVisitor fromRegularVisitor(MappingVisitor visitor) {
/* 116 */     return (FlatMappingVisitor)new RegularAsFlatMappingVisitor(visitor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitField(String srcClsName, String srcName, @Nullable String srcDesc, String[] dstNames) throws IOException {
/* 122 */     Objects.requireNonNull(dstNames);
/* 123 */     return visitField(srcClsName, srcName, srcDesc, (String[])null, dstNames, (String[])null);
/*     */   }
/*     */   
/*     */   default boolean visitMethod(String srcClsName, String srcName, @Nullable String srcDesc, String[] dstNames) throws IOException {
/* 127 */     Objects.requireNonNull(dstNames);
/* 128 */     return visitMethod(srcClsName, srcName, srcDesc, (String[])null, dstNames, (String[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethodArg(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcName, String[] dstNames) throws IOException {
/* 134 */     Objects.requireNonNull(dstNames);
/* 135 */     return visitMethodArg(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcName, (String[])null, (String[])null, (String[])null, dstNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethodVar(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName, String[] dstNames) throws IOException {
/* 144 */     Objects.requireNonNull(dstNames);
/* 145 */     return visitMethodVar(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName, (String[])null, (String[])null, (String[])null, dstNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitClass(String srcName, String dstName) throws IOException {
/* 154 */     return visitClass(srcName, MappingUtil.toArray(dstName));
/*     */   }
/*     */   
/*     */   default void visitClassComment(String srcName, String comment) throws IOException {
/* 158 */     visitClassComment(srcName, (String)null, comment);
/*     */   }
/*     */   
/*     */   default void visitClassComment(String srcName, @Nullable String dstName, String comment) throws IOException {
/* 162 */     visitClassComment(srcName, MappingUtil.toArray(dstName), comment);
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean visitField(String srcClsName, String srcName, @Nullable String srcDesc, String dstName) throws IOException {
/* 167 */     return visitField(srcClsName, srcName, srcDesc, (String)null, dstName, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitField(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, String dstName, @Nullable String dstDesc) throws IOException {
/* 173 */     return visitField(srcClsName, srcName, srcDesc, 
/* 174 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstName), MappingUtil.toArray(dstDesc));
/*     */   }
/*     */ 
/*     */   
/*     */   default void visitFieldComment(String srcClsName, String srcName, @Nullable String srcDesc, String comment) throws IOException {
/* 179 */     visitFieldComment(srcClsName, srcName, srcDesc, (String)null, (String)null, (String)null, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void visitFieldComment(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, @Nullable String dstName, @Nullable String dstDesc, String comment) throws IOException {
/* 187 */     visitFieldComment(srcClsName, srcName, srcDesc, 
/* 188 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstName), MappingUtil.toArray(dstDesc), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethod(String srcClsName, String srcName, @Nullable String srcDesc, String dstName) throws IOException {
/* 194 */     return visitMethod(srcClsName, srcName, srcDesc, (String)null, dstName, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethod(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, String dstName, @Nullable String dstDesc) throws IOException {
/* 200 */     return visitMethod(srcClsName, srcName, srcDesc, 
/* 201 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstName), MappingUtil.toArray(dstDesc));
/*     */   }
/*     */ 
/*     */   
/*     */   default void visitMethodComment(String srcClsName, String srcName, @Nullable String srcDesc, String comment) throws IOException {
/* 206 */     visitMethodComment(srcClsName, srcName, srcDesc, (String)null, (String)null, (String)null, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void visitMethodComment(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, @Nullable String dstName, @Nullable String dstDesc, String comment) throws IOException {
/* 214 */     visitMethodComment(srcClsName, srcName, srcDesc, 
/* 215 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstName), MappingUtil.toArray(dstDesc), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethodArg(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcName, String dstName) throws IOException {
/* 221 */     return visitMethodArg(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcName, (String)null, (String)null, (String)null, dstName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethodArg(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, String dstName) throws IOException {
/* 230 */     return visitMethodArg(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcName, 
/*     */         
/* 232 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstMethodName), MappingUtil.toArray(dstMethodDesc), MappingUtil.toArray(dstName));
/*     */   }
/*     */ 
/*     */   
/*     */   default void visitMethodArgComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcName, String comment) throws IOException {
/* 237 */     visitMethodArgComment(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcName, (String)null, (String)null, (String)null, (String)null, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void visitMethodArgComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, @Nullable String dstName, String comment) throws IOException {
/* 248 */     visitMethodArgComment(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcName, 
/* 249 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstMethodName), MappingUtil.toArray(dstMethodDesc), MappingUtil.toArray(dstName), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethodVar(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName, String dstName) throws IOException {
/* 256 */     return visitMethodVar(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName, (String)null, (String)null, (String)null, dstName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitMethodVar(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, String dstName) throws IOException {
/* 265 */     return visitMethodVar(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName, 
/*     */         
/* 267 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstMethodName), MappingUtil.toArray(dstMethodDesc), MappingUtil.toArray(dstName));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void visitMethodVarComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName, String comment) throws IOException {
/* 273 */     visitMethodVarComment(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName, (String)null, (String)null, (String)null, (String)null, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void visitMethodVarComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, @Nullable String dstName, String comment) throws IOException {
/* 283 */     visitMethodVarComment(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName, 
/*     */         
/* 285 */         MappingUtil.toArray(dstClsName), MappingUtil.toArray(dstMethodName), MappingUtil.toArray(dstMethodDesc), MappingUtil.toArray(dstName), comment);
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\FlatMappingVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */