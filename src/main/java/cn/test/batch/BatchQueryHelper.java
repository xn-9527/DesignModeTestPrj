package cn.test.batch;

import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chay
 * @date 2019/09/06
 */
public final class BatchQueryHelper {
    private static final Logger logger = LoggerFactory.getLogger(BatchQueryHelper.class);

    private static final long EXPECTED_QUERY_TIME = 1000;
    private static final int SINGLE_QUERY_MAX_VALUE = 2;

    private static final ExecutorService poolTaskExecutor = Executors.newFixedThreadPool(3);

    private BatchQueryHelper() {}

    /**
     * 执行批量查询接口的方法
     * @param ids ID 列表
     * @param function 实际查询函数
     * @param <P> 查询参数的类型
     * @param <T> 查询结果中的类型
     * @return list
     */
    public static <P, T> List<T> executeBatchQuery(Collection<P> ids, Function<List<P>, List<T>> function) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= SINGLE_QUERY_MAX_VALUE) {
            return function.apply(innerIds);
        }
        List<T> result = new LinkedList<>();
        innerBatchQuery(innerIds, function, result::addAll);
        return result;
    }

    /**
     * 执行批量查询接口的方法
     * @param ids ID 列表
     * @param function 实际查询函数
     * @param <P> 查询参数的类型
     * @param <T> 查询结果中的类型
     * @return list
     */
    public static <P, T> List<T> executeBatchWithMaxNumQuery(Collection<P> ids, Function<List<P>, List<T>> function, Integer maxNum) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        maxNum = maxNum == null ? SINGLE_QUERY_MAX_VALUE : maxNum;
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= maxNum) {
            return function.apply(innerIds);
        }
        List<T> result = new LinkedList<>();
        innerBatchWithMaxNumQuery(innerIds, function, result::addAll, maxNum);
        return result;
    }

    /**
     * 执行两个参数批量查询接口的方法
     *
     * @param ids ID 列表
     * @param param2 函数第二个参数
     * @param function 实际查询函数
     * @param <P> 查询参数的类型
     * @param <U> 函数第二个参数类型
     * @param <T>  查询结果中的类型
     * @return list
     */
    public static <P, U, T> List<T> executeBatchBiParamQuery(Collection<P> ids,U param2, BiFunction<List<P>, U, List<T>> function) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= SINGLE_QUERY_MAX_VALUE) {
            return function.apply(innerIds, param2);
        }
        List<T> result = new LinkedList<>();
        innerBatchBiParamQuery(innerIds, param2, function, result::addAll);
        return result;
    }

    /**
     * 执行两个参数批量查询接口的方法
     *
     * @param param1 函数第1个参数
     * @param ids ID 列表
     * @param function 实际查询函数
     * @param <U> 函数第1个参数类型
     * @param <P> 查询参数的类型
     * @param <T>  查询结果中的类型
     * @return list
     */
    public static <P, U, T> List<T> executeBatchBiParamQueryV2(U param1, Collection<P> ids, BiFunction<U, List<P>, List<T>> function) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= SINGLE_QUERY_MAX_VALUE) {
            return function.apply(param1, innerIds);
        }
        List<T> result = new LinkedList<>();
        innerBatchBiParamQuery(param1, innerIds, function, result::addAll);
        return result;
    }

    /**
     * 执行批量查询单个返回结果的接口的方法
     * @param ids ID 列表
     * @param function 实际查询函数
     * @param <P> 查询参数的类型
     * @param <T> 查询结果中的类型
     * @return list 里面是每次分批查count的结果，需要外面自己计算
     */
    public static <P, T> List<T> executeBatchSingleResultQuery(Collection<P> ids, Function<List<P>, T> function) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= SINGLE_QUERY_MAX_VALUE) {
            return Collections.singletonList(function.apply(innerIds));
        }
        List<T> result = new LinkedList<>();
        innerBatchQuery(innerIds, function, result::add);
        return result;
    }

    /**
     * 执行批量查询接口的方法
     * @param ids ID 列表
     * @param function 实际查询函数
     * @param <T> 查询结果中的类型
     * @return map
     */
    public static <P, T> Map<P, T> executeBatchQueryToMap(Collection<P> ids, Function<List<P>, Map<P, T>> function) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= SINGLE_QUERY_MAX_VALUE) {
            return function.apply(innerIds);
        }
        Map<P, T> result = new LinkedHashMap<>();
        innerBatchQuery(innerIds, function, result::putAll);
        return result;
    }

    /**
     * 执行批量查询接口的方法
     *
     * @param <P>      双参数函数第一个参数类型
     * @param <U>      双参数函数第二个参数类型
     * @param <T>      查询结果中的类型
     * @param ids      ID 列表，双参数函数第一个参数
     * @param param2   双参数函数第二个参数
     * @param function 实际查询双参数函数，第一个参数是集合，第二个参数是param2
     * @return map 返回结果
     */
    public static <P, U, T> Map<P, T> executeBatchQueryToMapV2(Collection<P> ids, U param2, BiFunction<List<P>, U, Map<P, T>> function) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= SINGLE_QUERY_MAX_VALUE) {
            return function.apply(innerIds, param2);
        }
        Map<P, T> result = new LinkedHashMap<>();
        innerBatchBiParamQuery(innerIds, param2, function, result::putAll);
        return result;
    }

    /**
     * 执行批量查询接口的方法
     *
     * @param <P>      双参数函数第一个参数类型
     * @param <U>      双参数函数第二个参数类型
     * @param <T>      查询结果中的类型
     * @param ids      ID 列表，双参数函数第一个参数
     * @param param2   双参数函数第二个参数
     * @param function 实际查询双参数函数，第一个参数是集合，第二个参数是param2
     * @return map 返回结果
     */
    public static <P, U, T> Map<P, T> executeBatchQueryToMapConcurrentV2(Collection<P> ids, U param2, BiFunction<Collection<P>, U, Map<P, T>> function, ExecutorService executorService, int batchSize) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<P> innerIds = ids.stream().distinct().collect(Collectors.toList());
        int queryNum = innerIds.size();
        if (queryNum <= batchSize) {
            return function.apply(innerIds, param2);
        }
        Map<P, T> result = new ConcurrentHashMap<>();
        innerBatchBiParamConcurrentQuery(innerIds, param2, function, result::putAll, executorService, batchSize);
        return result;
    }

    private static <P, R> void innerBatchQuery(List<P> innerIds, Function<List<P>, R> function, Consumer<R> consumer) {
        int queryNum = innerIds.size();
        int fromIndex = 0;
        int toIndex = SINGLE_QUERY_MAX_VALUE;
        List<P> temp;
        long startTime = System.currentTimeMillis();

        temp = innerIds.subList(fromIndex, toIndex);
        fromIndex = toIndex;
        while (CollectionUtils.isNotEmpty(temp)) {
            R singleResult = function.apply(temp);
            if (singleResult != null) {
                consumer.accept(singleResult);
            }
            toIndex = Math.min(toIndex + SINGLE_QUERY_MAX_VALUE, queryNum);
            temp = innerIds.subList(fromIndex, toIndex);
            fromIndex = toIndex;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime > EXPECTED_QUERY_TIME) {
            logger.warn("invoke batchQueryHelper interface elapse {}, queryNum {}", elapsedTime, queryNum);
        }
    }

    /**
     * 可控分批数量的分批查询方法
     *
     * @param innerIds
     * @param function
     * @param consumer
     * @param maxNum
     * @param <P>
     * @param <R>
     */
    private static <P, R> void innerBatchWithMaxNumQuery(List<P> innerIds, Function<List<P>, R> function, Consumer<R> consumer, Integer maxNum) {
        maxNum = maxNum == null ? SINGLE_QUERY_MAX_VALUE : maxNum;
        int queryNum = innerIds.size();
        int fromIndex = 0;
        int toIndex = maxNum;
        List<P> temp;
        long startTime = System.currentTimeMillis();

        temp = innerIds.subList(fromIndex, toIndex);
        fromIndex = toIndex;
        while (CollectionUtils.isNotEmpty(temp)) {
            R singleResult = function.apply(temp);
            if (singleResult != null) {
                consumer.accept(singleResult);
            }
            toIndex = Math.min(toIndex + maxNum, queryNum);
            temp = innerIds.subList(fromIndex, toIndex);
            fromIndex = toIndex;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime > EXPECTED_QUERY_TIME) {
            logger.warn("invoke batchQueryHelper interface elapse {}, queryNum {}", elapsedTime, queryNum);
        }
    }

    /**
     * 执行两个参数批量查询接口的方法
     *
     * @param innerIds ID 列表
     * @param param2 函数第二个参数
     * @param function 实际查询函数
     * @param <P> 查询参数的类型
     * @param <U> 函数第二个参数类型
     * @param <R>  查询结果中的类型
     * @return list
     */
    private static <P,U,R> void innerBatchBiParamQuery(List<P> innerIds,U param2, BiFunction<List<P>, U, R> function, Consumer<R> consumer) {
        int queryNum = innerIds.size();
        int fromIndex = 0;
        int toIndex = SINGLE_QUERY_MAX_VALUE;
        List<P> temp;
        long startTime = System.currentTimeMillis();

        temp = innerIds.subList(fromIndex, toIndex);
        fromIndex = toIndex;
        while (CollectionUtils.isNotEmpty(temp)) {
            R singleResult = function.apply(temp, param2);
            if (singleResult != null) {
                consumer.accept(singleResult);
            }
            toIndex = Math.min(toIndex + SINGLE_QUERY_MAX_VALUE, queryNum);
            temp = innerIds.subList(fromIndex, toIndex);
            fromIndex = toIndex;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime > EXPECTED_QUERY_TIME) {
            logger.warn("invoke batchQueryHelper interface elapse {}, queryNum {}", elapsedTime, queryNum);
        }
    }

    /**
     * 并发执行两个参数批量查询接口的方法
     *
     * @param innerIds ID 列表
     * @param param2 函数第二个参数
     * @param function 实际查询函数
     * @param <P> 查询参数的类型
     * @param <U> 函数第二个参数类型
     * @param <R>  查询结果中的类型
     * @return list
     */
    private static <P,U,R> void innerBatchBiParamConcurrentQuery(List<P> innerIds,U param2, BiFunction<Collection<P>, U, R> function, Consumer<R> consumer, ExecutorService executorService, int batchSize) {
        int queryNum = innerIds.size();
        int fromIndex = 0;
        int toIndex = batchSize;
        List<P> temp;
        long startTime = System.currentTimeMillis();
        final List<List<P>> partitionTempList = new LinkedList<>();

        temp = innerIds.subList(fromIndex, toIndex);
        fromIndex = toIndex;
        while (CollectionUtils.isNotEmpty(temp)) {
            partitionTempList.add(temp);

            toIndex = Math.min(toIndex + batchSize, queryNum);
            temp = innerIds.subList(fromIndex, toIndex);
            fromIndex = toIndex;
        }

        if (CollectionUtils.isNotEmpty(partitionTempList)) {
            CountDownLatch countDownLatch = new CountDownLatch(partitionTempList.size());
            partitionTempList.forEach(partitionTemp -> {
                executorService.submit(new RunnableWrapper(() -> {
                    try {
                        R singleResult = function.apply(partitionTemp, param2);
                        if (singleResult != null) {
                            consumer.accept(singleResult);
                        }
                    } catch (Exception e) {
                        logger.warn("innerBatchBiParamConcurrentQuery error", e);
                    } finally {
                        logger.info("innerBatchBiParamConcurrentQuery finally, thread:{}", Thread.currentThread().getName());
                        countDownLatch.countDown();
                    }
                }));
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                logger.info("countDownLatch await error", e);
            }
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime > EXPECTED_QUERY_TIME) {
            logger.warn("invoke batchQueryHelper interface elapse {}, queryNum {}", elapsedTime, queryNum);
        }
    }

    /**
     * 执行两个参数批量查询接口的方法
     *
     * @param param1 函数第1个参数
     * @param innerIds ID 列表
     * @param function 实际查询函数
     * @param <U> 函数第1个参数类型
     * @param <P> 查询参数的类型
     * @param <R>  查询结果中的类型
     * @return list
     */
    private static <P,U,R> void innerBatchBiParamQuery(U param1, List<P> innerIds,BiFunction<U, List<P>, R> function, Consumer<R> consumer) {
        int queryNum = innerIds.size();
        int fromIndex = 0;
        int toIndex = SINGLE_QUERY_MAX_VALUE;
        List<P> temp;
        long startTime = System.currentTimeMillis();

        temp = innerIds.subList(fromIndex, toIndex);
        fromIndex = toIndex;
        while (CollectionUtils.isNotEmpty(temp)) {
            R singleResult = function.apply(param1, temp);
            if (singleResult != null) {
                consumer.accept(singleResult);
            }
            toIndex = Math.min(toIndex + SINGLE_QUERY_MAX_VALUE, queryNum);
            temp = innerIds.subList(fromIndex, toIndex);
            fromIndex = toIndex;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime > EXPECTED_QUERY_TIME) {
            logger.warn("invoke batchQueryHelper interface elapse {}, queryNum {}", elapsedTime, queryNum);
        }
    }

    public static void main(String[] args) {
        List<String> ids = Arrays.asList("a", "b", "c", "d");
        executeBatchQueryToMapV2(ids, "+cc", (innerIds, param1) -> {
            Map<String, String> result = new LinkedHashMap<>();
            for (String id : innerIds) {
                result.put(id, id + param1 + "+" + Thread.currentThread().getName());
            }
            return result;
        }).forEach((k, v) -> logger.info(k + " : " + v));

        executeBatchQueryToMapConcurrentV2(ids, "+cc", (innerIds, param1) -> {
            Map<String, String> result = new LinkedHashMap<>();
            for (String id : innerIds) {
                result.put(id, id + param1 + "+" + Thread.currentThread().getName());
            }
            return result;
        }, poolTaskExecutor, 3).forEach((k, v) -> logger.info("concurrent:" + k + " : " + v));

        poolTaskExecutor.shutdown();
    }

}
