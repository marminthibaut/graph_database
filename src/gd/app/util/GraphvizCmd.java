/**
 * 
 */
package gd.app.util;

import java.io.IOException;

/**
 * @author clement
 * 
 */
public enum GraphvizCmd {
    /** dot command */
    DOT,
    /** neato command */
    NEATO,
    /** twopi command */
    TWOPI,
    /** circo command */
    CIRCO,
    /** fdp command */
    FDP;

    @Override
    public String toString() {
        switch (this) {
            case DOT:
                return "dot";
            case NEATO:
                return "neato";
            case TWOPI:
                return "twopi";
            case CIRCO:
                return "circo";
            case FDP:
                return "fdp";
            default:
                return "dot";
        }
    }

    /**
     * @param dot_file_path
     * @param output_file_path
     * @return 0 if the command executed normally, other value in otherwise.
     */
    public int exec(String dot_file_path, String output_file_path) {
        int return_value = 0;
        String graphviz_cmd = this.toString() + " " + dot_file_path + " -o "
                + output_file_path;

        Process process;
        try {
            process = Runtime.getRuntime().exec(graphviz_cmd);
            return_value = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return_value = -1;
        }

        return return_value;
    }

    /**
     * @param graphviz_command
     * @return an instance of GraphvizCmd. If graphviz_command isn't a correct
     *         value DOT is returned
     */
    public static GraphvizCmd getInstance(String graphviz_command) {
        switch (graphviz_command) {
            case "dot":
                return DOT;
            case "neato":
                return NEATO;
            case "twopi":
                return TWOPI;
            case "circo":
                return CIRCO;
            case "fdp":
                return FDP;
            default:
                return DOT;
        }
    }

}
