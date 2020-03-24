package cn.tree.ergodic.temp;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Chay
 * @date 2020/3/24 9:59
 */
public class Test2 {
    public static class TreeNode {
        int data;
        TreeNode leftChild;
        TreeNode rightChild;

        public TreeNode(int data) {
            this.data = data;
        }
    }

    public static TreeNode createTree(LinkedList<Integer> input) {
        TreeNode treeNode = null;
        if (input == null || input.isEmpty()) {
            return null;
        }
        Integer data = input.removeFirst();
        if (data != null) {
            treeNode = new TreeNode(data);
            treeNode.leftChild=createTree(input);
            treeNode.rightChild=createTree(input);
        }
        return treeNode;
    }

    public static void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.data);
        preOrder(root.leftChild);
        preOrder(root.rightChild);
    }

    public static void levelOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.data);
            if (node.leftChild != null) {
                queue.add(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.add(node.rightChild);
            }
        }
    }

    public static void quickSort(int[] input, int start, int end) {
        //!!!!!终止条件
        if (start >= end) {
            return;
        }
        int middle = getMiddle(input, start, end);
        quickSort(input, start, middle - 1);
        quickSort(input, middle + 1, end);
    }

    public static int getMiddle(int[] input, int start, int end) {
        int pivot = input[start];
        int left = start;
        int right = end;
        while(left != right) {
            while (left < right && input[right] > pivot) {
                right --;
            }
            while (left < right && input[left] <= pivot) {
                left ++;
            }
            if (left < right) {
                swap(input, left, right);
            }
        }
        input[start] = input[left];
        input[left] = pivot;

        return left;
    }

    public static void swap(int[] input, int a, int b) {
        int temp = input[a];
        input[a] = input[b];
        input[b] = temp;
    }

    public static void main(String[] args) {
        int[] input = {1, 4,3,6,2,8,5};
        quickSort(input, 0, input.length -1);
        System.out.println(Arrays.toString(input));

        System.out.println("#################");
        LinkedList<Integer> input1 = new LinkedList<>();
        input1.add(1);
        input1.add(4);
        input1.add(3);
        input1.add(6);
        input1.add(2);
        input1.add(8);
        input1.add(5);
        TreeNode node = createTree(input1);
        preOrder(node);
        System.out.println("#################");
        levelOrder(node);
    }
}
