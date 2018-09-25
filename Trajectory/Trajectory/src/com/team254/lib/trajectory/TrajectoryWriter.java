package com.team254.lib.trajectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TrajectoryWriter {
    private String directory;

    public void setDirectory(String directoryPath) {
        directory = directoryPath;
    }

    private String getPath(String trajectory) {
        File directory = new File(this.directory);
        String fullTrajectoryPath = trajectory + ".txt";
        File file = new File(directory, fullTrajectoryPath);
        return file.getPath();
    }

    private File createFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            return null;
        }
    }

    private boolean populateFile(File file, String data) {
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean fileWasWritten(Object file, boolean fileHasData) {
        return file instanceof File && fileHasData;
    }

    private void writeTelemetry(boolean fileWasWritten, String path) {
        if (fileWasWritten) {
            System.out.println("Wrote " + path);
        } else {
            System.err.println("File" + path + "could not be written!!!!");
            System.exit(1);
        }
    }

    public void writeFile(String fileName, String data) {
        String fullPath = getPath(fileName);
        File file = createFile(fullPath);
        boolean fileHasData = populateFile(file, data);
        boolean fileWasWritten = fileWasWritten(file, fileHasData);
        writeTelemetry(fileWasWritten, fullPath);
    }

}
