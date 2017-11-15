package com.jkb.apt;    //PackageElement

public class MyClass<T> {  //TypeElement

    private int a;      //VariableElement
    private String b;
    private MyClass instance;   //VariableElement
    private T t;    //TypeParameterElement

    public MyClass(T t) {  //ExecutableElement
        this.t = t;     //泛型参数t为TypeParameterElement
    }

    public void setA(int a) {   //ExecutableElement
        this.a = a;
    }
}
