/**
*
* @author Mert Eser Meral / eser.meral@ogr.sakarya.edu.tr
* @since 01.04.2024
* G211210047 / 1-B
* <p>
* Analiz kisminin yapildigi class
* </p>
*/
package odev1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analiz {
		
	
	  	public static void analyzeClasses(String dosya) {
	        // Klonlanan depodaki dosyalari ve klasörleri al
	        File directory = new File(dosya);
	        File[] files = directory.listFiles();	        
	        if (files != null) 
	        {
	        	 
	            for (File file : files) 
	            {
	                if (file.isDirectory()) { // Eger bir klasor ise, bu klasoru de analiz eder
	                    
	                    analyzeClasses(file.getAbsolutePath());
	                } 
	                else if (file.getName().endsWith(".java")) // Eger bir java dosyası ise, bu dosyayı analiz edecek.
	                {
	                    
	                    try {
	                    	if (classMi(file)) //bu sekilde sadece classlari analiz ettim
	                    		{ 
	                            	analyzeJavaFile(file);	                            	
	                        	}
	                    } 
	                    catch (IOException e) 
	                    {
	                        e.printStackTrace();
	                        
	                    }
	                }
	                
	            }
	            
	            
	        }
	        
	    }
	  	
	    private static boolean classMi(File file) throws IOException { //class olanlari ayıklayan kisim
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String line;
	        boolean sınıfBul = false;
	        while ((line = reader.readLine()) != null) {
	            if (line.contains("class ")) {
	                sınıfBul = true;
	                break;
	            }
	        }
	        reader.close();
	        return sınıfBul;
	    }


	    private static void analyzeJavaFile(File file) throws IOException { //Java dosyasini analiz kismi
	        System.out.println("Sınıf: " + file.getName());

	        int javadocSatir = 0;
	        int yorum = 0;
	        int kodSatir = 0;
	        int fonksiyonSayac = 0;
	        int loc = 0;
	        boolean inYorum = false; //yorum icinde mi kontrol icin 
	        boolean inJavadoc = false; //javadoc icinde mi kontrol icin

	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String satir;
	        while ((satir = reader.readLine()) != null) //dosyayi bastan sona okuyan while dongusu
	        {
	            satir = satir.trim(); //satirin bastaki ve sondaki yorumlari kaldirdim
	            
	            // Javadoc satırı mı kontrol et
	            if (satir.startsWith("/**")) {
	                inJavadoc = true;
	                if(satir.contains("*/")) {
	                	javadocSatir++;//tek satırlık javadoc
	                	inJavadoc = false;
	                }
	            }
	            
	            
	            else if (inJavadoc &&satir.trim().equals("*/")) { //satırda sadece */ varsa javadoc bitisi
	                    inJavadoc = false;                
	                }
	            
	            else if(inJavadoc) javadocSatir++;
	            
	            

	            else if (satir.startsWith("//")) { // tek satirlik yorum
	                yorum++;
	            } 
	            else if (satir.startsWith("/*")) { // yorum icine girdi
	                inYorum = true;
	                
	                if(satir.contains("*/")) {
	                	yorum++;//tek satirlik yorum
	                	inYorum = false;
	                	int commentEndIndex = satir.indexOf("*/"); //yorum satırının bitis indexi
	                	String codePart = satir.substring(commentEndIndex + 2);//yorumdan sonraki kisim
	                	if (!codePart.trim().isEmpty()) {
	                        // Yorum sonrasinda herhangi bir sey varsa, bunu kod satiri olarak kabul et
	                        kodSatir++;
	                    }
	                }
	                
	               
	            }
	  
	            else if (inYorum && satir.trim().equals("*/")) { //satirda sadece */ varsa yorum bitisi
	                    inYorum = false;
	                }
	            else if (inYorum) yorum++;
	                 
	            else {
	                // Bos satir veya kod satiri mi kontrol et
	                if (!satir.isEmpty()) {
	                    kodSatir++;
	                    if(satir.contains("//")) yorum++; //hem kod satiri hem yorum satiri olma durumu
	                    else if(satir.contains("/**")&&satir.contains("*/"))javadocSatir++; //hem kod hem javadoc satiri
	                    else if(satir.contains("/*")&&satir.contains("*/"))yorum++; //hem kod hem yorum satiri
	                }
	            }

	            // Fonksiyon kontrol kismi
	            String functionPattern = "^(public|private|protected|default)?\\s*(static\\s*|final\\s*|abstract\\s*|synchronized\\s*)*\\w+(\\W(\\w)*\\W)*\\s+\\w+\\([^)]*\\)(\\W|\\w)*(?<!;)$";
	            Pattern pattern = Pattern.compile(functionPattern);
	            Matcher matcher = pattern.matcher(satir);
	            if(!satir.contains("class")&&matcher.find()) {
	            	fonksiyonSayac++;
	            }
	            // LOC hesaplama
	            loc++;
	        }
	        reader.close();
	        
	        // Analiz sonuçlarını ekrana yazdiran kisim
	        System.out.println("Javadoc Satır Sayısı: " + javadocSatir); 
	        System.out.println("Yorum Satır Sayısı: " + yorum);
	        System.out.println("Kod Satır Sayısı: " + kodSatir);
	        System.out.println("LOC: " + loc);
	        System.out.println("Fonksiyon Sayısı: " + fonksiyonSayac);

	        // Yorum Sapma Yüzdesi hesaplama
	        double yg = ((double)(javadocSatir + yorum) * 0.8) / fonksiyonSayac;
	        double yh = ((double)kodSatir / fonksiyonSayac) * 0.3;
	        double yorumSapmaYuzdesi = ((100 * yg) / yh) - 100;

	        System.out.format("Yorum Sapma Yüzdesi: %%%.2f%n", yorumSapmaYuzdesi);
	        System.out.println("------------------------------");
	    }

}
