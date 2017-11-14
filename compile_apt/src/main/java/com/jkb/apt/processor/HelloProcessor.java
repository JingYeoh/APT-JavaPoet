package com.jkb.apt.processor;


import com.google.auto.service.AutoService;
import com.jkb.apt.annimator.HelloAPT;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

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

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
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
