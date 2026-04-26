/*     */ package org.ms.donutduels.foliascheduler.mappingio;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.MappingFormat;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.enigma.EnigmaDirReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.enigma.EnigmaFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.intellij.MigrationMapFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.jobf.JobfFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.proguard.ProGuardFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.simple.RecafSimpleFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.srg.JamFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.srg.SrgFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.srg.TsrgFileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.tiny.Tiny1FileReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.format.tiny.Tiny2FileReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MappingReader
/*     */ {
/*     */   private static final int DETECT_HEADER_LEN = 4096;
/*     */   
/*     */   @Nullable
/*     */   public static MappingFormat detectFormat(Path file) throws IOException {
/*  50 */     if (Files.isDirectory(file, new java.nio.file.LinkOption[0])) {
/*  51 */       return MappingFormat.ENIGMA_DIR;
/*     */     }
/*     */     
/*  54 */     Reader reader = new InputStreamReader(Files.newInputStream(file, new java.nio.file.OpenOption[0]), StandardCharsets.UTF_8); 
/*  55 */     try { String fileName = file.getFileName().toString();
/*  56 */       int dotIdx = fileName.lastIndexOf('.');
/*  57 */       String fileExt = (dotIdx >= 0) ? fileName.substring(dotIdx + 1) : null;
/*     */       
/*  59 */       MappingFormat mappingFormat = detectFormat(reader, fileExt);
/*  60 */       reader.close(); return mappingFormat; }
/*     */     catch (Throwable throwable) { try { reader.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/*  65 */      } @Nullable public static MappingFormat detectFormat(Reader reader) throws IOException { return detectFormat(reader, null); }
/*     */ 
/*     */   
/*     */   private static MappingFormat detectFormat(Reader reader, @Nullable String fileExt) throws IOException {
/*  69 */     char[] buffer = new char[4096];
/*  70 */     int pos = 0;
/*     */ 
/*     */ 
/*     */     
/*  74 */     BufferedReader br = (reader instanceof BufferedReader) ? (BufferedReader)reader : new BufferedReader(reader);
/*     */     
/*  76 */     br.mark(4096);
/*     */     int len;
/*  78 */     while (pos < buffer.length && (
/*  79 */       len = br.read(buffer, pos, buffer.length - pos)) >= 0) {
/*  80 */       pos += len;
/*     */     }
/*     */     
/*  83 */     br.reset();
/*  84 */     if (pos < 3) return null;
/*     */     
/*  86 */     switch (String.valueOf(buffer, 0, 3)) {
/*     */       case "v1\t":
/*  88 */         return MappingFormat.TINY_FILE;
/*     */       case "tin":
/*  90 */         return MappingFormat.TINY_2_FILE;
/*     */       case "tsr":
/*  92 */         return MappingFormat.TSRG_2_FILE;
/*     */       case "CLA":
/*  94 */         return MappingFormat.ENIGMA_FILE;
/*     */       case "PK:":
/*     */       case "CL:":
/*     */       case "FD:":
/*     */       case "MD:":
/*  99 */         return detectSrgOrXsrg(br, fileExt);
/*     */       case "CL ":
/*     */       case "FD ":
/*     */       case "MD ":
/*     */       case "MP ":
/* 104 */         return MappingFormat.JAM_FILE;
/*     */     } 
/*     */     
/* 107 */     String headerStr = String.valueOf(buffer, 0, pos);
/*     */     
/* 109 */     if (headerStr.contains("<migrationMap>"))
/* 110 */       return MappingFormat.INTELLIJ_MIGRATION_MAP_FILE; 
/* 111 */     if ((headerStr.startsWith("p ") || headerStr
/* 112 */       .startsWith("c ") || headerStr
/* 113 */       .startsWith("f ") || headerStr
/* 114 */       .startsWith("m ")) && headerStr
/* 115 */       .contains(" = "))
/* 116 */       return MappingFormat.JOBF_FILE; 
/* 117 */     if (headerStr.contains(" -> "))
/* 118 */       return MappingFormat.PROGUARD_FILE; 
/* 119 */     if (headerStr.contains("\n\t")) {
/* 120 */       return MappingFormat.TSRG_FILE;
/*     */     }
/*     */     
/* 123 */     if (fileExt != null && 
/* 124 */       fileExt.equals(MappingFormat.CSRG_FILE.fileExt)) return MappingFormat.CSRG_FILE;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static MappingFormat detectSrgOrXsrg(BufferedReader reader, @Nullable String fileExt) throws IOException {
/*     */     String line;
/* 135 */     while ((line = reader.readLine()) != null) {
/* 136 */       if (line.startsWith("FD:")) {
/* 137 */         String[] parts = line.split(" ");
/*     */         
/* 139 */         if (parts.length < 5 || 
/* 140 */           isEmptyOrStartsWithHash(parts[3]) || 
/* 141 */           isEmptyOrStartsWithHash(parts[4])) {
/* 142 */           return MappingFormat.SRG_FILE;
/*     */         }
/*     */         
/* 145 */         return MappingFormat.XSRG_FILE;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     return MappingFormat.XSRG_FILE.fileExt.equals(fileExt) ? MappingFormat.XSRG_FILE : MappingFormat.SRG_FILE;
/*     */   }
/*     */   
/*     */   private static boolean isEmptyOrStartsWithHash(String string) {
/* 153 */     return (string.isEmpty() || string.startsWith("#"));
/*     */   }
/*     */   
/*     */   public static List<String> getNamespaces(Path file) throws IOException {
/* 157 */     return getNamespaces(file, (MappingFormat)null);
/*     */   }
/*     */   
/*     */   public static List<String> getNamespaces(Path file, MappingFormat format) throws IOException {
/* 161 */     if (format == null) {
/* 162 */       format = detectFormat(file);
/* 163 */       if (format == null) throw new IOException("invalid/unsupported mapping format");
/*     */     
/*     */     } 
/* 166 */     if (format.features().hasNamespaces()) {
/* 167 */       Reader reader = Files.newBufferedReader(file); 
/* 168 */       try { List<String> list = getNamespaces(reader, format);
/* 169 */         if (reader != null) reader.close();  return list; } catch (Throwable throwable) { if (reader != null)
/*     */           try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 171 */     }  return Arrays.asList(new String[] { "source", "target" });
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getNamespaces(Reader reader) throws IOException {
/* 176 */     return getNamespaces(reader, (MappingFormat)null);
/*     */   }
/*     */   
/*     */   public static List<String> getNamespaces(Reader reader, MappingFormat format) throws IOException {
/* 180 */     if (format == null) {
/* 181 */       if (!reader.markSupported()) reader = new BufferedReader(reader); 
/* 182 */       reader.mark(4096);
/* 183 */       format = detectFormat(reader);
/* 184 */       reader.reset();
/* 185 */       if (format == null) throw new IOException("invalid/unsupported mapping format");
/*     */     
/*     */     } 
/* 188 */     if (format.features().hasNamespaces()) {
/* 189 */       checkReaderCompatible(format);
/*     */       
/* 191 */       switch (format) {
/*     */         case TINY_FILE:
/* 193 */           return Tiny1FileReader.getNamespaces(reader);
/*     */         case TINY_2_FILE:
/* 195 */           return Tiny2FileReader.getNamespaces(reader);
/*     */         case TSRG_2_FILE:
/* 197 */           return TsrgFileReader.getNamespaces(reader);
/*     */       } 
/* 199 */       throw new IllegalStateException();
/*     */     } 
/*     */     
/* 202 */     return Arrays.asList(new String[] { "source", "target" });
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
/*     */   public static void read(Path path, MappingVisitor visitor) throws IOException {
/* 214 */     read(path, (MappingFormat)null, visitor);
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
/*     */   public static void read(Path path, MappingFormat format, MappingVisitor visitor) throws IOException {
/* 226 */     if (format == null) {
/* 227 */       format = detectFormat(path);
/* 228 */       if (format == null) throw new IOException("invalid/unsupported mapping format");
/*     */     
/*     */     } 
/* 231 */     if (format.hasSingleFile())
/* 232 */     { Reader reader = Files.newBufferedReader(path); 
/* 233 */       try { read(reader, format, visitor);
/* 234 */         if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null)
/*     */           try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }
/* 236 */     else { switch (format) {
/*     */         case ENIGMA_DIR:
/* 238 */           EnigmaDirReader.read(path, visitor);
/*     */           return;
/*     */       } 
/* 241 */       throw new IllegalStateException(); }
/*     */   
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
/*     */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/* 254 */     read(reader, (MappingFormat)null, visitor);
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
/*     */   public static void read(Reader reader, MappingFormat format, MappingVisitor visitor) throws IOException {
/* 266 */     if (format == null) {
/* 267 */       if (!reader.markSupported()) reader = new BufferedReader(reader); 
/* 268 */       reader.mark(4096);
/* 269 */       format = detectFormat(reader);
/* 270 */       reader.reset();
/* 271 */       if (format == null) throw new IOException("invalid/unsupported mapping format");
/*     */     
/*     */     } 
/* 274 */     checkReaderCompatible(format);
/*     */     
/* 276 */     switch (format) {
/*     */       case TINY_FILE:
/* 278 */         Tiny1FileReader.read(reader, visitor);
/*     */         return;
/*     */       case TINY_2_FILE:
/* 281 */         Tiny2FileReader.read(reader, visitor);
/*     */         return;
/*     */       case ENIGMA_FILE:
/* 284 */         EnigmaFileReader.read(reader, visitor);
/*     */         return;
/*     */       case SRG_FILE:
/*     */       case XSRG_FILE:
/* 288 */         SrgFileReader.read(reader, visitor);
/*     */         return;
/*     */       case JAM_FILE:
/* 291 */         JamFileReader.read(reader, visitor);
/*     */         return;
/*     */       case TSRG_2_FILE:
/*     */       case CSRG_FILE:
/*     */       case TSRG_FILE:
/* 296 */         TsrgFileReader.read(reader, visitor);
/*     */         return;
/*     */       case PROGUARD_FILE:
/* 299 */         ProGuardFileReader.read(reader, visitor);
/*     */         return;
/*     */       case INTELLIJ_MIGRATION_MAP_FILE:
/* 302 */         MigrationMapFileReader.read(reader, visitor);
/*     */         return;
/*     */       case RECAF_SIMPLE_FILE:
/* 305 */         RecafSimpleFileReader.read(reader, visitor);
/*     */         return;
/*     */       case JOBF_FILE:
/* 308 */         JobfFileReader.read(reader, visitor);
/*     */         return;
/*     */     } 
/* 311 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkReaderCompatible(MappingFormat format) throws IOException {
/* 316 */     if (!format.hasSingleFile())
/* 317 */       throw new IOException("can't read mapping format " + format.name + " using a Reader, use the Path based API"); 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\MappingReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */