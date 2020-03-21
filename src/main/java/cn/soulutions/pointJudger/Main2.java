package cn.soulutions.pointJudger;

import java.util.Scanner;

//只能通过70%,求大佬帮忙看看
public class Main2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        int[][] p = new int[N][2];
        for (int i = 0; i < N; i++) {
            p[i][0] = input.nextInt();
            p[i][1] = input.nextInt();
        }
        input.close();
        Solution(N, p);
    }

    public static void Solution(int N, int[][] p) {
        QuickSort(p, 0, N - 1);
        int[] ymax = new int[N];
        ymax[N - 1] = p[N - 1][1];
        for (int i = N - 2; i >= 0; i--) {
            ymax[i] = (p[i][1] > ymax[i + 1]) ? p[i][1] : ymax[i + 1];
        }
        for (int i = 0; i < N; i++) {
            if (p[i][1] == ymax[i]) {
                System.out.println(p[i][0] + " " + p[i][1]);
            }
        }
    }

    public static void QuickSort(int[][] p, int start, int end) {
        int i = start;
        int j = end;
        int k = p[start][0];
        int[] t;
        while (i < j) {
            t = p[i];
            while (i < j && p[j][0] >= k) {
                j--;
            }
            p[i] = p[j];
            p[j] = t;
            while (i < j && p[i][0] <= k) {
                i++;
            }
            p[j] = p[i];
            p[i] = t;
        }
        if (i - 1 > start) QuickSort(p, start, i - 1);
        if (i + 1 < end) QuickSort(p, i + 1, end);
    }
}
