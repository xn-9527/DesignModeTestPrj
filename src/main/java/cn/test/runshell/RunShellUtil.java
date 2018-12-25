package cn.test.runshell;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Created by chay on 2018/12/24.
 */
@Slf4j
public class RunShellUtil {
    public static void main(String[] args) {
        String shPath = "G:\\WorkSpaceSSD\\DesignModeTestPrj\\src\\main\\java\\cn\\test\\runshell\\demo-classes.bat";
        runShell(shPath);
    }

    public static void runShell(String shPath) {
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(shPath);
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
            log.error("run shell io exception:" + e.getMessage(), e);
        } catch (InterruptedException e) {
            log.error("run shell interrupted exception:" + e.getMessage(), e);
        } finally {
            if (ps != null) {
                ps.destroy();
            }
        }
    }
}
