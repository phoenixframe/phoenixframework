package org.phoenix.api.utils;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
/**
 * java通过加载认证证书，抓取https的url源码方法
 * @author mengfeiyang
 *
 */
public class MyX509TrustManager implements X509TrustManager {
    private X509TrustManager sunJSSEX509TrustManager;
    
    private void addKeyStore(KeyStore ks) throws Exception{
	        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
	        tmf.init(ks);
	        TrustManager tms [] = tmf.getTrustManagers();
	        for (int i = 0; i < tms.length; i++) {
	            if (tms[i] instanceof X509TrustManager) {
	                sunJSSEX509TrustManager = (X509TrustManager) tms[i];
	                return;
	            }
	        }
    }
    
    public MyX509TrustManager(URI uri,String pass) {
    	try{
	        KeyStore ks = KeyStore.getInstance("JKS");
	        ks.load(new FileInputStream(new File(uri)),pass.toCharArray());
	        addKeyStore(ks);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public MyX509TrustManager(String keystoreFile,String pass) {
    	try{
	        KeyStore ks = KeyStore.getInstance("JKS");
	        ks.load(new FileInputStream(keystoreFile),pass.toCharArray());
	        addKeyStore(ks);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public MyX509TrustManager(File file,String pass) {
    	try{
	        KeyStore ks = KeyStore.getInstance("JKS");
	        ks.load(new FileInputStream(file),pass.toCharArray());
	        addKeyStore(ks);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
     
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException excep) {
         excep.printStackTrace();
        }
    }
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        try {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException excep) {
         excep.printStackTrace();
        }
    }
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return sunJSSEX509TrustManager.getAcceptedIssuers();
    }
}