package com.koalitech.bsmart.domain.remote;

import android.util.Log;

import com.koalitech.bsmart.domain.enity.Contact;
import com.koalitech.bsmart.domain.enity.DynamicInfo;
import com.koalitech.bsmart.domain.enity.Education;
import com.koalitech.bsmart.domain.enity.Experience;
import com.koalitech.bsmart.domain.enity.Interest;
import com.koalitech.bsmart.domain.enity.Jobresume;
import com.koalitech.bsmart.domain.enity.JobsSkill;
import com.koalitech.bsmart.domain.enity.PersonalChar;
import com.koalitech.bsmart.domain.enity.Precommend;
import com.koalitech.bsmart.domain.enity.User;
import com.koalitech.service.http.IFileTransport;
import com.koalitech.service.http.IFtListener;
import com.koalitech.service.http.Request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zoson on 2016/10/6.
 */
public class RemoteServer {

    private Map<String,String> cache;
    private HttpManager httpRequest;
    private String server_ip = "";
    public RemoteServer(){
        httpRequest = HttpManager.getDefault();
    }

    private static String register = "register";
    private static String submit_code  = "submit_code";
    private static String check = "check";
    private static String verify_phone = "verify_phone";
    private static String login = "login";
    private static String getBaseInfo = "getBaseInfo";
    private static String getProvinceList = "getProvinceList";
    private static String getCityList = "getCityList";
    private static String getSchoolList = "getSchoolList";
    private static String getMajorClassfied = "getMajorClassfied";
    private static String getMajorList = "getMajorList";
    private static String getWorkExp = "getWorkExp";
    private static String getPositDescription = "getPositDescription";
    private static String getHobby = "getHobby";
    private static String getGraduate = "getGraduate";
    private static String getGStory = "getGStory";
    private static String getP_Graduate = "getP_Graduate";
    private static String getP_GStory="getP_GStory";
    private static String getConnectInfo="getConnectInfo";
    private static String getPrecommend = "getPrecommend";
    private static String getprovide = "getprovide";
    private static String getoldprovide = "getoldprovide";

    private static String add_BaseInfo = "add_BaseInfo";
    private static String add_WorkExp="add_WorkExp";
    private static String add_Graduate="add_Graduate";
    private static String add_P_Graduate="add_P_Graduate";
    private static String add_HSkill="add_HSkill";
    private static String add_ConnectInfo="add_ConnectInfo";
    private static String add_SSkil="add_SSkil";
    private static String add_Precommend = "add_Precommend";
    private static String add_Hobby = "add_Hobby";
    private static String add_PersonalChar = "add_PersonalChar";
    private static String getPersonalChar = "getPersonalChar";
    private static String uploadImg = "uploadImg";
    private static String getDynamic = "getDynamic";
    private static String getOldDynamic = "getoldDynamic";
    private static String add_Dynamic = "add_Dynamic";

    private static String add_Service = "add_Service";
    private static String add_Enroll = "add_Enroll";
    private static String add_Interview = "add_Interview";

    private static String getService = "getService";
    private static String getEnroll = "getEnroll";
    private static String getInterview = "getInterview";

    private static String del_WorkExp = "del_WorkExp";
    private static String del_Posit = "del_Posit";
    private static String del_Graduate = "del_Graduate";
    private static String del_GStory = "del_GStory";
    private static String del_P_Graduate = "del_P_Graduate";
    private static String del_P_GStory = "del_P_GStory";
    private static String del_ComputerSkill = "del_ComputerSkill";
    private static String del_Certificate = "del_Certificate";
    private static String del_SJobSkill = "del_SJobSkill";
    private static String del_English = "del_English";
    private static String del_Interview = "del_Interview";
    private static String del_Enroll = "del_Enroll";

    private static String get_order_con = "get_order_con";
    private static String add_order = "add_order";
    private static String getsoldservice = "getsoldservice";
    private static String getbuyservice = "getbuyservice";

    private static String getEnglish = "getEnglish";
    private static String getComputer = "getComputer";
    private static String getCertificate = "getCertificate";
    private static String getSJobSkill = "getSJobSkill";
    private static String add_JobSkill = "add_JobSkill";

    private static String get_talented = "get_talented";
    private static String get_userId = "get_userId";


    private static String get_InIndustry = "get_InIndustry";
    private static String get_Function = "get_Function";

    private static String getoldDynamic = "getoldDynamic";
    private static String findDynamic = "find_Dynamic";

    private static String resetPassword = "resetPassword";


    private static String add_applyPrecommend = "add_applyPrecommend";

    private static String add_noprivacy = "add_noprivacy";
    private static String add_privacy = "add_privacy";

    private static String getsoldserivce = "getsoldservice";
    private static String getprofessional = "getprofessional";

    private static String get_useAccount = "get_useAccount";

    private static String get_com_server = "get_com_server";
    private static String update_com_server = "update_com_server";
    private static String add_com_server ="add_com_server";
    private static String get_com_server_Detail = "get_com_server_Detail";
    private static String del_com_server = "del_com_server";
    private static String get_comName = "get_comName";
    private static String add_com_Order = "add_com_Order";
    private static String get_com_order_Record = "get_com_order_Record";
    private static String get_com_order_detail = "get_com_order_detail";
    private static String get_order_crowd ="get_order_crowd";

    private static String get_comment = "get_comment";
    private static String add_comment = "add_comment";

    public void login(String account,String password,boolean isphone,HttpListener httpListener){
        Map<String,String> params = new HashMap<>();
        if (isphone){
            params.put("phone", account);
        }else{
            params.put("mail", account);
        }
        params.put("password",password);
        Request request = new Request.Builder().setParams(params).setUrl(server_ip).setApi(login).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    //添加评论
    public void add_comment(long rid, long Dy_id, String Dy_content, HttpListener httpListener) {
        Map<String,String> params = new HashMap<>();
        params.put("rid", String.valueOf(rid));
        params.put("Dy_id", String.valueOf(Dy_id));
        params.put("Dy_content", Dy_content);
        Request request = new Request.Builder().setParams(params).setUrl(server_ip).setApi(add_comment).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    //获取评论
    public void get_comment(long rid, long Dy_id, HttpListener httpListener) {
        Map<String,String> params = new HashMap<>();
        params.put("rid", String.valueOf(rid));
        params.put("Dy_id", String.valueOf(Dy_id));
        Request request = new Request.Builder().setParams(params).setUrl(server_ip).setApi(get_comment).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void add_com_Order(long uid, int sid,String comName,String method,String payment,int price,HttpListener httpListener){

        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(uid));
        params.put("com_server_id",String.valueOf(sid));
        params.put("s_name",comName);
        params.put("s_model",method);
        params.put("payment",payment);
        params.put("price", String.valueOf(price));
        Request request = new Request.Builder().setParams(params).setUrl(server_ip).setApi(get_comment).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void get_com_order_Record(long rid,HttpListener httpListener){
        Request request = new Request.Builder().addFileParams("rid", String.valueOf(rid)).setUrl(server_ip).setApi(get_com_order_Record).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void get_com_server_Detail(long rid,int s_id,HttpListener httpListener){
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("s_id", String.valueOf(s_id));
        Request request = new Request.Builder().setParams(params).setUrl(server_ip).setObject(httpListener).setApi(get_com_server_Detail).build();
        httpRequest.sendPost(request);

    }

    public void get_com_server(long rid,HttpListener httpListener){
        StringBuilder sb = new StringBuilder();
        sb.append("rid").append("=").append(rid);
        Request request = new Request.Builder().addParams("rid",String.valueOf(rid)).setApi(get_com_server).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendGet(request);
    }

    public void register(String phone,String password,boolean isphone,HttpListener httpListener){
        Map<String,String>params = new HashMap<>();
        params.put("password",password);
        if(isphone){
            params.put("phone",phone);
        }else{
            params.put("mail",phone);
        }
        Request request = new Request.Builder().setParams(params).setApi(register).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void submit_code(String account,String vcode ,boolean isphone,HttpListener httpListener){

        Map<String,String>params = new HashMap<>();
        params.put("vcode",vcode);
        if(isphone){
            params.put("phone", account);
        }else{
            params.put("mail",account);
        }
        Request request = new Request.Builder().setParams(params).setApi(submit_code).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void check(String account,boolean isphone,HttpListener httpListener){
        Map<String,String>params = new HashMap<>();
        if(isphone){
            params.put("phone", account);
        }else{
            params.put("mail",account);
        }
        Request request = new Request.Builder().setParams(params).setApi(check).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void verify_phone(String phone ,boolean isphone,HttpListener httpListener){
        Map<String,String>params = new HashMap<>();
        if(isphone){
            params.put("phone", phone);
        }else{
            params.put("mail",phone);
        }
        Request request = new Request.Builder().setParams(params).setApi(verify_phone).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void getBaseInfo(long rid,HttpListener httpListener) {
        Request request = new Request.Builder().addParams("rid",String.valueOf(rid)).setApi(getBaseInfo).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getProvinceList(HttpListener httpListener) {
        Request request = new Request.Builder().setApi(getProvinceList).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getCityList(int pid,HttpListener httpListener) {
        Request request = new Request.Builder().setApi(getCityList).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getSchoolList(int pid,HttpListener listener) {
        Request request = new Request.Builder().addParams("pid",String.valueOf(pid)).setApi(getSchoolList).setObject(listener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getMajorClassfied(HttpListener httpListener) {
        Request request = new Request.Builder().setApi(getMajorClassfied).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getMajorList(int cid,HttpListener httpListener) {
        Request request = new Request.Builder().addParams("cid",String.valueOf(cid)).setApi(getMajorList).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getWorkExp(long rid,HttpListener listener) {
        Request request = new Request.Builder().addParams("rid",String.valueOf(rid)).setApi(getWorkExp).setObject(listener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getPositDescription(int wid,HttpListener httpListener) {
        Request request = new Request.Builder().addParams("wid",String.valueOf(wid)).setApi(getPositDescription).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getHobby(long rid,HttpListener httpListener) {
        Request request = new Request.Builder().addParams("rid",String.valueOf(rid)).setApi(getHobby).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getGraduate(long rid,HttpListener httpListener) {
        Request request = new Request.Builder().addParams("rid",String.valueOf(rid)).setApi(getGraduate).setObject(httpListener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getGStory(int gid,HttpListener listener) {
        Request request = new Request.Builder().addParams("gid",String.valueOf(gid)).setApi(getGStory).setObject(listener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getP_Graduate(long rid,HttpListener listener) {
        Request request = new Request.Builder().addParams("rid",String.valueOf(rid)).setApi(getP_Graduate).setObject(listener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void getP_GStory(int pgid,HttpListener listener) {
        Request request = new Request.Builder().addParams("pgid",String.valueOf(pgid)).setApi(getP_GStory).setObject(listener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }


    public void getConnectInfo(long rid,HttpListener listener){
        Request request = new Request.Builder().addParams("rid",String.valueOf(rid)).setApi(getConnectInfo).setObject(listener).setUrl(server_ip).build();
        httpRequest.sendGet(request);
    }

    public void add_WorkExp(Jobresume jobresume,HttpListener httpListener){
        long rid = jobresume.getUid();
        String ComName = jobresume.getCompany();
        String Position = jobresume.getPostion();
        String W_StartDate = jobresume.getDuration().toString_beginDate();
        String W_EndDate = jobresume.getDuration().toString_endDate();
        String city = jobresume.getCity();
        String province = jobresume.getProvince();
        List<String> intros = jobresume.getIntroduction();
        String arr = "";
        int num_intros = intros.size();
        for(int i=0;i<num_intros;i++){
            arr+="\""+intros.get(i)+"\"";
            if (i<num_intros-1)arr+=",";
        }
        String intro = "{\"name\":\"dess\",\"data\":["+arr+"]}";
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("ComName",ComName);
        params.put("Position",Position);
        params.put("StartDate",W_StartDate);
        params.put("EndDate",W_EndDate);
        params.put("ComCity",city);
        params.put("num",String.valueOf(num_intros));
        params.put("dess",intro);
        params.put("Province",province);

        Request request = new Request.Builder().setObject(httpListener).setParams(params).setUrl(server_ip).setApi(add_WorkExp).build();
        httpRequest.sendPost(request);
    }

    public void add_BaseInfo(User user,HttpListener httpListener){
        long rid = user.getUid();
        String name = user.getName();
        String birthday = user.getBirthdayFormat();
        String sex = user.getGender();
        String province = user.getAddress().getProvince();
        String city = user.getAddress().getCity();
        String emotional = user.getRelationship();
        String selfintro = user.getIntroduction();

        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("birthday",birthday);
        params.put("sex",sex);
        params.put("province",province);
        params.put("city",city);
        params.put("emotional",emotional);
        params.put("selfInfo", selfintro);
        params.put("name", name);

        Request request = new Request.Builder().setParams(params).setApi(add_BaseInfo).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void add_Graduate(Education education,HttpListener httpListener){
        //rid，school，major，city，StartDate，EndDate
        long rid = education.getUid();
        String school = education.getSchool();
        String major = education.getMajor();
        String city = education.getAddress().getCity();
        String province = education.getAddress().getProvince();
        String StartDate = education.getDuration().toString_beginDate();
        String EndDate = education.getDuration().toString_endDate();
        List<String> intros = education.getStories();

        String arr = "";
        int num_intros = intros.size();
        for(int i=0;i<num_intros;i++){
            arr+="\""+intros.get(i)+"\"";
            if (i<num_intros-1)arr+=",";
        }
        String story = "{\"name\":\"storys\",\"data\":["+arr+"]}";

        Map<String,String> params = new HashMap<>();
        params.put("school",school);
        params.put("major",major);
        params.put("city",city);
        params.put("StartDate",StartDate);
        params.put("EndDate",EndDate);
        params.put("province",province);
        params.put("storys",story);
        params.put("num",String.valueOf(num_intros));

        if (education.getDegree().equals(Education.POSTGRADUATE)){
            Request request = new Request.Builder().setUrl(server_ip).setApi(add_P_Graduate).setParams(params).setObject(httpListener).build();
            httpRequest.sendPost(request);
        }else if (education.getDegree().equals(Education.UNDERGRADUATE)){
            Request request = new Request.Builder().setUrl(server_ip).setApi(add_Graduate).setParams(params).setObject(httpListener).build();
            httpRequest.sendPost(request);
        }

    }
    //
//    public static void add_P_Graduate(ParamsParcel parcel,HttpListener httpListener){
//        //rid，school，major，city，StartDate，EndDate
//        HashMap<String,Object> hashMap;
//        if (parcel==null){
//            hashMap = new HashMap<>();
//        }else{
//            hashMap = parcel.getParcel();
//        }
//        int rid = (int)hashMap.get("rid");
//        String school = (String)hashMap.get("school");
//        String major = (String)hashMap.get("major");
//        String city = (String)hashMap.get("city");
//        String StartDate = (String)hashMap.get("StartDate");
//        String EndDate = (String)hashMap.get("EndDate");
//        String params = "rid="+rid+"&school="+school+"&major="+major+"&city="+city+"&StartDate="+StartDate+"&EndDate="+EndDate;
//        httpRequest.sendPost(add_P_Graduate,params,httpListener);
//    }
//
//    public static void add_HSkill(ParamsParcel parcel,HttpListener httpListener){
//        //rid，English，Computer，Certificate1，Certificate2 ，Certificate3，Certificate4
//        HashMap<String,Object> hashMap;
//        if (parcel==null){
//            hashMap = new HashMap<>();
//        }else{
//            hashMap = parcel.getParcel();
//        }
//        int rid=(int)hashMap.get("rid");
//        String English = (String)hashMap.get("English");
//        String Computer = (String)hashMap.get("Computer");
//        String Certificate1 = (String)hashMap.get("Certificate1");
//        String Certificate2 = (String)hashMap.get("Certificate2");
//        String Certificate3 = (String)hashMap.get("Certificate3");
//        String Certificate4 = (String)hashMap.get("Certificate4");
//        String params = "rid="+rid+"&English="+English+"&Computer="+Computer+"&Certificate1="+Certificate1+"&Certificate2="+Certificate2+"&Certificate3="+Certificate3+"&Certificate4="+Certificate4;
//        httpRequest.sendPost(add_HSkill,params,httpListener);
//    }
//
    public void add_ConnectInfo(Contact contact,HttpListener httpListener){
        //rid，QQ，phone，mail，wechat，Alipay，FaceTime
        long rid = (long)contact.getUid();
        String QQ = contact.getQq();
        String phone = contact.getPhone().getNum();
        String mail = contact.getEmail();
        String wechat = contact.getWechat();
        String Alipay = contact.getAlipay();
        String FaceTime = contact.getPhone().getNum();

        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("QQ",QQ);
        params.put("phone",phone);
        params.put("mail",mail);
        params.put("wechat",wechat);
        params.put("Alipay",Alipay);
        params.put("FaceTime", FaceTime);

        Request request = new Request.Builder().setUrl(server_ip).setApi(add_ConnectInfo).setObject(httpListener).setParams(params).build();
        httpRequest.sendPost(request);
    }


//
//    public static void add_SSkil(ParamsParcel parcel,HttpListener httpListener){
//        HashMap<String,Object> hashMap;
//        if (parcel==null){
//            hashMap = new HashMap<>();
//        }else{
//            hashMap = parcel.getParcel();
//        }
//        int rid = (int)hashMap.get("rid");
//        String skill = (String)hashMap.get("skill");
//        String params = "rid="+rid+"&skill="+skill;
//        httpRequest.sendPost(add_SSkil,params,httpListener);
//    }

    public static void add_JobSkill(){
//        String param = "{\"rid\": \"1\",\"english\": {\"eName\":\"CET4\",\"levels\":\"424以下\"},\"computer\": {\"comName\":[\"C++\",\"python\"],\"levels\":[\"2级\",\"5级\"]},\"certificate\": {\"cerNames\":[\"教师资格证\",\"律师证\"],\"contents\":[\"部分通过\",\"已经通过\"]},\"SJobSkill\":{\"skill\":[\"组织能力\",\"领导能力\",\"沟通能力\"],\"prove\":[\"班长\",\"团支书\",\"学生会干部\"]}}";
//        httpRequest.sendPost(add_JobSkill,param,new HttpListener() {
//            @Override
//            public void succToRequire(String msg, String data) {
//                System.out.println("msg="+msg+" "+"data="+data);
//            }
//
//            @Override
//            public void failToRequire(String msg, String data) {
//                System.out.println("msg="+msg+" "+"data="+data);
//            }
//
//            @Override
//            public void netWorkError(String msg, String data) {
//                System.out.println("msg="+msg+" "+"data="+data);
//            }
//        });
    }

    public void uploadImg(long rid,String imgfile,HttpListener httpFileListener){
        Request request = new Request.Builder().addFileParams("headImg",imgfile).addParams("rid", String.valueOf(rid)).setObject(httpFileListener).setApi(uploadImg).setUrl(server_ip).build();
        httpRequest.sendPost(request);
    }

    public void getDynamic(long rid,HttpListener httpListener){
        Request request = new Request.Builder().addParams("rid", String.valueOf(rid)).setUrl(server_ip).setObject(httpListener).setApi(getDynamic).build();
        httpRequest.sendGet(request);
    }

    public void getOldDynamic(long rid, long did, HttpListener httpListener) {
        String param = "rid="+rid+"&d_id="+did;
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("d_id", String.valueOf(did));
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).setParams(params).setApi(getOldDynamic).build();
        httpRequest.sendPost(request);
    }

    public void add_Dynamic(DynamicInfo dynamicInfo,HttpListener httpListener){
        long rid = dynamicInfo.getUid();
        String content = dynamicInfo.getContent();
        Map<String,String> fileparams = new HashMap<>();
        for (int i=0;i<dynamicInfo.getImageFiles().size();i++){
            fileparams.put("img"+(i+1),dynamicInfo.getImageFiles().get(i));

        }
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("content",content);

        Request request = new Request.Builder().setParams(params).setFileParams(fileparams).setApi(add_Dynamic).setUrl(server_ip).setObject(httpListener).build();

        String[] params2 = {"img1","img2","img3","img4","img5","img6","img7","img8","img9"};
        httpRequest.sendPost(request);
    }

    public void add_Precomment(Precommend precommend,HttpListener httpListener){
        long rid = precommend.getUid();
        String pnanme = precommend.getPname();
        String comname = precommend.getComname();
        String email = precommend.getEmail();
        String wprovince = precommend.getWaddr().getProvince();
        String wcity = precommend.getWaddr().getCity();
        String function = precommend.getFunction();
        String industry = precommend.getIndustry();
        String salary = precommend.getSalary();
        String endate = precommend.getEndate();
        String comdescribe = precommend.getComdescribe();
        String pdescribe = precommend.getPdescribe();

        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("Pname",pnanme);
        params.put("Comname",comname);
        params.put("com_email", email);
        params.put("Wprovince", wprovince);
        params.put("Wcity", wcity);
        params.put("Function", function);
        params.put("Industry", industry);
        params.put("Salary", salary);
        params.put("Enddate", endate);
        params.put("Comdescribe", comdescribe);
        params.put("Pdescribe", pdescribe);

        Request request = new Request.Builder().setObject(httpListener).setUrl(server_ip).setApi(add_Precommend).setParams(params).build();
        httpRequest.sendPost(request);
    }

    public void getPrecommend(long rid,HttpListener httpListener){
        getPrecommend(rid, 0, "", httpListener);
    }

    public void getPrecommend(long rid,int choice,String choiceParam, HttpListener httpListener){
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("choice",String.valueOf(choice));
        switch (choice) {
            case 0:
                break;
            case 1://city
                params.put("city",choiceParam);
                break;
            case 2://field
                params.put("indu",choiceParam);
                break;
            case 3://function
                params.put("func",choiceParam);
                break;
        }
        Request request = new Request.Builder().setObject(httpListener).setParams(params).setUrl(server_ip).setApi(getPrecommend).build();
        httpRequest.sendPost(request);
    }

    public void add_Hobby(Interest interest,HttpListener httpListener){
        String movies = interest.getMovies();
        String books = interest.getBooks();
        String music = interest.getMusic();
        String sports = interest.getSports();
        String et = interest.getEt();
        long rid = interest.getRid();

        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("Movies",movies);
        params.put("Books",books);
        params.put("Music",music);
        params.put("Sports", sports);
        params.put("ET", et);

        Request request = new Request.Builder().setApi(add_Hobby).setObject(httpListener).setUrl(server_ip).setParams(params).build();
        httpRequest.sendPost(request);
    }


    public void getPersonalChar(long rid,HttpListener httpListener){
        String param = "rid="+rid;
        Request request = new Request.Builder().setApi(getPersonalChar).addParams("rid",String.valueOf(rid)).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendGet(request);
    }

    public void add_PersonalChar(PersonalChar personalChar,HttpListener httpListener){
        long rid = personalChar.getRid();
        String ml1 = personalChar.getMyLabel1();
        String ml2 = personalChar.getMyLabel2();
        String ml3 = personalChar.getMyLabel3();
        String des1 = personalChar.getDescribe1();
        String des2 = personalChar.getDescribe2();
        String des3 = personalChar.getDescribe3();
        String param = "rid="+rid+"&MyLabel1="+ml1+"&MyLabel2="+ml2+"&MyLabel3="+ml3+"&Describe1="+des1+"&Describe2="+des2+"&Describe3="+des3;

        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("MyLabel1",ml1);
        params.put("MyLabel2",ml2);
        params.put("MyLabel3",ml3);
        params.put("Describe1",des1);
        params.put("Describe2", des2);
        params.put("Describe3", des3);

        Request request = new Request.Builder().setUrl(server_ip).setApi(add_PersonalChar).setObject(httpListener).setParams(params).build();
        httpRequest.sendPost(request);
    }

    public void add_Experience(Experience experience,HttpListener httpListener){
        add_Interview(experience, httpListener);
        add_Enroll(experience, httpListener);
        add_Service(experience, httpListener);
    }

    public void add_Interview(Experience experience,HttpListener httpListener){
        long rid = experience.getRid();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"name\":\"dess\",\"data\":[");
        List<String> interviews = experience.getInterviewCom();
        int len = interviews.size();
        for (int i=experience.getInterviewId().size();i<len;i++){
            stringBuilder.append("\""+interviews.get(i)+"\"");
            if (i<len-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]}");
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("coms",stringBuilder.toString());

        Request request = new Request.Builder().setObject(httpListener).setUrl(server_ip).setApi(add_Interview).setParams(params).build();
        httpRequest.sendPost(request);
    }

    public void add_Enroll(Experience experience,HttpListener httpListener){
        long rid = experience.getRid();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"name\":\"dess\",\"data\":[");
        List<String> offers = experience.getOfferCom();
        int len = offers.size();
        for (int i=experience.getOfferId().size();i<len;i++){
            stringBuilder.append("\""+offers.get(i)+"\"");
            if (i<len-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]}");
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("coms",stringBuilder.toString());

        Request request = new Request.Builder().setObject(httpListener).setUrl(server_ip).setApi(add_Enroll).setParams(params).build();
        httpRequest.sendPost(request);
    }

    public void add_Service(Experience experience,HttpListener httpListener){
        long rid = experience.getRid();
        String s1 = experience.getService1();
        String s2 = experience.getService2();
        String s3 = experience.getService3();
        int price = experience.getPrice();

        Map<String,String> params = new HashMap<>();
        params.put("rid", String.valueOf(rid));
        params.put("service1", s1);
        params.put("service2", s2);
        params.put("service3", s3);
        params.put("price", String.valueOf(price));

        Request request = new Request.Builder().setObject(httpListener).setUrl(server_ip).setApi(add_Service).setParams(params).build();
        httpRequest.sendPost(request);
    }


    public void getprovide(long rid, int choice, String choiceParam, HttpListener httpListener){
        String param = "rid="+rid+"&choice="+choice;
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("choice",String.valueOf(choice));

        if(choice == 1) {
            params.put("school",choiceParam);
        }else if(choice == 2) {
            params.put("in_com",choiceParam);
        }else if(choice == 3) {
            params.put("en_com",choiceParam);
        }

        Request request = new Request.Builder().setObject(httpListener).setApi(getprovide).setUrl(server_ip).setParams(params).build();
        httpRequest.sendPost(request);
    }



    public void getOldProvide(long rid, int service_id, HttpListener httpListener) {
        String param = "rid="+rid+"&service_id="+service_id;
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("service_id", String.valueOf(service_id));
        Request request = new Request.Builder().setObject(httpListener).setUrl(server_ip).setParams(params).setApi(getoldprovide).build();
        httpRequest.sendPost(request);
    }

    public void getService(long rid,HttpListener httpListener){
        String param = "rid="+rid;
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getService).build();
        httpRequest.sendPost(request);
    }

    public void getEnroll(long rid ,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getEnroll).build();
        httpRequest.sendPost(request);
    }

    public  void getInterview(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getInterview).build();
        httpRequest.sendPost(request);
    }

    public void del_WorkExp(long rid,int wid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("wid",String.valueOf(wid)).setApi(del_WorkExp).build();
        httpRequest.sendPost(request);
    }

    public void del_Posit(long rid,int wid,int pid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("wid",String.valueOf(wid)).addParams("pid",String.valueOf(pid)).setApi(del_Posit).build();
        httpRequest.sendPost(request);
    }

    public void del_Graduate(long rid,int gid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("gid",String.valueOf(gid)).setApi(del_Graduate).build();
        httpRequest.sendPost(request);
    }

    public void del_GStory(long rid,int gid,int gsid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid", String.valueOf(rid)).addParams("gid", String.valueOf(gid)).addParams("gsid",String.valueOf(gsid)).setApi(del_GStory).build();
        httpRequest.sendPost(request);
    }

    public void del_P_Graduate(long rid,int p_gid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("p_gid",String.valueOf(p_gid)).setApi(del_P_Graduate).build();
        httpRequest.sendPost(request);
    }

    public void del_P_GStory(long rid,int p_gid,int ps_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("p_gid",String.valueOf(p_gid)).addParams("ps_id",String.valueOf(ps_id)).setApi(del_P_GStory).build();
        httpRequest.sendPost(request);
    }

    public void del_English(long rid,int eid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("eid",String.valueOf(eid)).setApi(del_English).build();
        httpRequest.sendPost(request);
    }

    public void del_ComputerSkill(long rid,int comp_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("comp_id",String.valueOf(comp_id)).setApi(del_ComputerSkill).build();
        httpRequest.sendPost(request);
    }

    public void del_Certificate(long rid,int cer_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("cer_id",String.valueOf(cer_id)).setApi(del_Certificate).build();
        httpRequest.sendPost(request);
    }

    public void del_SJobSkill(long rid,int sjob_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("sjob_id",String.valueOf(sjob_id)).setApi(del_SJobSkill).build();
        httpRequest.sendPost(request);
    }

    public void del_Interview(long rid,int ICom_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("ICom_id",String.valueOf(ICom_id)).setApi(del_Interview).build();
        httpRequest.sendPost(request);
    }

    public void del_Enroll(long rid,int ECom_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("ECom_id",String.valueOf(ECom_id)).setApi(del_Enroll).build();
        httpRequest.sendPost(request);
    }

    public void get_order_con(long rid,int c_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("service_id",String.valueOf(c_id)).setApi(get_order_con).build();
        httpRequest.sendPost(request);
    }

    public void add_order(long rid,int service_id,String sname,String smodel,String type,int payment,HttpListener httpListener){
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("service_id",String.valueOf(service_id));
        params.put("s_name",sname);
        params.put("s_model",smodel);
        params.put("payment",String.valueOf(payment));
        params.put("price",String.valueOf(payment));
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).setParams(params).setApi(add_order).build();
        httpRequest.sendPost(request);
    }

    public void getsoldservice(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getsoldserivce).build();
        httpRequest.sendPost(request);
    }

    public void getbuyservice(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getbuyservice).build();
        httpRequest.sendPost(request);
    }

    public void getprofessional(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getprofessional).build();
        httpRequest.sendPost(request);
    }

    public void getEnglish(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getEnglish).build();
        httpRequest.sendPost(request);
    }

    public void getCertificate(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getCertificate).build();
        httpRequest.sendPost(request);

    }

    public void getComputer(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getComputer).build();
        httpRequest.sendPost(request);
    }

    public void getSJobSkill(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(getSJobSkill).build();
        httpRequest.sendPost(request);
    }

    public void add_JobSkill(JobsSkill jobsSkill,HttpListener httpListener){
        Map<String,String> param = new HashMap<>();
        param.put("rid", String.valueOf(jobsSkill.getRid()));
        param.put("english", jobsSkill.getEnglishName());
        param.put("elevel", jobsSkill.getEnglishlevel());

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"comNames\":[");
        for (int i=jobsSkill.getCom_ids().size();i<jobsSkill.getComputerLevels().size();i++){
            sb.append("\""+jobsSkill.getComputerNames().get(i)+"\"");
            if (i<jobsSkill.getComputerLevels().size()-1){
                sb.append(",");
            }
        }
        sb.append("],");
        sb.append("\"levels\":[");
        for (int i=jobsSkill.getCom_ids().size();i<jobsSkill.getComputerLevels().size();i++){
            sb.append("\""+jobsSkill.getComputerLevels().get(i)+"\"");
            if (i<jobsSkill.getComputerLevels().size()-1){
                sb.append(",");
            }
        }
        sb.append("]");
        sb.append("}");

        StringBuilder sb2 = new StringBuilder();
        //sb.append("&certificate=";
        sb2.append("{");
        sb2.append("\"cerNames\":[");
        for (int i=jobsSkill.getCer_ids().size();i<jobsSkill.getCerNames().size();i++){
            sb2.append("\""+jobsSkill.getCerNames().get(i)+"\"");
            if (i<jobsSkill.getCerNames().size()-1){
                sb2.append(",");
            }
        }
        sb2.append("],");
        sb2.append("\"contents\":[");
        for (int i=jobsSkill.getCer_ids().size();i<jobsSkill.getCerNames().size();i++){
            sb2.append("\""+jobsSkill.getCerContents().get(i)+"\"");
            if (i<jobsSkill.getCerNames().size()-1){
                sb2.append(",");
            }
        }
        sb2.append("]");
        sb2.append("}");


        StringBuilder sb3 = new StringBuilder();
        //+="&SJobSkill=";
        sb3.append("{");
        sb3.append("\"skill\":[");
        for (int i=jobsSkill.getSjob_ids().size();i<jobsSkill.getSkillprove().size();i++){
            sb3.append("\""+jobsSkill.getSjobSkills().get(i)+"\"");
            if (i<jobsSkill.getSkillprove().size()-1){
                sb3.append(",");
            }
        }
        sb3.append("],");
        sb3.append("\"prove\":[");
        for (int i=jobsSkill.getSjob_ids().size();i<jobsSkill.getSkillprove().size();i++){
            sb3.append("\""+jobsSkill.getSkillprove().get(i)+"\"");
            if (i<jobsSkill.getSkillprove().size()-1){
                sb3.append(",");
            }
        }
        sb3.append("]");
        sb.append("}");

        param.put("computer", sb.toString());
        param.put("certificate", sb2.toString());
        param.put("SJobSkill", sb3.toString());

        Request request = new Request.Builder().setObject(httpListener).setUrl(server_ip).setApi(add_JobSkill).setParams(param).build();
        httpRequest.sendPost(request);
    }

    public void get_talented(long rid,int choice, String choiceParam, HttpListener httpListener){
        Map<String,String> params = new HashMap<>();
        String param = "rid="+rid+"&"+"choice="+choice;
        params.put("rid",String.valueOf(rid));
        params.put("choice",String.valueOf(choice));
        switch (choice) {
            case 1:
                params.put("school",choiceParam);
                break;
            case 3:
                params.put("u_major",choiceParam);
                break;
        }
        Request request = new Request.Builder().setApi(get_talented).setParams(params).setUrl(server_ip).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void get_userId(String account,HttpListener httpListener){
        Request request = new Request.Builder().setObject(httpListener).setUrl(server_ip).setApi(get_userId).addParams("account", account).build();
        httpRequest.sendPost(request);
    }

    public static void add_applyPrecommend(long rid,String comName,String number,String imgfile){

    }

    public void get_InIndustry(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(get_InIndustry).build();
        httpRequest.sendPost(request);
    }

    public void get_Function(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(get_Function).build();
        httpRequest.sendPost(request);
    }

    public void getoldDynamic(long rid,int d_id,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid", String.valueOf(rid)).addParams("d_id", String.valueOf(d_id)).setApi(getoldDynamic).build();
        httpRequest.sendPost(request);

    }

    public void resetPassword(long rid,String oldps,String newps,HttpListener httpListener){
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(rid));
        params.put("password",oldps);
        params.put("newpassword", newps);

        Request request = new Request.Builder().setApi(resetPassword).setUrl(server_ip).setParams(params).setObject(httpListener).build();
        httpRequest.sendPost(request);
    }

    public void add_applyPrecommend(long uid,String comName,String cerNum,String imgfile,HttpListener httpListener){
        Map<String,String> params = new HashMap<>();
        params.put("rid",String.valueOf(uid));
        params.put("comName",comName);
        params.put("number",cerNum);
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpRequest).setApi(add_applyPrecommend).setParams(params).addFileParams("Img", imgfile).build();
    }

    public void add_noprivacy(long uid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(uid)).setApi(add_noprivacy).build();
        httpRequest.sendPost(request);
    }

    public void add_privacy(long uid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(uid)).setApi(add_privacy).build();
        httpRequest.sendPost(request);
    }

    public void getsoldserivce(long uid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(uid)).setApi(getsoldserivce).build();
        httpRequest.sendPost(request);
    }

    public void get_useAccount(long rid,HttpListener httpListener){
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).setApi(get_useAccount).build();
        httpRequest.sendPost(request);
    }

    public void findDynamic(long rid, String Dword, HttpListener httpListener) {
        Request request = new Request.Builder().setUrl(server_ip).setObject(httpListener).addParams("rid",String.valueOf(rid)).addParams("Dword",Dword).setApi(add_noprivacy).build();
        httpRequest.sendPost(request);
    }
}
