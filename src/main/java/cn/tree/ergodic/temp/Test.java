package cn.tree.ergodic.temp;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Chay
 * @date 2020/3/19 9:18
 */
public class Test {
    public static class TreeNode {
        int data;
        TreeNode leftChild;
        TreeNode rightChild;

        public TreeNode(int data) {
            this.data = data;
        }
    }

    public static TreeNode createTree(LinkedList<Integer> input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        Integer data = input.removeFirst();
        if (data != null) {
            TreeNode treeNode = new TreeNode(data);
            treeNode.leftChild = createTree(input);
            treeNode.rightChild = createTree(input);
            return treeNode;
        }

        return null;
    }

    public static void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data);
        if (node.leftChild != null) {
            preOrder(node.leftChild);
        }
        if (node.rightChild != null) {
            preOrder(node.rightChild);
        }
    }

    public static void levelOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.data);

            if (node.leftChild != null) {
                queue.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.offer(node.rightChild);
            }
        }


    }

    public static void quickSort(int[] input, int start, int end) {
        if (start >= end) {
            return;
        }
        int middle = getMiddle(input , start, end);
        quickSort(input, start, middle - 1);
        quickSort(input, middle + 1, end);
    }

    public static int getMiddle(int[] input, int start, int end) {
        int pivot = input[start];
        int left = start;
        int right = end;
        while(left != right) {
            while(left < right && input[right] > pivot) {
                right --;
            }
            while(left < right && input[left] <= pivot) {
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

    public static void swap(int[] input, int start, int end) {
        int temp = input[start];
        input[start] = input[end];
        input[end] = temp;
    }

    public static void main(String[] args) {
        LinkedList<Integer> input = new LinkedList<Integer>();
        input.add(1);
        input.add(24);
        input.add(23);
        input.add(56);
        input.add(10);
        TreeNode node = createTree(input);
        preOrder(node);
        System.out.println("##########");
        levelOrder(node);

        int[] input2 = {1,2,6,3,80,44};
        quickSort(input2, 0, input2.length -1);
        System.out.println(
            Arrays.toString(input2)
        );
    }
}
