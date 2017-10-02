package org.cheetah.core;

/**
 * @author Wooyea
 */
public class ParserContext {
    public VarsMap vars  = new VarsMap();

    public void bindVar(String key, Object var){
        vars.bind(key, var);
    }

    public Object getValueByPath(String path){
        return vars.getByPath(path);
    }
}
