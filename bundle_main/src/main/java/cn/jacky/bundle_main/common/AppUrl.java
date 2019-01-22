package cn.jacky.bundle_main.common;

/**
 * Created by Hzj on 2017/8/17.
 * 网络请求url
 */

public class AppUrl {
    //Tag
    public final static String Common_Tag = "CookMan";

    public final static String Key_MobAPI_Cook = "1b5cf0085e135";

    public final static int Per_Page_Size = 20;

    public final static String baseURL = "http://apicloud.mob.com/v1/cook/";
    public final static String Cook_Service_CategoryQuery = "category/query";//查询菜谱的所有分类
    public final static String Cook_Service_MenuSearch = "menu/search";//根据标签ID/菜谱名称查询菜谱详情
    public final static String Cook_Service_MenuQuery = "menu/query";//根据菜谱ID查询菜谱详情

    public final static String Cook_Parameter_Key = "key";//MobAPI 开发者Key
    public final static String Cook_Parameter_Cid = "cid";//
    public final static String Cook_Parameter_Name = "name";//
    public final static String Cook_Parameter_Page = "page";//
    public final static String Cook_Parameter_Size = "size";//

}
