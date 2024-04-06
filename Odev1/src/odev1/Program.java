/**
*
* @author Mert Eser Meral / eser.meral@ogr.sakarya.edu.tr
* @since 01.04.2024
* G211210047 / 1-B
* <p>
* Programin basladigi ana class
* </p>
*/
package odev1;

public class Program {

    public static void main(String[] args) {
        
        // Kullanıcıdan GitHub depo linkini alma
        String repoLink = Input.getInput("Lütfen GitHub depo linkini girin:");
        System.out.println("Depo klonlanıyor...");
        // Depoyu klonlama
        Depo.cloneRepository(repoLink);
        String folderName = repoLink.substring(repoLink.lastIndexOf("/") + 1).replace(".git", "");
        // Klonlanmis depodaki sınıfları analiz etme
        System.out.println("Sınıflar analiz ediliyor...\n\n");
        Analiz.analyzeClasses(folderName);
    }                
}
