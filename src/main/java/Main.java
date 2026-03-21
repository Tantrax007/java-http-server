import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    System.out.println("File content using 8 bytes chunk: " + readFile());
  }

  private static String readFile() {
    final StringBuilder sb = new StringBuilder();
    try (final FileReader fis = new FileReader("README.md")) {
      int index = 0;
      int[] eightChunk = new int[8];

      while (true) {
        final int intChar; // Local

        intChar = fis.read();

        // If we reach the end
        if (intChar == -1) return sb.toString();

        eightChunk[index] = intChar;

        // If we have chunk ready
        if (index == 7) {
          System.out.println("Chunk ready");
          index = 0;
          sb.append(castBuffer(eightChunk));
        } else {
          System.out.println("Building chunk");
          index++;
        }
      }

    } catch (FileNotFoundException e) {
      System.err.println("Unable to find file");
      return "";
    } catch (IOException io) {
      System.err.println("Error while opening file");
      return "";
    }
  }

  private static String castBuffer(int[] byteBuffer) {
    if (byteBuffer.length != 8) {
      System.err.println("The buffer length is not exactly 8 bytes long");
      throw new IllegalArgumentException("Invalid byteArray length");
    }

    final StringBuilder sb = new StringBuilder();

    for (int j : byteBuffer) {
      System.out.println("Character: " + (char) j);
      sb.append((char) j);
    }

    System.out.println("Chunk result: " + sb.toString());

    return sb.toString();
  }
}
