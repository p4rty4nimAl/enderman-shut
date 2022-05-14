package com.p4.endermanshut;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigInit {
        public static double endermanVolume = 100D;
        public static boolean turnOffScreams = true;
        public static File configDir;
        public static File configFile;
        public static FileWriter configFileWriter;

        public static boolean loadData() throws IOException {
                if (Files.readString(configFile.toPath()).length() == 0) return true;
                endermanVolume = Double.parseDouble(Files.readString(configFile.toPath()).split("/")[0]);
                turnOffScreams = Boolean.parseBoolean(Files.readString(configFile.toPath()).split("/")[1]);
                return true;
        }
        public static boolean init() throws IOException {
                configDir = new File(FabricLoader.getInstance().getConfigDir().toFile(), "enderman-shut");
                configFile = new File(configDir + "\\config.txt");
                if (!configFile.exists()) {
                        if (!configDir.mkdirs()) return false;
                        if (!configFile.createNewFile()) return false;
                }
                return loadData();
        }

        public static int save() throws IOException {
                configFileWriter = new FileWriter(configFile);
                configFileWriter.write(endermanVolume + "/" + turnOffScreams);
                configFileWriter.close();
                return 1;
        }
}

