package cn.test.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by xiaoni on 2018/11/6.
 */
//*表示支持所有的Annotations
@SupportedAnnotationTypes("*")
//表示只支持jdk1.6的代码
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {

    }

    /**
     * 这是AbstractProcessor唯一一个必需覆盖的abstract方法
     * @param annotations 此注解处理器所要处理的注解集合
     * @param roundEnv 访问到当前这个Round中的语法树节点，每个节点表示一个Element。
     *                 在javax.lang.model包中定义了16类Element。详见ElementKind。
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        return false;
    }
}
