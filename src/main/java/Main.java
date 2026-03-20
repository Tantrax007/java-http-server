import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    System.out.println("Contenido del fichero: " + readFile());
  }

  private static String readFile(){
    final StringBuilder sb = new StringBuilder();
    try (final FileReader fis = new FileReader("README.md")){
      int intChar;
      do{
        intChar = fis.read();
        System.out.println("Caracter leido: " + (char) intChar);
        sb.append((char) intChar);
      } while (intChar != -1);
    } catch (FileNotFoundException e) {
      System.err.println("No ha sido posible encontrar el archivo");
      return "";
    } catch (IOException io){
      System.err.println("Error al manipular el fichero README.md");
      return "";
    }

    return sb.toString();
  }
}
