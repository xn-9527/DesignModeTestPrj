package cn.tree.minGenerateTree;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Created by xiaoni on 2019/7/1.
 *         用于求得最小生成树——图的极小连通子图，不需要回路
 *         常用的算法有kruskal算法和prim算法，prim算法本质是基于贪心算法。
 *         最小生成树的存储：一维数组，数组下标(从0开始)所对应的元素，代表该顶点在最小生成树当中的父亲节点(根节点没有父亲节点，所以元素值是-1)
 */
@Slf4j
public class Prim {
    final static int INF = Integer.MAX_VALUE;

    public static int[] prim(int[][] matrix) {
        List<Integer> reachedVertexList = new ArrayList<>();
        //选择顶点0为初始顶点，放入已触达顶点的集合中
        reachedVertexList.add(0);
        //创建最小生成树数组，首元素设为-1
        int[] parents = new int[matrix.length];
        parents[0] = -1;

        //边的权重
        int weight;
        //源顶点下标
        int fromIndex = 0;
        //目标顶点下标
        int toIndex = 0;

        while (reachedVertexList.size() < matrix.length) {
            weight = INF;
            //在已触达的顶点中，寻找到达新顶点的最短边
            for (Integer vertexIndex : reachedVertexList) {
                for (int i = 0; i < matrix.length; i++) {
                    if (!reachedVertexList.contains(i)) {
                        if (matrix[vertexIndex][i] < weight) {
                            fromIndex = vertexIndex;
                            toIndex = i;
                            weight = matrix[vertexIndex][i];
                            log.info("源顶点下标:{},目标顶点下标:{},权重:{}", fromIndex, toIndex, weight);
                        }
                    }
                }
            }
            log.info("######最终源顶点下标:{},目标顶点下标:{},权重:{}", fromIndex, toIndex, weight);
            //确定了权值最小的目标顶点，放入已触达顶点集合
            reachedVertexList.add(toIndex);
            log.info("********已触达顶点集合为:{}", reachedVertexList);
            //放入最小生成树的数组
            parents[toIndex] = fromIndex;
            log.info("========最小生成树数组为:{}", Arrays.toString(parents));
        }
        return parents;
    }

    public static void main(String[] args) {
        //图的存储方式是邻接矩阵
        /**
         * 邻接矩阵（Adjacency Matrix）是表示顶点之间相邻关系的矩阵。设G=(V,E)是一个图，其中V={v1,v2,…,vn} [1]  。G的邻接矩阵是一个具有下列性质的n阶方阵：
         ①对无向图而言，邻接矩阵一定是对称的，而且主对角线一定为零（在此仅讨论无向简单图），副对角线不一定为0，有向图则不一定如此。
         ②在无向图中，任一顶点i的度为第i列（或第i行）所有非零元素的个数，在有向图中顶点i的出度为第i行所有非零元素的个数，而入度为第i列所有非零元素的个数。
         ③用邻接矩阵法表示图共需要n^2个空间，由于无向图的邻接矩阵一定具有对称关系，所以扣除对角线为零外，仅需要存储上三角形或下三角形的数据即可，因此仅需要n（n-1）/2个空间。
         *
         * (0)-----4-----(1)------7-----(3)
         *  |             |              |
         *  3             8              9
         *  |             |              |
         * (2)--------------------1-----(4)
         */
        int[][] matrix = new int[][]{
                {0, 4, 3, INF, INF},
                {4, 0, 8, 7, INF},
                {3, 8, 9, INF, 1},
                {INF, 7, INF, 0, 9},
                {INF, INF, 1, 9, 0}
        };
        int[] parents = prim(matrix);
        //最小生成树[-1, 0, 0, 1, 2]
        /**
         * (0)-----4-----(1)------7-----(3)
         *  |
         *  3
         *  |
         * (2)--------------------1-----(4)
         */
        System.out.println(Arrays.toString(parents));//[-1, 0, 0, 1, 2]
    }

}
