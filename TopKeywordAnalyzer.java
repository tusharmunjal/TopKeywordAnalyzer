package tech.codingclub.utility.GeneralClasses;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TopKeywordAnalyzer implements Runnable{
    private final String filePath;
    public TopKeywordAnalyzer(String filePath)
    {
        this.filePath=filePath;
    }

    public void run() {
        ArrayList<String> keywordsFileData=FileUtility.readFileAsList(filePath);
        HashMap<String,Integer> keywordCounter=new HashMap<String,Integer>();

        for(String row:keywordsFileData)
        {
            String[] keywords=row.split(" ");
            for(String keyword:keywords)
            {
                String str=keyword.toLowerCase();
                if(!keywordCounter.containsKey(str))
                {
                    keywordCounter.put(str,1);
                }
                else {
                    Integer value = keywordCounter.get(str);
                    keywordCounter.put(str,value+1);
                }
            }
        }

        ArrayList<KeywordCount> keywordCountArrayList = new ArrayList<KeywordCount>();
        for(String keyword:keywordCounter.keySet())
        {
            keywordCountArrayList.add(new KeywordCount(keyword,keywordCounter.get(keyword)));
        }

        Collections.sort(keywordCountArrayList, new Comparator<KeywordCount>() {
            @Override
            public int compare(KeywordCount o1, KeywordCount o2) {
                if(o2.count== o1.count)
                {
                    return o1.keyword.compareTo(o2.keyword);
                }
                return o2.count- o1.count;
            }

        });
        /*for (KeywordCount keywordCount:keywordCountArrayList)
        {
            System.out.println(keywordCount.keyword+":"+keywordCount.count);
        }*/
        String json=new Gson().toJson(keywordCountArrayList);
        System.out.println(json);
        String convertJson="{\"keyword\":\"Hello GSON\",\"count\":100}";
        KeywordCount keywordCount = new Gson().fromJson(convertJson,KeywordCount.class);
        System.out.println("Conerted to object"+keywordCount.keyword+" : "+keywordCount.count);

        String convertJsonArray="[{\"keyword\":\"Hello GSON\",\"count\":1},{\"keyword\":\"Last one\",\"count\":100}]";
        //ArrayList<KeywordCount> converyedArrayList=new Gson().fromJson(convertJsonArray, new TypeToken<ArrayList<KeywordCount>>(){},getType());
    }

    public static void main(String[] args) {
        TaskManager taskManager=new TaskManager(1);
        taskManager.waitTillQueueFreeAndAddTask(new TopKeywordAnalyzer("C:\\Users\\acer\\OneDrive\\Documents\\Jana gana mana adhinayaka jaya he.txt"));
    }

}
