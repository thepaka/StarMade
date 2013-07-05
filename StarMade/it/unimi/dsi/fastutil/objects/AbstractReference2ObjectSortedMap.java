/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public abstract class AbstractReference2ObjectSortedMap<K, V> extends AbstractReference2ObjectMap<K, V>
/*     */   implements Reference2ObjectSortedMap<K, V>
/*     */ {
/*     */   public static final long serialVersionUID = -1773560792952436569L;
/*     */ 
/*     */   public ReferenceSortedSet<K> keySet()
/*     */   {
/*  68 */     return new KeySet();
/*     */   }
/*     */ 
/*     */   public ObjectCollection<V> values()
/*     */   {
/* 111 */     return new ValuesCollection();
/*     */   }
/*     */ 
/*     */   public ObjectSortedSet<Map.Entry<K, V>> entrySet()
/*     */   {
/* 135 */     return reference2ObjectEntrySet();
/*     */   }
/*     */ 
/*     */   protected static class ValuesIterator<K, V> extends AbstractObjectIterator<V>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Map.Entry<K, V>> i;
/*     */ 
/*     */     public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, V>> i)
/*     */     {
/* 128 */       this.i = i;
/*     */     }
/* 130 */     public V next() { return ((Map.Entry)this.i.next()).getValue(); } 
/* 131 */     public boolean hasNext() { return this.i.hasNext(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   protected class ValuesCollection extends AbstractObjectCollection<V>
/*     */   {
/*     */     protected ValuesCollection()
/*     */     {
/*     */     }
/*     */ 
/*     */     public ObjectIterator<V> iterator()
/*     */     {
/* 115 */       return new AbstractReference2ObjectSortedMap.ValuesIterator(AbstractReference2ObjectSortedMap.this.entrySet().iterator()); } 
/* 116 */     public boolean contains(Object k) { return AbstractReference2ObjectSortedMap.this.containsValue(k); } 
/* 117 */     public int size() { return AbstractReference2ObjectSortedMap.this.size(); } 
/* 118 */     public void clear() { AbstractReference2ObjectSortedMap.this.clear(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   protected static class KeySetIterator<K, V> extends AbstractObjectBidirectionalIterator<K>
/*     */   {
/*     */     protected final ObjectBidirectionalIterator<Map.Entry<K, V>> i;
/*     */ 
/*     */     public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, V>> i)
/*     */     {
/*  92 */       this.i = i;
/*     */     }
/*  94 */     public K next() { return ((Map.Entry)this.i.next()).getKey(); } 
/*  95 */     public K previous() { return ((Map.Entry)this.i.previous()).getKey(); } 
/*  96 */     public boolean hasNext() { return this.i.hasNext(); } 
/*  97 */     public boolean hasPrevious() { return this.i.hasPrevious(); }
/*     */ 
/*     */   }
/*     */ 
/*     */   protected class KeySet extends AbstractReferenceSortedSet<K>
/*     */   {
/*     */     protected KeySet()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean contains(Object k)
/*     */     {
/*  72 */       return AbstractReference2ObjectSortedMap.this.containsKey(k); } 
/*  73 */     public int size() { return AbstractReference2ObjectSortedMap.this.size(); } 
/*  74 */     public void clear() { AbstractReference2ObjectSortedMap.this.clear(); } 
/*  75 */     public Comparator<? super K> comparator() { return AbstractReference2ObjectSortedMap.this.comparator(); } 
/*  76 */     public K first() { return AbstractReference2ObjectSortedMap.this.firstKey(); } 
/*  77 */     public K last() { return AbstractReference2ObjectSortedMap.this.lastKey(); } 
/*  78 */     public ReferenceSortedSet<K> headSet(K to) { return AbstractReference2ObjectSortedMap.this.headMap(to).keySet(); } 
/*  79 */     public ReferenceSortedSet<K> tailSet(K from) { return AbstractReference2ObjectSortedMap.this.tailMap(from).keySet(); } 
/*  80 */     public ReferenceSortedSet<K> subSet(K from, K to) { return AbstractReference2ObjectSortedMap.this.subMap(from, to).keySet(); } 
/*  81 */     public ObjectBidirectionalIterator<K> iterator(K from) { return new AbstractReference2ObjectSortedMap.KeySetIterator(AbstractReference2ObjectSortedMap.this.entrySet().iterator(new AbstractReference2ObjectMap.BasicEntry(from, null))); } 
/*  82 */     public ObjectBidirectionalIterator<K> iterator() { return new AbstractReference2ObjectSortedMap.KeySetIterator(AbstractReference2ObjectSortedMap.this.entrySet().iterator()); }
/*     */ 
/*     */   }
/*     */ }

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.AbstractReference2ObjectSortedMap
 * JD-Core Version:    0.6.2
 */