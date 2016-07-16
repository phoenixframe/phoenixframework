package org.phoenix.node.compiler;
import javax.tools.*;

import java.io.IOException;
 
/**
 * 文件管理器
 * @author mengfeiyang
 *
 */
@SuppressWarnings("rawtypes")
public class ClassFileManager extends ForwardingJavaFileManager {
    private JavaClassObject jclassObject;
    
	public JavaClassObject getJavaClassObject() {
        return jclassObject;
    }
    @SuppressWarnings("unchecked")
	public ClassFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }
    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
        String className, JavaFileObject.Kind kind, FileObject sibling)
            throws IOException {
            jclassObject = new JavaClassObject(className, kind);
        return jclassObject;
    }
}