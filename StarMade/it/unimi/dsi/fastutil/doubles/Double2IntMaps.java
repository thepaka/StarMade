/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollections;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Double2IntMaps
/*     */ {
/*  98 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */   public static Double2IntMap singleton(double key, int value)
/*     */   {
/* 185 */     return new Singleton(key, value);
/*     */   }
/*     */ 
/*     */   public static Double2IntMap singleton(Double key, Integer value)
/*     */   {
/* 200 */     return new Singleton(key.doubleValue(), value.intValue());
/*     */   }
/*     */ 
/*     */   public static Double2IntMap synchronize(Double2IntMap m)
/*     */   {
/* 279 */     return new SynchronizedMap(m);
/*     */   }
/*     */ 
/*     */   public static Double2IntMap synchronize(Double2IntMap m, Object sync)
/*     */   {
/* 289 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */ 
/*     */   public static Double2IntMap unmodifiable(Double2IntMap m)
/*     */   {
/* 358 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ 
/*     */   public static class UnmodifiableMap extends Double2IntFunctions.UnmodifiableFunction
/*     */     implements Double2IntMap, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2IntMap map;
/*     */     protected volatile transient ObjectSet<Double2IntMap.Entry> entries;
/*     */     protected volatile transient DoubleSet keys;
/*     */     protected volatile transient IntCollection values;
/*     */ 
/*     */     protected UnmodifiableMap(Double2IntMap m)
/*     */     {
/* 306 */       super();
/* 307 */       this.map = m;
/*     */     }
/*     */     public int size() {
/* 310 */       return this.map.size(); } 
/* 311 */     public boolean containsKey(double k) { return this.map.containsKey(k); } 
/* 312 */     public boolean containsValue(int v) { return this.map.containsValue(v); } 
/*     */     public int defaultReturnValue() {
/* 314 */       throw new UnsupportedOperationException(); } 
/* 315 */     public void defaultReturnValue(int defRetValue) { throw new UnsupportedOperationException(); } 
/*     */     public int put(double k, int v) {
/* 317 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public void putAll(Map<? extends Double, ? extends Integer> m) {
/* 320 */       throw new UnsupportedOperationException();
/*     */     }
/* 322 */     public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() { if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.double2IntEntrySet()); return this.entries; } 
/* 323 */     public DoubleSet keySet() { if (this.keys == null) this.keys = DoubleSets.unmodifiable(this.map.keySet()); return this.keys; } 
/* 324 */     public IntCollection values() { if (this.values == null) return IntCollections.unmodifiable(this.map.values()); return this.values; } 
/*     */     public void clear() {
/* 326 */       throw new UnsupportedOperationException(); } 
/* 327 */     public String toString() { return this.map.toString(); }
/*     */ 
/*     */     public Integer put(Double k, Integer v) {
/* 330 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public int remove(double k) {
/* 334 */       throw new UnsupportedOperationException(); } 
/* 335 */     public int get(double k) { return this.map.get(k); } 
/* 336 */     public boolean containsKey(Object ok) { return this.map.containsKey(ok); }
/*     */ 
/*     */     public boolean containsValue(Object ov)
/*     */     {
/* 340 */       return this.map.containsValue(ov);
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 348 */       return this.map.isEmpty(); } 
/* 349 */     public ObjectSet<Map.Entry<Double, Integer>> entrySet() { return ObjectSets.unmodifiable(this.map.entrySet()); }
/*     */ 
/*     */   }
/*     */ 
/*     */   public static class SynchronizedMap extends Double2IntFunctions.SynchronizedFunction
/*     */     implements Double2IntMap, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2IntMap map;
/*     */     protected volatile transient ObjectSet<Double2IntMap.Entry> entries;
/*     */     protected volatile transient DoubleSet keys;
/*     */     protected volatile transient IntCollection values;
/*     */ 
/*     */     protected SynchronizedMap(Double2IntMap m, Object sync)
/*     */     {
/* 219 */       super(sync);
/* 220 */       this.map = m;
/*     */     }
/*     */ 
/*     */     protected SynchronizedMap(Double2IntMap m) {
/* 224 */       super();
/* 225 */       this.map = m;
/*     */     }
/*     */     public int size() {
/* 228 */       synchronized (this.sync) { return this.map.size(); }  } 
/* 229 */     public boolean containsKey(double k) { synchronized (this.sync) { return this.map.containsKey(k); }  } 
/* 230 */     public boolean containsValue(int v) { synchronized (this.sync) { return this.map.containsValue(v); }  } 
/*     */     public int defaultReturnValue() {
/* 232 */       synchronized (this.sync) { return this.map.defaultReturnValue(); }  } 
/* 233 */     public void defaultReturnValue(int defRetValue) { synchronized (this.sync) { this.map.defaultReturnValue(defRetValue); }  } 
/*     */     public int put(double k, int v) {
/* 235 */       synchronized (this.sync) { return this.map.put(k, v); } 
/*     */     }
/*     */ 
/* 238 */     public void putAll(Map<? extends Double, ? extends Integer> m) { synchronized (this.sync) { this.map.putAll(m); }  } 
/*     */     public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 240 */       if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.double2IntEntrySet(), this.sync); return this.entries; } 
/* 241 */     public DoubleSet keySet() { if (this.keys == null) this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync); return this.keys; } 
/* 242 */     public IntCollection values() { if (this.values == null) return IntCollections.synchronize(this.map.values(), this.sync); return this.values; } 
/*     */     public void clear() {
/* 244 */       synchronized (this.sync) { this.map.clear(); }  } 
/* 245 */     public String toString() { synchronized (this.sync) { return this.map.toString(); } }
/*     */ 
/*     */     public Integer put(Double k, Integer v) {
/* 248 */       synchronized (this.sync) { return (Integer)this.map.put(k, v); }
/*     */     }
/*     */ 
/*     */     public int remove(double k) {
/* 252 */       synchronized (this.sync) { return this.map.remove(k); }  } 
/* 253 */     public int get(double k) { synchronized (this.sync) { return this.map.get(k); }  } 
/* 254 */     public boolean containsKey(Object ok) { synchronized (this.sync) { return this.map.containsKey(ok); } }
/*     */ 
/*     */     public boolean containsValue(Object ov)
/*     */     {
/* 258 */       synchronized (this.sync) { return this.map.containsValue(ov); }
/*     */ 
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 266 */       synchronized (this.sync) { return this.map.isEmpty(); }  } 
/* 267 */     public ObjectSet<Map.Entry<Double, Integer>> entrySet() { synchronized (this.sync) { return this.map.entrySet(); }  } 
/*     */     public int hashCode() {
/* 269 */       synchronized (this.sync) { return this.map.hashCode(); }  } 
/* 270 */     public boolean equals(Object o) { synchronized (this.sync) { return this.map.equals(o); }
/*     */ 
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Singleton extends Double2IntFunctions.Singleton
/*     */     implements Double2IntMap, Serializable, Cloneable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected volatile transient ObjectSet<Double2IntMap.Entry> entries;
/*     */     protected volatile transient DoubleSet keys;
/*     */     protected volatile transient IntCollection values;
/*     */ 
/*     */     protected Singleton(double key, int value)
/*     */     {
/* 116 */       super(value);
/*     */     }
/*     */     public boolean containsValue(int v) {
/* 119 */       return this.value == v;
/*     */     }
/* 121 */     public boolean containsValue(Object ov) { return ((Integer)ov).intValue() == this.value; }
/*     */ 
/*     */     public void putAll(Map<? extends Double, ? extends Integer> m) {
/* 124 */       throw new UnsupportedOperationException();
/*     */     }
/* 126 */     public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() { if (this.entries == null) this.entries = ObjectSets.singleton(new SingletonEntry()); return this.entries; } 
/* 127 */     public DoubleSet keySet() { if (this.keys == null) this.keys = DoubleSets.singleton(this.key); return this.keys; } 
/* 128 */     public IntCollection values() { if (this.values == null) this.values = IntSets.singleton(this.value); return this.values;
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 156 */       return false;
/*     */     }
/*     */     public ObjectSet<Map.Entry<Double, Integer>> entrySet() {
/* 159 */       return double2IntEntrySet();
/*     */     }
/* 161 */     public int hashCode() { return HashCommon.double2int(this.key) ^ this.value; }
/*     */ 
/*     */     public boolean equals(Object o) {
/* 164 */       if (o == this) return true;
/* 165 */       if (!(o instanceof Map)) return false;
/*     */ 
/* 167 */       Map m = (Map)o;
/* 168 */       if (m.size() != 1) return false;
/* 169 */       return ((Map.Entry)entrySet().iterator().next()).equals(m.entrySet().iterator().next());
/*     */     }
/*     */     public String toString() {
/* 172 */       return "{" + this.key + "=>" + this.value + "}";
/*     */     }
/*     */ 
/*     */     protected class SingletonEntry
/*     */       implements Double2IntMap.Entry, Map.Entry<Double, Integer>
/*     */     {
/*     */       protected SingletonEntry()
/*     */       {
/*     */       }
/*     */ 
/*     */       public Double getKey()
/*     */       {
/* 131 */         return Double.valueOf(Double2IntMaps.Singleton.this.key); } 
/* 132 */       public Integer getValue() { return Integer.valueOf(Double2IntMaps.Singleton.this.value); }
/*     */ 
/*     */       public double getDoubleKey() {
/* 135 */         return Double2IntMaps.Singleton.this.key;
/*     */       }
/*     */ 
/*     */       public int getIntValue() {
/* 139 */         return Double2IntMaps.Singleton.this.value; } 
/* 140 */       public int setValue(int value) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */       public Integer setValue(Integer value) {
/* 143 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       public boolean equals(Object o) {
/* 146 */         if (!(o instanceof Map.Entry)) return false;
/* 147 */         Map.Entry e = (Map.Entry)o;
/*     */ 
/* 149 */         return (Double2IntMaps.Singleton.this.key == ((Double)e.getKey()).doubleValue()) && (Double2IntMaps.Singleton.this.value == ((Integer)e.getValue()).intValue());
/*     */       }
/*     */       public int hashCode() {
/* 152 */         return HashCommon.double2int(Double2IntMaps.Singleton.this.key) ^ Double2IntMaps.Singleton.this.value; } 
/* 153 */       public String toString() { return Double2IntMaps.Singleton.this.key + "->" + Double2IntMaps.Singleton.this.value; }
/*     */ 
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class EmptyMap extends Double2IntFunctions.EmptyFunction
/*     */     implements Double2IntMap, Serializable, Cloneable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     public boolean containsValue(int v)
/*     */     {
/*  67 */       return false; } 
/*  68 */     public void putAll(Map<? extends Double, ? extends Integer> m) { throw new UnsupportedOperationException(); } 
/*     */     public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() {
/*  70 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*  72 */     public DoubleSet keySet() { return DoubleSets.EMPTY_SET; } 
/*     */     public IntCollection values() {
/*  74 */       return IntSets.EMPTY_SET; } 
/*  75 */     public boolean containsValue(Object ov) { return false; } 
/*  76 */     private Object readResolve() { return Double2IntMaps.EMPTY_MAP; } 
/*  77 */     public Object clone() { return Double2IntMaps.EMPTY_MAP; } 
/*  78 */     public boolean isEmpty() { return true; } 
/*     */     public ObjectSet<Map.Entry<Double, Integer>> entrySet() {
/*  80 */       return double2IntEntrySet();
/*     */     }
/*  82 */     public int hashCode() { return 0; }
/*     */ 
/*     */     public boolean equals(Object o) {
/*  85 */       if (!(o instanceof Map)) return false;
/*     */ 
/*  87 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     public String toString() {
/*  90 */       return "{}";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.doubles.Double2IntMaps
 * JD-Core Version:    0.6.2
 */