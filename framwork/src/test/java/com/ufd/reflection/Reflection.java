package com.ufd.reflection;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.ufd.framwork.CustomExceptions;



public class Reflection {
	static LinkedHashMap<String, String> packageNameMap = null;
	
	@SuppressWarnings("rawtypes")
	static Class currentClassObject;
	
	@SuppressWarnings("rawtypes")
	public static LinkedHashMap<String, String> findAllPackages() throws CustomExceptions {
	    
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());
		Reflections reflections = new Reflections(new ConfigurationBuilder()
		        .setScanners(new SubTypesScanner(false), new ResourcesScanner())
		        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		        	);
		Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
		packageNameMap = new LinkedHashMap<String, String>();
		for (Class classInstance : classes) {
		    String packageName = classInstance.getPackage().getName();
		    String className = classInstance.getSimpleName();
		    if(!packageNameMap.containsKey(className)){
		    	packageNameMap.put(className, packageName);	
		    }else{
		    	throw new CustomExceptions("Class Name : " + className.toUpperCase() + " already exist in " + packageNameMap.get(className) 
		    	+ " package, please update the class name in " + packageName.toUpperCase() + "package" );
		    } 
		}
	    return packageNameMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getClass(String className) throws CustomExceptions{
		String pkg = packageNameMap.get(className);
		try {
			currentClassObject = Class.forName(pkg+"."+className);
		} catch (ClassNotFoundException e) {
			throw new CustomExceptions(e.getMessage());
		}
		return currentClassObject;
	}
	
	public static Field getFiled(String name) throws CustomExceptions{
		Field field;
		try {
			field = currentClassObject.getField(name);
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new CustomExceptions(e.getMessage());
		} catch (SecurityException e) {
			throw new CustomExceptions(e.getMessage());
		}
		return field;
	}

}
