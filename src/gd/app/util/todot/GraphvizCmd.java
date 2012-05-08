/**
 * 
 */
package gd.app.util.todot;

import java.io.IOException;

/**
 * 
 * Enumeration des commandes Graphviz
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @version 0.1
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
        return super.toString().toLowerCase();
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
        } catch (IOException e) {
            e.printStackTrace();
            return_value = -1;
        } catch (InterruptedException e) {
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
        if (graphviz_command.equals("dot"))
            return DOT;
        if (graphviz_command.equals("neato"))
            return NEATO;
        if (graphviz_command.equals("twopi"))
            return TWOPI;
        if (graphviz_command.equals("circo"))
            return CIRCO;
        if (graphviz_command.equals("fdp"))
            return FDP;

        return DOT;
    }

}
