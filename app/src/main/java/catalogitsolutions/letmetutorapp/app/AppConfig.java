
/*Developed By: Catalog IT Solutions. Copyright © 2017. Catalog. All Rights Reserved.This product is protected by copyright
 laws. Unauthorized reproduction or distribution of this product,or any portion of it,may result in severe civil and
 criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 File Name: AppConfig.java
 Created By: Sireesha A
 Created On: 02/Jan/2017
 Last Modified By: Sireesha A
 Last Modified On: 10/Feb/2017
 Purpose: To get webservices
  */

package catalogitsolutions.letmetutorapp.app;

public class AppConfig {

    //Base url
    public static String BASE_URL = "http://192.168.2.30/sample_android_api/";

    //public static String PROFILEIMG_URL = "http://192.168.2.105/sample_android_api/";

    public static final String STUDENT_LOGIN_URL = BASE_URL + "student_login.php";
    public static final String TEACHER_LOGIN_URL = BASE_URL + "teacher_login.php";
    public static final String TEACHER_REGISTER_URL = BASE_URL + "teacher_register.php";
    public static final String STUDENT_REGISTER_URL = BASE_URL + "student_register.php";
    public static final String SELECT_CITY_URL = BASE_URL + "select_city.php";
    public static final String SELECT_ZONE_URL = BASE_URL + "select_zone.php?cityid=";
    public static final String SELECT_SUBAREA_URL = BASE_URL + "select_subarea.php?zoneid=";
    public static final String SELECT_CLASS_URL = BASE_URL + "select_class.php";
    public static final String SELECT_TEACHLOC_URL = BASE_URL + "select_teachinglocation.php";
    public static final String SELECT_SUBJECT_URL = BASE_URL + "select_subject.php";
    public static final String SELECT_TIME_URL = BASE_URL + "select_time.php";
    public static final String SELECT_QUALI_URL = BASE_URL + "select_qualification.php";
    public static final String UPDATE_TEACHER_URL = BASE_URL + "update_teacher.php";
    public static final String UPDATE_STUDENT_URL = BASE_URL + "update_students.php";
    public static final String GETTEACHERUSERNAME_URL = BASE_URL + "getTeacherUsername.php";
    public static final String GETSTUDENTUSERNAME_URL = BASE_URL + "getStudentUsername.php";
    public static final String CHECKTEACHERDETAIL_URL = BASE_URL + "checkteacherdetails.php";
    public static final String CHECKSTUDENTSDETAIL_URL = BASE_URL + "checkstudentdetails.php";
    public static final String SEARCHTEACHER_URL = BASE_URL + "searchteacher_list.php";

    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String SHARED_TEACHER_PREF_NAME = "teacherloginapp";
    public static final String TEACHERUSER_SHARED_PREF = "admin_teacher";

    public static final String SHARED_STUDENTS_PREF_NAME = "stuloginapp";
    public static final String STUDENTSUSER_SHARED_PREF = "admin_stu";
}
