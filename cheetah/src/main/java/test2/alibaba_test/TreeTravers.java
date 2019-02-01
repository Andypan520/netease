package test2.alibaba_test;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author pandechuan 2019/01/28
 * 第二题(45分钟内完成)
 * 二叉树层级遍历，Node数据结构为
 * class Node {
 * public String data;
 * public Node left;
 * public Node right;
 * }
 * <p>
 * 比如
 * A
 * / \
 * B  C
 * / \
 * D  E
 */
public class TreeTravers {

    private static List<String> midDataList = Lists.newArrayList();
    private static List<String> preDataList = Lists.newArrayList();
    private static List<String> afterDataList = Lists.newArrayList();

    // 中序遍历
    public static void midSearch(Node node) {
        if (node.left != null) {
            midSearch(node.left);
        }
        midDataList.add(node.data);
        if (node.right != null) {
            midSearch(node.right);
        }
    }

    // 前序遍历
    public static void preSearch(Node node) {
        preDataList.add(node.data);
        if (node.left != null) {
            preSearch(node.left);
        }
        if (node.right != null) {
            preSearch(node.right);
        }
    }

    // 后序遍历
    public static void afterSearch(Node node) {
        if (node.left != null) {
            afterSearch(node.left);
        }
        if (node.right != null) {
            afterSearch(node.right);
        }
        afterDataList.add(node.data);
    }


    public static void main(String[] args) {
        Node nodeD = new Node("D", null, null);
        Node nodeE = new Node("E", null, null);
        Node nodeB = new Node("B", nodeD, null);
        Node nodeC = new Node("C", null, nodeE);
        Node nodeA = new Node("A", nodeB, nodeC);

        midSearch(nodeA);
        System.out.println("middle===" + midDataList);

        preSearch(nodeA);
        System.out.println("previous===" + preDataList);

        afterSearch(nodeA);
        System.out.println("after===" + afterDataList);


    }

    static class Node {
        public String data;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(String data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

}
