/*     */ package org.ms.donutduels.foliascheduler.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.Bukkit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MinecraftVersions
/*     */ {
/*     */   @NotNull
/*  32 */   private static final Map<String, Update> allUpdates = new LinkedHashMap<>(); @NotNull
/*  33 */   private static final Map<String, Version> allVersions = new LinkedHashMap<>(); @Nullable
/*  34 */   private static Version CURRENT = null; @NotNull
/*     */   public static final Update WORLD_OF_COLOR;
/*     */   @NotNull
/*     */   public static final Update UPDATE_AQUATIC;
/*     */   @NotNull
/*     */   public static final Update VILLAGE_AND_PILLAGE;
/*     */   @NotNull
/*     */   public static final Update BUZZY_BEES;
/*     */   @NotNull
/*     */   public static final Update NETHER_UPDATE;
/*     */   
/*     */   @NotNull
/*     */   private static Update registerUpdate(@NotNull Update update) {
/*  47 */     allUpdates.put(update.toString(), update);
/*  48 */     for (Version version : update.versions) {
/*  49 */       allVersions.put(version.toString(), version);
/*     */     }
/*  51 */     return update;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static final Update CAVES_AND_CLIFFS_1;
/*     */   
/*     */   @NotNull
/*     */   public static final Update CAVES_AND_CLIFFS_2;
/*     */   
/*     */   @NotNull
/*     */   public static Map<String, Update> updates() {
/*  63 */     return Collections.unmodifiableMap(allUpdates);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public static final Update WILD_UPDATE;
/*     */   @NotNull
/*     */   public static final Update TRAILS_AND_TAILS;
/*     */   @NotNull
/*     */   public static final Update TRICKY_TRIALS;
/*     */   
/*     */   @NotNull
/*     */   public static Map<String, Version> versions() {
/*  75 */     return Collections.unmodifiableMap(allVersions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Version getCurrent() {
/*  84 */     if (CURRENT == null) {
/*  85 */       CURRENT = parseCurrentVersion(Bukkit.getVersion());
/*     */     }
/*  87 */     return CURRENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Version parseCurrentVersion(@NotNull String versionString) {
/*  97 */     Pattern pattern = Pattern.compile("\\d+\\.\\d+(\\.\\d+)?");
/*  98 */     Matcher matcher = pattern.matcher(versionString);
/*     */     
/* 100 */     if (!matcher.find()) {
/* 101 */       throw new IllegalArgumentException("Could not find any version in: " + versionString);
/*     */     }
/* 103 */     String currentVersion = matcher.group();
/* 104 */     String[] parts = currentVersion.split("\\.");
/* 105 */     int major = Integer.parseInt(parts[0]);
/* 106 */     int minor = Integer.parseInt(parts[1]);
/* 107 */     int patch = (parts.length == 3) ? Integer.parseInt(parts[2]) : 0;
/*     */ 
/*     */     
/* 110 */     Version version = allVersions.get(major + "." + minor + "." + patch);
/* 111 */     if (version != null) {
/* 112 */       return version;
/*     */     }
/*     */     
/* 115 */     return new Version(major, minor, patch, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 121 */     WORLD_OF_COLOR = registerUpdate(new Update(1, 12, update -> {
/*     */             update.version(0, 1);
/*     */ 
/*     */             
/*     */             update.version(1, 1);
/*     */             
/*     */             update.version(2, 1);
/*     */           }));
/*     */     
/* 130 */     UPDATE_AQUATIC = registerUpdate(new Update(1, 13, update -> {
/*     */             update.version(0, 1);
/*     */ 
/*     */             
/*     */             update.version(1, 2);
/*     */             
/*     */             update.version(2, 2);
/*     */           }));
/*     */     
/* 139 */     VILLAGE_AND_PILLAGE = registerUpdate(new Update(1, 14, update -> {
/*     */             update.version(0, 1);
/*     */             
/*     */             update.version(1, 1);
/*     */             
/*     */             update.version(2, 1);
/*     */             
/*     */             update.version(3, 1);
/*     */             
/*     */             update.version(4, 1);
/*     */           }));
/* 150 */     BUZZY_BEES = registerUpdate(new Update(1, 15, update -> {
/*     */             update.version(0, 1);
/*     */ 
/*     */             
/*     */             update.version(1, 1);
/*     */             
/*     */             update.version(2, 1);
/*     */           }));
/*     */     
/* 159 */     NETHER_UPDATE = registerUpdate(new Update(1, 16, update -> {
/*     */             update.version(0, 1);
/*     */             
/*     */             update.version(1, 1);
/*     */             
/*     */             update.version(2, 2);
/*     */             
/*     */             update.version(3, 2);
/*     */             
/*     */             update.version(4, 3);
/*     */             update.version(5, 3);
/*     */           }));
/* 171 */     CAVES_AND_CLIFFS_1 = registerUpdate(new Update(1, 17, update -> {
/*     */             update.version(0, 1);
/*     */ 
/*     */             
/*     */             update.version(1, 1);
/*     */           }));
/*     */ 
/*     */     
/* 179 */     CAVES_AND_CLIFFS_2 = registerUpdate(new Update(1, 18, update -> {
/*     */             update.version(0, 1);
/*     */ 
/*     */             
/*     */             update.version(1, 1);
/*     */             
/*     */             update.version(2, 2);
/*     */           }));
/*     */     
/* 188 */     WILD_UPDATE = registerUpdate(new Update(1, 19, update -> {
/*     */             update.version(0, 1);
/*     */             
/*     */             update.version(1, 1);
/*     */             
/*     */             update.version(2, 2);
/*     */             
/*     */             update.version(3, 3);
/*     */             
/*     */             update.version(4, 3);
/*     */           }));
/* 199 */     TRAILS_AND_TAILS = registerUpdate(new Update(1, 20, update -> {
/*     */             update.version(0, 1);
/*     */             
/*     */             update.version(1, 1);
/*     */             
/*     */             update.version(2, 2);
/*     */             
/*     */             update.version(3, 3);
/*     */             
/*     */             update.version(4, 3);
/*     */             update.version(5, 4);
/*     */             update.version(6, 4);
/*     */           }));
/* 212 */     TRICKY_TRIALS = registerUpdate(new Update(1, 21, update -> {
/*     */             update.version(0, 1);
/*     */             update.version(1, 1);
/*     */             update.version(2, 2);
/*     */             update.version(3, 2);
/*     */             update.version(4, 3);
/*     */             update.version(5, 4);
/*     */             update.version(6, 5);
/*     */             update.version(7, 5);
/*     */           }));
/*     */   }
/*     */   
/*     */   public static class Update
/*     */     implements Comparable<Update>
/*     */   {
/*     */     private final int major;
/*     */     private final int minor;
/* 229 */     private final List<MinecraftVersions.Version> versions = new ArrayList<>();
/*     */     private boolean lock = false;
/*     */     
/*     */     public Update(int major, int minor, @NotNull List<MinecraftVersions.Version> versions) {
/* 233 */       this.major = major;
/* 234 */       this.minor = minor;
/* 235 */       this.versions.addAll(versions);
/* 236 */       this.lock = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Update(int major, int minor, @NotNull Consumer<Update> init) {
/* 247 */       this.major = major;
/* 248 */       this.minor = minor;
/* 249 */       init.accept(this);
/* 250 */       this.lock = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMajor() {
/* 259 */       return this.major;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMinor() {
/* 268 */       return this.minor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void version(int patch, int protocol) {
/* 279 */       if (this.lock) {
/* 280 */         throw new IllegalStateException("Cannot add versions after initialization");
/*     */       }
/* 282 */       MinecraftVersions.Version version = new MinecraftVersions.Version(this, patch, protocol);
/* 283 */       this.versions.add(version);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCurrent() {
/* 292 */       return MinecraftVersions.getCurrent().getUpdate().equals(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAbove() {
/* 301 */       return (MinecraftVersions.getCurrent().getUpdate().compareTo(this) > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAtLeast() {
/* 310 */       return (MinecraftVersions.getCurrent().getUpdate().compareTo(this) >= 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isBelow() {
/* 319 */       return (MinecraftVersions.getCurrent().getUpdate().compareTo(this) < 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAtMost() {
/* 328 */       return (MinecraftVersions.getCurrent().getUpdate().compareTo(this) <= 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public MinecraftVersions.Version get(int patch) {
/*     */       try {
/* 340 */         return this.versions.get(patch);
/* 341 */       } catch (IndexOutOfBoundsException e) {
/* 342 */         throw new IllegalArgumentException("Unknown version: " + this.major + "." + this.minor + "." + patch);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(@NotNull Update other) {
/* 348 */       int majorCompare = Integer.compare(this.major, other.major);
/* 349 */       return (majorCompare != 0) ? majorCompare : Integer.compare(this.minor, other.minor);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 354 */       if (this == o) return true; 
/* 355 */       if (o == null || getClass() != o.getClass()) return false; 
/* 356 */       Update update = (Update)o;
/* 357 */       return (this.major == update.major && this.minor == update.minor && Objects.equals(this.versions, update.versions));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 362 */       return Objects.hash(new Object[] { Integer.valueOf(this.major), Integer.valueOf(this.minor), this.versions });
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String toString() {
/* 367 */       return this.major + "." + this.minor;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Version
/*     */     implements Comparable<Version>
/*     */   {
/*     */     @NotNull
/*     */     private final MinecraftVersions.Update update;
/*     */ 
/*     */     
/*     */     private final int major;
/*     */ 
/*     */     
/*     */     private final int minor;
/*     */     
/*     */     private final int patch;
/*     */     
/*     */     private final int protocol;
/*     */ 
/*     */     
/*     */     public Version(int major, int minor, int patch, int protocol) {
/* 391 */       this.major = major;
/* 392 */       this.minor = minor;
/* 393 */       this.patch = patch;
/* 394 */       this.protocol = protocol;
/*     */ 
/*     */ 
/*     */       
/* 398 */       this.update = new MinecraftVersions.Update(major, minor, Collections.singletonList(this));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Version(@NotNull MinecraftVersions.Update update, int patch, int protocol) {
/* 409 */       this.update = update;
/* 410 */       this.major = update.major;
/* 411 */       this.minor = update.minor;
/* 412 */       this.patch = patch;
/* 413 */       this.protocol = protocol;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public MinecraftVersions.Update getUpdate() {
/* 422 */       return this.update;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMajor() {
/* 431 */       return this.major;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMinor() {
/* 440 */       return this.minor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getPatch() {
/* 449 */       return this.patch;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getProtocol() {
/* 463 */       return this.protocol;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCurrent() {
/* 472 */       return MinecraftVersions.getCurrent().equals(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAbove() {
/* 481 */       return (MinecraftVersions.getCurrent().compareTo(this) > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAtLeast() {
/* 490 */       return (MinecraftVersions.getCurrent().compareTo(this) >= 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isBelow() {
/* 499 */       return (MinecraftVersions.getCurrent().compareTo(this) < 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAtMost() {
/* 508 */       return (MinecraftVersions.getCurrent().compareTo(this) <= 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String toProtocolString() {
/* 517 */       return "v" + this.major + "_" + this.minor + "_R" + this.protocol;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(@NotNull Version other) {
/* 522 */       int majorCompare = Integer.compare(this.major, other.major);
/* 523 */       if (majorCompare != 0) return majorCompare; 
/* 524 */       int minorCompare = Integer.compare(this.minor, other.minor);
/* 525 */       if (minorCompare != 0) return minorCompare; 
/* 526 */       return Integer.compare(this.patch, other.patch);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 531 */       if (this == o) return true; 
/* 532 */       if (o == null || getClass() != o.getClass()) return false; 
/* 533 */       Version version = (Version)o;
/* 534 */       return (this.major == version.major && this.minor == version.minor && this.patch == version.patch);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 539 */       return Objects.hash(new Object[] { Integer.valueOf(this.major), Integer.valueOf(this.minor), Integer.valueOf(this.patch) });
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String toString() {
/* 544 */       return this.major + "." + this.minor + "." + this.patch;
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliaschedule\\util\MinecraftVersions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */