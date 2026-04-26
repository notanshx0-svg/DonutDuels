/*    */ package org.ms.donutduels.foliascheduler.mappingio.tree;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Experimental
/*    */ public interface HierarchyInfoProvider<T>
/*    */ {
/*    */   String getNamespace();
/*    */   
/*    */   @Nullable
/*    */   String resolveField(String paramString1, String paramString2, @Nullable String paramString3);
/*    */   
/*    */   @Nullable
/*    */   String resolveMethod(String paramString1, String paramString2, @Nullable String paramString3);
/*    */   
/*    */   @Nullable
/*    */   T getMethodHierarchy(String paramString1, String paramString2, @Nullable String paramString3);
/*    */   
/*    */   @Nullable
/*    */   default T getMethodHierarchy(MappingTreeView.MethodMappingView method) {
/* 48 */     int nsId = method.getTree().getNamespaceId(getNamespace());
/* 49 */     if (nsId == -2) throw new IllegalArgumentException("disassociated namespace");
/*    */     
/* 51 */     String owner = method.getOwner().getName(nsId);
/* 52 */     String name = method.getName(nsId);
/* 53 */     String desc = method.getDesc(nsId);
/*    */     
/* 55 */     if (owner == null || name == null) {
/* 56 */       return null;
/*    */     }
/* 58 */     return getMethodHierarchy(owner, name, desc);
/*    */   }
/*    */ 
/*    */   
/*    */   int getHierarchySize(T paramT);
/*    */ 
/*    */   
/*    */   Collection<? extends MappingTreeView.MethodMappingView> getHierarchyMethods(T paramT, MappingTreeView paramMappingTreeView);
/*    */   
/*    */   default Collection<? extends MappingTree.MethodMapping> getHierarchyMethods(T hierarchy, MappingTree tree) {
/* 68 */     return (Collection)getHierarchyMethods(hierarchy, tree);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\tree\HierarchyInfoProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */