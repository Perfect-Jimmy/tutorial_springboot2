package com.tutorial.test.redis;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by Jimmy. 2017/12/18  17:30
 * Set是string类型的无序集合
 * 集合成员是唯一的，这就意味着集合中不能出现重复的数据。
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class SetRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加数据Long add(K key, V... values);
     */
    @Test
    public void saveTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set1,set2,set3",",");
        redisTemplate.opsForSet().add("save_set",array);
    }

    /**
     * 移除集合中一个或多个成员 Long remove(K key, Object... values);
     */
    @Test
    public void removeTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set1,set2,set3",",");
        redisTemplate.opsForSet().add("remove_set",array);
        redisTemplate.opsForSet().remove("remove_set","set2","set3");
    }

    /**
     * 移除并返回集合中的一个随机元素 V pop(K key);
     */
    @Test
    public void popTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set1,set2,set3",",");
        redisTemplate.opsForSet().add("pop_set",array);
        redisTemplate.opsForSet().pop("pop_set");
        System.out.println(redisTemplate.opsForSet().members("pop_set"));
    }

    /**
     * 元素从source集合移动到destination集合 Boolean move(K source, V value, K destKey);
     */
    @Test
    public void moveTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set1,set2,set3",",");
        redisTemplate.opsForSet().add("source_set",array);
        redisTemplate.opsForSet().add("dest_set","dest");
        redisTemplate.opsForSet().move("source_set","set1","dest_set");
        System.out.println(redisTemplate.opsForSet().members("dest_set"));
    }

    /**
     * 无序集合长度 Long size(K key);
     */
    @Test
    public void sizeTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set1,set2,set3",",");
        redisTemplate.opsForSet().add("size_set",array);
        System.out.println(redisTemplate.opsForSet().size("size_set"));
    }

    /**
     * 判断member元素是否是集合key的成员 Boolean isMember(K key, Object o);
     */
    @Test
    public void memberTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set1,set2,set3",",");
        redisTemplate.opsForSet().add("member_set",array);
        System.out.println(redisTemplate.opsForSet().isMember("member_set","set1"));
        System.out.println(redisTemplate.opsForSet().isMember("member_set","set7"));
    }

    /**
     * 无序集合交集
     * Set<V> intersect(K key, K otherKey)
     * Set<V> intersect(K key, Collection<K> otherKeys);
     */
    @Test
    public void intersectTest(){
        String[] array1 = StringUtils.delimitedListToStringArray("set1,set1,set2,set3",",");
        String[] array2 = StringUtils.delimitedListToStringArray("set1,set2,set3,set4",",");
        String[] array3 = StringUtils.delimitedListToStringArray("set0,set1,set2,set5",",");
        redisTemplate.opsForSet().add("intersect_set_1",array1);
        redisTemplate.opsForSet().add("intersect_set_2",array2);
        redisTemplate.opsForSet().add("intersect_set_3",array3);
        Set<String> set = redisTemplate.opsForSet().intersect("intersect_set_1","intersect_set_2");
        System.out.println(set);//[set3, set2, set1]
        List<String> list = Lists.newArrayList();
        list.add("intersect_set_2");
        list.add("intersect_set_3");
        Set<String> setMuti = redisTemplate.opsForSet().intersect("intersect_set_1",list);
        System.out.println(setMuti);//[set2, set1]
    }

    /**
     * 交集存储到destKey无序集合中
     * Long intersectAndStore(K key, K otherKey, K destKey);
     * Long intersectAndStore(K key, Collection<K> otherKeys, K destKey);
     */
    @Test
    public void intersectAndStore(){
        String[] array1 = StringUtils.delimitedListToStringArray("set6,set1,set2,set3",",");
        String[] array2 = StringUtils.delimitedListToStringArray("set1,set2,set3,set4",",");
        String[] array3 = StringUtils.delimitedListToStringArray("set0,set1,set2,set5",",");
        redisTemplate.opsForSet().add("intersect_store_set_1",array1);
        redisTemplate.opsForSet().add("intersect_store_set_2",array2);
        redisTemplate.opsForSet().add("intersect_store_set_3",array3);

        redisTemplate.opsForSet().intersectAndStore("intersect_store_set_1","intersect_store_set_2","intersect_store_set_dest");

        System.out.println(redisTemplate.opsForSet().members("intersect_store_set_dest"));
        //[set2, set3, set1]
        List<String> list = Lists.newArrayList();
        list.add("intersect_store_set_1");
        list.add("intersect_store_set_2");
        redisTemplate.opsForSet().intersectAndStore("intersect_store_set_3",list,"intersect_store_set_destMuti");
        System.out.println(redisTemplate.opsForSet().members("intersect_store_set_destMuti"));
        //[set2, set1]
    }

    /**
     * 并集
     * Set<V> union(K key, K otherKey);
     * Set<V> union(K key, Collection<K> otherKeys);
     * 并集存储到destKey无序集合中
     * Long unionAndStore(K key, K otherKey, K destKey);
     * Long unionAndStore(K key, Collection<K> otherKeys, K destKey);
     */
    @Test
    public void unionTest(){
        String[] array1 = StringUtils.delimitedListToStringArray("set1,set2,set3,set4",",");
        String[] array2 = StringUtils.delimitedListToStringArray("set1,set2,set4,set5",",");
        String[] array3 = StringUtils.delimitedListToStringArray("set4,set6,set7,set8",",");
        redisTemplate.opsForSet().add("union_set_1",array1);
        redisTemplate.opsForSet().add("union_set_2",array2);
        redisTemplate.opsForSet().add("union_set_3",array3);
        System.out.println( redisTemplate.opsForSet().union("union_set_1","union_set_2"));
        //[set3, set5, set4, set1, set2]
        List<String> list = Lists.newArrayList();
        list.add("union_set_1");
        list.add("union_set_2");
        System.out.println( redisTemplate.opsForSet().union("union_set_3",list));
        //[set3, set5, set1, set7, set6, set4, set2, set8]
        //------------------并集存储-----------------------------------------------------------------
        redisTemplate.opsForSet().add("union_store_set_1",array1);
        redisTemplate.opsForSet().add("union_store_set_2",array2);
        redisTemplate.opsForSet().add("union_store_set_3",array3);
        System.out.println( redisTemplate.opsForSet().unionAndStore("union_store_set_1","union_store_set_2","union_store_set_dest"));
    }

    /**
     * 差集:集合key中排除otherKey中也有的,key中剩下的即为差集
     * Set<V> difference(K key, K otherKey);
     * Set<V> difference(K key, Collection<K> otherKeys);
     * 差集存储
     * Long differenceAndStore(K key, K otherKey, K destKey);
     * Long differenceAndStore(K key, Collection<K> otherKeys, K destKey);
     *
     */
    @Test
    public void differenceTest(){
        String[] array1 = StringUtils.delimitedListToStringArray("set1,set2,set3,set4",",");
        String[] array2 = StringUtils.delimitedListToStringArray("set1,set2,set4,set5",",");
        String[] array3 = StringUtils.delimitedListToStringArray("set4,set6,set7,set8",",");
        redisTemplate.opsForSet().add("difference_set_1",array1);
        redisTemplate.opsForSet().add("difference_set_2",array2);
        redisTemplate.opsForSet().add("difference_set_3",array3);
        System.out.println( redisTemplate.opsForSet().difference("difference_set_1","difference_set_2"));
        List<String> list = Lists.newArrayList();
        list.add("difference_set_1");
        list.add("difference_set_2");
        System.out.println( redisTemplate.opsForSet().difference("difference_set_1",list));
    }

    /**
     * 随机获取集合中的一个元素V randomMember(K key);
     * 随机获取集合中的多个元素List<V> randomMembers(K key, long count);
     * 随机获取集合中的多个元素（去重）Set<V> distinctRandomMembers(K key, long count);
     */
    @Test
    public void randomMemberTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set2,set3,set4,set2,set3,set5,set3,set6",",");
        redisTemplate.opsForSet().add("random_set",array);
        System.out.println(redisTemplate.opsForSet().randomMember("random_set"));
        System.out.println(redisTemplate.opsForSet().randomMembers("random_set",4));//[set5, set5, set3, set3]
        System.out.println(redisTemplate.opsForSet().distinctRandomMembers("random_set",11));//[set4, set1, set3, set2, set5, set6]
    }

    /**
     * 遍历Cursor<V> scan(K key, ScanOptions options);
     */
    @Test
    public void scanTest(){
        String[] array = StringUtils.delimitedListToStringArray("set1,set2,set3,set4,set2,set3,set5,set3,set6",",");
        redisTemplate.opsForSet().add("scan_set1",array);
        Cursor<Object> curosr = redisTemplate.opsForSet().scan("scan_set1", ScanOptions.NONE);
        while(curosr.hasNext()){
            System.out.println(curosr.next());
        }
    }
}
