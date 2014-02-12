package cn.me.xdf.common.json;

import cn.me.xdf.JunitBaseTest;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseContent;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.material.MaterialInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-31
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class ScoreJsonTest extends JunitBaseTest {


    /**
     * 课程与素材------test
     */
    public void testCourseContent() {

        //课程素材关系实体的定义
        CourseContent courseContent = new CourseContent();

        //课程
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setFdId("courseInfo_id");
        courseInfo.setFdTitle("courseInfo_title");
        //章节
        CourseCatalog catalog1 = new CourseCatalog();
        catalog1.setFdId("catelog1_id");
        catalog1.setFdName("第一节");
        catalog1.setCourseInfo(courseInfo);
        catalog1.setFdDescription("说明");
        catalog1.setHbmParent(null);
        //素材
        MaterialInfo materialInfo = new MaterialInfo();
        materialInfo.setFdId("materialInfo_id1");
        materialInfo.setFdName("素材名称1");
        materialInfo.setFdCreateTime(new Date());
        materialInfo.setFdType("fd_type");

        //附件
        List<AttMain> attMains = new ArrayList<AttMain>();
        AttMain attMain1 = new AttMain();
        attMain1.setFdId("att_main_id");
        attMain1.setFdFileName("att_main_name");
        attMain1.setFdModelName("model_name1");
        attMain1.setFdModelId("model_id1");
        attMain1.setFdFileType("file_type");
        attMains.add(attMain1);
        materialInfo.setAttMains(attMains);

        courseContent.setFdId("course_content_id1");
        courseContent.setCatalog(catalog1);
        courseContent.setMaterial(materialInfo);

        courseContent.setCatalog(catalog1);
        String jsonStr = JsonUtils.writeObjectToJson(courseContent);
        System.out.println(jsonStr);


    }


    /**
     * 章节-------test
     */
    public void testCatalogJson() {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setFdId("courseInfo_id");
        courseInfo.setFdTitle("courseInfo_title");

        List<CourseCatalog> items = new ArrayList<CourseCatalog>();

        CourseCatalog catalog1 = new CourseCatalog();
        catalog1.setFdId("catelog1_id");
        catalog1.setFdName("第一节");
        catalog1.setCourseInfo(courseInfo);
        catalog1.setFdDescription("说明");
        catalog1.setHbmParent(null);
        items.add(catalog1);


        CourseCatalog catalog2 = new CourseCatalog();
        catalog2.setFdId("catelog2_id");
        catalog2.setFdName("第二节");
        catalog2.setCourseInfo(courseInfo);
        catalog2.setFdDescription("说明");
        catalog2.setHbmParent(catalog1);
        items.add(catalog2);

        JsonUtils.writeObjectToJson(items);
    }

}
