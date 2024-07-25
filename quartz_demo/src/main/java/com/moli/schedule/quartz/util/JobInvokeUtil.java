package com.moli.schedule.quartz.util;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author moli
 * @time 2024-07-16 17:55:18
 * @description 定时任务执行工具类
 */
public class JobInvokeUtil {

    public static void invokeMethod(String invokeTarget) throws Exception {
        // 1. 获取方法名
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        // 2. 获取方法参数
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        // 3. 获取bean，并执行对应的方法
        if (!isValidClassName(invokeTarget)) {
            Object bean = SpringUtil.getBean(beanName);
            invokeMethod(bean, methodName, methodParams);
        } else {
            Object bean = Class.forName(beanName).newInstance();
            invokeMethod(bean, methodName, methodParams);
        }
    }

    private static void invokeMethod(Object bean, String methodName, List<Object[]> params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!CollectionUtils.isEmpty(params)) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(params));
            method.invoke(bean, getMethodParamsValue(params));
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    public static Class<?>[] getMethodParamsType(List<Object[]> params) {
        Class<?>[] classes = new Class[params.size()];
        int index = 0;
        for (Object[] pat : params) {
            classes[index++] = (Class<?>) pat[1];
        }
        return classes;
    }

    public static Object[] getMethodParamsValue(List<Object[]> params) {
        Object[] values = new Object[params.size()];
        int index = 0;
        for (Object[] pat : params) {
            values[index++] = pat[0];
        }
        return values;
    }

    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    public static String getBeanName(String invokeTarget) {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    public static String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr)) return null;
        String[] methodParams = methodStr.split(",");
        List<Object[]> classes = new LinkedList<>();
        for (String methodParam : methodParams) {
            String param = StringUtils.trimToEmpty(methodParam);
            if (StringUtils.contains(param, "'")) {
                classes.add(new Object[]{StringUtils.replace(param, "'", ""), String.class});
            } else if (StringUtils.equals(param, "true") || StringUtils.equals(param, "false")) {
                classes.add(new Object[]{Boolean.valueOf(param), Boolean.class});
            } else if (StringUtils.containsIgnoreCase(param, "L")) {
                classes.add(new Object[]{Long.valueOf(StringUtils.replaceIgnoreCase(param, "L", "")), Long.class});
            } else if (StringUtils.containsIgnoreCase(param, "D")) {
                classes.add(new Object[]{Double.valueOf(StringUtils.replaceIgnoreCase(param, "D", "")), Double.class});
            } else {
                classes.add(new Object[]{Integer.valueOf(param), Integer.class});
            }
        }
        return classes;
    }
}
