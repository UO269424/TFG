package business;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class CommandExecutor {

    public String execute(Path[] secuencia)   {
        String img1 = String.valueOf(secuencia[0]);
        String img2 = String.valueOf(secuencia[1]);
        String img3 = String.valueOf(secuencia[2]);
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

        return null;
    }
}
