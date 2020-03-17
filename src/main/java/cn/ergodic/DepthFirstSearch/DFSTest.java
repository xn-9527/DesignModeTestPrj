package cn.ergodic.DepthFirstSearch;

import cn.ergodic.TreeNode;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 * 深度优先遍历
 *
 * @Author: xiaoni
 * @Date: 2020/3/17 20:00
 */
public class DFSTest {
    /**
     * 构建二叉树
     *
     * @param inputList
     * @return
     */
    public static TreeNode createBinaryTree(LinkedList<Integer> inputList) {
        TreeNode node = null;
        if (inputList == null || inputList.isEmpty()) {
            return null;
        }
        Integer data = inputList.removeFirst();
        if (data != null) {
            node = new TreeNode(data);
            //这种无条件的创建方法，将永远只有左子结点
            node.setLeftChild(createBinaryTree(inputList));
            node.setRightChild(createBinaryTree(inputList));
        }
        return node;
    }

    /**
     * 二叉树前序遍历
     * 先根节点，再左节点，再右节点
     *
     * @param node 节点
     */
    public static void preOrderTraveral(TreeNode node) {
        if(node == null) {
            return;
        }
        System.out.println(node.getData());
        preOrderTraveral(node.getLeftChild());
        preOrderTraveral(node.getRightChild());
    }

    /**
     * 二叉树非递归前序遍历
     *
     * @param root
     */
    public static void preOrderTraveralWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode treeNode = root;
        while(treeNode != null || !stack.isEmpty()) {
            //迭代访问节点的左孩子，并入栈
            while(treeNode != null) {
                System.out.println(treeNode.getData());
                stack.push(treeNode);
                treeNode = treeNode.getLeftChild();
            }
            //如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                treeNode = treeNode.getRightChild();
            }
        }
    }

    /**
     * 二叉树中序遍历
     * 先左节点，再根节点，再右节点
     *
     * @param node
     */
    public static void inOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderTraveral(node.getLeftChild());
        System.out.println(node.getData());
        inOrderTraveral(node.getRightChild());
    }

    /**
     * 二叉树后序遍历
     * 先左，再右，再根
     *
     * @param node
     */
    public static void postOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrderTraveral(node.getLeftChild());
        postOrderTraveral(node.getRightChild());
        System.out.println(node.getData());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入节点数量：");
        int size = sc.nextInt();
        LinkedList<Integer> inputList = new LinkedList<Integer>();
        System.out.println("请输入" + size + "个数，以空格分割");
        for (int i=0;i<size;i++) {
            inputList.add(sc.nextInt());
        }
        sc.close();
        TreeNode treeNode = createBinaryTree(inputList);
        System.out.println("前序遍历：");
        preOrderTraveral(treeNode);
        System.out.println("前序遍历非递归：");
        preOrderTraveralWithStack(treeNode);
        System.out.println("中序遍历：");
        inOrderTraveral(treeNode);
        System.out.println("后序遍历：");
        postOrderTraveral(treeNode);
    }
}
