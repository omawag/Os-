import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Problem {
    public char problemNumber;
    public String[] answers;

    public Problem(char problemNumber){
        this.problemNumber = Character.toUpperCase(problemNumber);
        File[] testcases = new File("testcases/"+ problemNumber +"/out").listFiles();
        answers = new String[Objects.requireNonNull(testcases).length];
        for (int i = 0; i < testcases.length; i++) {
            try {
                answers[i] = Files.readString(testcases[i].toPath());
            } catch (IOException e){throw new RuntimeException(e);}
        }
    }
}
