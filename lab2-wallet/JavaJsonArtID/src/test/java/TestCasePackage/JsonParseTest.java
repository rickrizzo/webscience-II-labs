package TestCasePackage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class JsonParseTest {

    @Test
    public void readTransaction(){
        JsonParser parser = new JsonParser();
        InputStream inputStream;
        ClassLoader classLoader;
        classLoader = getClass().getClassLoader();
        inputStream = classLoader.getResourceAsStream("sample.json");
        Reader reader = new InputStreamReader(inputStream);

        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();

        String JsonString = rootObject.toString();
        String fileName = "testFileJasonJava.txt";
        try{

            FileWriter fileWriter = new FileWriter( fileName,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(JsonString);
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error writing to file '"+fileName+"'");//lookup formatting
        }
    }
}
