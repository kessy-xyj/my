package indi.uhyils.util;

import java.lang.reflect.*;

/**
 * 反射工具类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月27日 16时46分
 */
public class ReflactUtil {


    /**
     * 获取实例中的某个属性的值
     *
     * @param obj      实例
     * @param attrName 属性
     * @return 属性的值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getAttrByGetMethod(Object obj, String attrName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (obj == null) {
            return null;
        }
        if (attrName != null && attrName.length() > 1) {
            Method declaredMethod = obj.getClass().getDeclaredMethod("get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1));
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj);
        } else {
            return null;
        }
    }

    /**
     * 用field获取类某个属性的名称
     *
     * @param object
     * @param attrName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getAttrByField(Object object, String attrName) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = object.getClass().getDeclaredField(attrName);
        declaredField.setAccessible(true);
        return declaredField.get(object);
    }

    /**
     * 获取某个方法的返回值的泛型
     *
     * @return 所有泛型
     */
    public static Class getGenericity(Method method) throws ClassNotFoundException {
        method.setAccessible(true);
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            //因为list泛型只有一个值 所以直接取0下标
            String typeName = actualTypeArguments[0].getTypeName();
            //真实返回值类型 Class对象
            return Class.forName(typeName);
        }
        return null;
    }

}
