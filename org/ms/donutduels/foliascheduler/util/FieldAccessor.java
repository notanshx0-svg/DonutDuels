/*     */ package org.ms.donutduels.foliascheduler.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldAccessor
/*     */ {
/*     */   @NotNull
/*     */   private final Field field;
/*     */   
/*     */   public FieldAccessor(@NotNull Field field) {
/*  20 */     this.field = field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Field getField() {
/*  29 */     return this.field;
/*     */   }
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
/*     */   public boolean getBoolean(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/*  46 */       return this.field.getBoolean(obj);
/*  47 */     } catch (IllegalAccessException e) {
/*  48 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   @Nullable
/*     */   public Object get(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/*  83 */       return this.field.get(obj);
/*  84 */     } catch (IllegalAccessException e) {
/*  85 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void set(@Nullable Object obj, @Nullable Object value) throws IllegalArgumentException {
/*     */     try {
/* 134 */       this.field.set(obj, value);
/* 135 */     } catch (IllegalAccessException e) {
/* 136 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setFloat(@Nullable Object obj, float f) throws IllegalArgumentException {
/*     */     try {
/* 155 */       this.field.setFloat(obj, f);
/* 156 */     } catch (IllegalAccessException e) {
/* 157 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public byte getByte(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/* 175 */       return this.field.getByte(obj);
/* 176 */     } catch (IllegalAccessException e) {
/* 177 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setBoolean(@Nullable Object obj, boolean z) throws IllegalArgumentException {
/*     */     try {
/* 196 */       this.field.setBoolean(obj, z);
/* 197 */     } catch (IllegalAccessException e) {
/* 198 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public char getChar(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/* 216 */       return this.field.getChar(obj);
/* 217 */     } catch (IllegalAccessException e) {
/* 218 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setDouble(@Nullable Object obj, double d) throws IllegalArgumentException {
/*     */     try {
/* 237 */       this.field.setDouble(obj, d);
/* 238 */     } catch (IllegalAccessException e) {
/* 239 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setByte(@Nullable Object obj, byte b) throws IllegalArgumentException {
/*     */     try {
/* 258 */       this.field.setByte(obj, b);
/* 259 */     } catch (IllegalAccessException e) {
/* 260 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public short getShort(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/* 278 */       return this.field.getShort(obj);
/* 279 */     } catch (IllegalAccessException e) {
/* 280 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setChar(@Nullable Object obj, char c) throws IllegalArgumentException {
/*     */     try {
/* 299 */       this.field.setChar(obj, c);
/* 300 */     } catch (IllegalAccessException e) {
/* 301 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public int getInt(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/* 319 */       return this.field.getInt(obj);
/* 320 */     } catch (IllegalAccessException e) {
/* 321 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public long getLong(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/* 339 */       return this.field.getLong(obj);
/* 340 */     } catch (IllegalAccessException e) {
/* 341 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setShort(@Nullable Object obj, short s) throws IllegalArgumentException {
/*     */     try {
/* 360 */       this.field.setShort(obj, s);
/* 361 */     } catch (IllegalAccessException e) {
/* 362 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setInt(@Nullable Object obj, int i) throws IllegalArgumentException {
/*     */     try {
/* 381 */       this.field.setInt(obj, i);
/* 382 */     } catch (IllegalAccessException e) {
/* 383 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public float getFloat(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/* 401 */       return this.field.getFloat(obj);
/* 402 */     } catch (IllegalAccessException e) {
/* 403 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public double getDouble(@Nullable Object obj) throws IllegalArgumentException {
/*     */     try {
/* 421 */       return this.field.getDouble(obj);
/* 422 */     } catch (IllegalAccessException e) {
/* 423 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   public void setLong(@Nullable Object obj, long l) throws IllegalArgumentException {
/*     */     try {
/* 442 */       this.field.setLong(obj, l);
/* 443 */     } catch (IllegalAccessException e) {
/* 444 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliaschedule\\util\FieldAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */