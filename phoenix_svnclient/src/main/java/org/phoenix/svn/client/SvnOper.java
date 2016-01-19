package org.phoenix.svn.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * svn客户端
 * @author mengfeiyang
 *
 */
public class SvnOper {
	private String svnUrl;
	private String name;
	private String password;
	private String definePath;
	private static SVNClientManager ourClientManager;
	
	public SvnOper(String svnUrl, String name,String password, String definePath) {
		this.svnUrl = svnUrl;
		this.name = name;
		this.password = password;
		this.definePath = definePath;
        setupLibrary();
	}

	public void disPlayRespositoryTree() {
		try {
			SVNURL repositoryURL = null;
			SVNRepository repository = null;
			repositoryURL = SVNURL.parseURIEncoded(svnUrl);
			repository = SVNRepositoryFactory.create(repositoryURL);
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authManager);

			System.out.println("Repository Root: "
					+ repository.getRepositoryRoot(true));
			System.out.println("Repository UUID: "
					+ repository.getRepositoryUUID(true));
			System.out.println("");
			listEntries(repository, "");
			long latestRevision = repository.getLatestRevision();
			System.out.println("");
			System.out.println("---------------------------------------------");
			System.out.println("版本库的最新版本是: " + latestRevision);
		} catch (SVNException svne) {
			System.err.println("获取最新版本号时出错: " + svne.getMessage());
		}
	}
	
	public String doUpdate() {
		try {
			ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
			ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
			File updateFile=new File(definePath);
			SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
			updateClient.setIgnoreExternals(false);
			long versionNum = updateClient.doUpdate(updateFile, SVNRevision.HEAD, SVNDepth.INFINITY,false,false);
			return "doUpdate Success!lastest version："+versionNum;
		} catch (SVNException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String doImport() {
		SVNCommitInfo commitInfo = null;
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(svnUrl);
			ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
			ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
			File impDir = new File(definePath);
			commitInfo = ourClientManager.getCommitClient().doImport(impDir, repositoryURL,"import operation!",null, false,false,SVNDepth.INFINITY);
		} catch (SVNException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return commitInfo.toString();
	}
	
	public String doDiff() {
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
		File compFile = new File(definePath);
		SVNDiffClient diff=ourClientManager.getDiffClient();
		BufferedOutputStream result;
		try {
			result = new BufferedOutputStream(new FileOutputStream(definePath+".diff.txt"));
			diff.doDiff(compFile, SVNRevision.HEAD, SVNRevision.WORKING, SVNRevision.HEAD, SVNDepth.INFINITY, true, result,null);
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(SVNRevision.WORKING+"+++"+SVNRevision.HEAD);
		return "diff result:"+definePath+".diff.txt";
	}
	
	public String doCommit() {
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
		File commitFile=new File(definePath);
		SVNStatus status = null;
		try {
			status = ourClientManager.getStatusClient().doStatus(commitFile, true);
			if(status.getContentsStatus()==SVNStatusType.STATUS_UNVERSIONED){
				ourClientManager.getWCClient().doAdd(commitFile, false, false, false, SVNDepth.INFINITY,false,false);
				ourClientManager.getCommitClient().doCommit(new File[] { commitFile }, true, "",null,null,true, false, SVNDepth.INFINITY);
				return "add file success!";
			}else{
				ourClientManager.getCommitClient().doCommit(new File[] { commitFile }, true, "",null,null,true, false, SVNDepth.INFINITY);
				return "commit file success!";
			}
		} catch (SVNException e) {
			e.printStackTrace();
			return "commit file error!";
		}
	}
	public HashMap<String, String> disPayFileAttributes() {
		SVNURL repositoryURL = null;
		SVNRepository repository = null;
		String info;
		HashMap<String, String> fileAttributes = new HashMap<String, String>();
		try {
			repositoryURL = SVNURL.parseURIEncoded(svnUrl);
			repository = SVNRepositoryFactory.create(repositoryURL);
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authManager);

			SVNProperties fileProperties = new SVNProperties();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			SVNNodeKind nodeKind = repository.checkPath(definePath, -1);

			if (nodeKind == SVNNodeKind.NONE) {
				info = "要查看的文件在 '" + svnUrl + "'中不存在.";
				fileAttributes.put("info", info);
				return fileAttributes;
			} else if (nodeKind == SVNNodeKind.DIR) {
				info = "要查看对应版本的条目在 '" + svnUrl + "'中是一个目录.";
				fileAttributes.put("info", info);
				return fileAttributes;
			}
			repository.getFile(definePath, -1, fileProperties, baos);

			String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
			boolean isTextType = SVNProperty.isTextMimeType(mimeType);
			fileAttributes.put("isTextType", isTextType + "");
			Iterator<?> iterator = fileProperties.nameSet().iterator();
			while (iterator.hasNext()) {
				String propertyName = (String) iterator.next();
				String propertyValue = fileProperties.getStringValue(propertyName);
				fileAttributes.put(propertyName, propertyValue);
				System.out.println("文件的属性: " + propertyName + "=" + propertyValue);
			}
			long latestRevision = -1;
			latestRevision = repository.getLatestRevision();
			System.out.println("版本库的最新版本号: " + latestRevision);
			fileAttributes.put("lastest", latestRevision + "");

		} catch (SVNException svne) {
			svne.printStackTrace();
			fileAttributes.put("info", svne.getMessage());
		}
        return fileAttributes;
	}
	public String checkOut(){
		try {
			SVNURL repositoryURL = null;
			repositoryURL = SVNURL.parseURIEncoded(svnUrl);	
			ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
			ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
			File wcDir = new File(definePath);
			SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
			updateClient.setIgnoreExternals(false);
			long workingVersion = -1;
			workingVersion = updateClient.doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,false);
			return workingVersion+" checkout to："+wcDir;
		} catch (SVNException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
    public List<SvnLogModel> displaySvnLog() {
    	try{
	        long startRevision = 0;
	        long endRevision = -1;
	        List<SvnLogModel> svnLogModels = new ArrayList<SvnLogModel>();
	        SVNRepository repository = null;
	        repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
	        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
	        repository.setAuthenticationManager(authManager);
	        endRevision = repository.getLatestRevision();
	        Collection<?> logEntries = repository.log(new String[]{""}, null,startRevision, endRevision, true, true);
	        Iterator<?> entries = logEntries.iterator(); 
	        while (entries.hasNext()) {
	            SVNLogEntry logEntry = (SVNLogEntry) entries.next();
	            SvnLogModel svnLog = new SvnLogModel();
	            svnLog.setRevision(logEntry.getRevision());
	            svnLog.setAuthor(logEntry.getAuthor());
	            svnLog.setDate(logEntry.getDate());
	            svnLog.setMessage(logEntry.getMessage());
	            
	            if (logEntry.getChangedPaths().size() > 0) {
	            	List<String> changedPathList = new ArrayList<String>();
	                Set<?> changedPathsSet = logEntry.getChangedPaths().keySet();
	                Iterator<?> changedPaths = changedPathsSet.iterator();
	                while (changedPaths.hasNext()) {
	                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
	                    changedPathList.add(entryPath.getType()+" "+entryPath.getPath()+((entryPath.getCopyPath() != null) ? " (from " + entryPath.getCopyPath() + " revision " + entryPath.getCopyRevision() + ")" : ""));
	                }
	                svnLog.setChangedPaths(changedPathList);
	            }
	            svnLogModels.add(svnLog);
	        }
	        return svnLogModels;
    	} catch (SVNException e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    private static void setupLibrary() {
    	System.setProperty("svnkit.http.sslProtocols", "SSLv3");
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }
    
    private static void listEntries(SVNRepository repository, String path)
            throws SVNException {
        Collection<?> entries = repository.getDir(path, -1, null,(Collection<?>) null);
        Iterator<?> iterator = entries.iterator();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            System.out.println("/" + (path.equals("") ? "" : path + "/")
                    + entry.getName() + " (author: '" + entry.getAuthor()
                    + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")");
            if (entry.getKind() == SVNNodeKind.DIR) {
                listEntries(repository, (path.equals("")) ? entry.getName()
                        : path + "/" + entry.getName());
            }
        }
	}
}
