package com.example.travelassistant.food.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



import com.example.travelassistant.food.bean.FoodBean;
import com.example.travelassistant.food.bean.ShopBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetsUtils {

    /*读取assets文件夹中的内容 存放到字符串当中
     */
    private static Map<String,Bitmap> logoImgMap;
    private static Map<String,Bitmap>contentlogoImgMap;
    public static String getJsonFromAssets(Context context, String filename){



        //1.获取Assets文件夹管理器
        AssetManager am = context.getResources().getAssets();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        2.获取输入流
        try {
            InputStream is = am.open(filename);
            //读取内容存放到内存流当中
            int hasRead=0;
            byte[]buf=new  byte[1024];
            while(true){
                hasRead = is.read(buf);
                if(hasRead==-1){
                    break;
                }
                baos.write(buf,0,hasRead);
            }
            String msg=baos.toString();
            is.close();
            return msg;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    读取Assets文件夹下的图片 返回bitmap对象
     */

    public static Bitmap getBitmapFromAssets(Context context, String filename){
        Bitmap bitmap=null;
        //获取文件夹管理者
        AssetManager assets = context.getResources().getAssets();
        try {
            InputStream inputStream=assets.open(filename);
            //通过位图管理器 将输入流转换成位图对象
            byte[] bytes = streamToByte(inputStream);

//            byte[] data=readStream(inputStream);
//
//            if(data!=null){
           // bitmap = BitmapFactory.decodeStream(new PatchInputStream(inputStream));

            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

//
//            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }
    /*
     * 得到图片字节流 数组大小
     * */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
    public static byte[] streamToByte(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return buffer.toByteArray();
    }


    /*
    将Assets文件夹当中的图片一起读取，放置在内存中，便于管理
     */
    public static void saveBitmapFromAssets(Context context, List<ShopBean> shopBeanList)
    {
        logoImgMap=new HashMap<>();
        contentlogoImgMap=new HashMap<>();

        for(int i=0;shopBeanList.size()>i;i++)
        {
            String shopname=shopBeanList.get(i).getShopName();
            String shopPic = shopBeanList.get(i).getShopPic();
            String substring = shopPic.substring(32);


            String filename=substring;
            Bitmap logobm=getBitmapFromAssets(context,filename);

            logoImgMap.put(shopname,logobm);

            List<FoodBean> foodList = shopBeanList.get(i).getFoodList();
            for (int j = 0; j < foodList.size(); j++) {
                String foodName = foodList.get(j).getFoodName();
                String foodPic = foodList.get(j).getFoodPic();
                String substring1 = foodPic.substring(32);
                Bitmap foodbm=getBitmapFromAssets(context,substring1);
                contentlogoImgMap.put(foodName,foodbm);





            }



        }

    }
    public static Map<String, Bitmap> getContentlogoImgMap() {
        return contentlogoImgMap;
    }

    public static Map<String, Bitmap> getLogoImgMap() {
        return logoImgMap;
    }
}
