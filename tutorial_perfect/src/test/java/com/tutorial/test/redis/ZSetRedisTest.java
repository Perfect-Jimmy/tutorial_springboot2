package com.tutorial.test.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jimmy. 2017/12/18  17:34
 * Redis 有序集合和无序集合一样也是string类型元素的集合,且不允许重复的成员。
 * 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
 * 有序集合的成员是唯一的,但分数(score)却可以重复
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ZSetRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增一个有序集合，存在的话为false，不存在的话为true
     * Boolean add(K key, V value, double score);
     */
    @Test
    public void addTest(){
        Boolean flag = redisTemplate.opsForZSet().add("add_zset","java",10.0);
        System.out.println(flag);
    }

    /**
     * 新增一个有序集合 Long add(K key, Set<TypedTuple<V>> tuples);
     */
    @Test
    public void addTuplesTest(){
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("php",9.6);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>("C++",9.9);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        System.out.println(redisTemplate.opsForZSet().add("add_zset_tuples",tuples));
        System.out.println(redisTemplate.opsForZSet().range("add_zset_tuples",0,-1));
    }

    /**
     * 移除一个或多个元素 Long remove(K key, Object... values);
     */
    @Test
    public void removeTest(){
        redisTemplate.opsForZSet().add("remove_zset","java",10.0);
        redisTemplate.opsForZSet().add("remove_zset","jquery",11.0);
        redisTemplate.opsForZSet().add("remove_zset","react",20.0);
        redisTemplate.opsForZSet().add("remove_zset","c#",9.0);
        System.out.println(redisTemplate.opsForZSet().remove("remove_zset","java","c#"));
    }

    /**
     * 增加元素的score值并返回增加后的值 Double incrementScore(K key, V value, double delta);
     */
    @Test
    public void incrementScoreTest(){
        redisTemplate.opsForZSet().add("incrementScore_zset","java",10.0);
        System.out.println(redisTemplate.opsForZSet().incrementScore("incrementScore_zset","java",25.0));
    }

    /**
     * 返回指定成员的排名(score递增) Long rank(K key, Object o);
     */
    @Test
    public void rankTest(){
        redisTemplate.opsForZSet().add("rank_zset","java",10.0);
        redisTemplate.opsForZSet().add("rank_zset","react",21.0);
        redisTemplate.opsForZSet().add("rank_zset","c++",21.0);
        redisTemplate.opsForZSet().add("rank_zset","go",27.0);

        System.out.println(redisTemplate.opsForZSet().rank("rank_zset","java")); //0---排名第一
        System.out.println(redisTemplate.opsForZSet().rank("rank_zset","c++"));//1
        System.out.println(redisTemplate.opsForZSet().rank("rank_zset","react"));//2
        System.out.println(redisTemplate.opsForZSet().rank("rank_zset","go"));//3
    }

    /**
     * 返回指定成员的排名(score递减) Long reverseRank(K key, Object o);
     */
    @Test
    public void reverseRankTest(){
        redisTemplate.opsForZSet().add("reverseRank_zset","java",10.0);
        redisTemplate.opsForZSet().add("reverseRank_zset","react",21.0);
        redisTemplate.opsForZSet().add("reverseRank_zset","c++",21.0);
        redisTemplate.opsForZSet().add("reverseRank_zset","go",27.0);

        System.out.println(redisTemplate.opsForZSet().reverseRank("reverseRank_zset","java"));//3
        System.out.println(redisTemplate.opsForZSet().reverseRank("reverseRank_zset","c++"));//2
        System.out.println(redisTemplate.opsForZSet().reverseRank("reverseRank_zset","react"));//1
        System.out.println(redisTemplate.opsForZSet().reverseRank("reverseRank_zset","go"));//0
    }

    /**
     * 返回指定区间内元素(score递增) Set<V> range(K key, long start, long end);
     * 返回指定区间内元素(score递减) Set<V> reverseRange(K key, long start, long end);
     */
    @Test
    public void rangeTest(){
        redisTemplate.opsForZSet().add("range_zset","java",10.0);
        redisTemplate.opsForZSet().add("range_zset","react",21.0);
        redisTemplate.opsForZSet().add("range_zset","c++",21.0);
        redisTemplate.opsForZSet().add("range_zset","go",27.0);
        System.out.println(redisTemplate.opsForZSet().range("range_zset",0,-1));//[java, c++, react, go]
        System.out.println(redisTemplate.opsForZSet().range("range_zset",0,2));//[java, c++, react]
    }

    /**
     * 返回指定区间内成员的对象(score递增) Set<TypedTuple<V>> rangeWithScores(K key, long start, long end);
     * 返回指定区间内成员的对象(score递减) Set<TypedTuple<V>> reverseRangeWithScores(K key, long start, long end);
     */
    @Test
    public void rangeWithScoresTest(){
        redisTemplate.opsForZSet().add("range_zset","java",10.0);
        redisTemplate.opsForZSet().add("range_zset","react",21.0);
        redisTemplate.opsForZSet().add("range_zset","c++",21.0);
        redisTemplate.opsForZSet().add("range_zset","go",27.0);
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeWithScores("range_zset",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }
    }

    /**
     * 返回指定分数内的成员(score递增) Set<V> rangeByScore(K key, double min, double max);
     * 返回指定分数内的成员(score递减) Set<V> reverseRangeByScore(K key, double min, double max);
     */
    @Test
    public void rangeByScoreTest() {
        redisTemplate.opsForZSet().add("range_score_zset", "java", 10.0);
        redisTemplate.opsForZSet().add("range_score_zset", "react", 21.0);
        redisTemplate.opsForZSet().add("range_score_zset", "c++", 21.0);
        redisTemplate.opsForZSet().add("range_score_zset", "go", 27.0);
        System.out.println(redisTemplate.opsForZSet().rangeByScore("range_score_zset",5.0,25.7));//[java, c++, react]
        System.out.println(redisTemplate.opsForZSet().rangeByScore("range_score_zset",10.,21.0));//[java, c++, react]
    }

    /**
     * 通过分数返回有序集合指定区间内的成员对象，并在索引范围内，按score递增
     * Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count);
     * 通过分数返回有序集合指定区间内的成员对象，并在索引范围内，按score递减
     * Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max);
     */
    @Test
    public void rangeByScoreWithScoresTest(){

    }

    /**
     * 通过分数返回有序集合指定区间内的成员个数 Long count(K key, double min, double max);
     */
    @Test
    public void countTest(){
        redisTemplate.opsForZSet().add("count_zset", "java", 10.0);
        redisTemplate.opsForZSet().add("count_zset", "react", 21.0);
        redisTemplate.opsForZSet().add("count_zset", "c++", 21.0);
        redisTemplate.opsForZSet().add("count_zset", "go", 27.0);
        System.out.println(redisTemplate.opsForZSet().count("count_zset",7.0,10.0));
    }

    /**
     * 获取有序集合的成员数，内部调用的就是zCard方法  Long size(K key);
     * 获取有序集合的成员数 Long zCard(K key);
     */
    @Test
    public void sizeTest(){
        redisTemplate.opsForZSet().add("size_zset", "java", 10.0);
        redisTemplate.opsForZSet().add("size_zset", "react", 21.0);
        redisTemplate.opsForZSet().add("size_zset", "c++", 21.0);
        redisTemplate.opsForZSet().add("size_zset", "go", 27.0);
        System.out.println(redisTemplate.opsForZSet().size("size_zset"));
        System.out.println(redisTemplate.opsForZSet().zCard("size_zset"));
    }

    /**
     * 获取指定成员的score Double score(K key, Object o);
     */
    @Test
    public void scoreTest(){
        redisTemplate.opsForZSet().add("score_zset", "java", 10.0);
        redisTemplate.opsForZSet().add("score_zset", "react", 21.0);
        redisTemplate.opsForZSet().add("score_zset", "c++", 21.0);
        redisTemplate.opsForZSet().add("score_zset", "go", 27.0);
        System.out.println(redisTemplate.opsForZSet().score("score_zset","java"));
    }

    /**
     * 根据索引范围移除元素(score递增) Long removeRange(K key, long start, long end);
     * 根据score值移除元素 Long removeRangeByScore(K key, double min, double max);
     */
    @Test
    public void removeRangeTest(){
        redisTemplate.opsForZSet().add("remove_range_zset", "java", 10.0);
        redisTemplate.opsForZSet().add("remove_range_zset", "react", 21.0);
        redisTemplate.opsForZSet().add("remove_range_zset", "c++", 21.0);
        redisTemplate.opsForZSet().add("remove_range_zset", "go", 27.0);
        System.out.println(redisTemplate.opsForZSet().removeRange("remove_range_zset",0,1));
        System.out.println(redisTemplate.opsForZSet().removeRangeByScore("remove_range_zset",10,25));
    }

    /**
     * 并集并存储到destKey中,key相同score值会相加
     * Long unionAndStore(K key, K otherKey, K destKey);
     * Long unionAndStore(K key, Collection<K> otherKeys, K destKey);
     */
    @Test
    public void unionAndStoreTest(){
        redisTemplate.opsForZSet().add("union_zset_1", "java", 10.0);
        redisTemplate.opsForZSet().add("union_zset_1", "c++", 12.0);
        redisTemplate.opsForZSet().add("union_zset_2", "react", 21.0);
        redisTemplate.opsForZSet().add("union_zset_2", "react", 21.0);
        System.out.println( redisTemplate.opsForZSet().unionAndStore("union_zset_1","union_zset_2","union_zset_dest1"));
        System.out.println( redisTemplate.opsForZSet().unionAndStore("union_zset_1","union_zset_1","union_zset_dest2"));
    }

    /**
     * 交集并存储
     * Long intersectAndStore(K key, K otherKey, K destKey);
     * Long intersectAndStore(K key, Collection<K> otherKeys, K destKey);
     */
    @Test
    public void intersectAndStoreTest(){

    }

    /**
     * 遍历 Cursor<TypedTuple<V>> scan(K key, ScanOptions options);
     */
    @Test
    public void scanTest(){
        redisTemplate.opsForZSet().add("scan_zset", "java", 10.0);
        redisTemplate.opsForZSet().add("scan_zset", "c++", 12.0);
        redisTemplate.opsForZSet().add("scan_zset", "react", 21.0);
        redisTemplate.opsForZSet().add("scan_zset", "react", 21.0);
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan("scan_zset", ScanOptions.NONE);
        while (cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            System.out.println(item.getValue() + ":" + item.getScore());
        }

    }
}
