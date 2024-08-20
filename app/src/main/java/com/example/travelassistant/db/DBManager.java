package com.example.travelassistant.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.travelassistant.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBManager {
    public static SQLiteDatabase database;
    /*初始化数据库信息*/
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    //    新增几条博客记录
    public static long addBlogInfo(Context context){
        long l=0;
//        for (int i = 0; i < 2; i++) {
//            ContentValues values = new ContentValues();
//            values.put("num",10086+i);
//            values.put("name","长城");
//            values.put("avatar","111");
//            values.put("nickname","北京协和医院推文");
//            values.put("introduce","简介");
//            String s = insertIntoDB(context,R.drawable.jiangzuo);
//            values.put("content",s);
//            long info = database.insert("info", null, values);
//            l=l+info;
//
//
//        }
        ContentValues values1 =new ContentValues();
        values1.put("num",100861);
        values1.put("name","医疗知识讲座");
        values1.put("avatar","111");
        values1.put("nickname","@北京协和医院宣传");
        values1.put("introduce","生活急救|急救常识|中毒急救|应急救护");

        String s = insertIntoDB(context,R.drawable.jiangzuo);
        values1.put("content",s);
        long info1 = database.insert("info", null, values1);
//
        ContentValues values2 =new ContentValues();
        values2.put("num",100862);
        values2.put("name","脱发的起居饮食");
        values2.put("avatar","111");
        values2.put("nickname","@生活小妙招分享");
        values2.put("introduce","脱发问题的起居饮食调理对于改善脱发状况具有重要意义。以下从起居和饮食两个方面给出具体的建议：\n" +
                "\n" +
                "一、起居调理\n" +
                "\n" +
                "睡眠充足：保持足够的睡眠时间，建议每晚7-8小时。良好的睡眠有助于调节荷尔蒙水平，减少压力，促进头发生长。\n" +
                "避免熬夜：尽量在24点之前入睡，因为晚上22点到凌晨2点是负责向头发运输营养促进生长的荷尔蒙的分泌高峰期。\n" +
                "洗头时间：建议在晚上洗头，避免在早上洗头时洗发水残留在因头发生长而松弛的毛孔中，导致头皮受损。如果早上需要洗头，建议使用清水轻轻冲洗。\n" +
                "泡澡习惯：每天泡个澡可以促进新陈代谢，有助于改善血液循环，对头发产生积极影响。\n" +
                "生活习惯：避免频繁地染发、烫发和拉直发，这些行为会损伤头发和头皮。\n" +
                "二、饮食调理\n" +
                "\n" +
                "多吃富含铁元素的食物：如豆腐、胡萝卜、花生、菠菜、马铃薯等，这些食物有助于缓解脱发的症状。\n" +
                "补充植物蛋白：如大豆、黑芝麻、玉米等，这些食物可以帮助补充植物蛋白，改善脱发情况。\n" +
                "摄入维生素丰富的食物：如苹果、芹菜、猕猴桃等，这些食物有助于维持上皮组织的功能，促进头发生长。\n" +
                "优质蛋白食品：适当摄入鸡、牛奶、肉类等含丰富蛋白质的食物，帮助增强体质，有利于改善脱发。\n" +
                "忌口食物：脱发患者需要避免烟、酒及辛辣刺激食物，如葱、蒜、韭菜、姜、花椒、辣椒、桂皮等。同时，也要少吃油腻、燥热食物（肥肉、油炸食品）以及过食糖和脂肪丰富的食物（肝类、肉类、洋葱等）。\n" +
                "总结：脱发的起居饮食调理需要综合考虑睡眠、洗头时间、泡澡习惯、生活习惯以及饮食等多个方面。通过合理的起居安排和饮食调理，可以有效改善脱发状况，促进头发生长。同时，如果脱发情况严重，建议及时就医咨询专业医生。");
        String s2 = insertIntoDB(context,R.drawable.tuofa);
        values2.put("content",s2);
        long info2 = database.insert("info", null, values2);


        ContentValues values3 =new ContentValues();
        values3.put("num",100863);
        values3.put("name","什么是智慧医疗");
        values3.put("avatar","111");
        values3.put("nickname","春暖金花开");
        values3.put("introduce","智慧医疗（WITMED）是近年来兴起的医疗领域专有名词，它主要通过打造健康档案区域医疗信息平台，利用最先进的物联网技术，实现患者与医务人员、医疗机构、医疗设备之间的互动，逐步达到信息化。以下是关于智慧医疗的详细解释：\n" +
                        "\n" +
                        "概念与背景：\n" +
                        "智慧医疗致力于将人工智能、传感技术等高科技融入医疗行业，推动医疗服务走向真正的智能化，促进医疗事业的繁荣发展。\n" +
                        "在中国新医改的大背景下，智慧医疗正在逐步走进寻常百姓的生活，以解决医疗成本高、渠道少、覆盖面低等问题。\n" +
                        "组成部分：\n" +
                        "智慧医疗由三部分组成：智慧医院系统、区域卫生系统、以及家庭健康系统。\n" +
                        "智慧医院系统：包括医院信息系统（HIS）、实验室信息管理系统（LIS）、医学影像信息的存储和传输系统（PACS）以及医生工作站等，实现病人诊疗信息和行政管理信息的收集、存储、处理、提取及数据交换。\n" +
                        "区域卫生系统：通过收集、处理、传输社区、医院、医疗科研机构、卫生监管部门记录的所有信息，实现医疗资源的优化配置和共享。\n" +
                        "家庭健康系统：为市民提供健康保障，包括视讯医疗、远程照护、健康监测等功能。\n" +
                        "特点：\n" +
                        "数据驱动：通过搜集、整理、分析医疗数据，为医疗决策提供依据。\n" +
                        "个性化定制：根据病人的个体差异和特定需求，提供个性化的医疗方案和治疗计划。\n" +
                        "异地医疗：打破地域限制，通过远程诊断、远程手术等方式，实现医疗资源的共享和分配。\n" +
                        "医患互动：提倡医患之间更加紧密的互动和沟通，提高医患双方的参与感和满意度。\n" +
                        "信息安全：注重个人隐私保护和信息安全，采取多层次的安全措施，防止数据泄露和滥用。\n" +
                        "应用场景：\n" +
                        "远程会诊：利用5G网络高速率的特性，支持高清远程会诊和医学影像数据的高速传输与共享。\n" +
                        "远程超声：建立高清无延迟的远程超声系统，实现跨区域、跨医院之间的业务指导和质量管控。\n" +
                        "远程手术：利用医工机器人和高清音视频交互系统，实现跨地域远程精准手术操控和指导。\n" +
                        "应急救援：通过5G网络实时传输医疗设备监测信息、车辆实时定位信息等，提升管理救治效率。\n" +
                        "意义：\n" +
                        "智慧医疗不仅使医疗服务更加便捷、高效，还能有效降低医疗成本，提高医疗质量，缓解医疗资源紧张的问题，推动健康中国的建设。");
        String s3 = insertIntoDB(context,R.drawable.down);
        values3.put("content",s3);
        long info3 = database.insert("info", null, values3);

        ContentValues values4 =new ContentValues();
        values4.put("num",100864);
        values4.put("name","如何补气血");
        values4.put("avatar","111");
        values4.put("nickname","大俊爱养生");
        values4.put("introduce","补气血是中医理论中的重要概念，对于维持人体正常功能至关重要。以下是一些补气血的方法，供您参考：\n" +
                "\n" +
                "一、中药调理\n" +
                "\n" +
                "中药调理是补气血的重要方法之一。由于脾胃是气血生化之源，所以调理脾胃是补气血的关键。可以选用山药、薏米、南瓜等健脾益胃的食材进行食用，以辅助提升脾胃功能。此外，中药材如黄芪、白术、升麻等，通过煎制服用，也具有调理脾胃和补充气血的良好效果。\n" +
                "\n" +
                "二、运动调理\n" +
                "\n" +
                "运动是补气血的有效手段。选择合适的有氧运动，如慢跑、跳绳、瑜伽等，可以有效地促进身体的新陈代谢，调节内分泌水平，帮助气血恢复常态。但请注意，运动不宜过于剧烈，以免耗伤气血。");
        String s4 = insertIntoDB(context,R.drawable.qixue);
        values4.put("content",s4);
        long info4 = database.insert("info", null, values4);




        return l;
    }

    /*获取数据库中全部的数据*/
    @SuppressLint("Range")
    public static List<BlogBean>queryAll(){
        Cursor cursor=database.query("info",null,null,null,null,null,null);
        CursorWindow cw = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            cw = new CursorWindow("test", 5000000);
        }
        AbstractWindowedCursor ac = (AbstractWindowedCursor) cursor;
        ac.setWindow(cw);
        List<BlogBean> blogBeanList=new ArrayList<>();
        while(cursor.moveToNext()){
            BlogBean blogBean=new BlogBean();
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String num = cursor.getString(cursor.getColumnIndex("num"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String introduce = cursor.getString(cursor.getColumnIndex("introduce"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            blogBean.set_id(Integer.parseInt(id));
            blogBean.setAvatar(avatar);
            blogBean.setContent(content);
            blogBean.setName(name);
            blogBean.setNickname(nickname);
            blogBean.setNum(num);
            blogBean.setIntroduce(introduce);
            blogBeanList.add(blogBean);

        }
        return blogBeanList;

    }
    public static long addOneBlogInfo(BlogBean blogBean){
        ContentValues values = new ContentValues();
        values.put("num",blogBean.getNum());
        values.put("name",blogBean.getName());
        values.put("avatar",blogBean.getAvatar());
        values.put("nickname",blogBean.getNickname());
        values.put("introduce",blogBean.getIntroduce());
        values.put("content",blogBean.getContent());
        return database.insert("info",null,values);
    }
    public static List<BlogBean> queryBlogformtb(String num)
    {
        List<BlogBean> blogBeanList=new ArrayList<>();
        String sql="select * from info where num=?";
        Cursor cursor=database.rawQuery(sql,new String[]{num});
        CursorWindow cw = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            cw = new CursorWindow("test", 5000000);
        }
        AbstractWindowedCursor ac = (AbstractWindowedCursor) cursor;
        ac.setWindow(cw);
        while (cursor.moveToNext()) {
            BlogBean blogBean=new BlogBean();
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex("num"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
            @SuppressLint("Range") String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            @SuppressLint("Range") String introduce = cursor.getString(cursor.getColumnIndex("introduce"));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            blogBean.set_id(Integer.parseInt(id));
            blogBean.setAvatar(avatar);
            blogBean.setContent(content);
            blogBean.setName(name);
            blogBean.setNickname(nickname);
            blogBean.setIntroduce(introduce);
            blogBean.setNum(number);
            blogBeanList.add(blogBean);
        }
        return blogBeanList;



    }
    public static String insertIntoDB(Context context,int id){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileNamePrefix = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (storageDir != null) {
            try {
                // 创建一个临时文件
                File imageFile = File.createTempFile(
                        imageFileNamePrefix,  // prefix
                        ".jpg",               // suffix
                        storageDir            // directory
                );

                // 从res中读取图片资源
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

                // 将Bitmap写入到文件中
                FileOutputStream out = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // 100 is the quality of the saved JPEG (max quality)
                out.flush();
                out.close();

                // 现在你可以使用imageFile的绝对路径了
                String filePath = imageFile.getAbsolutePath();
                return filePath;
                // ... 在这里使用filePath

            } catch (IOException e) {

                // 适当的错误处理
                e.printStackTrace();
                return null;
                // 可以考虑通知用户或记录错误
            }
        } else {
            return null;
            // 处理存储目录为空的情况
            // ...
        }
    }
}
