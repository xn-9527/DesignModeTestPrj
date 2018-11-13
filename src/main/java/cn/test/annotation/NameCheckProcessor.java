package cn.test.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;
import java.util.Set;

import cn.test.annotation.NameChecker;

/**
 * Created by xiaoni on 2018/11/6.
 */
//*表示支持所有的Annotations
@SupportedAnnotationTypes("*")
//表示只支持jdk1.6的代码
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;

    /**
     * 初始化名称检查插件
     *
     * @param processingEnvironment
     */
    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        nameChecker = new NameChecker(processingEnvironment);
    }

    /**
     * 对输入的语法树的各个节点进行名称检查
     * 这是AbstractProcessor唯一一个必需覆盖的abstract方法
     *
     * @param annotations 此注解处理器所要处理的注解集合
     * @param roundEnv    访问到当前这个Round中的语法树节点，每个节点表示一个Element。
     *                    在javax.lang.model包中定义了16类Element。详见ElementKind。
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                nameChecker.checkNames(element);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        /**
         * G:\WorkSpaceSSD\DesignModeTestPrj\target\classes>javac -processor cn.test.annotation.NameCheckProcessor ../../src/main/java/cn/test/annotation/BADLY_NAMED_CODE.java
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:6: 警告: 名称“BADLY_NAMED_CODE”应当符合驼式命名法（Camel Case Names）
         public class BADLY_NAMED_CODE {
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:8: 警告: 名称“colors”应当以大写字母开头
         enum colors {
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:9: 警告: 常量“red”应当全部以大写字母或下划线命名，并且以字母开头
         red, blue, green;
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:9: 警告: 常量“blue”应当全部以大写字母或下划线命名，并且以字母开头
         red, blue, green;
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:9: 警告: 常量“green”应当全部以大写字母或下划线命名，并且以字母开头
         red, blue, green;
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:12: 警告: 常量“_FORTY_TWO”应当全部以大写字母或下划线命名，并且以字母开头
         static final int _FORTY_TWO = 42;
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:14: 警告: 名称“NOT_A_CONSTANT”应当以小写字母开头
         public static int NOT_A_CONSTANT = _FORTY_TWO;
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:16: 警告: 一个普通方法 “BADLY_NAMED_CODE”不应当与类名重复，避免与构造函数产生混淆
         protected void BADLY_NAMED_CODE() {
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:16: 警告: 名称“BADLY_NAMED_CODE”应当以小写字母开头
         protected void BADLY_NAMED_CODE() {
         ^
         ..\..\src\main\java\cn\test\annotation\BADLY_NAMED_CODE.java:20: 警告: 名称“NOTcamelCASEmethodNAME”应当以小写字母开头
         public void NOTcamelCASEmethodNAME() {
         ^
         10 个警告

         */
    }
}
