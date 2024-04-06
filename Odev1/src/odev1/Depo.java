/**
*
* @author Mert Eser Meral / eser.meral@ogr.sakarya.edu.tr
* @since 01.04.2024
* G211210047 / 1-B
* <p>
* Git depo klonlamasinin yapildigi class
* </p>
*/
package odev1;

import java.io.File;
import java.io.IOException;

public class Depo {
	public static void cloneRepository(String repositoryUrl) { 
        try {
            // Git komutuyla depoyu klonlama
            ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repositoryUrl);
            processBuilder.directory(new File(System.getProperty("user.dir"))); // calisma dizinini ayarla
            Process process = processBuilder.start();

            // Git klonlama islemini bekle
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Depo başarıyla klonlandı.");
            } else {
                System.err.println("Depo klonlanırken bir hata oluştu.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	
}
