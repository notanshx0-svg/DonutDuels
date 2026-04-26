/*     */ package org.ms.donutduels.foliascheduler.mappingio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public interface MappingVisitor
/*     */ {
/*     */   default Set<MappingFlag> getFlags() {
/*  56 */     return MappingFlag.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void reset() {
/*  63 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitHeader() throws IOException {
/*  72 */     return true;
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
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitClass(String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitField(String paramString1, @Nullable String paramString2) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitMethod(String paramString1, @Nullable String paramString2) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitMethodArg(int paramInt1, int paramInt2, @Nullable String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean visitMethodVar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitEnd() throws IOException {
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void visitDstName(MappedElementKind paramMappedElementKind, int paramInt, String paramString) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void visitDstDesc(MappedElementKind targetKind, int namespace, String desc) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/* 164 */     return true;
/*     */   }
/*     */   
/*     */   void visitComment(MappedElementKind paramMappedElementKind, String paramString) throws IOException;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\MappingVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */