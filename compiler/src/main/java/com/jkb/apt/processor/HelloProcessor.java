package com.jkb.apt.processor;


import com.google.auto.service.AutoService;
import com.jkb.apt.annimator.HelloAPT;
import com.jkb.apt.utils.Consts;
import com.jkb.apt.utils.Logger;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * APT注解解释器
 *
 * @author JingYeoh.
 *         Date 17-11-14.
 *         Github https://github.com/justkiddingbaby
 *         Blog http://blog.justkiddingbaby.com
 */
@AutoService(Processor.class)
public class HelloProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elements;
    private Logger logger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        elements = processingEnvironment.getElementUtils();
        logger = new Logger(processingEnvironment.getMessager());

        logger.i("HelloProcessor init() method");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        logger.i("HelloProcessor process() method");
        List<TypeElement> typeElements = new ArrayList<>();
        //获取被该注解声明的元素
        Set<? extends Element> annotationSet = roundEnvironment.getElementsAnnotatedWith(HelloAPT.class);
        for (Element element : annotationSet) {
            //判断元素类型
            ElementKind kind = element.getKind();
            if (kind != ElementKind.CLASS) {
                logger.e("HelloAPT Annotation can only worked on class");
                return false;
            }
            TypeElement typeElement = (TypeElement) element;
            typeElements.add(typeElement);
        }
//        return generateCodes(typeElements);
        return generateCodesByPoet(typeElements);
    }

    /**
     * 生成代码
     */
    private boolean generateCodes(List<TypeElement> typeElements) {
        if (typeElements == null || typeElements.isEmpty()) {
            logger.w("@HelloProcessor is not found!!!");
            return false;
        }
        for (TypeElement element : typeElements) {
            HelloAPT annotation = element.getAnnotation(HelloAPT.class);
            if (annotation == null) continue;
            String annotationValue = annotation.value();
            String simpleName = element.getSimpleName().toString();
            logger.i("----->" + simpleName + "<-----" + annotationValue);
            //创建java文件
            try {
                simpleName += "$" + "print";
                FileObject fileObject = filer.createResource(StandardLocation.SOURCE_OUTPUT, Consts
                        .PACKAGE_OF_GENERATE_FILE, simpleName + ".java");
                Writer writer = fileObject.openWriter();
                writer.append("package ").append(Consts.PACKAGE_OF_GENERATE_FILE).append(";").append("\n");
                writer.append("/*This file is auto created by APT,please don't edit it!!!*/").append("\n");
                writer.append("public ").append("class ").append(simpleName).append("{").append("\n");
                writer.append("public static void main(String[]args)").append("{").append("\n");
                writer.append("System.out.println(").append("\"").append(annotationValue).append("\"").append(");");
                writer.append("\n");
                writer.append("}");
                writer.append("\n");
                writer.append("}");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 通过JavaPoet生成代码
     */
    private boolean generateCodesByPoet(List<TypeElement> typeElements) {
        if (typeElements == null || typeElements.isEmpty()) {
            logger.w("@HelloProcessor is not found!!!");
            return false;
        }
        for (TypeElement element : typeElements) {
            HelloAPT annotation = element.getAnnotation(HelloAPT.class);
            if (annotation == null) continue;
            String annotationValue = annotation.value();
            String className = element.getSimpleName().toString() + "$Poet";
            logger.i("----->" + className + "<-----" + annotationValue);

            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, annotationValue)
                    .build();
            TypeSpec classSpec = TypeSpec.classBuilder(className)
                    .addJavadoc(Consts.WARNING_TIPS)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(main)
                    .build();
            try {
                JavaFile.builder(Consts.PACKAGE_OF_GENERATE_FILE, classSpec)
                        .build().writeTo(filer);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new HashSet<>();
        result.add(HelloAPT.class.getName());
        return result;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
