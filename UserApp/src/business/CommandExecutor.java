package business;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

class ImagePath //pojo class
{
    String file;

    public void setFile(String file) {
        this.file = file;
    }
}

public class CommandExecutor {

    public String execute(String user, Path image)   {
        Long elapsed = System.currentTimeMillis();
        System.out.println(String.format("Nueva imagen: %s", image));
        String img1 = String.valueOf(image);
        HttpClient httpClient = HttpClientBuilder.create().build();

        String url = String.format("http://127.0.0.1:5000/users/%s", user);
        HttpPost post = new HttpPost(url);

        Gson gson = new Gson();
        ImagePath newImage = new ImagePath();
        newImage.setFile(img1);

        StringEntity postingString = null;
        try {
            postingString = new StringEntity(gson.toJson(newImage));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response;
        try {
            response = httpClient.execute(post);
            if(response.getStatusLine().getStatusCode() == 200) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().getContent().transferTo(out);
                System.out.println(String.format("Elapsed time for petition = %d", System.currentTimeMillis()-elapsed));
                System.out.println(out.toString());
                return out.toString();
            }
        }catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

        /*
        Runtime rt = Runtime.getRuntime();
        try {
            Process proc = rt.exec(String.format(String.format("py infer.py %s %s %s",  img1, img2, img3)));

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s, lastline = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);

            }
            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.err.println(s);
                lastline = s;
            }
            if(lastline!=null)
                return lastline;
        }
        catch(IOException e)    {
            System.err.println(e.getMessage());
        }
 */

        return null;
    }
}
