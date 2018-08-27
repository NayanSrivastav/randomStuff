import com.tinify.Source;
import com.tinify.Tinify;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TinyPng {
    static Map<String, Integer> typeCount = new HashMap<>(90000);

    public static void main(String[] args) throws IOException {
        Tinify.setKey("SXPeFidnvaORwXpAw7ZkgniLQStxEAwf");
        int i = Tinify.compressionCount();
        File file = new File("/Users/nayan/Documents/personal/LuxoLiving/vhosts/luxoliving.com.au/images");
        compress(file);

        System.out.println(typeCount);
    }

    ///Users/nayan/Documents/personal/LuxoLiving/vhosts/luxoliving.com.au/images/shopping_cart.png
    private static void compress(File file) throws IOException {
        if (file.exists()) {
            File[] dirContents = file.listFiles();
            if (dirContents != null) {
                for (File content : dirContents) {
                    if (content.isDirectory()) {
                        compress(content);
                    } else {
                        Source source = Source.fromFile(content.getPath());
                        String path = content.getPath();
                        String fileName = content.getName();
                        if (fileName.contains(".")) {
                            String[] split = fileName.split("\\.");
                            updateTypeCount(split[split.length - 1].toLowerCase());
                            String newFilePath = path.substring(0, path.indexOf(fileName)) + "____compressed____" + fileName;
                            source.toFile(content.getPath());
                        }
                    }
                }
            }
        }
    }

    private static void updateTypeCount(String type) {
        if (!typeCount.containsKey(type)) {
            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
        } else {
            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
        }
    }
}
