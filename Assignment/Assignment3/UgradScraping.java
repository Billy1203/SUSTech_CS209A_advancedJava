import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UgradScraping {
    public static void main(String[] args){
        HashMap<Integer, Integer> count = new HashMap<>();
        final String url = "http://www.cs.princeton.edu/people/ugrad";
        try{
            Document doc = Jsoup.connect(url).get();
            Elements web_page = doc.select(".people-ugrad");
            for(Element e:web_page){
                Elements data = e.select("li");
//                StringBuilder test = new StringBuilder();
                for(Element item:data){
                    int year = Integer.valueOf(item.text().split(" '")[1]);
                    String name = item.text().split(" '")[0];
                    name = name.replace(", "," ");
                    System.out.println(name+", "+year);
                    if(count.containsKey(year)){
                        count.put(year, count.get(year)+1);
                    }else{
                        count.put(year, 1);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        for(Map.Entry<Integer,Integer> i:count.entrySet())
            System.out.println("Graduating year:"+i.getKey()+", Student number:"+i.getValue());
    }
}
