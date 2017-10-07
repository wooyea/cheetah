package org.cheetah.core;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wooyea
 */
public class VarsMap {

    public Map<String, Object> vars = new HashMap<>();
    public Map< Class<? extends Object>, ClassTools> classTools = new HashMap<>();


    class ClassTools {
        public FieldAccess fieldAccess;
        public MethodAccess methodAccess;
    }

    public void bind(String key, Object value){

        Class<? extends Object> valueClass = value.getClass();

        if (!classTools.containsKey(value.getClass())) {
            ClassTools ct = new ClassTools();
            ct.fieldAccess = FieldAccess.get(valueClass);
            ct.methodAccess = MethodAccess.get(valueClass);

            classTools.put(valueClass, ct);
        }

        vars.put(key, value);
    }

    public Map<String, Object> getVars() {
        return vars;
    }

    public Object getByPath(String path) {
        String[] pathParts = path.split("\\.");

        if (pathParts == null || pathParts.length == 0) {

            pathParts = new String[1];
            pathParts[0] = path;
        }

        Object o = vars;

        for (int i=0; i< pathParts.length; ++i) {
            o = getValueByName(o, pathParts[i]);
            if (o == null) {
                break;
            }
        }

        return o;
    }

    public Object getValueByName(Object source, String name){
        if (source instanceof Map) {
            return ((Map)source).get(name);
        } else {
            ClassTools classTool = classTools.get(source.getClass());
            classTool.fieldAccess.get(source, name);
            String getterName = "get"
                    + name.substring(0,1).toUpperCase()
                    + name.substring(1);
            int index = classTool.methodAccess.getIndex(getterName);
            return classTool.methodAccess.invoke(source, index);
        }
    }
}
