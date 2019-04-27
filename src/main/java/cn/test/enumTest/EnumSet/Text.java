package cn.test.enumTest.EnumSet;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by xiaoni on 2019/4/27.
 * 用EnumSet代替位操作
 */
@Slf4j
public class Text {
    public enum Style {
        BOLD,
        ITALIC,
        UNDERLINE,
        STRIKE_THROUGH
    }

    //any set could be passed in, but EnumSet is clearley best
    public void applyStyles(Set<Style> styles) {
        styles.forEach(style -> {
            log.info(style.toString());
        });
    }

    public static void main(String[] args) {
        Text text = new Text();
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
    }
}
