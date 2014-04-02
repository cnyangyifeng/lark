package com.kuxue.service.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.utils.array.ArrayUtils;
import com.kuxue.common.utils.array.SortType;
import com.kuxue.model.material.ExamQuestion;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.material.Task;
import com.kuxue.service.BaseService;

/**
 * 作业的service
 * @author yuhz
 */
@Service
@Transactional(readOnly = true)
public class TaskService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<Task> getEntityClass() {
		return Task.class;
	}
	public List<Map> getTaskByMaterId(MaterialInfo info){
		 if(info.getTasks()==null||info.getTasks().size()==0){
			 return null;
		 }
		 List<Task> Tasks = info.getTasks();
		 ArrayUtils.sortListByProperty(Tasks, "fdOrder", SortType.HIGHT);
		 List<Map> list = new ArrayList<Map>();
		 for (Task task : Tasks) {
			 Map map = new HashMap();
			 map.put("id", task.getFdId());
			 map.put("subject", task.getFdSubject());
			 map.put("score", task.getFdStandardScore());
			 map.put("index", task.getFdOrder());
			 list.add(map);
		}
		 return list;
	}
	
}
