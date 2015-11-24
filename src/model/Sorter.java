package model;

import java.util.*;
import java.util.Map.Entry;
import model.Document;

public class Sorter {

    static <K, V extends Comparable<? super V>> List<Entry<Double, Document>> entriesSortedByValues(Map<Double, Document> map) {

        List<Entry<Double, Document>> sortedEntries = new ArrayList<Entry<Double, Document>>(map.entrySet());

        Collections.sort(sortedEntries, new Comparator<Entry<Double, Document>>() {
            @Override
            public int compare(Entry<Double, Document> e1, Entry<Double, Document> e2) {
                return e2.getKey().compareTo(e1.getKey());
            }
        });
        
        return sortedEntries;
    }

//    public static void main(String[] args) {
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        map.put("A", 34);
//        map.put("B", 25);
//        map.put("C", 50);
//        map.put("D", 50); // "duplicate" value
//        
//        Iterator it = entriesSortedByValues(map).iterator();
//        
//        while(it.hasNext()){
//            Entry e = (Entry<String,Integer>)it.next();
//            System.out.println("Key:"+e.getKey()+" Value:"+e.getValue());
//        }
//        
//    }
}
