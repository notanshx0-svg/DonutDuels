/*     */ package org.ms.donutduels.foliascheduler.mappingio.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public final class VisitOrder
/*     */ {
/*     */   public static VisitOrder createByInputOrder() {
/*  48 */     return new VisitOrder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VisitOrder createByName() {
/*  57 */     return (new VisitOrder())
/*  58 */       .classesBySrcName()
/*  59 */       .fieldsBySrcNameDesc()
/*  60 */       .methodsBySrcNameDesc()
/*  61 */       .methodArgsByLvIndex()
/*  62 */       .methodVarsByLvIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VisitOrder classComparator(Comparator<MappingTreeView.ClassMappingView> comparator) {
/*  68 */     this.classComparator = comparator;
/*     */     
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public VisitOrder classesBySrcName() {
/*  74 */     return classComparator(compareBySrcName());
/*     */   }
/*     */   
/*     */   public VisitOrder classesBySrcNameShortFirst() {
/*  78 */     return classComparator(compareBySrcNameShortFirst());
/*     */   }
/*     */   
/*     */   public VisitOrder fieldComparator(Comparator<MappingTreeView.FieldMappingView> comparator) {
/*  82 */     this.fieldComparator = comparator;
/*     */     
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public VisitOrder fieldsBySrcNameDesc() {
/*  88 */     return fieldComparator(compareBySrcNameDesc());
/*     */   }
/*     */   
/*     */   public VisitOrder methodComparator(Comparator<MappingTreeView.MethodMappingView> comparator) {
/*  92 */     this.methodComparator = comparator;
/*     */     
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public VisitOrder methodsBySrcNameDesc() {
/*  98 */     return methodComparator(compareBySrcNameDesc());
/*     */   }
/*     */   
/*     */   public VisitOrder methodArgComparator(Comparator<MappingTreeView.MethodArgMappingView> comparator) {
/* 102 */     this.methodArgComparator = comparator;
/*     */     
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public VisitOrder methodArgsByPosition() {
/* 108 */     return methodArgComparator(Comparator.comparingInt(MappingTreeView.MethodArgMappingView::getArgPosition));
/*     */   }
/*     */   
/*     */   public VisitOrder methodArgsByLvIndex() {
/* 112 */     return methodArgComparator(Comparator.comparingInt(MappingTreeView.MethodArgMappingView::getLvIndex));
/*     */   }
/*     */   
/*     */   public VisitOrder methodVarComparator(Comparator<MappingTreeView.MethodVarMappingView> comparator) {
/* 116 */     this.methodVarComparator = comparator;
/*     */     
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public VisitOrder methodVarsByLvtRowIndex() {
/* 122 */     return methodVarComparator(
/* 123 */         Comparator.<MappingTreeView.MethodVarMappingView>comparingInt(MappingTreeView.MethodVarMappingView::getLvIndex)
/* 124 */         .thenComparingInt(MappingTreeView.MethodVarMappingView::getLvtRowIndex));
/*     */   }
/*     */   
/*     */   public VisitOrder methodVarsByLvIndex() {
/* 128 */     return methodVarComparator(
/* 129 */         Comparator.<MappingTreeView.MethodVarMappingView>comparingInt(MappingTreeView.MethodVarMappingView::getLvIndex)
/* 130 */         .thenComparingInt(MappingTreeView.MethodVarMappingView::getStartOpIdx)
/* 131 */         .thenComparingInt(MappingTreeView.MethodVarMappingView::getEndOpIdx));
/*     */   }
/*     */   
/*     */   public VisitOrder methodsFirst(boolean methodsFirst) {
/* 135 */     this.methodsFirst = methodsFirst;
/*     */     
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public VisitOrder fieldsFirst() {
/* 141 */     return methodsFirst(false);
/*     */   }
/*     */   
/*     */   public VisitOrder methodsFirst() {
/* 145 */     return methodsFirst(true);
/*     */   }
/*     */   
/*     */   public VisitOrder methodVarsFirst(boolean varsFirst) {
/* 149 */     this.methodVarsFirst = varsFirst;
/*     */     
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   public VisitOrder methodArgsFirst() {
/* 155 */     return methodVarsFirst(false);
/*     */   }
/*     */   
/*     */   public VisitOrder methodVarsFirst() {
/* 159 */     return methodVarsFirst(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends MappingTreeView.ElementMappingView> Comparator<T> compareBySrcName() {
/* 165 */     return (a, b) -> 
/* 166 */       (a instanceof MappingTreeView.ClassMappingView || b instanceof MappingTreeView.ClassMappingView) ? compareNestaware(a.getSrcName(), b.getSrcName(), false) : compare(a.getSrcName(), b.getSrcName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends MappingTreeView.MemberMappingView> Comparator<T> compareBySrcNameDesc() {
/* 175 */     return (a, b) -> {
/*     */         int cmp = compare(a.getSrcName(), b.getSrcName());
/*     */         return (cmp != 0) ? cmp : compare(a.getSrcDesc(), b.getSrcDesc());
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends MappingTreeView.ElementMappingView> Comparator<T> compareBySrcNameShortFirst() {
/* 183 */     return (a, b) -> 
/* 184 */       (a instanceof MappingTreeView.ClassMappingView || b instanceof MappingTreeView.ClassMappingView) ? compareNestaware(a.getSrcName(), b.getSrcName(), true) : compareShortFirst(a.getSrcName(), b.getSrcName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends MappingTreeView.MemberMappingView> Comparator<T> compareBySrcNameDescShortFirst() {
/* 193 */     return (a, b) -> {
/*     */         int cmp = compareShortFirst(a.getSrcName(), b.getSrcName());
/*     */         return (cmp != 0) ? cmp : compare(a.getSrcDesc(), b.getSrcDesc());
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static int compare(@Nullable String a, @Nullable String b) {
/* 201 */     if (a == null || b == null) return compareNullLast(a, b);
/*     */     
/* 203 */     return ALPHANUM.compare(a, b);
/*     */   }
/*     */   
/*     */   public static int compare(String a, int startA, int endA, String b, int startB, int endB) {
/* 207 */     return ALPHANUM.compare(a.substring(startA, endA), b.substring(startB, endB));
/*     */   }
/*     */   
/*     */   public static int compareShortFirst(@Nullable String a, @Nullable String b) {
/* 211 */     if (a == null || b == null) return compareNullLast(a, b);
/*     */     
/* 213 */     int cmp = a.length() - b.length();
/*     */     
/* 215 */     return (cmp != 0) ? cmp : ALPHANUM.compare(a, b);
/*     */   }
/*     */   
/*     */   public static int compareShortFirst(String a, int startA, int endA, String b, int startB, int endB) {
/* 219 */     int lenA = endA - startA;
/* 220 */     int ret = Integer.compare(lenA, endB - startB);
/* 221 */     if (ret != 0) return ret;
/*     */     
/* 223 */     return ALPHANUM.compare(a.substring(startA, endA), b.substring(startB, endB));
/*     */   }
/*     */   
/*     */   public static int compareNestaware(@Nullable String a, @Nullable String b) {
/* 227 */     return compareNestaware(a, b, false);
/*     */   }
/*     */   
/*     */   public static int compareShortFirstNestaware(@Nullable String a, @Nullable String b) {
/* 231 */     return compareNestaware(a, b, true);
/*     */   }
/*     */   
/*     */   private static int compareNestaware(@Nullable String a, @Nullable String b, boolean shortFirst) {
/* 235 */     if (a == null || b == null) {
/* 236 */       return compareNullLast(a, b);
/*     */     }
/*     */     
/* 239 */     int pos = 0;
/*     */     
/*     */     do {
/* 242 */       int endA = a.indexOf('$', pos);
/* 243 */       int endB = b.indexOf('$', pos);
/*     */       
/* 245 */       int positiveEndA = (endA < 0) ? a.length() : endA;
/* 246 */       int positiveEndB = (endB < 0) ? b.length() : endB;
/*     */ 
/*     */ 
/*     */       
/* 250 */       int ret = shortFirst ? compareShortFirst(a, pos, positiveEndA, b, pos, positiveEndB) : compare(a, pos, positiveEndA, b, pos, positiveEndB);
/*     */       
/* 252 */       if (ret != 0)
/* 253 */         return ret; 
/* 254 */       if (((endA < 0) ? true : false) != ((endB < 0) ? true : false)) {
/* 255 */         return (endA < 0) ? -1 : 1;
/*     */       }
/*     */       
/* 258 */       pos = endA + 1;
/* 259 */     } while (pos > 0);
/*     */     
/* 261 */     return 0;
/*     */   }
/*     */   
/*     */   public static int compareNullLast(@Nullable String a, @Nullable String b) {
/* 265 */     if (a == null) {
/* 266 */       if (b == null) {
/* 267 */         return 0;
/*     */       }
/* 269 */       return 1;
/*     */     } 
/* 271 */     if (b == null) {
/* 272 */       return -1;
/*     */     }
/* 274 */     return ALPHANUM.compare(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends MappingTreeView.ClassMappingView> Collection<T> sortClasses(Collection<T> classes) {
/* 281 */     return sort(classes, (Comparator)this.classComparator);
/*     */   }
/*     */   
/*     */   public <T extends MappingTreeView.FieldMappingView> Collection<T> sortFields(Collection<T> fields) {
/* 285 */     return sort(fields, (Comparator)this.fieldComparator);
/*     */   }
/*     */   
/*     */   public <T extends MappingTreeView.MethodMappingView> Collection<T> sortMethods(Collection<T> methods) {
/* 289 */     return sort(methods, (Comparator)this.methodComparator);
/*     */   }
/*     */   
/*     */   public <T extends MappingTreeView.MethodArgMappingView> Collection<T> sortMethodArgs(Collection<T> args) {
/* 293 */     return sort(args, (Comparator)this.methodArgComparator);
/*     */   }
/*     */   
/*     */   public <T extends MappingTreeView.MethodVarMappingView> Collection<T> sortMethodVars(Collection<T> vars) {
/* 297 */     return sort(vars, (Comparator)this.methodVarComparator);
/*     */   }
/*     */   
/*     */   private static <T> Collection<T> sort(Collection<T> inputs, Comparator<? super T> comparator) {
/* 301 */     if (comparator == null || inputs.size() < 2) return inputs;
/*     */     
/* 303 */     List<T> ret = new ArrayList<>(inputs);
/* 304 */     ret.sort(comparator);
/*     */     
/* 306 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean isMethodsFirst() {
/* 310 */     return this.methodsFirst;
/*     */   }
/*     */   
/*     */   public boolean isMethodVarsFirst() {
/* 314 */     return this.methodVarsFirst;
/*     */   }
/*     */   
/* 317 */   private static final AlphanumericComparator ALPHANUM = new AlphanumericComparator(Locale.ROOT);
/*     */   private Comparator<MappingTreeView.ClassMappingView> classComparator;
/*     */   private Comparator<MappingTreeView.FieldMappingView> fieldComparator;
/*     */   private Comparator<MappingTreeView.MethodMappingView> methodComparator;
/*     */   private Comparator<MappingTreeView.MethodArgMappingView> methodArgComparator;
/*     */   private Comparator<MappingTreeView.MethodVarMappingView> methodVarComparator;
/*     */   private boolean methodsFirst;
/*     */   private boolean methodVarsFirst;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\tree\VisitOrder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */