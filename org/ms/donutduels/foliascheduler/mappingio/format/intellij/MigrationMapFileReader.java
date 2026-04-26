/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.intellij;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MigrationMapFileReader
/*     */ {
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/*  48 */     read(reader, "source", "target", visitor);
/*     */   }
/*     */   
/*     */   public static void read(Reader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*  52 */     BufferedReader br = (reader instanceof BufferedReader) ? (BufferedReader)reader : new BufferedReader(reader);
/*     */     
/*  54 */     read(br, sourceNs, targetNs, visitor);
/*     */   }
/*     */   
/*     */   private static void read(BufferedReader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*     */     try {
/*  59 */       read0(reader, sourceNs, targetNs, visitor);
/*  60 */     } catch (XMLStreamException e) {
/*  61 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void read0(BufferedReader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException, XMLStreamException {
/*  66 */     CharArrayReader parentReader = null;
/*     */     
/*  68 */     if (visitor.getFlags().contains(MappingFlag.NEEDS_MULTIPLE_PASSES)) {
/*  69 */       char[] buffer = new char[100000];
/*  70 */       int pos = 0;
/*     */       
/*     */       int len;
/*  73 */       while ((len = reader.read(buffer, pos, buffer.length - pos)) >= 0) {
/*  74 */         pos += len;
/*     */         
/*  76 */         if (pos == buffer.length) buffer = Arrays.copyOf(buffer, buffer.length * 2);
/*     */       
/*     */       } 
/*  79 */       parentReader = new CharArrayReader(buffer, 0, pos);
/*  80 */       reader = new CustomBufferedReader(parentReader);
/*     */     } 
/*     */     
/*  83 */     XMLInputFactory factory = XMLInputFactory.newInstance();
/*     */     
/*     */     while (true) {
/*  86 */       XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);
/*     */       
/*     */       boolean visitHeader;
/*  89 */       if (visitHeader = visitor.visitHeader()) {
/*  90 */         visitor.visitNamespaces(sourceNs, Collections.singletonList(targetNs));
/*     */       }
/*     */       
/*  93 */       if (visitor.visitContent()) {
/*  94 */         int depth = 0;
/*     */         
/*  96 */         while (xmlReader.hasNext()) {
/*  97 */           String name, type, srcName, dstName; int event = xmlReader.next();
/*     */           
/*  99 */           switch (event) {
/*     */             case 1:
/* 101 */               name = xmlReader.getLocalName();
/*     */               
/* 103 */               if (depth != (name.equals("migrationMap") ? 0 : 1)) {
/* 104 */                 throw new IOException("unexpected depth at line " + xmlReader.getLocation().getLineNumber());
/*     */               }
/*     */               
/* 107 */               depth++;
/*     */               
/* 109 */               switch (name) {
/*     */                 case "name":
/*     */                 case "order":
/*     */                 case "description":
/* 113 */                   if (visitHeader) {
/* 114 */                     String value = xmlReader.getAttributeValue(null, "value");
/*     */                     
/* 116 */                     if (name.equals("order")) name = "migrationmap:order"; 
/* 117 */                     if (name.equals("name") && value.equals("Unnamed migration map"))
/*     */                       continue; 
/* 119 */                     visitor.visitMetadata(name, value);
/*     */                   } 
/*     */ 
/*     */                 
/*     */                 case "entry":
/* 124 */                   type = xmlReader.getAttributeValue(null, "type");
/*     */                   
/* 126 */                   if (type == null || type.isEmpty()) throw new IOException("missing/empty type attribute at line " + xmlReader.getLocation().getLineNumber()); 
/* 127 */                   if (type.equals("package"))
/* 128 */                     continue;  if (!type.equals("class")) throw new IOException("unexpected entry type " + type + " at line " + xmlReader.getLocation().getLineNumber());
/*     */                   
/* 130 */                   srcName = xmlReader.getAttributeValue(null, "oldName");
/* 131 */                   dstName = xmlReader.getAttributeValue(null, "newName");
/*     */ 
/*     */                   
/* 134 */                   if (srcName == null || srcName.isEmpty()) throw new IOException("missing/empty oldName attribute at line " + xmlReader.getLocation().getLineNumber()); 
/* 135 */                   if (dstName == null || dstName.isEmpty()) throw new IOException("missing/empty newName attribute at line " + xmlReader.getLocation().getLineNumber());
/*     */                   
/* 137 */                   srcName = srcName.replace('.', '/');
/* 138 */                   dstName = dstName.replace('.', '/');
/*     */                   
/* 140 */                   if (visitor.visitClass(srcName)) {
/* 141 */                     visitor.visitDstName(MappedElementKind.CLASS, 0, dstName);
/* 142 */                     visitor.visitElementContent(MappedElementKind.CLASS);
/*     */                   } 
/*     */               } 
/*     */             
/*     */             
/*     */ 
/*     */             
/*     */             case 2:
/* 150 */               depth--;
/*     */           } 
/*     */ 
/*     */         
/*     */         } 
/*     */       } 
/* 156 */       if (visitor.visitEnd()) {
/* 157 */         if (parentReader != null) {
/* 158 */           ((CustomBufferedReader)reader).forceClose();
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 164 */       if (parentReader == null) {
/* 165 */         throw new IllegalStateException("repeated visitation requested without NEEDS_MULTIPLE_PASSES");
/*     */       }
/* 167 */       parentReader.reset();
/* 168 */       reader = new CustomBufferedReader(parentReader);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class CustomBufferedReader
/*     */     extends BufferedReader {
/*     */     private CustomBufferedReader(Reader in) {
/* 175 */       super(in);
/*     */     }
/*     */     
/*     */     public void forceClose() throws IOException {
/* 179 */       super.close();
/*     */     }
/*     */     
/*     */     public void close() throws IOException {}
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\intellij\MigrationMapFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */