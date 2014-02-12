package cn.me.xdf.service.bam;

import cn.me.xdf.BaseTest;
import cn.me.xdf.model.course.CourseInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-4
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class BamCourseServiceTest extends BaseTest {

    @Autowired
    private BamCourseService bamCourseService;

    @Test
    public void testSaveBamCourse() {
        //测试
//        CourseInfo courseInfo = bamCourseService.get(CourseInfo.class, "1420de343fd77bc99047dcd4c71a6196");
//        bamCourseService.saveBamCourse(courseInfo, null);
    }
    public static void main(String[] args) {
		String s ="4,,";
		System.out.println(s.split(",").length);
	}
}
