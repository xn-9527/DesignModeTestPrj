package cn.test.runshell;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Created by xiaoni on 2018/12/24.
 */
public class RunShell {
    public static void main(String[] args) {
        try {
            runShell();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runShell() {
        //            String shpath="G:\\WorkSpaceSSD\\DesignModeTestPrj\\src\\main\\java\\cn\\test\\runshell\\demo-classes.sh";
        Process ps = null;
        try {
            String shpath = "G:\\WorkSpaceSSD\\DesignModeTestPrj\\src\\main\\java\\cn\\test\\runshell\\demo-classes.bat";
            ps = Runtime.getRuntime().exec(shpath);
            ps.waitFor();

            try(BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));) {
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                String result = sb.toString();
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.destroy();
            }
        }
    }
}
