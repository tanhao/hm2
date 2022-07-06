package com.xzy.mem.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.pojo.result.response.ResultRp;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName RoadUtils
 * @Description 路纸工具类
 * @Auther yzy
 * @Date 2019/8/12 11:39
 * @Version 1.0
 */
public class RoadUtils {
    /**
     * 统计PK10的路纸信息
     *@auther yzy
     *@date 2019/8/13 10:41
     */
    public static JSONObject createPk10RoadInfo(List<ResultRp> list){
        Map<String, Integer> changLongMap = new HashMap<String, Integer>();
        JSONObject obj = new JSONObject();
        for(int i=1;i<11;i++){
            String valKey = new StringBuilder().append("no").append(i).append("val").toString();
            obj.put(valKey,new JSONArray());
            String ouKey = new StringBuilder().append("no").append(i).append("ou").toString();
            obj.put(ouKey,new JSONArray());
            String oeKey = new StringBuilder().append("no").append(i).append("oe").toString();
            obj.put(oeKey,new JSONArray());

            JSONObject numJson = new JSONObject();
            for(int no=1;no<11;no++){
                numJson.put(no+"",0);
            }
            obj.put(new StringBuilder().append("no").append(i).toString(),numJson);
        }
        obj.put("gyh",new JSONArray());
        obj.put("gyhou",new JSONArray());
        obj.put("gyhoe",new JSONArray());
        for(int i=0,len=list.size();i<len;i++){
            if(StringUtils.isBlank(list.get(i).getResult())){
                continue;
            }
            addRoadInfo(list.get(i).getResult(),LotteryUtils.GROUP_PK10,obj,changLongMap);
            if(obj.getJSONArray("gyh").size()>=50){
                break;
            }
        }
        Map<String,Integer> sortedMap = changLongMap.entrySet().stream().filter((entry)->entry.getValue()>1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(old,newValue)->old,LinkedHashMap::new));
        JSONArray clArray = new JSONArray();

        for(Map.Entry<String,Integer> entry:sortedMap.entrySet()){
            JSONObject clJson = new JSONObject();
            clJson.put(entry.getKey(),entry.getValue());
            clArray.add(clJson);
        }
        obj.put("changlong", clArray);
        return obj;
    }

    /**
     * 统计时时彩类的路纸信息
     *@auther yzy
     *@date 2019/8/15 9:18
     */
    public static JSONObject createSscRoadInfo(List<ResultRp> list){
        Map<String, Integer> changLongMap = new HashMap<String, Integer>();
        JSONObject obj = new JSONObject();
        for(int i=1;i<6;i++){
            String valKey = new StringBuilder().append("no").append(i).append("val").toString();
            obj.put(valKey,new JSONArray());
            String ouKey = new StringBuilder().append("no").append(i).append("ou").toString();
            obj.put(ouKey,new JSONArray());
            String oeKey = new StringBuilder().append("no").append(i).append("oe").toString();
            obj.put(oeKey,new JSONArray());

            JSONObject numJson = new JSONObject();
            for(int no=0;no<10;no++){
                numJson.put(no+"",0);
            }
            obj.put(new StringBuilder().append("no").append(i).toString(),numJson);
        }
        obj.put("dtt",new JSONArray());
        obj.put("sumou",new JSONArray());
        obj.put("sumoe",new JSONArray());
        for(int i=0,len=list.size();i<len;i++){
            if(StringUtils.isBlank(list.get(i).getResult())){
                continue;
            }
            addRoadInfo(list.get(i).getResult(),LotteryUtils.GROUP_SSC,obj,changLongMap);
            if(obj.getJSONArray("sumou").size()>=50){
                break;
            }
        }
        Map<String,Integer> sortedMap = changLongMap.entrySet().stream().filter((entry)->entry.getValue()>1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(old,newValue)->old,LinkedHashMap::new));
        JSONArray clArray = new JSONArray();

        for(Map.Entry<String,Integer> entry:sortedMap.entrySet()){
            JSONObject clJson = new JSONObject();
            clJson.put(entry.getKey(),entry.getValue());
            clArray.add(clJson);
        }
        obj.put("changlong", clArray);

        return obj;
    }

    /**
     * 统计广东11选5的路纸信息
     *@auther yzy
     *@date 2019/8/15 9:18
     */
    public static JSONObject createGD11X5RoadInfo(List<ResultRp> list){
        Map<String, Integer> changLongMap = new HashMap<String, Integer>();
        JSONObject obj = new JSONObject();
        for(int i=1;i<6;i++){
            String valKey = new StringBuilder().append("no").append(i).append("val").toString();
            obj.put(valKey,new JSONArray());
            String oeKey = new StringBuilder().append("no").append(i).append("oe").toString();
            obj.put(oeKey,new JSONArray());
            String ouKey = new StringBuilder().append("no").append(i).append("ou").toString();
            obj.put(ouKey,new JSONArray());

            JSONObject numJson = new JSONObject();
            for(int no=1;no<12;no++){
                numJson.put(no+"",0);
            }
            obj.put(new StringBuilder().append("no").append(i).toString(),numJson);
        }
        JSONObject numJsons = new JSONObject();
        for(int no=1;no<12;no++){
            numJsons.put(no+"",0);
        }
        obj.put(new StringBuilder().append("noNum").toString(),numJsons);
        obj.put("sumou",new JSONArray());
        obj.put("sumoe",new JSONArray());
        obj.put("sumwsou",new JSONArray());
        obj.put("dtt",new JSONArray());
        List<String> noNumList = new ArrayList<String>();//存放无出期数的数据
        for(int i=0,len=list.size();i<len;i++){
            if(StringUtils.isBlank(list.get(i).getResult())){
                continue;
            }
            noNumList.add(list.get(i).getResult());
            addRoadInfo(list.get(i).getResult(),LotteryUtils.GROUP_11X5,obj,changLongMap);
            if(obj.getJSONArray("sumou").size()>=50){
                break;
            }
        }
        Collections.reverse(noNumList);//倒序排列
        for(int i=0,len=noNumList.size();i<len;i++){
            addNoNum(noNumList.get(i),obj,LotteryUtils.GROUP_11X5,12);
        }
        Map<String,Integer> sortedMap = changLongMap.entrySet().stream().filter((entry)->entry.getValue()>1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(old,newValue)->old,LinkedHashMap::new));
        JSONArray clArray = new JSONArray();

        for(Map.Entry<String,Integer> entry:sortedMap.entrySet()){
            JSONObject clJson = new JSONObject();
            clJson.put(entry.getKey(),entry.getValue());
            clArray.add(clJson);
        }
        obj.put("changlong", clArray);
        return obj;
    }

    /**
     * 统计快乐8的路纸信息
     *@auther hzh
     *@date 2019/8/15 9:18
     */
    public static JSONObject createKL8RoadInfo(List<ResultRp> list){
        Map<String, Integer> changLongMap = new HashMap<String, Integer>();
        JSONObject obj = new JSONObject();
        obj.put("sum",new JSONArray());
        obj.put("sumou",new JSONArray());
        obj.put("sumoe",new JSONArray());
        obj.put("wx",new JSONArray());
        obj.put("tehe",new JSONArray());
        obj.put("oehe",new JSONArray());
        for(int i=0,len=list.size();i<len;i++){
            if(StringUtils.isBlank(list.get(i).getResult())){
                continue;
            }
            addRoadInfo(list.get(i).getResult(),LotteryUtils.GROUP_KL8,obj,changLongMap);
            if(obj.getJSONArray("sum").size()>=30){
                break;
            }
        }
        Map<String,Integer> sortedMap = changLongMap.entrySet().stream().filter((entry)->entry.getValue()>1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(old,newValue)->old,LinkedHashMap::new));
        JSONArray clArray = new JSONArray();

        for(Map.Entry<String,Integer> entry:sortedMap.entrySet()){
            JSONObject clJson = new JSONObject();
            clJson.put(entry.getKey(),entry.getValue());
            clArray.add(clJson);
        }
        obj.put("changlong", clArray);
        return obj;
    }

    /**
     *统计PCDD的路纸信息
     * @param list
     * @return
     */
    public static JSONObject createPCDDRoadInfo(List<ResultRp> list){
        Map<String, Integer> changLongMap = new HashMap<String, Integer>();
        JSONObject obj = new JSONObject();
        obj.put("sumou",new JSONArray());
        obj.put("sumoe",new JSONArray());
        obj.put("sebo",new JSONArray());
        for(int i=0,len=list.size();i<len;i++){
            if(StringUtils.isBlank(list.get(i).getResult())){
                continue;
            }
            addRoadInfo(list.get(i).getResult(),LotteryUtils.GROUP_LUCK28,obj,changLongMap);
        }
        Map<String,Integer> sortedMap = changLongMap.entrySet().stream().filter((entry)->entry.getValue()>1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(old,newValue)->old,LinkedHashMap::new));
        JSONArray clArray = new JSONArray();

        for(Map.Entry<String,Integer> entry:sortedMap.entrySet()){
            JSONObject clJson = new JSONObject();
            clJson.put(entry.getKey(),entry.getValue());
            clArray.add(clJson);
        }
        obj.put("changlong", clArray);
        return obj;
    }


    /**
     * 统计广西快乐10的路纸信息
     *@auther hzh
     *@date 2019/8/15 9:18
     */
    public static JSONObject createGXKL10RoadInfo(List<ResultRp> list){
        Map<String, Integer> changLongMap = new HashMap<String, Integer>();
        JSONObject obj = new JSONObject();
        for(int i=1;i<6;i++){
            String valKey = new StringBuilder().append("no").append(i).append("val").toString();
            String oeKey = new StringBuilder().append("no").append(i).append("oe").toString();
            String ouKey = new StringBuilder().append("no").append(i).append("ou").toString();
            String wsouKey = new StringBuilder().append("no").append(i).append("wsou").toString();
            String flsxKey = new StringBuilder().append("no").append(i).append("flsx").toString();
            String sbKey = new StringBuilder().append("no").append(i).append("sb").toString();
            String heKey = new StringBuilder().append("no").append(i).append("heoe").toString();
            obj.put(wsouKey,new JSONArray());
            obj.put(flsxKey,new JSONArray());
            obj.put(sbKey,new JSONArray());
            obj.put(heKey,new JSONArray());
            obj.put(oeKey,new JSONArray());
            obj.put(valKey,new JSONArray());
            obj.put(ouKey,new JSONArray());
            JSONObject numJson = new JSONObject();
            for(int no=1;no<22;no++){
                numJson.put(no+"",0);
            }
            obj.put(new StringBuilder().append("no").append(i).toString(),numJson);
        }
        JSONObject numJsons = new JSONObject();
        for(int no=1;no<22;no++){
            numJsons.put(no+"",0);
        }
        obj.put(new StringBuilder().append("noNum").toString(),numJsons);
        obj.put("sumou",new JSONArray());//总和大小
        obj.put("sumoe",new JSONArray());//总和单双
        obj.put("sumws",new JSONArray());//总和尾数
        obj.put("dtt",new JSONArray());//龙虎
        List<String> noNumList = new ArrayList<String>();
        for(int i=0,len=list.size();i<len;i++){
            if(StringUtils.isBlank(list.get(i).getResult())){
                continue;
            }
            noNumList.add(list.get(i).getResult());
            addRoadInfo(list.get(i).getResult(),LotteryUtils.GROUP_GXKL10,obj,changLongMap);
            if(obj.getJSONArray("sumou").size()>=50){
                break;
            }
        }
        Collections.reverse(noNumList);//倒序排列
        for(int i=0,len=noNumList.size();i<len;i++){
            addNoNum(noNumList.get(i),obj,LotteryUtils.GROUP_GXKL10,22);
        }
        Map<String,Integer> sortedMap = changLongMap.entrySet().stream().filter((entry)->entry.getValue()>1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(old,newValue)->old,LinkedHashMap::new));
        JSONArray clArray = new JSONArray();

        for(Map.Entry<String,Integer> entry:sortedMap.entrySet()){
            JSONObject clJson = new JSONObject();
            clJson.put(entry.getKey(),entry.getValue());
            clArray.add(clJson);
        }
        obj.put("changlong", clArray);
        return obj;
    }


    /**
     * 统计广东快乐10的路纸信息
     *@auther hzh
     *@date 2019/8/15 9:18
     */
    public static JSONObject createGDKL10RoadInfo(List<ResultRp> list){
        Map<String, Integer> changLongMap = new HashMap<String, Integer>();
        JSONObject obj = new JSONObject();
        for(int i=1;i<9;i++){
            String valKey = new StringBuilder().append("no").append(i).append("val").toString();
            String oeKey = new StringBuilder().append("no").append(i).append("oe").toString();
            String ouKey = new StringBuilder().append("no").append(i).append("ou").toString();
            String wsouKey = new StringBuilder().append("no").append(i).append("wsou").toString();
            String heKey = new StringBuilder().append("no").append(i).append("heoe").toString();
            if(i<5){
                String dttKey = new StringBuilder().append("no").append(i).append("dtt").toString();
                obj.put(dttKey,new JSONArray());//龙虎
            }
            obj.put(wsouKey,new JSONArray());
            obj.put(oeKey,new JSONArray());
            obj.put(valKey,new JSONArray());
            obj.put(heKey,new JSONArray());
            obj.put(ouKey,new JSONArray());
            JSONObject numJson = new JSONObject();
            for(int no=1;no<21;no++){
                numJson.put(no+"",0);
            }
            obj.put(new StringBuilder().append("no").append(i).toString(),numJson);
        }
        JSONObject numJsons = new JSONObject();
        for(int no=1;no<21;no++){
            numJsons.put(no+"",0);
        }
        obj.put(new StringBuilder().append("noNum").toString(),numJsons);
        obj.put("sumou",new JSONArray());//总和大小
        obj.put("sumoe",new JSONArray());//总和单双
        obj.put("sumws",new JSONArray());//总和尾数
        List<String> noNumList = new ArrayList<String>();
        for(int i=0,len=list.size();i<len;i++){
            if(StringUtils.isBlank(list.get(i).getResult())){
                continue;
            }
            noNumList.add(list.get(i).getResult());
            addRoadInfo(list.get(i).getResult(),LotteryUtils.GROUP_HAPPY10,obj,changLongMap);
            if(obj.getJSONArray("sumou").size()>=50){
                break;
            }
        }
        Collections.reverse(noNumList);//倒序排列
        for(int i=0,len=noNumList.size();i<len;i++){
            addNoNum(noNumList.get(i),obj,LotteryUtils.GROUP_HAPPY10,21);
        }
        Map<String,Integer> sortedMap = changLongMap.entrySet().stream().filter((entry)->entry.getValue()>1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(old,newValue)->old,LinkedHashMap::new));
        JSONArray clArray = new JSONArray();

        for(Map.Entry<String,Integer> entry:sortedMap.entrySet()){
            JSONObject clJson = new JSONObject();
            clJson.put(entry.getKey(),entry.getValue());
            clArray.add(clJson);
        }
        obj.put("changlong", clArray);
        return obj;
    }

    /**
     * 计算无出期数
     * num 传过来的次数
     */
    public static void addNoNum(String result,JSONObject obj,String groupKey,int num){
        List<Integer> lists= Arrays.asList(result.split(",")).stream().map((value)->Integer.parseInt(value)).collect(Collectors.toList());
        String noKey = new StringBuilder().append("noNum").toString();
        for(int no=1;no<num;no++){
            if(no==lists.get(0)){
                String numKey = new StringBuilder().append(lists.get(0)).toString();
                obj.getJSONObject(noKey).put(numKey,0);
            }else if(no==lists.get(1)){
                String numKey = new StringBuilder().append(lists.get(1)).toString();
                obj.getJSONObject(noKey).put(numKey,0);
            }else if(no==lists.get(2)){
                String numKey = new StringBuilder().append(lists.get(2)).toString();
                obj.getJSONObject(noKey).put(numKey,0);
            }else if(no==lists.get(3)){
                String numKey = new StringBuilder().append(lists.get(3)).toString();
                obj.getJSONObject(noKey).put(numKey,0);
            }else if(no==lists.get(4)){
                String numKey = new StringBuilder().append(lists.get(4)).toString();
                obj.getJSONObject(noKey).put(numKey,0);
            } else {
                if(groupKey.equals(LotteryUtils.GROUP_HAPPY10)){
                    if(no==lists.get(5)){
                        String numKey = new StringBuilder().append(lists.get(5)).toString();
                        obj.getJSONObject(noKey).put(numKey,0);
                    }else if(no==lists.get(6)){
                        String numKey = new StringBuilder().append(lists.get(6)).toString();
                        obj.getJSONObject(noKey).put(numKey,0);
                    }else if(no==lists.get(7)){
                        String numKey = new StringBuilder().append(lists.get(7)).toString();
                        obj.getJSONObject(noKey).put(numKey,0);
                    }else {
                        String numKey = new StringBuilder().append(no).toString();
                        obj.getJSONObject(noKey).put(numKey,obj.getJSONObject(noKey).getInteger(numKey)+1);
                    }
                }else {
                    String numKey = new StringBuilder().append(no).toString();
                    obj.getJSONObject(noKey).put(numKey,obj.getJSONObject(noKey).getInteger(numKey)+1);
                }
            }
        }
    }

    /**
     * 赛果生成路纸信
     *@auther yzy
     *@date 2019/8/12 16:00
     */
    private static void addRoadInfo(String result,String groupKey,JSONObject obj,Map<String, Integer> changLongMap ){

        List<Integer> list= Arrays.asList(result.split(",")).stream().map((value)->Integer.parseInt(value)).collect(Collectors.toList());

        Integer heOverVal = LotteryUtils.OVER_UNDER_VALUE_MAP.get(groupKey)[0];
        Integer noOverVal = LotteryUtils.OVER_UNDER_VALUE_MAP.get(groupKey)[1];

        Integer heVal = 0;
        if(groupKey.equals(LotteryUtils.GROUP_PK10)){
            heVal = list.get(0)+list.get(1);
        }else if(groupKey.equals(LotteryUtils.GROUP_SSC)){
            heVal = list.stream().reduce(0,Integer::sum);
        }else if(groupKey.equals(LotteryUtils.GROUP_KL8)){
            heVal = list.stream().reduce(0,Integer::sum);
        }else if(groupKey.equals(LotteryUtils.GROUP_LUCK28)){
            heVal = list.get(0)+list.get(1)+list.get(2);
        }else if(groupKey.equals(LotteryUtils.GROUP_11X5)){
            heVal = list.stream().reduce(0,Integer::sum);
        }else if(groupKey.equals(LotteryUtils.GROUP_GXKL10)){
            heVal = list.stream().reduce(0,Integer::sum);
        }else if(groupKey.equals(LotteryUtils.GROUP_HAPPY10)){
            heVal = list.stream().reduce(0,Integer::sum);
        }
        String heOu = heVal>heOverVal?LotteryUtils.OVER:LotteryUtils.UNDER;
        if(groupKey.equals(LotteryUtils.GROUP_HAPPY10)){
            heOu = heVal>heOverVal?LotteryUtils.OVER:heVal<heOverVal?LotteryUtils.UNDER:LotteryUtils.DRAW;
        }
        String heOe = heVal%2==1?LotteryUtils.ODD:LotteryUtils.EVEN;
        if(groupKey.equals(LotteryUtils.GROUP_PK10)){
            obj.getJSONArray("gyh").add(heVal);
            obj.getJSONArray("gyhou").add(heOu);
            obj.getJSONArray("gyhoe").add(heOe);
            createChangLong(changLongMap,"gyh","ou",heOu);
            createChangLong(changLongMap,"gyh","oe",heOe);
        }else if(groupKey.equals(LotteryUtils.GROUP_SSC)){
            int no1 = list.get(0),no5 = list.get(4);
            String dtt = no1>no5?LotteryUtils.DRAGON:no1<no5?LotteryUtils.TIGER:LotteryUtils.DRAW;
            obj.getJSONArray("dtt").add(dtt);
            createChangLong(changLongMap,"dtt","",dtt);
            obj.getJSONArray("sumou").add(heOu);
            obj.getJSONArray("sumoe").add(heOe);
            createChangLong(changLongMap,"sumou","ou",heOu);
            createChangLong(changLongMap,"sumoe","oe",heOe);
        }else if(groupKey.equals(LotteryUtils.GROUP_KL8)){
            obj.getJSONArray("sum").add(heVal);
            obj.getJSONArray("sumou").add(heOu);
            obj.getJSONArray("sumoe").add(heOe);
            String wx=""; String tehe=""; String oehe=""; int odd=0;
            if(heVal>=210 && heVal<=695){//取五行
                wx =LotteryUtils.GOLD;
            }else if(heVal>=696 && heVal<=763){
                wx =LotteryUtils.WOOD;
            }else if(heVal>=764 && heVal<=855){
                wx =LotteryUtils.WATER;
            }else if(heVal>=856 && heVal<=923){
                wx =LotteryUtils.FIRE;
            }else{
                wx =LotteryUtils.EARTH;
            }
            if(list.get(10)<=40){//前后和
                tehe=LotteryUtils.TOP;
            }else if(list.get(9)>40){
                tehe=LotteryUtils.END;
            }else if(list.get(9)<=40 && list.get(10)>=41){
                tehe=LotteryUtils.DRAW;
            }
            for(int i=0,len=list.size();i<len;i++){
                if(list.get(i)%2==1){
                    odd=odd+1;
                }
            }
            if(odd>=11){//单双和
                oehe=LotteryUtils.ODD;
            }else if(odd==10){
                oehe=LotteryUtils.DRAW;
            }else {
                oehe=LotteryUtils.EVEN;
            }
            String sumoe = heVal%2==1?LotteryUtils.ODD:LotteryUtils.EVEN;
            obj.getJSONArray("tehe").add(tehe);
            obj.getJSONArray("oehe").add(oehe);
            obj.getJSONArray("wx").add(wx);
            if(heVal==810){//和值810
                sumoe=LotteryUtils.DRAW;
                heOu=LotteryUtils.DRAW;
            }
            createChangLong(changLongMap,"sumou","ou",heOu);
            createChangLong(changLongMap,"sumoe","oe",sumoe);
            createChangLong(changLongMap,"tehe","te",tehe);
            createChangLong(changLongMap,"oehe","oehe",oehe);
            createChangLong(changLongMap,"wx","wx",wx);
        }else if(groupKey.equals(LotteryUtils.GROUP_LUCK28)){
            String sb="";
            if(heVal==1 || heVal==4 || heVal==7 || heVal==10 || heVal==16 || heVal==19 || heVal==22 || heVal==25){//色波
                sb=LotteryUtils.GREEN;
            }else if(heVal==2 || heVal==5 || heVal==8 || heVal==11 || heVal==17 || heVal==20 || heVal==23 || heVal==26){
                sb=LotteryUtils.BLUE;
            }else if(heVal==3 || heVal==6 || heVal==9 || heVal==12 || heVal==15 || heVal==18 || heVal==21 || heVal==24){
                sb=LotteryUtils.RED;
            }else{
                sb="yellowBall";
            }
            createChangLong(changLongMap,"sumsb","sb",sb);
            createChangLong(changLongMap,"sumou","ou",heOu);
            createChangLong(changLongMap,"sumoe","oe",heOe);
        }else  if(groupKey.equals(LotteryUtils.GROUP_11X5)){
            int no1 = list.get(0),no5 = list.get(4);
            String dtt = no1>no5?LotteryUtils.DRAGON:no1<no5?LotteryUtils.TIGER:LotteryUtils.DRAW;
            obj.getJSONArray("dtt").add(dtt);
            String ws=heVal%10 >= 5?LotteryUtils.OVER:LotteryUtils.UNDER;
            obj.getJSONArray("sumwsou").add(ws);
            if(heOu.equals(LotteryUtils.OVER) && heVal==30 ){
                heOu = LotteryUtils.DRAW;
            }
            obj.getJSONArray("sumou").add(heOu);
            obj.getJSONArray("sumoe").add(heOe);
            createChangLong(changLongMap,"sumou","ou",heOu);
            createChangLong(changLongMap,"sumoe","oe",heOe);
            createChangLong(changLongMap,"sumwsou","ou",ws);
            createChangLong(changLongMap,"dtt","",dtt);
        }else  if(groupKey.equals(LotteryUtils.GROUP_GXKL10)){
            int no1 = list.get(0),no5 = list.get(4);
            String dtt = no1>no5?LotteryUtils.DRAGON:no1<no5?LotteryUtils.TIGER:LotteryUtils.DRAW;
            obj.getJSONArray("dtt").add(dtt);
            if(heOu.equals(LotteryUtils.UNDER) && heVal==55 ){
                heOu = LotteryUtils.DRAW;
            }
            String ws=heVal%10 >= 5?LotteryUtils.OVER:LotteryUtils.UNDER;
            obj.getJSONArray("sumws").add(ws);
            obj.getJSONArray("sumou").add(heOu);
            obj.getJSONArray("sumoe").add(heOe);
            createChangLong(changLongMap,"sumou","ou",heOu);
            createChangLong(changLongMap,"sumoe","oe",heOe);
            createChangLong(changLongMap,"sumws","oe",ws);//总和尾数
            createChangLong(changLongMap,"dtt","",dtt);
        }else  if(groupKey.equals(LotteryUtils.GROUP_HAPPY10)){
            String dtt1 = list.get(0)>list.get(7)?LotteryUtils.DRAGON:LotteryUtils.TIGER;
            String dtt2 = list.get(1)>list.get(6)?LotteryUtils.DRAGON:LotteryUtils.TIGER;
            String dtt3 = list.get(2)>list.get(5)?LotteryUtils.DRAGON:LotteryUtils.TIGER;
            String dtt4 = list.get(3)>list.get(4)?LotteryUtils.DRAGON:LotteryUtils.TIGER;
            String dtt1Key = new StringBuilder().append("no").append(1).append("dtt").toString();
            obj.getJSONArray(dtt1Key).add(dtt1);
            obj.getJSONArray("no2dtt").add(dtt2);
            obj.getJSONArray("no3dtt").add(dtt3);
            obj.getJSONArray("no4dtt").add(dtt4);
            createChangLong(changLongMap,"no1dtt","",dtt1);
            createChangLong(changLongMap,"no2dtt","",dtt2);
            createChangLong(changLongMap,"no3dtt","",dtt3);
            createChangLong(changLongMap,"no4dtt","",dtt4);
            String ws=heVal%10 >= 5?LotteryUtils.OVER:LotteryUtils.UNDER;
            obj.getJSONArray("sumws").add(ws);
            obj.getJSONArray("sumou").add(heOu);
            obj.getJSONArray("sumoe").add(heOe);
            createChangLong(changLongMap,"sumoe","oe",heOe);
            createChangLong(changLongMap,"sumou","ou",heOu);
            createChangLong(changLongMap,"sumws","oe",ws);//总和尾数
        }
        if(!groupKey.equals(LotteryUtils.GROUP_KL8) && !groupKey.equals(LotteryUtils.GROUP_LUCK28)){
            for(int i=0,len=list.size();i<len;i++){
                String valKey = new StringBuilder().append("no").append(i+1).append("val").toString();
                obj.getJSONArray(valKey).add(list.get(i));
                String ouKey = new StringBuilder().append("no").append(i+1).append("ou").toString();
                String noOu = list.get(i)>noOverVal?LotteryUtils.OVER:LotteryUtils. UNDER;
                String oeKey = new StringBuilder().append("no").append(i+1).append("oe").toString();
                String noOe = list.get(i)%2==1?LotteryUtils.ODD:LotteryUtils.EVEN;

                String noKey = new StringBuilder().append("no").append(i+1).toString();
                String numKey = new StringBuilder().append(list.get(i)).toString();
                obj.getJSONObject(noKey).put(numKey,obj.getJSONObject(noKey).getInteger(numKey)+1);

                if(groupKey.equals(LotteryUtils.GROUP_11X5)){
                    if(noOu.equals(LotteryUtils.OVER) && list.get(i)==11 ){
                        noOu = LotteryUtils.DRAW;
                    }
                    if(noOe.equals(LotteryUtils.ODD) && list.get(i)==11 ){
                        noOe = LotteryUtils.DRAW;
                    }
                }else if(groupKey.equals(LotteryUtils.GROUP_GXKL10)){
                    String flsx="";String sb="";String heoe="";String ws="";
                    if(list.get(i)==21){
                        ws=LotteryUtils.DRAW;
                        flsx=LotteryUtils.DRAW;
                        noOe=LotteryUtils.DRAW;
                        noOu=LotteryUtils.DRAW;
                    }else{
                        ws=list.get(i)%10 >= 5?LotteryUtils.OVER:LotteryUtils.UNDER;
                        if(list.get(i)==1 || list.get(i)==2 || list.get(i)==3 || list.get(i)==4 || list.get(i)==5 ){
                            flsx=LotteryUtils.F;
                        }else  if(list.get(i)==6 || list.get(i)==7 || list.get(i)==8 || list.get(i)==9 || list.get(i)==10 ){
                            flsx=LotteryUtils.L;
                        }else  if(list.get(i)==11 || list.get(i)==12 || list.get(i)==13 || list.get(i)==14 || list.get(i)==15 ){
                            flsx=LotteryUtils.S;
                        }else  if(list.get(i)==16 || list.get(i)==17 || list.get(i)==18 || list.get(i)==19 || list.get(i)==20 ){
                            flsx=LotteryUtils.X;
                        }
                    }
                    if(list.get(i)==1 || list.get(i)==4 || list.get(i)==7 || list.get(i)==10 || list.get(i)==13 || list.get(i)==16 || list.get(i)==19 ){
                        sb=LotteryUtils.RED;
                    }else  if(list.get(i)==2 || list.get(i)==5 || list.get(i)==8 || list.get(i)==11 || list.get(i)==14 || list.get(i)==17 || list.get(i)==20 ){
                        sb=LotteryUtils.BLUE;
                    }else  if(list.get(i)==3 || list.get(i)==6 || list.get(i)==9 || list.get(i)==12 || list.get(i)==15 || list.get(i)==18 || list.get(i)==21 ){
                        sb=LotteryUtils.GREEN;
                    }
                    heoe=((list.get(i)%10)+(list.get(i)/10%10))%2==1?LotteryUtils.ODD:LotteryUtils.EVEN;
                    String flsxKey = new StringBuilder().append("no").append(i+1).append("flsx").toString();
                    String wsouKey = new StringBuilder().append("no").append(i+1).append("wsou").toString();
                    String sbKey = new StringBuilder().append("no").append(i+1).append("sb").toString();
                    String heKey = new StringBuilder().append("no").append(i+1).append("heoe").toString();

                    obj.getJSONArray(wsouKey).add(ws);
                    obj.getJSONArray(flsxKey).add(flsx);
                    obj.getJSONArray(sbKey).add(sb);
                    obj.getJSONArray(heKey).add(heoe);
                    createChangLong(changLongMap,wsouKey,"ws",ws);
                    createChangLong(changLongMap,sbKey,"sb",sb);
                    createChangLong(changLongMap,flsxKey,"flsx",flsx);
                    createChangLong(changLongMap,heKey,"heoe",heoe);
                }else if(groupKey.equals(LotteryUtils.GROUP_HAPPY10)){
                    String heoe="";String ws="";
                    ws=list.get(i)%10 >= 5?LotteryUtils.OVER:LotteryUtils.UNDER;
                    heoe=((list.get(i)%10)+(list.get(i)/10%10))%2==1?LotteryUtils.ODD:LotteryUtils.EVEN;
                    String wsouKey = new StringBuilder().append("no").append(i+1).append("wsou").toString();
                    String heKey = new StringBuilder().append("no").append(i+1).append("heoe").toString();
                    obj.getJSONArray(wsouKey).add(ws);
                    obj.getJSONArray(heKey).add(heoe);
                    createChangLong(changLongMap,wsouKey,"ws",ws);
                    createChangLong(changLongMap,heKey,"heoe",heoe);
                }
                obj.getJSONArray(oeKey).add(noOe);
                obj.getJSONArray(ouKey).add(noOu);
                createChangLong(changLongMap,noKey,"ou",noOu);
                createChangLong(changLongMap,noKey,"oe",noOe);
                if(groupKey.equals(LotteryUtils.GROUP_PK10) && i < 5) {
                    String noDt = list.get(i) > list.get(len - i - 1) ? LotteryUtils.DRAGON : LotteryUtils.TIGER;
                    createChangLong(changLongMap,noKey,"dt", noDt);
                }
            }
        }
    }

    /**
     * 赛果生成长龙数据
     *@auther yzy
     *@date 2019/8/13 17:09
     */
    private static void createChangLong(Map<String, Integer> changLongMap, String field, String type, String typeVal) {
        String fieldTypeChangLong = field + type + "_changLong";
        if (changLongMap.get(fieldTypeChangLong) == null || changLongMap.get(fieldTypeChangLong) == -1) {
            String fieldTypeVal = field + "_" + typeVal;
            if (changLongMap.get(fieldTypeVal) != null) {
                changLongMap.put(fieldTypeVal, changLongMap.get(fieldTypeVal) + 1);
            } else {
                if (changLongMap.get(fieldTypeChangLong) == null) {
                    changLongMap.put(fieldTypeVal, 1);
                    changLongMap.put(fieldTypeChangLong, -1);
                } else {
                    changLongMap.put(fieldTypeChangLong, -2);
                }
            }
        }
    }



}
