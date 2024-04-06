/**
*
* @author Mert Eser Meral / eser.meral@ogr.sakarya.edu.tr
* @since 01.04.2024
* G211210047 / 1-B
* <p>
* Kullanicidan git depo url alinan class
* </p>
*/
package odev1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
	//kullanicidan git depo linki alinan kisim
	public static String getInput(String prompt) {
        System.out.println(prompt);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

	
}
