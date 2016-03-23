package ch.heigvd.res.caesar.protocol;

/**
 *
 * @author Olivier Liechti
 */
public class Protocol {

   public static final int A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER = 42;
   public static final int BUFFER_SIZE = 256;

   public static String encrypt(String src, int key) {
      String dest = "";
      for (int i = 0; i < src.length(); i++) {
         char character = src.charAt(i);
         char shiftedCharacter;

         //if it's a lowercase letter
         if ('a' <= character && character <= 'z') {
            shiftedCharacter = (char) ('a' + (((character - 'a') + key) % 26));
         } else if ('A' <= character && character <= 'Z') {
            shiftedCharacter = (char) ('A' + (((character - 'A') + key) % 26));
         } else {
            shiftedCharacter = character;
         }
         dest += shiftedCharacter;
      }
      return dest;
   }

   public static String decrypt(String src, int key) {
      String dest = "";
      for (int i = 0; i < src.length(); i++) {
         char character = src.charAt(i);
         char shiftedCharacter;
         int shift;
         if ('a' <= character && character <= 'z') {
            shift = ((character - 'a') - key);
            if(shift < 0){
               shift += 26;
            }
            shiftedCharacter = (char) ('a' + shift);
            
         } else if ('A' <= character && character <= 'Z') {
            shift = ((character - 'A') - key);
            if(shift < 0){
               shift += 26;
            }
            shiftedCharacter = (char) ('A' + shift);
         } else {
            shiftedCharacter = character;
         }
         dest += shiftedCharacter;
      }
      return dest;

   }

}
