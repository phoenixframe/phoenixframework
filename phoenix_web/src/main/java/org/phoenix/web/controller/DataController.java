package org.phoenix.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.phoenix.model.CaseBean;
import org.phoenix.model.DataBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.InterfaceDataBean;
import org.phoenix.utils.GetNow;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.dto.AjaxObj;
import org.phoenix.web.dto.DataDTO;
import org.phoenix.web.dto.SheetContentDTO;
import org.phoenix.web.service.ICaseService;
import org.phoenix.web.service.IDataService;
import org.phoenix.web.service.IInBatchDataService;
import org.phoenix.web.service.IInDataBeanService;
import org.phoenix.web.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;
/**
 * 测试数据控制器
 * @author mengfeiyang
 *
 */
@Controller
@RequestMapping("/data")
@AuthClass("login")
public class DataController {
	private IDataService dataService;
	private ICaseService caseService;
	private IInBatchDataService inBatchDataService;
	private IInDataBeanService inDataBeanService;
	
	public IInDataBeanService getInDataBeanService() {
		return inDataBeanService;
	}
	@Resource
	public void setInDataBeanService(IInDataBeanService inDataBeanService) {
		this.inDataBeanService = inDataBeanService;
	}
	public IInBatchDataService getInBatchDataService() {
		return inBatchDataService;
	}
	@Resource
	public void setInBatchDataService(IInBatchDataService inBatchDataService) {
		this.inBatchDataService = inBatchDataService;
	}
	public IDataService getDataService() {
		return dataService;
	}
	@Resource
	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}

	public ICaseService getCaseService() {
		return caseService;
	}
	@Resource
	public void setCaseService(ICaseService caseService) {
		this.caseService = caseService;
	}

	@RequestMapping("/list/{id}")
	public String list(@PathVariable Integer id,Model model){
		model.addAttribute(caseService.getCaseBean(id));
		model.addAttribute("datas", dataService.getDataPager(id));
		return "data/list";
	}
	
	@RequestMapping(value="/batchadd/{id}",method=RequestMethod.GET)
	public String batchadd(@PathVariable Integer id, Model model,HttpSession session){
		model.addAttribute(new DataDTO());
		model.addAttribute(caseService.getCaseBean(id));
		return "data/batchadd";
	}
	@RequestMapping(value="/batchadd/{id}",method=RequestMethod.POST)
	public String batchadd(@PathVariable Integer id,DataDTO dataDTO,Model model){
		List<DataBean> dataList = new ArrayList<DataBean>();
		List<DataBean> ll = dataDTO.getCaseDataBeanList();
		for(DataBean l : ll){
			if(l.getDataName() != null && !l.getDataName().equals("")){
				dataList.add(l);
			}
		}
		dataService.addDataBeanList(dataList);
		return "redirect:/data/list/"+id;
	}
	
	
	@RequestMapping(value="/delete/{cid}/{id}")
	public String delete(@PathVariable Integer cid,@PathVariable Integer id){
		dataService.deleData(id);
		return "redirect:/data/list/"+cid;
	}
	
	@RequestMapping(value="/update/{cid}/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer cid,@PathVariable Integer id,Model model,HttpSession session){
		model.addAttribute(new DataDTO());
		model.addAttribute(dataService.getData(id));
		model.addAttribute(caseService.getCaseBean(cid));
		return "data/edit";
	}
	@RequestMapping(value="/update/{cid}/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer cid,@PathVariable Integer id,@Valid DataDTO dataDTO,BindingResult br,Model model){
		if(br.hasErrors()){
			model.addAttribute(caseService.getCaseBean(cid));
			return "data/edit";
		}
		DataBean dataBean = dataService.getData(id);
		dataBean.setDataContent(dataDTO.getDataContent());
		dataBean.setDataName(dataDTO.getDataName());
		dataService.updateData(dataBean);
		return "redirect:/data/list/"+cid;
	}
	
	@RequestMapping(value="/export/{id}",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String exportData(@PathVariable Integer id,HttpServletRequest req,Model model){
		CaseBean caseBean = caseService.getCaseBean(id);
		String fileName = GetNow.getCurrentTime("yyyyMMddHHmmss")+"_"+caseBean.getCaseName();
		String filePath = req.getSession().getServletContext().getRealPath("/resources/upload/")+"/"+fileName+".xlsx";
		SheetContentDTO sheetDTO = new SheetContentDTO();
		ExcelUtil excelUtil = new ExcelUtil();
		List<String[]> parameters = new ArrayList<String[]>();
		List<InterfaceBatchDataBean> ibatchDataList = inBatchDataService.getInBatchList(id);
		sheetDTO.setCaseName(caseBean.getCaseName());
		sheetDTO.setCaseId(id+"");
		String[] paramName = null;
		for(InterfaceBatchDataBean batchData : ibatchDataList){
			List<InterfaceDataBean> idataBeanList = inDataBeanService.getDataBeans(batchData.getId());
			String[] paramContent = new String[idataBeanList.size()+2];
			paramName = new String[idataBeanList.size()+2];
			paramName[0] = "功能说明";
			paramName[1] = "期望值\\参数名";
			paramContent[0] = batchData.getRemark();
			paramContent[1] = batchData.getExpectData();
			for(int i = 0;i<idataBeanList.size();i++){
				paramName[i+2] = idataBeanList.get(i).getDataName();
				paramContent[i+2] = idataBeanList.get(i).getDataContent();
			}
			parameters.add(paramContent);
		}
		sheetDTO.setParamNames(paramName);
		sheetDTO.setParameters(parameters);
		excelUtil.setExportExcelPath(filePath);
		try{
			excelUtil.exportExcel(sheetDTO);
			String fileUrl = "http://"+req.getServerName()+":"+req.getServerPort()+"/phoenix_web/resources/upload/"+fileName+".xlsx";
			return JSON.toJSONString(new AjaxObj(1,"导出接口用例[ "+caseBean.getCaseName()+" ]的数据成功！<br>点击下载:<a href='"+fileUrl+"'>"+fileUrl+"</a>",fileName));
		}catch(Exception e){
			return JSON.toJSONString(new AjaxObj(0,"导出接口用例[ "+caseBean.getCaseName()+" ]的数据失败！<br>"+e.getCause()));
		}
	}
	/**
	 * 删除不用的文件
	 * @param fileName
	 * @param req
	 */
	@RequestMapping(value="/dfile/{fileName}",method=RequestMethod.POST)
	public @ResponseBody String deleteFile(@PathVariable String fileName,HttpServletRequest req){
		String filePath = req.getSession().getServletContext().getRealPath("/resources/upload/")+"/"+fileName+".xlsx";
		new File(filePath).delete();
		return JSON.toJSONString(new AjaxObj(1,"文件[ "+fileName+" ]删除成功"));
	}
	/**
	 * 根据接口用例的id，列出该接口用例下游多少批参数
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/INTERFACE_CASE/list/{id}")
	public String iList(@PathVariable Integer id,Model model){
		List<InterfaceBatchDataBean> iilist = inBatchDataService.getInBatchList(id);
		model.addAttribute("datas", iilist);
		model.addAttribute("dataCount", iilist.size());
		model.addAttribute("caseId",id);
		model.addAttribute("caseName", caseService.getCaseBean(id).getCaseName());
		return "data/ilist";
	}
	/**
	 * 根据单一批次的id，删除该批次的数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/INTERFACE_CASE/dbatch/{cid}/{id}")
	public String deleteBatchBean(@PathVariable Integer cid,@PathVariable Integer id){
		inBatchDataService.deleteInBatch(id);
		return "redirect:/data/INTERFACE_CASE/list/"+cid;
	}
	/**
	 * 加载一个批次的数据，用于更新。cid用于更新完成后能返回到指定指定用例的列表
	 * @param cid
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/INTERFACE_CASE/update/{cid}/{id}")
	public String updateBatchBean(@PathVariable Integer cid,@PathVariable Integer id,Model model){
		model.addAttribute(inBatchDataService.getInBatchBean(id));
		model.addAttribute(caseService.getCaseBean(cid));
		model.addAttribute("iBatchDataBean",new InterfaceBatchDataBean());
		return "data/ibatchDataEdit";
	}
	/**
	 * 执行数据批次的更新操作，id为批次id
	 * @param caseId
	 * @param id
	 * @param expectData
	 * @return
	 */
	@RequestMapping(value="/INTERFACE_CASE/update/{cid}/{id}",method=RequestMethod.POST)
	public String updateBatchData(@PathVariable Integer cid,@PathVariable Integer id,String expectData,String remark){
		InterfaceBatchDataBean iDataBean = inBatchDataService.getInBatchBean(id);
		iDataBean.setExpectData(expectData);
		iDataBean.setRemark(remark);
		inBatchDataService.updateInBatch(iDataBean);
		return "redirect:/data/INTERFACE_CASE/list/"+cid;
	}
	/**
	 * 加载添加数据批次的界面
	 * @param caseId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/INTERFACE_CASE/add/{id}",method=RequestMethod.GET)
	public String addBatchBean(@PathVariable Integer id,Model model){
		model.addAttribute(caseService.getCaseBean(id));
		model.addAttribute(new InterfaceBatchDataBean());
		return "data/ibatchDataAdd";
	}
	/**
	 * 执行添加操作
	 * @param caseId
	 * @param expectData
	 * @return
	 */
	@RequestMapping(value="/INTERFACE_CASE/add/{caseId}",method=RequestMethod.POST)
	public String addBatch(@PathVariable Integer caseId,String expectData,String remark){
		CaseBean caseBean = caseService.getCaseBean(caseId);
		InterfaceBatchDataBean interBatchDataBean = new InterfaceBatchDataBean();
		interBatchDataBean.setCaseBean(caseBean);
		interBatchDataBean.setRemark(remark);
		interBatchDataBean.setExpectData(expectData);
		inBatchDataService.addInBatch(interBatchDataBean);
		return "redirect:/data/INTERFACE_CASE/list/"+caseId;
	}
	
	
	/**
	 * 根据batchid加载一个数据批次
	 * @param bid
	 * @param model
	 * @return
	 */
	@RequestMapping("/INTERFACE_CASE/iblist/{bid}")
	public String iBList(@PathVariable Integer bid,Model model){
		model.addAttribute("batchBean", inBatchDataService.getInBatchBean(bid));
		return "data/iblist";
	}
	
	/**
	 * 更新一个数据批次
	 * @param bid
	 * @param id
	 * @return
	 */
	@RequestMapping("/INTERFACE_CASE/iblist/update/{bid}/{id}")
	public String updateDataBeanGet(@PathVariable Integer bid,@PathVariable Integer id,Model model){
		model.addAttribute(inDataBeanService.getDataBean(id));
		model.addAttribute("idatabean",new InterfaceDataBean());
		return "data/idataBeanEdit";
	}
	/**
	 * 执行更新操作，更新完成后跳转到指定批次的数据列表
	 * @param bid
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/INTERFACE_CASE/iblist/update/{bid}/{id}",method=RequestMethod.POST)
	public String updateDataBeanPost(String dataName,String dataContent,@PathVariable Integer bid, @PathVariable Integer id){
		InterfaceDataBean idataBean= inDataBeanService.getDataBean(id);
		idataBean.setDataName(dataName);
		idataBean.setDataContent(dataContent);
		inDataBeanService.updateDataBean(idataBean);
		return "redirect:/data/INTERFACE_CASE/iblist/"+bid;
	}
	/**
	 * 删除数据批次下的单条记录，删除后返回指定批次列表
	 * @param bid
	 * @param id
	 * @return
	 */
	@RequestMapping("/INTERFACE_CASE/iblist/delete/{bid}/{id}")
	public String deleteDataBean(@PathVariable Integer bid,@PathVariable Integer id){
		inDataBeanService.deleteDataBean(id);
		return "redirect:/data/INTERFACE_CASE/iblist/"+bid;
	}
	/**
	 * 加载添加单条数据的界面
	 * @param bid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/INTERFACE_CASE/iblist/add/{bid}",method=RequestMethod.GET)
	public String addDataBeanGet(@PathVariable Integer bid,Model model){
		model.addAttribute("bid", bid);
		model.addAttribute(new InterfaceDataBean());
		return "data/idataBeanAdd";
	}
	/**
	 * 执行添加操作
	 * @param dataName
	 * @param dataContent
	 * @param bid
	 * @return
	 */
	@RequestMapping(value="/INTERFACE_CASE/iblist/add/{bid}",method=RequestMethod.POST)
	public String addDataBean(String dataName,String dataContent,@PathVariable Integer bid){
		InterfaceBatchDataBean interBatchDataBean = inBatchDataService.getInBatchBean(bid);
		InterfaceDataBean interDataBean = new InterfaceDataBean();
		interDataBean.setDataContent(dataContent);
		interDataBean.setDataName(dataName);
		interDataBean.setInterfaceBatchDataBean(interBatchDataBean);
		inDataBeanService.addDataBean(interDataBean);
		return "redirect:/data/INTERFACE_CASE/iblist/"+bid;
	}
	
	@RequestMapping(value="/import/{cid}",method=RequestMethod.GET)
	public String importData(@PathVariable Integer cid,Model model){
		model.addAttribute(caseService.getCaseBean(cid));
		model.addAttribute(new SheetContentDTO());
		return "data/importData";
	}
	/**
	 * 执行批量数据导入的操作，如果勾选了isRewrite，则会先清除表中原有的数据，然后再重新插入，<br>
	 * 若没有勾选，则直接执行插入操作，与原有的数据共同保存在数据库中。
	 * @param sheetContentDTO
	 * @param br
	 * @param attach
	 * @param req
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/import/{cid}",method=RequestMethod.POST)
	public String importDatas(@Valid SheetContentDTO sheetContentDTO, BindingResult br ,@RequestParam("attachs")MultipartFile attach,HttpServletRequest req,Model model){
		model.addAttribute("casename", sheetContentDTO.getCaseName());
		model.addAttribute("caseid", sheetContentDTO.getCaseId());
		if(br.hasErrors())return "data/importData";
		ExcelUtil excelUtil = new ExcelUtil();
		String realpath = req.getSession().getServletContext().getRealPath("/resources/upload");
		String errorInfo= null;
		if(attach.isEmpty()) errorInfo = "文件不能为空";
		if(!attach.getOriginalFilename().endsWith(".xlsx") && !attach.getOriginalFilename().endsWith(".xls"))errorInfo = "请选择[xlsx、xls]格式的数据文件";
		if(errorInfo != null){model.addAttribute("errorInfo", errorInfo);return "data/importData";}
		String filePath = realpath+"/"+req.getSession().getId()+"."+Files.getFileExtension(attach.getOriginalFilename());;
		File f = new File(filePath);
		SheetContentDTO sheetContent = null;
		try{
			FileUtils.copyInputStreamToFile(attach.getInputStream(),f);
			excelUtil.setImportExcelPath(filePath);
			sheetContent = excelUtil.getExcelContent(sheetContentDTO.getSheetName());
		} catch (Exception e){
			errorInfo = "发生异常，请检查数据表的数据格式和指定的Sheet页名称是否正确,"+e.getClass().getSimpleName()+","+e.getMessage();
			model.addAttribute("errorInfo", errorInfo);
			f.delete();
			return "data/importData";
		}
		CaseBean caseBean = null;
		if(sheetContent.getCaseId() != null)caseBean = caseService.getCaseBean(Integer.parseInt(sheetContent.getCaseId()));
		else if(sheetContent.getCaseName() != null)caseBean = caseService.getCaseBeanByName(sheetContent.getCaseName());
		if(!sheetContentDTO.getCaseId().equals(caseBean.getId()+"")){
			errorInfo = "当前用例是：[ "+sheetContentDTO.getCaseName()+" ] ，数据表中的数据不属于当前用例";
			model.addAttribute("errorInfo", errorInfo);			f.delete();
			return "data/importData";
		}
		if(sheetContentDTO.getIsRewrite()){
			inBatchDataService.deleteInBatchByCaseId(caseBean.getId());
			for(String[] params : sheetContent.getParameters()){
				InterfaceBatchDataBean tempBatchBean = inBatchDataService.addInBatch(new InterfaceBatchDataBean(caseBean,params[1],params[0]));
				for(int i=2;i<sheetContent.getParamNames().length;i++){
					inDataBeanService.addDataBean(new InterfaceDataBean(tempBatchBean,sheetContent.getParamNames()[i],params[i]));
				}
			}
		} else {
			for(String[] params : sheetContent.getParameters()){
				InterfaceBatchDataBean tempBatchBean = inBatchDataService.addInBatch(new InterfaceBatchDataBean(caseBean,params[1],params[0]));
				for(int i=2;i<sheetContent.getParamNames().length;i++){
					inDataBeanService.addDataBean(new InterfaceDataBean(tempBatchBean,sheetContent.getParamNames()[i],params[i]));
				}
			}
		}
		f.delete();
		return "redirect:/data/INTERFACE_CASE/list/"+caseBean.getId();
	}
}
