package cn.tree.ergodic.BreadthFirstSearch;

import cn.tree.ergodic.DepthFirstSearch.DFSTest;
import cn.tree.ergodic.TreeNode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 广度优先遍历
 *
 * @Author: xiaoni
 * @Date: 2020/3/17 19:57
 */
public class BFSTest {

    public static void levelOrderTraversal(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.getData());
            if (node.getLeftChild() != null) {
                queue.offer(node.getLeftChild());
            }
            if(node.getRightChild() != null) {
                queue.offer(node.getRightChild());
            }
        }
    }

    public static void main(String[] args) {
        Integer[] input = {1,20,32,5,43};
        TreeNode node = DFSTest.createBinaryTree(new LinkedList<Integer>(Arrays.asList(input)));
        levelOrderTraversal(node);
    }
}
