package cn.me.xdf.view.model;


public class VCourseAuth {
   
   /**
    * 教师
    */
   private String teacherName;
   
   private String teacherDept;
   
   private String teacherEmail;
   
   /**
    * 导师
    */
   private String adviserName;
   
   private String adviserDept;
   
   private String adviserEmail;
   
   /**
	 * 创建时间
	 */
   
   private String fdCreateTime;

   

   public String getTeacherEmail() {
	return teacherEmail;
   }

   public void setTeacherEmail(String teacherEmail) {
	   this.teacherEmail = teacherEmail;
   }

   public String getAdviserEmail() {
	   return adviserEmail;
   }

   public void setAdviserEmail(String adviserEmail) {
	   this.adviserEmail = adviserEmail;
   }

   public String getTeacherName() {
	   return teacherName;
   }

   public void setTeacherName(String teacherName) {
	   this.teacherName = teacherName;
   }

   public String getTeacherDept() {
	   return teacherDept;
   }

   public void setTeacherDept(String teacherDept) {
	   this.teacherDept = teacherDept;
   }

   public String getAdviserName() {
	   return adviserName;
   }

   public void setAdviserName(String adviserName) {
	   this.adviserName = adviserName;
   }

   public String getAdviserDept() {
	   return adviserDept;
   }

   public void setAdviserDept(String adviserDept) {
	   this.adviserDept = adviserDept;
   }

   public String getFdCreateTime() {
	   return fdCreateTime;
   }

   public void setFdCreateTime(String fdCreateTime) {
	   this.fdCreateTime = fdCreateTime;
   }

}
