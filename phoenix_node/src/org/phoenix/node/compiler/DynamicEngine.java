package org.phoenix.node.compiler;
import javax.tools.*;

import org.phoenix.aop.PhoenixLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
 
/**
 * 动态编译引擎
 * @author mengfeiyang
 *
 */
public class DynamicEngine {
    private static DynamicEngine ourInstance = new DynamicEngine();
    private String compileError; 
	private static String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ");
	 
	private static String WEB_INF = classPath.substring(0, classPath.length()-1);
	private static String WEB_INF_Path = WEB_INF.substring(0, WEB_INF.lastIndexOf("/"))+"/";
	 
	private static String libPath = WEB_INF_Path+"lib/";
	
	public String getWebInfPath(){
		return WEB_INF_Path;
	}
	public String getLibPath(){
		return libPath;
	}
    public String getCompileError() {
		return compileError;
	}
	public void setCompileError(String compileError) {
		this.compileError = compileError;
	}
	public static DynamicEngine getInstance() {
        return ourInstance;
    }
    private URLClassLoader parentClassLoader;
    private String classpath;
    private DynamicEngine() {
        this.parentClassLoader = (URLClassLoader) this.getClass().getClassLoader();
        this.buildClassPath();
    }
    private void buildClassPath() {
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for (URL url : this.parentClassLoader.getURLs()) {
            String p = url.getFile();
            sb.append(p).append(File.pathSeparator);
        }
        this.classpath = sb.toString();
    }
    @SuppressWarnings({ "rawtypes" })
	public Class javaCodeToClass(String fullClassName, String javaCode) throws Exception{
        long start = System.currentTimeMillis();
        Class clazz = null;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager standfileManager = compiler.getStandardFileManager(diagnostics, null, null);
		Iterable<? extends File> jarFiles = Arrays.asList(new File(libPath).listFiles());
	    standfileManager.setLocation(StandardLocation.CLASS_PATH, jarFiles);

        ClassFileManager fileManager = new ClassFileManager(standfileManager);
        
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject(fullClassName, javaCode));
 
        List<String> options = new ArrayList<String>();
        options.add("-encoding");
        options.add("UTF-8");
        options.add("-classpath");
        options.add(this.classpath);
 
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jfiles);
        boolean success = task.call();
        
        if (success) {
            JavaClassObject jco = fileManager.getJavaClassObject();
            DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(this.parentClassLoader);
            clazz = dynamicClassLoader.loadClass(fullClassName,jco);
            dynamicClassLoader.close();
        } else {
            String error = "";
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                error = error + compilePrint(diagnostic);
            }
            setCompileError(error);
            throw new Exception("代码编译失败，请检查代码语法是否有误");
        }
        long end = System.currentTimeMillis();
        PhoenixLogger.info("javaCodeToClass use:"+(end-start)+"ms");
        parentClassLoader.close();
        standfileManager.flush();
        standfileManager.close();
        fileManager.flush();
        fileManager.close();
        return clazz;
    }
 
    private String compilePrint(Diagnostic<?> diagnostic) {
    	PhoenixLogger.info("Code:" + diagnostic.getCode());
    	PhoenixLogger.info("Kind:" + diagnostic.getKind());
    	PhoenixLogger.info("Position:" + diagnostic.getPosition());
    	PhoenixLogger.info("Start Position:" + diagnostic.getStartPosition());
    	PhoenixLogger.info("End Position:" + diagnostic.getEndPosition());
    	PhoenixLogger.info("Source:" + diagnostic.getSource());
    	PhoenixLogger.info("Message:" + diagnostic.getMessage(null));
    	PhoenixLogger.info("LineNumber:" + diagnostic.getLineNumber());
    	PhoenixLogger.info("ColumnNumber:" + diagnostic.getColumnNumber());
        StringBuffer res = new StringBuffer();
        res.append("Code:[" + diagnostic.getCode() + "]\n");
        res.append("Kind:[" + diagnostic.getKind() + "]\n");
        res.append("Position:[" + diagnostic.getPosition() + "]\n");
        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
        res.append("Source:[" + diagnostic.getSource() + "]\n");
        res.append("Message:[" + diagnostic.getMessage(null) + "]\n");
        res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
        return res.toString();
    }
}