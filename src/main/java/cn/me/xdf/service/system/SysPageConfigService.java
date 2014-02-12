package cn.me.xdf.service.system;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.organization.SysOrgElement;
import cn.me.xdf.model.system.PageConfig;
import cn.me.xdf.model.system.SysAppConfig;
import cn.me.xdf.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringUtil;


@Service
public class SysPageConfigService extends BaseService {


    @Override
    public Class<PageConfig> getEntityClass() {
        return PageConfig.class;
    }
    
    public int getMaxOrder(String fdType){
    	 Finder finder=Finder.create("from PageConfig page where page.fdType=:fdType ");
    	 finder.append(" order by page.fdOrder  desc");
    	 finder.setParam("fdType", fdType);
    	 List orders=find(finder);   	
    	 if(orders!=null&&orders.size()>0){
    		 return ((PageConfig)orders.get(0)).getFdOrder();
    	}
    	 return 0;
    }
    public List<PageConfig> getPageConfigs(String ptype,String fdkey){
    	Finder finder=Finder.create(" from PageConfig page ");
    	if(StringUtil.isNotEmpty(fdkey)){
    		finder.append(" , SysOrgElement ele ");
    		
    	}
    	finder.append(" where page.fdType=:ptype ");
    	finder.setParam("ptype", ptype);
    	if(StringUtil.isNotEmpty(fdkey)){
    		finder.append(" and  page.fdElementId=ele.fdId ");
    		finder.append(" and ele.fdName like :fdkey");
    		finder.setParam("fdkey", "%"+fdkey+"%");
    	}
    	finder.append(" order by page.fdOrder ");
    	List list=find(finder);
    	if(!StringUtil.isNotEmpty(fdkey)&&list!=null&&list.size()>0){
    		return list;
    		
    	}else{
    		if(StringUtil.isNotEmpty(fdkey)&&list!=null&&list.size()>0){
	    		List<PageConfig> pages=new ArrayList<PageConfig>();
	    		for(int i=0;i<list.size();i++){
	    			Object[] obj=(Object[]) list.get(i);
	    			PageConfig page=(PageConfig) obj[0];
	    			pages.add(page);
	    		}
	    		return pages;
    	   }
    	}
    	return null;
    }
    
    public boolean checkUnique(String elementId){
    	Finder finder=Finder.create(" from PageConfig page ");
    	finder.append(" where page.fdElementId=:elementId");
    	finder.setParam("elementId", elementId);
    	List list=find(finder);
    	if(list!=null&&list.size()>0){
    		return false;
    	}
    	return true;
    }
    public boolean checkIdAndName(String elementId,String fdname){
    	Finder finder=Finder.create(" from SysOrgElement org ");
    	finder.append(" where  org.fdId=:element  and org.fdName=:fdname");
    	finder.setParam("element", elementId);
    	finder.setParam("fdname", fdname);
    	List list=find(finder);
    	if(list!=null&&list.size()>0){
    		return true;
    	}
    	return false;
    }
}
