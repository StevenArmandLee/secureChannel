package secureChannel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class RC4 {
	//Werte k�nnen angepasst werden, wobei zu beachten ist, dass wenn KEY_SIZE_MAX ge�ndert wird,
		//alte Keys nicht mehr funktionieren werden. Genaueres in der Methode schedule();
		private static final int KEY_SIZE_MIN = 1;
		private static final int KEY_SIZE_MAX = 256;
		
		/**
		 * Dies dient zur �berpr�fung, ob der Key g�ltig ist.
		 * @param strkey - der zu pr�fende Key
		 * @return
		 */
		public boolean checkKey(String strkey){
			char[] key = strkey.toCharArray();
			if(key.length < KEY_SIZE_MIN || key.length >= KEY_SIZE_MAX)
				return false;
			return true;
		}
		
		/**
		 * Erschwert das Nachvollziehen des Schl�ssels
		 * @param key String
		 * @return
		 */
		private int[] schedule(char[] key) {
			//Array f�llen, damit Werte sp�ter vertauscht werden k�nnen.
			int schedule[] = new int[KEY_SIZE_MAX];
			for(int i = 0; i < KEY_SIZE_MAX; i++){
				schedule[i] = i;
			}
			//Dieser Teil ist eine einfache Permutation. Die Verschl�sselung des Textes w�rde auch ohne diesen Teil funktionieren.
			//Sie dient jedoch dazu, den Key schwerer nachvollziehbarer zu machen, in dem hier "zuf�llig" Teile vertauscht werden,
			//wobei die Key-L�nge und die Maximale Key-L�nge als Seed verwendet werden.
			int j = 0;
			for(int i = 0; i < KEY_SIZE_MAX; i++){
				j = (j + schedule[i] + key[i % key.length]) % KEY_SIZE_MAX;
				swap(i, j, schedule);
			}
			// -- //
			return schedule;
		}
		
		/**
		 * Diese Methode dient zur Verschl�sselung eines Strings.
		 * Wird ein bereits verschl�sselter String mit dem selbigen Key angegeben, der zum Verschl�sseln verwendet wurde, so wird der String wieder entschl�sselt.
		 * 
		 * @param strkey - Der Key
		 * @param strmsg - Die Nachricht, die veschl�sselt bzw. entschl�sselt werden soll
		 * @return
		 */
		public char[] crypt(String strkey, String strmsg){
			char[] key = strkey.toCharArray(); //Der Key
			char[] msg = strmsg.toCharArray(); //Der zu verschl�sselnde Text
			char[] res = new char[msg.length]; //Das verschl�sselte Ergebnis muss genau so gro� wie der zu verschl�sselnde Text sein.
			
			int schedule[] = schedule(key);
			
			int k = 0;
			int j = 0;
			
			//Hier findet die eigentliche Verschl�sselung statt.
			for(int i = 0; i < msg.length; i++){//Es wird jeder einzelnde char im Text einzelnd durchgegangen
				k = (k+1) % KEY_SIZE_MAX; 			//
				j = (j+schedule[k]) % KEY_SIZE_MAX;		//�nlich wie in der schedule() Methode, werden hier erneut "zuf�llige" Teile vertauscht
				swap(k, j, schedule);				//
				
				//Hier wird die Nachricht letztendlich verschl�sselt.
				int random = schedule[(schedule[k] + schedule[j]) % KEY_SIZE_MAX];	//Erst wird ein "zuf�lliger" Wert aus der Schedule genommen,
				res[i] = (char)(random^(int)msg[i]);					//Dann wird immer ein char aus der Nachricht bitweise XOR mit dem "zuf�lligen" Wert genommen.
			}
			return res; //Jeder einzelnde char der Nachricht wurde ge�ndert und in res gepackt; Der Text ist verschl�sselt.
		}

		
		/**
		 * Diese Methode ist nur daf�r da um Platz zu sparen. Sie tauscht die Werte an den Stellen i und j in einem Array.
		 * @param i int
		 * @param j int
		 * @param schedule int[]
		 */
		public void swap(int i, int j, int[] schedule){
			int old = schedule[i];
			schedule[i] = j;
			schedule[j] = old;		
		}

}
