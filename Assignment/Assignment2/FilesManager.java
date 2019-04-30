import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FilesManager {
    // count size for case 1
    public static long getCount(File f){
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for(int i=0;i<flist.length;i++){
            if(flist[i].isDirectory()){
                size = size+getCount(flist[i]);
                size--;
            }
        }
        return size;
    }

    // memory size for case 2
    public static long getFileSize(File f) throws Exception{
        long size = 0;
        if(f.exists()){
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            size = fis.available();
        }else{
            f.createNewFile();
            System.out.println("No such file");
        }
        return size;
    }

    // memory size for case 2
    public static long getDictSize(File f){
        long size = 0;
        File flist[] = f.listFiles();
        for(int i=0;i<flist.length;i++){
            if(flist[i].isDirectory()){
                size = size+getDictSize(flist[i]);
            }else{
                size = size+flist[i].length();
            }
        }
        return size;
    }


    // case 1 return the count of args[0]
    public static void countNumber(File file) throws Exception {
        if(file.isDirectory()){
            System.out.println(getCount(file));
        }else{
            System.out.println("This is file, the count is only 1.");
        }
    }

    // case 2 return the memory size of args[0] with "k"
    public static void countMemory(File file) throws Exception{
        long l = 0;
        if(file.isDirectory()){
            l = getDictSize(file);
            System.out.println(l/1024+"k");
        }else{
            l = getFileSize(file);
            System.out.println(l/1024+"k");
        }
    }

    // case 3 show the big file which is bigger than 1000000
    public static void countBigFile(File file){
        if(file.isFile()){
            if(file.length()>1000000){
                System.out.println(file.getPath());
            }
        }else{
            File children[] = file.listFiles();
            if(children!=null){
                for(File f : children)
                    countBigFile(f);
            }
        }
    }

    // for case 4
    public static final Map<String, Integer> resultMap = new HashMap<String, Integer>();

    // get file type for case 4
    public static void getFileType(File file){
        if(!file.exists()){
            return;
        }

        File[] fileList = file.listFiles();
        for(File file1 : fileList){
            if(file1.isFile()){
                String endTemp = file1.getName().substring(file1.getName().lastIndexOf(".") + 1);
                Integer num = resultMap.get(endTemp);
                if (num == null) {
                    resultMap.put(endTemp, 1);
                } else {
                    resultMap.put(endTemp, num + 1);
                }
            }
            if(file1.isDirectory()){
                getFileType(file1);
            }
        }
    }

    // case 4
    public static void countCategories(File file){
        getFileType(file);
        Set<Map.Entry<String, Integer>> entrySet = resultMap.entrySet();
        Iterator<Map.Entry<String, Integer>> it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            System.out.println("."+entry.getKey() + "," + entry.getValue());
        }
    }

    public static void main(String[] args){
        FilesManager filesManager = new FilesManager();
        try{
            String path = args[0];
            File file = new File(path);
            switch (args[1]){
                case "1":
                    countNumber(file);
                    break;
                case "2":
                    countMemory(file);
                    break;
                case "3":
                    countBigFile(file);
                    break;
                case "4":
                    countCategories(file);
                    break;
                default:
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
