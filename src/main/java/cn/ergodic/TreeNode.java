package cn.ergodic;

import lombok.Data;

/**
 * @Author: xiaoni
 * @Date: 2020/3/17 19:58
 */
@Data
public class TreeNode {
    int data;
    TreeNode leftChild;
    TreeNode rightChild;

    public TreeNode(int data) {
        this.data = data;
    }
}
