/*
 [The "BSD license"]
 Copyright (c) 2011-2014 Joel Li (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core;

import org.beetl.core.cache.Cache;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.HTMLTagParserException;
import org.beetl.core.exception.ScriptEvalError;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.misc.ByteClassLoader;
import org.beetl.core.misc.ClassSearch;
import org.beetl.core.om.ObjectUtil;
import org.beetl.core.statement.Program;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统核心类，详见指南
 *
 * @author joelli
 */
public class GroupTemplate {


    private Configuration conf;
    private ResourceLoader resourceLoader;
    private ClassLoader classLoader;
    private ByteClassLoader byteLoader;
    private ErrorHandler errorHandler;
    private ClassSearch classSearch;
    private List<Listener> ls = new ArrayList<>();
    private Map<String, Object> sharedVars;
    private List<VirtualAttributeEval> virtualAttributeList = new ArrayList<VirtualAttributeEval>();


    public GroupTemplate() {

    }

    public GroupTemplate(Configuration conf) {

        try {
            this.conf = conf;
        } catch (Exception ex) {
            throw new RuntimeException("初始化失败", ex);
        }

    }

    /**
     * 使用loader 和 conf初始化GroupTempalte
     *
     * @param loader 资源加载器
     * @param conf   模板引擎配置
     */

    public GroupTemplate(ResourceLoader loader, Configuration conf) {

        try {
            this.resourceLoader = loader;
            this.conf = conf;
        } catch (Exception ex) {
            throw new RuntimeException("初始化失败", ex);
        }

    }

    /**
     * GroupTempalte 动态加载默写类使用的classloader
     *
     * @param classLoader 资源加载器
     */

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        byteLoader = new ByteClassLoader(classLoader);
    }


    /**
     * 返回用来加载动态类的classloader，此加载类的parent loader是通过
     * setClassLoader 添加的，默认就是加载beetl包的classloader
     *
     * @return
     */
    public ByteClassLoader getByteLoader() {
        return byteLoader;
    }


    /**
     * 执行某个脚本，参数是paras，返回的是顶级变量
     *
     * @param key
     * @param paras
     * @return
     */
    public Map runScript(String key, Map<String, Object> paras) throws ScriptEvalError {
        return this.runScript(key, paras, null);

    }

    /**
     * 执行某个脚本，参数是paras，返回的是顶级变量 ,如果script有输出，则输出到writer里
     *
     * @param key
     * @param paras
     * @param w
     * @return
     */
    public Map runScript(String key, Map<String, Object> paras, Writer w) throws ScriptEvalError {
        return this.runScript(key, paras, w, this.resourceLoader);
    }

    /**
     * 执行某个脚本，参数是paras，返回的是顶级变量
     *
     * @param key
     * @param paras
     * @param w
     * @param loader 额外的资源管理器就在脚本
     * @return
     * @throws ScriptEvalError
     */
    public Map runScript(String key, Map<String, Object> paras, Writer w, ResourceLoader loader) throws ScriptEvalError {
        throw new UnsupportedOperationException();
    }

    public BeetlException validateTemplate(String key, ResourceLoader loader) {
        throw new UnsupportedOperationException();
    }

    public BeetlException validateTemplate(String key) {
        throw new UnsupportedOperationException();
    }

    public BeetlException validateScript(String key, ResourceLoader loader) {
        throw new UnsupportedOperationException();
    }

    public BeetlException validateScript(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 使用额外的资源加载器加载模板
     *
     * @param key
     * @param loader
     * @return
     */
    public Template getTemplate(String key, ResourceLoader loader) {
        return this.getTemplateByLoader(key, loader, true);
    }

    /**
     * 获取模板key的标有ajaxId的模板片段。
     *
     * @param key
     * @param ajaxId
     * @param loader
     * @return
     */
    public Template getAjaxTemplate(String key, String ajaxId, ResourceLoader loader) {
        Template template = this.getTemplateByLoader(key, loader, true);
        template.ajaxId = ajaxId;
        return template;
    }

    /**
     * 得到模板，并指明父模板
     *
     * @param key
     * @param parent
     * @return
     */
    public Template getTemplate(String key, String parent, ResourceLoader loader) {
        Template template = this.getTemplate(key, loader);
        template.isRoot = false;
        return template;
    }

    /**
     * 得到模板，并指明父模板。
     *
     * @param key
     * @param parent，此参数目前未使用
     * @return
     */
    public Template getTemplate(String key, String parent) {
        Template template = this.getTemplate(key);
        template.isRoot = false;
        return template;
    }

    public Template getHtmlFunctionOrTagTemplate(String key, String parent) {
        Template template = this.getHtmlFunctionOrTagTemplate(key);
        template.isRoot = false;
        return template;
    }

    /**
     * 获取指定模板。
     * 注意，不能根据Template为空来判断模板是否存在，请调用ResourceLoader来判断
     *
     * @param key
     * @return
     */
    public Template getTemplate(String key) {

        return getTemplateByLoader(key, this.resourceLoader, true);
    }

    /**
     * 对于用html function 或者html tag，允许使用特殊定界符号，此方法会检查是否为此配置了特殊的定界符号，并引用
     *
     * @param key
     * @return
     */
    public Template getHtmlFunctionOrTagTemplate(String key) {
        return getTemplateByLoader(key, this.resourceLoader, false);
    }

    /**
     * 获取模板的ajax片段，
     *
     * @param key           ，key为模板resourceId
     * @param ajaxId,ajax标示
     * @return
     */
    public Template getAjaxTemplate(String key, String ajaxId) {

        Template t = getTemplateByLoader(key, this.resourceLoader, true);
        t.ajaxId = ajaxId;
        return t;
    }

    private Template getTemplateByLoader(String key, ResourceLoader loader, boolean isTextTemplate) {

        Resource resource = loader.getResource(key);
        Program program = this.loadTemplate(resource, isTextTemplate);
        return new Template(this, program, this.conf);
    }

    private Program loadTemplate(Resource res, boolean isTextTemplate) {

        Program program = new Program();
        program.gt = this;
        program.rs = program.res = res;

        return program;
    }


    public Program getProgram(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 判断是否加载过模板
     *
     * @param key
     * @return
     */
    public boolean hasTemplate(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 手工删除加载过的模板
     *
     * @param key
     */
    public void removeTemplate(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 关闭GroupTemplate，清理所有的资源
     */
    public void close() {
        this.resourceLoader.close();
        ContextLocalBuffer.threadLocal.remove();
    }

    // /** 为事件类型注册一个监听器
    // * @param type
    // * @param e
    // */
    // public void onEvent(int type,Listener e){
    //
    // }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Configuration getConf() {
        return conf;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void fireEvent(Event event) {
        for (Listener l : this.ls) {
            l.onEvent(event);
        }
    }

    public void addListener(Listener listener) {
        this.ls.add(listener);
    }

    public Cache getProgramCache() {

        throw new UnsupportedOperationException();
    }

    public void registerFunction(String name, Function fn) {
        throw new UnsupportedOperationException();
    }

    /**
     * 注册一个function包，包名由packageName指定，方法名是对象o里的所有方法
     * 如果方法定义为void，则方法返回null，如果方法参数最后一个Context，则传入一个Context
     *
     * @param packageName
     * @param o
     */
    public void registerFunctionPackage(String packageName, Object o) {
        checkFunctionName(packageName);
        registerFunctionPackage(packageName, o.getClass(), o);

    }

    public void registerFunctionPackage(String packageName, Class cls) {
        checkFunctionName(packageName);
        Object o = ObjectUtil.tryInstance(cls.getName());
        registerFunctionPackage(packageName, cls, o);

    }

    private void checkFunctionName(String name) {

        if (!BeetlUtil.checkNameing(name)) {
            int[] log = BeetlUtil.getLog();
            throw new RuntimeException("注册方法名不合法:" + name + ",错误位置:" + log[0] + ",出现错误的字符:" + (char) log[1]);
        }
    }

    protected void registerFunctionPackage(String packageName, Class target, Object o) {

        throw new UnsupportedOperationException();

    }

    /**
     * 注册一个自定义格式化函数
     *
     * @param name
     * @param format
     */
    public void registerFormat(String name, Format format) {
        throw new UnsupportedOperationException();
    }

    public void registerDefaultFormat(Class type, Format format) {
        throw new UnsupportedOperationException();
    }

    public void registerTag(String name, Class tagCls) {
        throw new UnsupportedOperationException();
    }

    public void registerTagFactory(String name, TagFactory tagFactory) {
        throw new UnsupportedOperationException();
    }

    private void checkTagName(String name) {
        if (!BeetlUtil.checkNameing(name)) {
            int[] log = BeetlUtil.getLog();
            if (log[1] == 58) {
                throw new RuntimeException("注册Tag名称不合法:" + name + ",错误位置:" + log[0] + ",出现错误的字符:" + (char) log[1]
                        + ",请使用\'.\'");
            } else {
                throw new RuntimeException("注册Tag名称不合法:" + name + ",错误位置:" + log[0] + ",出现错误的字符:" + (char) log[1]);
            }
        }
    }

    public TagFactory getTagFactory(String name) {
        throw new UnsupportedOperationException();
    }

    public Function getFunction(String name) {
        throw new UnsupportedOperationException();
    }

    public Format getFormat(String name) {
        throw new UnsupportedOperationException();
    }

    public Format getDefaultFormat(Class type) {
        throw new UnsupportedOperationException();

    }

    public void registerVirtualAttributeEval(VirtualAttributeEval e) {
        virtualAttributeList.add(e);
    }

    public void registerVirtualAttributeClass(Class cls, VirtualClassAttribute virtual) {
        throw new UnsupportedOperationException();

    }

    public VirtualClassAttribute getVirtualAttributeEval(Class c, String attributeName) {

        throw new UnsupportedOperationException();

    }

    /**
     * 通过class的简单名字找到class
     *
     * @param simpleName
     * @return
     */
    public Class loadClassBySimpleName(String simpleName) {
        throw new UnsupportedOperationException();
    }

    public NativeSecurityManager getNativeSecurity() {
        throw new UnsupportedOperationException();
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public Map<String, Object> getSharedVars() {
        return sharedVars;

    }

    public void setSharedVars(Map<String, Object> sharedVars) {
        this.sharedVars = sharedVars;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public ClassSearch getClassSearch() {
        return classSearch;
    }


}
