import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args) {
//    System.out.println("File content using 8 bytes chunk: " + readFileManually());
    System.out.println("File content using 8 bytes chunk version 2: " + readFileWithEightBytesBuffer());
  }

  private static String readFileWithEightBytesBuffer() {
    final StringBuilder sb = new StringBuilder();

    // Use of FileInputStream because 'FileReader' is way to limitated
    try (final FileInputStream fis = new FileInputStream("README.md")) {
      System.out.println("Total available Bytes: " + fis.available());

      final int buffer = 8;
      byte[] bytes = new byte[buffer];

      System.out.println("Gonna read from the file 8 bytes at a time");

      while(true){
        if (fis.available() <= 8){
          final int lastAvailableBytes = fis.available();
          final byte[] lastBytes = new byte[lastAvailableBytes];
          if (fis.read(lastBytes) != lastAvailableBytes) {
            System.err.println("Couldn't read " + buffer + " bytes");
            return sb.toString();
          }

          // Remaining bytes
          sb.append(new String(lastBytes, 0, lastAvailableBytes, StandardCharsets.UTF_8));

          return sb.toString();
        }

        if (fis.read(bytes) != buffer) {
          System.err.println("Couldn't read " + buffer + " bytes");
          return "";
        }

        sb.append(new String(bytes, 0, buffer, StandardCharsets.UTF_8));
      }

    } catch (FileNotFoundException e) {
      System.err.println("Unable to find file");
    } catch (IOException io) {
      System.err.println("Error while opening file");
    }

    return sb.toString();
  }

  private static String readFileManually() {
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
