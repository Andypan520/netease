package common;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pandechuan on 2018/1/9.
 */
public class CollectionUtil {
    private static final Logger logger = Logger.getLogger(CollectionUtil.class);

    public static <T> Collection<T> setProperty(Collection<T> beans, final String property, Object value) {
        for (Object bean : beans) {
            try {
                if (PropertyUtils.isWriteable(bean, property)) {
                    PropertyUtils.setProperty(bean, property, value);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.setProperty error", e);
            }
        }
        return beans;

    }

    public static <T> boolean isEquals(Collection<T> beans,
                                       Collection<T> beans2) {
        if (beans == null) {
            if (beans2 == null || beans2.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
        if (beans2 == null) {
            if (beans.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        if (beans.size() != beans2.size()) {
            return false;
        }

        HashSet<T> set = new HashSet<T>(beans);
        for (T t : beans2) {
            if (!set.contains(t)) {
                return false;
            }
        }

        return true;
    }

    public static List<?> collectProperty(Collection<?> beans, String property) {
        List<Object> properties = new ArrayList<Object>();
        for (Object bean : beans) {
            try {
                properties.add(PropertyUtils.getProperty(bean, property));
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.collectProperty error", e);
            }
        }
        return properties;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> collectProperty(Collection<?> beans, String property, Class<T> propertyClazz) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptyList();
        }
        List<T> properties = new ArrayList<T>();
        for (Object bean : beans) {
            if (bean == null) {
                continue;
            }
            try {
                properties.add((T) PropertyUtils.getProperty(bean, property));
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.collectProperty error", e);
            }
        }
        return properties;
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> collectProperty(Collection<?> beans, String property, Collection<T> properties) {
        for (Object bean : beans) {
            try {
                properties.add((T) PropertyUtils.getProperty(bean, property));
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.collectProperty error", e);
            }
        }
        return properties;
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> collectPropertySet(Collection<?> beans, String property, Class<T> propertyClazz) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptySet();
        }
        Set<T> properties = new HashSet<T>();
        for (Object bean : beans) {
            try {
                properties.add((T) PropertyUtils.getProperty(bean, property));
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.collectProperty error", e);
            }
        }
        return properties;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] collectPropertyArray(Collection<?> beans, String property, T[] array) {
        int size = beans.size();
        if (array.length < size) {
            array = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size);
        }
        Object[] result = array;
        int i = 0;
        for (Object bean : beans) {
            try {
                result[i] = PropertyUtils.getProperty(bean, property);
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.collectProperty error", e);
            }
            i++;
        }

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    public static <V> Map<Long, V> toMap(Collection<V> beans, String idPropertyName) {
        if (beans == null || beans.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Long, V> idMap = new HashMap<Long, V>();
        for (V bean : beans) {
            try {
                Long id = (Long) PropertyUtils.getProperty(bean, idPropertyName);
                idMap.put(id, bean);
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.getIdMap error", e);
            }
        }
        return idMap;
    }

    public static <K, V> Map<K, V> toMap(Collection<V> beans, String idPropertyName, Class<K> clazz) {
        if (beans == null || beans.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<K, V> idMap = new HashMap<K, V>();
        for (V bean : beans) {
            try {
                @SuppressWarnings("unchecked")
                K id = (K) PropertyUtils.getProperty(bean, idPropertyName);
                idMap.put(id, bean);
            } catch (Exception e) {
                throw new IllegalArgumentException("CollectionUtils.getIdMap error", e);
            }
        }
        return idMap;
    }

   /* public static void copyProperty(Collection<?> targets, Collection<?> sources, PropertyPair idPropertyPair,
                                    PropertyPair... extPropertyPairs) {
        Map<Long, ?> idMap = getIdMap(sources, idPropertyPair.getSecond());
        try {
            for (Object target : targets) {
                Long id = (Long) PropertyUtils.getProperty(target, idPropertyPair.getFirst());
                Object source = idMap.get(id);
                if (source != null) {
                    MusicBeanUtils.copyProperty(target, source, extPropertyPairs);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException( "CollectionUtils.copyProperty error", e);
        }
    }*/

   /* public static <T> List<T> convert(Class<T> targetClass, Collection<?> sources, PropertyPair... propertyPairs) {
        List<T> targets = new ArrayList<T>(sources.size());
        for (Object source : sources) {
            targets.add(MusicBeanUtils.convert(targetClass, source, propertyPairs));
        }
        return targets;
    }*/

    public static boolean getHasMoreMark(List<?> data, int limit, boolean fixSize) {
        if (data != null && data.size() > limit) {
            if (fixSize) {
                data.subList(limit, data.size()).clear();
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean getHasMoreMark(List<?> data, int limit) {
        return getHasMoreMark(data, limit, true);
    }

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return c != null && !c.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public static <T, T1> Map<T, T1> convertListToMap(List<T1> list, String keyProperty) {
        if (list == null || list.size() == 0) {
            return Collections.emptyMap();
        } else {
            try {
                Map<T, T1> map = new HashMap<T, T1>();
                for (T1 item : list) {
                    Object property = PropertyUtils.getProperty(item, keyProperty);
                    map.put((T) property, item);
                }
                return map;
            } catch (Exception ex) {
                logger.error(ex);
                throw new RuntimeException("convert failed.");
            }
        }
    }

    public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        int size = list.size();
        fromIndex = Math.min(fromIndex, size);
        toIndex = Math.min(toIndex, size);
        return list.subList(fromIndex, toIndex);
    }

    public static <T> List<T> subListByLimitAndOffset(List<T> list, int limit, int offset) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        int size = list.size();
        int toIndex = Math.min(offset + limit, size);
        int fromIndex = Math.min(offset, size);
        return list.subList(fromIndex, toIndex);
    }



    @SuppressWarnings("unchecked")
    public static <T> T get(Map<String, Object> map, String key, Class<T> clazz) {
        Object value = map.get(key);
        if (value != null) {
            if (clazz == Long.class) {
                return (T) Long.valueOf(value.toString());
            } else if (clazz == Integer.class) {
                return (T) Integer.valueOf(value.toString());
            } else if (clazz == Float.class) {
                return (T) Float.valueOf(value.toString());
            } else if (clazz == Double.class) {
                return (T) Double.valueOf(value.toString());
            } else {
                return clazz.cast(value);
            }
        }
        return null;
    }

    public static List<Long> getFirstHasOtherNot(List<Long> first, List<Long> other) {
        List<Long> result = new ArrayList<Long>();
        boolean isInOther = false;
        for (long t : first) {
            isInOther = false;
            for (long o : other) {
                if (t == o) {
                    isInOther = true;
                }
            }
            if (!isInOther) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 按照Size大小切分collection
     */
    public static <T> Collection<Collection<T>> splistCollections(Collection<T> collection, int size) {
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        List<Collection<T>> splitedCollections = new LinkedList<Collection<T>>();
        if (collection.size() <= size) {
            splitedCollections.add(collection);
            return splitedCollections;
        }
        /**
         * 开始切割
         */
        Iterator<T> iterator = collection.iterator();
        List<T> list = new LinkedList<T>();
        splitedCollections.add(list);
        while (iterator.hasNext()) {
            if (list.size() < size) {
                list.add(iterator.next());
            } else {
                list = new LinkedList<T>();
                list.add(iterator.next());
                splitedCollections.add(list);
            }
        }
        return splitedCollections;
    }

    public static void main(String[] args) {

      /*  List<Long> first = new ArrayList<Long>();
        first.add(333L);
        first.add(1234L);
        first.add(435L);
        first.add(7655L);
        List<Long> other = new ArrayList<Long>();
        other.add(333L);
        other.add(1234L);
        other.add(5644L);
        other.add(77777L);
        List<Long> res = getFirstHasOtherNot(first, other);
        System.out.println(res);
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < 22; i++) {
            ids.add(i);
        }
        int limit = 20 + 1;
        int offset = 20;
        ids = subListByLimitAndOffset(ids, limit, offset);
        for (int id : ids) {
            System.out.println(id);
        }*/
      System.out.println(45L == 45l);
    }

}
